package com.catgen.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catgen.Company;
import com.catgen.Constants;
import com.catgen.DefaultText;
import com.catgen.MailMsgs;
import com.catgen.MailObj;
import com.catgen.MasterData;
import com.catgen.NetworkMarket;
import com.catgen.Referrer;
import com.catgen.UserSessionBean;
import com.catgen.Utils;
import com.catgen.exception.MailNotSentException;
import com.catgen.exception.NoMatchFoundException;
import com.catgen.exception.RegistrationException;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.NMCreator;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.ReferrerCreator;
import com.catgen.factories.SchemeFactory;
import com.catgen.factories.UserSessionFactory;
import com.catgen.factories.VendorCreator;
import com.catgen.factories.XMLFactory;
import com.catgen.thread.MailerThread;

public class UserSessionController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, RegistrationException, MailNotSentException, NoMatchFoundException {
		String res="";
		String strSubModule = request.getParameter("submodule");
		String userId = request.getParameter("userid");
		String pwd = request.getParameter("pwd");
		String marketname = request.getParameter("marketname");
		String address = request.getParameter("address");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zip = request.getParameter("zip");
		String country = request.getParameter("country");
		String landline = request.getParameter("landline");
		String mobile = request.getParameter("mobile");
		String email = request.getParameter("email");

		String sBusinessType = request.getParameter("businessType");
		String sScheme = request.getParameter("scheme");
		
		int businessType=0, scheme=0;
		
		try{
			businessType = Integer.parseInt(sBusinessType);
		}catch(Exception e){
		}
		
		try{
			scheme = Integer.parseInt(sScheme);
		}catch(Exception e){
		}
		
		int subModule = 0;
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}

		MasterData masterData = new MasterData();
		switch(subModule){
		case Constants.CHECK_AVAILABILITY:
			res = checkAvailability(conn, userId);
			break;
		case Constants.REGISTER:
			UserSessionBean userSessionBean = new UserSessionBean();
			userSessionBean.userId = userId;
			userSessionBean.password = pwd;
			userSessionBean.validated = Constants.VALIDATED;
			userSessionBean.scheme = scheme;
			userSessionBean.endDate = Utils.getDateAfterGivenMonths(SchemeFactory.getNumberofMonthsByScheme(conn, Integer.parseInt(request.getParameter("scheme"))));
			
			switch(businessType){
			case Constants.NETWORK_MARKET:
				NetworkMarket networkMarket = new NetworkMarket();
				networkMarket.NetworkMarketID = userId;
				networkMarket.Name = marketname;
				networkMarket.Address = address;
				networkMarket.City = city;
				networkMarket.State = state;
				networkMarket.Zip = zip;
				networkMarket.Country = country;
				networkMarket.Contact = landline;
				networkMarket.mobileNo = mobile;
				networkMarket.ContactEmail = email;
				networkMarket.Template = DefaultText.DEFAULT_TEMPLATE;
				networkMarket.LogoImage = DefaultText.DEFAULT_LOGOIMAGE;
				networkMarket.Header = DefaultText.DEFAULT_HEADER;
				networkMarket.Description = DefaultText.DEFAULT_DESC;
				userSessionBean.type = Constants.NETWORK_MARKET;
										
				masterData.userSessionBean = userSessionBean;
				masterData.networkMarket = networkMarket;
				break;
			case Constants.VENDOR:
				Company company = new Company();
				company.Code = userId;
				company.Name = marketname;
				company.Address = address;
				company.City = city;
				company.State = state;
				company.Zip = zip;
				company.Country = country;
				company.Contact = landline;
				company.mobileNo = mobile;
				company.ContactEmail = email;
				userSessionBean.type = Constants.VENDOR;
				
				masterData.userSessionBean = userSessionBean;
				masterData.company = company;
				break;
			case Constants.REFERRER:
				Referrer referrer = new Referrer();
				referrer.referrerCode = userId;
				referrer.name = marketname;
				referrer.address = address;
				referrer.city = city;
				referrer.state = state;
				referrer.zip = zip;
				referrer.countryCode = country;
				referrer.contact = landline;
				referrer.mobileNo = mobile;
				referrer.contactEmail = email;
				userSessionBean.type = Constants.REFERRER;
				
				masterData.userSessionBean = userSessionBean;
				masterData.referrer = referrer;
				break;
			}
			res = register(conn, masterData, businessType);
			break;
		case Constants.SEND_AUTHENTICATION_CODE:
			res = sendAuthenticationCode(email);
			break;
		case Constants.FORGOT_PASSWORD:
			res = handleForgottenPasswordRequest(conn, email, businessType, userId);
			break;
		}
		return res;
	}
	
	public static String checkAvailability(Connection conn, String userId) throws SQLException{
		boolean available = UserSessionFactory.isUserIdAvailable(conn, userId);
		String res = "";
		if(available){
			res = XMLFactory.getMessageElement("status", "1")+XMLFactory.getMessageElement("message", "Available");
		}else{
			res = XMLFactory.getMessageElement("status", "0")+XMLFactory.getMessageElement("message", "Not available");
		}
		return res;
	}
	
	public static String register(Connection conn, MasterData masterData, int businessType) throws RegistrationException{
		String type="", email="", body="";
		if(Constants.NETWORK_MARKET==businessType){
			body = MailMsgs.REGISTRATION_NOTIFICATION_BODY;
			type = "NETWORK MARKET";
			email = masterData.networkMarket.ContactEmail;
			NMCreator.createNewNM(conn, masterData);
		}else if(Constants.VENDOR==businessType){
			type = "VENDOR";
			email = masterData.company.ContactEmail;
			VendorCreator.createNewVendor(conn, masterData);
		}else if(Constants.REFERRER==businessType){
			body = MailMsgs.REGISTRATION_NOTIFICATION_BODY_REFERRER;
			type = "REFERRER";
			email = masterData.referrer.contactEmail;
			ReferrerCreator.createNewReferrer(conn, masterData);
		}
		try{
			String subject = MailMsgs.REGISTRATION_NOTIFICATION_SUBJECT;
			body = body.replaceAll(MailMsgs.OPENENTRY_ID, masterData.userSessionBean.userId);
			body = body.replaceAll(MailMsgs.ACCOUNT_TYPE,type);
			body = body.replaceAll(MailMsgs.PASSWORD,masterData.userSessionBean.password);
			MailObj mailObj = new MailObj();
			mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
			mailObj.to = email;
			mailObj.subject = subject;
			mailObj.body = body;
			new MailerThread(mailObj).sendMail();
		}catch(Exception e){
			e.printStackTrace();
		}
		return XMLFactory.getMessageElement("message", "Registration Successful!");
	}
	
	public static String sendAuthenticationCode(String email) throws MailNotSentException{
		String authCode = Utils.generateRandomString(8);
		MailObj mailObj = new MailObj();
		mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
		mailObj.to = email;
		mailObj.subject = MailMsgs.EMAIL_AUTHENTICATION_SUBJECT;
		mailObj.body = MailMsgs.EMAIL_AUTHENTICATION_BODY.replaceAll(MailMsgs.AUTH_CODE, authCode);
		new MailerThread(mailObj).sendMail();
		return XMLFactory.getMessageElement("authcode", authCode);
	}
	
	public static String handleForgottenPasswordRequest(Connection conn, String email, int businessType, String userId) throws NoMatchFoundException, SQLException, MailNotSentException{
		boolean isValidEmail=false, isValidType=false;
		UserSessionBean userSessionBean = new UserSessionBean();
		userSessionBean.userId = userId;
		String pwd = Utils.generateRandomString(8);
		userSessionBean.password = pwd;
		
		switch(businessType){
		case Constants.NETWORK_MARKET:
			userSessionBean.type = Constants.NETWORK_MARKET;
			isValidEmail = NetMarketFactory.NetworkMarketExistsWithEmailId(conn, userId, email);
			isValidType = UserSessionFactory.isValidUser(userId, ""+Constants.NETWORK_MARKET);
			break;
		case Constants.VENDOR:
			userSessionBean.type = Constants.VENDOR;
			isValidEmail = CompanyFactory.VendorExistsWithEmailId(conn, userId, email);
			isValidType = UserSessionFactory.isValidUser(userId, ""+Constants.VENDOR);
			break;
		}
		
		if(isValidEmail && isValidType){
			UserSessionFactory.updatePassword(conn, userSessionBean);
			
			MailObj mailObj = new MailObj();
			mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
			mailObj.to = email;
			mailObj.subject = MailMsgs.PASSWORD_RESET_NOTIFICATION_SUBJECT;
			mailObj.body = MailMsgs.PASSWORD_RESET_NOTIFICATION_BODY.replaceAll(MailMsgs.OPENENTRY_ID, userId).replaceAll(MailMsgs.PASSWORD, pwd);
			new MailerThread(mailObj).sendMail();
		}else{
			throw new NoMatchFoundException("No match found. Please retry with correct data");
		}
		return XMLFactory.getMessageElement("message", "Password reset and mailed to Email ID: "+email);
	}
}

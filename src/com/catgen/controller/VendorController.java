package com.catgen.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.catgen.Company;
import com.catgen.Constants;
import com.catgen.MailMsgs;
import com.catgen.MailObj;
import com.catgen.MasterCompany;
import com.catgen.NetworkMarket;
import com.catgen.exception.MailNotSentException;
import com.catgen.exception.NoVendorFoundException;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.XMLFactory;
import com.catgen.helper.VendorHelper;
import com.catgen.thread.MailerThread;

import java.sql.Connection;
import java.sql.SQLException;

public class VendorController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NoVendorFoundException, MailNotSentException 
	{
		String res = "";
		String strSubModule = request.getParameter("submodule");
		String marketId = request.getParameter("marketid");
		String companyCode = request.getParameter("companycode");
//		String companyURL = request.getParameter("companyurl");
//		String productURL = request.getParameter("producturl");
		String strReceiverType = request.getParameter("receiverType");
		String subject = request.getParameter("subject");
		String message = request.getParameter("message");
		
		int subModule = 0, receiverType;
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			subModule = 0;
		}
		
		try{
			receiverType = Integer.parseInt(strReceiverType);
		}catch(Exception e){
			receiverType = -1;
		}
		
		switch(subModule){
		case Constants.ADD_VENDOR:
			res = addVendor(conn, marketId, companyCode);
			break;
		case Constants.REMOVE_VENDOR:
			res = removeVendor(conn, marketId, companyCode);
			break;
		case Constants.FEATURE_VENDOR:
			res = featureVendor(conn, marketId, companyCode);
			break;
		case Constants.UNFEATURE_VENDOR:
			res = unfeatureVendor(conn, marketId, companyCode);
			break;
//		case Constants.MAKE_ASSOCIATION:
//			res = makeAssociation(conn, marketId, companyCode);
//			res = "";
//			break;
		case Constants.REMOVE_ASSOCIATION:
			res = removeAssociation(conn, marketId, companyCode);
			break;
		case Constants.UPDATE_VENDOR_DATAFEED_LINK:
//			MasterCompany masterCompany = new MasterCompany();
//			masterCompany.companyCode = companyCode;
//			masterCompany.companyURL = companyURL;
//			masterCompany.productURL = productURL;
//			res = updateDatafeedURL(conn, masterCompany);
			break;
		case Constants.REFRESH_VENDOR_DATA:
//			res = refreshVendorData(conn, companyCode);
			break;
		case Constants.MAIL_MANAGER:
			MailObj mailObj = new MailObj();
			mailObj.subject = subject;
			mailObj.body = message;
			res = mailVendor(conn,marketId,mailObj,receiverType);
			break;
		case Constants.ADD_VENDOR_DIRECTLY:
			res = addVendorDirectly(conn, marketId, companyCode);
			break;
		}
		return res;
	}
	
	public static String addVendor(Connection conn, String marketId, String companyCode) throws SQLException, MailNotSentException{
		Company company = CompanyFactory.getCompanyByCode(conn, companyCode);
		CompanyFactory.makeAssociation(conn, marketId, companyCode);
		CompanyFactory.addCompany(conn, marketId, companyCode);
//		NetworkMarket netMarket = NetMarketFactory.getNetworkMarketByCode(conn, marketId);
//		MasterMarket mm = MasterMarketFactory.getMasterMarketByCode(conn, marketId);

//		MailObj mailObj = new MailObj();
//		mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
//		mailObj.to = company.ContactEmail;
//		mailObj.subject = MailMsgs.ADD_APPROVED_NOTIFICATION_SUBJECT.replaceAll(MailMsgs.OPENENTRY_ID, netMarket.Name);
//		mailObj.body = MailMsgs.ADD_APPROVED_NOTIFICATION_BODY.replaceAll(MailMsgs.OPENENTRY_ID,company.Name).replaceAll(MailMsgs.OPENENTRY_ID2, netMarket.Name+"(http://"+mm.DomainName+")");
//		new MailerThread(mailObj).sendMail();
		
		String res = XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("companycode", companyCode);
		res += XMLFactory.getMessageElement("companyname", company.Name);
		res += XMLFactory.getMessageElement("url", StringEscapeUtils.escapeXml((null!=company.CompanyURL)?company.CompanyURL:""));
		return res;
	}
	
	public static String featureVendor(Connection conn, String marketId, String companyCode) throws SQLException{
		CompanyFactory.featureVendor(conn, marketId, companyCode);
		return XMLFactory.getMessageElement("companycode",companyCode)+XMLFactory.getMessageElement("marketid", marketId);
	}
	
	public static String unfeatureVendor(Connection conn, String marketId, String companyCode) throws SQLException{
		CompanyFactory.unfeatureVendor(conn, marketId, companyCode);
		return XMLFactory.getMessageElement("companycode",companyCode)+XMLFactory.getMessageElement("marketid", marketId);
	}
	
	
	public static String removeVendor(Connection conn, String marketId, String companyCode) throws SQLException{
		CompanyFactory.removeAssociation(conn, marketId, companyCode);
		return XMLFactory.getMessageElement("companycode",companyCode);
	}
	
//	public static String makeAssociation(Connection conn, String marketId, String companyCode) throws SQLException{
//		CompanyFactory.makeAssociation(conn, marketId, companyCode);
//		return XMLFactory.getMessageElement("marketid",marketId)+XMLFactory.getMessageElement("marketname",NetMarketFactory.getNetworkMarketByCode(conn, marketId).Name);
//	}
	
	public static String removeAssociation(Connection conn, String marketId, String companyCode) throws SQLException{
		CompanyFactory.removeAssociation(conn, marketId, companyCode);
		return XMLFactory.getMessageElement("marketid",marketId)+XMLFactory.getMessageElement("marketname",NetMarketFactory.getNetworkMarketByCode(conn, marketId).Name);
	}
	
	public static String updateDatafeedURL(Connection conn, MasterCompany masterCompany) throws SQLException{
		//MasterCompanyFactory.save(conn, masterCompany);
		//return XMLFactory.getMessageElement("message", "CompanyURL and ProductURL successfully updated for vendor "+masterCompany.companyCode+".");
		return XMLFactory.getMessageElement("message", "This functionality is discontinued");
	}
	
	public static String refreshVendorData(Connection conn, String companyCode) throws SQLException{
//		CompanyFactory.UpdateCompany(conn, companyCode);
		return XMLFactory.getMessageElement("message", "Vendor Data Refreshed.");
	}
	
	public static String mailVendor(Connection conn, String marketId, MailObj mailObj, int receiverType) throws SQLException, NoVendorFoundException, MailNotSentException{
		VendorHelper.sendMailToSelectedVendorType(conn, marketId, mailObj, receiverType);
		return XMLFactory.getMessageElement("message", "Mail Sent.");
	}
	
	public static String addVendorDirectly(Connection conn, String marketId, String companyCode) throws SQLException, MailNotSentException{
		CompanyFactory.requestAssociation(conn, marketId, companyCode);
		CompanyFactory.makeAssociation(conn, marketId, companyCode);
		CompanyFactory.addCompany(conn, marketId, companyCode);
		Company company = CompanyFactory.getCompanyByCode(conn, companyCode);
		NetworkMarket networkMarket = NetMarketFactory.getNetworkMarketByCode(conn, marketId);
		
		MailObj mailObj = new MailObj();
		mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
		mailObj.to = company.ContactEmail;
		mailObj.subject = MailMsgs.ADD_NOTIFICATION_SUBJECT;
		mailObj.body = MailMsgs.ADD_NOTIFICATION_BODY.replaceAll(MailMsgs.OPENENTRY_ID,company.Code).replaceAll(MailMsgs.OPENENTRY_ID2, networkMarket.NetworkMarketID);
		new MailerThread(mailObj).sendMail();
		
		String res = XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("companycode", companyCode);
		res += XMLFactory.getMessageElement("companyname", CompanyFactory.getCompanyByCode(conn, companyCode).Name);
		res += XMLFactory.getMessageElement("vendorcount", String.valueOf(CompanyFactory.getNetmarketMembers(conn,marketId).size()));
		res += XMLFactory.getMessageElement("nmstatus", networkMarket.premium?"premium":"");
		return res;
	}
}

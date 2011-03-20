package com.catgen.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catgen.Charge;
import com.catgen.Constants;
import com.catgen.FundBank;
import com.catgen.MailMsgs;
import com.catgen.MailObj;
import com.catgen.UserSessionBean;
import com.catgen.Utils;
import com.catgen.exception.NotASuperAdminException;
import com.catgen.factories.CategoryFactory;
import com.catgen.factories.ChargeFactory;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.FlagFactory;
import com.catgen.factories.FundBankFactory;
import com.catgen.factories.MarketMemberFactory;
import com.catgen.factories.MasterCompanyFactory;
import com.catgen.factories.MasterMarketFactory;
import com.catgen.factories.NMCreator;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.PageFactory;
import com.catgen.factories.ProductFactory;
import com.catgen.factories.ProductLineFactory;
import com.catgen.factories.ReferralFactory;
import com.catgen.factories.ReferrerFactory;
import com.catgen.factories.SchemeFactory;
import com.catgen.factories.StyleFactory;
import com.catgen.factories.SuperAdminFactory;
import com.catgen.factories.SuperNMFactory;
import com.catgen.factories.UserSessionFactory;
import com.catgen.factories.VendorCreator;
import com.catgen.factories.XMLFactory;
import com.catgen.helper.CategoryHelper;
import com.catgen.helper.FundBankHelper;
import com.catgen.thread.MailerThread;

import java.sql.Connection;
import java.sql.SQLException;

public class SuperAdminController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NotASuperAdminException, IllegalAccessException, InstantiationException, ClassNotFoundException {
		String res="";
		String strSubModule = request.getParameter("submodule");
		String id = request.getParameter("id");
		String sType = request.getParameter("type");
		String marketId = request.getParameter("marketid");
		String companyCode = request.getParameter("companycode");
		String referrerCode = request.getParameter("referrercode");
		String sBusiness = request.getParameter("business");
		String businessCode = request.getParameter("businesscode");
		String domainName = request.getParameter("domainname");
		String strAddCharge = request.getParameter("add");
		String strFeatureCharge = request.getParameter("feature");
		String strFundAmount = request.getParameter("fundamount");
		
		int subModule = 0, type=0, business=0;
		double addCharge=0.0,featureCharge=0.0,fundAmount=0.0;
		
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		try{
			type = Integer.parseInt(sType);
		}catch(Exception e){
		}
		
		try{
			business = Integer.parseInt(sBusiness);
		}catch(Exception e){
		}
		
		try{
			if(strAddCharge!=null && strAddCharge.length()>0){
				addCharge = Double.parseDouble(strAddCharge);
			}
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		try{
			if(strFeatureCharge!=null && strFeatureCharge.length()>0){
				featureCharge = Double.parseDouble(strFeatureCharge);
			}
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		try{
			if(strFundAmount!=null && strFundAmount.length()>0){
				fundAmount = Double.parseDouble(strFundAmount);
			}
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		switch(subModule){
		case Constants.DELETE_SELECTED_NM:
			res = deleteSelectedNMs(conn, marketId);
			break;
		case Constants.DELETE_SELECTED_COMPANIES:
			res = deleteSelectedCompanies(conn, companyCode);
			break;
		case Constants.LOGIN_AS:
			res = loginAs(request, id, type);
			break;
		case Constants.GENERATE_USERID:
			UserSessionBean userSessionBean = new UserSessionBean();
			userSessionBean.userId = businessCode;
			userSessionBean.password = Utils.generateRandomString(8);
			userSessionBean.type = business;
			userSessionBean.validated = true;
			userSessionBean.endDate = Utils.getDateAfterGivenMonths(SchemeFactory.getNumberofMonthsByScheme(conn, Integer.parseInt(request.getParameter("scheme"))));
			res = generateUserId(conn, userSessionBean);
			break;
		case Constants.VALIDATE_NM:
			res = validateNM(conn, marketId);
			break;
		case Constants.VALIDATE_VENDOR:
			res = validateVendor(conn, companyCode);
			break;
		case Constants.VALIDATE_REFERRER:
			res = validateReferrer(conn, referrerCode);
			break;
		case Constants.RESET_PASSWORD:
			UserSessionBean userSessionBean2 = new UserSessionBean();
			userSessionBean2.userId = businessCode;
			userSessionBean2.password = Utils.generateRandomString(8);
			userSessionBean2.type = business;
			res = resetPassword(conn, userSessionBean2);
			break;
		case Constants.INVALIDATE_NM_USER_ACCOUNT:
			UserSessionBean userSessionBean3 = new UserSessionBean();
			userSessionBean3.userId = id;
			userSessionBean3.type = type;
			res = invalidateNMUserAccount(conn, userSessionBean3);
			break;
		case Constants.INVALIDATE_VENDOR_USER_ACCOUNT:
			UserSessionBean userSessionBean4 = new UserSessionBean();
			userSessionBean4.userId = id;
			userSessionBean4.type = type;
			res = invalidateVendorUserAccount(conn, userSessionBean4);
			break;
		case Constants.IMPORT_CATEGORY_LIST_TXT:
			res = importCategories(conn);
			break;
		case Constants.UPGRADE_TO_SUPERNM:
			res = upgradeToSuperNM(conn, marketId);
			break;
		case Constants.DEGRADE_FROM_SUPERNM:
			res = degradeFromSuperNM(conn, marketId);
			break;
		case Constants.DOMAIN_MANAGER:
			res = updateDomainName(conn, marketId, domainName);
			break;
		case Constants.FETCH_CHARGES:
			res = fetchCharges(conn,marketId);
			break;
		case Constants.UPDATE_CHARGES:
			res = updateCharges(conn,marketId,addCharge,featureCharge);
			break;
		case Constants.DEFAULT_CHARGES:
			res = defaultCharges(conn,marketId);
			break;
		case Constants.FETCH_FUND:
			res = fetchFunds(conn,marketId);
			break;
		case Constants.ADD_FUND:
			res = addFund(conn,marketId,fundAmount);
			break;
		case Constants.CHANGE_PREMIUM_STATUS:
			res = changePremiumStatus(conn, marketId);
			break;
		}	
		return res;
	}
	
	private static String deleteSelectedNMs(Connection conn, String networkmarkets) throws SQLException {
		if(networkmarkets != null && networkmarkets.length() > 0){
			String[] marketIds = networkmarkets.split(":");
			for(String marketId : marketIds){
				NetMarketFactory.remove(conn, marketId);
				CategoryFactory.removeAll(conn, marketId);
				UserSessionFactory.removeNM(conn, marketId);
				MarketMemberFactory.removeNM(conn, marketId);
				ProductFactory.removeNM(conn, marketId);
				ReferralFactory.removeNM(conn, marketId);
				StyleFactory.removeNM(conn, marketId);
				PageFactory.removeAll(conn, marketId);
				SuperNMFactory.removeAll(conn, marketId);
			}
		}
		return "";
	}

	private static String deleteSelectedCompanies(Connection conn, String companies) throws SQLException {
		if(companies != null && companies.length() > 0){
			String[] companiesCode = companies.split(":");
			for(String companyCode : companiesCode){
				CompanyFactory.removeCompany(conn, companyCode);
				UserSessionFactory.removeVendor(conn, companyCode);
				MarketMemberFactory.removeCompany(conn, companyCode);
				MasterCompanyFactory.removeCompany(conn, companyCode);
				ProductFactory.removeCompany(conn, companyCode);
				ProductLineFactory.removeCompany(conn, companyCode);
				ReferralFactory.removeCompany(conn, companyCode);
			}
		}
		return "";
	}

	public static String loginAs(HttpServletRequest request, String id, int type) throws NotASuperAdminException{
		String ex_user = (String)request.getSession().getAttribute("USER");
		String ex_type = (String)request.getSession().getAttribute("TYPE"); 
		boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(ex_user, ex_type);
		if(isSuperAdmin){
			request.getSession().setAttribute("USER", id);
			request.getSession().setAttribute("TYPE", String.valueOf(type));
		}else{
			throw new NotASuperAdminException("Illegal Access! Can be accessed by Super Admin only");
		}
		return "";
	}
	
	public static String generateUserId(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		UserSessionFactory.save(conn, userSessionBean);
		return XMLFactory.getMessageElement("userid", userSessionBean.userId)+XMLFactory.getMessageElement("pwd",userSessionBean.password )+XMLFactory.getMessageElement("type", String.valueOf(userSessionBean.type));
	}
	
	public static String validateNM(Connection conn, String marketId) throws SQLException{
		UserSessionBean userSessionBean = new UserSessionBean();
		userSessionBean.userId = marketId;
		userSessionBean.type = Constants.NETWORK_MARKET;
		UserSessionFactory.validateUser(conn, userSessionBean);
		try{
			String body = MailMsgs.VALIDATION_SUCCESSFUL_BODY;
			body = body.replaceAll(MailMsgs.OPENENTRY_ID, marketId);
			MailObj mailObj = new MailObj();
			mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
			mailObj.to = NetMarketFactory.getNetworkMarketByCode(conn, marketId).ContactEmail;
			mailObj.subject = MailMsgs.WELCOME;
			mailObj.body = body;
			new MailerThread(mailObj).sendMail();
		}catch(Exception e){
			e.printStackTrace();
		}
		return(XMLFactory.getMessageElement("marketid", marketId));
	}
	
	public static String validateVendor(Connection conn, String companyCode) throws SQLException{
		UserSessionBean userSessionBean = new UserSessionBean();
		userSessionBean.userId = companyCode;
		userSessionBean.type = Constants.VENDOR;
		UserSessionFactory.validateUser(conn, userSessionBean);
		try{
			String body = MailMsgs.VALIDATION_SUCCESSFUL_BODY;
			body = body.replaceAll(MailMsgs.OPENENTRY_ID, companyCode);
			MailObj mailObj = new MailObj();
			mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
			mailObj.to = CompanyFactory.getCompanyByCode(conn, companyCode).ContactEmail;
			mailObj.subject = MailMsgs.WELCOME;
			mailObj.body = body;
			new MailerThread(mailObj).sendMail();
		}catch(Exception e){
			e.printStackTrace();
		}
		return(XMLFactory.getMessageElement("companycode", companyCode));
	}
	
	public static String validateReferrer(Connection conn, String referrerCode) throws SQLException{
		UserSessionBean userSessionBean = new UserSessionBean();
		userSessionBean.userId = referrerCode;
		userSessionBean.type = Constants.REFERRER;
		UserSessionFactory.validateUser(conn, userSessionBean);
		try{
			String body = MailMsgs.VALIDATION_SUCCESSFUL_BODY;
			body = body.replaceAll(MailMsgs.OPENENTRY_ID, referrerCode);
			MailObj mailObj = new MailObj();
			mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
			mailObj.to = ReferrerFactory.getReferrerByReferrerCode(conn, referrerCode).contactEmail;
			mailObj.subject = MailMsgs.WELCOME;
			mailObj.body = body;
			new MailerThread(mailObj).sendMail();
		}catch(Exception e){
			e.printStackTrace();
		}
		return(XMLFactory.getMessageElement("referrercode", referrerCode));
	}
	
	public static String resetPassword(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		UserSessionFactory.updatePassword(conn, userSessionBean);
		try{
			String to="";
			switch(userSessionBean.type){
			case Constants.NETWORK_MARKET:
				to=NetMarketFactory.getNetworkMarketByCode(conn, userSessionBean.userId).ContactEmail;
				break;
			case Constants.VENDOR:
				to=CompanyFactory.getCompanyByCode(conn, userSessionBean.userId).ContactEmail;
				break;
			}
			MailObj mailObj = new MailObj();
			mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
			mailObj.to = to;
			mailObj.subject = MailMsgs.PASSWORD_RESET_NOTIFICATION_SUBJECT;
			mailObj.body = MailMsgs.PASSWORD_RESET_NOTIFICATION_BODY.replaceAll(MailMsgs.OPENENTRY_ID, userSessionBean.userId).replaceAll(MailMsgs.PASSWORD, userSessionBean.password);
			new MailerThread(mailObj).sendMail();
		}catch(Exception e){
			e.printStackTrace();
		}
		return XMLFactory.getMessageElement("userid", userSessionBean.userId)+XMLFactory.getMessageElement("pwd",userSessionBean.password );
	}
	
	public static String invalidateNMUserAccount(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		NMCreator.removeNM(conn, userSessionBean);
		return XMLFactory.getMessageElement("marketid", userSessionBean.userId);
	}
	
	public static String invalidateVendorUserAccount(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		VendorCreator.removeVendor(conn, userSessionBean);
		return XMLFactory.getMessageElement("companycode", userSessionBean.userId);
	}
	
	public static String importCategories(Connection conn) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException{
		FlagFactory.setCategoryImportFlag(conn);
		CategoryHelper.saveTxtCategory(conn);
		FlagFactory.clearCategoryImportFlag(conn);
		return "";
	}
	
	public static String upgradeToSuperNM(Connection conn, String marketId) throws SQLException{
		MasterMarketFactory.upgradeToSuperNM(conn, marketId);
		return XMLFactory.getMessageElement("marketId", marketId) + XMLFactory.getMessageElement("marketName", NetMarketFactory.getNetworkMarketByCode(conn, marketId).Name);
	}
	
	public static String degradeFromSuperNM(Connection conn, String marketId) throws SQLException{
		MasterMarketFactory.degradeFromSuperNM(conn, marketId);
		SuperNMFactory.removeAllSubNmFromSuperNM(conn, marketId);
		return XMLFactory.getMessageElement("marketId", marketId);
	}
	
	public static String updateDomainName(Connection conn, String marketId, String domainName) throws SQLException{
		MasterMarketFactory.updateDomainName(conn, marketId, domainName);
		return XMLFactory.getMessageElement("marketid", marketId);
	}
	
	public static String fetchCharges(Connection conn, String marketId) throws SQLException{
		Charge charge = ChargeFactory.getCharge(conn, marketId);
		String res = "";
		res += XMLFactory.getMessageElement("marketid", charge.marketId);
		res += XMLFactory.getMessageElement("add", String.valueOf(charge.chargeToAddVendor));
		res += XMLFactory.getMessageElement("feature", String.valueOf(charge.chargeToFeatureVendor));
		res += XMLFactory.getMessageElement("default", String.valueOf(charge.isDefault));
		return res;
	}
	
	public static String updateCharges(Connection conn, String marketId, double addCharge, double featureCharge) throws SQLException{
		Charge charge = new Charge();
		charge.marketId = marketId;
		charge.chargeToAddVendor = addCharge;
		charge.chargeToFeatureVendor = featureCharge;
		ChargeFactory.update(conn, charge);
		return "";
	}
	
	public static String defaultCharges(Connection conn, String marketId) throws SQLException{
		ChargeFactory.updateToDefault(conn, marketId);
		return fetchCharges(conn, marketId);
	}
	
	public static String fetchFunds(Connection conn, String userId) throws SQLException{
		String res = "";
		res += XMLFactory.getMessageElement("marketid", userId);
		res += XMLFactory.getMessageElement("regular", String.valueOf(FundBankHelper.getTotalRegularFund(conn, userId)));
		res += XMLFactory.getMessageElement("outreach", String.valueOf(FundBankHelper.getTotalOutreachFund(conn, userId)));
		return res;
	}
	
	public static String addFund(Connection conn, String marketId, double fundAmount) throws SQLException{
		FundBank fundBank = new FundBank();
		fundBank.userId = marketId;
		fundBank.amount = fundAmount;
		fundBank.note="From SA Console to marketId "+marketId;
		FundBankFactory.performOutreachFundDeposit(conn, fundBank);
		return fetchFunds(conn, marketId);
	}
	
	public static String changePremiumStatus(Connection conn, String marketId) throws SQLException{
		NetMarketFactory.changePremiumStatus(conn, marketId);
		String res = XMLFactory.getMessageElement("marketId", marketId);
		res += XMLFactory.getMessageElement("premium", NetMarketFactory.getNetworkMarketByCode(conn, marketId).premium?"1":"0");
		return res;
	}
}
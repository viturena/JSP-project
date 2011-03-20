package com.catgen.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import com.catgen.Category;
import com.catgen.Constants;
import com.catgen.FundBank;
import com.catgen.NetworkMarket;
import com.catgen.UserSessionBean;
import com.catgen.Utils;
import com.catgen.exception.DuplicateCatalogCodeException;
import com.catgen.exception.InvalidHashException;
import com.catgen.exception.MailNotSentException;
import com.catgen.exception.NMMSClosedForHashException;
import com.catgen.exception.SpreadsheetNotPublishedException;
import com.catgen.exception.VendorCredentialInvalidOrExpiredException;
import com.catgen.factories.CategoryFactory;
import com.catgen.factories.ChargeFactory;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.FlagFactory;
import com.catgen.factories.FundBankFactory;
import com.catgen.factories.HashFactory;
import com.catgen.factories.LoggerFactory;
import com.catgen.factories.MasterCompanyFactory;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.ReferralBillingFactory;
import com.catgen.factories.SchemeFactory;
import com.catgen.factories.UserSessionFactory;
import com.catgen.factories.XMLFactory;

public class GAERequestController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, MailNotSentException, DuplicateCatalogCodeException, VendorCredentialInvalidOrExpiredException, SpreadsheetNotPublishedException, NMMSClosedForHashException, InvalidHashException {
		String res="";
		String strSubModule = request.getParameter("submodule");
		String companyCode = request.getParameter("companyCode");
		String marketId = request.getParameter("marketId");
		String sskey = request.getParameter("sskey");
		String strStartDate = request.getParameter("strStartDate");
		String strEndDate = request.getParameter("strEndDate");
		String password = request.getParameter("password");
		String amount = request.getParameter("amount");
		String hash = request.getParameter("hash");
		Date startDate = Utils.getDatefromString(null, strStartDate);
		Date endDate = Utils.getDatefromString(null, strEndDate);
		String companySheet = request.getParameter("companysheet");
		String productSheet = request.getParameter("productsheet");
		
//		Calendar cal = Calendar.getInstance();
//		cal.setTime (endDate);
//		cal.add (Calendar.DATE, 1);
//		endDate = cal.getTime();
		
		int subModule = 0;
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
//		float creditAmount = 0.0f;
//		try{
//			creditAmount = Float.parseFloat(amount);
//		}catch(Exception e){
//		}
		
		double amt = 0.0;
		try{
			amt = Float.parseFloat(amount);
		}catch(Exception e){
		}
		
		switch(subModule){
		case Constants.AUTO_AMS_REGISTRATION:
			res = registerAmsAccountForCatalog(conn,companyCode,sskey,companySheet,productSheet,endDate);
			break;
		case Constants.GAE_CATALOG_UPDATE:
			validateVendor(conn, companyCode, password);
			res = updateCatalog(conn, companyCode,companySheet,productSheet);
			break;
		case Constants.GAE_FETCH_ALL_NM_LIST:
			res = getAllNMListXML(conn);
			break;
		case Constants.GAE_FETCH_ASSOCIATED_NM_LIST:
			validateVendor(conn, companyCode, password);
			res = getAssociatedNMListXML(conn, companyCode);
			break;
		case Constants.GAE_REMOVE_ASSOCIATION:
			validateVendor(conn, companyCode, password);
			res = removeAssociation(conn, marketId, companyCode);
			break;
		case Constants.GAE_REQUEST_ASSOCIATION:
			validateVendor(conn, companyCode, password);
			res = requestAssociation(conn, marketId, companyCode);
			break;
		case Constants.GAE_FETCH_REFERRALS:
			validateVendor(conn, companyCode, password);
			res = fetchReferrals(conn, companyCode, startDate, endDate);
			break;
//		case Constants.CREDIT_PURCHASE:
//			validateVendor(conn, companyCode, password);
//			validateHash(conn, hash);
//			res = purchaseCredit(conn, companyCode, creditAmount);
//			res += XML\Factory.getMessageElement("hash", hash);
//			break;
//		case Constants.GET_CREDIT_BALANCE:
//			validateVendor(conn, companyCode, password);
//			res = getCreditBalance(conn, companyCode);
//			break;
//		case Constants.GET_CREDIT_PURCHASE_HISTORY:
//			validateVendor(conn, companyCode, password);
//			res = ReferralsController.getCreditPurchaseHistory(conn, companyCode, startDate, endDate);
//			break;
		case Constants.GENERATE_HASH_FOR_GAE:
			res = generateHashForGae(conn);
			break;
		case Constants.GAE_DISABLE_HASH_FLAG:
			res = disableHashFlag(conn);
			break;
//		case Constants.BILLING_SUMMARY_REPORT:
//			validateVendor(conn, companyCode, password);
//			res = ReferralsController.getReferralPaymentSummary(conn,companyCode,Constants.VENDOR,startDate,endDate);
//			break;
//		case Constants.DETAILED_BILLING_REPORT:
//			validateVendor(conn, companyCode, password);
//			res = ReferralsController.getReferralPaymentDetail(conn,companyCode,Constants.VENDOR,startDate,endDate);
//			break;
		case Constants.CATEGORIES_FOR_GAE:
			res = fetchNMCategories(conn,companyCode);
			break;
//		case Constants.ADD_VENDOR_AFTER_PAYMENT:
//			validateVendor(conn, companyCode, password);
//			validateHash(conn, hash);
			//LoggerFactory.write(conn, "ADD::MARKET:"+marketId+"::COMP:"+companyCode+"AMT:"+amt+"::HASH:"+hash);
			//res = addVendorPostPayment(conn, marketId, companyCode, amt);
//			break;
		case Constants.FEATURE_VENDOR_AFTER_PAYMENT:
			validateVendor(conn, companyCode, password);
			validateHash(conn, hash);
			LoggerFactory.write(conn, "FEATURE::MARKET:"+marketId+"::COMP:"+companyCode+"AMT:"+amt+"::HASH:"+hash);
			res = featureVendorPostPayment(conn, marketId, companyCode, amt);
			break;
		case Constants.CATALOG_FREE_OR_PREMIUM:
			validateVendor(conn, companyCode, password);
			validateHash(conn, hash);
			res = updatePremiumTillDate(conn, companyCode, endDate);
			break;
		}	
		return res;
	}
	
	public static void validateVendor(Connection conn, String companyCode, String password) throws SQLException, VendorCredentialInvalidOrExpiredException{
		UserSessionBean userSessionBean = new UserSessionBean();
		userSessionBean.userId = companyCode;
		userSessionBean.password = password;
		userSessionBean.type = Constants.VENDOR;
		
		boolean activeStatus = UserSessionFactory.isAccountActive(conn, userSessionBean) && UserSessionFactory.hasValidPassword(conn, userSessionBean);
		if(!activeStatus){
			throw new VendorCredentialInvalidOrExpiredException();
		}
	}
	
	public static void validateHash(Connection conn, String hash) throws SQLException, InvalidHashException{
		HashFactory.validateAndRemoveHash(conn, hash);
	}
	
	public static String registerAmsAccountForCatalog(Connection conn, String companyCode, String sskey, String companySheet, String productSheet, Date endDate) throws SQLException, MailNotSentException, DuplicateCatalogCodeException, SpreadsheetNotPublishedException{
		String ssurl = Constants.GOOGLE_SPREADSHEET_DATAFEED_FORMAT.replaceAll(Constants.KEY_VARIABLE, sskey).replaceAll(Constants.SEQUENCE_VARIABLE, companySheet);
		if(Utils.fileExists(ssurl)){
			MasterCompanyFactory.save(conn, companyCode, sskey, companySheet, productSheet, endDate);
			CompanyFactory.UpdateCompany(conn, companyCode);
	
			UserSessionBean userSessionBean = new UserSessionBean();
			userSessionBean.userId = companyCode;
			userSessionBean.password = Utils.generateRandomString(8);
			userSessionBean.type = Constants.VENDOR;
			userSessionBean.validated = true;
			userSessionBean.endDate = Utils.getDateAfterGivenMonths(SchemeFactory.getNumberofMonthsByScheme(conn, Constants.PLATINUM_SCHEME));
			UserSessionFactory.save(conn, userSessionBean);
	
			return XMLFactory.getMessageElement("message", "Catalog Registered to Network Market Application")+XMLFactory.getMessageElement("pass", userSessionBean.password);
		}else{
			throw new SpreadsheetNotPublishedException();
		}
	}
	
	public static String updateCatalog(Connection conn, String companyCode, String companySheet, String productSheet) throws SQLException, MailNotSentException{
		String sskey = MasterCompanyFactory.getSSkeyByCompanyCode(conn, companyCode);
		Date endDate = MasterCompanyFactory.getMasterCompanyByCode(conn, companyCode).premiumTill;
		
		MasterCompanyFactory.removeCompany(conn, companyCode);
		try {
			MasterCompanyFactory.save(conn, companyCode, sskey, companySheet, productSheet, endDate);
		} catch (DuplicateCatalogCodeException e) {
		}
		CompanyFactory.UpdateCompany(conn, companyCode);
		return "";
	}
	
	public static String getAllNMListXML(Connection conn) throws SQLException{
		List<NetworkMarket> networkMarkets = NetMarketFactory.getNetmarketMembers(conn);
		int count = networkMarkets.size();
		String res = XMLFactory.getMessageElement("count", String.valueOf(count));
		int i=1;
		if(networkMarkets!=null){
			for(NetworkMarket networkMarket: networkMarkets){
				String temp = "";
				temp = XMLFactory.getMessageElement("code_"+i, networkMarket.NetworkMarketID.replaceAll("&", "&amp;").trim());
				temp+= XMLFactory.getMessageElement("name_"+i, networkMarket.Name.replaceAll("&", "&amp;").trim());
				temp+= XMLFactory.getMessageElement("add_"+i, String.valueOf(ChargeFactory.getCharge(conn, networkMarket.NetworkMarketID).chargeToAddVendor));
				temp+= XMLFactory.getMessageElement("feature_"+i, String.valueOf(ChargeFactory.getCharge(conn, networkMarket.NetworkMarketID).chargeToFeatureVendor));
				res += XMLFactory.getMessageElement("nm_"+i, temp);
				i++;
			}
		}
		res = XMLFactory.getMessageElement("nm_list", res);
		return res;
	}
	
	public static String getAssociatedNMListXML(Connection conn, String companyCode) throws SQLException{
		List<String> networkMarkets = NetMarketFactory.getAssociatedNetworkMarketsIdList(conn, companyCode);
		int count = networkMarkets.size();
		String res = XMLFactory.getMessageElement("count", String.valueOf(count));
		int i=1;
		if(networkMarkets!=null){
			for(String marketId: networkMarkets){
				String temp = "";
				NetworkMarket networkMarket = NetMarketFactory.getNetworkMarketByCode(conn, marketId);
				boolean approved = CompanyFactory.isCompanyApprovedByNM(conn, marketId, companyCode);
				Date acceptedTill = CompanyFactory.companyAcceptedInNMTill(conn, marketId, companyCode);
				Date featuredTill = CompanyFactory.companyFeaturedInNMTill(conn, marketId, companyCode);
				temp = XMLFactory.getMessageElement("code_"+i, networkMarket.NetworkMarketID.replaceAll("&", "&amp;").trim());
				temp+= XMLFactory.getMessageElement("name_"+i, networkMarket.Name.replaceAll("&", "&amp;").trim());
				temp+= XMLFactory.getMessageElement("approved_"+i, (approved)?"1":"0");
				temp+= XMLFactory.getMessageElement("acceptedtill_"+i, Utils.returnFormattedDateString(Constants.STANDARD_DATE_FORMAT, acceptedTill));
				temp+= XMLFactory.getMessageElement("accepted_"+i, (Utils.checkForValidity(acceptedTill))?"1":"0");
				temp+= XMLFactory.getMessageElement("featuredtill_"+i, Utils.returnFormattedDateString(Constants.STANDARD_DATE_FORMAT, featuredTill));
				temp+= XMLFactory.getMessageElement("featured_"+i, (Utils.checkForValidity(featuredTill))?"1":"0");
				res += XMLFactory.getMessageElement("nm_"+i, temp);
				i++;
			}
		}
		res = XMLFactory.getMessageElement("nm_list", res);
		return res;
	}
	
	public static String removeAssociation(Connection conn, String marketId, String companyCode) throws SQLException{
		CompanyFactory.removeAssociation(conn, marketId, companyCode);
		return "";
	}
	
	public static String requestAssociation(Connection conn, String marketId, String companyCode) throws SQLException{
		CompanyFactory.requestAssociation(conn, marketId, companyCode);
		return "";
	}

	public static String fetchReferrals(Connection conn, String companyCode, Date startDate, Date endDate) throws SQLException{
		return ReferralsController.fetchReferrals(conn, companyCode, Constants.VENDOR, startDate, endDate);
	}

	public static String purchaseCredit(Connection conn, String companyCode, float creditAmount) throws SQLException{
		ReferralBillingFactory.purchaseCredit(conn, companyCode, creditAmount, "Purchase through GAE");
		return XMLFactory.getMessageElement("amount", String.valueOf(creditAmount));
	}

	public static String getCreditBalance(Connection conn, String companyCode) throws SQLException{
		return XMLFactory.getMessageElement("balance", String.valueOf(ReferralBillingFactory.getCreditBalance(conn, companyCode)));
	}
	
	public static String generateHashForGae(Connection conn) throws SQLException, NMMSClosedForHashException{
		if(FlagFactory.getFlags(conn).gaeHash){
			String hash = UUID.randomUUID().toString();
			HashFactory.saveHash(conn, hash);
			return XMLFactory.getMessageElement("hash", hash);
		}else{
			throw new NMMSClosedForHashException();
		}
	}
	
	public static String disableHashFlag(Connection conn) throws SQLException{
		FlagFactory.clearGaeHashFlag(conn);
		return "";
	}
	
	public static String fetchNMCategories(Connection conn, String companyCode) throws SQLException{
		List<Category> categories = CategoryFactory.getAssociatedNMCategoriesByCompanyCode(conn, companyCode);
		String res="";
		int i=1;
		if(categories!=null && categories.size()>0){
			for(Category category: categories){
				if(category.categoryKey!=null && category.categoryKey.length()>0){
					res+=XMLFactory.getMessageElement("category_"+i,StringEscapeUtils.escapeXml(category.categoryValue))+ XMLFactory.getMessageElement("key_"+i, category.categoryKey);
				}
				i++;
			}
			res=XMLFactory.getMessageElement("categories", res);
			res+=XMLFactory.getMessageElement("count", String.valueOf(categories.size()));
		}
		return res;
	}
	
	public static String addVendorPostPayment(Connection conn, String marketId, String companyCode, double amt) throws SQLException{
		CompanyFactory.addCompany(conn, marketId, companyCode);
		return "";
	}
	
	public static String featureVendorPostPayment(Connection conn, String marketId, String companyCode, double amt) throws SQLException{
		CompanyFactory.featureVendor(conn, marketId, companyCode);
		FundBank fundBank = new FundBank();
		fundBank.amount = amt;
		fundBank.note = "VENDOR FEATURE::COMPANYCODE:"+companyCode;
		FundBankFactory.performCompleteFundDeposit(conn, fundBank);
		return "";
	}
	
	public static String updatePremiumTillDate(Connection conn, String companyCode, Date endDate) throws SQLException{
		MasterCompanyFactory.updatePremiumTillDate(conn, companyCode, endDate);
		return "";
	}
}

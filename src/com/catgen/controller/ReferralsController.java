package com.catgen.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catgen.Constants;
import com.catgen.CreditPurchase;
import com.catgen.Referral;
import com.catgen.ReferralPayment;
import com.catgen.ReferralPaymentSummary;
import com.catgen.ReferralSummary;
import com.catgen.Utils;
import com.catgen.factories.ReferralBillingFactory;
import com.catgen.factories.XMLFactory;
import com.catgen.helper.ReferralsHelper;

public class ReferralsController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		String res = "";
		String strSubModule = request.getParameter("submodule");
		String userId = request.getParameter("userid");
		String sType = request.getParameter("type");
		String strStartDate = request.getParameter("startdate");
		String strEendDate = request.getParameter("enddate");
		
		int subModule = 0, type=0;
		
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}

		try{
			type = Integer.parseInt(sType);
		}catch(Exception e){
		}
		
		Date startDate = Utils.getDatefromString(null, strStartDate);
		Date endDate = Utils.getDatefromString(null, strEendDate);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime (endDate);
		cal.add (Calendar.DATE, 1);
		endDate = cal.getTime();
		
		switch(subModule){
		case Constants.FETCH_REFERRALS:
			res = fetchReferrals(conn, userId, type, startDate, endDate);
			break;
		case Constants.REFERRALS_SUMMARY:
			res = fetchReferralsSummary(conn, userId, type, startDate, endDate);
			break;
//		case Constants.GET_REPORT_LIST:
//			res = getReportList(conn,type);
//			break;
//		case Constants.CREDIT_BALANCE_REPORT:
//			res = getCreditBalance(conn, userId);
//			break;
//		case Constants.CREDIT_PURCHASE_REPORT:
//			res = getCreditPurchaseHistory(conn,userId,startDate,endDate);
//			break;
//		case Constants.BILLING_SUMMARY_REPORT:
//			res = getReferralPaymentSummary(conn,userId,type,startDate,endDate);
//			break;
//		case Constants.DETAILED_BILLING_REPORT:
//			res = getReferralPaymentDetail(conn,userId,type,startDate,endDate);
//			break;
		}
		
		return res;
	}
	
	public static String fetchReferrals(Connection conn, String userId, int type, Date startDate, Date endDate) throws SQLException{
		String res="";
		List<Referral> referrals = new ArrayList<Referral>();
		referrals = ReferralsHelper.getReferralsByType(conn, userId, startDate, endDate, type);
		int ctr=1;
		String mainRef="", refItem="";
		for(Referral referral: referrals){
			refItem = "";
			refItem += XMLFactory.getMessageElement("timestamp_"+ctr, new SimpleDateFormat().format(referral.ReferralDate));
			refItem += XMLFactory.getMessageElement("email_"+ctr,referral.Email);
			refItem += XMLFactory.getMessageElement("marketId_"+ctr, referral.NetworkMarketID);
			refItem += XMLFactory.getMessageElement("companycode_"+ctr, referral.CompanyCode);
			refItem += XMLFactory.getMessageElement("clientip_"+ctr, referral.ClientIP);
			refItem += XMLFactory.getMessageElement("referralevent_"+ctr, referral.ReferralEvent);
			refItem += XMLFactory.getMessageElement("extradata_"+ctr, referral.ExtraData);
			refItem += XMLFactory.getMessageElement("level_"+ctr, String.valueOf(referral.Level));
			refItem += XMLFactory.getMessageElement("city_"+ctr, referral.City);
			refItem += XMLFactory.getMessageElement("country_"+ctr, referral.Country);
			refItem += XMLFactory.getMessageElement("bot_"+ctr, (referral.Bot)?"true":"false");
			mainRef+= XMLFactory.getMessageElement("referral_"+ctr, refItem);
			ctr++;
		}
		res = XMLFactory.getMessageElement("count", String.valueOf(referrals.size())) + XMLFactory.getMessageElement("userid", userId) + XMLFactory.getMessageElement("startdate", new SimpleDateFormat("dd/MM/yyyy").format(startDate)) + XMLFactory.getMessageElement("enddate", new SimpleDateFormat("dd/MM/yyyy").format(endDate)) + XMLFactory.getMessageElement("data", mainRef);
		res = XMLFactory.getMessageElement("referrals", res);
		return res;
	}
	
	public static String fetchReferralsSummary(Connection conn, String userId, int type, Date startDate, Date endDate) throws SQLException{
		String res="";
		HashMap<String, ReferralSummary> summaries = new HashMap<String, ReferralSummary>();
		List<Referral> referrals = new ArrayList<Referral>();
		referrals = ReferralsHelper.getReferralsByType(conn, userId, startDate, endDate, type);
		
		for(Referral referral: referrals){
			if(summaries.get(referral.CompanyCode)==null){
				ReferralSummary summary = new ReferralSummary();
				summary.companyCode = referral.CompanyCode;
				if(referral.Bot){
					summary.botVisitCount++;
				}else{
					summary.nonBotVisitCount++;
				}
				summaries.put(referral.CompanyCode, summary);
			}else{
				if(referral.Bot){
					summaries.get(referral.CompanyCode).botVisitCount++;
				}else{
					summaries.get(referral.CompanyCode).nonBotVisitCount++;
				}
			}
		}
		
		int ctr=1;
		String summ="";
		for (ReferralSummary referralSummary : summaries.values()) {
			summ = XMLFactory.getMessageElement("company_"+ctr, referralSummary.companyCode);
			summ += XMLFactory.getMessageElement("nonbot_"+ctr, String.valueOf(referralSummary.nonBotVisitCount));
			summ += XMLFactory.getMessageElement("bot_"+ctr, String.valueOf(referralSummary.botVisitCount));
			summ = XMLFactory.getMessageElement("summary_"+ctr, summ);
			res+=summ;
			ctr++;
		}
		res = XMLFactory.getMessageElement("count", String.valueOf(summaries.size())) + XMLFactory.getMessageElement("userid", userId) + XMLFactory.getMessageElement("startdate", new SimpleDateFormat("dd/MM/yyyy").format(startDate)) + XMLFactory.getMessageElement("enddate", new SimpleDateFormat("dd/MM/yyyy").format(endDate)) + XMLFactory.getMessageElement("data", res);
		return res;
	}

	public static String getCreditBalance(Connection conn, String companyCode) throws SQLException{
		return XMLFactory.getMessageElement("balance", String.valueOf(ReferralBillingFactory.getCreditBalance(conn, companyCode)));
	}

	public static String getReportList(Connection conn, int type) throws SQLException{
		String res="";
		res+=XMLFactory.getMessageElement("name_1", "Summary Billing Report");
		res+=XMLFactory.getMessageElement("value_1", String.valueOf(Constants.BILLING_SUMMARY_REPORT));
		res+=XMLFactory.getMessageElement("name_2", "Detailed Billing Report");
		res+=XMLFactory.getMessageElement("value_2", String.valueOf(Constants.DETAILED_BILLING_REPORT));

		if(Constants.VENDOR==type){
			res+=XMLFactory.getMessageElement("name_3", "Credit Balance Report");
			res+=XMLFactory.getMessageElement("value_3", String.valueOf(Constants.CREDIT_BALANCE_REPORT));
			res+=XMLFactory.getMessageElement("name_4", "Credit Purchase Report");
			res+=XMLFactory.getMessageElement("value_4", String.valueOf(Constants.CREDIT_PURCHASE_REPORT));
			res+=XMLFactory.getMessageElement("count", "4");
		}else{
			res+=XMLFactory.getMessageElement("count", "2");
			
		}
		return res;
	}
	
	public static String getCreditPurchaseHistory(Connection conn, String companyCode, Date startDate, Date endDate) throws SQLException{
		List<CreditPurchase> creditPurchaseHistory = ReferralBillingFactory.getCreditPurchaseHistory(conn, companyCode, startDate, endDate);
		int count = creditPurchaseHistory.size();
		String res = XMLFactory.getMessageElement("count", String.valueOf(count));
		res += XMLFactory.getMessageElement("companyCode", companyCode);
		int i=1;
		float net=0.00f;
		if(creditPurchaseHistory!=null){
			for(CreditPurchase creditPurchase: creditPurchaseHistory){
				String temp = "";
				float amt = creditPurchase.amount;
				net += amt;
				temp = XMLFactory.getMessageElement("date_"+i, new SimpleDateFormat().format(creditPurchase.purchaseDate));
				temp+= XMLFactory.getMessageElement("amount_"+i, String.valueOf(amt));
				temp+= XMLFactory.getMessageElement("note_"+i, creditPurchase.note.replaceAll("&", "&amp;").trim());
				res += XMLFactory.getMessageElement("history_"+i, temp);
				i++;
			}
		}
		res += XMLFactory.getMessageElement("net", String.valueOf(net));
		res = XMLFactory.getMessageElement("credit-history-list", res);
		return res;
	}
	
	public static String getReferralPaymentDetail(Connection conn, String userId, int type, Date startDate, Date endDate) throws SQLException{
		List<ReferralPayment> referralPayments;
		if(Constants.VENDOR==type){
			referralPayments = ReferralBillingFactory.getReferralPaymentMade(conn, userId, startDate, endDate);
		}else{
			referralPayments = ReferralBillingFactory.getReferralPaymentReceived(conn, userId, startDate, endDate);
		}
		int count = referralPayments.size();
		String res = XMLFactory.getMessageElement("count", String.valueOf(count));
		int i=1;
		float net = 0.00f;
		if(referralPayments!=null){
			for(ReferralPayment referralPayment: referralPayments){
				String temp = "";
				float amt = referralPayment.amount;
				net+=amt;
				temp = XMLFactory.getMessageElement("date_"+i, new SimpleDateFormat().format(referralPayment.referralDate));
				temp+= XMLFactory.getMessageElement("id_"+i, referralPayment.paymentId);
				temp+= XMLFactory.getMessageElement("payerid_"+i, referralPayment.payerId);
				temp+= XMLFactory.getMessageElement("payeeid_"+i, referralPayment.payeeId);
				temp+= XMLFactory.getMessageElement("amount_"+i, String.valueOf(amt));
				res += XMLFactory.getMessageElement("payment_"+i, temp);
				i++;
			}
		}
		res += XMLFactory.getMessageElement("net", String.valueOf(net));
		res = XMLFactory.getMessageElement("referral-payment-list", res);
		return res;
	}
	
	public static String getReferralPaymentSummary(Connection conn, String userId, int type, Date startDate, Date endDate) throws SQLException{
		List<ReferralPaymentSummary> referralPaymentSummaries;
		if(Constants.VENDOR==type){
			referralPaymentSummaries = ReferralBillingFactory.getReferralPaymentMadeSummary(conn, userId, startDate, endDate);
		}else{
			referralPaymentSummaries = ReferralBillingFactory.getReferralPaymentReceivedSummary(conn, userId, startDate, endDate);
		}
		int count = referralPaymentSummaries.size();
		String res = XMLFactory.getMessageElement("count", String.valueOf(count));
		int i=1;
		float net=0.00f;
		if(referralPaymentSummaries!=null){
			for(ReferralPaymentSummary referralPaymentSummary: referralPaymentSummaries){
				String temp = "";
				float amt = referralPaymentSummary.total;
				net+=amt;
				temp = XMLFactory.getMessageElement("date_"+i, new SimpleDateFormat("dd/MM/yyyy").format(referralPaymentSummary.referralDate));
				temp+= XMLFactory.getMessageElement("no_"+i, String.valueOf(referralPaymentSummary.referralCount));
				temp+= XMLFactory.getMessageElement("amount_"+i, String.valueOf(amt));
				res += XMLFactory.getMessageElement("payment_"+i, temp);
				i++;
			}
		}
		res += XMLFactory.getMessageElement("net", String.valueOf(net));
		res = XMLFactory.getMessageElement("referral-payment-list", res);
		return res;
	}
}

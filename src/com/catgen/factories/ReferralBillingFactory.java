package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.catgen.Constants;
import com.catgen.CreditPurchase;
import com.catgen.ReferralPayment;
import com.catgen.ReferralPaymentSummary;

public class ReferralBillingFactory {
	
	public static float getCreditBalance(Connection conn, String userId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select amount from Credit where userId=?");
		try{
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getFloat("amount");
			}
			return 0.0f;
		}finally{
			pstmt.close();
		}
	}
	
	public static List<CreditPurchase> getCreditPurchaseHistory(Connection conn, String userId, Date startDate, Date endDate) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from CreditPurchase where userId=? and PurchaseDate>=? and PurchaseDate<=?");
		List<CreditPurchase> creditPurchaseHistory = new ArrayList<CreditPurchase>();
		try{
			pstmt.setString(1, userId);
			pstmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
			pstmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				CreditPurchase creditPurchase = new CreditPurchase();
				populateCreditPurchaseFromResultSet(rs,creditPurchase);
				creditPurchaseHistory.add(creditPurchase);
			}
			return creditPurchaseHistory;
		}finally{
			pstmt.close();
		}
	}
	
	public static List<ReferralPayment> getReferralPaymentMade(Connection conn, String userId, Date startDate, Date endDate) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select ReferralDate, PaymentId, sum(amount) from ReferralPayment where payerId=? and ReferralDate>=? and ReferralDate<=? group by ReferralDate, PaymentId order by ReferralDate asc");
		List<ReferralPayment> referralPayments = new ArrayList<ReferralPayment>();
		try{
			pstmt.setString(1, userId);
			pstmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
			pstmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				ReferralPayment referralPayment = new ReferralPayment();
				
				java.sql.Timestamp t = rs.getTimestamp("ReferralDate");
				if(t != null)
					referralPayment.referralDate = new java.util.Date(t.getTime());
				
				referralPayment.paymentId = rs.getString("PaymentId");
				referralPayment.amount = rs.getFloat(3);
				referralPayment.payerId = userId;
				referralPayment.payeeId = getPayeeIdByReferralPaymentId(conn, referralPayment.paymentId);
				
				referralPayments.add(referralPayment);
			}
			return referralPayments;
		}finally{
			pstmt.close();
		}
	}
	
	public static List<ReferralPayment> getReferralPaymentReceived(Connection conn, String userId, Date startDate, Date endDate) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select ReferralDate, PaymentId, PayerId, amount from ReferralPayment where payeeId=? and ReferralDate>=? and ReferralDate<=? group by ReferralDate, PaymentId order by ReferralDate asc");
		List<ReferralPayment> referralPayments = new ArrayList<ReferralPayment>();
		try{
			pstmt.setString(1, userId);
			pstmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
			pstmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				ReferralPayment referralPayment = new ReferralPayment();
				
				java.sql.Timestamp t = rs.getTimestamp("ReferralDate");
				if(t != null)
					referralPayment.referralDate = new java.util.Date(t.getTime());
				
				referralPayment.paymentId = rs.getString("PaymentId");
				referralPayment.payerId = rs.getString(3);
				referralPayment.payeeId = userId;
				referralPayment.amount = rs.getFloat(4);
				
				referralPayments.add(referralPayment);
			}
			return referralPayments;
		}finally{
			pstmt.close();
		}
	}
	
	public static List<ReferralPaymentSummary> getReferralPaymentMadeSummary(Connection conn, String userId, Date startDate, Date endDate) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select date(ReferralDate), count(distinct PaymentId), sum(amount) from ReferralPayment where payerId=? and ReferralDate>=? and ReferralDate<=? group by date(ReferralDate) order by 1 asc");
		List<ReferralPaymentSummary> referralPaymentSummaries = new ArrayList<ReferralPaymentSummary>();
		try{
			pstmt.setString(1, userId);
			pstmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
			pstmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				ReferralPaymentSummary referralPaymentSummary = new ReferralPaymentSummary();
				referralPaymentSummary.referralDate = rs.getDate(1);
				referralPaymentSummary.referralCount = rs.getInt(2);
				referralPaymentSummary.total = rs.getFloat(3);
				
				referralPaymentSummaries.add(referralPaymentSummary);
			}
			return referralPaymentSummaries;
		}finally{
			pstmt.close();
		}
	}
	
	public static List<ReferralPaymentSummary> getReferralPaymentReceivedSummary(Connection conn, String userId, Date startDate, Date endDate) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select date(ReferralDate), count(distinct PaymentId), sum(amount) from ReferralPayment where payeeId=? and ReferralDate>=? and ReferralDate<=? group by date(ReferralDate) order by 1 asc");
		List<ReferralPaymentSummary> referralPaymentSummaries = new ArrayList<ReferralPaymentSummary>();
		try{
			pstmt.setString(1, userId);
			pstmt.setTimestamp(2, new java.sql.Timestamp(startDate.getTime()));
			pstmt.setTimestamp(3, new java.sql.Timestamp(endDate.getTime()));
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				ReferralPaymentSummary referralPaymentSummary = new ReferralPaymentSummary();
				referralPaymentSummary.referralDate = rs.getDate(1);
				referralPaymentSummary.referralCount = rs.getInt(2);
				referralPaymentSummary.total = rs.getFloat(3);
				
				referralPaymentSummaries.add(referralPaymentSummary);
			}
			return referralPaymentSummaries;
		}finally{
			pstmt.close();
		}
	}
	
	public static void purchaseCredit(Connection conn, String userId, float creditAmount, String note) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update Credit set Amount = Amount+? where UserId=?");
		try{
			pstmt.setFloat(1, creditAmount);
			pstmt.setString(2, userId);
			int rows = pstmt.executeUpdate();
			if(rows==0){
				openCreditAccount(conn, userId, creditAmount);
			}
			logCreditPurchase(conn,userId,creditAmount,note);
		}finally{
			pstmt.close();
		}
	}
	
	public static void openCreditAccount(Connection conn, String userId, float creditAmount) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into Credit(userid,amount) values(?,?)");
		try{
			pstmt.setString(1, userId);
			pstmt.setFloat(2, creditAmount);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void logCreditPurchase(Connection conn, String userId, float creditAmount, String note) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into CreditPurchase(userid,amount,note) values(?,?,?)");
		try{
			pstmt.setString(1, userId);
			pstmt.setFloat(2, creditAmount);
			pstmt.setString(3, note);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void chargeVendorForReferral(Connection conn, String userId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update Credit set Amount = Amount-? where UserId=?");
		try{
			pstmt.setFloat(1, Constants.CHARGE_PER_REFERRAL);
			pstmt.setString(2, userId);
			int rows = pstmt.executeUpdate();
			if(rows==0){
				openCreditAccount(conn, userId, -Constants.CHARGE_PER_REFERRAL);
			}
		}finally{
			pstmt.close();
		}
	}
	
	public static void payForReferrals(Connection conn, ReferralPayment referralPayment) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into ReferralPayment(PaymentId, Seq, PayerId, PayeeId, Amount) values(?,?,?,?,?)");
		try{
			pstmt.setString(1, referralPayment.paymentId);
			pstmt.setInt(2, referralPayment.seq);
			pstmt.setString(3, referralPayment.payerId);
			pstmt.setString(4, referralPayment.payeeId);
			pstmt.setFloat(5, referralPayment.amount);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
		
	}
	
	public static void populateCreditPurchaseFromResultSet(ResultSet rs, CreditPurchase creditPurchase) throws SQLException{
		java.sql.Timestamp t = rs.getTimestamp("PurchaseDate");
		if(t != null) creditPurchase.purchaseDate = new java.util.Date(t.getTime());
		creditPurchase.userId = rs.getString("userId");
		creditPurchase.amount = rs.getFloat("amount");
		creditPurchase.note = rs.getString("note");
	}
	
	public static String getPayeeIdByReferralPaymentId(Connection conn, String paymentId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select distinct payeeid from ReferralPayment where paymentid=?");
		String payee="";
		try{
			pstmt.setString(1, paymentId.trim());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				payee += rs.getString(1).trim()+" ";
			}
			return payee;
		}finally{
			pstmt.close();
		}
	}
}

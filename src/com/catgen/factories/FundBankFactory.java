package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catgen.Constants;
import com.catgen.FundBank;
import com.catgen.Utils;

public class FundBankFactory {
	public static void performRegularFundDeposit(Connection conn, FundBank fundBank) throws SQLException{
		performFundDeposit(conn,fundBank,Constants.FUND_TYPE_REGULAR);
	}
	
	public static void performOutreachFundDeposit(Connection conn, FundBank fundBank) throws SQLException{
		performFundDeposit(conn,fundBank,Constants.FUND_TYPE_OUTREACH);
	}
	
	public static void performCompleteFundDeposit(Connection conn, FundBank fundBank) throws SQLException{
		performFundDeposit(conn,fundBank,Constants.FUND_TYPE_COMPLETE);
	}
	
	private static void performFundDeposit(Connection conn, FundBank fundBank, int fundType) throws SQLException{
		String hash = Utils.generateRandomString(8);
		fundBank.note = "[#"+hash+"] "+Constants.FUND_TYPE[fundType]+" ::: "+fundBank.note;
		double amount = fundBank.amount;
		if(Constants.FUND_TYPE_COMPLETE==fundType){
			fundBank.userId = Constants.DEFAULT_REFERRER;
			deposit(conn,fundBank,fundType);
		}else{
			fundBank.amount = Utils.getAmountAndCommission(amount, Constants.FUND_FINAL);
			deposit(conn,fundBank,fundType);
			fundBank.userId = Constants.DEFAULT_REFERRER;
			fundBank.amount = Utils.getAmountAndCommission(amount, Constants.FUND_DEDUCTION);
			deposit(conn,fundBank,Constants.FUND_TYPE_REGULAR);
		}
	}
	
	private static void deposit(Connection conn, FundBank fundBank, int fundType) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into FundBank(userid,amount,fundtype,note) values(?,?,?,?)");
		try{
			pstmt.setString(1, fundBank.userId);
			pstmt.setDouble(2, fundBank.amount);
			pstmt.setInt(3, fundType);
			pstmt.setString(4, fundBank.note);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static List<FundBank> getRegularFundAmount(Connection conn, String userId) throws SQLException{
		return getFundBankList(conn,userId,Constants.FUND_TYPE_REGULAR);
	}
	
	public static List<FundBank> getOutreachFundAmount(Connection conn, String userId) throws SQLException{
		return getFundBankList(conn,userId,Constants.FUND_TYPE_OUTREACH);
	}
	
	private static List<FundBank> getFundBankList(Connection conn, String userId, int fundType) throws SQLException{
		List<FundBank> fundBankList = new ArrayList<FundBank>();
		PreparedStatement pstmt = conn.prepareStatement("select * from FundBank where userid=? and fundtype=?");
		try{
			pstmt.setString(1, userId);
			pstmt.setInt(2, fundType);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				FundBank fundBank = new FundBank();
				populateFundBankFromResultSet(rs,fundBank);
				fundBankList.add(fundBank);
			}
			return fundBankList;
		}finally{
			pstmt.close();
		}
	}
	
	public static List<FundBank> getLatestFundTransfers(Connection conn) throws SQLException{
		List<FundBank> fundBankList = new ArrayList<FundBank>();
		PreparedStatement pstmt = conn.prepareStatement("select * from FundBank order by transdate desc limit 0,20");
		try{
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				FundBank fundBank = new FundBank();
				populateFundBankFromResultSet(rs,fundBank);
				fundBankList.add(fundBank);
			}
			return fundBankList;
		}finally{
			pstmt.close();
		}
	}
	
	private static void populateFundBankFromResultSet(ResultSet rs, FundBank fundBank) throws SQLException{
		java.sql.Timestamp t = rs.getTimestamp("transdate");
		if(t != null) fundBank.transDate = new java.util.Date(t.getTime());
		fundBank.userId = rs.getString("userid");
		fundBank.amount = rs.getDouble("amount");
		fundBank.note = rs.getString("note");
	}
}

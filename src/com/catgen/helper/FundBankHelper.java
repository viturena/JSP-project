package com.catgen.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.catgen.Constants;
import com.catgen.FundBank;
import com.catgen.factories.FundBankFactory;

public class FundBankHelper {
	public static double getTotalRegularFund(Connection conn, String userId) throws SQLException{
		return getFundBankAmount(conn, userId, Constants.FUND_TYPE_REGULAR);
	}

	public static double getTotalOutreachFund(Connection conn, String userId) throws SQLException{
		return getFundBankAmount(conn, userId, Constants.FUND_TYPE_OUTREACH);
	}
	
	private static double getFundBankAmount(Connection conn, String userId, int fundType) throws SQLException{
		List<FundBank> funds = null;
		double amount = 0.0;
		if(Constants.FUND_TYPE_REGULAR==fundType){
			funds = FundBankFactory.getRegularFundAmount(conn, userId);
		}else{
			funds = FundBankFactory.getOutreachFundAmount(conn, userId);
		}
		if(null!=funds && funds.size()>0){
			for(FundBank fund: funds){
				amount+=fund.amount;
			}
		}
		return amount;
	}
}

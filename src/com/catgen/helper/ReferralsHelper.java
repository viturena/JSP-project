package com.catgen.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.catgen.Constants;
import com.catgen.Referral;
import com.catgen.factories.ReferralFactory;

public class ReferralsHelper {
	public static List<Referral> getReferralsByType(Connection conn, String userId, Date startDate, Date endDate, int type) throws SQLException{
		List<Referral> referrals = new ArrayList<Referral>();
		switch(type){
		case Constants.NETWORK_MARKET:
			referrals = ReferralFactory.getReferralInfoByMarketID(conn, userId, startDate, endDate);
			break;
		case Constants.REFERRER:
			referrals = ReferralFactory.getReferralInfoByEmail(conn, userId, startDate, endDate);
			break;
		case Constants.VENDOR:
			referrals = ReferralFactory.getReferralInfoByCompany(conn, userId, startDate, endDate);
			break;
		}
		return referrals;
	}
	
	public static boolean isBot(String userAgent){
		boolean bot=true;
		if(userAgent==null || userAgent.length()==0){
			bot=false;
		}else{
			userAgent=userAgent.toLowerCase();
			for(int i=0;i<Constants.HUMAN_KEYWORDS.length;i++){
				if(userAgent.indexOf(Constants.HUMAN_KEYWORDS[i])!=-1){
					bot=false;
					break;
				}
			}
			for(int j=0;j<Constants.NON_HUMAN_KEYWORDS.length;j++){
				if(userAgent.indexOf(Constants.NON_HUMAN_KEYWORDS[j])!=-1){
					bot=true;
					break;
				}
			}
		}
		return bot;
	}
}

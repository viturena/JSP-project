package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

//import com.catgen.Constants;
import com.catgen.Referral;
//import com.catgen.ReferralPayment;
//import com.catgen.Utils;
import com.catgen.helper.ReferralsHelper;

public class ReferralFactory 
{
	private static void LoadReferralFromResultSet(ResultSet rs, Referral referral) throws SQLException
	{
		referral.NetworkMarketID = rs.getString("NetworkMarketID");
		referral.CompanyCode = rs.getString("CompanyCode");

		java.sql.Timestamp t = rs.getTimestamp("ReferralDate");
		
		if(t != null)
			referral.ReferralDate = new java.util.Date(t.getTime());
				
		referral.Email = rs.getString("Email");
		referral.ClientIP = rs.getString("ClientIP");
		referral.ReferralEvent = rs.getString("ReferralEvent");
		referral.ExtraData = rs.getString("ExtraData");
		referral.Level = rs.getInt("Level"); 
		// Referrals Geolocation Changes: Feb 2010 - begin
		referral.City = rs.getString("City");
		referral.Country = rs.getString("Country");
		// Referrals Geolocation Changes: Feb 2010 - end
		referral.UserAgent = rs.getString("UserAgent");
		referral.Bot = rs.getBoolean("bot");
	}
	
	@SuppressWarnings("unchecked")
	public static String getExtraParameters(HttpServletRequest request, String prefix)
	{
		String s = "";

		java.util.Enumeration paramNames = request.getParameterNames();
		while(paramNames.hasMoreElements()) 
		{
			String param = (String)paramNames.nextElement();

			if(param != null && param.startsWith(prefix))
				s = s + param + "=" + request.getParameter(param) + "\n"; 
		}

		return s;
	}
	
	@SuppressWarnings("unchecked")
	public static void SaveReferral(Connection conn, String networkMarketID, String companyCode, String referralID, String referralEventType, String clientIP, String extraData, String userAgent) throws SQLException
	{
		int l = 0;
		// Referrals Geolocation Changes: Feb 2010 - begin
		String city = "";
		String country = "";
		boolean isBot = ReferralsHelper.isBot(userAgent);
		if(null != clientIP){
			Referral referral = new Referral();
			referral.ClientIP = clientIP;
			try{
				referral = populateCityAndCountryInfo(conn, referral);
			}catch(Exception e){
				e.printStackTrace();
			}
			city = referral.City;
			country = referral.Country;
		}
		// Referrals Geolocation Changes: Feb 2010 - end
		List<String> refs = Arrays.asList(referralID.split(","));
		List<String> referrers = new ArrayList(refs);
		//String paymentId = (!isBot)?Utils.generateRandomString(8):null;
		for(String s: referrers)
		{
			String email = s.trim();
			if(email.length() > 0)
			{
				l++;
				if(l > 3)
					break;
				
				// Referrals Geolocation Changes: Feb 2010 - begin
				// PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Referrals(Email, NetworkMarketID, CompanyCode, ClientIP, ReferralEvent, ExtraData, ReferralDate, Level) VALUES(?, ?, ?, ?, ?, ?, ?, ?)");
				PreparedStatement pstmt = conn.prepareStatement("INSERT INTO Referrals(Email, NetworkMarketID, CompanyCode, ClientIP, ReferralEvent, ExtraData, ReferralDate, Level, City, Country, userAgent, bot, paymentId) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				// Referrals Geolocation Changes: Feb 2010 - end
					
				pstmt.setString(1, email);
				pstmt.setString(2, networkMarketID);
				pstmt.setString(3, companyCode);
				pstmt.setString(4, clientIP);
				pstmt.setString(5, referralEventType);
				pstmt.setString(6, extraData);

				pstmt.setTimestamp(7, new java.sql.Timestamp( Calendar.getInstance().getTime().getTime()));
				pstmt.setInt(8, l);
				// Referrals Geolocation Changes: Feb 2010 - begin
				pstmt.setString(9, city);
				pstmt.setString(10, country);
				// Referrals Geolocation Changes: Feb 2010 - end
				pstmt.setString(11, userAgent);
				pstmt.setBoolean(12, isBot);
				//pstmt.setString(13, paymentId);
				pstmt.setString(13, null);

				pstmt.executeUpdate();
			}
		}
		
//		Referral Payment - Begin
//		if(!isBot){
//			if(getTodaysReferralCount(conn, networkMarketID, companyCode, clientIP)<=1){
//				ReferralBillingFactory.chargeVendorForReferral(conn, companyCode);
//				float balance = ReferralBillingFactory.getCreditBalance(conn, companyCode);
//				if(balance>0.00f){
//					referrers.add(Constants.DEFAULT_REFERRER);
//					Set<String> set = new HashSet(referrers);
//					List<String> payeeList = new ArrayList(set);
//					
//					if(payeeList!=null){
//						for(String payee: payeeList){
//							if(UserSessionFactory.isUserIdAvailable(conn, payee)){
//								payeeList.remove(payee);
//							}
//						}
//					}
//					
//					float amount = (float)(((int)(Constants.CHARGE_PER_REFERRAL/payeeList.size()*1000.0))/1000.0);
//					float amount2= Constants.CHARGE_PER_REFERRAL - (payeeList.size()-1)*amount;
//					
//					int seq=1;
//					for(String payee: payeeList){
//						ReferralPayment referralPayment = new ReferralPayment();
//						referralPayment.paymentId = paymentId;
//						referralPayment.seq=seq;
//						referralPayment.payerId = companyCode;
//						referralPayment.payeeId = payee;
//						referralPayment.amount = Constants.DEFAULT_REFERRER.equalsIgnoreCase(payee)?amount2:amount;
//						ReferralBillingFactory.payForReferrals(conn, referralPayment);
//						seq++;
//					}
//				}
//			}else{
//				ReferralFactory.clearPaymentIdFromReferral(conn, paymentId);
//			}
//		}
//		Referral Payment - End
	}

	public static List<Referral> getReferralInfoByEmail(Connection conn, String email, Date startDate, Date endDate) throws SQLException
	{
		ArrayList<Referral> referrals = new ArrayList<Referral>();
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Referrals WHERE Email = ? AND ReferralDate > ? AND ReferralDate < ? ORDER BY ReferralDate DESC");
		try
		{
			pstmt.setString(1, email);
			pstmt.setTimestamp(2, new java.sql.Timestamp( startDate.getTime() ));
			pstmt.setTimestamp(3, new java.sql.Timestamp( endDate.getTime() ));

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Referral referral = new Referral();

					LoadReferralFromResultSet(rs, referral);
					
					referrals.add(referral);
				}
			}
			finally
			{
				rs.close();
			}
		}
		finally
		{
			pstmt.close();
		}
		
		return referrals;
	}

	public static List<Referral> getReferralInfoByCompany(Connection conn, String companyCode, Date startDate, Date endDate) throws SQLException
	{
		ArrayList<Referral> referrals = new ArrayList<Referral>();
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Referrals WHERE CompanyCode = ? AND ReferralDate > ? AND ReferralDate < ? ORDER BY ReferralDate DESC");
		try
		{
			pstmt.setString(1, companyCode);
			pstmt.setTimestamp(2, new java.sql.Timestamp( startDate.getTime() ));
			pstmt.setTimestamp(3, new java.sql.Timestamp( endDate.getTime() ));

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Referral referral = new Referral();

					LoadReferralFromResultSet(rs, referral);
					
					referrals.add(referral);
				}
			}
			finally
			{
				rs.close();
			}
		}
		finally
		{
			pstmt.close();
		}
		
		return referrals;
	}

	public static List<Referral> getReferralInfoByMarketID(Connection conn, String marketID, Date startDate, Date endDate) throws SQLException
	{
		ArrayList<Referral> referrals = new ArrayList<Referral>();
		
		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Referrals WHERE NetworkMarketID = ? AND ReferralDate > ? AND ReferralDate < ? ORDER BY ReferralDate DESC");
		try
		{
			pstmt.setString(1, marketID);
			pstmt.setTimestamp(2, new java.sql.Timestamp( startDate.getTime() ));
			pstmt.setTimestamp(3, new java.sql.Timestamp( endDate.getTime() ));

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Referral referral = new Referral();

					LoadReferralFromResultSet(rs, referral);
					
					referrals.add(referral);
				}
			}
			finally
			{
				rs.close();
			}
		}
		finally
		{
			pstmt.close();
		}
		
		return referrals;
	}
	
	// Referrals Geolocation Changes: Feb 2010 - begin
	public static Referral populateCityAndCountryInfo(Connection conn, Referral referral) throws Exception{
		String city = "";
		String countryCode = "";
		String countryName = "";
		long locationId = 0L;
		String concatStr = "";
		
		if(referral.ClientIP.indexOf(":") != -1) referral.ClientIP = "0.0.0.0";
		long ip = getConvertedIP(referral.ClientIP);
		
		if(ip != 0){
			locationId = getLocationIdFromIP(conn,ip);
			if(locationId>0L){
				concatStr = getCityAndCountryCodebyLocId(conn,locationId);
				try{
					city = concatStr.split("\\:")[0];
				}catch(Exception e){}
				
				try{
					countryCode = concatStr.split("\\:")[1];
				}catch(Exception e){}
				
				if(countryCode.length()>0){
					countryName = getCountryNamebyCode(conn,countryCode);	
				}
			}
		}
		referral.City = city;
		referral.Country = countryName;
		return referral;
	}
	
	public static long getLocationIdFromIP(Connection conn, long ip) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select locid from MAPIPTOLOCID where startipnum <= ? and endipnum >= ?");
		try
		{
			pstmt.setLong(1, ip);
			pstmt.setLong(2, ip);
			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					return(rs.getLong(1));
				}
			}
			finally
			{
				rs.close();
			}
		}catch(Exception e){	
		}
		finally
		{
			pstmt.close();
		}
		
		return 0L;
	}
	
	public static String getCityAndCountryCodebyLocId(Connection conn, long locationId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select city,twocharcode from MAPLOCIDTODATA where locid = ?");
		try
		{
			pstmt.setLong(1, locationId);
			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					return(rs.getString(1).trim()+":"+rs.getString(2).trim());
				}
			}
			finally
			{
				rs.close();
			}
		}catch(Exception e){	
		}
		finally
		{
			pstmt.close();
		}
		
		return ":";
	}
	
	public static String getCountryNamebyCode(Connection conn, String countryCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select name from Countries where twocharcode = ?");
		try
		{
			pstmt.setString(1, countryCode);
			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					return(rs.getString(1).trim());
				}
			}
			finally
			{
				rs.close();
			}
		}catch(Exception e){	
		}
		finally
		{
			pstmt.close();
		}
		return "NA";
	}
	
	public static long getConvertedIP(String stringIP){
		if (stringIP != null){
			if(stringIP.length()>=7){
				String[] ipToken =  stringIP.split("\\.");
				if(ipToken.length==4){
					long t1 = Long.valueOf(ipToken[0]).longValue();
					long t2 = Long.valueOf(ipToken[1]).longValue();
					long t3 = Long.valueOf(ipToken[2]).longValue();
					long t4 = Long.valueOf(ipToken[3]).longValue();
					long longIP = 16777216*t1 + 65536*t2 + 256*t3 + t4;
					return longIP;
				}
			}
		}
		return 0L;
	}
	public static void removeCompany(Connection conn, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Referrals where companycode = ?");
		
		pstmt.setString(1,companyCode);

		pstmt.executeUpdate();
	}
	
	public static void removeNM(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Referrals where NetworkMarketID = ?");
		
		pstmt.setString(1,marketId);

		pstmt.executeUpdate();
	}
	
	public static int getTodaysReferralCount(Connection conn, String marketId, String companyCode, String clientIp) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select count(distinct paymentid) from Referrals where NetworkMarketId=? and CompanyCode=? and ClientIp=? and bot=false and ReferralDate>date(CURRENT_TIMESTAMP) and paymentid is not null");
		try{
			pstmt.setString(1, marketId.trim());
			pstmt.setString(2, companyCode.trim());
			pstmt.setString(3, clientIp.trim());
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}finally{
			pstmt.close();
		}
	}
	
	public static void clearPaymentIdFromReferral(Connection conn, String paymentId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update Referrals set paymentid=null where paymentId=?");
		try{
			pstmt.setString(1, paymentId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	// Referrals Geolocation Changes: Feb 2010 - end
}

package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.catgen.Constants;
import com.catgen.Referrer;

public class ReferrerFactory {
	public static void save(Connection conn, Referrer referrer) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into referrer(referrercode, Name, Country, Contact, ContactEmail, Address, City, State, Zip, mobileNo) values(?,?,?,?,?,?,?,?,?,?)");
		try{
			pstmt.setString(1, referrer.referrerCode);
			pstmt.setString(2, referrer.name);
			pstmt.setString(3, referrer.countryCode);
			pstmt.setString(4, referrer.contact);
			pstmt.setString(5, referrer.contactEmail);
			pstmt.setString(6, referrer.address);
			pstmt.setString(7, referrer.city);
			pstmt.setString(8, referrer.state);
			pstmt.setString(9, referrer.zip);
			pstmt.setString(10, referrer.mobileNo);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static Referrer getReferrerByReferrerCode(Connection conn, String referrerCode) throws SQLException{
		//getting referrer by referrer code
		PreparedStatement pstmt = conn.prepareStatement("select * from referrer where referrercode=?");
		Referrer referrer = null;
		try{
			pstmt.setString(1, referrerCode);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				referrer = new Referrer();
				LoadReferrerFromResultSet(rs, referrer);
			}
		}finally{
			pstmt.close();
		}
		return referrer;
	}
	
	public static boolean isValidReferrer(String userId, String sType){
		int type=0;
		try{
			type = Integer.parseInt(sType);
		}catch(Exception e){
			type=-1;
		}
		if(userId!=null && userId.length()>0 && (Constants.REFERRER==type)){
			return true;
		}
		return false;
	}
	
	private static void LoadReferrerFromResultSet(ResultSet rs, Referrer referrer) throws SQLException{
		referrer.referrerCode = rs.getString("referrercode");
		referrer.name = rs.getString("name");
		referrer.address = rs.getString("address");
		referrer.city = rs.getString("city");
		referrer.state = rs.getString("state");
		referrer.zip = rs.getString("zip");
		referrer.countryCode = rs.getString("country");
		referrer.contact = rs.getString("contact");
		referrer.mobileNo = rs.getString("mobileNo");
		referrer.contactEmail = rs.getString("contactEmail");
	}
}

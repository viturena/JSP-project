package com.catgen.factories;
//NMMS Changes [Registration and Login] : March 2010
import java.sql.*;

import com.catgen.Constants;
import com.catgen.UserSessionBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class UserSessionFactory {
	public static int getUserType(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement( "select type from User where userId=? and password=sha1(?) and validated = true and beginDate<=CURRENT_DATE AND endDate>=CURRENT_DATE" );
		ResultSet rs;
		try{
			pstmt.setString(1, userSessionBean.userId);
			pstmt.setString(2, userSessionBean.password);
			rs = pstmt.executeQuery();
			if(rs.next()){
				return Integer.parseInt(rs.getString("type"));
			}
		}catch(Exception e){
			return Constants.NONE;
		}finally{
			pstmt.close();
		}
		return Constants.NONE;
	}
	
	public static void removeNM(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from User where userid=? and type=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setInt(2, Constants.NETWORK_MARKET);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void removeVendor(Connection conn, String vendorCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from User where userid=? and type=?");
		try{
			pstmt.setString(1, vendorCode);
			pstmt.setInt(2, Constants.VENDOR);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void validateUser(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update User set validated=true where userid=? and type=?");
		try{
			pstmt.setString(1, userSessionBean.userId);
			pstmt.setInt(2, userSessionBean.type);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void save(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		PreparedStatement pstmtx = conn.prepareStatement("delete from User where userId=? and type=?");
		PreparedStatement pstmt = conn.prepareStatement("insert into User(userId, password, type, validated, scheme, beginDate, endDate) values(?,sha1(?),?,?,?,CURRENT_DATE,?)");
		try{
			pstmtx.setString(1, userSessionBean.userId);
			pstmtx.setInt(2, userSessionBean.type);
			
			pstmt = conn.prepareStatement("insert into User(userId, password, type, validated, scheme, beginDate, endDate) values(?,sha1(?),?,?,?,CURRENT_DATE,?)");
			pstmt.setString(1, userSessionBean.userId);
			pstmt.setString(2, userSessionBean.password);
			pstmt.setInt(3, userSessionBean.type);
			pstmt.setBoolean(4, userSessionBean.validated);
			pstmt.setInt(5, userSessionBean.scheme);
			pstmt.setDate(6, new java.sql.Date(userSessionBean.endDate.getTime().getTime()));
			
			pstmtx.execute();
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void updatePassword(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update User set password=sha1(?) where userId=? and type=?");
		try{
			pstmt.setString(1, userSessionBean.password);
			pstmt.setString(2, userSessionBean.userId);
			pstmt.setInt(3, userSessionBean.type);
			
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static List<UserSessionBean> getValidatedUserList(Connection conn, int type) throws SQLException{
		List<UserSessionBean> validatedUserList = new ArrayList<UserSessionBean>();
		PreparedStatement pstmt = conn.prepareStatement("select * from User where validated = true and beginDate<=CURRENT_DATE and endDate>=CURRENT_DATE and type=?");
		pstmt.setInt(1, type);
		ResultSet rs;
		try{
			rs = pstmt.executeQuery();
			while(rs.next()){
				UserSessionBean userSessionBean = new UserSessionBean();
				Calendar calendar1 = Calendar.getInstance();
				Calendar calendar2 = Calendar.getInstance();
				userSessionBean.userId = rs.getString("userId");
				userSessionBean.type = rs.getInt("type");
				userSessionBean.validated = rs.getBoolean("validated");
				userSessionBean.scheme = rs.getInt("scheme");
				
				calendar1.setTime(rs.getDate("beginDate"));
				userSessionBean.beginDate = calendar1;
				
				calendar2.setTime(rs.getDate("endDate"));
				userSessionBean.endDate = calendar2;
				
				validatedUserList.add(userSessionBean);
			}
		}finally{
			pstmt.close();
		}
		return validatedUserList;
	}
	
	public static List<UserSessionBean> getUnvalidatedUserList(Connection conn, int type) throws SQLException{
		List<UserSessionBean> unvalidatedUserList = new ArrayList<UserSessionBean>();
		PreparedStatement pstmt = conn.prepareStatement("select * from User where validated = false and beginDate<=CURRENT_DATE and endDate>=CURRENT_DATE and type=?");
		pstmt.setInt(1, type);
		ResultSet rs;
		try{
			rs = pstmt.executeQuery();
			while(rs.next()){
				UserSessionBean userSessionBean = new UserSessionBean();
				Calendar calendar1 = Calendar.getInstance();
				Calendar calendar2 = Calendar.getInstance();
				userSessionBean.userId = rs.getString("userId");
				userSessionBean.type = rs.getInt("type");
				userSessionBean.validated = rs.getBoolean("validated");
				userSessionBean.scheme = rs.getInt("scheme");
				
				calendar1.setTime(rs.getDate("beginDate"));
				userSessionBean.beginDate = calendar1;
				
				calendar2.setTime(rs.getDate("endDate"));
				userSessionBean.endDate = calendar2;
				
				unvalidatedUserList.add(userSessionBean);
			}
		}finally{
			pstmt.close();
		}
		return unvalidatedUserList;
	}
	
//	public static void cancel(Connection conn, String userId) throws SQLException{
//		PreparedStatement pstmt = conn.prepareStatement("Update User set endDate='0001-01-01' validated=false where userId=?");
//		try{
//			pstmt.setString(1, userId);
//			pstmt.executeUpdate();
//		}finally{
//			pstmt.close();
//		}
//	}
	
//	public static void validate(Connection conn, MasterData masterData) throws SQLException{
//		PreparedStatement pstmt = conn.prepareStatement("update User set beginDate=CURRENT_DATE, endDate=?, validated=true where userId=?");
//		try{
//			UserSessionBean userSessionBean = masterData.userSessionBean;
//			Calendar calendar = Utils.getDateAfterGivenMonths(SchemeFactory.getNumberofMonthsByScheme(conn, userSessionBean.scheme));
//			java.util.Date date = calendar.getTime();
//			
//			pstmt.setDate(1, new java.sql.Date(date.getTime()));
//			pstmt.setString(2, userSessionBean.userId);
//			
//			pstmt.executeUpdate();
//		}finally{
//			pstmt.close();
//		}
//	}
	
	public static boolean isValidUser(String userId, String strType){
		if(userId==null || userId.length()==0 || strType==null || strType.length()==0){
			return false;
		}
		int type=0;
		try{
			type = Integer.parseInt(strType);
		}catch(Exception e){
			return false;
		}
		
		switch(type){
		case Constants.SUPER_ADMIN:
			return SuperAdminFactory.isSuperAdmin(userId, strType);
		case Constants.NETWORK_MARKET:
			return NetMarketFactory.isValidNM(userId, strType);
		case Constants.VENDOR:
			return CompanyFactory.isValidVendor(userId, strType);
		case Constants.REFERRER:
			return ReferrerFactory.isValidReferrer(userId, strType);
		default:
			return false;
		}
	}
	
	public static boolean hasValidPassword(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from User where userid=? and type=? and password=sha1(?)");
		try{
			pstmt.setString(1, userSessionBean.userId);
			pstmt.setInt(2, userSessionBean.type);
			pstmt.setString(3, userSessionBean.password);
			
			ResultSet rs = pstmt.executeQuery();
			return(rs.next());
		}finally{
			pstmt.close();
		}
	}
	
	public static boolean isUserIdAvailable(Connection conn, String userId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from User where userid=?");
		try{
			pstmt.setString(1, userId);
			ResultSet rs = pstmt.executeQuery();
			return !rs.next();
		}finally{
			pstmt.close();
		}
	}
	
	public static boolean isAccountActive(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from User where userid=? and type=? and beginDate<=CURRENT_DATE and endDate>=CURRENT_DATE and validated=true");
		try{
			pstmt.setString(1, userSessionBean.userId);
			pstmt.setInt(2, userSessionBean.type);
			return pstmt.executeQuery().next();
		}finally{
			pstmt.close();
		}
	}
	
	public static UserSessionBean getUserSessionBeanByIdAndType(Connection conn, String userId, int type) throws SQLException{
		UserSessionBean userSessionBean = new UserSessionBean();
		PreparedStatement pstmt = conn.prepareStatement("select * from User where userid=? and type=?");
		try{
			pstmt.setString(1, userId);
			pstmt.setInt(2, type);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()){
				loadUserSessionBeanFromResultSet(rs, userSessionBean);
			}
		}finally{
			pstmt.close();
		}
		return userSessionBean;
	}
	
	public static void loadUserSessionBeanFromResultSet(ResultSet rs, UserSessionBean userSessionBean) throws SQLException{
		userSessionBean.userId = rs.getString("userid");
		userSessionBean.type = rs.getInt("type");
		userSessionBean.validated = rs.getBoolean("validated");
		userSessionBean.scheme = rs.getInt("scheme");
		userSessionBean.password = rs.getString("password");
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(rs.getDate("beginDate"));
		userSessionBean.beginDate = calendar1;
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(rs.getDate("endDate"));
		userSessionBean.endDate = calendar2;
	}
}

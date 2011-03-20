package com.catgen.factories;

import java.sql.*;

public class SchemeFactory {
	public static int getNumberofMonthsByScheme(Connection conn, int schemeType){
		PreparedStatement pstmt=null;
		ResultSet rs;
		try{
			pstmt = conn.prepareStatement("select duration from scheme where type=?");
			pstmt.setInt(1, schemeType);
			rs = pstmt.executeQuery();
			if (rs.next()){
				return rs.getInt("duration");
			}
		}catch(Exception e){
			e.printStackTrace();
			return 0;
		}finally{
			try{
				pstmt.close();
			}catch(Exception e){
			}
		}
		return 0;
	}
}

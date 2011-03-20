package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SuperNMFactory {
	public static List<String> getContainedNmByContainerNm(Connection conn, String containerNm) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select distinct containedNm from nm2nmmap where containerNm=? and containedNm!=?");
		List<String> containedNmList = null;
		try{
			pstmt.setString(1, containerNm);
			pstmt.setString(2, containerNm);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				containedNmList = new ArrayList<String>();
				do{
					containedNmList.add(rs.getString("containedNm"));
				}while(rs.next());
			}
		}finally{
			pstmt.close();
		}
		return containedNmList;
	}
	
	public static void removeAllSubNmFromSuperNM(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from nm2nmmap where containerNm=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void removeAll(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from nm2nmmap where containerNm = ? or containedNm = ?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
}

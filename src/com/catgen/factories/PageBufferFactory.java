package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.catgen.PageBuffer;

public class PageBufferFactory {
	public static void save(Connection conn, PageBuffer pageBuffer) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into pagebuffer values(?,?,?,?,?)");
		try{
			pstmt.setString(1, pageBuffer.marketId);
			pstmt.setString(2, pageBuffer.bufferId);
			pstmt.setInt(3, pageBuffer.size);
			pstmt.setInt(4, pageBuffer.seq);
			pstmt.setString(5, pageBuffer.data);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void removePageBuffers(Connection conn, String marketId, String bufferId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from pagebuffer where marketId=? and bufferid=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, bufferId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void removePageBuffer(Connection conn, String marketId, String bufferId, int seq) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from pagebuffer where marketId=? and bufferid=? and seq=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, bufferId);
			pstmt.setInt(3,seq);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static String getPageBufferData(Connection conn, String marketId, String bufferId, int seq) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from pagebuffer where marketid=? and bufferid=? and seq=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, bufferId);
			pstmt.setInt(3, seq);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getString("data");
			}else{
				return "-- PAGE BUFFER NOT FOUND --";
			}
		}finally{
			pstmt.close();
		}
	}
	
	public static int getPageBufferSize(Connection conn, String marketId, String bufferId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select distinct size from pagebuffer where marketid=? and bufferid=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, bufferId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getInt("size");
			}else{
				return 0;
			}
		}finally{
			pstmt.close();
		}
	}
	
	public static int availablePageBufferCount(Connection conn, String marketId, String bufferId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select count(*) from pagebuffer where marketid=? and bufferid=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, bufferId);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt(1);
		}finally{
			pstmt.close();
		}
	}
}

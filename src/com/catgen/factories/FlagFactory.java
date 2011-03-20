package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.catgen.Flag;

public class FlagFactory {
	
	public static Flag getFlags(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select * from flags");
		Flag flag = null;
		try{
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				flag = new Flag();
				populateFlagFromResultSet(flag, rs);
			}
		}finally{
			pstmt.close();
		}
		return flag;
	}
	
	public static void setImageCheckFlag(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update flags set image_check=true");
		try{
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void clearImageCheckFlag(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update flags set image_check=false");
		try{
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void setCategoryImportFlag(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update flags set category_import=true");
		try{
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void clearCategoryImportFlag(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update flags set category_import=false");
		try{
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void setGaeHashFlag(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update flags set gae_hash=true");
		try{
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void clearGaeHashFlag(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update flags set gae_hash=false");
		try{
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void populateFlagFromResultSet(Flag flag, ResultSet rs) throws SQLException{
		flag.imageCheck = rs.getBoolean("image_check");
		flag.categoryImport = rs.getBoolean("category_import");
		flag.gaeHash = rs.getBoolean("gae_hash");
	}
}

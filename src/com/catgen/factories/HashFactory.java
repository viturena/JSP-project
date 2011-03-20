package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.catgen.exception.InvalidHashException;

public class HashFactory {
	public static void saveHash(Connection conn, String hash) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into HashDump(hash) values(?)");
		try{
			pstmt.setString(1, hash);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static boolean validateAndRemoveHash(Connection conn, String hash) throws SQLException, InvalidHashException{
		PreparedStatement pstmt = conn.prepareStatement("select * from HashDump where hash=?");
		try{
			pstmt.setString(1, hash);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				removeHash(conn, hash);
				return true;
			}else{
				throw new InvalidHashException();
			}
		}finally{
			pstmt.close();
		}
	}

	public static void removeHash(Connection conn, String hash) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from HashDump where hash=?");
		try{
			pstmt.setString(1, hash);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
}

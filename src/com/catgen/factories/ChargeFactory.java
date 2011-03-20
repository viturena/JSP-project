package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.catgen.Charge;
import com.catgen.Constants;

public class ChargeFactory {
	private static void save(Connection conn, Charge charge) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into Charges values(?,?,?)");
		try{
			pstmt.setString(1, charge.marketId);
			pstmt.setDouble(2, charge.chargeToAddVendor);
			pstmt.setDouble(3, charge.chargeToFeatureVendor);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void updateToDefault(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Charges where marketid=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void update(Connection conn, Charge charge) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update Charges set addvendor=?,featurevendor=? where marketid=?");
		try{
			pstmt.setDouble(1, charge.chargeToAddVendor);
			pstmt.setDouble(2, charge.chargeToFeatureVendor);
			pstmt.setString(3, charge.marketId);
			int updated = pstmt.executeUpdate();
			if(updated==0){
				save(conn,charge);
			}
		}finally{
			pstmt.close();
		}
	}
	
	public static Charge getCharge(Connection conn, String marketId) throws SQLException{
		Charge charge = new Charge();
		PreparedStatement pstmt = conn.prepareStatement("select * from Charges where marketid=?");
		try{
			pstmt.setString(1, marketId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				populateChargeFromResultSet(rs, charge);
			}else{
				charge.marketId = marketId;
				charge.chargeToAddVendor = Constants.CHARGE_ADD_VENDOR;
				charge.chargeToFeatureVendor = Constants.CHARGE_FEATURE_VENDOR;
				charge.isDefault = true;
			}
			return charge;
		}finally{
			pstmt.close();
		}
	}
	
	public static void populateChargeFromResultSet(ResultSet rs, Charge charge) throws SQLException{
		charge.marketId = rs.getString("marketid");
		charge.chargeToAddVendor = rs.getDouble("addvendor");
		charge.chargeToFeatureVendor = rs.getDouble("featurevendor");
		charge.isDefault = false;
	}
}

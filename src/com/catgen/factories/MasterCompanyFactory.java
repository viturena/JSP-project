package com.catgen.factories;
//NMMS Changes [Registration and Login] : March 2010
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.catgen.Constants;
import com.catgen.MasterCompany;
import com.catgen.Utils;
import com.catgen.exception.DuplicateCatalogCodeException;
import com.catgen.loader.MasterCompanyLoader;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class MasterCompanyFactory 
{
	public static void save(Connection conn, String companyCode, String sskey, String companySheet, String productSheet, Date endDate) throws SQLException, DuplicateCatalogCodeException{
		PreparedStatement pstmt=conn.prepareStatement("INSERT INTO mastercompanies(companycode, sskey, companysheet, productsheet, premiumtill) values(?,?,?,?,?)");
		try{
			pstmt.setString(1, companyCode);
			pstmt.setString(2, sskey);
			pstmt.setString(3, companySheet);
			pstmt.setString(4, productSheet);
			pstmt.setDate(5, new java.sql.Date(endDate.getTime()));
			pstmt.execute();
		}catch(MySQLIntegrityConstraintViolationException e){
			throw new DuplicateCatalogCodeException();
		}finally{
			pstmt.close();
		}
	}
	
	public static void updateMasterCompanies(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("DELETE FROM mastercompanies");
		pstmt.executeUpdate();
		
		MasterCompanyLoader masterCompanyLoader = new MasterCompanyLoader();
		masterCompanyLoader.LoadData(conn, "http://spreadsheets.google.com/feeds/cells/t7fgcHCqRaohq3LEjkw4h7Q/od6/public/basic");
	}
	
	private static void LoadMasterCompaniesFromResultSet(ResultSet rs, MasterCompany masterCompany) throws SQLException{
		masterCompany.companyCode = rs.getString("companycode");
		String companySheet = rs.getString("companysheet");
		String productSheet = rs.getString("productsheet");
		masterCompany.premiumTill = rs.getDate("premiumtill");
		String key = rs.getString("sskey");
		
		if(companySheet!=null && companySheet.length()>0){
			masterCompany.companyURL = Utils.getCompanyDataFeedURLBySpreadsheetKey(key, companySheet);
		}else{
			masterCompany.companyURL = Utils.getCompanyDataFeedURLBySpreadsheetKey(key, String.valueOf(Constants.SEQUENCE_COMPANY_SHEET));
		}		
		
		if(productSheet!=null && productSheet.length()>0){
			String []seqs = productSheet.split(",");
			for(int i=0;i<seqs.length;i++){
				masterCompany.productURL.add(Utils.getProductDataFeedURLBySpreadsheetKey(key,seqs[i]));
			}
		}else{
			masterCompany.productURL.add(Utils.getCompanyDataFeedURLBySpreadsheetKey(key, String.valueOf(Constants.SEQUENCE_PRODUCT_SHEET)));
		}
	}

	public static MasterCompany getMasterCompanyByCode(Connection conn, String companyCode) throws SQLException{
		MasterCompany masterCompany = null;

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM mastercompanies WHERE companycode = ?" );
		try{
			pstmt.setString(1, companyCode);
			ResultSet rs = pstmt.executeQuery();
			try{
				if(rs.next()){
					masterCompany = new MasterCompany();
					LoadMasterCompaniesFromResultSet(rs, masterCompany);
				}
			}finally{
				rs.close();
			}
		}finally{
			pstmt.close();
		}
		return masterCompany;
	}
	
	public static List<MasterCompany> getMasterCompanies(Connection conn) throws SQLException{
		ArrayList<MasterCompany> masterCompanies = new ArrayList<MasterCompany>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM mastercompanies ORDER BY companycode" );
		try{
			ResultSet rs = pstmt.executeQuery();
			try{
				while(rs.next()){
					MasterCompany masterCompany = new MasterCompany();
					LoadMasterCompaniesFromResultSet(rs, masterCompany);
					masterCompanies.add(masterCompany);
				}
			}finally{
				rs.close();
			}
		}finally{
			pstmt.close();
		}	
		return masterCompanies;
	}
	
	public static String getSSkeyByCompanyCode(Connection conn, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement( "SELECT sskey FROM mastercompanies where companycode=?" );
		try{
			pstmt.setString(1, companyCode);
			ResultSet rs = pstmt.executeQuery();
			try{
				if(rs.next()){
					return rs.getString("sskey");
				}
			}finally{
				rs.close();
			}
		}finally{
			pstmt.close();
		}	
		return "";
	}
	
	public static List<MasterCompany> getMasterCompaniesStartingWith(Connection conn, String character) throws SQLException{
		ArrayList<MasterCompany> masterCompanies = new ArrayList<MasterCompany>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM mastercompanies where companycode like ? ORDER BY companycode" );
		try{
			pstmt.setString(1, character+"%");
			ResultSet rs = pstmt.executeQuery();
			try{
				while(rs.next()){
					MasterCompany masterCompany = new MasterCompany();
					LoadMasterCompaniesFromResultSet(rs, masterCompany);
					masterCompanies.add(masterCompany);
				}
			}finally{
				rs.close();
			}
		}finally{
			pstmt.close();
		}	
		return masterCompanies;
	}
	
	public static void removeCompany(Connection conn, String companyCode) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from mastercompanies where companycode = ?");
		
		pstmt.setString(1,companyCode);

		pstmt.executeUpdate();
	}
	
	public static void updatePremiumTillDate(Connection conn, String companyCode, Date endDate) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update mastercompanies set premiumtill=? where companycode = ?");
		
		pstmt.setDate(1, new java.sql.Date(endDate.getTime()));
		pstmt.setString(2,companyCode);

		pstmt.executeUpdate();
	}
}

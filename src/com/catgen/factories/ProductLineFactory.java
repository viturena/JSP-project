package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catgen.ProductLine;

public class ProductLineFactory 
{
	private static void LoadProductLineFromResultSet(ResultSet rs, ProductLine productLine) throws SQLException
	{
		//productLine.NetworkMarketID = rs.getString("MarketID");
		productLine.CompanyCode = rs.getString("CompanyCode");
		productLine.Code = rs.getString("Code");
		productLine.Name = rs.getString("Name");
	}

	public static boolean ExistsByName(Connection conn, String marketId, String companyCode, String name) throws SQLException
	{
		boolean result = false;
		
		if(name == null || name.trim().length() == 0)
			return result;
		
		PreparedStatement pstmt = conn.prepareStatement( "SELECT COUNT(*) FROM ProductLines WHERE MarketID = ? AND CompanyCode = ? AND Name = ?" );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			pstmt.setString(3, name);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
					result = rs.getInt(1) > 0;
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
		
		return result;
	}
	
	public static List<ProductLine> getMarketProductLines(Connection conn, String marketId) throws SQLException
	{
		ArrayList<ProductLine> productLines = new ArrayList<ProductLine>();
		
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM ProductLines WHERE MarketID = ? ORDER BY Name" );
		try
		{
			pstmt.setString(1, marketId);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					ProductLine productLine = new ProductLine();

					LoadProductLineFromResultSet(rs, productLine);
					
					productLines.add(productLine);
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
		
		
		return productLines;
	}
	
	
	public static List<ProductLine> getProductLines(Connection conn, String marketId, String companyCode) throws SQLException
	{
		ArrayList<ProductLine> productLines = new ArrayList<ProductLine>();
		
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM ProductLines WHERE MarketID = ? AND CompanyCode = ? ORDER BY Name" );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					ProductLine productLine = new ProductLine();

					LoadProductLineFromResultSet(rs, productLine);

					productLines.add(productLine);
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
		
		
		return productLines;
	}

	public static ProductLine getProductLineByCode(Connection conn, String marketId, String companyCode, String productLineCode) throws SQLException
	{
		ProductLine productLine = null;

		if(productLineCode == null || productLineCode.trim().length() == 0)
			return productLine;
		
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM ProductLines WHERE MarketID = ? AND CompanyCode = ? AND Name = ?" );
		try
		{
			pstmt.setString(1, marketId);
			pstmt.setString(2, companyCode);
			pstmt.setString(3, productLineCode);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					productLine = new ProductLine();

					LoadProductLineFromResultSet(rs, productLine);
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
		
		
		return productLine;
	}
	
	public static ProductLine getProductLineByName(Connection conn, String companyCode, String productLineName) throws SQLException
	{
		ProductLine productLine = null;

		if(productLineName == null || productLineName.trim().length() == 0)
			return productLine;
		
		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM ProductLines WHERE CompanyCode = ? AND Name = ?" );
		try
		{
			//pstmt.setString(1, marketId);
			pstmt.setString(1, companyCode);
			pstmt.setString(2, productLineName);

			ResultSet rs = pstmt.executeQuery();
			try
			{
				if(rs.next())
				{
					productLine = new ProductLine();

					LoadProductLineFromResultSet(rs, productLine);
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
		
		
		return productLine;
	}

	public static void removeCompany(Connection conn, String companyCode) throws SQLException {
			PreparedStatement pstmt = conn.prepareStatement("delete from ProductLines where companycode = ?");
			
			pstmt.setString(1,companyCode);

			pstmt.executeUpdate();
	}
}

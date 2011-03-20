package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import com.catgen.PriceRange;

public class PriceFactory {
	public static List<PriceRange> getPriceRanges(Connection conn) throws SQLException
	{
		List<PriceRange> priceRangeList = new ArrayList<PriceRange>();
		PreparedStatement pstmt = conn.prepareStatement( "select * from pricerange order by value asc" );
		try
		{
			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					PriceRange priceRange = new PriceRange();
					LoadPriceFromResultSet(rs, priceRange);
					priceRangeList.add(priceRange);
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
		return priceRangeList;
	}
	
	public static void LoadPriceFromResultSet(ResultSet rs, PriceRange priceRange) throws SQLException{
		priceRange.value = rs.getInt("value");
		priceRange.display = rs.getString("display");
		priceRange.greaterthan = rs.getInt("greaterthan");
		priceRange.lesserthan = rs.getInt("lesserthan");
	}
}
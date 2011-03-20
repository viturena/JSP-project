package com.catgen.factories;
//NMMS Changes [Registration and Login] : March 2010

import com.catgen.Country;

import java.util.List;
import java.util.ArrayList;
import java.sql.*;

public class CountryFactory {
	public static List<Country> fetchAllCountries(Connection conn){
		List<Country> countries = new ArrayList<Country>();
		ResultSet rs;
		PreparedStatement pstmt;
		try
		{
			pstmt = conn.prepareStatement( "SELECT twoCharCode, Name FROM Countries" );
			rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Country country = new Country();
					country.code = rs.getString("twoCharCode");
					country.name = rs.getString("name");
					countries.add(country);
				}
			}
			finally
			{
				rs.close();
				pstmt.close();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return countries;
	}
}

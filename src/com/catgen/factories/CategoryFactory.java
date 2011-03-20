package com.catgen.factories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.catgen.Category;
import com.catgen.Constants;

public class CategoryFactory {
	
	// NMMS Changes [Registration and Login] : March 2010 - begin
	public static void save(Connection conn, Category category) throws SQLException{
		PreparedStatement pstmt=conn.prepareStatement("INSERT INTO Category(marketid, catkey, catvalue) values(?, ?, ?)");
		try{
			pstmt.setString(1, category.marketId);
			pstmt.setString(2, category.categoryKey);
			pstmt.setString(3, category.categoryValue);
			
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void removeAll(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Category where marketid = ?");
		try{
			pstmt.setString(1, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void editCategory(Connection conn, String marketId, String catKey, String editedCategoryValue) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("update Category set catvalue = ? where marketid=? and catkey=?");
		try{
			pstmt.setString(1, editedCategoryValue);
			pstmt.setString(2, marketId);
			pstmt.setString(3, catKey);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	
	public static void removeCategory(Connection conn, String marketId, String catKey) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Category where marketid=? and catkey=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.setString(2, catKey);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
    /**
     * get the value of Category from its key.
     * @param catkey Category key from which to get the category value
     */	

     public static String getCategoryValue(Connection conn, String catkey) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement( "SELECT categoryname FROM xmlcategory where categorykey = ?" );
		String categoryName="";
		try{
			pstmt.setString(1, catkey);
			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					categoryName = rs.getString("categoryname");
				}
			}
			finally
			{
				rs.close();
			}
			
		}finally{
			pstmt.close();
		}
		return categoryName;
	}
	

     /**
     * Get the standard categoris of NM to be provided for listing to all the NM members.
     */	

	public static List<Category> getStandardCategories(Connection conn) throws SQLException
	{
		ArrayList<Category> categories = new ArrayList<Category>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM xmlcategory order by categoryname asc limit 10" );
		try
		{
			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Category category = new Category();
					category.categoryKey = rs.getString("categorykey");
					category.categoryValue = rs.getString("categoryname");
					categories.add(category);
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
		return categories;
	}

	public static List<Category> getCategories(Connection conn, String marketId) throws SQLException
	{
		ArrayList<Category> categories = new ArrayList<Category>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM Category WHERE MarketID = ? order by catkey asc" );
		try
		{
			pstmt.setString(1, marketId);
			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Category category = new Category();
					LoadCategoryFromResultSet(rs, category);
					categories.add(category);
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
		return categories;
	}

	public static List<Category> getAssociatedNMCategoriesByCompanyCode(Connection conn, String companyCode) throws SQLException
	{
		ArrayList<Category> categories = new ArrayList<Category>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT distinct c.catkey,c.catvalue FROM Category c, nm2companymap m WHERE c.MarketID = m.marketid and m.companycode=? order by c.catvalue asc" );
		try
		{
			pstmt.setString(1, companyCode);
			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					Category category = new Category();
					LoadCategoryFromResultSet(rs, category);
					categories.add(category);
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
		return categories;
	}

	public static List<String> getCategoryKeys(Connection conn, String marketId) throws SQLException
	{
		ArrayList<String> categories = new ArrayList<String>();

		PreparedStatement pstmt = conn.prepareStatement( "SELECT distinct catkey FROM Category WHERE MarketID = ? order by catkey asc" );
		try
		{
			pstmt.setString(1, marketId);
			ResultSet rs = pstmt.executeQuery();
			try
			{
				while(rs.next())
				{
					categories.add(rs.getString(1));
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
		return categories;
	}
	
	public static void LoadCategoryFromResultSet(ResultSet rs, Category category) throws SQLException{
		try{
			category.marketId = rs.getString("marketid");
		}catch(SQLException e){
			
		}
		category.categoryKey = rs.getString("catkey");
		category.categoryValue = rs.getString("catvalue");
	}
	
	// Category Property Addition Change : March 2010 - Begin
	public static String getValueforCategoryKey(List<Category> categories, String key){
		for(Category cat: categories){
			if((cat.categoryKey).equalsIgnoreCase(key)){
				return cat.categoryValue;
			}
		}
		return ("");
	}
	// Category Property Addition Change : March 2010 - End
	
	
	/**
     * Save Categories selected from standard category to the NM categorylist.
     * @param StaticCategories Categories selected from standard to be used in NM
	 * @param marketId market id for NM to insert category
     */
	public static void saveMarketCategories(Connection conn,String[] StaticCategories ,String marketId ){
		if(StaticCategories.length > 0){  // Save only when some of the categories are selected
			for(int counter = 0; counter < StaticCategories.length; counter++)                    
			{                    
			    Category category = new Category();
				category.marketId = marketId;
				category.categoryKey = StaticCategories[counter];
				try {
					category.categoryValue = getCategoryValue(conn,category.categoryKey);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				try{
					CategoryFactory.save(conn, category);
				}
				catch(Exception e){
					
				}
				
			} 
		}
	}
	
	
	public static void saveXmlCategory(Connection conn, Category category) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("insert into xmlcategory(categorykey, categoryname) values(?,?)");
		try{
			pstmt.setString(1, category.categoryKey);
			pstmt.setString(2, category.categoryValue);
			pstmt.executeUpdate();
		}finally{
			pstmt.close();
		}
	}
	
	public static void clearXmlCategories(Connection conn) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from xmlcategory");
		try{
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	
	/**
     * get categories one level below the passed category key
     * @param key Category Key of which subcategories are extracted
	 * @param marketId 
     */

	public static List<Category> getOneLevelChildCategories(Connection conn, String key, String marketId) throws SQLException{
		List<Category> categories = new ArrayList<Category>();
		int keyLength = key.length()+Constants.CHARS_PER_CATEGORY;
		PreparedStatement pstmt = conn.prepareStatement("select * from xmlcategory where categorykey like ? and length(categorykey) = ? and categorykey in (select catkey from Category where length(catkey) = ? and marketid= ?) order by categorykey");
		try{
			pstmt.setString(1, key+"%");
			pstmt.setInt(2, keyLength);
			pstmt.setInt(3, keyLength);
			pstmt.setString(4, marketId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
//				String categorykey = rs.getString("categorykey");
//				if(categorykey.length()==key.length()+Constants.CHARS_PER_CATEGORY){
					Category category = new Category();
					populateCategoryFromResultSet(rs, category);
					categories.add(category);
//				}
			}
		}finally{
			pstmt.close();
		}
		return categories;
	}
	
	
	public static List<Category> getChildCategories(Connection conn, String key) throws SQLException{
		List<Category> categories = new ArrayList<Category>();
		PreparedStatement pstmt = conn.prepareStatement("select * from xmlcategory where categorykey like ? order by categorykey");
		try{
			pstmt.setString(1, key+"%");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				String categorykey = rs.getString("categorykey");
				if(categorykey.length()==key.length()+Constants.CHARS_PER_CATEGORY){
					Category category = new Category();
					populateCategoryFromResultSet(rs, category);
					categories.add(category);
				}
			}
		}finally{
			pstmt.close();
		}
		return categories;
	}
	
	public static void populateCategoryFromResultSet(ResultSet rs, Category category) throws SQLException{
		category.categoryKey = rs.getString("categorykey");
		category.categoryValue = rs.getString("categoryname");
	}
	
	public static String getCategoryNameByCategoryKeyFromXml(Connection conn, String key) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select categoryname from xmlcategory where categorykey=?");
		String name = "Not Found";
		try{
			pstmt.setString(1, key);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				name = rs.getString("categoryname");
			}
		}finally{
			pstmt.close();
		}
		return name;
	}
	
	
	public static String getCategoryNameByCategoryKeyFromCategory(Connection conn, String key, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select catvalue from Category where catkey=? and marketid = ?");
		String name = "Not Found";
		try{
			pstmt.setString(1, key);
			pstmt.setString(2, marketId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				name = rs.getString("catvalue");
			}
		}finally{
			pstmt.close();
		}
		return name;
	}
	
	public static boolean isValidXmlCategoryKey(Connection conn, String key) throws SQLException{
		boolean flag = false;
		PreparedStatement pstmt = conn.prepareStatement("select * from xmlcategory where categorykey=?");
		pstmt.setString(1, key);
		try{
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				flag = true;
			}
		}finally{
			pstmt.close();
		}
		return flag;
	}
	
	public static String getRootCategoryKey(Connection conn, String key) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("select min(categorykey) from xmlcategory");
		try{
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				return rs.getString("categorykey");
			}
		}finally{
			pstmt.close();
		}
		return "0";
	}
}
package com.catgen.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.catgen.Category;
import com.catgen.Constants;
import com.catgen.Utils;
import com.catgen.factories.CategoryFactory;
import com.catgen.factories.LoggerFactory;
import com.catgen.factories.XMLFactory;

public class CategoryHelper {
	
	public static void saveXmlCategory(Connection conn) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException{
		LoggerFactory.write(conn, "Category XML path: "+Constants.CATEGORY_XML_PATH);
		List<Category> categoryList = CategoryHelper.getCategoryListFromXML();
		CategoryFactory.clearXmlCategories(conn);
		for(Category category: categoryList){
			CategoryFactory.saveXmlCategory(conn, category);
		}
	}
	
	public static void saveTxtCategory(Connection conn) throws IllegalAccessException, InstantiationException, ClassNotFoundException, SQLException, IOException{
		LoggerFactory.write(conn, "Category TXT path: "+Constants.CATEGORY_TXT_PATH);
		List<Category> categoryList = CategoryHelper.getCategoryListFromTxt();
		CategoryFactory.clearXmlCategories(conn);
		for(Category category: categoryList){
			CategoryFactory.saveXmlCategory(conn, category);
		}
	}

	public static List<Category> getCategoryListFromXML() throws IllegalAccessException, InstantiationException, ClassNotFoundException{
		List<Category> categories = new ArrayList<Category>();
	
		Document doc = XMLFactory.getXMLDocumentByURL(Constants.CATEGORY_XML_PATH);
		NodeList nodeList = doc.getElementsByTagName("category");
		for(int i=0;i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			if(Node.ELEMENT_NODE==node.getNodeType()){
				Element element = (Element)node;
				
				NodeList keylist = element.getElementsByTagName("key");
	            Element keyElement = (Element)keylist.item(0);
	            NodeList keyFNList = keyElement.getChildNodes();
	            String keyValue = ((Node)keyFNList.item(0)).getNodeValue().trim();
	            
	            NodeList namelist = element.getElementsByTagName("name");
	            Element nameElement = (Element)namelist.item(0);
	            NodeList nameFNList = nameElement.getChildNodes();
	            String nameValue = ((Node)nameFNList.item(0)).getNodeValue().trim();
	            
	            Category category = new Category();
	            category.categoryKey = keyValue;
	            category.categoryValue = nameValue;
	            categories.add(category);
			}
		}
		return categories;
	}
	
	public static boolean hasSubCategory(Connection conn, String key) throws SQLException{
		List<Category> categories = CategoryFactory.getChildCategories(conn, key);
		if (categories.size()>0){
			return true;
		}else{
			return false;
		}
	}
	
	public static List<Category> getXmlSubCategoryListByCategoryKey(Connection conn, String marketId, String key) throws SQLException{
		List<Category> categories = new ArrayList<Category>();
		if(key==null || key.length()==0){
			//non-category search.
			//display all the top level categories selected by the Network Market
			List<Category> dbCategories = CategoryHelper.getRootCategories(conn, marketId);
			for(Category category: dbCategories){
				if(CategoryFactory.isValidXmlCategoryKey(conn, category.categoryKey) || category.categoryKey.length()==0){
					categories.add(category);
				}
			}
		}else{
			//category search.
			//display child categories of selected category
			categories = CategoryFactory.getOneLevelChildCategories(conn, key,marketId);
		}
		return categories;
	}
	
	public static List<Category> getCategoryListFromTxt() throws IOException{
		String url = Constants.CATEGORY_TXT_PATH;
		List<Category> categories = new ArrayList<Category>();
		URL urlObj = new URL(url);
		URLConnection urlConnection = urlObj.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		
		int lineNo = 1;
		String lineText;
		String[] parent = new String[99];
		int[] relativeLoc = new int[99];
		
		while ((lineText = in.readLine()) != null){
			String[] token = lineText.split(">>");
			
			String tempKey = "";
			for(int ctr=1;ctr<=token.length;ctr++){
				String thisToken = token[ctr-1];
				String thisParent = parent[ctr-1];
				if(!(thisToken.equals(thisParent))){
					relativeLoc[ctr-1]++;
					tempKey += (Constants.XML_CATEGORY_ROOT_NAME.equalsIgnoreCase(thisToken))?Constants.XML_CATEGORY_ROOT_KEY:Utils.getThreeCharValue(relativeLoc[ctr-1]);
					parent[ctr-1] = thisToken;
					relativeLoc[ctr]=0;
					
					Category category = new Category();
					category.categoryKey = tempKey;
					category.categoryValue = thisToken;
					categories.add(category);
				}else{
					if(Constants.XML_CATEGORY_ROOT_NAME.equalsIgnoreCase(thisToken)){
						tempKey = Constants.XML_CATEGORY_ROOT_KEY;
					}else{
						tempKey += Utils.getThreeCharValue(relativeLoc[ctr-1]);
					}
					parent[ctr-1] = thisToken;
				}
			}
			lineNo++;
		}
		in.close();
		return categories;
	}
	
	public static String getoneRecursiveHTML(Connection conn, String categoryKey, String marketId) throws SQLException{
		if(categoryKey!=null && categoryKey.length()>0){
			if(CategoryFactory.getOneLevelChildCategories(conn, categoryKey,marketId).size()==0){
				return "<li><a href=\"##RELATIVE_PATH##Search.html?category="+categoryKey+"&test=correct&pricerange=1\">"+CategoryFactory.getCategoryNameByCategoryKeyFromXml(conn, categoryKey)+"</a></li>";
			}else{
				String ret = "<li><a href=\""+Constants.RELATIVE_PATH+"Search.html?category="+categoryKey+"&test=correct&pricerange=1\">"+CategoryFactory.getCategoryNameByCategoryKeyFromXml(conn, categoryKey)+"</a><ul>";
				for(Category category: CategoryFactory.getOneLevelChildCategories(conn, categoryKey,marketId)){
					 ret+=CategoryHelper.getoneRecursiveHTML(conn, category.categoryKey,marketId);
				}
				ret+="</ul></li>";
				return ret;
			}
		}else{
			return "";
		}
	}
	
	public static String getoneRecursiveHTML1(Connection conn, String categoryKey, String marketId,String categoryValue) throws SQLException{
		if(categoryKey!=null && categoryKey.length()>0){
			if(CategoryFactory.getOneLevelChildCategories(conn, categoryKey,marketId).size()==0){
				return "<li><a href=\"##RELATIVE_PATH##Search.html?category="+categoryKey+"&test=correct&pricerange=1\">"+categoryValue+"</a></li>";
			}else{
				String ret = "<li><a href=\""+Constants.RELATIVE_PATH+"Search.html?category="+categoryKey+"&test=correct&pricerange=1\">"+categoryValue+"</a><ul>";
				for(Category category: CategoryFactory.getOneLevelChildCategories(conn, categoryKey,marketId)){
					 ret+=CategoryHelper.getoneRecursiveHTML(conn, category.categoryKey,marketId);
				}
				ret+="</ul></li>";
				return ret;
			}
		}else{
			return "";
		}
	}
	
	public static String getRecursiveHTML(Connection conn, String categoryKey) throws SQLException{
		if(categoryKey!=null && categoryKey.length()>0){
			if(CategoryFactory.getChildCategories(conn, categoryKey).size()==0){
				return "<li><a href=\"##RELATIVE_PATH##Search.html?category="+categoryKey+"&test=correct&pricerange=1\">"+CategoryFactory.getCategoryNameByCategoryKeyFromXml(conn, categoryKey)+"</a></li>";
			}else{
				String ret = "<li><a href=\""+Constants.RELATIVE_PATH+"Search.html?category="+categoryKey+"&test=correct&pricerange=1\">"+CategoryFactory.getCategoryNameByCategoryKeyFromXml(conn, categoryKey)+"</a><ul>";
				for(Category category: CategoryFactory.getChildCategories(conn, categoryKey)){
					 ret+=CategoryHelper.getRecursiveHTML(conn, category.categoryKey);
				}
				ret+="</ul></li>";
				return ret;
			}
		}else{
			return "";
		}
	}
	
	public static List<Category> getRootCategories(Connection conn, String marketId) throws SQLException{
		List<Category> categories = CategoryFactory.getCategories(conn, marketId);
		List<Category> rootCategories = new ArrayList<Category>();
		HashMap<String, String> map = new HashMap<String,String>();
		for(Category category:categories){
			if(category.categoryKey!=null && category.categoryKey.length()>=0){
				map.put(category.categoryKey, "#");
			}
		}
		for(Category cat:categories){
			if(null!=cat.categoryKey && cat.categoryKey.length()>=Constants.XML_CATEGORY_ROOT_KEY.length()){
				if(map.get(cat.categoryKey.substring(0, cat.categoryKey.length()-Constants.XML_CATEGORY_ROOT_KEY.length()))==null){
					rootCategories.add(cat);
				}
			}
		}
		return rootCategories; 
	}
}

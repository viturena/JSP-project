package com.catgen.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catgen.Constants;
import com.catgen.Product;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.ProductFactory;
import com.catgen.factories.XMLFactory;

public class FeaturedProductController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		String res="";
		String strSubModule = request.getParameter("submodule");
		String marketId = request.getParameter("marketid");
		String companyCode = request.getParameter("companycode");
		String productCode = request.getParameter("productcode");
		
		int subModule = 0;
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		switch(subModule){
		case Constants.REMOVE_FEATURED_PRODUCT:
			res = removeFeaturedProduct(conn, marketId, companyCode, productCode);
			break;
		case Constants.LIST_FEATURED_PRODUCTS:
			res = listFeaturedProducts(conn, companyCode);
			break;
		case Constants.FEATURE_PRODUCT:
			res = addFeaturedProduct(conn, marketId, companyCode, productCode);
			break;
		case Constants.LAZY_NM:
			res = flipLazyNMStatus(conn,marketId);
			break;
		}	
		return res;
	}
	
	public static String removeFeaturedProduct(Connection conn, String marketId, String companyCode, String productCode) throws SQLException{
		ProductFactory.removeFeaturedProduct(conn, marketId, companyCode, productCode);
		String res = XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("companycode", companyCode);
		res += XMLFactory.getMessageElement("productcode", productCode);
		return res;
	}
	
	public static String listFeaturedProducts(Connection conn, String companyCode) throws SQLException{
		List<Product> products = ProductFactory.getProducts(conn, companyCode);
		String pgCount = XMLFactory.getMessageElement("productcount", String.valueOf(products.size()));
		String res="";
		
		int ctr=1;
		for(Product product: products){
			String temp = XMLFactory.getMessageElement("name"+ctr, product.Name.replaceAll("&", "and"));
			temp += XMLFactory.getMessageElement("code"+ctr, product.Code);
			try {
				temp += XMLFactory.getMessageElement("imageurl"+ctr, product.ImageURL!=null?URLEncoder.encode(product.ImageURL,"UTF-8"):product.ImageURL);
			} catch (UnsupportedEncodingException e) {
			}
			temp = XMLFactory.getMessageElement("product"+ctr, temp);
			res += temp;
			ctr++;
		}
		return pgCount + res;
	}
	
	public static String addFeaturedProduct(Connection conn, String marketId, String companyCode, String productCode) throws SQLException{
		int rowId = ProductFactory.getMaxFeaturedProductRowId(conn, marketId)+1;
		ProductFactory.insertFeaturedProduct(conn, marketId, companyCode, productCode, rowId);
		String res = XMLFactory.getMessageElement("marketid", marketId);
		res += XMLFactory.getMessageElement("companycode", companyCode);
		res += XMLFactory.getMessageElement("companyname", CompanyFactory.getCompanyByCode(conn, companyCode).Name);
		res += XMLFactory.getMessageElement("productcode", productCode);
		res += XMLFactory.getMessageElement("productname", ProductFactory.getProductByCode(conn, companyCode, productCode).Name);
		return res;
	}
	
	public static String flipLazyNMStatus(Connection conn, String marketId) throws SQLException{
		NetMarketFactory.changeLazyStatus(conn, marketId);
		String res = XMLFactory.getMessageElement("marketId", marketId);
		res += XMLFactory.getMessageElement("lazy", NetMarketFactory.getNetworkMarketByCode(conn, marketId).lazy?"1":"0");
		return res;
	}
}

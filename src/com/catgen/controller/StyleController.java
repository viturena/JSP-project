package com.catgen.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catgen.Constants;
import com.catgen.Style;
import com.catgen.Utils;
import com.catgen.exception.SessionInactiveException;
import com.catgen.exception.StyleDataTypeFormatException;
import com.catgen.factories.StyleFactory;
import com.catgen.factories.XMLFactory;

import java.sql.*;

public class StyleController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NumberFormatException, StyleDataTypeFormatException, SessionInactiveException{
		String res="";
		String strSubModule = request.getParameter("submodule");
		
		String scrBgImg = request.getParameter("value_scrBgImg");
		String scrBgColor = request.getParameter("value_bgColor");
		String scrBgRpt = request.getParameter("radio_bgTileRpt");
		String pageBgImage = request.getParameter("value_pageBgImage");
		String pageBgColor = request.getParameter("value_pageBgColor");
		String pageBgRpt = request.getParameter("radio_pageBgRpt");
		String pageFont = request.getParameter("value_pageFont");
		String pageFontColor = request.getParameter("value_pageFontColor");
		String _pageFontSize = request.getParameter("value_pageFontSize");
		String titlebarBgImage = request.getParameter("value_titlebarBgImage");
		String titlebarBgColor = request.getParameter("value_titlebarBgColor");
		String titlebarBgRepeat = request.getParameter("radio_titlebarBgRepeat");
		String titlebarFont = request.getParameter("value_titlebarFont");
		String titlebarFontColor = request.getParameter("value_titlebarFontColor");
		String _titlebarFontSize = request.getParameter("value_titlebarFontSize");
		String navbarBgImage = request.getParameter("value_navbarBgImage");
		String navbarBgColor = request.getParameter("value_navbarBgColor");
		String navbarBgRepeat = request.getParameter("radio_navbarBgRepeat");
		String navbarFont = request.getParameter("value_navbarFont");
		String navbarFontColor = request.getParameter("value_navbarFontColor");
		String _navbarFontSize = request.getParameter("value_navbarFontSize");
		String textHome = request.getParameter("value_textHome");
		String textVendors = request.getParameter("value_textVendors");
		String textPages = request.getParameter("value_textPages");
		String textFeatured = request.getParameter("value_textFeatured");
		String textProducts = request.getParameter("value_textProducts");
		String textCompanies = request.getParameter("value_textCompanies");
		String textCountry = request.getParameter("value_textCountry");
		String textSearchResult = request.getParameter("value_textSearchResult");
		String textCategory = request.getParameter("value_textCategory");
		String textKeyword = request.getParameter("value_textKeyword");
		String textNotFound = request.getParameter("value_textNotFound");
		String _searchCountry = request.getParameter("radio_searchCountry");
		String _searchCategory = request.getParameter("radio_searchCategory");
		String _searchPrice = request.getParameter("radio_searchPrice");
		String defaultSearchText = request.getParameter("value_defaultSearchText");
		String searchImageButtonURL = request.getParameter("value_searchImageButtonURL");
		String _rowCount = request.getParameter("value_rowCount");
		String _columnCount = request.getParameter("value_columnCount");
		String _thumbnailWidth = request.getParameter("value_thumbnailWidth");
		String _thumbnailHeight = request.getParameter("value_thumbnailHeight");
		String thumbnailBgColor = request.getParameter("value_thumbnailBgColor");
		String thumbnailTextColor = request.getParameter("value_thumbnailTextColor");
		String orderButtomImageURL = request.getParameter("value_orderButtomImageURL");
		String featuredVendorTrustSeal = request.getParameter("value_featuredVendorTrustSeal");
		String noProductImage = request.getParameter("value_noProductImage");
		String includeCategorySidebar = request.getParameter("radio_includeCategorySidebar");
		String categoryBgImage = request.getParameter("value_categoryBgImage");
		String categoryBgColor = request.getParameter("value_categoryBgColor");
		String categoryBgImgRepeat = request.getParameter("radio_categoryBgImgRepeat");
		String categoryTitleBgImage = request.getParameter("value_categoryTitleBgImage");
		String categoryTitleBgColor = request.getParameter("value_categoryTitleBgColor");
		String categoryTitleBgImageRepeat = request.getParameter("radio_categoryTitleBgImageRepeat");
		String categoryTitleFont = request.getParameter("value_categoryTitleFont");
		String categoryTitleFontColor = request.getParameter("value_categoryTitleFontColor");
		String _categoryTitleFontSize = request.getParameter("value_categoryTitleFontSize");
		String categoryListBgImage = request.getParameter("value_categoryListBgImage");
		String categoryListBgColor = request.getParameter("value_categoryListBgColor");
		String categoryListBgImageRepeat = request.getParameter("radio_categoryListBgImageRepeat");
		String categoryListFont = request.getParameter("value_categoryListFont");
		String categoryListFontColor = request.getParameter("value_categoryListFontColor");
		String _categoryListFontSize = request.getParameter("value_categoryListFontSize");
		String categoryHoverBgImage = request.getParameter("value_categoryHoverBgImage");
		String categoryHoverBgColor = request.getParameter("value_categoryHoverBgColor");
		String categoryHoverBgImageRepeat = request.getParameter("radio_categoryHoverBgImageRepeat");
		String categoryHoverFont = request.getParameter("value_categoryHoverFont");
		String categoryHoverFontColor = request.getParameter("value_categoryHoverFontColor");
		String _categoryHoverFontSize = request.getParameter("value_categoryHoverFontSize");
		
		Style style = (Style)request.getSession().getAttribute("STYLE_OBJ");
		
		if(style==null){
			throw new SessionInactiveException();
		}
		
		if(scrBgImg!=null)
			style.screenBackgroundImage=scrBgImg;

		if(scrBgColor!=null)
			style.screenBackgroundColor="#"+scrBgColor;

		if(scrBgRpt!=null)
			style.screenBackgroundRepeat=scrBgRpt;

		if(pageBgImage!=null)
			style.pageBackgroundImage=pageBgImage;

		if(pageBgColor!=null)
			style.pageBackgroundColor="#"+pageBgColor;

		if(pageBgRpt!=null)
			style.pageBackgroundRepeat=pageBgRpt;

		if(pageFont!=null)
			style.pageFont=pageFont;

		if(pageFontColor!=null)
			style.pageFontColor="#"+pageFontColor;

		if(_pageFontSize!=null)
			style.pageFontSize=Utils.convert2Int(_pageFontSize,"Page Font Size");

		if(titlebarBgImage!=null)
			style.titlebarBackgroundImage=titlebarBgImage;

		if(titlebarBgColor!=null)
			style.titlebarBackgroundColor="#"+titlebarBgColor;

		if(titlebarBgRepeat!=null)
			style.titlebarBackgroundRepeat=titlebarBgRepeat;

		if(titlebarFont!=null)
			style.titlebarFont=titlebarFont;

		if(titlebarFontColor!=null)
			style.titlebarFontColor="#"+titlebarFontColor;

		if(_titlebarFontSize!=null)
			style.titlebarFontSize=Utils.convert2Int(_titlebarFontSize,"Titlebar font size");

		if(navbarBgImage!=null)
			style.navbarBackgroundImage=navbarBgImage;

		if(navbarBgColor!=null)
			style.navbarBackgroundColor="#"+navbarBgColor;

		if(navbarBgRepeat!=null)
			style.navbarBackgroundRepeat=navbarBgRepeat;

		if(navbarFont!=null)
			style.navbarFont=navbarFont;

		if(navbarFontColor!=null)
			style.navbarFontColor="#"+navbarFontColor;

		if(_navbarFontSize!=null)
			style.navbarFontSize=Utils.convert2Int(_navbarFontSize,"Navbar font size");

		if(textHome!=null)
			style.homeText=textHome;

		if(textVendors!=null)
			style.vendorsText=textVendors;

		if(textPages!=null)
			style.pagesText=textPages;

		if(textFeatured!=null)
			style.featuredText=textFeatured;

		if(textProducts!=null)
			style.productsText=textProducts;

		if(textCompanies!=null)
			style.companiesText=textCompanies;

		if(textCountry!=null)
			style.countryText=textCountry;

		if(textSearchResult!=null)
			style.searchResultText=textSearchResult;

		if(textCategory!=null)
			style.categoryText=textCategory;

		if(textKeyword!=null)
			style.keywordText=textKeyword;

		if(textNotFound!=null)
			style.notFoundText=textNotFound;

		if(_searchCountry!=null)
			style.includeCountrySearch=Utils.convert2Boolean(_searchCountry,"Include country search");

		if(_searchCategory!=null)
			style.includeCategorySearch=Utils.convert2Boolean(_searchCategory,"Include category search");

		if(_searchPrice!=null)
			style.includePriceSearch=Utils.convert2Boolean(_searchPrice,"Include price search");

		if(defaultSearchText!=null)
			style.defaultSearchText=defaultSearchText;

		if(searchImageButtonURL!=null)
			style.searchButtonImageURL=searchImageButtonURL;

		if(_rowCount!=null)
			style.productSearchRowCount=Utils.convert2Int(_rowCount,"Row count");

		if(_columnCount!=null)
			style.productSearchColumnCount=Utils.convert2Int(_columnCount,"Column Count");

		if(_thumbnailWidth!=null)
			style.thumbnailWidthInPixels=Utils.convert2Int(_thumbnailWidth,"Thumbnail width");

		if(_thumbnailHeight!=null)
			style.thumbnailHeightInPixels=Utils.convert2Int(_thumbnailHeight,"Thumbnail height");

		if(thumbnailBgColor!=null)
			style.thumbnailBackgroundColor="#"+thumbnailBgColor;

		if(thumbnailTextColor!=null)
			style.thumbnailTextColor="#"+thumbnailTextColor;

		if(orderButtomImageURL!=null)
			style.orderButtonImageURL=orderButtomImageURL;

		if(featuredVendorTrustSeal!=null)
			style.trustSealImageURL=featuredVendorTrustSeal;

		if(noProductImage!=null)
			style.noProductImageURL=noProductImage;

		if(includeCategorySidebar!=null)
			style.includeCategoryList=includeCategorySidebar;

		if(categoryBgImage!=null)
			style.categoryBackgroundImage=categoryBgImage;

		if(categoryBgColor!=null)
			style.categoryBackgroundColor="#"+categoryBgColor;

		if(categoryBgImgRepeat!=null)
			style.categoryBackgroundRepeat=categoryBgImgRepeat;

		if(categoryTitleBgImage!=null)
			style.categoryTitleBackgroundImage=categoryTitleBgImage;

		if(categoryTitleBgColor!=null)
			style.categoryTitleBackgroundColor="#"+categoryTitleBgColor;

		if(categoryTitleBgImageRepeat!=null)
			style.categoryTitleBackgroundRepeat=categoryTitleBgImageRepeat;

		if(categoryTitleFont!=null)
			style.categoryTitleFont=categoryTitleFont;

		if(categoryTitleFontColor!=null)
			style.categoryTitleFontColor="#"+categoryTitleFontColor;

		if(_categoryTitleFontSize!=null)
			style.categoryTitleFontSize=Utils.convert2Int(_categoryTitleFontSize,"Category title font size");

		if(categoryListBgImage!=null)
			style.categoryListBackgroundImage=categoryListBgImage;

		if(categoryListBgColor!=null)
			style.categoryListBackgroundColor="#"+categoryListBgColor;

		if(categoryListBgImageRepeat!=null)
			style.categoryListBackgroundRepeat=categoryListBgImageRepeat;

		if(categoryListFont!=null)
			style.categoryListFont=categoryListFont;

		if(categoryListFontColor!=null)
			style.categoryListFontColor="#"+categoryListFontColor;

		if(_categoryListFontSize!=null)
			style.categoryListFontSize=Utils.convert2Int(_categoryListFontSize,"Category list font size");

		if(categoryHoverBgImage!=null)
			style.categoryHoverBackgroundImage=categoryHoverBgImage;

		if(categoryHoverBgColor!=null)
			style.categoryHoverBackgroundColor="#"+categoryHoverBgColor;

		if(categoryHoverBgImageRepeat!=null)
			style.categoryHoverBackgroundRepeat=categoryHoverBgImageRepeat;

		if(categoryHoverFont!=null)
			style.categoryHoverFont=categoryHoverFont;

		if(categoryHoverFontColor!=null)
			style.categoryHoverFontColor="#"+categoryHoverFontColor;

		if(_categoryHoverFontSize!=null)
			style.categoryHoverFontSize=Utils.convert2Int(_categoryHoverFontSize,"Category hover font size");

		int subModule = Integer.parseInt(strSubModule);
		
		switch(subModule){
		case Constants.UPDATE_STYLE:
			res = updateStyle(conn, style);
			break;
		}	
		return res;
	}
	
	public static String updateStyle(Connection conn, Style style) throws SQLException{
		StyleFactory.remove(conn, style.marketId);
		StyleFactory.save(conn, style);
		return(XMLFactory.getMessageElement("message", "Style updated successfully"));
	}
}

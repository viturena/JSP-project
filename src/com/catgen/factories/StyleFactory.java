package com.catgen.factories;
// Style Incorporation to Template change Feb2010 - NEW FILE

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.catgen.Constants;
import com.catgen.Style;

public class StyleFactory {

	public static void save(Connection conn, Style style) throws SQLException{
		String query = "insert into Style("+
		"marketid,"+
		"ibScr,"+
		"cbScr,"+
		"rbScr,"+
		"fPage,"+
		"cPage,"+
		"sPage,"+
		"ibPage,"+
		"cbPage,"+
		"rbPage,"+
		"fTitle,"+
		"cTitle,"+
		"sTitle,"+
		"ibTitle,"+
		"cbTitle,"+
		"rbTitle,"+
		"fNav,"+
		"cNav,"+
		"sNav,"+
		"ibNav,"+
		"cbNav,"+
		"rbNav,"+
		"tHome,"+
		"tVendors,"+
		"tPages,"+
		"tFeatured,"+
		"tProducts,"+
		"tCompanies,"+
		"tCountry,"+
		"tSearchResult,"+
		"tCategory,"+
		"tKeyword,"+
		"tNotFound,"+
		"incCountry,"+
		"incCategory,"+
		"incPrice,"+
		"tEnterKeyword,"+
		"searchBtnUrl,"+
		"prodRowCount,"+
		"prodColCount,"+
		"tnWidth,"+
		"tnHeight,"+
		"tnBgColor,"+
		"tnTxtColor,"+
		"orderImgUrl,"+
		"trustSealImgUrl,"+
		"noProdImgUrl,"+
		"ibCat,"+
		"cbCat,"+
		"rbCat,"+
		"fCategoryTitle,"+
		"cCategoryTitle,"+
		"sCategoryTitle,"+
		"ibCategoryTitle,"+
		"cbCategoryTitle,"+
		"rbCategoryTitle,"+
		"fCategoryList,"+
		"cCategoryList,"+
		"sCategoryList,"+
		"ibCategoryList,"+
		"cbCategoryList,"+
		"rbCategoryList,"+
		"fCategoryHover,"+
		"cCategoryHover,"+
		"sCategoryHover,"+
		"ibCategoryHover,"+
		"cbCategoryHover,"+
		"rbCategoryHover,"+
		"incCategoryList) values"+
		"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt=conn.prepareStatement(query);
		try{
			pstmt.setString(1, style.marketId);
			pstmt.setString(2, style.screenBackgroundImage);
			pstmt.setString(3, style.screenBackgroundColor);
			pstmt.setString(4, style.screenBackgroundRepeat);
			pstmt.setString(5, style.pageFont);
			pstmt.setString(6, style.pageFontColor);
			pstmt.setInt(7, style.pageFontSize);
			pstmt.setString(8, style.pageBackgroundImage);
			pstmt.setString(9, style.pageBackgroundColor);
			pstmt.setString(10, style.pageBackgroundRepeat);
			pstmt.setString(11, style.titlebarFont);
			pstmt.setString(12, style.titlebarFontColor);
			pstmt.setInt(13, style.titlebarFontSize);
			pstmt.setString(14, style.titlebarBackgroundImage);
			pstmt.setString(15, style.titlebarBackgroundColor);
			pstmt.setString(16, style.titlebarBackgroundRepeat);
			pstmt.setString(17, style.navbarFont);
			pstmt.setString(18, style.navbarFontColor);
			pstmt.setInt(19, style.navbarFontSize);
			pstmt.setString(20, style.navbarBackgroundImage);
			pstmt.setString(21, style.navbarBackgroundColor);
			pstmt.setString(22, style.navbarBackgroundRepeat);
			pstmt.setString(23, style.homeText);
			pstmt.setString(24, style.vendorsText);
			pstmt.setString(25, style.pagesText);
			pstmt.setString(26, style.featuredText);
			pstmt.setString(27, style.productsText);
			pstmt.setString(28, style.companiesText);
			pstmt.setString(29, style.countryText);
			pstmt.setString(30, style.searchResultText);
			pstmt.setString(31, style.categoryText);
			pstmt.setString(32, style.keywordText);
			pstmt.setString(33, style.notFoundText);
			pstmt.setString(34, (style.includeCountrySearch)?"y":"n");
			pstmt.setString(35, (style.includeCategorySearch)?"y":"n");
			pstmt.setString(36, (style.includePriceSearch)?"y":"n");
			pstmt.setString(37, style.defaultSearchText);
			pstmt.setString(38, style.searchButtonImageURL);
			pstmt.setInt(39, style.productSearchRowCount);
			pstmt.setInt(40, style.productSearchColumnCount);
			pstmt.setInt(41, style.thumbnailWidthInPixels);
			pstmt.setInt(42, style.thumbnailHeightInPixels);
			pstmt.setString(43, style.thumbnailBackgroundColor);
			pstmt.setString(44, style.thumbnailTextColor);
			pstmt.setString(45, style.orderButtonImageURL);
			pstmt.setString(46, style.trustSealImageURL);
			pstmt.setString(47, style.noProductImageURL);
			pstmt.setString(48, style.categoryBackgroundImage);
			pstmt.setString(49, style.categoryBackgroundColor);
			pstmt.setString(50, style.categoryBackgroundRepeat);
			pstmt.setString(51, style.categoryTitleFont);
			pstmt.setString(52, style.categoryTitleFontColor);
			pstmt.setInt(53, style.categoryTitleFontSize);
			pstmt.setString(54, style.categoryTitleBackgroundImage);
			pstmt.setString(55, style.categoryTitleBackgroundColor);
			pstmt.setString(56, style.categoryTitleBackgroundRepeat);
			pstmt.setString(57, style.categoryListFont);
			pstmt.setString(58, style.categoryListFontColor);
			pstmt.setInt(59, style.categoryListFontSize);
			pstmt.setString(60, style.categoryListBackgroundImage);
			pstmt.setString(61, style.categoryListBackgroundColor);
			pstmt.setString(62, style.categoryListBackgroundRepeat);
			pstmt.setString(63, style.categoryHoverFont);
			pstmt.setString(64, style.categoryHoverFontColor);
			pstmt.setInt(65, style.categoryHoverFontSize);
			pstmt.setString(66, style.categoryHoverBackgroundImage);
			pstmt.setString(67, style.categoryHoverBackgroundColor);
			pstmt.setString(68, style.categoryHoverBackgroundRepeat);
			pstmt.setString(69, style.includeCategoryList);
			
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}
	
	public static void remove(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("DELETE from Style where marketid=?");
		try{
			pstmt.setString(1, marketId);
			pstmt.execute();
		}finally{
			pstmt.close();
		}
	}

	public static Style getStyle(Connection conn, String marketId) throws SQLException {
        Style style = new Style();
        PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM Style WHERE MarketID = ?");
        try {
            pstmt.setString(1, marketId);
            ResultSet rs = pstmt.executeQuery();
            try {
                if (rs.next()) {
                    LoadStyleFromResultSet(rs, style);
                }
            } finally {
                rs.close();
            }
        } finally {
            pstmt.close();
        }
        return style;
    }

    public static void LoadStyleFromResultSet(ResultSet rs, Style style) throws SQLException {
    	style.marketId = rs.getString("marketId");
        style.screenBackgroundImage = rs.getString("ibScr");
        style.screenBackgroundColor = getBgColor(rs.getString("cbScr"),Constants.DEFAULT_BG_COLOR_BODY);
        style.screenBackgroundRepeat = rs.getString("rbScr");

        style.pageFont = getFontFamily(rs.getString("fPage"));
        style.pageFontColor = getFontColor(rs.getString("cPage"),Constants.DEFAULT_FONT_COLOR_PAGE);
        style.pageFontSize = getFontSize(Integer.parseInt((rs.getString("sPage")!=null)?rs.getString("sPage"):"0"));
        style.pageBackgroundImage = rs.getString("ibPage");
        style.pageBackgroundColor = getBgColor(rs.getString("cbPage"),Constants.DEFAULT_BG_COLOR_PAGE);
        style.pageBackgroundRepeat = rs.getString("rbPage");

        style.titlebarFont = getFontFamily(rs.getString("fTitle"));
        style.titlebarFontColor = getFontColor(rs.getString("cTitle"),Constants.DEFAULT_FONT_COLOR_TITLE);
        style.titlebarFontSize = getFontSize(Integer.parseInt((rs.getString("sTitle")!=null)?rs.getString("sTitle"):"0"));
        style.titlebarBackgroundImage = rs.getString("ibTitle");
        style.titlebarBackgroundColor = getBgColor(rs.getString("cbTitle"),Constants.DEFAULT_BG_COLOR_TITLE);
        style.titlebarBackgroundRepeat = rs.getString("rbTitle");

        style.navbarFont = getFontFamily(rs.getString("fNav"));
        style.navbarFontColor = getFontColor(rs.getString("cNav"),Constants.DEFAULT_FONT_COLOR_NAVBAR);
        style.navbarFontSize = getFontSize(Integer.parseInt((rs.getString("sNav")!=null)?rs.getString("sNav"):"0"));
        style.navbarBackgroundImage = rs.getString("ibNav");
        style.navbarBackgroundColor = getBgColor(rs.getString("cbNav"),Constants.DEFAULT_BG_COLOR_NAVBAR);
        style.navbarBackgroundRepeat = rs.getString("rbNav");

        // Template additional enhancement change Feb2010 - Begin
        style.homeText = (rs.getString("tHome") != null) ? rs.getString("tHome") : "";
        style.vendorsText = (rs.getString("tVendors") != null) ? rs.getString("tVendors") : "Vendors";
        style.pagesText = (rs.getString("tPages") != null) ? rs.getString("tPages") : "Pages";
        style.featuredText = (rs.getString("tFeatured") != null) ? rs.getString("tFeatured") : "Featured";
        style.productsText = (rs.getString("tProducts") != null) ? rs.getString("tProducts") : "Products";
        style.companiesText = (rs.getString("tCompanies") != null) ? rs.getString("tCompanies") : "Companies";
        style.countryText = (rs.getString("tCountry") != null) ? rs.getString("tCountry") : "Country";
        style.searchResultText = (rs.getString("tSearchResult") != null) ? rs.getString("tSearchResult") : "Search Result";
        style.categoryText = (rs.getString("tCategory") != null) ? rs.getString("tCategory") : "Category";
        style.keywordText = (rs.getString("tKeyword") != null) ? rs.getString("tKeyword") : "Keyword";
        style.notFoundText = (rs.getString("tNotFound") != null) ? rs.getString("tNotFound") : "Not Found";

        style.includeCountrySearch = (rs.getString("incCountry") != null && ("n".equalsIgnoreCase(rs.getString("incCountry").trim()))) ? false : true;
        style.includeCategorySearch = (rs.getString("incCategory") != null && ("n".equalsIgnoreCase(rs.getString("incCategory").trim()))) ? false : true;
        style.includePriceSearch = (rs.getString("incPrice") != null && ("n".equalsIgnoreCase(rs.getString("incPrice").trim()))) ? false : true;

        style.defaultSearchText = (rs.getString("tEnterKeyword") != null) ? rs.getString("tEnterKeyword") : "Enter Keyword";
        style.searchButtonImageURL = (rs.getString("searchBtnUrl") != null) ? rs.getString("searchBtnUrl") : "/images/common/search_btn.png";
        style.productSearchRowCount = (rs.getInt("prodRowCount") > 0) ? rs.getInt("prodRowCount") : 12;
        style.productSearchColumnCount = (rs.getInt("prodColCount") > 0) ? rs.getInt("prodColCount") : 8;
        style.thumbnailWidthInPixels = (rs.getInt("tnWidth") > 0 ) ? rs.getInt("tnWidth") : 100;
        style.thumbnailHeightInPixels = (rs.getInt("tnHeight") > 0 ) ?rs.getInt("tnHeight") : 100;

        style.thumbnailBackgroundColor = getBgColor(rs.getString("tnBgColor"),"#EEE");
        style.thumbnailTextColor = getFontColor(rs.getString("tnTxtColor"),"#666");
        style.orderButtonImageURL = (rs.getString("orderImgUrl") != null) ? rs.getString("orderImgUrl") : "/images/common/order.png";
        style.trustSealImageURL = (rs.getString("trustSealImgUrl") != null) ? rs.getString("trustSealImgUrl") : "/images/common/featured.gif";
        style.noProductImageURL = (rs.getString("noProdImgUrl") != null) ? rs.getString("noProdImgUrl") : "/images/common/notfound.gif";
        // Template additional enhancement change Feb2010 - End

        // Category Property Addition Change : March 2010 - Begin	
        style.categoryBackgroundImage = rs.getString("ibCat");
        style.categoryBackgroundColor = getBgColor(rs.getString("cbCat"),Constants.DEFAULT_BG_COLOR_CATEGORY);
        style.categoryBackgroundRepeat = rs.getString("rbCat");

        style.categoryTitleFont = getFontFamily(rs.getString("fCategoryTitle"));
        style.categoryTitleFontColor = getFontColor(rs.getString("cCategoryTitle"),Constants.DEFAULT_FONT_COLOR_CATEGORYTITLE);
        style.categoryTitleFontSize = getFontSize(Integer.parseInt((rs.getString("sCategoryTitle")!=null)?rs.getString("sCategoryTitle"):"0"));
        style.categoryTitleBackgroundImage = rs.getString("ibCategoryTitle");
        style.categoryTitleBackgroundColor = getBgColor(rs.getString("cbCategoryTitle"),Constants.DEFAULT_BG_COLOR_CATEGORYTITLE);
        style.categoryTitleBackgroundRepeat = rs.getString("rbCategoryTitle");

        style.categoryListFont = getFontFamily(rs.getString("fCategoryList"));
        style.categoryListFontColor = getFontColor(rs.getString("cCategoryList"),Constants.DEFAULT_FONT_COLOR_CATEGORYLIST);
        style.categoryListFontSize = getFontSize(Integer.parseInt((rs.getString("sCategoryList")!=null)?rs.getString("sCategoryList"):"0"));
        style.categoryListBackgroundImage = rs.getString("ibCategoryList");
        style.categoryListBackgroundColor = getBgColor(rs.getString("cbCategoryList"),Constants.DEFAULT_BG_COLOR_CATEGORYLIST);
        style.categoryListBackgroundRepeat = rs.getString("rbCategoryList");

        style.categoryHoverFont = getFontFamily(rs.getString("fCategoryHover"));
        style.categoryHoverFontColor = getFontColor(rs.getString("cCategoryHover"),Constants.DEFAULT_FONT_COLOR_HOVERLIST);
        style.categoryHoverFontSize = getFontSize(Integer.parseInt((rs.getString("sCategoryHover")!=null)?rs.getString("sCategoryHover"):"0"));
        style.categoryHoverBackgroundImage = rs.getString("ibCategoryHover");
        style.categoryHoverBackgroundColor = getBgColor(rs.getString("cbCategoryHover"),Constants.DEFAULT_BG_COLOR_HOVERLIST);
        style.categoryHoverBackgroundRepeat = rs.getString("rbCategoryHover");

        style.includeCategoryList = rs.getString("incCategoryList");
    }

    public static String getBackgroundStyle(String image, String color, String repeat, String defBgColor) {
        String styleStr = "";
        if (image != null && image.length() > 0) {
            styleStr = "background-image:url(" + image + "); background-repeat:";
            if(repeat.equalsIgnoreCase(Style.REPEAT_XX))
            	styleStr += "repeat-x; ";
            else if(repeat.equalsIgnoreCase(Style.REPEAT_YY))
            	styleStr += "repeat-y; ";
            else if(repeat.equalsIgnoreCase(Style.REPEAT_XY))
                styleStr += "repeat; ";
            else
            	styleStr += "no-repeat; ";
        } else{
        	styleStr = "background:" + getBgColor(color, defBgColor) + "; ";
        }
        return styleStr;
    }
    
    public static String getBgColor(String color, String defBgColor){
    	if (color != null && color.length() > 0) {
            return color;
    	}else{
    		return defBgColor;
    	}
    }

    public static String getFontStyle(String fontFamily, int fontSize, String fontColor, String defFontColor) {
        String fontStyle;
        fontFamily = getFontFamily(fontFamily);
        fontSize = getFontSize(fontSize);
        fontColor = getFontColor(fontColor, defFontColor);
        fontStyle = "font-family: " + fontFamily + "; font-size:" + fontSize + "px; color:" + fontColor + ";";
        return fontStyle;
    }
    
    public static String getFontFamily(String fontFamily){
    	return (fontFamily != null && fontFamily.length() > 0) ? fontFamily : "Arial";
    }
    
    public static int getFontSize(int fontSize){
    	return (fontSize > 0) ? fontSize : 10;
    }
    
    public static String getFontColor(String fontColor, String defFontColor){
    	return (fontColor != null && fontColor.length() > 0) ? fontColor : defFontColor;
    }
    
    public static void removeNM(Connection conn, String marketId) throws SQLException{
		PreparedStatement pstmt = conn.prepareStatement("delete from Style where marketid = ?");
		
		pstmt.setString(1,marketId);

		pstmt.executeUpdate();
	}
}

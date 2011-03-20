package com.catgen;
//Style Incorporation to Template change Feb2010 - NEW FILE

public class Style {

	public static final String REPEAT_XX = "xx";
	public static final String REPEAT_YY = "yy";
	public static final String REPEAT_XY = "xy";
	public static final String NONE      = "no";

	public static final String NO_CATEGORY = "none";
	public static final String LEFT_CATEGORY = "left";
	public static final String RIGHT_CATEGORY = "right";
	
	public String marketId;
	public String screenBackgroundImage;
	public String screenBackgroundColor;
	public String screenBackgroundRepeat;

	public String pageFont;
	public String pageFontColor;
	public int pageFontSize;
	public String pageBackgroundImage;
	public String pageBackgroundColor;
	public String pageBackgroundRepeat;

	public String titlebarFont;
	public String titlebarFontColor;
	public int titlebarFontSize;
	public String titlebarBackgroundImage;
	public String titlebarBackgroundColor;
	public String titlebarBackgroundRepeat;

	public String navbarFont;
	public String navbarFontColor;
	public int navbarFontSize;
	public String navbarBackgroundImage;
	public String navbarBackgroundColor;
	public String    navbarBackgroundRepeat;
	
	// Template additional enhancement change Feb2010 - Begin
	public String homeText;
	public String vendorsText;
	public String pagesText;
	public String featuredText;
	public String productsText;
	public String companiesText;
	public String countryText;
	public String searchResultText;
	public String categoryText;
	public String keywordText;
	public String notFoundText;
	
	public boolean includeCountrySearch;
	public boolean includeCategorySearch;
	public boolean includePriceSearch;
	
	public String defaultSearchText;
	public String searchButtonImageURL;
	public int productSearchRowCount;
	public int productSearchColumnCount;
	public int thumbnailWidthInPixels;
	public int thumbnailHeightInPixels;
	public String thumbnailBackgroundColor;
	public String thumbnailTextColor;
	public String orderButtonImageURL;
	public String trustSealImageURL;
	public String noProductImageURL;
	// Template additional enhancement change Feb2010 - End

	// Category Property Addition Change : March 2010 - Begin
	public String categoryBackgroundImage;
	public String categoryBackgroundColor;
	public String categoryBackgroundRepeat;

	public String categoryTitleFont;
	public String categoryTitleFontColor;
	public int categoryTitleFontSize;
	public String categoryTitleBackgroundImage;
	public String categoryTitleBackgroundColor;
	public String categoryTitleBackgroundRepeat;

	public String categoryListFont;
	public String categoryListFontColor;
	public int categoryListFontSize;
	public String categoryListBackgroundImage;
	public String categoryListBackgroundColor;
	public String categoryListBackgroundRepeat;

    public String categoryHoverFont;
	public String categoryHoverFontColor;
	public int categoryHoverFontSize;
	public String categoryHoverBackgroundImage;
	public String categoryHoverBackgroundColor;
	public String categoryHoverBackgroundRepeat;

    public String includeCategoryList;
    // Category Property Addition Change : March 2010 - End
}

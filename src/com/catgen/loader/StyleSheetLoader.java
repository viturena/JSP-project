package com.catgen.loader;
//Template Stylesheet Change Feb2010
public class StyleSheetLoader extends DataLoader {
	
	public StyleSheetLoader()
	{
		super();
		
		tableName = "Style";
		IsRowData = false;
		IncludeRowID = false;
		
		dbColumn.put("marketid", new DataDefinition( "marketid", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("marketid", new DataDefinition( "market id", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		
		dbColumn.put("screen background image", new DataDefinition( "ibScr", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("screen background color", new DataDefinition( "cbScr", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("screen background image repeat", new DataDefinition( "rbScr", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		
		dbColumn.put("page general font", new DataDefinition( "fPage", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("page general font color", new DataDefinition( "cPage", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("page general font size", new DataDefinition( "sPage", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("page background image", new DataDefinition( "ibPage", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("page background color", new DataDefinition( "cbPage", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("page background image repeat", new DataDefinition( "rbPage", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		
		dbColumn.put("title bar font", new DataDefinition( "fTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("title bar font color", new DataDefinition( "cTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("title bar font size", new DataDefinition( "sTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("title bar background image", new DataDefinition( "ibTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("title bar background color", new DataDefinition( "cbTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("title bar image repeat", new DataDefinition( "rbTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		
		dbColumn.put("navbar font", new DataDefinition( "fNav", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("navbar font color", new DataDefinition( "cNav", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("navbar font size", new DataDefinition( "sNav", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("navbar background image", new DataDefinition( "ibNav", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("navbar background color", new DataDefinition( "cbNav", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("navbar background image repeat", new DataDefinition( "rbNav", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		
		// Template additional enhancement change Feb2010 - Begin
		dbColumn.put("customized text for home", new DataDefinition( "tHome", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for vendors", new DataDefinition( "tVendors", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for pages", new DataDefinition( "tPages", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for featured", new DataDefinition( "tFeatured", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for products", new DataDefinition( "tProducts", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for companies", new DataDefinition( "tCompanies", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for country", new DataDefinition( "tCountry", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for search result", new DataDefinition( "tSearchResult", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for category", new DataDefinition( "tCategory", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for keyword", new DataDefinition( "tKeyword", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("customized text for not found", new DataDefinition( "tNotFound", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("include country search", new DataDefinition( "incCountry", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("include category search", new DataDefinition( "incCategory", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("include price search", new DataDefinition( "incPrice", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("default search text", new DataDefinition( "tEnterKeyword", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("search button image url", new DataDefinition( "searchBtnUrl", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("product search rowcount", new DataDefinition( "prodRowCount", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("product search columncount", new DataDefinition( "prodColCount", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("thumbnail width in pixels", new DataDefinition( "tnWidth", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("thumbnail height in pixels", new DataDefinition( "tnHeight", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("thumbnail background color", new DataDefinition( "tnBgColor", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("thumbnail text color", new DataDefinition( "tnTxtColor", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("order button image url", new DataDefinition( "orderImgUrl", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("trustseal image url for featured products", new DataDefinition( "trustSealImgUrl", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		dbColumn.put("no product' image url", new DataDefinition( "noProdImgUrl", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
		// Template additional enhancement change Feb2010 - End

		// Category Property Addition Change : March 2010 - Begin
        dbColumn.put("category background image", new DataDefinition( "ibCat", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category background color", new DataDefinition( "cbCat", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category background image repeat", new DataDefinition( "rbCat", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category title font", new DataDefinition( "fCategoryTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category title font color", new DataDefinition( "cCategoryTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category title font size", new DataDefinition( "sCategoryTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category title background image", new DataDefinition( "ibCategoryTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category title background color", new DataDefinition( "cbCategoryTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category title background image repeat", new DataDefinition( "rbCategoryTitle", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category list font", new DataDefinition( "fCategoryList", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category list font color", new DataDefinition( "cCategoryList", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category list font size", new DataDefinition( "sCategoryList", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category list background image", new DataDefinition( "ibCategoryList", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category list background color", new DataDefinition( "cbCategoryList", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category list background image repeat", new DataDefinition( "rbCategoryList", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category hover font", new DataDefinition( "fCategoryHover", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category hover font color", new DataDefinition( "cCategoryHover", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category hover font size", new DataDefinition( "sCategoryHover", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category hover background image", new DataDefinition( "ibCategoryHover", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category hover background color", new DataDefinition( "cbCategoryHover", DataDefinition.STRING, DataDefinition.LOADER_TRIM));
        dbColumn.put("category hover background image repeat", new DataDefinition( "rbCategoryHover", DataDefinition.STRING, DataDefinition.LOADER_TRIM));

        dbColumn.put("include category list", new DataDefinition( "incCategoryList", DataDefinition.STRING, DataDefinition.LOADER_TRIM));

        // Category Property Addition Change : March 2010 - Begin
	}
}

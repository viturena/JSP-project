package com.catgen;

import javax.servlet.jsp.PageContext;

public class CatgenPageContext {

	public NetworkMarket NetworkMarket;
	public Company Company;
	public Page Page;
	public Product Product;
	public ProductLine ProductLine;
	
	public String PageType;
	public String PageID;
	public String PageName;
	public String DefaultPageName;
	public String ProductID;
	public String ProductLineID;
	public String Search;
	public String searchCountry;
	public String searchCategory;
	public int priceRangeCode;
	public boolean singlePageTemplate; 
	public String Template;
	public String HostName;
	
	public int PageNumber;
	public int PageSize;
	
	public String OriginalReferralID;
	public String ReferralID;
	
	public String getKeyword()
	{
		String keywords = "";
		
		if(Product != null)
			keywords += " " + Product.Code + " " + Product.Name;

		if(ProductLine != null)
			keywords += " " + ProductLine.Name;
		
		return keywords;		
	}
	
	public String RelativePath;
	
	public static CatgenPageContext getInstance(PageContext pageContext)
	{
		CatgenPageContext catgenPageContext = (CatgenPageContext)pageContext.getRequest().getAttribute("catgenContext");
		
		if(catgenPageContext == null)
		{		
			catgenPageContext = new CatgenPageContext();

			catgenPageContext.NetworkMarket = (NetworkMarket)pageContext.getRequest().getAttribute("market");
			catgenPageContext.Company = (Company)pageContext.getRequest().getAttribute("company");
			catgenPageContext.Page = (Page)pageContext.getRequest().getAttribute("page");

			catgenPageContext.ProductID = pageContext.getRequest().getParameter("productid");
			catgenPageContext.PageID = pageContext.getRequest().getParameter("pageid");
			catgenPageContext.Search = pageContext.getRequest().getParameter("search");
			catgenPageContext.searchCountry = pageContext.getRequest().getParameter("searchCountry");
			catgenPageContext.searchCategory = pageContext.getRequest().getParameter("searchCategory");
			
			catgenPageContext.PageSize = Utils.StrToIntDef( pageContext.getRequest().getParameter("pagesize"), 0 );
			catgenPageContext.PageNumber = Utils.StrToIntDef( pageContext.getRequest().getParameter("pagenumber"), 0 );

			catgenPageContext.OriginalReferralID = pageContext.getRequest().getParameter("refid");
		
			if((catgenPageContext.OriginalReferralID == null || catgenPageContext.OriginalReferralID.trim().length() == 0) )
			{
				if(pageContext.getRequest() instanceof javax.servlet.http.HttpServletRequest)
					catgenPageContext.OriginalReferralID =  Utils.getCookieValue( "refid", (javax.servlet.http.HttpServletRequest)pageContext.getRequest());
			}
			
			catgenPageContext.RelativePath = pageContext.getRequest().getParameter("relativePath");
			if(catgenPageContext.RelativePath == null)
				catgenPageContext.RelativePath = "";
		}

		String networkMarketID = null;
		if(catgenPageContext.NetworkMarket != null)
			networkMarketID = catgenPageContext.NetworkMarket.NetworkMarketID;
		
		if(catgenPageContext.OriginalReferralID != null && (! catgenPageContext.OriginalReferralID.startsWith(networkMarketID)))
		{ 
			if( pageContext.getResponse() instanceof javax.servlet.http.HttpServletResponse)
				Utils.SetCookieValue("refid", catgenPageContext.OriginalReferralID, (javax.servlet.http.HttpServletResponse)pageContext.getResponse());
		}
		
		return catgenPageContext;
	}
}

<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*, org.apache.commons.lang.*" %><%
String userId = (String)session.getAttribute("USER");
String type = (String)session.getAttribute("TYPE");
boolean isValidNM = NetMarketFactory.isValidNM(userId, type);
if(!isValidNM){
	session.setAttribute("errcode","504");
}else{ 
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	String marketId = (String)session.getAttribute("USER");
	try{
		Style style = StyleFactory.getStyle(conn, marketId);
		session.setAttribute("STYLE_OBJ",style);%>
		<input type="hidden" id="module" value="<%=Constants.STYLE%>"></input>
		<input type="hidden" id="updateStyleValues" value="<%=Constants.UPDATE_STYLE%>"></input>
		<form id="styleFrame" name="styleFrame">
			<table width="100%">
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Screen Properties</b></td>
				</tr>
				<tr>
					<td>Background Image</td>
					<td><% 
						String scrBgImg = (style.screenBackgroundImage!=null)?StringEscapeUtils.escapeHtml(style.screenBackgroundImage):"";%>
						<input type="text" name="value_scrBgImg" value="<%=scrBgImg %>" size="32" maxlength="512"></input>
						<input type="hidden" name="hidden_value_scrBgImg" value="<%=scrBgImg %>"></input>
					</td>
				</tr>
				<tr>
					<td>Background Color</td>
					<td><%
						String scrBgColor = (style.screenBackgroundColor!=null)?StringEscapeUtils.escapeHtml(style.screenBackgroundColor):""; %>
						<input class="color" type="text" name="value_bgColor" value="<%=scrBgColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_bgColor" value="<%=scrBgColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Tile Direction</td>
					<td><%
						String scrBgRpt = style.screenBackgroundRepeat;%>
						X<input type="radio" name="radio_bgTileRpt" value="<%=Style.REPEAT_XX %>" <%=Utils.isChecked(scrBgRpt, Style.REPEAT_XX)%>/>&nbsp;&nbsp;&nbsp;
						Y<input type="radio" name="radio_bgTileRpt" value="<%=Style.REPEAT_YY %>" <%=Utils.isChecked(scrBgRpt, Style.REPEAT_YY)%>/>&nbsp;&nbsp;&nbsp;
						XY<input type="radio" name="radio_bgTileRpt" value="<%=Style.REPEAT_XY %>" <%=Utils.isChecked(scrBgRpt, Style.REPEAT_XY)%>/>&nbsp;&nbsp;&nbsp;
						NONE<input type="radio" name="radio_bgTileRpt" value="<%=Style.NONE %>" <%=Utils.isChecked(scrBgRpt, Style.NONE)%>/>&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="hidden_radio_bgTileRpt" value="<%=scrBgRpt%>"></input>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Page Properties</b></td>
				</tr>
				<tr>
					<td>Background Image</td>
					<td><%
						String pageBgImage = (style.pageBackgroundImage!=null)?StringEscapeUtils.escapeHtml(style.pageBackgroundImage):"";%>
						<input type="text" name="value_pageBgImage" value="<%=pageBgImage %>" size="32" maxlength="512"></input>
						<input type="hidden" name="hidden_value_pageBgImage" value="<%=pageBgImage%>"></input>
					</td>
				</tr>
				<tr>
					<td>Background Color</td>
					<td><%
						String pageBgColor = (style.pageBackgroundColor!=null)?StringEscapeUtils.escapeHtml(style.pageBackgroundColor):"";%>
						<input class="color" type="text" name="value_pageBgColor" value="<%=pageBgColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_pageBgColor" value="<%=pageBgColor%>"></input>
					</td>
				</tr>
				<tr>
					<td>Tile Direction</td>
					<td><%
						String pageBgRpt = style.pageBackgroundRepeat;%>
						X<input type="radio" name="radio_pageBgRpt" value="<%=Style.REPEAT_XX %>" <%=Utils.isChecked(pageBgRpt, Style.REPEAT_XX)%>/>&nbsp;&nbsp;&nbsp;
						Y<input type="radio" name="radio_pageBgRpt" value="<%=Style.REPEAT_YY %>" <%=Utils.isChecked(pageBgRpt, Style.REPEAT_YY)%>/>&nbsp;&nbsp;&nbsp;
						XY<input type="radio" name="radio_pageBgRpt" value="<%=Style.REPEAT_XY %>" <%=Utils.isChecked(pageBgRpt, Style.REPEAT_XY)%>/>&nbsp;&nbsp;&nbsp;
						NONE<input type="radio" name="radio_pageBgRpt" value="<%=Style.NONE %>" <%=Utils.isChecked(pageBgRpt, Style.NONE)%>/>&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="hidden_radio_pageBgRpt" value="<%=pageBgRpt%>"></input>
					</td>
				</tr>
				<tr>
					<td>Font</td>
					<td><%
						String pageFont = (style.pageFont!=null)?StringEscapeUtils.escapeHtml(style.pageFont):"";%>
						<input type="text" name="value_pageFont" value="<%=pageFont%>" size="32" maxlength="64"></input>
						<input type="hidden" name="hidden_value_pageFont" value="<%=pageFont %>"></input>
					</td>
				</tr>
				<tr>
					<td>Font Color</td>
					<td><%
						String pageFontColor=(style.pageFontColor!=null)?StringEscapeUtils.escapeHtml(style.pageFontColor):"";%>
						<input class="color" type="text" name="value_pageFontColor" value="<%=pageFontColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_pageFontColor" value="<%=pageFontColor%>"></input>
					</td>
				</tr>
				<tr>
					<td>Font Size</td>
					<td><%
						int pageFontSize=style.pageFontSize;%>
						<input type="text" name="value_pageFontSize" value="<%=pageFontSize %>" size="32" maxlength="2"></input>
						<input type="hidden" name="hidden_value_pageFontSize" value="<%=pageFontSize %>"></input>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Title Bar Properties</b></td>
				</tr>
				<tr>
					<td>Background Image</td>
					<td><%
						String titlebarBgImage = (style.titlebarBackgroundImage!=null)?StringEscapeUtils.escapeHtml(style.titlebarBackgroundImage):""; %>
						<input type="text" name="value_titlebarBgImage" value="<%=titlebarBgImage %>" size="32" maxlength="512"></input>
						<input type="hidden" name="hidden_value_titlebarBgImage" value="<%=titlebarBgImage %>"></input>
					</td>
				</tr>
				<tr>
					<td>Background Color</td>
					<td><%
						String titlebarBgColor = (style.titlebarBackgroundColor!=null)?StringEscapeUtils.escapeHtml(style.titlebarBackgroundColor):"";%>
						<input class="color" type="text" name="value_titlebarBgColor" value="<%=titlebarBgColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_titlebarBgColor" value="<%=titlebarBgColor %>"></input></td>
				</tr>
				<tr>
					<td>Tile Direction</td>
					<td><%
						String titlebarBgRepeat = style.titlebarBackgroundRepeat;%>
						X<input type="radio" name="radio_titlebarBgRepeat" value="<%=Style.REPEAT_XX %>" <%=Utils.isChecked(titlebarBgRepeat, Style.REPEAT_XX)%>/>&nbsp;&nbsp;&nbsp;
						Y<input type="radio" name="radio_titlebarBgRepeat" value="<%=Style.REPEAT_YY %>" <%=Utils.isChecked(titlebarBgRepeat, Style.REPEAT_YY)%>/>&nbsp;&nbsp;&nbsp;
						XY<input type="radio" name="radio_titlebarBgRepeat" value="<%=Style.REPEAT_XY %>" <%=Utils.isChecked(titlebarBgRepeat, Style.REPEAT_XY)%>/>&nbsp;&nbsp;&nbsp;
						NONE<input type="radio" name="radio_titlebarBgRepeat" value="<%=Style.NONE %>" <%=Utils.isChecked(titlebarBgRepeat, Style.NONE)%>/>&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="hidden_radio_titlebarBgRepeat" value="<%=titlebarBgRepeat%>"></input>
					</td>
				</tr>
				<tr>
					<td>Font</td>
					<td><%
						String titlebarFont = (style.titlebarFont!=null)?StringEscapeUtils.escapeHtml(style.titlebarFont):"";%>
						<input type="text" name="value_titlebarFont" value="<%=titlebarFont %>" size="32" maxlength="64"></input>
						<input type="hidden" name="hidden_value_titlebarFont" value="<%=titlebarFont %>"></input>
					</td>
				</tr>
				<tr>
					<td>Font Color</td>
					<td><%
						String titlebarFontColor = (style.titlebarFontColor!=null)?StringEscapeUtils.escapeHtml(style.titlebarFontColor):"";%>
						<input class="color" type="text" name="value_titlebarFontColor" value="<%=titlebarFontColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_titlebarFontColor" value="<%=titlebarFontColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Font Size</td>
					<td><%
						int titlebarFontSize = style.titlebarFontSize;%>
						<input type="text" name="value_titlebarFontSize" value="<%=titlebarFontSize %>" size="32" maxlength="2"></input>
						<input type="hidden" name="hidden_value_titlebarFontSize" value="<%=titlebarFontSize %>"></input>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Nav Bar Properties</b></td>
				</tr>
				<tr>
					<td>Background Image</td>
					<td><%
						String navbarBgImage = (style.navbarBackgroundImage!=null)?StringEscapeUtils.escapeHtml(style.navbarBackgroundImage):"";%>
						<input type="text" name="value_navbarBgImage" value="<%=navbarBgImage %>" size="32" maxlength="512"></input>
						<input type="hidden" name="hidden_value_navbarBgImage" value="<%=navbarBgImage %>"></input>
					</td>
				</tr>
				<tr>
					<td>Background Color</td>
					<td><%
						String navbarBgColor = (style.navbarBackgroundColor!=null)?StringEscapeUtils.escapeHtml(style.navbarBackgroundColor):"";%>
						<input class="color" type="text" name="value_navbarBgColor" value="<%=navbarBgColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_navbarBgColor" value="<%=navbarBgColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Tile Direction</td>
					<td><%
						String navbarBgRepeat = style.navbarBackgroundRepeat;%>
						X<input type="radio" name="radio_navbarBgRepeat" value="<%=Style.REPEAT_XX %>" <%=Utils.isChecked(navbarBgRepeat, Style.REPEAT_XX)%>/>&nbsp;&nbsp;&nbsp;
						Y<input type="radio" name="radio_navbarBgRepeat" value="<%=Style.REPEAT_YY %>" <%=Utils.isChecked(navbarBgRepeat, Style.REPEAT_YY)%>/>&nbsp;&nbsp;&nbsp;
						XY<input type="radio" name="radio_navbarBgRepeat" value="<%=Style.REPEAT_XY %>" <%=Utils.isChecked(navbarBgRepeat, Style.REPEAT_XY)%>/>&nbsp;&nbsp;&nbsp;
						NONE<input type="radio" name="radio_navbarBgRepeat" value="<%=Style.NONE %>" <%=Utils.isChecked(navbarBgRepeat, Style.NONE)%>/>&nbsp;&nbsp;&nbsp;
						<input type="hidden" name = "hidden_radio_navbarBgRepeat" value = "<%=navbarBgRepeat%>"></input>
					</td>
				</tr>
				<tr>
					<td>Font</td>
					<td><%
						String navbarFont = (style.navbarFont!=null)?StringEscapeUtils.escapeHtml(style.navbarFont):"";%>
						<input type="text" name="value_navbarFont" value="<%=navbarFont %>" size="32" maxlength="64"></input>
						<input type="hidden" name="hidden_value_navbarFont" value="<%=navbarFont%>"></input>
					</td>
				</tr>
				<tr>
					<td>Font Color</td>
					<td><%
						String navbarFontColor = (style.navbarFontColor!=null)?StringEscapeUtils.escapeHtml(style.navbarFontColor):"";%>
						<input class="color" type="text" name="value_navbarFontColor" value="<%=navbarFontColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_navbarFontColor" value="<%=navbarFontColor%>"></input>
					</td>
				</tr>
				<tr>
					<td>Font Size</td>
					<td><%
						int navbarFontSize = style.navbarFontSize;%>
						<input type="text" name="value_navbarFontSize" value="<%=navbarFontSize %>" size="32" maxlength="2"></input>
						<input type="hidden" name="hidden_value_navbarFontSize" value="<%=navbarFontSize %>"></input>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Text Customization</b></td>
				</tr>
				<tr>
					<td>HOME</td>
					<td><%
						String textHome=(style.homeText!=null)?StringEscapeUtils.escapeHtml(style.homeText):"";%>
						<input type="text" name="value_textHome" value="<%=textHome %>" size="32" maxlength="20"></input>
						<input type="hidden" name="hidden_value_textHome" value="<%=textHome %>"></input>
					</td>
				</tr>
				<tr>
					<td>VENDORS</td>
					<td><%
						String textVendors=(style.vendorsText!=null)?StringEscapeUtils.escapeHtml(style.vendorsText):"";%>
						<input type="text" name="value_textVendors" value="<%=textVendors %>" size="32" maxlength="20"></input>
						<input type="hidden" name="hidden_value_textVendors" value="<%=textVendors %>"></input>
					</td>
				</tr>
				<tr>
					<td>PAGES</td>
					<td><%
						String textPages=(style.pagesText!=null)?StringEscapeUtils.escapeHtml(style.pagesText):""; %>
						<input type="text" name="value_textPages" value="<%=textPages %>" size="32" maxlength="20"></input>
						<input type="hidden" name="hidden_value_textPages" value="<%=textPages %>"></input>
					</td>
				</tr>
				<tr>
					<td>FEATURED</td>
					<td><%
						String textFeatured=(style.featuredText!=null)?StringEscapeUtils.escapeHtml(style.featuredText):"";%>
						<input type="text" name="value_textFeatured" value="<%=textFeatured %>" size="32" maxlength="20"></input>
						<input type="hidden" name="hidden_value_textFeatured" value="<%=textFeatured %>"></input>
					</td>
				</tr>
				<tr>
					<td>PRODUCTS</td>
					<td><%
						String textProducts = (style.productsText!=null)?StringEscapeUtils.escapeHtml(style.productsText):"";%>
						<input type="text" name="value_textProducts" value="<%=textProducts %>" size="32" maxlength="20"></input>
						<input type="hidden" name="hidden_value_textProducts" value="<%=textProducts%>"></input>
					</td>
				</tr>
				<tr>
					<td>COMPANIES</td>
					<td><%
						String textCompanies = (style.companiesText!=null)?StringEscapeUtils.escapeHtml(style.companiesText):"";%>
						<input type="text" name="value_textCompanies" value="<%=textCompanies %>" size="32" maxlength="20"></input>
						<input type="hidden" name="hidden_value_textCompanies" value="<%=textCompanies%>"></input>
					</td>
				</tr>
				<tr>
					<td>COUNTRY</td>
					<td><%
						String textCountry = (style.countryText!=null)?StringEscapeUtils.escapeHtml(style.countryText):"";%>
						<input type="text" name="value_textCountry" value="<%=textCountry %>" size="32" maxlength="20"></input>
						<input type="hidden" name="hidden_value_textCountry" value="<%=textCountry %>"></input>
					</td>
				</tr>
				<tr>
					<td>SEARCH RESULT</td>
					<td><%
						String textSearchResult = (style.searchResultText!=null)?StringEscapeUtils.escapeHtml(style.searchResultText):"";%>
						<input type="text" name="value_textSearchResult" value="<%=textSearchResult %>" size="32" maxlength="64"></input>
						<input type="hidden" name="hidden_value_textSearchResult" value="<%=textSearchResult %>"></input>
					</td>
				</tr>
				<tr>
					<td>CATEGORY</td>
					<td><%
						String textCategory = (style.categoryText!=null)?StringEscapeUtils.escapeHtml(style.categoryText):"";%>
						<input type="text" name="value_textCategory" value="<%=textCategory %>" size="32" maxlength="20"></input>
						<input type="hidden" name="hidden_value_textCategory" value="<%=textCategory%>"></input>
					</td>
				</tr>
				<tr>
					<td>KEYWORD</td>
					<td><%
						String textKeyword = (style.keywordText!=null)?StringEscapeUtils.escapeHtml(style.keywordText):"";%>
						<input type="text" name="value_textKeyword" value="<%=textKeyword %>" size="32" maxlength="20"></input>
						<input type="hidden" name="hidden_value_textKeyword" value="<%=textKeyword %>"></input>
					</td>
				</tr>
				<tr>
					<td>NOT FOUND</td>
					<td><%
						String textNotFound = (style.notFoundText!=null)?StringEscapeUtils.escapeHtml(style.notFoundText):"";%>
						<input type="text" name="value_textNotFound" value="<%=textNotFound %>" size="32" maxlength="32"></input>
						<input type="hidden" name="hidden_value_textNotFound" value="<%=textNotFound %>"></input>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Search Customization</b></td>
				</tr>
				<tr>
					<td>Include Country Search</td>
					<td><% 
						boolean searchCountry = style.includeCountrySearch;%>
						Yes&nbsp;<input type="radio" name="radio_searchCountry" value="y" <%=(searchCountry==true)?"checked":"" %>/>&nbsp;&nbsp;&nbsp;
						No&nbsp;<input type="radio" name="radio_searchCountry" value="n" <%=(searchCountry==false)?"checked":"" %>/>
						<input type="hidden" name = "hidden_radio_searchCountry" value="<%=(searchCountry)?"y":"n"%>"></input>
					</td>
				</tr>
				<tr>
					<td>Include Category Search</td>
					<td><%
						boolean searchCategory = style.includeCategorySearch;%>
						Yes&nbsp;<input type="radio" name="radio_searchCategory" value="y" <%=(searchCategory==true)?"checked":"" %>/>&nbsp;&nbsp;&nbsp;
						No&nbsp;<input type="radio" name="radio_searchCategory" value="n" <%=(searchCategory==false)?"checked":"" %>/>
						<input type="hidden" name = "hidden_radio_searchCategory" value="<%=(searchCategory)?"y":"n"%>"></input>
					</td>
				</tr>
				<tr>
					<td>Include Price Search</td>
					<td><%
						boolean searchPrice = style.includePriceSearch;%>
						Yes&nbsp;<input type="radio" name="radio_searchPrice" value="y" <%=(searchPrice==true)?"checked":"" %>/>&nbsp;&nbsp;&nbsp;
						No&nbsp;<input type="radio" name="radio_searchPrice" value="n" <%=(searchPrice==false)?"checked":"" %>/>
						<input type="hidden" name = "hidden_radio_searchPrice" value="<%=(searchPrice)?"y":"n"%>"></input>
					</td>
				</tr>
				<tr>
					<td>Default Search Text</td>
					<td><%
						String defaultSearchText = (style.defaultSearchText!=null)?StringEscapeUtils.escapeHtml(style.defaultSearchText):"";%>
						<input type="text" name="value_defaultSearchText" value="<%=defaultSearchText %>" size="32" maxlength="32"></input>
						<input type="hidden" name="hidden_value_defaultSearchText" value="<%=defaultSearchText%>"></input>
					</td>
				</tr>
				<tr>
					<td>Search Image Button URL</td>
					<td><%
						String searchImageButtonURL = (style.searchButtonImageURL!=null)?StringEscapeUtils.escapeHtml(style.searchButtonImageURL):"";%>
						<input type="text" name="value_searchImageButtonURL" value="<%=searchImageButtonURL %>" size="32" maxlength="256"></input>
						<input type="hidden" name="hidden_value_searchImageButtonURL" value="<%=searchImageButtonURL%>"></input>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Search Result Display</b></td>
				</tr>
				<tr>
					<td>Number of rows</td>
					<td><%
						int rowCount = style.productSearchRowCount;%>
						<input type="text" name="value_rowCount" value="<%=rowCount %>" size="32" maxlength="2"></input>
						<input type="hidden" name="hidden_value_rowCount" value="<%=rowCount %>"></input>
					</td>
				</tr>
				<tr>
					<td>Number of columns</td>
					<td><%
						int columnCount = style.productSearchColumnCount;%>
						<input type="text" name="value_columnCount" value="<%=columnCount %>" size="32" maxlength="2"></input>
						<input type="hidden" name="hidden_value_columnCount" value="<%=columnCount %>"></input>
					</td>
				</tr>
				<tr>
					<td>Thumbnail Width(pixels)</td>
					<td><%
						int thumbnailWidth = style.thumbnailWidthInPixels;%>
						<input type="text" name="value_thumbnailWidth" value="<%=thumbnailWidth %>" size="32" maxlength="3"></input>
						<input type="hidden" name="hidden_value_thumbnailWidth" value="<%=thumbnailWidth %>"></input>
					</td>
				</tr>
				<tr>
					<td>Thumbnail Height(pixels)</td>
					<td><%int thumbnailHeight = style.thumbnailHeightInPixels; %>
						<input type="text" name="value_thumbnailHeight" value="<%=thumbnailHeight%>" size="32" maxlength="3"></input>
						<input type="hidden" name="hidden_value_thumbnailHeight" value="<%=thumbnailHeight %>"></input>
					</td>
				</tr>
				<tr>
					<td>Thumbnail Background Color</td>
					<td><%
						String thumbnailBgColor = (style.thumbnailBackgroundColor!=null)?StringEscapeUtils.escapeHtml(style.thumbnailBackgroundColor):"";%>
						<input class="color" type="text" name="value_thumbnailBgColor" value="<%=thumbnailBgColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_thumbnailBgColor" value="<%=thumbnailBgColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Thumbnail Text Color</td>
					<td><%
						String thumbnailTextColor = (style.thumbnailTextColor!=null)?StringEscapeUtils.escapeHtml(style.thumbnailTextColor):"";%>
						<input class="color" type="text" name="value_thumbnailTextColor" value="<%=thumbnailTextColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_thumbnailTextColor" value="<%=thumbnailTextColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Order Button Image URL</td>
					<td><%
						String orderButtomImageURL = (style.orderButtonImageURL!=null)?StringEscapeUtils.escapeHtml(style.orderButtonImageURL):"";%>
						<input type="text" name="value_orderButtomImageURL" value="<%=orderButtomImageURL %>" size="32" maxlength="256"></input>
						<input type="hidden" name="hidden_value_orderButtomImageURL" value="<%=orderButtomImageURL%>"></input>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Misc URL</b></td>
				</tr>
				<tr>
					<td>&quot;Featured Vendor TrustSeal&quot; Image URL</td>
					<td><%
						String featuredVendorTrustSeal = (style.trustSealImageURL!=null)?StringEscapeUtils.escapeHtml(style.trustSealImageURL):"";%>
						<input type="text" name="value_featuredVendorTrustSeal" value="<%=featuredVendorTrustSeal %>" size="32" maxlength="256"></input>
						<input type="hidden" name="hidden_value_featuredVendorTrustSeal" value="<%=featuredVendorTrustSeal %>"></input>
					</td>
				</tr>
				<tr>
					<td>&quot;No Product Image&quot; URL</td>
					<td><%
						String noProductImage = (style.noProductImageURL!=null)?StringEscapeUtils.escapeHtml(style.noProductImageURL):"";%>
						<input type="text" name="value_noProductImage" value="<%=noProductImage %>" size="32" maxlength="256"></input>
						<input type="hidden" name="hidden_value_noProductImage" value="<%=noProductImage%>"></input>
					</td>
				</tr>
				<tr>
					<td colspan="2"><hr/></td>
				</tr>
				<tr>
					<td colspan="2"><b>Category Sidebar Customization</b></td>
				</tr>
				<tr>
					<td>Include category sidebar</td>
					<td><%
						String includeCategorySidebar = style.includeCategoryList;%>
						Left&nbsp;<input type="radio" name="radio_includeCategorySidebar" value="<%=Style.LEFT_CATEGORY %>" <%= Utils.categoryChecked(includeCategorySidebar, Style.LEFT_CATEGORY) %>/>&nbsp;&nbsp;&nbsp;
						Right&nbsp;<input type="radio" name="radio_includeCategorySidebar" value="<%=Style.RIGHT_CATEGORY %>" <%= Utils.categoryChecked(includeCategorySidebar, Style.RIGHT_CATEGORY) %>/>&nbsp;&nbsp;&nbsp;
						NONE&nbsp;<input type="radio" name="radio_includeCategorySidebar" value="<%=Style.NO_CATEGORY %>" <%= Utils.categoryChecked(includeCategorySidebar, Style.NO_CATEGORY) %>/>
						<input type="hidden" name="hidden_radio_includeCategorySidebar" value="<%=includeCategorySidebar%>"></input>
					</td>
				</tr>
				<tr>
					<td>Background Image</td>
					<td><%
						String categoryBgImage = (style.categoryBackgroundImage!=null)?StringEscapeUtils.escapeHtml(style.categoryBackgroundImage):"";%>
						<input type="text" name="value_categoryBgImage" value="<%=categoryBgImage %>" size="32" maxlength="512"></input>
						<input type="hidden" name="hidden_value_categoryBgImage" value="<%=categoryBgImage %>"></input>
					</td>
				</tr>
				<tr>
					<td>Background Color</td>
					<td><%
						String categoryBgColor = (style.categoryBackgroundColor!=null)?StringEscapeUtils.escapeHtml(style.categoryBackgroundColor):"";%>
						<input class="color" type="text" name="value_categoryBgColor" value="<%=categoryBgColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_categoryBgColor" value="<%=categoryBgColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Background Image Repeat</td>
					<td><%
						String categoryBgImgRepeat = style.categoryBackgroundRepeat;%>
						X<input type="radio" name="radio_categoryBgImgRepeat" value="<%=Style.REPEAT_XX %>" <%=Utils.isChecked(categoryBgImgRepeat, Style.REPEAT_XX)%>/>&nbsp;&nbsp;&nbsp;
						Y<input type="radio" name="radio_categoryBgImgRepeat" value="<%=Style.REPEAT_YY %>" <%=Utils.isChecked(categoryBgImgRepeat, Style.REPEAT_YY)%>/>&nbsp;&nbsp;&nbsp;
						XY<input type="radio" name="radio_categoryBgImgRepeat" value="<%=Style.REPEAT_XY %>" <%=Utils.isChecked(categoryBgImgRepeat, Style.REPEAT_XY)%>/>&nbsp;&nbsp;&nbsp;
						NONE<input type="radio" name="radio_categoryBgImgRepeat" value="<%=Style.NONE %>" <%=Utils.isChecked(categoryBgImgRepeat, Style.NONE)%>/>&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="hidden_radio_categoryBgImgRepeat" value="<%=categoryBgImgRepeat%>"></input>
					</td>
				</tr>
				<tr>
					<td>Title Background Image</td>
					<td><%
						String categoryTitleBgImage = (style.categoryTitleBackgroundImage!=null)?StringEscapeUtils.escapeHtml(style.categoryTitleBackgroundImage):"";%>
						<input type="text" name="value_categoryTitleBgImage" value="<%=categoryTitleBgImage %>" size="32" maxlength="512"></input>
						<input type="hidden" name="hidden_value_categoryTitleBgImage" value="<%=categoryTitleBgImage%>"></input>
					</td>
				</tr>
				<tr>
					<td>Title Background Color</td>
					<td><%
						String categoryTitleBgColor = (style.categoryTitleBackgroundColor!=null)?StringEscapeUtils.escapeHtml(style.categoryTitleBackgroundColor):"";%>
						<input class="color" type="text" name="value_categoryTitleBgColor" value="<%=categoryTitleBgColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_categoryTitleBgColor" value="<%=categoryTitleBgColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Title Background Image Repeat</td>
					<td><%
					    String categoryTitleBgImageRepeat = style.categoryTitleBackgroundRepeat;%>
						X<input type="radio" name="radio_categoryTitleBgImageRepeat" value="<%=Style.REPEAT_XX %>" <%=Utils.isChecked(categoryTitleBgImageRepeat, Style.REPEAT_XX)%>/>&nbsp;&nbsp;&nbsp;
						Y<input type="radio" name="radio_categoryTitleBgImageRepeat" value="<%=Style.REPEAT_YY %>" <%=Utils.isChecked(categoryTitleBgImageRepeat, Style.REPEAT_YY)%>/>&nbsp;&nbsp;&nbsp;
						XY<input type="radio" name="radio_categoryTitleBgImageRepeat" value="<%=Style.REPEAT_XY %>" <%=Utils.isChecked(categoryTitleBgImageRepeat, Style.REPEAT_XY)%>/>&nbsp;&nbsp;&nbsp;
						NONE<input type="radio" name="radio_categoryTitleBgImageRepeat" value="<%=Style.NONE %>" <%=Utils.isChecked(categoryTitleBgImageRepeat, Style.NONE)%>/>&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="hidden_radio_categoryTitleBgImageRepeat" value="<%=categoryTitleBgImageRepeat%>"></input>
				</tr>
				<tr>
					<td>Title Font</td>
					<td><%
						String categoryTitleFont = (style.categoryTitleFont!=null)?StringEscapeUtils.escapeHtml(style.categoryTitleFont):"";%>
						<input type="text" name="value_categoryTitleFont" value="<%=categoryTitleFont %>" size="32" maxlength="64"></input>
						<input type="hidden" name="hidden_value_categoryTitleFont" value="<%=categoryTitleFont %>"></input>
					</td>
				</tr>
				<tr>
					<td>Title Font Color</td>
					<td><%
						String categoryTitleFontColor = (style.categoryTitleFontColor!=null)?StringEscapeUtils.escapeHtml(style.categoryTitleFontColor):"";%>
						<input class="color" type="text" name="value_categoryTitleFontColor" value="<%=categoryTitleFontColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_categoryTitleFontColor" value="<%=categoryTitleFontColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Title Font Size</td>
					<td><%
						int categoryTitleFontSize = style.categoryTitleFontSize;%>
						<input type="text" name="value_categoryTitleFontSize" value="<%=categoryTitleFontSize %>" size="32" maxlength="2"></input>
						<input type="hidden" name="hidden_value_categoryTitleFontSize" value="<%=categoryTitleFontSize %>"></input>
					</td>
				</tr>
				<tr>
					<td>List Background Image</td>
					<td><%
						String categoryListBgImage = (style.categoryListBackgroundImage!=null)?StringEscapeUtils.escapeHtml(style.categoryListBackgroundImage):"";%>
						<input type="text" name="value_categoryListBgImage" value="<%=categoryListBgImage %>" size="32" maxlength="512"></input>
						<input type="hidden" name="hidden_value_categoryListBgImage" value="<%=categoryListBgImage %>"></input></td>
				</tr>
				<tr>
					<td>List Background Color</td>
					<td><%
						String categoryListBgColor = (style.categoryListBackgroundColor!=null)?StringEscapeUtils.escapeHtml(style.categoryListBackgroundColor):"";%>
						<input class="color" type="text" name="value_categoryListBgColor" value="<%=categoryListBgColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_categoryListBgColor" value="<%=categoryListBgColor%>"></input>
					</td>
				</tr>
				<tr>
					<td>List Background Image Repeat</td>
					<td><%
					    String categoryListBgImageRepeat = style.categoryListBackgroundRepeat;%>
						X<input type="radio" name="radio_categoryListBgImageRepeat" value="<%=Style.REPEAT_XX %>" <%=Utils.isChecked(categoryListBgImageRepeat, Style.REPEAT_XX)%>/>&nbsp;&nbsp;&nbsp;
						Y<input type="radio" name="radio_categoryListBgImageRepeat" value="<%=Style.REPEAT_YY %>" <%=Utils.isChecked(categoryListBgImageRepeat, Style.REPEAT_YY)%>/>&nbsp;&nbsp;&nbsp;
						XY<input type="radio" name="radio_categoryListBgImageRepeat" value="<%=Style.REPEAT_XY %>" <%=Utils.isChecked(categoryListBgImageRepeat, Style.REPEAT_XY)%>/>&nbsp;&nbsp;&nbsp;
						NONE<input type="radio" name="radio_categoryListBgImageRepeat" value="<%=Style.NONE %>" <%=Utils.isChecked(categoryListBgImageRepeat, Style.NONE)%>/>&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="hidden_radio_categoryListBgImageRepeat" value="<%=categoryListBgImageRepeat%>"></input>
					</td>
				</tr>
				<tr>
					<td>List Font</td>
					<td><%
						String categoryListFont = (style.categoryListFont!=null)?StringEscapeUtils.escapeHtml(style.categoryListFont):"";%>
						<input type="text" name="value_categoryListFont" value="<%=categoryListFont %>" size="32" maxlength="64"></input>
						<input type="hidden" name="hidden_value_categoryListFont" value="<%=categoryListFont %>"></input>
					</td>
				</tr>
				<tr>
					<td>List Font Color</td>
					<td><%
						String categoryListFontColor = (style.categoryListFontColor!=null)?StringEscapeUtils.escapeHtml(style.categoryListFontColor):"";%>
						<input class="color" type="text" name="value_categoryListFontColor" value="<%=categoryListFontColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_categoryListFontColor" value="<%=categoryListFontColor%>"></input>
					</td>
				</tr>
				<tr>
					<td>List Font Size</td>
					<td><%
						int categoryListFontSize = style.categoryListFontSize;%>
						<input type="text" name="value_categoryListFontSize" value="<%=categoryListFontSize %>" size="32" maxlength="2"></input>
						<input type="hidden" name="hidden_value_categoryListFontSize" value="<%=categoryListFontSize %>"></input>
					</td>
				</tr>
				<tr>
					<td>Hover Background Image</td>
					<td><%
						String categoryHoverBgImage = (style.categoryHoverBackgroundImage!=null)?StringEscapeUtils.escapeHtml(style.categoryHoverBackgroundImage):"";%>
						<input type="text" name="value_categoryHoverBgImage" value="<%=categoryHoverBgImage %>" size="32" maxlength="512"></input>
						<input type="hidden" name="hidden_value_categoryHoverBgImage" value="<%=categoryHoverBgImage %>"></input>
					</td>
				</tr>
				<tr>
					<td>Hover Background Color</td>
					<td><%
						String categoryHoverBgColor = (style.categoryHoverBackgroundColor!=null)?StringEscapeUtils.escapeHtml(style.categoryHoverBackgroundColor):"";%>
						<input class="color" type="text" name="value_categoryHoverBgColor" value="<%=categoryHoverBgColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_categoryHoverBgColor" value="<%=categoryHoverBgColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Hover Background Image Repeat</td>
					<td><%
						String categoryHoverBgImageRepeat = style.categoryHoverBackgroundRepeat;%>
						X<input type="radio" name="radio_categoryHoverBgImageRepeat" value="<%=Style.REPEAT_XX %>" <%=Utils.isChecked(categoryHoverBgImageRepeat, Style.REPEAT_XX)%>/>&nbsp;&nbsp;&nbsp;
						Y<input type="radio" name="radio_categoryHoverBgImageRepeat" value="<%=Style.REPEAT_YY %>" <%=Utils.isChecked(categoryHoverBgImageRepeat, Style.REPEAT_YY)%>/>&nbsp;&nbsp;&nbsp;
						XY<input type="radio" name="radio_categoryHoverBgImageRepeat" value="<%=Style.REPEAT_XY %>" <%=Utils.isChecked(categoryHoverBgImageRepeat, Style.REPEAT_XY)%>/>&nbsp;&nbsp;&nbsp;
						NONE<input type="radio" name="radio_categoryHoverBgImageRepeat" value="<%=Style.NONE %>" <%=Utils.isChecked(categoryHoverBgImageRepeat, Style.NONE)%>/>&nbsp;&nbsp;&nbsp;
						<input type="hidden" name="hidden_radio_categoryHoverBgImageRepeat" value="<%=categoryHoverBgImageRepeat%>"></input>
					</td>
				</tr>
				<tr>
					<td>Hover Font</td>
					<td><%
						String categoryHoverFont = (style.categoryHoverFont!=null)?StringEscapeUtils.escapeHtml(style.categoryHoverFont):"";%>
						<input type="text" name="value_categoryHoverFont" value="<%=categoryHoverFont %>" size="32" maxlength="64"></input>
						<input type="hidden" name="hidden_value_categoryHoverFont" value="<%=categoryHoverFont %>"></input>
					</td>
				</tr>
				<tr>
					<td>Hover Font Color</td>
					<td><%
						String categoryHoverFontColor = (style.categoryHoverFontColor!=null)?StringEscapeUtils.escapeHtml(style.categoryHoverFontColor):"";%>
						<input class="color" type="text" name="value_categoryHoverFontColor" value="<%=categoryHoverFontColor %>" size="32" maxlength="16"></input>
						<input type="hidden" name="hidden_value_categoryHoverFontColor" value="<%=categoryHoverFontColor %>"></input>
					</td>
				</tr>
				<tr>
					<td>Hover Font Size</td>
					<td><%
						int categoryHoverFontSize = style.categoryHoverFontSize;%>
						<input type="text" name="value_categoryHoverFontSize" value="<%=categoryHoverFontSize %>" size="32" maxlength="2"></input>
						<input type="hidden" name="hidden_value_categoryHoverFontSize" value="<%=categoryHoverFontSize %>"></input>
					</td>
				</tr>
				<tr height="100">
					<td colspan="2"><center><input type="button" name="submitStylePage" value="Update" onclick="updateStyle('<%=marketId %>')"></input></center></td>
				</tr>
			</table>
		</form>
		<br/><br/>
		<%
	} finally{
		conn.close();
	}
}%>
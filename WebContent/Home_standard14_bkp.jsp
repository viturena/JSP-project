<%@page import = "com.catgen.thread.*,com.catgen.*,com.catgen.factories.*,com.catgen.helper.*,org.apache.commons.lang.StringEscapeUtils,org.apache.catalina.util.*,java.util.List,java.util.HashMap,java.util.*,java.net.URLEncoder,java.sql.Connection,java.util.Date" %>
<%@page contentType="text/html;charset=UTF-8"%><%
CatgenPageContext catgenContext = CatgenPageContext.getInstance(pageContext);
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	NetworkMarket networkMarket = catgenContext.NetworkMarket;
	
	if (networkMarket == null) {
		%><jsp:forward page="<%=Constants.OE_URL%>"></jsp:forward><%
	}
	
	UserSessionBean userSessionBean = UserSessionFactory.getUserSessionBeanByIdAndType(conn, networkMarket.NetworkMarketID, Constants.NETWORK_MARKET);
	boolean isActive = UserSessionFactory.isAccountActive(conn, userSessionBean);
	if(!isActive){
		%><jsp:forward page="AccountNotActive.jsp"></jsp:forward><%
	}
	
	boolean productDisplayPage = false;
	List<Product> displayProducts = new ArrayList<Product>();
	TemplatePropertiesBean templatePropertiesBean = TemplateInitializer.initializeTemplateProperties(catgenContext, conn, networkMarket.NetworkMarketID);
	Style style = templatePropertiesBean.style;
	catgenContext.PageSize = style.productSearchRowCount * style.productSearchColumnCount;
	int totalProductCount = 0;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="com.catgen.thread.ProductFilterThread"%>
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="<%=networkMarket.Keywords %>" />
	 <meta name="description" content="<%=networkMarket.MetaDescription %>" />
        <meta name="google-site-verification" content="<%=networkMarket.GSiteVerification%>" />
        <title>
            <%= StringEscapeUtils.escapeHtml(networkMarket.Name)%>
        </title>
        <style type="text/css">
            .thumbnailContent {
                width: <%= style.thumbnailWidthInPixels + 10 %>px;
                height: <%= style.thumbnailHeightInPixels + 30 %>px;
                background:<%= style.thumbnailBackgroundColor%>;
                color:<%= style.thumbnailTextColor%>
            }
            .thumbnailImage {
                max-width: <%= style.thumbnailWidthInPixels %>px;
                max-height: <%= style.thumbnailHeightInPixels %>px;
                width: expression(this.width > <%= style.thumbnailWidthInPixels %>? "<%= style.thumbnailWidthInPixels %>px": "auto");
                height: expression(this.height > <%= style.thumbnailHeightInPixels %>? "<%= style.thumbnailHeightInPixels %>px": "auto");
            }
            
            .largeImage {
                max-width: 320px;
                max-height: 240px;
                width: expression(this.width > 320 ? "320px" : "auto");
                height: expression(this.height > 240 ? "240px" : "auto");
            }
            body{
                <%= StyleFactory.getBackgroundStyle(style.screenBackgroundImage, style.screenBackgroundColor, style.screenBackgroundRepeat, Constants.DEFAULT_BG_COLOR_BODY)%>
                <%= StyleFactory.getFontStyle(style.pageFont, style.pageFontSize, style.pageFontColor, Constants.DEFAULT_FONT_COLOR_PAGE)%>
            }

            a #homeAnchor, a:visited #homeAnchor, a:hover #homeAnchor{
                text-decoration: none;
            }
            .page{
                width: 1024px;
                <%= StyleFactory.getBackgroundStyle(style.pageBackgroundImage, style.pageBackgroundColor, style.pageBackgroundRepeat, Constants.DEFAULT_BG_COLOR_PAGE)%>
            }
		
            .contents{
                padding: 10px;
            }
	     select, input{
		  <%= StyleFactory.getFontStyle(style.pageFont, style.pageFontSize, style.pageFontColor, Constants.DEFAULT_FONT_COLOR_SELECT)%>
		  color:#000;
	     }
            .NavBar{
                height: 32px;
                <%= StyleFactory.getBackgroundStyle(style.navbarBackgroundImage, style.navbarBackgroundColor, style.navbarBackgroundRepeat, Constants.DEFAULT_BG_COLOR_NAVBAR)%>
            }

            #NavBarText a{
                font-weight:bold;
                <%= StyleFactory.getFontStyle(style.navbarFont, style.navbarFontSize, style.navbarFontColor, Constants.DEFAULT_FONT_COLOR_NAVBAR)%>
                
            }

            .Title{
                padding-left: 10px;
                padding-right: 10px;
                font-weight:bold;
                height:32px;
                <%= StyleFactory.getBackgroundStyle(style.titlebarBackgroundImage, style.titlebarBackgroundColor, style.titlebarBackgroundRepeat, Constants.DEFAULT_BG_COLOR_TITLE)%>
                <%= StyleFactory.getFontStyle(style.titlebarFont, style.titlebarFontSize, style.titlebarFontColor, Constants.DEFAULT_FONT_COLOR_TITLE)%>
                
            }

            a{
                text-decoration:none;
            }
            
            A #openentry {
                background-color:#3B5998; 
                color:#FFF;
                height:30px;
                margin-top:10px;
                font-size:12px;
            }
            A:hover #openentry {
                background-color:#5370AA;
                height: 30px;
                color:#FFF;
                height:30px;
                margin-top:10px;
                font-size:12px;
            }

            .categoryBackground{
                <%= StyleFactory.getBackgroundStyle(style.categoryBackgroundImage, style.categoryBackgroundColor, style.categoryBackgroundRepeat, Constants.DEFAULT_BG_COLOR_CATEGORY) %>
            }
            .categoryTitle{
                padding-left: 10px;
                padding-top: 10px;
                margin-bottom: 1px;
                font-weight: bold;
                <%= StyleFactory.getBackgroundStyle(style.categoryTitleBackgroundImage, style.categoryTitleBackgroundColor, style.categoryTitleBackgroundRepeat, Constants.DEFAULT_BG_COLOR_CATEGORYTITLE) %>
                <%= StyleFactory.getFontStyle(style.categoryTitleFont, style.categoryTitleFontSize, style.categoryTitleFontColor, Constants.DEFAULT_FONT_COLOR_CATEGORYTITLE) %>
                height: 25px;
            }
            .categoryList{
                height: 25px;
                padding-left: 10px;
                padding-top: 10px;
                margin-bottom:1px;
                <%= StyleFactory.getBackgroundStyle(style.categoryListBackgroundImage, style.categoryListBackgroundColor, style.categoryListBackgroundRepeat, Constants.DEFAULT_BG_COLOR_CATEGORYLIST) %>
                <%= StyleFactory.getFontStyle(style.categoryListFont, style.categoryListFontSize, style.categoryListFontColor, Constants.DEFAULT_FONT_COLOR_CATEGORYLIST) %>
            }
            .hoverList{
                height: 25px;
                padding-left: 10px;
                padding-top: 10px;
                margin-bottom:1px;
                <%= StyleFactory.getBackgroundStyle(style.categoryHoverBackgroundImage, style.categoryHoverBackgroundColor, style.categoryHoverBackgroundRepeat, Constants.DEFAULT_BG_COLOR_HOVERLIST) %>
                <%= StyleFactory.getFontStyle(style.categoryHoverFont, style.categoryHoverFontSize, style.categoryHoverFontColor, Constants.DEFAULT_FONT_COLOR_HOVERLIST) %>
            }
        </style>
        <script language="javascript" type="text/javascript" src="/js/common/script.js"></script>
        <script language="javascript" type="text/javascript">
            function img2txt(img){
                img.src = "<%= style.noProductImageURL%>";
            }
        </script>

    </head>

    <body>
        <table class="page" width="960" border="0" align="center" cellpadding="0" cellspacing="0">
            <tr>
                <td align="center">
                   <a href="<%= catgenContext.RelativePath%>Home.html?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>">
                        <div id="homeAnchor">
                            <table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td align="left" valign="top">
                                        <%= networkMarket.LogoImage%>
                                    </td>
                                    <td align="center" valign="top">
                                        <%= networkMarket.Header%>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </a>
                </td>
           </tr>
            <tr>
                <td align="left" valign="middle">
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" class="NavBar">
                        <tr>
                            <td width="40%" align="left" valign="middle">
                                <table border="0" cellspacing="0" cellpadding="0">
                                    <tr align="left">
                                        <td id="NavBarText">&nbsp;&nbsp;&nbsp;
                                            <a href="<%= catgenContext.RelativePath%>Home.html?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>"><%= style.homeText%></a> <a>|</a>
                                            <a href="<%= catgenContext.RelativePath%>Vendors.html?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>"><%= style.vendorsText%></a> <a>|</a>
                                            <%
                                            if (templatePropertiesBean.mtPages != null && templatePropertiesBean.mtPages.size() > 0) {
                                                for (Page mtPage : templatePropertiesBean.mtPages) {
                                                	if(!mtPage.Hidden){%>
                                            			<a href="<%= catgenContext.RelativePath%><%= URLEncoder.encode(Utils.getSafeString(mtPage.Name).trim())%>?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>"><%= StringEscapeUtils.escapeHtml(mtPage.Name)%></a> <a>|</a> <%
                                                	}
                                                }
                                            }%>
                                            <a href="<%= catgenContext.RelativePath%>Feedback.html?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>">Feedback</a> <a>|</a>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                            <td width="60%" align="right" valign="middle">
                                <form action="<%= catgenContext.RelativePath%>Search.html" method="get">
                                    <table align="right" valign="middle" border="0">
                                        <tr>
                                            <td><input type="hidden" name="pagesize" value="<%=catgenContext.PageSize%>" /></td>
                                            <td><input type="hidden" name="singlePageTemplate" value="true" /></td>
                                            <td><input type="hidden" name="test" value="correct"></input></td>
                                            <td><input type="hidden" name="defSearchText" value = "<%= style.defaultSearchText%>"></input></td>
                                            <% if (style.includeCategorySearch) {%>
                                            <td>
                                                <select name="category" id="category">
                                                	<option value = ''>--- Select ---</option><%
                                                     for (Category cat : templatePropertiesBean.categories) {%>
                                                        <option value = '<%=cat.categoryKey%>'><%=cat.categoryValue%></option>
                                                    <%}%>
                                                </select>
                                            </td>
                                            <%}%>
                                            <% if (style.includeCountrySearch) {%>
                                            <td>
                                                <select name="country" id="country">
                                                	<option value = ''><%= style.countryText %></option>
                                                	<%
                                                     for (String country : templatePropertiesBean.countries){%>
                                                        <option value = '<%= country%>'><%= country%></option>
                                                    <%}%>
                                                </select>
                                            </td>
                                            <%}%>
                                            <% if (style.includePriceSearch) {%>
                                            <td>
                                                <select name="pricerange" id="pricerange">
                                                    <option value="1" >Any Price</option><%
                                                     for (PriceRange pr : templatePropertiesBean.priceRanges) {
                                                         if (pr.value != 1) {%>
                                                    <option value="<%=pr.value%>" <% if (catgenContext.priceRangeCode == pr.value) {%>selected<% }%>><%=pr.display%></option><%
                                                         }
                                                     }%>
                                                </select>
                                            </td>
                                            <%}%>
                                            <td><input type="text" name="search" id="search" size=15 onclick = "this.value=''" value="<%= style.defaultSearchText%>"/></td>
                                            <td><input name="imageField" type="image" id="imageField" src="<%= style.searchButtonImageURL%>" align="top" /></td>
                                        </tr>
                                    </table>
                                </form>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                        <tr>
                        <%
                        if(Style.LEFT_CATEGORY.equals(style.includeCategoryList)){%>
                        <td class="categoryBackground" valign="top" width=<%=Constants.CATEGORY_COLUMN_WIDTH %>>
                            <div class="contents">
                            <%
                            for (Category cat : templatePropertiesBean.categories) {
                                if("".equals(cat.categoryKey)){%>
                                	<div class="categoryTitle"><%= cat.categoryValue %></div><%
                                } else{%>
                                    <a href="<%= catgenContext.RelativePath%>Search.html?category=<%=cat.categoryKey%>&pagesize=<%=catgenContext.PageSize%>&singlePageTemplate=true&test=correct&pricerange=1">
                                        <div id='<%=cat.categoryKey%>' class="categoryList" onmouseover="this.className = 'hoverList';" onmouseout="this.className = 'categoryList';">
                                            <%=cat.categoryValue%>
                                        </div>
                                    </a><%
                              	}
                            }%>
                            </div>
                            </td>
                        <%}%>
                        <td valign="top">
                            <%
                            if ("Home".equalsIgnoreCase(catgenContext.PageName)) {%>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                                    <tr>
                                        <td>
                                            <div class="contents"><%=(networkMarket.Description != null) ? networkMarket.Description : ""%></div>
                                        </td>
                                    </tr>
                                    <tr valign="middle">
                                        <td>
                                            <table width="100%" border="0" cellpadding="0" cellspacing="0" class="Title">
                                                <tr>
                                                    <td>
                                                        <%= style.featuredText + " " + style.productsText%>
                                                    </td>
                                                    <td align="right">
                                                        <%@ include file = "/common/addthis.jsp" %>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr valign="top" align="left">
                                        <td><%
                                           if (templatePropertiesBean.networkMarketFeaturedProducts != null && templatePropertiesBean.networkMarketFeaturedProducts.size() > 0) {
                                               productDisplayPage = true;
                                               displayProducts = templatePropertiesBean.networkMarketFeaturedProducts;
                                           } else {%>
                                            <center><h1><%= style.productsText + " " + style.notFoundText%></h1></center><%
                                           }%>
                                        </td>
                                    </tr>
                                </table>
                            <% }
                            else if ("Search".equalsIgnoreCase(catgenContext.PageName)) {%>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                                    <tr valign="middle">
                                        <td>
                                            <table width="100%">
                                                <tr>
                                                    <td>
                                                        <b><%= style.searchResultText + ": "%></b><br />
                                                        &nbsp;&nbsp;&nbsp;&nbsp;<%= style.keywordText + ": "%><%=(catgenContext.Search != null) ? catgenContext.Search : ""%><br />
                                                        <% if (style.includeCategorySearch) {%>
	                                                        &nbsp;&nbsp;&nbsp;&nbsp;<%= style.categoryText + ": "%>
	                                                        <a style="text-decoration: none; background-color: orange; padding: 2px;" href="<%= catgenContext.RelativePath%>Search.html?category=&pagesize=<%=catgenContext.PageSize%>&singlePageTemplate=true&test=correct&pricerange=1&search=">Display Top-Level Categories</a>&nbsp;&nbsp;|&nbsp;&nbsp;<%
	                                                        String catKey = catgenContext.searchCategory;
	                                                        String origin = Constants.XML_CATEGORY_ROOT_KEY;
	                                                        catKey = (catKey!=null)?catKey:origin;
	                                                        List<Category> categories = CategoryFactory.getCategories(conn, networkMarket.NetworkMarketID);
	                                                        for(Category category: categories){
	                                                        	if(category.categoryKey!=null && category.categoryKey.length()>0 && catKey.contains(category.categoryKey)){
	                                                        		origin = category.categoryKey;
	                                                        		break;
	                                                        	}
	                                                        }
	                                                        if(CategoryFactory.isValidXmlCategoryKey(conn, catKey)){
		                                                        for(int i=origin.length();i<=catKey.length();i+=Constants.CHARS_PER_CATEGORY){%>
																	<a style="text-decoration: none; background-color: lime; padding: 2px;" href="<%= catgenContext.RelativePath%>Search.html?category=<%=catKey.substring(0,i)%>&pagesize=<%=catgenContext.PageSize%>&singlePageTemplate=true&test=correct&pricerange=1"><%=CategoryFactory.getCategoryNameByCategoryKeyFromXml(conn,catKey.substring(0,i)) %></a>&nbsp;&gt;&nbsp; 
																<%} 
															}%>
		
															<br />
                                                        <%}%>
                                                        <% if (style.includeCountrySearch) {%>
                                                        	&nbsp;&nbsp;&nbsp;&nbsp;<%= style.countryText + ": "%><%= (catgenContext.searchCountry != null) ? catgenContext.searchCountry : ""%> <br />
                                                        <%}%>
                                                    </td>
                                                    <td align="right">
                                                        <%@ include file = "/common/addthis.jsp" %>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr valign="top" align="left">
                                        <td>
                                        <%
                                           List<Product> prodList = new ArrayList<Product>();
                                           if (catgenContext.PageNumber == 0) {
                                               prodList = SearchHelper.findProductsBySearchEntries(conn, catgenContext);
                                               session.setAttribute("PRODUCTS_IN_SESSION", prodList);
                                           } else {
                                               prodList = (List<Product>) session.getAttribute("PRODUCTS_IN_SESSION");
                                           }
                                           totalProductCount = prodList.size();
                                           if (catgenContext.PageSize > 0) {
                                               prodList = ProductFactory.getPagedProducts(prodList, catgenContext.PageSize, catgenContext.PageNumber);
                                           }
                                           if (prodList != null && prodList.size() > 0) {
                                               productDisplayPage = true;
                                               displayProducts = prodList;
                                           } else {%>
                                            	<center><h1><%= style.productsText + " " + style.notFoundText%></h1></center>
                                        <% } %>
                                        </td>
                                    </tr>
                                </table>
                            <%}
                            else if ("Vendors".equalsIgnoreCase(catgenContext.PageName)) {
                               if (templatePropertiesBean.mtCompanies == null || templatePropertiesBean.mtCompanies.size() == 0) {
                                   %><br/>&nbsp;&nbsp;&nbsp;0 <%=style.vendorsText%><br/><br/><%
                               } else {
                                   int rowno = 1;%>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                                    <tr valign="middle">
                                        <td>
                                            <table width="100%" cellpadding="0" cellspacing="0" class="Title">
                                                <tr>
                                                    <td>
                                                        <%= style.vendorsText%>
                                                    </td>
                                                    <td align="right">
                                                        <%@ include file = "/common/addthis.jsp" %>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <div class="contents">
                                                <table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
                                                    <tr>
                                                        <td width="20">&nbsp;&nbsp;&nbsp;#</td>
                                                        <td width="120"><%= style.featuredText%></td>
                                                        <td><%= style.companiesText%></td>
                                                        <td width="120">&nbsp;&nbsp;<%= style.countryText%></td>
                                                        <td><%= style.productsText%></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="5">
                                                            <hr size="1"/>
                                                        </td>
                                                    </tr>
                                                    <%
                                                    int totalPCount=0;
                                                    for (Company comp : templatePropertiesBean.mtCompanies) {
                                                        String companyURL = Utils.getCompanyURL(comp, catgenContext.RelativePath, catgenContext.ReferralID);%>
                                                    <tr>
                                                        <td>&nbsp;&nbsp;&nbsp;<%=rowno%></td>
                                                        <td>&nbsp;<%if (templatePropertiesBean.trustedCompanies.get(comp.Code) != null) {%><img src="<%= style.trustSealImageURL%>"></img><%}%>&nbsp;</td>
                                                        <td><a href="<%= companyURL%>" target="_blank"><%= StringEscapeUtils.escapeHtml(comp.Name)%></a></td>
                                                        <td>&nbsp;&nbsp;<%= StringEscapeUtils.escapeHtml((comp.Country!=null && comp.Country.length()>0)?comp.Country:"---")%></td><%
                                                        int pCount = ProductFactory.getVendorProductCount(conn, comp.Code);
                                                        totalPCount+=pCount;%>
                                                        <td><a href="<%= URLEncoder.encode(comp.Code, "UTF-8")%>/Products.html?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>"><%=pCount%>&nbsp;<%= style.productsText%></a></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="5">
                                                            <hr size="1"/>
                                                        </td>
                                                    </tr>
                                                    <% rowno++;
                                                    }
                                                    %>
                                                    <tr height="60" valign="bottom">
                                                        <td colspan="5"><%= style.vendorsText%> : <%=--rowno%></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="5"><%= style.productsText%> : <%=totalPCount%></td>
                                                    </tr>
                                                    <tr height="50">
                                                        <td colspan="5">
                                                        </td>
                                                    </tr>
                                                </table>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            <%
                               }
                            }
                            else if ("Feedback".equalsIgnoreCase(catgenContext.PageName)) { %>
                            	<form action="<%= catgenContext.RelativePath%>Feedback.html?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>" method="get">
                            	<input type="hidden" name ="feedbackSubmitted" value="true"/>
                            	<table border="0" cellpadding="0" cellspacing="10" align="center"><%
									String feedbackSubmitted = request.getParameter("feedbackSubmitted");
	                            	if(feedbackSubmitted==null || feedbackSubmitted.length()==0){
	                            	%>
                            		<tr>
                            			<td colspan="2" align="center"><h2>Feedback Form</h2></td>
                            		</tr>
                            		<tr>
                            			<td colspan="2" align="center">You can use this form to send message to the Network Market <%=(networkMarket.Name!=null)?networkMarket.Name:"" %>.</td>
                            		</tr>
                            		<tr height="20">
                            			<td colspan="2"></td>
                            		</tr>
                            		<tr>
                            			<td align="right">Name : </td>
                            			<td align="left"><input type="text" name="nameField" size=50 /></td>
                            		</tr>
                            		<tr>
                            			<td align="right">Email ID : </td>
                            			<td align="left"><input type="text" name="emailField" size=50 /></td>
                            		</tr>
                            		<tr>
                            			<td align="right">Subject : </td>
                            			<td align="left"><input type="text" name="subField" size=50 /></td>
                            		</tr>
                            		<tr>
                            			<td align="right">Mesage : </td>
                            			<td align="left"><textarea cols=60 rows=8 name="messageText"></textarea></td>
                            		</tr>
                            		<tr>
                            			<td></td>
                            			<td align="left"><input type=submit value="Send Mail" /></td>
                            		</tr>
                            	<%}else{%>
                            		<tr>
                            			<td colspan="2" align="left"><br/><br/><br/><%
		                            		String name = request.getParameter("nameField");
		                            		String from = request.getParameter("emailField");
		                            		String to = networkMarket.ContactEmail;
		                            		String subject = "[OpenEntry: Message from "+from+"] "+request.getParameter("subField");
		                            		String subject2 = "[OpenEntry: Mail sent notification] "+request.getParameter("subField");
		                            		String message = request.getParameter("messageText")+MailMsgs.NOTIFICATION_MESSAGE;
		                            		
		                            		MailObj mailObj = new MailObj();
		                            		mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
		                            		
		                            		try{
			                            		mailObj.to = from;
			                            		mailObj.subject = subject2;
			                            		mailObj.body = message;
			                            		new MailerThread(mailObj).sendMail();
		                            			
			                            		mailObj.to = to;
			                            		mailObj.subject = subject;
			                            		mailObj.body = "Mail From: "+from+"(Click on the Email ID to reply)\n\n\n-----------------------------------------------------------------------------------\n"+message;
			                            		new MailerThread(mailObj).sendMail();
		                            			%><h3>Feedback Submitted.</h3><%
		                            		}catch(Exception e){
		                            			%><h3>Feedback Could not be Submitted.</h3><%
		                            		}%>
                            			</td>
                            		</tr>
                            	<%}%>
                            	</table>
                            	<br /><br /><br /><br />
                            	</form>
                            <%} else if ("Products".equalsIgnoreCase(catgenContext.PageName)) {%>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                                    <tr valign="middle">
                                        <td>
                                            <table width="100%" cellpadding="0" cellspacing="0" class="Title">
                                                <tr>
                                                    <td>
                                                        <%=catgenContext.Company.Name%>
                                                    </td>
                                                    <td align="right">
                                                        <%@ include file = "/common/addthis.jsp" %>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                                <%
									List<Product> products = new ArrayList<Product>();
                                	if (catgenContext.PageNumber == 0) {
										products = ProductFactory.getProducts(conn, catgenContext.Company.Code);
										session.setAttribute("PRODUCTS_IN_SESSION", products);
                                	}else{
                                		products = (List<Product>) session.getAttribute("PRODUCTS_IN_SESSION");
                                	}
									totalProductCount = products.size();
									if (catgenContext.PageSize > 0) {
										products = ProductFactory.getPagedProducts(products, catgenContext.PageSize, catgenContext.PageNumber);
										productDisplayPage = true;
										displayProducts = products;
									}
                                } else {%>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                                    <tr>
                                        <td>
                                    <%
                                    if (catgenContext.PageName != null && templatePropertiesBean.mtPages.size() > 0) {
                                        for (Page pg : templatePropertiesBean.mtPages) {
                                            if (catgenContext.PageName.equalsIgnoreCase(pg.Name)) {%>
                                            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                                <tr valign="middle">
                                                    <td>
                                                        <table width="100%" cellpadding="0" cellspacing="0" class="Title">
                                                            <tr>
                                                                <td>
                                                                    <%=catgenContext.PageName%>
                                                                </td>
                                                                <td align="right">
                                                                    <%@ include file = "/common/addthis.jsp" %>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>
                                                        <div class="contents"><%= (pg.Description != null) ? pg.Description : ""%></div>
                                                    </td>
                                                </tr>
                                            </table><%
                                               }
                                           }
                                       }%>
                                        </td>
                                    </tr>
                                </table>
                                <%
                                }
                                if (productDisplayPage) {%>
                                <table width="100%" border="0" cellpadding="0" cellspacing="0" align="center">
                                    <tr>
                                        <td>
                                            <table border="0" align="center" cellspacing="5" cellpadding="2"><%
                                                int n = 1;
                                                int rowNumber = 0;

                                				for (Product product : displayProducts) {
			                            			if(product!=null){
	                                                    Company company = CompanyFactory.getCachedCompanyByCode(conn, product.CompanyCode);
	                                                    String productURL = Utils.getReferralURL(catgenContext.RelativePath, catgenContext.ReferralID, company.Code, "product,marketfeatured,home", Utils.getProductURL(company, product, catgenContext.RelativePath, catgenContext.ReferralID), product.Code);
	                                                    String currency = Utils.getCurrencySymbol(company, product);
	                                                    if (product.ImageURL != null && product.ImageURL.length() > 0) {
	                                                        if (rowNumber % style.productSearchColumnCount == 0) {%>
	                                                        <tr valign="top" align="center"><%}%>
	                                                            <td class="thumbnailContent">
	                                                                <table height="100%" align="center" cellpadding="5" cellspacing="0" border="0">
	                                                                    <tr>
	                                                                        <td height="10%" align="center" valign="top">
	                                                                <%
	                                                                if (templatePropertiesBean.trustedCompanies.get(company.Code) != null) {%>
	                                                                            <%= style.featuredText%>
	                                                                <%}%>
	                                                                        </td>
	                                                                    </tr>
	                                                                    <tr>
	                                                                        <td height="80%" align="center" valign="middle">
	                                                                            <img class="thumbnailImage" src="<%=product.ImageURL%>" border="0" onError="img2txt(this)" onClick="ShowPopup('LargeImageContainerDiv<%=n%>');" onMouseOver="ShowBiggerImage('LargeImageContainerDiv<%=n%>');" onMouseOut="ShowDefaultImage('LargeImageContainerDiv<%=n%>');" title="Click to fix the popup image!" />
	                                                                        </td>
	                                                                    </tr>
	                                                                    <tr>
	                                                                        <td height="10%" align="center" valign="top">
	                                                                            <%
	                                                                            if (product.Price != null && product.Price.length() > 0) {%>
	                                                                                $<%= StringEscapeUtils.escapeHtml(product.Price)%><%
	                                                                            }%>
	                                                                            <a href="<%= productURL %>" target="_blank"><img src="<%= style.orderButtonImageURL%>" width="40" height="14" border="0" /></a>
	                                                                        </td>
	                                                                    </tr>
	                                                                </table>
	                                                                <div style="display:none; position:absolute; z-index:2; color:#000000; " id="LargeImageContainerDiv<%=n%>">
	                                                                    <table bgcolor="#CCCCCC" border="5" bordercolor="#35557C" cellpadding="5px" cellspacing="0">
	                                                                        <tr>
	                                                                            <td valign="top" align="center">
	                                                                                <div align="left"><img src="/images/common/close.gif" onclick="HidePopup('LargeImageContainerDiv<%=n%>');" style="cursor:pointer" title="Close" /></div>
	                                                                                <div style="width:320px"><img src="<%=product.ImageURL%>" class="largeImage" height="240px" /></div>
	                                                                            </td>
	                                                                            <% if (product.Description != null) {%>
	                                                                            <td valign="top" align="left">
	                                                                                <div style="width:160px">
	                                                                                    <%= product.Description%>
	                                                                                </div>
	                                                                            </td>
	                                                                            <%}%>
	                                                                        </tr>
	                                                                        <tr>
	                                                                            <td colspan="2" valign="middle" align="left">
	                                                                            <%
	                                                                                String ProductName = product.Name.replaceAll(" +"," ");
	                                                                                StringBuilder bob = new StringBuilder();
	                                                                                for (String string : ProductName.split(" ")) {
	                                                                                    bob.append(string.substring(0, 1).toUpperCase());
	                                                                                    bob.append(string.substring(1).toLowerCase());
	                                                                                    bob.append(" ");
	                                                                                }
	                                                                                ProductName = bob.toString().trim();
	                                                                            %>
	                                                                                <%= StringEscapeUtils.escapeHtml(ProductName)%>
	                                                                                <br />
	                                                                                <%= company.Name %>
	                                                                                <br />
	                                                                                <%= company.Country %>
	                                                                            </td>
	                                                                        </tr>
	                                                                    </table>
	                                                                </div>
	                                                            </td><%
	                                                     if (rowNumber % style.productSearchColumnCount == (style.productSearchColumnCount - 1)) {%>
	                                                        </tr>
	                                                    <%}
	                                                rowNumber++;
	                                                n++;
	                                            }
			                            	}
                                        }%>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <table border="0" cellspacing="0" cellpadding="0" width="100%">
                                                <tr>
                                                    <td>
                                                        <center>
                                                        <%
                                                            catgenContext.PageNumber = (catgenContext.PageNumber == 0) ? 1 : catgenContext.PageNumber;
                                                            int pageCount = totalProductCount / catgenContext.PageSize;
                                                            String pagerURL = "Search.html?" + request.getQueryString();
                                                            if (pageCount > 1) {%>|&nbsp;<%}
                                                                    for (int i = 1; i <= pageCount; i++) {
                                                                        if (pagerURL != null) {
                                                                            if (pagerURL.indexOf("&pagenumber=") > 0) {
                                                                                pagerURL = pagerURL.replaceAll("&pagenumber=[0-9]+", "&pagenumber=" + i);
                                                                            } else {
                                                                    pagerURL = pagerURL + "&pagenumber=" + i;
                                                                }
                                                            }
                                                            if (pageCount > 1) {
                                                                if (i == catgenContext.PageNumber) {%>
                                                                    <%= i%>&nbsp;|&nbsp;<%
                                                                } else {%>
                                                            <a href="<%= pagerURL%>"><%= i%></a>&nbsp;|&nbsp;<%
                                                                }
                                                            }
                                                        }
                                                        %>
                                                        </center>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            <%}%>
                            </td>
                            <%--right category here --%>
                            <%
                        if(Style.RIGHT_CATEGORY.equals(style.includeCategoryList)){%>
	                        <td class="categoryBackground" valign="top" width=<%=Constants.CATEGORY_COLUMN_WIDTH %>>
                                    <div class="contents">
                                    <%
	                            for (Category cat : templatePropertiesBean.categories) {
	                                if("".equals(cat.categoryKey)){%>
	                                	<div class="categoryTitle"><%= cat.categoryValue %></div><%
	                                } else{%>
	                                    <a href="<%= catgenContext.RelativePath%>Search.html?category=<%=cat.categoryKey%>&pagesize=<%=catgenContext.PageSize%>&singlePageTemplate=true&test=correct&pricerange=1">
	                                        <div id='<%=cat.categoryKey%>' class="categoryList" onmouseover="this.className = 'hoverList';" onmouseout="this.className = 'categoryList';">
	                                            <%=cat.categoryValue%>
	                                        </div>
	                                    </a><%
	                              	}
	                            }%>
                                    </div>
                            </td>
                        <%}%>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr valign="middle">
                <td align="center">
                    <%=(networkMarket.Footer != null) ? networkMarket.Footer : ""%>
                </td>
            </tr>
            <tr valign="middle">
            	<td align="center">
                    
            	</td>
            </tr>
        </table>
        <p align="center">
            <a href="http://www.openentry.com">Powered by OpenEntry using the latest cloud computing technology on Google and Amazon tools and servers</a>
        </p>
		<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
		<input type="hidden" name="Template Name" value="Home_<%=templatePropertiesBean.catgenPageContext.Template %>.jsp"></input>
    </body>
</html>
<%
}catch(Exception e){
	e.printStackTrace();
}finally{
	conn.close();
}%>
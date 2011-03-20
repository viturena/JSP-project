<%@page import = "com.catgen.thread.*,com.catgen.*,com.catgen.factories.*,com.catgen.helper.*,org.apache.commons.lang.StringEscapeUtils,org.apache.catalina.util.*,java.util.List,java.util.HashMap,java.util.*,java.net.URLEncoder,java.sql.Connection,java.util.Date" %>
<%@page contentType="text/html;charset=UTF-8"%>
<%
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
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta name="keywords" content="<%=networkMarket.Keywords%>" />
        <meta name="description" content="Online Marketplace hosted by OpenEntry on Google and Amazon tools and servers" />
        
        <script type="text/javascript" src="../js/balloon/balloon.config.js"></script>
		<script type="text/javascript" src="../js/balloon/balloon.js"></script>
		<script type="text/javascript" src="../js/balloon/box.js"></script>
		<script type="text/javascript" src="../js/balloon/yahoo-dom-event.js"></script>
		<script type="text/javascript">
		   // white balloon, with default configuration
		   var whiteBalloon    = new Balloon;
		
		   // Stemless blue balloon, with custom image
		   var blueBalloon  = new Balloon;
		   blueBalloon.balloonTextSize  = '90%';
		   blueBalloon.images           = '../images/balloons';
		   blueBalloon.balloonImage     = 'balloon.png';
		   blueBalloon.vOffset          = 15;
		   blueBalloon.shadow           = 0;
		   blueBalloon.stem             = false; // no stems, just the body
		
		   // Plain box with no fancy balloon images
		   var plainBox         = new Box();
		   plainBox.bgColor     = 'ivory';
		   plainBox.borderStyle = '2px solid blue';
		
		 </script>
        
        
        <title><%=networkMarket.Name %></title>
          <script type="text/javascript">
       // dd menu
          var timeout         = 500;
          var closetimer		= 0;
          var ddmenuitem      = 0;

          // open hidden layer
          function mopen(id)  
          {	
          	// cancel close timer
          	mcancelclosetime();
          	
          	// close old layer
          	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';
          	
          	// get new layer and show it
          	ddmenuitem = document.getElementById(id);
          	ddmenuitem.style.visibility = 'visible';
          }

          // close showed layer
          function mclose()
          {
          	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';
          }

          // go close timer
          function mclosetime()
          {
          	closetimer = window.setTimeout(mclose, timeout);
          }

          // cancel close timer
          function mcancelclosetime()
          {
          	if(closetimer)
          	{
          		window.clearTimeout(closetimer);
          		closetimer = null;
          	}
          }

          // close layer when click-out
          document.onclick = mclose; 

          var fixedImageActive = false;
          var biggerImageActive = false;

          function ShowHideFixedBiggerImage(div)
          {
          	if(fixedImageActive){
          		HidePopup(div);
          	}
          	else{
          		ShowPopup(div);
          	}
          }

          function ShowPopup(div){
          	ShowBiggerImage(div);
          	fixedImageActive = true;
          }
          //image scroller
          function ShowBiggerImageCss(div, event)
          {
        	  pos_y = event.offsetY?(event.offsetY):event.pageY-document.getElementById("products").offsetTop;
          	if(!biggerImageActive){
          		document.getElementById(div).style.left = (screen.width/2 - 400) + "px";
          		document.getElementById(div).style.top = (pos_y - 220)+"px";
          		document.getElementById(div).style.display = "inline";
          		biggerImageActive = true;
          	}
          }
          function ShowDefaultImage(div)
          {
          	if(!fixedImageActive){
          		document.getElementById(div).style.display = "none";
          		biggerImageActive = false;
          	}
          }
          function HidePopup(div)
          {
          		document.getElementById(div).style.display = "none";
          		fixedImageActive = false;
          		biggerImageActive = false;
          }
                    
          </script>
<link rel="stylesheet" href="../style/<%=networkMarket.NetworkMarketID %>.css" type="text/css"></link>
    </head>
    <body>
    	<div id="container">
        	<div id="header">
        		<div id="logo">
        			<span><%= networkMarket.LogoImage%></span>
        		</div>
        		<div id="banner">
        			<span><%= networkMarket.Header%></span>
        		</div>
        		<div id="search-box">
        			<form action="<%= catgenContext.RelativePath%>Search.html" method="get">
        				<input type="hidden" name="pagesize" value="<%=catgenContext.PageSize%>" />
						<input type="hidden" name="singlePageTemplate" value="true" />
						<input type="hidden" name="test" value="correct"></input>
						<input type="hidden" name="defSearchText" value = "<%= style.defaultSearchText%>"></input>
	        			<% if (style.includeCategorySearch) {%>
		        			<select name="category" id="category">
								<option value = ''>--- Select ---</option><%
								for (Category cat : templatePropertiesBean.categories) {%>
									<option value = '<%=cat.categoryKey%>'><%=cat.categoryValue%></option>
								<%}%>
	              			</select>
						<%}%>
						<% if (style.includeCountrySearch) {%>
							<select name="country" id="country">
								<option value = ''><%= style.countryText %></option><%
								for (String country : templatePropertiesBean.countries){%>
									<option value = '<%= country%>'><%= country%></option>
								<%}%>
							</select>
						<%}%>
						<% if (style.includePriceSearch) { %>
							<select name="pricerange" id="pricerange">
								<option value="1" >Any Price</option><%
								for (PriceRange pr : templatePropertiesBean.priceRanges) {
									if (pr.value != 1) {%>
										<option value="<%=pr.value%>" <% if (catgenContext.priceRangeCode == pr.value) {%>selected<% }%>><%=pr.display%></option><%
									}
								}%>
							</select>
						<%}%>
						<input type="text" name="search" id="search" size=15 onclick = "this.value=''" value="<%= style.defaultSearchText%>"/>
	                    <input name="imageField" type="image" id="imageField" src="<%= style.searchButtonImageURL%>" align="top" />
					</form>
        		</div>
        	</div>
        	<div id="menu-nav">
        			<ul><%
        				String prefix=catgenContext.RelativePath;
        				String suffix=".html?singlePageTemplate=true&pagesize="+catgenContext.PageSize;
        				%>
        					<li id="home_text"><a href="<%=prefix%>Home<%=suffix%>">Home</a></li> | 
        				<li><a href="<%=prefix%>Vendors<%=suffix%>"><%= style.vendorsText%></a></li> | 
        				<%
                                            if (templatePropertiesBean.mtPages != null && templatePropertiesBean.mtPages.size() > 0) {
                                                for (Page mtPage : templatePropertiesBean.mtPages) {
                                                	if(!mtPage.Hidden){%>
                                            			<li><a href="<%= catgenContext.RelativePath%><%= URLEncoder.encode(Utils.getSafeString(mtPage.Name).trim())%>?singlePageTemplate=true&pagesize=<%=catgenContext.PageSize%>"><%= StringEscapeUtils.escapeHtml(mtPage.Name)%></a> </li>| <%
                                                	}
                                                }
                                            }%>
        				<li><a href="<%=prefix%>Feedback<%=suffix%>">Feedback</a></li>
        			</ul>
        		</div>
        		<%
	                if ("Home".equalsIgnoreCase(catgenContext.PageName)) {%>
	                <div style="width: 100%;">
		        		<div id="home-page-description">
		        			<%=(networkMarket.Description != null) ? networkMarket.Description : ""%>
		        		</div>
		        		<div id="recent-vendors">
		        			<div id="rv-title">Recently added vendors </div>
							<div id="recent-verdor-list">
									<marquee height="100" onmouseout="this.start()" onmouseover="this.stop()" title="Recently Added vendors" id="marquee" truespeed="" scrolldelay="35" scrollamount="1" direction="up">
									<ul>
									<%
		                            int totalPCount=0;
                            		int rowno = 0;
		                            for (Company comp : templatePropertiesBean.mtCompanies) {%>
			        					<li><%
			                            	rowno++;
			                                String companyURL = Utils.getCompanyURL(comp, catgenContext.RelativePath, catgenContext.ReferralID);
			                                String trustSealImageURL = (templatePropertiesBean.trustedCompanies.get(comp.Code) != null)?style.trustSealImageURL:"";
			                                String companyName = StringEscapeUtils.escapeHtml(comp.Name);
			                                String country = StringEscapeUtils.escapeHtml((comp.Country!=null && comp.Country.length()>0)?comp.Country:"---");
			                                int pCount = ProductFactory.getVendorProductCount(conn, comp.Code);
			                                totalPCount+=pCount;
			                                boolean featured = CompanyFactory.isCompanyFeaturedForNM(conn, networkMarket.NetworkMarketID,comp.Code);
			                                System.out.println(featured);
			                                String urlToProductPage = URLEncoder.encode(comp.Code, "UTF-8")+"/Products.html?singlePageTemplate=true&pagesize="+catgenContext.PageSize;%>
			                                
			                                <a href="<%= companyURL%>" target="_blank"><%=companyName %></a> &nbsp;<%=country%><br />
			                                <a href="<%=urlToProductPage%>"><%=pCount%>&nbsp;<%= style.productsText%></a>
		                            	</li>
		                            <%} %>
										</ul>
								</marquee>
							</div>
		        		</div>
		        	</div>
		        	<%} 
        			String titleBarText = "";
        			if("Home".equalsIgnoreCase(catgenContext.PageName)){
        				titleBarText = style.featuredText + " " + style.productsText;
        			}else if("Search".equalsIgnoreCase(catgenContext.PageName)){
        				titleBarText = style.searchResultText;
        			}else if("Vendors".equalsIgnoreCase(catgenContext.PageName)){
        				titleBarText = style.vendorsText;
        			}else if("Feedback".equalsIgnoreCase(catgenContext.PageName)){
        				titleBarText = "Feedback";
        			}else if("Products".equalsIgnoreCase(catgenContext.PageName)){
        				titleBarText = catgenContext.Company.Name+" "+style.productsText;
        			}
        			%>
        			<div style="clear:both;"></div>
        	<div id="body-container">
        		<div id="category-list">
					<ul id="category">
					<%
					if(templatePropertiesBean.categories.size()>0){
						Category allCategoryLink = templatePropertiesBean.categories.get(0);
						if (!allCategoryLink.categoryValue.equalsIgnoreCase("All Categories")){
						%>
					<li>
					<a href="<%= catgenContext.RelativePath%>Search.html?category=&pagesize=<%=catgenContext.PageSize%>&singlePageTemplate=true&test=correct&pricerange=1">
							All Categories</a>
					</li>
					<% } %>
					<% } else { %>
					<li>
					<a href="<%= catgenContext.RelativePath%>Search.html?category=&pagesize=<%=catgenContext.PageSize%>&singlePageTemplate=true&test=correct&pricerange=1">
							All Categories</a>
					</li>
					<% } %>
					
					<%
	                    for (Category cat : templatePropertiesBean.categories) {%>
							<li>
								<a href="<%= catgenContext.RelativePath%>Search.html?category=<%=cat.categoryKey%>&pagesize=<%=catgenContext.PageSize%>&singlePageTemplate=true&test=correct&pricerange=1">
									<%=cat.categoryValue%>
								</a>
							</li>
	                    <%}%>
                   	</ul>
        		</div>
        		
        		<div id="body-content">
        			<div id="title-bar">
        				<span><%= titleBarText%></span>
        			</div>
        			<div id="body-data"><%
        				if("Home".equalsIgnoreCase(catgenContext.PageName)){ 
        					if (templatePropertiesBean.networkMarketFeaturedProducts != null && templatePropertiesBean.networkMarketFeaturedProducts.size() > 0) {
                                productDisplayPage = true;
                                displayProducts = templatePropertiesBean.networkMarketFeaturedProducts;
                            }
        				}else if("Search".equalsIgnoreCase(catgenContext.PageName)){ %>
	        				<div id="search-info">
	        					<div id="search-keyword-text"><%= style.keywordText+":" %></div>
	        					<div id="search-keyword"><%=(catgenContext.Search != null) ? catgenContext.Search : ""%></div>
       							<div id="search-category-trail">
									<div id="search-keyword-text"><%= style.categoryText+":" %></div><%
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
										String link = catgenContext.RelativePath+"Search.html?category=#CATKEY#&pagesize="+catgenContext.PageSize+"&singlePageTemplate=true&test=correct&pricerange=1";
										for(int i=origin.length();i<=catKey.length();i+=Constants.CHARS_PER_CATEGORY){
											String categoryName = CategoryFactory.getCategoryNameByCategoryKeyFromXml(conn,catKey.substring(0,i));%>
											<a class="intermediate-category" href="<%=link.replaceAll("#CATKEY#",catKey.substring(0,i))%>"><%=categoryName+" > " %></a> 
										<%} 
									}%>
								</div><%
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
								} else{
									%>
									<div>Not Found</div>
								<%}%>
							</div>
        				<%}else if("Vendors".equalsIgnoreCase(catgenContext.PageName)){ 
	        				if (templatePropertiesBean.mtCompanies != null && templatePropertiesBean.mtCompanies.size() > 0) {%>
		        				<div id="vendors">
		        					<div id="vendor-list-title">
		        						<div id="title-rowno">#</div>
		        						<div id="title-featured"><%=style.featuredText %></div>
		        						<div id="title-company"><%=style.companiesText %></div>
		        						<div id="title-country"><%=style.countryText %></div>
		        						<div id="title-productcount"><%=style.productsText %> Count</div>
		                            	<div id="clear"></div>
		        					</div><%
		                            int totalPCount=0;
                            		int rowno = 0;
		                            for (Company comp : templatePropertiesBean.mtCompanies) {%>
			        					<div id="vendor"><%
			                            	rowno++;
			                                String companyURL = Utils.getCompanyURL(comp, catgenContext.RelativePath, catgenContext.ReferralID);
			                                String trustSealImageURL = (templatePropertiesBean.trustedCompanies.get(comp.Code) != null)?style.trustSealImageURL:"";
			                                String companyName = StringEscapeUtils.escapeHtml(comp.Name);
			                                String country = StringEscapeUtils.escapeHtml((comp.Country!=null && comp.Country.length()>0)?comp.Country:"---");
			                                int pCount = ProductFactory.getVendorProductCount(conn, comp.Code);
			                                totalPCount+=pCount;
			                                boolean featured = CompanyFactory.isCompanyFeaturedForNM(conn, networkMarket.NetworkMarketID,comp.Code);
			                                System.out.println(featured);
			                                String urlToProductPage = URLEncoder.encode(comp.Code, "UTF-8")+"/Products.html?singlePageTemplate=true&pagesize="+catgenContext.PageSize;%>
			                                
			                                <div id="value-rowno"><%=rowno%></div>
			                                <div id="value-featured">&nbsp;<%if (featured) { %><img src="<%=trustSealImageURL %>"/><%} %></div>
			                                <div id="value-company"><a href="<%= companyURL%>" target="_blank"><%=companyName %></a></div>
			                                <div id="value-country"><%=country%></div>
			                                <div id="value-productcount"><a href="<%=urlToProductPage%>"><%=pCount%>&nbsp;<%= style.productsText%></a></div>
		                            		<div id="clear"></div>
		                            	</div>
		                            <%} %>
		                            <div id="total-vendor-count">Vendor Count: <span><%=rowno %></span></div>
		                            <div id="total-product-count">Product Count: <span><%=totalPCount %></span></div>
	                            	<div id="clear"></div>
		        				</div>
		                    <%} %>
        				<%}else if("Feedback".equalsIgnoreCase(catgenContext.PageName)){ %>
	        				<div id="feedback">
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
                            		<tr>
                            			<td height="20" colspan="2"></td>
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
		                            		String subject = "OpenEntry: Feedback";
		                            		String subject2 = "Mail sent notification";
		                            		String message = "Subject: "+request.getParameter("subField")+"\n\n"+request.getParameter("messageText")+MailMsgs.NOTIFICATION_MESSAGE;
		                            		
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
	        				</div>
        				<%}else if("Products".equalsIgnoreCase(catgenContext.PageName)){ %>
	        				<div id="vendor-products"><%
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
								}%>
	        				</div>
	        			<%}else{
	        				%><div id="content-pages"><%
		        			if (catgenContext.PageName != null && templatePropertiesBean.mtPages.size() > 0) {
		                        for (Page pg : templatePropertiesBean.mtPages) {%>
			        				<div id="content-page"><%
			                            if (catgenContext.PageName.equalsIgnoreCase(pg.Name)) {%>
				                            <div id="page-name"><%=catgenContext.PageName%></div>
				                            <div id="page-desc"><%= (pg.Description != null) ? pg.Description : ""%></div><%
		                            	}%>
			        				</div><%
	                        	}
	                    	}%></div><%
	        			}
						
       					if(productDisplayPage){ 
       						if(displayProducts!=null){%>
		        				<div id="products"><%
		        				int n = 1;
                                int rowNumber = 0;
								for (Product product : displayProducts) {
		      						Company company = CompanyFactory.getCachedCompanyByCode(conn, product.CompanyCode);
		      						String productURL = Utils.getReferralURL(catgenContext.RelativePath, catgenContext.ReferralID, company.Code, "product,marketfeatured,home", Utils.getProductURL(company, product, catgenContext.RelativePath, catgenContext.ReferralID), product.Code);
                                    String currency = Utils.getCurrencySymbol(company, product);
                                    if (product.ImageURL != null && product.ImageURL.length() > 0) {
                                       	String code = StringEscapeUtils.escapeHtml(product.Code);
                                       	String companyname = StringEscapeUtils.escapeHtml(CompanyFactory.getCompanyByCode(conn,product.CompanyCode).Name);
                                       	companyname = (companyname!=null & companyname.length()>0)?companyname:"";
                                       	companyname = (companyname.length()>18)?companyname.substring(0,18)+"...":companyname;
                                       	String description = StringEscapeUtils.escapeHtml(product.Description);
                                       	String imageurl = StringEscapeUtils.escapeHtml(product.ImageURL);
                                       	String name = StringEscapeUtils.escapeHtml(product.Name);
                                       	name = (name!=null & name.length()>0)?name:"";
                                       	name = (name.length()>18)?name.substring(0,18)+"...":name;
                                       	String price = StringEscapeUtils.escapeHtml(product.Price);
                                       	String siteurl = StringEscapeUtils.escapeHtml(product.URL);
                                       	String featured = StringEscapeUtils.escapeHtml((product.Featured)?"Featured":"");
                                       	String companyurl = company.CompanyURL;%>
                                       	<div id="product">
                                  			<div id="code"><%=code %></div>
                                  			<div id="companyname"><a href="<%=companyurl%>" target="_blank"><%=companyname %></a></div>
                                  			<div id="currency"><%=currency %></div>
                                  			<div id="description"><%=description %></div>
                                  			<div id="imageurl">
                                  			<img src="<%=imageurl %>" 
                                  			onmouseover="whiteBalloon.showTooltip(event,'load:LargeImageContainerDiv<%=n%>',0)"
                                  			onclick="whiteBalloon.showTooltip(event,'load:LargeImageContainerDiv<%=n%>',1)"
                                  			title="Click to fix the popup image!" />
                                  			</div>
                                  			<div id="name"><a href="<%=productURL%>" target="_blank"><%=name %></a></div>
                                  			<div id="price">
                                  				<% if (price == null) price= "N/A"; %>
                                  			<%=price %></div>
                                  			<div id="siteurl"><%=siteurl %></div>
                                  			<div id="featured"><%=featured %></div>
                                  		</div>
                                  		<div style="display:none;color:#000000;" id="LargeImageContainerDiv<%=n%>">
                                           <table width=530 style='table-layout:fixed' bgcolor="#CCCCCC" border="5" bordercolor="#35557C" cellpadding="5px" cellspacing="0">
                                               <col width=330>
											   <col width=200>
                                               <tr>
                                                   <td width="330" valign="top" align="center">
                                                       <div style="max-width: 320px;"><img src="<%=imageurl%>" class="largeImage" style="max-width: 320px;max-height: 320px;"/></div>
                                                   </td>
                                                   <% if (description != null) {%>
                                                   <td width="200" valign="top" align="left">
                                                       <div style="max-width:190px">
                                                           <%= description%>
                                                       </div>
                                                   </td>
                                                   <%}%>
                                               </tr>
                                               <tr>
                                                   <td width="530" colspan="2" valign="middle" align="left">
                                                   <%
                                                       String ProductName = name.replaceAll(" +"," ");
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
                                       <%}
                                    n++;rowNumber++;} %>
       								<div id="clear"></div>
		        				</div>
	        				<%}
       						catgenContext.PageNumber = (catgenContext.PageNumber == 0) ? 1 : catgenContext.PageNumber;
                            int pageCount = totalProductCount / catgenContext.PageSize+1;
                            String pagerURL = "Search.html?" + request.getQueryString();%>
                            <div id="pageno-list"><%
	                            for (int i = 1; i <= pageCount; i++) {
	                            	if (pagerURL != null) {
	                                	if (pagerURL.indexOf("&pagenumber=") > 0) {
	                                    	pagerURL = pagerURL.replaceAll("&pagenumber=[0-9]+", "&pagenumber=" + i);
	                                    } else {
	                                    	pagerURL = pagerURL + "&pagenumber=" + i;
	                                	}
	                            	}
		                            if (pageCount > 1) {
		                            	if(i==1){
		                            		%><div id="pageno-label">Pages &gt;&gt; </div><%
		                            	}
		                                if (i == catgenContext.PageNumber) {%>
		                                    <div id="pageno-current"><%=i%></div><%
		                                } else {%>
		                                    <div id="pageno-other"><a href="<%=pagerURL%>"><%=i%></a></div><%
		                                }
		                            }
		                        }%>
       							<div id="clear"></div>
                            </div><%
       					}%>
       				</div>
       				<div id="clear"></div>
       			</div>
       			<div id="clear"></div>
       		</div>
        	<div id="footer">
        		<div id="footer-content">
        			<span><%=(networkMarket.Footer != null) ? networkMarket.Footer : ""%></span>
        		</div>
        		<div id="openentry">
        			<span>Powered by OpenEntry on Google and Amazon Cloud Computing Environment.</span>
        		</div>
        	</div>
        </div>
        <input type="hidden" name="Template Name" value="Home_<%=templatePropertiesBean.catgenPageContext.Template %>.jsp"></input>
    </body>
</html>
<%
}catch(Exception e){
	e.printStackTrace();
}finally{
	conn.close();
}%>
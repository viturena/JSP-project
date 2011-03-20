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
<html>
    <head><%
		String browserTitle = "";
    	String sufx = "Nepal Tourism Year 2011,visit nepal 2011,handicraft,jewelry,trekking,tours,travels";
		if("Products".equalsIgnoreCase(catgenContext.PageName)){
			String coname = catgenContext.Company.Name;
			coname = (coname!=null)?(coname+" ::: "):"";
			browserTitle += coname;
		}
		browserTitle+=sufx;%>
        <meta name="google-site-verification" content="v5FxkeI9pYBS0FHdC-qXhcMqIOUfLSbrJPD0KfUuEGQ" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title><%=browserTitle %></title>
        <link rel="stylesheet" href="http://www.icloudtech.com/resources/nty2011/style/nty2011.css" type="text/css"></link>
        <%--<link rel="stylesheet" href="http://localhost:8080/NetMarket/style/nty2011.css" type="text/css"></link> --%>
	<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-17804379-6']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>
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
        		<div id="menu-nav">
        			<ul><%
        				String prefix=catgenContext.RelativePath;
        				String suffix=".html";
        				if(style.homeText!=null && style.homeText.length()>0){
        					%><li><a href="<%= catgenContext.RelativePath%>Home.html"><%= style.homeText%></a></li>
					<%}%>
					<li><a href="<%=prefix%>Vendors<%=suffix%>"><%= style.vendorsText%></a></li><%
                        if (templatePropertiesBean.mtPages != null && templatePropertiesBean.mtPages.size() > 0) {
                            for (Page mtPage : templatePropertiesBean.mtPages) {
                            	if(!mtPage.Hidden){%>
                        			<li><a href="<%= catgenContext.RelativePath%><%= URLEncoder.encode(Utils.getSafeString(mtPage.Name).trim())%>"><%= StringEscapeUtils.escapeHtml(mtPage.Name)%></a></li><%
                            	}
                            }
                        }%>
        				<li><a href="<%=prefix%>Feedback<%=suffix%>">Feedback</a></li>
        			</ul>
        		</div>
        		<div id="search-box">
        			<form action="<%= catgenContext.RelativePath%>Search.html" method="get">
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
        	<div id="body-container">
        		<div id="category-list">
					<ul id="category">
							<li>
								<a href="Search.html?category=&pricerange=1">
									All Categories
								</a>
							</li><%
	                    for (Category cat : templatePropertiesBean.categories) {%>
							<li>
								<a href="<%= catgenContext.RelativePath%>Search.html?category=<%=cat.categoryKey%>&pricerange=1">
									<%=cat.categoryValue%>
								</a>
							</li>
	                    <%}%>
                   	</ul>
        		</div>
        		<div id="body-content"><%
	                if ("Home".equalsIgnoreCase(catgenContext.PageName)) {%>
		        		<div id="home-page-description">
		        			<%=(networkMarket.Description != null) ? networkMarket.Description : ""%>
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
        			<div id="title-bar">
        				<span>
        					<%= titleBarText%><br/><br/>Did you LIKE NepalTourismYear2011.info?<br/><iframe src="http://www.facebook.com/plugins/like.php?href=www.nepaltourismyear2011.info&amp;layout=standard&amp;show_faces=true&amp;width=450&amp;action=like&amp;colorscheme=dark&amp;height=80" scrolling="no" frameborder="0" style="border:none; overflow:hidden; width:450px; height:80px;color: #fff;" allowTransparency="true"></iframe>
        				</span>
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
									<div id="search-keyword-text"><%= style.categoryText+":" %></div>
									<a class="intermediate-category" href="Search.html?category=&pricerange=1">All Categories&nbsp;&gt;&nbsp;</a><%
									String catKey = catgenContext.searchCategory;
									String origin = "C01044";
									catKey = (catKey!=null)?catKey:origin;
									List<Category> categories = CategoryFactory.getCategories(conn, networkMarket.NetworkMarketID);
									for(Category category: categories){
										if(category.categoryKey!=null && category.categoryKey.length()>0 && catKey.contains(category.categoryKey)){
											origin = category.categoryKey;
											break;
										}
									}
									if(CategoryFactory.isValidXmlCategoryKey(conn, catKey)){
										String link = catgenContext.RelativePath+"Search.html?category=#CATKEY#&pricerange=1";
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
								} %>
							</div>
        				<%}else if("Vendors".equalsIgnoreCase(catgenContext.PageName)){ 
	        				if (templatePropertiesBean.mtCompanies != null && templatePropertiesBean.mtCompanies.size() > 0) {%>
		        				<div id="vendors">
		        					<div id="vendor-list-title">
		        						<div id="title-rowno">#</div>
		        						<div id="title-featured">Featured</div>
		        						<div id="title-company">Online Shops</div>
		        						<div id="title-country">Country</div>
		        						<div id="title-productcount">Products</div>
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
			                                String urlToProductPage = URLEncoder.encode(comp.Code, "UTF-8")+"/Products.html";%>
			                                
			                                <div id="value-rowno"><%=rowno%></div>
			                                <div id="value-featured">&nbsp;<%if (featured) { %><img src="<%=trustSealImageURL %>"/><%} %></div>
			                                <div id="value-company"><a href="<%=urlToProductPage%>"><%=companyName %></a></div>
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
	        					<form action="<%= catgenContext.RelativePath%>Feedback.html" method="get">
                            	<input type="hidden" name ="feedbackSubmitted" value="true"/>
                            	<table border="0" cellpadding="0" cellspacing="10" align="center"><%
									String feedbackSubmitted = request.getParameter("feedbackSubmitted");
	                            	if(feedbackSubmitted==null || feedbackSubmitted.length()==0){
	                            		String subj = request.getParameter("subj");
	                            		subj = (subj!=null && subj.length()>0)?subj:"";
	                            	%>
                            		<tr>
                            			<td colspan="2" align="center"><h2>Write to us</h2></td>
                            		</tr>
                            		<tr>
                            			<td colspan="2" align="center">You can use this form to mail us.</td>
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
                            			<td align="left"><input type="text" name="subField" size=50 value="<%=subj%>" /></td>
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
		                            		String subject = "NTY2011: Feedback";
		                            		String subject2 = "NTY2011: Mail sent notification";
		                            		String message = "Subject: "+request.getParameter("subField")+"\n\n"+request.getParameter("messageText")+MailMsgs.NOTIFICATION_MESSAGE;
		                            		
		                            		MailObj mailObj = new MailObj();
		                            		mailObj.from = "icloud.technologies@gmail.com";
		                            		
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
                               		products = SearchHelper.findProductsBySearchEntries(conn, catgenContext);
								    session.setAttribute("PRODUCTS_IN_SESSION", products);
								} else {
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
                                       	companyname = (companyname!=null && companyname.length()>0)?companyname:"";
										String companyname1 = companyname;
                                       	companyname = (companyname.length()>20)?companyname.substring(0,20)+"...":companyname;
                                       	String description = StringEscapeUtils.escapeHtml(product.Description);
                                       	description = (description !=null && description.length()>0)?description :"";
                                       	String imageurl = StringEscapeUtils.escapeHtml(product.ImageURL);
                                       	String name = StringEscapeUtils.escapeHtml(product.Name);
                                       	name = (name!=null && name.length()>0)?name:"";
                                       	String name1 = name;
										name = (name.length()>25)?name.substring(0,25)+"...":name;
                                       	String description1 = (description.length()>1000)?description.substring(0,1000)+"...":description;
                                       	String price = StringEscapeUtils.escapeHtml(product.Price);
                                       	String siteurl = StringEscapeUtils.escapeHtml(product.URL);
                                       	String featured = StringEscapeUtils.escapeHtml((product.Featured)?"Featured":"");
                                       	String companyurl = company.CompanyURL;%>
                                       	<div id="product">
                                  			<div id="code"><%=code %></div>
                                  			<div id="companyname"><a href="http://www.<%=MasterMarketFactory.getMasterMarketByCode(conn, networkMarket.NetworkMarketID).DomainName%>/<%=product.CompanyCode %>/Products.html" title="Click for more products by - <%=companyname1%>"><%=companyname %></a></div>
                                  			<div id="currency"><%=currency %></div>
                                  			<div id="description"><%=description %></div>
                                  			<div id="imageurl"><a href="<%=productURL%>" target="_blank" title="<%=name1%>:<%=description1%> -- Hosted on Nepal Tourism Year 2011 online marketplace"><img src="<%=imageurl %>" border="0"/></a></div>
                                  			<div id="name"><a href="<%=productURL%>" target="_blank" title="<%=name1%>:<%=description1%> -- Hosted on Nepal Tourism Year 2011 online marketplace"><%=name %></a></div>
                                  			<div id="price"><%=price %></div>
                                  			<div id="siteurl"><%=siteurl %></div>
                                  			<div id="featured"><%=featured %></div>
                                  		</div>
                                       <%}
		        					} %>
       								<div id="clear"></div>
		        				</div>
	        				<%}
       						catgenContext.PageNumber = (catgenContext.PageNumber == 0) ? 1 : catgenContext.PageNumber;
                            int pageCount = totalProductCount / catgenContext.PageSize+1;
							String qry = (request.getQueryString()!=null)?request.getQueryString():"";
                            String pagerURL = "Search.html?" + qry;%>
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
		                                    <div id="pageno-other">
		                                    	<form name="myform_<%=i %>" action="Products.html" method="post">
		                                    		<input type="hidden" name="pagenumber" value="<%=i%>"/>
		                                    		<a href="javascript:document.myform_<%=i %>.submit();"><%=i %></a>
		                                    	</form>
		                                    </div><%
		                                }
		                            }
		                        }
		                        if("Products".equalsIgnoreCase(catgenContext.PageName)){%>
		                        	<div id="discussion" align="left">
		                        		<br/><br/><b>FACEBOOK FAN PAGE for <%=catgenContext.Company.Name %></b>
		                        		<img src="http://www.icloudtech.com/resources/nty2011/images/facebook.png" border="0"/>
										<div style="background-color:#fff;padding:40px;"><div id="fb-root"></div><script src="http://connect.facebook.net/en_US/all.js#appId=144702545582966&amp;xfbml=1"></script><fb:comments numposts="20" width="640" publish_feed="true" title="<%=catgenContext.Company.Name%> fanpage on Nepal Tourism Year 2011 Online Marketplace"></fb:comments></div>
		                        		If you see no comments above, please click <a href="http://www.nepaltourismyear2011.info/<%=catgenContext.Company.Code %>/Products.html" style="color:#fff;font-weight:bold;">HERE</a> to display comments, if available.<br/><br/>
		                        	</div>
		                        <%} %>
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
        			<%=(networkMarket.Footer != null) ? networkMarket.Footer : ""%>
        		</div>
        		<div id="openentry">
        			<span>&nbsp;&copy; NepalTourismYear2011.info. All rights reserved.<br/>&nbsp;Designed and maintained by <a href="http://www.icloudtech.com" target="_blank" style="color:#fff;cursor:pointer;text-decoration:none;font-weight:bold;">ICLOUDTECH</a> using system developed by <a href="http://www.openentry.com" target="_blank" style="color:#fff;cursor:pointer;text-decoration:none;font-weight:bold;">OPENENTRY.com</a></span>
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
<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*, org.apache.commons.lang.*" %>

<%@page import="com.catgen.helper.SuperNmHelper"%><style type="text/css">
	.thumbnailImage {
	    max-width: 100px;
	    max-height: 100px;
	    width: expression(this.width > 100? "100px": "auto");
	    height: expression(this.height > 100? "100px": "auto");
	}
</style><%
String userId = (String)session.getAttribute("USER");
String type = (String)session.getAttribute("TYPE");
boolean isValidNM = NetMarketFactory.isValidNM(userId, type);
if(!isValidNM){
	session.setAttribute("errcode","504");
}else{ 
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	String marketId = (String)session.getAttribute("USER");
	try{
		List<Product> products = ProductFactory.getNetworkMarketOwnFeaturedProducts(conn, marketId);%>
		<input type="hidden" id="module" value="<%=Constants.FEATURED_PRODUCTS%>"></input>
		<input type="hidden" id="removefeaturedproduct" value="<%=Constants.REMOVE_FEATURED_PRODUCT%>"></input>
		<input type="hidden" id="listfeaturedproducts" value="<%=Constants.LIST_FEATURED_PRODUCTS%>"></input>
		<input type="hidden" id="featureproduct" value="<%=Constants.FEATURE_PRODUCT%>"></input>
		<input type="hidden" id="lazynm" value="<%=Constants.LAZY_NM%>"></input>
		<table cellspacing="5" width="100%">
			<tr>
				<td>
					<h2>Featured Product Auto-Display</h2>
					<i>(If auto-display is set to true, in addition to featured products selected by a Network Market, an additional product,<br/>one for each vendor will be featured on the web-site. To change the auto-display status, click the button below.)</i><br/>
					<input type="button" style="padding:2px 10px;font-size:12;font-weight:bold" value="Auto Display: <%=String.valueOf(NetMarketFactory.getNetworkMarketByCode(conn,marketId).lazy).toUpperCase()%>" onclick="flipLazyStatus('<%=marketId %>')" id="lazy_<%=marketId%>"/><br/><br/><br/>
				</td>
			</tr>
			<tr>
				<td>
					<h2>Featured Products</h2>
					<div id="topdiv"></div>
				</td>
			</tr><%
			int productCount = products.size();
			int i=1;
			for(Product product: products){ %>
				<tr>
					<td>
						<div id="feat_<%=marketId%>_<%=product.CompanyCode%>_<%=product.Code%>">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr>
									<td width="50"><input type="image" src="images/common/Remove.jpg" id="featremove" onclick="removeFeaturedProduct('<%=marketId %>','<%=product.CompanyCode %>','<%=product.Code %>')"></input></td>
									<td width="200"><%
										if(product.CompanyCode!=null){%>
											<%=CompanyFactory.getCompanyByCode(conn, product.CompanyCode).Name %>
										<%}%>
									</td>
									<td width="200">
										<%=product.Name %> [<%=product.Code %>]
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr><%
			}%>
		</table>
		<br/><br/><br/></br>
		<h2>Feature a new Product</h2>
		<table cellspacing="10">
			<tr>
				<td>Select Company</td>
				<td><%
					List<Company> companies = SuperNmHelper.findAllContainedCompaniesBySuperMarketId(conn, marketId);%>
					<select id="select_companies" onchange="listVendorProducts()">
						<option></option>
						<%
							for(Company company: companies){%>
								<option value="<%=company.Code %>"><%=company.Code %>&nbsp;-&nbsp;<%=company.Name %></option>
							<%}
						%>
					</select>
				</td>
			</tr>
			<tr valign="top">
				<td>Select Product</td>
				<td>
					<span id="span_products">
						<select id="select_products" onchange="changeImage()">
						</select>
					</span><br/>
					<div id="div_imageUrlList"></div>
				</td>	
			</tr>
			<tr>
				<td colspan="2">
					<input type="button" id="addFeatProduct" value="Feature this product" onclick="featureThisProduct('<%=marketId %>')"></input>
					<div id="div_image"></div>
				</td>	
			</tr>
		</table>
	<%} finally{
		conn.close();
	} 
}%>
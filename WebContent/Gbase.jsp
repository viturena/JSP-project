<%@ page contentType="text/xml" %><%@ page import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder" %><%
	CatgenPageContext catgenContext = CatgenPageContext.getInstance(pageContext);

	NetworkMarket networkMarket = catgenContext.NetworkMarket;
	if (networkMarket == null)
	{
		response.sendRedirect("CompanyNotFound.jsp");
		return;
	}
%><?xml version="1.0" encoding="UTF-8"?>
<rss version="2.0" xmlns:g="http://base.google.com/ns/1.0">
	<channel>
		<title><%= StringEscapeUtils.escapeXml(networkMarket.Name) %> Google Base Feed</title>
		<description><%= StringEscapeUtils.escapeXml(networkMarket.Name) %> Google Base Feed</description>
		<link><%= StringEscapeUtils.escapeXml(catgenContext.RelativePath) %></link>
<%

	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try {
		List<Product> products = ProductFactory.getMarketProducts( conn, networkMarket.NetworkMarketID);

		if (products != null && products.size() > 0) {
			for( Product product : products ) {
%>
				<item>
					<title><%= StringEscapeUtils.escapeXml(product.Name) %></title>
					<g:brand><%= StringEscapeUtils.escapeXml(product.Name) %></g:brand>
					<g:condition>new</g:condition>
					<description><%= StringEscapeUtils.escapeXml(product.Description) %></description>
					<guid><%= StringEscapeUtils.escapeXml(product.Code) %></guid>
					<g:image_link><%= StringEscapeUtils.escapeXml(product.ImageURL) %></g:image_link>
					<link><%= StringEscapeUtils.escapeXml(product.URL) %></link>
					<g:mpn><%= StringEscapeUtils.escapeXml(product.Code) %></g:mpn>
					<g:price><%= StringEscapeUtils.escapeXml(product.Price) %></g:price>
					<g:product_type><%= StringEscapeUtils.escapeXml(product.ProductLine) %></g:product_type>
					<g:quantity>10</g:quantity>
				</item>
<%
			}
		}

	} finally {
		conn.close();
	}
%>
	</channel>
</rss>

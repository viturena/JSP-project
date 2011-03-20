<%@ page
	import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder,java.text.*"%>
<%

	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try {
		String marketID = request.getParameter("marketid");
		
		if("1".equals(request.getParameter("action")))
		{	
			String m = request.getParameter("netmarket");
			if(m != null && m.length() > 0)
				NetMarketFactory.UpdateNetworkMarket(conn, marketID, Constants.MEMBERS_AND_PRODUCTS);
			else
			{
				for(String companyCode : request.getParameterValues("companyid") )
				{
					//CompanyFactory.UpdateCompany(conn, marketID, companyCode);
				}
			}
		}

		List<Company> companies = CompanyFactory.getNetmarketMembers(conn, marketID);

		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		response.setHeader("Expires", df.format(Calendar.getInstance().getTime() ));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Network Market</title>
</head>
<body>
	<form action="NetworkMarket.jsp" method="post">
	<input type="hidden" name="action" value="1"/>
	<input type="hidden" name="marketid" value="<%= StringEscapeUtils.escapeHtml(marketID) %>"/>
    <input type="submit" name="netmarket" value="Update all &quot;<%= StringEscapeUtils.escapeHtml(marketID) %>&quot; vendors"/><br/><br/>
    <input type="submit" value="Update Selected Members"/>
    
    <br/><br/>
    <% for( Company company: companies ) {  %>
        <input type="checkbox" name="companyid" value="<%= StringEscapeUtils.escapeHtml(company.Code) %>"/> <%= StringEscapeUtils.escapeHtml(company.Code) %>
 	<br>
    <% } 
    
    if(companies != null && companies.size() > 0 ) {%>
	    <br/>
	    <input type="submit" name="netmarket" value="Update all &quot;<%= StringEscapeUtils.escapeHtml(marketID) %>&quot; vendors"/><br/><br/>
	    <input type="submit" value="Update Selected Members"/>
    <% } %>
    </form>

</body>
</html>

<%
	} finally {
		conn.close();
	}
%>
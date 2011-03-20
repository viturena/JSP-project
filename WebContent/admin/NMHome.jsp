<%@ page
	import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*"%>
<!-- NMMS Changes [Registration and Login] : March 2010 -->
<%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try {
		if("1".equals(request.getParameter("action")))
		{
			
			String marketID = (String)session.getAttribute("USER");
			String updateItem = request.getParameter("updateItem");
			if(updateItem != null && updateItem.length()>0){
				NetMarketFactory.UpdateNetworkMarket(conn, marketID, Integer.parseInt(request.getParameter("updateItem"))+1);
			}else{
				NetMarketFactory.UpdateNetworkMarket(conn, marketID, Constants.ALL);
			}
			
			CompanyFactory.ExpiresCompaniesCache();
		}

		List<MasterMarket> markets = MasterMarketFactory.getMasterMembers(conn);
		// NM Update Change : March 2010 - Begin
		HashMap<String, UpdateStatusBean> updateStatusMap = NetMarketFactory.getUpdateStatus(conn);
		// NM Update Change : March 2010 - End
		
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL);
		response.setHeader("Expires", df.format(Calendar.getInstance().getTime() ));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Network Markets</title>
		<style type="text/css">
			body{
				font-family: Arial;
				color: #666;
				font-size: 14px;
			}
			table{
				padding: 0px;
				margin: 0px;
				border: none;
				background-color: #efefef;
			}
			.redtext{
				font-weight: bold;
				color: red;
			}
		</style>
	</head>
	<body>
		<a href="../openentry3/index.jsp">Home</a>
		&nbsp;|&nbsp;
		<%if(session.getAttribute("USER")==null){ %>
			<a href="../Login.jsp">Login</a>
		<%}else{ %>
			<a href="../Logout.jsp">Logout</a>
		<%} %>
		<h1>OpenEntry.com</h1>
		<form action="NetworkMarkets.jsp" method="post">
		<input type="hidden" name="action" value="1"/>
	    <br/><br/>
	    <% 
	    	if(session.getAttribute("USER")!=null){
		    	java.util.Date date = new java.util.Date();
		    	String dateStr = (1900+date.getYear())+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
	    %>
	    <table cellpadding="3">
		    <%
		    	UpdateStatusBean updateStatusBean = updateStatusMap.get((String)session.getAttribute("USER"));
		    %>
		    <tr><td><h3>Market ID: <%=(String)session.getAttribute("USER")%></h3></td></tr>
		    <tr><td><b>Update:</b></td></tr>
	        <tr><td><a href="NetworkMarket.jsp?marketid=<%= URLEncoder.encode((String)session.getAttribute("USER"), "UTF-8" ) %>">Selected Vendors</a></td></tr>
	        <tr><td><a href="NetworkMarkets.jsp?action=1&marketid=<%= URLEncoder.encode((String)session.getAttribute("USER"), "UTF-8" ) %>&updateItem=-2">COMPLETE</a></td></tr>
	        <%
	        	int itemCount = Constants.SHEETS.length;
	        	for(int i=0;i<itemCount;i++){%>
	        		<tr><td><a href="NetworkMarkets.jsp?action=1&marketid=<%= URLEncoder.encode((String)session.getAttribute("USER"), "UTF-8" ) %>&updateItem=<%=i%>"><%= StringEscapeUtils.escapeHtml(Constants.SHEETS[i]) %></a></td></tr>
	        	<%}
	        %>
	        <tr height="20"><td></td></tr>
	        <tr><td>
	        	<% if(updateStatusBean!=null){ %>
		        	<font face="Verdana" color="#666666" size="2">
		        		Update in process for /<%= (String)session.getAttribute("USER") %><br/>
		        		Currently Updating 		: <%= NetMarketFactory.getUpdateStatusValue(updateStatusBean.status) %><br/>
		        		Updation running since 	: <%= updateStatusBean.timestamp %><br/>
		        		Current TimeStamp		: <%= dateStr%>
		        	</font>
	        	<%} %>
	        </td></tr>
	 	</table>
	 	<% 
		// NM Update Change : March 2010 - End
		
	    if (markets != null && markets.size() > 0) {    
	    %>
	    <br>
	    <input type="submit" value="Update"/>
	    <% } %>
	    </form>
	
	<br/><br/><br/>
	</body>
</html>

<%
		}
	} finally {
		conn.close();
	}
%>
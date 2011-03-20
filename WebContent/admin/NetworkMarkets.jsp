<%@ page
	import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*"%>
<%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try {
		String key = request.getParameter("key");
		LoggerFactory.write(conn, "key value="+key);
		if("1".equals(request.getParameter("action")))
		{
			LoggerFactory.write(conn, "inside if");
			String m = request.getParameter("master");
			if(m != null && m.length() > 0)
				MasterMarketFactory.UpdateMasterMarket(conn);
			else
			{
				for(String marketID : request.getParameterValues("marketid") )
				{
					String updateItem = request.getParameter("updateItem");
					if(updateItem != null && updateItem.length()>0){
						NetMarketFactory.UpdateNetworkMarket(conn, marketID, Integer.parseInt(request.getParameter("updateItem"))+1);
					}else{
						NetMarketFactory.UpdateNetworkMarket(conn, marketID, Constants.ALL);
					}
				}
			}
			
			CompanyFactory.ExpiresCompaniesCache();
		}
		LoggerFactory.write(conn,"out of IF");

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
</head>
<body>
	<form action="NetworkMarkets.jsp" method="post">
	<input type="hidden" name="action" value="1"/>
    <input type="submit" name="master" value="Update Master"/>
    
    <br/><br/>
    <% 
    	java.util.Date date = new java.util.Date();
    	String dateStr = (1900+date.getYear())+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
    %>
    <font face="Verdana" color="#666666" size="2">Current TimeStamp: <%= dateStr%></font><br/>
    <table>
    	<tr bgcolor="#DDDDDD">
    		<td width="30"></td>
    		<td width="100" align="left"><font face="Verdana" color="#666666" size="2">Market Id</font></td>
    		<td align="left"><font face="Verdana" color="#666666" size="2">Update Item</font></td>
    		<td width="400" align="left"><font face="Verdana" color="#666666" size="2">Status</font></td>
    	</tr>
    <%
    	LoggerFactory.write(conn, "Markets size:"+markets.size());
    	for( MasterMarket market : markets ) {
    		LoggerFactory.write(conn, "marketId="+market.NetworkMarketID);
    		if(Constants.NM_UPDATE_KEY.equalsIgnoreCase(key)){
    			NetMarketFactory.UpdateNetworkMarket(conn, market.NetworkMarketID, Constants.ALL);
    		}
   		// NM Update Change : March 2010 - Begin
       	UpdateStatusBean updateStatusBean = updateStatusMap.get(market.NetworkMarketID);
    %>
    	<tr bgcolor="#EEEEEE">
	        <td><input type="checkbox" name="marketid" value="<%= StringEscapeUtils.escapeHtml(market.NetworkMarketID) %>"/></td> 
	        <td><a href="NetworkMarket.jsp?marketid=<%= URLEncoder.encode(market.NetworkMarketID, "UTF-8" ) %>"><%= StringEscapeUtils.escapeHtml(market.NetworkMarketID) %></a></td>
	        <td><a href="NetworkMarkets.jsp?action=1&marketid=<%= URLEncoder.encode(market.NetworkMarketID, "UTF-8" ) %>&updateItem=-2">COMPLETE</a>&nbsp;|&nbsp;
	        <%
	        	int itemCount = Constants.SHEETS.length;
	        	for(int i=0;i<itemCount;i++){%>
	        		<a href="NetworkMarkets.jsp?action=1&marketid=<%= URLEncoder.encode(market.NetworkMarketID, "UTF-8" ) %>&updateItem=<%=i%>"><%= StringEscapeUtils.escapeHtml(Constants.SHEETS[i]) %></a>&nbsp;|&nbsp;
	        	<%}
	        %>
	        </td>
	        <td>
	        	<% if(updateStatusBean!=null){%>
		        	<font face="Verdana" color="#666666" size="2">
		        		Update in process for /<%= market.NetworkMarketID %><br/>
		        		Currently Updating 		: <%= NetMarketFactory.getUpdateStatusValue(updateStatusBean.status) %><br/>
		        		Updation running since 	: <%= updateStatusBean.timestamp %>
		        	</font>
	        	<%} %>
	        </td>
        </tr>
 	<% }%>
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
	<a href="/netmarket.zip">netmarket.zip</a>
</body>
</html>

<%
	} finally {
		conn.close();
	}
%>
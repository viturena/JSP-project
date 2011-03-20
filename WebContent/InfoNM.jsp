<%@ page import="com.catgen.*, com.catgen.factories.*, java.sql.*, java.util.*" %>
<input type="hidden" id="module" value="<%= Constants.SUPER_ADMIN %>">
<input type="hidden" id="deleteSelectedNMs" value="<%= Constants.DELETE_SELECTED_NM %>">
<input type="hidden" id="changePremiumStatus" value="<%=Constants.CHANGE_PREMIUM_STATUS%>"/>
<%
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	int pointer = 0;
	String[] color={"#ffffff","#C2CCDE"};
	String key = request.getParameter("key");
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
	if(!isSuperAdmin){
		session.setAttribute("errcode","501");
	}else{
		List<NetworkMarket> networkMarkets = NetMarketFactory.getNetmarketMembers(conn);%>
		<table border="0" cellspacing="2" cellpadding="5">
			<tr style="background-color:#324F81; color: #fff;">
				<td width="30">Delete</td>
				<td width="50">Market Id</td>
				<td width="50">Type</td>
				<td width="80">Market URL</td>
				<td width="140">Network Market Name</td>
				<td width="140">Email ID</td>
				<td width="30">Vendors</td>
				<td width="30">Products</td>
			</tr><%
		for(NetworkMarket networkMarket: networkMarkets){%>
			<tr style="background-color:<%=color[pointer]%>">
				<td><input type="checkbox" name="networkmarkets" value="<%=networkMarket.NetworkMarketID%>" /></td>
				<td><a target="_blank" href="<%=Constants.OE_URL %>/<%=networkMarket.NetworkMarketID %>"><%=networkMarket.NetworkMarketID %></a></td>
				<td align="center"><input type="image" id="spanid_<%=networkMarket.NetworkMarketID%>" src="images/common/<%=NetMarketFactory.getNetworkMarketByCode(conn, networkMarket.NetworkMarketID).premium?"premium":"free" %>.png" onclick="flipType('<%=networkMarket.NetworkMarketID %>')"/></td>
				<td><a target="_blank" href="<%=Constants.OE_URL %>/<%=networkMarket.NetworkMarketID %>"><%=Constants.OE_URL %>/<%=networkMarket.NetworkMarketID %></a></td>
				<td><%=networkMarket.Name %></td>
				<td><%=(networkMarket.ContactEmail!=null)?networkMarket.ContactEmail:"-" %></td>
				<td><%=CompanyFactory.getNMVendorCount(conn,networkMarket.NetworkMarketID) %></td>
				<td><%=ProductFactory.getNMProductCount(conn,networkMarket.NetworkMarketID) %></td>
			</tr>
		<%
		pointer=(pointer==1)?0:1;
		}%>
			<tr>
				<td align="left" colspan="7"><input type="button" value="Delete" onClick="deleteSelectedNMs()" /></td>
			</tr>
			<tr style="background-color:#324F81; color: #fff;">
				<td align="center" colspan="7"><%=networkMarkets.size()%>&nbsp; Network Markets.</td>
			</tr>
		</table><%
	}
}finally{
	conn.close();
}
%>
<%@page import="com.catgen.factories.SuperAdminFactory"%>
<%@page import="com.catgen.factories.MySqlDB"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.catgen.factories.NetMarketFactory"%>
<%@page import="java.util.List"%>
<%@page import="com.catgen.NetworkMarket"%>
<%@page import="com.catgen.Constants"%>

<input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>">
<input type="hidden" id="getCharges" value="<%=Constants.FETCH_CHARGES%>">
<input type="hidden" id="updateCharges" value="<%=Constants.UPDATE_CHARGES%>">
<input type="hidden" id="defaultCharges" value="<%=Constants.DEFAULT_CHARGES%>">
<%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try {
		String userId = (String)session.getAttribute("USER");
		String type = (String)session.getAttribute("TYPE");
		String marketId = userId;
		boolean isValidSuperNM = SuperAdminFactory.isSuperAdmin(userId,type);
		if(!isValidSuperNM){
			session.setAttribute("errcode","501");
		}else{
			List<NetworkMarket> markets = NetMarketFactory.getNetmarketMembers(conn);%>			
			<h3>Update Charges</h3>
			<table cellspacing="0" cellpadding="5" border="0" style="background-color:#eee;">
				<tr>
					<td>Select Network Market</td>
					<td>
						<select id="charges-nm" onchange="getSelectedMarketCharges()">
							<option value="">--- Select ---</option><%
							for(NetworkMarket netMarket: markets){%>
								<option value="<%=netMarket.NetworkMarketID %>"><%=netMarket.NetworkMarketID %>&nbsp;-&nbsp;<%=netMarket.Name %></option>	
							<%}%>
						</select>
					</td>
				</tr>
				<tr>
					<td>Market ID</td>
					<td><input type="text" id="market-charge" disabled></input></td>
				</tr>
				<tr>
					<td>Add Vendor ($)</td>
					<td><input type="text" id="add-vendor" disabled></input></td>
				</tr>
				<tr>
					<td>Feature Vendor ($)</td>
					<td><input type="text" id="feature-vendor"></input></td>
				</tr>
				<tr style="background-color: #ddd;">
					<td colspan="2" align="left">
						<input type="button" value="Update Charges" onclick="updateCharges()"/>
						<input type="button" value="Set charges to default value" onclick="defaultCharges()"/>
					</td>
				</tr>
			</table>
		<%}
	} finally {
		conn.close();
	}
%>
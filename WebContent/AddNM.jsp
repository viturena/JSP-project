<%@page import="com.catgen.helper.NetMarketHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.catgen.NetworkMarket"%>
<%@page import="java.util.List"%>
<%@page import="com.catgen.helper.SuperNmHelper"%>
<%@page import="com.catgen.factories.NetMarketFactory"%>
<%@page import="com.catgen.factories.MySqlDB"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.catgen.Constants"%>
<input type="hidden" id="module" value="<%=Constants.NETWORK_MARKET%>">
<input type="hidden" id="addnm" value="<%=Constants.ADD_NM%>">
<input type="hidden" id="removenm" value="<%=Constants.REMOVE_NM%>">
<%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try{
		String userId = (String)session.getAttribute("USER");
		String type = (String)session.getAttribute("TYPE");
		String marketId = userId;
		boolean isValidSuperNM = NetMarketFactory.isValidNM(userId, type) && SuperNmHelper.isSuperNm(conn,marketId);
		if(!isValidSuperNM){
			session.setAttribute("errcode","507");
		}else{ 	
			List<NetworkMarket> availableNetworkMarkets = SuperNmHelper.findAllContainedNMObjBySuperMarketId(conn, marketId);
			List<NetworkMarket> nonAvailableNetworkMarkets = NetMarketHelper.getOtherNetworkMarkets(conn,availableNetworkMarkets);%>
			
			<h2>Add Network Market</h2>
			<select id="AddNmList"><%
				for(NetworkMarket netMarket: nonAvailableNetworkMarkets){%>
						<option value="<%=netMarket.NetworkMarketID %>"><%=netMarket.NetworkMarketID %>&nbsp;-&nbsp;<%=netMarket.Name %></option>
				<%}%>
			</select><br/><br/>
			<input type="button" style="padding:10px 20px; font-size: 16px;" value="Add selected Network Market" onclick="addNetworkMarket('<%=marketId %>')"><br/><br/><br/><br/><br/>
			<h2>Sub Network Markets</h2>
			<table border="0">
				<tr>
					<td>
						<div id="openListing"><%
							for(NetworkMarket networkMarket: availableNetworkMarkets){
								if(!userId.equalsIgnoreCase(networkMarket.NetworkMarketID)){%>
									<div id="nm2nm_<%= marketId%>_<%=networkMarket.NetworkMarketID%>">
										<table border="0">
											<tr>
												<td width="50"><input type="image" src="images/common/Remove.jpg" onclick="removeNM('<%=marketId %>','<%=networkMarket.NetworkMarketID %>')"></input></td>
												<td width="120"><%=networkMarket.NetworkMarketID %></td>
												<td width="200">[<%=networkMarket.Name %>]</td>
											</tr>
										</table>
									</div>
								<%}
							}%>
						</div>
					</td>
				</tr>
			</table>
		<%}
	}finally{
		conn.close();
	}
%>
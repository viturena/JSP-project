<%@page import="com.catgen.helper.NetMarketHelper"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.catgen.NetworkMarket"%>
<%@page import="java.util.List"%>
<%@page import="com.catgen.helper.SuperNmHelper"%>
<%@page import="com.catgen.factories.NetMarketFactory"%>
<%@page import="com.catgen.factories.MySqlDB"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.catgen.Constants"%>
<%@page import="com.catgen.factories.SuperAdminFactory"%>
<input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>">
<input type="hidden" id="upgradeNm" value="<%=Constants.UPGRADE_TO_SUPERNM%>">
<input type="hidden" id="degradeNM" value="<%=Constants.DEGRADE_FROM_SUPERNM%>">
<%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try{
		String userId = (String)session.getAttribute("USER");
		String type = (String)session.getAttribute("TYPE");
		String marketId = userId;
		boolean isValidSuperNM = SuperAdminFactory.isSuperAdmin(userId,type);
		if(!isValidSuperNM){
			session.setAttribute("errcode","501");
		}else{ 	
			List<NetworkMarket> availableNetworkMarkets = SuperNmHelper.getAllSuperNMList(conn);
			List<NetworkMarket> nonAvailableNetworkMarkets = NetMarketHelper.getOtherNetworkMarkets(conn,availableNetworkMarkets);%>
			
			<h2>Upgrade to Super Network Market</h2>
			<select id="NonSuperNMList"><%
				for(NetworkMarket netMarket: nonAvailableNetworkMarkets){%>
					<option value="<%=netMarket.NetworkMarketID %>"><%=netMarket.NetworkMarketID %>&nbsp;-&nbsp;<%=netMarket.Name %></option>
				<%}%>
			</select><br/><br/>
			<input type="button" style="padding:10px 20px; font-size: 16px;" value="Upgrade" onclick="upgrade2SuperNM()"><br/><br/><br/><br/><br/>
			<h2>Existing Super Network Markets</h2>
			<table border="0">
				<tr>
					<td>
						<div id="superNmMasterDiv"><%
							for(NetworkMarket networkMarket: availableNetworkMarkets){
								if(!userId.equalsIgnoreCase(networkMarket.NetworkMarketID)){%>
									<div id="superNM_<%=networkMarket.NetworkMarketID%>">
										<table border="0">
											<tr>
												<td width="50"><input type="image" src="images/common/Remove.jpg" onclick="degrade2NM('<%=networkMarket.NetworkMarketID %>')"></input></td>
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
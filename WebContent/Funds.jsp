<%@page import="com.catgen.factories.SuperAdminFactory"%>
<%@page import="com.catgen.factories.MySqlDB"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.catgen.factories.NetMarketFactory"%>
<%@page import="java.util.List"%>
<%@page import="com.catgen.NetworkMarket"%>
<%@page import="com.catgen.Constants"%>


<%@page import="com.catgen.FundBank"%>
<%@page import="com.catgen.factories.FundBankFactory"%>
<%@page import="java.text.SimpleDateFormat"%><input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>">
<input type="hidden" id="fetchFund" value="<%=Constants.FETCH_FUND%>">
<input type="hidden" id="addFund" value="<%=Constants.ADD_FUND%>">
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
			<h3>Fund Manager</h3>
			<table cellspacing="0" cellpadding="5" border="0" style="background-color:#eee;">
				<tr>
					<td>Select Network Market</td>
					<td>
						<select id="nmlist4fund" onchange="getSelectedMarketFunds()">
							<option value="">--- Select ---</option><%
							for(NetworkMarket netMarket: markets){%>
								<option value="<%=netMarket.NetworkMarketID %>"><%=netMarket.NetworkMarketID %>&nbsp;-&nbsp;<%=netMarket.Name %></option>	
							<%}%>
						</select>
					</td>
				</tr>
				<tr>
					<td>Market ID</td>
					<td><input type="text" id="selectedmarket" disabled></input></td>
				</tr>
				<tr>
					<td>Regular Fund ($)</td>
					<td><input type="text" id="regularfund" disabled></input></td>
				</tr>
				<tr>
					<td>OutReach ($)</td>
					<td><input type="text" id="outreachfund" disabled></input></td>
				</tr>
				<tr style="background-color: #ddd;">
					<td colspan="2" align="left">
						&nbsp;Add $<input type="text" id="fund2add" size="4" maxlength="4"/> to OUTREACH FUND of selected market.<br/>
						&nbsp;Note: <%=Constants.PERCENT_OPENENTRY %>% will be deducted, and the rest will be deposited. 
						<br/> 
						<input type="button" value="Add Fund" style="padding:5px 10px;" onclick="addFund()"/>
					</td>
				</tr>
			</table>
			<br/><br/>
			<h3>Latest fund additions</h3>
			<table cellspacing="0" cellpadding="5" border="0" style="background-color: #ddd">
				<tr>
					<td>Transfer Date</td>
					<td>Market ID</td>
					<td>Amount(USD)</td>
					<td>Transaction Note</td>
				</tr><%
			List<FundBank> lastAdditions = FundBankFactory.getLatestFundTransfers(conn);
			if(null!=lastAdditions && lastAdditions.size()>0){
				int i=0;
				for(FundBank lastAddition: lastAdditions){
					String bg = (i==0)?"#fff":"#eee";%>
					<tr style="background-color:<%=bg%>">
						<td><%=new SimpleDateFormat("dd/MM/yyyy hh:mm a").format(lastAddition.transDate) %></td>
						<td><%=lastAddition.userId %></td>
						<td><%=lastAddition.amount %></td>
						<td><%=lastAddition.note %></td>
					</tr><%
					i=(i+1)%2;
				}
			}%>
			</table>
		<%}
	} finally {
		conn.close();
	}
%>
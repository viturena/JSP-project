
<%@page import="com.catgen.factories.NetMarketFactory"%>
<%@page import="com.catgen.NetworkMarket"%>
<%@page import="com.catgen.MasterMarket"%>
<%@page import="java.util.List"%>
<%@page import="com.catgen.factories.MySqlDB"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.catgen.factories.MasterMarketFactory"%>
<%@page import="com.catgen.factories.SuperAdminFactory"%>
<%@page import="com.catgen.Constants"%>

<input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>">
<input type="hidden" id="updateDomainName" value="<%=Constants.DOMAIN_MANAGER%>"><%
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
	if(!isSuperAdmin){
		session.setAttribute("errcode","501");
	}else{
		List<MasterMarket> masterMarkets = MasterMarketFactory.getMasterMembers(conn);
		String color="";
		if(masterMarkets!=null && masterMarkets.size()>0){%>
			<h3>Domain Manager</h3><br/>
			<table cellspacing="0" cellpadding="5" border="0">
				<tr bgcolor="#324F81">
					<td style="color:#fff">Market ID</td>
					<td style="color:#fff" width="400">Network Market Name</td>
					<td style="color:#fff">Domain Name</td>
					<td style="color:#fff">Update</td>
				</tr><%
				for(MasterMarket masterMarket: masterMarkets){
					String marketId = masterMarket.NetworkMarketID;
					NetworkMarket networkMarket = NetMarketFactory.getNetworkMarketByCode(conn, marketId);
					if(networkMarket==null){
						continue;
					}
					String name = networkMarket.Name;
					String domainName = masterMarket.DomainName;
					
					name = (name!=null && name.length()>0)?name:"";
					domainName = (domainName!=null && domainName.length()>0)?domainName:"";
					color = ("#ffffff".equals(color))?"#c2ccde":"#ffffff";%>
					<tr bgcolor="<%=color%>">
						<td><%=marketId %></td>
						<td><%=name %></td>
						<td><input type="text" id="domainname_<%=marketId %>" size="32" value="<%=domainName %>"></input></td>
						<td><div id="domaindiv_<%=marketId %>"><input type="button" id="domaindiv_<%=marketId%>" value="Update" onclick="updateDomainName('<%=marketId %>')"></input></div></td>
					</tr>
				<%}%>
			</table>
		<%}else{%>
			<br/><br/>ERROR: Insufficient data.<%
		}
	}
}finally{
	conn.close();
}%>
<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*, org.apache.commons.lang.*" %><%
String userId = (String)session.getAttribute("USER");
String type = (String)session.getAttribute("TYPE");
boolean isValidVendor = CompanyFactory.isValidVendor(userId, type);
if(!isValidVendor){
	session.setAttribute("errcode","505");
}else{ 
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	String companyCode = (String)session.getAttribute("USER");
	try{ %>
		<h3>Associated Network Markets</h3><%
		List<String> associatedNMIdList = NetMarketFactory.getAssociatedNetworkMarketsIdList(conn, companyCode);%>
		<input type="hidden" id="module" value="<%=Constants.MEMBERS_AND_PRODUCTS%>"/>
		<input type="hidden" id="makeAssociation" value="<%=Constants.MAKE_ASSOCIATION%>"/>
		<input type="hidden" id="removeAssociation" value="<%=Constants.REMOVE_ASSOCIATION%>"/>
		<div id="masterDiv"><%
			for(String id: associatedNMIdList){
				NetworkMarket nm = NetMarketFactory.getNetworkMarketByCode(conn, id);
				boolean isAccepted = CompanyFactory.isCompanyAcceptedByNM(conn, nm.NetworkMarketID,companyCode);
				boolean isFeatured = CompanyFactory.isCompanyFeaturedForNM(conn, nm.NetworkMarketID, companyCode);%>
				<div id="associatedNM_<%=nm.NetworkMarketID%>">
					<table width="100%" cellspacing="0" cellpadding="0" border="0">
						<tr valign="middle">
							<td width="80"><%
								if(isAccepted){%>
									<input type="image" src="images/common/Remove.jpg" onclick="removeAssociation('<%=nm.NetworkMarketID %>','<%=companyCode %>')"></input>
								<%}else{ %>
									<img src="images/common/Pending.jpg"/>
								<%} %>	
							</td>
							<td width="400">
								<%=nm.Name %>
								<%=(isAccepted)?"":" [Pending approval by NM]" %>
								<font class="redtext"><%=(isFeatured)?" [Featured by NM]":"" %></font>
							</td>
						</tr>
					</table>
				</div>
			<%}%>
		</div>
		<br/><%
		List<NetworkMarket> networkMarkets = NetMarketFactory.getNetmarketMembers(conn);%>
		<h3>Request association with Network Market</h3>Select Network Market
		<select id="netmarket_list">
			<option></option><%
			for(NetworkMarket networkMarket: networkMarkets){
				if(!(associatedNMIdList.contains(networkMarket.NetworkMarketID))){%>
					<option id="nml_<%=networkMarket.NetworkMarketID%>" value="<%=networkMarket.NetworkMarketID%>"><%=networkMarket.NetworkMarketID%> - <%=networkMarket.Name %></option>
				<%}
			}
		%></select><br/>
		<input type="button" value="Associate" onclick="makeAssociation('<%=companyCode %>')"></input><%		
	}finally{
		conn.close();
	}
}
%>
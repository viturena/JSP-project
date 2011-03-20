<%@ page import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*"%>
<h2>Network Market Validation Manager</h2>
<input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>">
<input type="hidden" id="validateNM" value="<%=Constants.VALIDATE_NM%>">
<input type="hidden" id="invalidateNM" value="<%=Constants.INVALIDATE_NM_USER_ACCOUNT %>"><%
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
	if(!isSuperAdmin){
		session.setAttribute("errcode","501");
	}else{ 
		List<UserSessionBean> unvalidatedUserList = UserSessionFactory.getUnvalidatedUserList(conn, Constants.NETWORK_MARKET);
		for(UserSessionBean userSessionBean : unvalidatedUserList){ 
			if(userSessionBean!=null){
				NetworkMarket networkMarket = NetMarketFactory.getNetworkMarketByCode(conn, userSessionBean.userId);
				if(networkMarket!=null){ %>
					<div id="validatenm_<%=networkMarket.NetworkMarketID%>">
						<table align="center" bgcolor="#FFDDBB">
							<tr>
								<td width="200">UserId</td>
								<td width="300"><%=(userSessionBean.userId!=null)?userSessionBean.userId:"" %></td>
							</tr>
							<tr>
								<td>Network Market Name</td>
								<td><%=(networkMarket.Name!=null)?networkMarket.Name:"" %></td>
							</tr>
							<tr>
								<td>Address</td>
								<td><%=(networkMarket.Address!=null)?networkMarket.Address:"" %></td>
							</tr>
							<tr>
								<td>City</td>
								<td><%=(networkMarket.City!=null)?networkMarket.City:"" %></td>
							</tr>
							<tr>
								<td>State</td>
								<td><%=(networkMarket.State!=null)?networkMarket.State:"" %></td>
							</tr>
							<tr>
								<td>Country</td>
								<td><%=(networkMarket.Country!=null)?networkMarket.Country:"" %></td>
							</tr>
							<tr>
								<td>Zip</td>
								<td><%=(networkMarket.Zip!=null)?networkMarket.Zip:"" %></td>
							</tr>
							<tr>
								<td>Scheme Type</td>
								<td><%=(userSessionBean.scheme>0)?Constants.SCHEME_TYPE[userSessionBean.scheme-1]:"" %></td>
							</tr>
							<tr>
								<td>Membership Request Date</td>
								<td><%=userSessionBean.beginDate.getTime() %></td>
							</tr>
							<tr>
								<td colspan="2">
									<div id="result_<%=networkMarket.NetworkMarketID%>">
										<input type="button" onclick="validateNM('<%=networkMarket.NetworkMarketID %>')" value="Validate">
										<input type="button" onclick="invalidateNM('<%=networkMarket.NetworkMarketID %>')" value="Invalidate"></input>
									</div>
								</td>
							</tr>
						</table>
					</div>
					<br/><br/><br/>
				<%}
			}
		}
	}
}finally{
	conn.close();
}%>
<%@ page import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*"%>
<h2>Referrer Validation Manager</h2>
<input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>">
<input type="hidden" id="validateReferrer" value="<%=Constants.VALIDATE_REFERRER%>">
<input type="hidden" id="invalidateReferrer" value="<%=Constants.INVALIDATE_REFERRER_USER_ACCOUNT %>"><%
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
	if(!isSuperAdmin){
		session.setAttribute("errcode","501");
	}else{
		List<UserSessionBean> unvalidatedUserList = UserSessionFactory.getUnvalidatedUserList(conn, Constants.REFERRER);
		for(UserSessionBean userSessionBean : unvalidatedUserList){ 
			if(userSessionBean!=null){
				Referrer referrer = ReferrerFactory.getReferrerByReferrerCode(conn, userSessionBean.userId);
				if(referrer!=null){ %>
					<div id="validateReferrer_<%=referrer.referrerCode%>">
						<table align="center" bgcolor="#FFDDBB">
							<tr>
								<td width="200">UserId</td>
								<td width="300"><%=(referrer.referrerCode!=null)?referrer.referrerCode:"" %></td>
							</tr>
							<tr>
								<td>Referrer Name</td>
								<td><%=(referrer.name!=null)?referrer.name:"" %></td>
							</tr>
							<tr>
								<td>Address</td>
								<td><%=(referrer.address!=null)?referrer.address:"" %></td>
							</tr>
							<tr>
								<td>City</td>
								<td><%=(referrer.city!=null)?referrer.city:"" %></td>
							</tr>
							<tr>
								<td>State</td>
								<td><%=(referrer.state!=null)?referrer.state:"" %></td>
							</tr>
							<tr>
								<td>Country</td>
								<td><%=(referrer.countryCode!=null)?referrer.countryCode:"" %></td>
							</tr>
							<tr>
								<td>Zip</td>
								<td><%=(referrer.zip!=null)?referrer.zip:"" %></td>
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
									<div id="resultReferrer_<%=referrer.referrerCode%>">
										<input type="button" onclick="validateReferrer('<%=referrer.referrerCode %>')" value="Validate">
										<input type="button" onclick="invalidateReferrer('<%=referrer.referrerCode %>')" value="Invalidate"></input>
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
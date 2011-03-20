<%@ page import="java.util.*, org.apache.commons.lang.*,java.sql.*,com.catgen.*,com.catgen.factories.*,java.net.URLEncoder, java.text.*"%><%
String userId = (String)session.getAttribute("USER");
String type = (String)session.getAttribute("TYPE");
boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
if(!isSuperAdmin){
	session.setAttribute("errcode","501");
}else{ %>
	<h2>Vendor Validation Manager</h2>
	<input type="hidden" id="module" value="<%=Constants.SUPER_ADMIN%>">
	<input type="hidden" id="validateVendor" value="<%=Constants.VALIDATE_VENDOR%>">
	<input type="hidden" id="invalidateVendor" value="<%=Constants.INVALIDATE_VENDOR_USER_ACCOUNT %>"><%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try{
		List<UserSessionBean> unvalidatedUserList = UserSessionFactory.getUnvalidatedUserList(conn,Constants.VENDOR);
		for(UserSessionBean userSessionBean : unvalidatedUserList){
			if(userSessionBean!=null){
				Company company = CompanyFactory.getCompanyByCode(conn, userSessionBean.userId);
				if(company!=null){
				%>
					<table width="500" bgcolor="#FFDDBB">
						<tr>
							<td width="200">UserId</td>
							<td width="300"><%=(userSessionBean.userId!=null)?userSessionBean.userId:"" %></td>
						</tr>
						<tr>
							<td>Vendor Name</td>
							<td><%=(company.Name!=null)?company.Name:"" %></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><%=(company.Address!=null)?company.Address:"" %></td>
						</tr>
						<tr>
							<td>City</td>
							<td><%=(company.City!=null)?company.City:"" %></td>
						</tr>
						<tr>
							<td>State</td>
							<td><%=(company.State!=null)?company.State:"" %></td>
						</tr>
						<tr>
							<td>Country</td>
							<td><%=(company.Country!=null)?company.Country:"" %></td>
						</tr>
						<tr>
							<td>Zip</td>
							<td><%=(company.Zip!=null)?company.Zip:"" %></td>
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
								<div id="result2_<%=userSessionBean.userId%>">
									<input type="button" value="Validate" onclick="validateVendor('<%=userSessionBean.userId %>')">
									<input type="button" value="Invalidate" onclick="invalidateVendor('<%=userSessionBean.userId %>')"></input>
								</div>
							</td>
						</tr>
					</table><br/><br/><br/><%
				}
			}
		}
	}finally{
		conn.close();
	}
}%>
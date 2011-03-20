<%@ page import="java.sql.*, com.catgen.*, com.catgen.factories.*, java.util.*"%>
<input type="hidden" id="module" value="<%=Constants.COMMON%>">
<input type="hidden" id="changePassword" value="<%=Constants.CHANGE_PASSWORD%>">
<%
Connection conn = MySqlDB.getDBConnection(getServletContext());
try{
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	if(!UserSessionFactory.isValidUser(userId,type)){
		session.setAttribute("errcode","502");
	}else{%>
	<h2>Change Password</h2><br/>
	<table align="left" border="0" cellspacing="2" cellpadding="5">
		<tr bgcolor="#E8F2FE">
			<td>Current Password</td>
			<td><input type="password" id="oldPwd" size="20"/></td>
		</tr>
		<tr bgcolor="#E8F2FE">
			<td>New Password</td>
			<td><input type="password" id="newPwd1" size="20"/></td>
		</tr>
		<tr bgcolor="#E8F2FE">
			<td>Re-Enter New Password</td>
			<td><input type="password" id="newPwd2" size="20"/></td>
		</tr>
		<tr>
			<td colspan="2"><input type="button" value="Change Password" onclick="changePassword('<%=userId %>','<%=type %>')"/></td>
		</tr>
	</table><%
		}
	} finally{
	conn.close();
}%>
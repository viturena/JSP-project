<%@ page import="com.catgen.factories.*, com.catgen.*, java.sql.*"%>
<%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try{
		String state = request.getParameter("state");
		if("1".equals(state)){
			String userId = request.getParameter("userId");
			String pwd = request.getParameter("pwd");
			if(userId!=null && userId.length()>0 && pwd!=null && pwd.length()>0){
				UserSessionBean userSessionBean = new UserSessionBean();
				userSessionBean.userId = userId;
				userSessionBean.password = pwd;
				int type = UserSessionFactory.getUserType(conn, userSessionBean);
				if(Constants.SUPER_ADMIN==type || Constants.NETWORK_MARKET==type || Constants.REFERRER==type){
					session.setAttribute("USER",userSessionBean.userId);
					session.setAttribute("TYPE",String.valueOf(type));
					%>You are logged in as a <%=Utils.getUserTypeStringFromInt(type) %> user.<br/>Please click <a href="DataBeanPopulator.jsp?link=<%=Constants.HOME %>">here</a> to continue<%
				}else{
					%>User Credentials incorrect or approval in process. <br/>Could not sign in<%
				}
			}else{
				%>Mandatory values missing!<%
			}
		}else{
	%>
	<form action="DataBeanPopulator.jsp?link=<%=Constants.LOGIN %>" method="post">
		<input type="hidden" name="state" value="1"></input>
		<table align="right" border="0" style="padding-right: 60px;">
			<tr>
				<td>User ID</td>
				<td><input type="text" name="userId" maxlength="25" value=""></input></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="pwd" maxlength="25" value=""></input></td>
			</tr>
			<tr>
				<td align="right" colspan="2"><input type="submit" value="Login"/><input type="reset"/></td>
			</tr>
			<tr>
				<td align="right" colspan="2"><a href="DataBeanPopulator.jsp?link=<%=Constants.FORGOT_PASSWORD %>" style="color: blue;">Forgot Password</a></td>
			</tr>
		</table>
	</form>
	<%
		}
	}finally{
		conn.close();
	}%>
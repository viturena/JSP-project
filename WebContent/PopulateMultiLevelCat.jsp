<%@page import="com.catgen.Constants"%>
<%@page import="com.catgen.factories.MySqlDB, java.sql.*"%>
<%@page import="com.catgen.factories.*"%><br />
<input type="hidden" id="module" value="<%= Constants.SUPER_ADMIN %>" />
<input type="hidden" id="importcategorylist" value="<%= Constants.IMPORT_CATEGORY_LIST_TXT%>" />
<%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try{
		String userId = (String)session.getAttribute("USER");
		String type = (String)session.getAttribute("TYPE");
		boolean isSuperAdmin = SuperAdminFactory.isSuperAdmin(userId, type);
		if(!isSuperAdmin){
			session.setAttribute("errcode","501");
		}else{
			if(!FlagFactory.getFlags(conn).categoryImport){ %>
				<div id="preInitDiv2">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td align="center"><h2>Initiate Category Import</h2></td>
					</tr>
					<tr>
						<td align="center"><input type="button" value="Start" style="padding: 10px 25px; font-size: 16px; font-weight: bold;" onclick="importCategories()" id="initCatImport"/></td>
					</tr>
				</table>
				</div>
				<div align="center" id="initDiv2" style="display:none">
					<h2>Category List Import Initiated ... </h2>
				</div>
			<%}else{ %>
				<div align="center">
					<h2>Category List Import In-Process</h2>
				</div>
			<%} %>
			<br/><br/><br/><br/><%
		}
	}finally{
		conn.close();
	}
%>
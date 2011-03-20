<%@page import="com.catgen.Constants"%>
<%@page import="com.catgen.factories.MySqlDB, java.sql.*"%>
<%@page import="com.catgen.factories.FlagFactory"%><br />
<input type="hidden" id="module" value="<%= Constants.THREADS %>" />
<input type="hidden" id="imagecheck" value="<%= Constants.IMAGE_CHECK %>" />
<%
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try{
		if(!FlagFactory.getFlags(conn).imageCheck){ %>
			<div id="preInitDiv">
			<table width="100%" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td align="center"><h2>Initiate Image Check</h2></td>
				</tr>
				<tr>
					<td align="center"><input type="button" value="Start" style="padding: 10px 25px; font-size: 16px; font-weight: bold;" onclick="checkImage()" id="initImgChk"/></td>
				</tr>
			</table>
			</div>
			<div align="center" id="initDiv" style="display:none">
				<h2>Image Check Initiated ... </h2>
			</div>
		<%}else{ %>
			<div align="center">
				<h2>Image Check In-Process</h2>
			</div>
		<%} %>
		<br/><br/><br/><br/><%
	}finally{
		conn.close();
	}
%>
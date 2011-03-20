<%@page import="com.catgen.MailMsgs"%>
<%@page import="com.catgen.factories.MySqlDB"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.catgen.Constants"%>
<%@page import="com.catgen.factories.NetMarketFactory"%>
<input type="hidden" id="module" value="<%=Constants.MEMBERS_AND_PRODUCTS%>"/>
<input type="hidden" id="mailManager" value="<%=Constants.MAIL_MANAGER%>"/>
<h2>Mail Manager</h2><br/>
<b>Send Mail To:</b><br/><br/><%
	String userId = (String)session.getAttribute("USER");
	String type = (String)session.getAttribute("TYPE");
	boolean isValidNM = NetMarketFactory.isValidNM(userId, type);
	if(!isValidNM){
		session.setAttribute("errcode","504");
	}else{ 
		Connection conn = MySqlDB.getDBConnection(getServletContext());
		try{
			for(int ctr=0;ctr<MailMsgs.MAIL_RECEIVER_CATEGORY.length;ctr++){%>
				<input type="radio" name="receiver_type" value="<%=ctr%>" <%if(ctr==0){ %> checked <%} %>/>&nbsp;
				<%=MailMsgs.MAIL_RECEIVER_CATEGORY[ctr] %><br/><%
			}%>
			<br/><br/><b>Subject:</b><input type="text" size="40" id="mailManagerSubject"/><br/>
			<br/><br/><b>Message:</b><br/><br/>
			<textarea id="mailManagerMessage" cols="80" rows="12"></textarea><br/><br/>
			<input type="button" value="Send Mail" onclick="mailManager('<%=userId %>')" style="padding: 8px 40px"><%
		}finally{
			conn.close();
		}
	}
%>

<%@page import="com.catgen.thread.MailerThread"%><%@ page import="com.catgen.*, com.catgen.factories.*" %><%
try{
	MailObj mailObj = new MailObj();
	mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
	mailObj.to = "james.technologies@gmail.com";
	mailObj.subject = "testing mail from Network Market Management System";
	mailObj.body = "message body";
	new MailerThread(mailObj).sendMail();
	%>Mail Sent<%
}catch(Exception e){
	%>Mail Not Sent.<%
	e.printStackTrace();
}%>
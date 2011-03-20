<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.catgen.factories.CategoryFactory"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.catgen.factories.MySqlDB"%>
<%@page import="java.util.List"%>
<%@page import="com.catgen.Category"%>
<%@page import="com.catgen.helper.CategoryHelper"%>
<%@page import="com.catgen.Constants"%>
<%@page import="java.util.Properties"%>
<%@page import="javax.mail.Session"%>
<%@page import="org.apache.commons.codec.binary.Base64"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%@page import="com.catgen.MailObj"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.internet.MimeMessage.RecipientType"%>
<%@page import="com.catgen.Utils"%>
<%@page import="javax.mail.Transport"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<%
String HOST = "mail.icloudtech.com";
String USER = new String(Base64.decodeBase64("aW5mb0BpY2xvdWR0ZWNoLmNvbQ==".getBytes()));
String PASSWORD = new String(Base64.decodeBase64("aWN0c3VwcG9ydEAxMjM=".getBytes()));
String PORT = "2626";

String STARTTLS = "true";
String AUTH = "true";
String DEBUG = "true";

Properties props = new Properties();

props.put("mail.smtp.host", HOST);
props.put("mail.smtp.port", PORT);
props.put("mail.smtp.user", USER);

props.put("mail.smtp.auth", AUTH);
props.put("mail.smtp.starttls.enable", STARTTLS);
props.put("mail.smtp.debug", DEBUG);

props.put("mail.smtp.socketFactory.port", PORT);
props.put("mail.smtp.socketFactory.fallback", "false");

try {

	Session session1 = Session.getDefaultInstance(props, null);
	session1.setDebug(true);
	MailObj mailObj = new MailObj();
	mailObj.from=USER;
	mailObj.to=USER;
	mailObj.subject=request.getParameter("responded-by")+" is interested!";
	mailObj.body="please contact "+request.getParameter("responded-by");
	
	MimeMessage message = new MimeMessage(session1);
	message.setContent(mailObj.body, "text/html");
	message.setSubject(mailObj.subject);
	message.setFrom(new InternetAddress(mailObj.from));
	message.setRecipients(MimeMessage.RecipientType.TO, Utils.getInternetAddressArrayFromString(mailObj.to));
	message.saveChanges();

	Transport transport = session1.getTransport("smtp");
	transport.connect(HOST, USER, PASSWORD);
	transport.sendMessage(message, message.getAllRecipients());
	transport.close();
	%><h1>Thank you for your response.</h1><h3>We will get back to you soon</h3><%

} catch (Exception e) {
	e.printStackTrace();
}
%>
</body>
</html>
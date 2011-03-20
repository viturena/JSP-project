<%@page import="com.catgen.Error"%><%
	int errCode=-1;
	Object errObj = session.getAttribute("errcode");
	if(errObj!=null){
		errCode = Integer.parseInt((String)errObj);
	}
	session.setAttribute("errcode",null);
	String errmsg="";
	switch(errCode){
	case 501:
		errmsg = Error.ERROR_501;
		break;
	case 502:
		errmsg = Error.ERROR_502;
		break;
	case 503:
		errmsg = Error.ERROR_503;
		break;
	case 504:
		errmsg = Error.ERROR_504;
		break;
	case 505:
		errmsg = Error.ERROR_505;
		break;
	case 506:
		errmsg = Error.ERROR_506;
		break;
	case 507:
		errmsg = Error.ERROR_507;
		break;
	default:
		errmsg = "UNKNOWN ERROR";		
	}%>
<font color="#ff0000">
	Error Code	:&nbsp;<%=errCode %><br/> 
	Description	:&nbsp;<%=errmsg %>
</font>
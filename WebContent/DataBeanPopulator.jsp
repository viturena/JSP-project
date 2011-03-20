<%@ page import="com.catgen.*, java.util.*" %><%
	int link=0;
	String slink = request.getParameter("link");
	try{
		link = Integer.parseInt(slink);
	}catch(Exception e){
		link = 0;
	}
	DisplayBean displayBean = new DisplayBean();
	String user = (String)session.getAttribute("USER");
	String sType = (String)session.getAttribute("TYPE");
	user=(user!=null)?user:"";
	sType=(sType!=null)?sType:"";
	int type=0;
	try{
		type=Integer.parseInt(sType);
	}catch(Exception e){}
	
	Connection conn = MySqlDB.getDBConnection(getServletContext());
	try{
		if(user.length()>0 && (Constants.NETWORK_MARKET==type || Constants.VENDOR==type || Constants.SUPER_ADMIN==type || Constants.REFERRER==type)){
			displayBean.pageHeader = "Welcome, "+user+" ["+Utils.getUserTypeStringFromInt(type)+"]";
		}
		if(user.length()>0 && (Constants.NETWORK_MARKET==type)){
			String domainName = MasterMarketFactory.getMasterMarketByCode(conn,user).DomainName;
			String image = NetMarketFactory.getNetworkMarketByCode(conn,user).premium?"premium":"free";
			displayBean.pageHeader += "<br/><a href=\"http://www."+domainName+"\" target=\"_blank\" class=\"hoverClass\">www."+domainName+"</a>";
			displayBean.pageHeader += "&nbsp;<img src=\"images/common/"+image+".png\" border=\"0\"/>";
		}
		session.setAttribute(Constants.ACTIVE_LINK,link);
		switch(link){
		case Constants.HOME:
			displayBean.browserTitle = "";
			displayBean.targetFile = "Welcome.jsp";
			break;
		case Constants.LOGIN:
			displayBean.browserTitle = "OpenEntry - Login";
			displayBean.targetFile = "Login.jsp";
			break;
		case Constants.REGISTER_NM:
			displayBean.browserTitle = "OpenEntry - Register Network Market";
			displayBean.targetFile = "RegisterNM.jsp";
			break;
		case Constants.REGISTER_REFERRER:
			displayBean.browserTitle = "OpenEntry - Register Referrer";
			displayBean.targetFile = "RegisterReferrer.jsp";
			break;
		case Constants.NETWORK_MARKET_INFO:
			displayBean.browserTitle = "OpenEntry - Network Market Info";
			displayBean.targetFile = "Sheet_NMInfo.jsp";
			break;
		case Constants.VENDOR:
			displayBean.browserTitle = "OpenEntry - Vendor Management";
			displayBean.targetFile = "Sheet_Companies.jsp";
			break;
		case Constants.PAGES:
			displayBean.browserTitle = "OpenEntry - Pages";
			displayBean.targetFile = "Sheet_Pages.jsp";
			break;
		case Constants.FEATURED_PRODUCTS:
			displayBean.browserTitle = "OpenEntry - Featured Products";
			displayBean.targetFile = "Sheet_FeaturedProducts.jsp";
			break;
		case Constants.CATEGORIES:
			displayBean.browserTitle = "OpenEntry - Categories";
			displayBean.targetFile = "Sheet_Categories.jsp";
			break;
		case Constants.STYLE:
			displayBean.browserTitle = "OpenEntry - StyleSheet";
			displayBean.targetFile = "Sheet_StyleSheet.jsp";
			break;
		case Constants.REQUEST_ADD:
			displayBean.browserTitle = "OpenEntry - Request Add";
			displayBean.targetFile = "Vendor_add.jsp";
			break;
		case Constants.REFRESH:
			displayBean.browserTitle = "OpenEntry - Refresh";
			displayBean.targetFile = "Vendor_update.jsp";
			break;
		case Constants.LOGOUT:
			displayBean.browserTitle = "OpenEntry - Logout";
			displayBean.targetFile = "Logout.jsp";
			break;
		case Constants.VALIDATE_NM:
			displayBean.browserTitle = "OpenEntry - Validate NM";
			displayBean.targetFile = "NMValidationManager.jsp";
			break;
		case Constants.VALIDATE_VENDOR:
			displayBean.browserTitle = "OpenEntry - Validate Vendor";
			displayBean.targetFile = "VendorValidationManager.jsp";
			break;
		case Constants.LOGIN_AS:
			displayBean.browserTitle = "OpenEntry - Login As";
			displayBean.targetFile = "Admin_LoginAs.jsp";
			break;
		case Constants.COMPANY_LOADER:
			displayBean.browserTitle = "OpenEntry - Company Loader";
			displayBean.targetFile = "CompanyLoader.jsp";
			break;
		case Constants.INFO_VENDOR:
			displayBean.browserTitle = "OpenEntry - Company Information";
			displayBean.targetFile = "CompanyInfo.jsp";
			break;
		case Constants.GENERATE_USERID:
			displayBean.browserTitle = "OpenEntry - Generate UserID";
			displayBean.targetFile = "Admin_GenerateUserId.jsp";
			break;
		case Constants.RESET_PASSWORD:
			displayBean.browserTitle = "OpenEntry - Reset Password";
			displayBean.targetFile = "Admin_ResetPassword.jsp";
			break;
		case Constants.CHANGE_PASSWORD:
			displayBean.browserTitle = "OpenEntry - Change Password";
			displayBean.targetFile = "ChangePassword.jsp";
			break;
		case Constants.ERR:
			displayBean.browserTitle = "OpenEntry - Error Page";
			displayBean.targetFile = "Err.jsp";
			break;
		case Constants.REFERRALS:
			displayBean.browserTitle = "OpenEntry - Referrals";
			displayBean.targetFile = "Referrals.jsp";
			break;
		case Constants.INFO_NM:
			displayBean.browserTitle = "OpenEntry - Network Market Information";
			displayBean.targetFile = "InfoNM.jsp";
			break;
		case Constants.IMAGE_CHECK:
			displayBean.browserTitle = "OpenEntry - Initiate Image Check";
			displayBean.targetFile = "ImageCheck.jsp";
			break;
		case Constants.MULTI_LEVEL_CATEGORY_LISTING:
			displayBean.browserTitle = "OpenEntry - Multi-level Category Listing";
			displayBean.targetFile = "MultiCat.jsp";
			break;
		case Constants.IMPORT_CATEGORY_LIST_TXT:
			displayBean.browserTitle = "OpenEntry - Import Categories";
			displayBean.targetFile = "PopulateMultiLevelCat.jsp";
			break;
		case Constants.FORGOT_PASSWORD:
			displayBean.browserTitle = "OpenEntry - Forgot Password";
			displayBean.targetFile = "ForgotPassword.jsp";
			break;
		case Constants.MAIL_MANAGER:
			displayBean.browserTitle = "OpenEntry - Mail Manager";
			displayBean.targetFile = "MailManager.jsp";
			break;
		case Constants.VALIDATE_REFERRER:
			displayBean.browserTitle = "OpenEntry - Referrer Validation Manager";
			displayBean.targetFile = "ReferrerValidationManager.jsp";
			break;
		case Constants.ADD_NM:
			displayBean.browserTitle = "OpenEntry - Add Network Market";
			displayBean.targetFile = "AddNM.jsp";
			break;
		case Constants.UPGRADE_TO_SUPERNM:
			displayBean.browserTitle = "OpenEntry - Add Network Market";
			displayBean.targetFile = "Admin_SuperNM.jsp";
			break;
		case Constants.DOMAIN_MANAGER:
			displayBean.browserTitle = "OpenEntry - Domain Manager";
			displayBean.targetFile = "DomainManager.jsp";
			break;
		case Constants.REFERRAL_BILLING:
			displayBean.browserTitle = "OpenEntry - Referral Billing";
			displayBean.targetFile = "ReferralBilling.jsp";
			break;
		case Constants.CHARGES:
			displayBean.browserTitle = "OpenEntry - Charges Setting and Info";
			displayBean.targetFile = "Charges.jsp";
			break;
		case Constants.FUNDS:
			displayBean.browserTitle = "OpenEntry - Funds Manager";
			displayBean.targetFile = "Funds.jsp";
			break;
		default:
			displayBean.browserTitle = "OpenEntry - Error Page";
			displayBean.targetFile = "Err.jsp";
			session.setAttribute("errcode","503");
			break;
		}
		session.setAttribute("DISPLAY_BEAN",displayBean);
	} finally{
		conn.close();
	}
%>

<%@page import="com.catgen.factories.MasterMarketFactory"%>
<%@page import="java.sql.Connection"%>
<%@page import="com.catgen.factories.MySqlDB"%>
<%@page import="com.catgen.factories.NetMarketFactory"%><jsp:include page="Home_OpenEntry.jsp"></jsp:include>
<%	String errcode = (String)session.getAttribute("errcode");
	if(errcode!=null && errcode.length()>0){%>
		<jsp:forward page="DataBeanPopulator.jsp?link=46"></jsp:forward>
	<%}
%>
package com.catgen.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catgen.Constants;
import com.catgen.Error;
import com.catgen.exception.DuplicateCatalogCodeException;
import com.catgen.exception.InvalidHashException;
import com.catgen.exception.InvalidPasswordException;
import com.catgen.exception.MailNotSentException;
import com.catgen.exception.NMMSClosedForHashException;
import com.catgen.exception.NoMatchFoundException;
import com.catgen.exception.NoVendorFoundException;
import com.catgen.exception.NotASuperAdminException;
import com.catgen.exception.RegistrationException;
import com.catgen.exception.SessionInactiveException;
import com.catgen.exception.SpreadsheetNotPublishedException;
import com.catgen.exception.StyleDataTypeFormatException;
import com.catgen.exception.VendorCredentialInvalidOrExpiredException;
import com.catgen.factories.UserSessionFactory;
import com.catgen.factories.XMLFactory;

import java.sql.Connection;

public class AjaxController extends HttpServlet{
	private static final long serialVersionUID = 1L;
	public void process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String res = "";
		int module=0;
		try{
			module = Integer.parseInt(request.getParameter("module"));
		}catch(Exception e){
			module = 0;
		}
		
		String xml = "";
		xml = Constants.XML_DEF_OPEN;
		try{
			if(!(module==Constants.USER_SESSION || module==Constants.GAE_REQUEST)){
				String userId = (String)request.getSession().getAttribute("USER");
				String type = (String)request.getSession().getAttribute("TYPE");
				if(!UserSessionFactory.isValidUser(userId, type)){
					throw new SessionInactiveException();
				}
			}
			switch(module){
				case Constants.NETWORK_MARKET:
					res = NMController.process(conn, request, response);
					break;
				case Constants.NETWORK_MARKET_INFO:
					res = NetworkMarketInfoController.process(conn, request, response);
					break;
				case Constants.MEMBERS_AND_PRODUCTS:
					res = VendorController.process(conn, request, response);
					break;
				case Constants.PAGES:
					res = PageController.process(conn, request, response);
					break;
				case Constants.FEATURED_PRODUCTS:
					res = FeaturedProductController.process(conn, request, response);
					break;
				case Constants.CATEGORIES:
					res = CategoryController.process(conn, request, response);
					break;
				case Constants.STYLE:
					res = StyleController.process(conn, request, response);
					break;
				case Constants.SUPER_ADMIN:
					res = SuperAdminController.process(conn, request, response);
					break;
				case Constants.COMMON:
					res = CommonController.process(conn, request, response);
					break;
				case Constants.USER_SESSION:
					res = UserSessionController.process(conn, request, response);
					break;
				case Constants.REFERRALS:
					res = ReferralsController.process(conn, request, response);
					break;
				case Constants.THREADS:
					res = ThreadController.process(conn, request, response);
					break;
				case Constants.GAE_REQUEST:
					res = GAERequestController.process(conn, request, response);
					break;
			}
			xml += Constants.XML_NOERROR;
			xml += res;
		}catch(StyleDataTypeFormatException es){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", es.getMessage());
		}catch(SessionInactiveException si){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", si.getMessage());
		}catch(NotASuperAdminException sa){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", sa.getMessage());
		}catch(InvalidPasswordException ip){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", ip.getMessage());
		}catch(RegistrationException regex){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", regex.getMessage());
		}catch(MailNotSentException mex){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", mex.getMessage());
		}catch(NoMatchFoundException nmfe){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", nmfe.getMessage());
		}catch(NoVendorFoundException nvf){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", nvf.getMessage());
		}catch(DuplicateCatalogCodeException ed){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", ed.getMessage());
		}catch(VendorCredentialInvalidOrExpiredException ecre){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", ecre.getMessage());
		}catch(SpreadsheetNotPublishedException esp){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", esp.getMessage());
		}catch(NMMSClosedForHashException nch){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", nch.getMessage());
		}catch(InvalidHashException ihe){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", ihe.getMessage());
		}catch(Exception e){
			xml += Constants.XML_ERROR;
			xml += XMLFactory.getMessageElement("errmsg", Error.GENERIC_EXCEPTION);
			e.printStackTrace();
		}finally{
			xml += Constants.XML_DEF_CLOSE;
		}
		
		response.setContentType("text/xml");
		response.setHeader("Cache-Control", "no-cache");
		response.getWriter().write(xml);
	}
}
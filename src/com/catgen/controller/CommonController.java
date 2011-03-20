package com.catgen.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.catgen.Constants;
import com.catgen.MailMsgs;
import com.catgen.MailObj;
import com.catgen.UserSessionBean;
import com.catgen.exception.InvalidPasswordException;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.UserSessionFactory;
import com.catgen.factories.XMLFactory;
import com.catgen.thread.MailerThread;

public class CommonController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, InvalidPasswordException{
		String res = "";
		String strSubModule = request.getParameter("submodule");
		String userId = request.getParameter("userid");
		String sType = request.getParameter("type");
		String oldPwd = request.getParameter("oldpwd");
		String newPwd = request.getParameter("newpwd");

		int subModule = 0, type=0;
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		try{
			type = Integer.parseInt(sType);
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		switch(subModule){
		case Constants.CHANGE_PASSWORD:
			UserSessionBean oldUserSessionBean = new UserSessionBean();
			oldUserSessionBean.userId = userId;
			oldUserSessionBean.type = type;
			oldUserSessionBean.password = oldPwd;
			
			UserSessionBean newUserSessionBean = new UserSessionBean();
			newUserSessionBean.userId = userId;
			newUserSessionBean.type = type;
			newUserSessionBean.password = newPwd;
			
			res = changePassword(conn, oldUserSessionBean, newUserSessionBean);
			break;
		}
		
		return res;
	}
	
	public static String changePassword(Connection conn, UserSessionBean oldUserSessionBean, UserSessionBean newUserSessionBean) throws SQLException, InvalidPasswordException{
		if(UserSessionFactory.hasValidPassword(conn, oldUserSessionBean)){
			UserSessionFactory.updatePassword(conn, newUserSessionBean);
			try{
				String to="";
				switch(newUserSessionBean.type){
				case Constants.NETWORK_MARKET:
					to=NetMarketFactory.getNetworkMarketByCode(conn, newUserSessionBean.userId).ContactEmail;
					break;
				case Constants.VENDOR:
					to=CompanyFactory.getCompanyByCode(conn, newUserSessionBean.userId).ContactEmail;
					break;
				}
				MailObj mailObj = new MailObj();
				mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
				mailObj.to = to;
				mailObj.subject = MailMsgs.PASSWORD_CHANGE_NOTIFICATION_SUBJECT;
				mailObj.body = MailMsgs.PASSWORD_CHANGE_NOTIFICATION_BODY.replaceAll(MailMsgs.OPENENTRY_ID, newUserSessionBean.userId).replaceAll(MailMsgs.PASSWORD, newUserSessionBean.password);
				new MailerThread(mailObj).sendMail();
			}catch(Exception e){
				e.printStackTrace();
			}
			return XMLFactory.getMessageElement("message", "Password Changed Successfully");
		}else{
			throw new InvalidPasswordException();
		}
	}
}

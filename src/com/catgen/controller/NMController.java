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
import com.catgen.NetworkMarket;
import com.catgen.exception.MailNotSentException;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.XMLFactory;
import com.catgen.thread.MailerThread;

public class NMController {
	public static String process(Connection conn, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, MailNotSentException {
		String res="";
		String strSubModule = request.getParameter("submodule");
		String marketId = request.getParameter("marketid");
		String subMarketId = request.getParameter("subMarketId");
		
		int subModule = 0;
		try{
			subModule = Integer.parseInt(strSubModule);
		}catch(Exception e){
			throw new NumberFormatException();
		}
		
		switch(subModule){
		case Constants.ADD_NM:
			res = addNetworkMarket(conn, marketId, subMarketId);
			break;
		case Constants.REMOVE_NM:
			res = removeNetworkMarket(conn, marketId, subMarketId);
			break;
		}	
		return res;
	}
	
	public static String addNetworkMarket(Connection conn, String marketId, String subMarketId) throws SQLException, MailNotSentException{
		NetMarketFactory.addNm2Nm(conn, marketId, subMarketId);
		
		NetworkMarket parentMarket = NetMarketFactory.getNetworkMarketByCode(conn, marketId);
		NetworkMarket subMarket = NetMarketFactory.getNetworkMarketByCode(conn, subMarketId);

		MailObj mailObj = new MailObj();
		mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
		mailObj.to = subMarket.ContactEmail;
		mailObj.subject = MailMsgs.ADD_NOTIFICATION_SUBJECT;
		mailObj.body = MailMsgs.ADDED_BY_SUPER_NM_BODY.replaceAll(MailMsgs.OPENENTRY_ID,subMarket.NetworkMarketID).replaceAll(MailMsgs.OPENENTRY_ID2, parentMarket.NetworkMarketID);
		new MailerThread(mailObj).sendMail();
		
		String res = XMLFactory.getMessageElement("message", "Added selected Network Market("+subMarketId+") successfully.");
		res += XMLFactory.getMessageElement("marketId", marketId);
		res += XMLFactory.getMessageElement("subMarketId", subMarketId);
		res += XMLFactory.getMessageElement("subMarketName", NetMarketFactory.getNetworkMarketByCode(conn, subMarketId).Name);
		return res;
	}
	
	public static String removeNetworkMarket(Connection conn, String marketId, String subMarketId) throws SQLException{
		NetMarketFactory.removeSubNm(conn, marketId, subMarketId);
		String res = XMLFactory.getMessageElement("marketId", marketId);
		res += XMLFactory.getMessageElement("subMarketId", subMarketId);
		return res;
	}
}

package com.catgen.helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.catgen.Company;
import com.catgen.Constants;
import com.catgen.MailMsgs;
import com.catgen.MailObj;
import com.catgen.Utils;
import com.catgen.exception.MailNotSentException;
import com.catgen.exception.NoVendorFoundException;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.NetMarketFactory;
import com.catgen.thread.MailerThread;

public class VendorHelper {

	public static void sendMailToSelectedVendorType(Connection conn, String marketId, MailObj mailObj, int receiverType) throws SQLException, NoVendorFoundException, MailNotSentException{
		List<Company> companies = new ArrayList<Company>();
		switch(receiverType){
			case 0:
				companies = CompanyFactory.getAllCompanies(conn);
				break;
			case 1:
				companies = CompanyFactory.getNetmarketMembers(conn, marketId);
				break;
			case 2:
				companies = CompanyFactory.getNmFeaturedCompanies(conn, marketId);
				break;
			case 3:
				companies = CompanyFactory.getNmNonFeaturedCompanies(conn, marketId);
				break;
		}
		List<String> emailList = Utils.getUniqueEmailIdListFromVendors(companies);
		if(emailList.size()==0){
			throw new NoVendorFoundException("No such vendor found.");
		}
		String emailId = NetMarketFactory.getNetworkMarketByCode(conn, marketId).ContactEmail;
		emailId = (emailId!=null)?emailId:"";
		String commaSeparatedEmail = Utils.getCommaSeparatedEmailFromEmailList(emailList);
		String footer = MailMsgs.MAIL_FOOTER;
		footer = footer.replaceAll(MailMsgs.OPENENTRY_NAME, NetMarketFactory.getNetworkMarketByCode(conn, marketId).Name);
		footer = footer.replaceAll(MailMsgs.OPENENTRY_ID, marketId);
		footer = footer.replaceAll(MailMsgs.ACCOUNT_TYPE, Constants.USER_TYPE[0]);
		footer = footer.replaceAll(MailMsgs.EMAIL_ID, emailId);
		footer = footer.replaceAll(MailMsgs.RECEIVER_CATEGORY, MailMsgs.MAIL_RECEIVER_CATEGORY[receiverType]);
		
		mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
		mailObj.to = commaSeparatedEmail;
		mailObj.subject ="[From Network Market '"+marketId+"' via OpenEntry]" + mailObj.subject;
		mailObj.body += footer;
		new MailerThread(mailObj).sendMail();
	}

	public static List<Company> getOtherCompanies(Connection conn, List<Company> availableCompanies) throws SQLException{
		List<Company> allCompanies = CompanyFactory.getAllCompanies(conn);
		List<Company> otherCompanies = new ArrayList<Company>();
		if(allCompanies!=null){
			if(availableCompanies!=null && availableCompanies.size()>0){
				for(Company oneOfAllCompanies: allCompanies){
					boolean present = false;
					for(Company oneOfAvailableCompanies: availableCompanies){
						if(oneOfAvailableCompanies.Code.equalsIgnoreCase(oneOfAllCompanies.Code)){
							present = true;
							break;
						}
					}
					if(!present){
						otherCompanies.add(oneOfAllCompanies);
					}
				}
			}else{
				return allCompanies;
			}
		}
		return otherCompanies;
	}
}

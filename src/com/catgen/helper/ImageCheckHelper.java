package com.catgen.helper;

import java.sql.Connection;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import com.catgen.Company;
import com.catgen.MailMsgs;
import com.catgen.MailObj;
import com.catgen.NetworkMarket;
import com.catgen.Product;
import com.catgen.exception.ImageCheckInterruptedException;
import com.catgen.factories.CompanyFactory;
import com.catgen.factories.FlagFactory;
import com.catgen.factories.LoggerFactory;
import com.catgen.factories.NetMarketFactory;
import com.catgen.factories.ProductFactory;
import com.catgen.thread.MailerThread;
import com.catgen.thread.ProductFilterThread;

public class ImageCheckHelper{
	
	HashMap<String, List<Product>> map = new HashMap<String, List<Product>>();
	
	public void initiateImageCheck(Connection conn){
		try{
			String msg="";
			FlagFactory.setImageCheckFlag(conn);
			LoggerFactory.write(conn, "Image Check Initiated: "+Calendar.getInstance().getTime().toString());
			List<Company> companies = CompanyFactory.getAllCompanies(conn);
			for(Company company: companies){
				if(FlagFactory.getFlags(conn).imageCheck){
					LoggerFactory.write(conn, "Image check for vendor : "+company.Code);
					List<Product> products = ProductFactory.getProducts(conn, company.Code);
					products = ProductFilterThread.findProductsWithNoImage(products);
					if(products!=null && products.size()>0){
						String message = "Hello "+company.Name+" ("+company.Code+"),\n\nWe noticed problems with the following images:\n\n\n";
						for(Product noImageProd: products){
							message += "Product Code: "+noImageProd.Code+"\n" +
									"Product Name: "+noImageProd.Name+"\n" +
											"Product Image URL: "+noImageProd.ImageURL+"\n\n";
						}
						message+="\n\nWe have temporarily removed these products from the network markets your catalog is associated with.\nPlease log into your account, correct these image URLs, and open your catalog by clicking here ("+company.CompanyURL+").\nYour product will be automatically available to the associated Network Markets.\n\n\nThanks and Regards,\nOpenEntry User Support Team\nSupport@OpenEntry.com";
						message+=MailMsgs.NOTIFICATION_MESSAGE;
						MailObj mailObj = new MailObj();
						mailObj.from = MailMsgs.OPENENTRY_MAIL_ID;
						mailObj.to = company.ContactEmail;
						mailObj.subject = "[OpenEntry] Notification of image problem on your catalog - "+company.CompanyURL;
						mailObj.body = message;
						new MailerThread(mailObj).sendMail();
						map.put(company.Code, products);
						msg += company.Name+" ["+company.Code+"] : Missing Image Count- "+products.size()+"\n";
					}
					for(Product product: products){
						LoggerFactory.write(conn, "Removing no-image product: "+company.Code+"/"+product.Code);
						ProductFactory.removeProduct(conn, company.Code, product.Code);
					}
				}else{
					throw new ImageCheckInterruptedException("Image Check Interrupted");
				}
			}
			
			List<NetworkMarket> networkMarkets = NetMarketFactory.getNetmarketMembers(conn);
			if(networkMarkets!=null){
				for(NetworkMarket networkMarket: networkMarkets){
					if(FlagFactory.getFlags(conn).imageCheck){
						LoggerFactory.write(conn, "Image check for network market : "+networkMarket.NetworkMarketID);
						List<Company> associatedCompanies = CompanyFactory.getAcceptedCompaniesByMarketId(conn, networkMarket.NetworkMarketID);
						String message = "";
						if(associatedCompanies!=null){
							for(Company comps: associatedCompanies){
								List<Product> prods = map.get(comps.Code);
								if(prods!=null && prods.size()>0){
									message += comps.Name+" ["+comps.Code+"] : Missing Image Count- "+prods.size()+"\n";
								}
							}
							if(message.length()>0){
								message = "List of associated companies with missing product image:\n\n\n" + message;
								MailObj mailObs = new MailObj();
								mailObs.from = MailMsgs.OPENENTRY_MAIL_ID;
								mailObs.to = networkMarket.ContactEmail;
								mailObs.subject = "[OpenEntry] Vendor Product Image missing Notification - "+networkMarket.NetworkMarketID;
								mailObs.body = message;
								new MailerThread(mailObs).sendMail();
							}
						}
					}else{
						throw new ImageCheckInterruptedException("Image Check Interrupted");
					}
				}
			}

			if(FlagFactory.getFlags(conn).imageCheck){
				if(msg.length()>0){
					msg = "List of companies and count of their missing products:\n\n\n" + msg;
					MailObj mailObs = new MailObj();
					mailObs.from = MailMsgs.OPENENTRY_MAIL_ID;
					mailObs.to = MailMsgs.OPENENTRY_MAIL_ID;
					mailObs.subject = "[OpenEntry] Check Image Report";
					mailObs.body = msg;
					new MailerThread(mailObs).sendMail();
				}
			}else{
				throw new ImageCheckInterruptedException("Image Check Interrupted");
			}
		}catch(ImageCheckInterruptedException ie){
			LoggerFactory.write(conn, "IMAGE CHECK INTERRUPTED BY SUPER ADMIN.");
			ie.printStackTrace();
		}catch(Exception e){
			LoggerFactory.write(conn, "IMAGE CHECK HALTED FOR UNKNOWN REASON.");
			e.printStackTrace();
		}finally{
			try{
				LoggerFactory.write(conn, "Image Check completed at: "+Calendar.getInstance().getTime().toString());
				FlagFactory.clearImageCheckFlag(conn);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

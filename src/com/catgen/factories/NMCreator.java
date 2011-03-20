package com.catgen.factories;
//NMMS Changes [Registration and Login] : March 2010
import java.sql.*;

import com.catgen.Category;
import com.catgen.DefaultText;
import com.catgen.MasterData;
import com.catgen.NetworkMarket;
import com.catgen.Page;
import com.catgen.Style;
import com.catgen.UserSessionBean;
import com.catgen.exception.RegistrationException;

public class NMCreator {

	public static void createNewNM(Connection conn, MasterData masterData) throws RegistrationException{
		try{
			String marketId = masterData.networkMarket.NetworkMarketID;
			Page page = new Page();
			page.NetworkMarketID = marketId;
			page.Name = DefaultText.PAGE1_NAME;
			page.Description = DefaultText.PAGE1_DESC;
			page.RowID = 1;
			
			Category cat = new Category();
			cat.marketId = marketId;
			cat.categoryKey = DefaultText.CAT_KEY;
			cat.categoryValue = DefaultText.CAT_VALUE;
			
			Style style = new Style();
			style.marketId = marketId;
			
			NetworkMarket networkMarket = masterData.networkMarket;
			UserSessionBean userSessionBean = masterData.userSessionBean;
			conn.setAutoCommit(false);
			
			MasterMarketFactory.save(conn, networkMarket);
			NetMarketFactory.save(conn, networkMarket);
			PageFactory.save(conn, page);
			CategoryFactory.save(conn, cat);
			StyleFactory.save(conn, style);
			UserSessionFactory.save(conn, userSessionBean);
			conn.commit();
		}catch(Exception e){
			throw new RegistrationException();
		}finally{
			try{
				conn.setAutoCommit(true);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	public static void removeNM(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		String marketId = userSessionBean.userId;
		
		try{
			conn.setAutoCommit(false);
			
			MasterMarketFactory.remove(conn, marketId);
			NetMarketFactory.remove(conn, marketId);
			PageFactory.removeAll(conn, marketId);
			CategoryFactory.removeAll(conn, marketId);
			StyleFactory.remove(conn, marketId);
			UserSessionFactory.removeNM(conn, marketId);
			
			conn.commit();
		}finally{
			try{
				conn.setAutoCommit(true);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}

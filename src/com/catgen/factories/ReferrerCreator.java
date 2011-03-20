package com.catgen.factories;

import java.sql.Connection;
import java.sql.SQLException;
import com.catgen.MasterData;
import com.catgen.Referrer;
import com.catgen.UserSessionBean;
import com.catgen.exception.RegistrationException;

public class ReferrerCreator {

	public static void createNewReferrer(Connection conn, MasterData masterData) throws RegistrationException{
		try{
			Referrer referrer = masterData.referrer;
			UserSessionBean userSessionBean = masterData.userSessionBean;
			conn.setAutoCommit(false);
			
			ReferrerFactory.save(conn, referrer);
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

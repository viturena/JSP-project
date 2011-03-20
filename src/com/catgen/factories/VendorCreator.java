package com.catgen.factories;
//NMMS Changes [Registration and Login] : March 2010
import java.sql.Connection;
import java.sql.SQLException;

import com.catgen.Company;
import com.catgen.MasterData;
import com.catgen.UserSessionBean;
import com.catgen.exception.RegistrationException;

public class VendorCreator {

	public static void createNewVendor(Connection conn, MasterData masterData) throws RegistrationException{
		try{
			UserSessionBean userSessionBean = masterData.userSessionBean;
			Company company = masterData.company;
			
			conn.setAutoCommit(false);
			UserSessionFactory.save(conn, userSessionBean);
			CompanyFactory.Save(conn, company);
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
	
	public static void removeVendor(Connection conn, UserSessionBean userSessionBean) throws SQLException{
		try{
			String companyCode = userSessionBean.userId;
			conn.setAutoCommit(false);

			UserSessionFactory.removeVendor(conn, companyCode);
			CompanyFactory.removeCompany(conn, companyCode);
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

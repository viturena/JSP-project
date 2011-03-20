package com.catgen.factories;

import com.catgen.Constants;

public class SuperAdminFactory {

	public static boolean isSuperAdmin(String userId, String sType){
		int type=0;
		try{
			type = Integer.parseInt(sType);
		}catch(Exception e){
			type=0;
		}
		if(userId!=null && userId.length()>0 && (Constants.SUPER_ADMIN==type)){
			return true;
		}
		return false;
	}
}

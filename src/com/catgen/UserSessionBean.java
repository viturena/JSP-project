package com.catgen;
import java.util.Calendar;

//NMMS Changes [Registration and Login] : March 2010
public class UserSessionBean {
	public String userId;
	public String password;
	public int type;
	public boolean validated;
	public int scheme;
	public Calendar beginDate;
	public Calendar endDate;
}

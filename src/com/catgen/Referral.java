package com.catgen;

import java.util.Date;

public class Referral 
{
	public Date ReferralDate;
	public String Email;
	public String NetworkMarketID;
	public String CompanyCode;
	public String ClientIP;
	public String ReferralEvent;
	public String ExtraData;
	public int Level; 
	// Referrals Geolocation Changes: Feb 2010 - begin
	public String City;
	public String Country;
	public String UserAgent;
	public boolean Bot;
	// Referrals Geolocation Changes: Feb 2010 - end
}

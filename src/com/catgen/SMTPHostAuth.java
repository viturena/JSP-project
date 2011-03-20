package com.catgen;

public class SMTPHostAuth {

	private String userId;
	private String password;
	private String smtpHost;
	
	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the smtpHost
	 */
	public String getSmtpHost() {
		return smtpHost;
	}
	/**
	 * @param smtpHost the smtpHost to set
	 */
	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}
}

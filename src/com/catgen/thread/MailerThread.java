package com.catgen.thread;

import com.catgen.MailObj;
import com.catgen.factories.MailFactory;

public class MailerThread extends Thread{
	
	MailObj mailObj;
	
	public MailerThread(MailObj mailObj){
		this.mailObj = mailObj;
	}
	
	public void run(){
		try{
			MailFactory.sendMail(this.mailObj);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void sendMail(){
		this.start();
		return;
	}
}

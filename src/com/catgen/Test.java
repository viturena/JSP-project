package com.catgen;

import java.util.Properties;
import javax.mail.Transport;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage.RecipientType;

import com.catgen.MailObj;
import com.catgen.Utils;
import com.catgen.exception.MailNotSentException;

class Test{

	//private static String HOST = "smtp.wlink.com.np";
	//private static String HOST = "smtp.wlink.com.np";
	private static String HOST = "mail.icloudtech.com";
//	private static String USER = "icloud.technologies@gmail.com";
//	private static String PASSWORD = "timpKa0A";
	private static String USER = "info@icloudtech.com";
	private static String PASSWORD = "ictsupport@123";
	//private static String PORT = "25";
	private static String PORT = "2626";
    //private static String PORT = "465";

	private static String STARTTLS = "true";
	private static String AUTH = "true";
	private static String DEBUG = "true";
	//private static String SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";

	public void sendMail(MailObj mailObj) throws MailNotSentException {
		
		Properties props = new Properties();

		props.put("mail.smtp.host", HOST);
		props.put("mail.smtp.port", PORT);
		props.put("mail.smtp.user", USER);

		props.put("mail.smtp.auth", AUTH);
		props.put("mail.smtp.starttls.enable", STARTTLS);
		props.put("mail.smtp.debug", DEBUG);

		props.put("mail.smtp.socketFactory.port", PORT);
		//props.put("mail.smtp.socketFactory.class", SOCKET_FACTORY);
		props.put("mail.smtp.socketFactory.fallback", "false");

		try {

			Session session = Session.getDefaultInstance(props, null);
			session.setDebug(true);

			MimeMessage message = new MimeMessage(session);
			//message.setText(mailObj.body);
            message.setContent(mailObj.body, "text/html");
			message.setSubject(mailObj.subject);
			message.setFrom(new InternetAddress(mailObj.from));
			message.setRecipients(RecipientType.TO, Utils.getInternetAddressArrayFromString(mailObj.to));
			message.saveChanges();

			Transport transport = session.getTransport("smtp");
			transport.connect(HOST, USER, PASSWORD);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

		} catch (Exception e) {
			System.out.println("ERROR MAILING EMAIL ID: "+mailObj.to);
			e.printStackTrace();
		}
	}

	public static void main(String args[]){
		String list = "udayr.shiwakoti@gmail.com,bishnu.karki@merojob.com,utpala@subisu.net.np,nepart_nepal@yahoo.com";
		
		// Use this one
		//String list = "info@icloudtech.com,hotel@rsingi.wlink.com.np,devesh@wlink.com.np,simrik23266@hotmail.com,info@yetimountainhome.com,basiltravel@wlink.com.np,minkc@wlink.com.np,shepherd@mos.com.np,summit@wlink.com.np,nepcraft@mos.com.np,skypass@mail.com.np,smileadv@wlink.com.np,utpala@subisu.net.np,acmetours@wlink.com.np,ktm@golchha.com,namaste@himhols.wlink.com.np,ankur@mail.com.np,asianheritage@wlink.com.np,ezen@ccsl.com.np,hanatour@wlink.com.np,hkido@mos.com.np,hnorbulinka@wlink.com.np,info@asianhands.com.np,info@borderlandresorts.com,info@creationnepal.com,info@hotelkathmanduview.com,info@hotelparkside.com,info@tibetfamilytours.com,info@travel-providers.com,karan_bhatta@yahoo.com,kcu@ntc.net.np,mgarden@wlink.com.np,nyctravels@wlink.com.np,saakha@prasuna.wlink.com.np,sagar@shcraft.wlink.com.np,sales@dwarikas.com,sarvij@wlink.com.np,shanker@mos.com.np,snowman@wlink.com.np,yeti@yetitravels.com.np,acrosstravel@mail.com.np,akshivko@yahoo.com,aline_npj@wlink.com.np,anangam@hotmail.com,angsonam25@yahoo.com,axisnepal1@wlink.com.np,bj_moktan@hotmail.com,chandani_travels@yahoo.com,clarion@wlink.com.np,cnepal@hotmail.com,dhakaweaves@gmail.com,discovery@wlink.com.np,dynamictt08@hotmail.com,encnepal@mail.com.np,euroasia@mail.com.np,fashion@mos.com.np,flyovertravel@ntc.net.np,fort@mos.com.np,hancraft@mail.com.np,heenatravelstours21@yahoo.com,heirloomcrafts@gmail.com,hermitage@mail.com.np,himhans@wlink.com.np,holidays@organictour.com,hotel@greenwich.wlink.com.np,hotel@lion.wlink.com.np,hotelmandap@mail.com.np,hotelnepal@ntc.net.np,htem@wlink.com.np,info@amazontournepal.com,info@hotelshangrila.com,info@inclusivetravels.com.np,info@nbe.com.np,info@pensionvasana.com.np,info@pupiltravel.com,info@royalmt.com.np,info@travelmakersnepal.com,khatiwada71@hotmail.com,leisure@wlink.com.np,lokta@mos.com.np,ltthanka@wlink.com.np,makharia@wlink.com.np,mguthi@mos.com.np,mitco@wlink.com.np,moonsun@ccsl.com.np,namtsorh@wlink.com.np,narayanstha@hotmail.com,nepal@altruismtravels.com,netra@ntc.net.np,nirvana@wlink.com.np,paragon@wlink.com.np,pnhill@wlink.com.np,questvolunteer@wlink.com.np,rabindra_prad@msn.com,roltrekshop@yahoo.com,sanahast@wlink.com.np,sat@wlink.com.np,seasky@tvl.wlink.com.np,shambala@wlink.com.np,shanghol@mos.com.np,snow@land.wlink.com.np,star@mos.com.np,sujan@wlink.com.np,sukunda@wlink.com.np,surebuddha@yahoo.co.uk,ththev@mos.com.np,toranlatravels@yahoo.com,trapla@tp.wlink.com.np,travel@mail.com.np,unitedtours@mail.com.np,utopia@wlink.com.np,vision@wlink.com.np,yetifabric@wlink.com.np,yingyang@wlink.com.np,info@nepaliproducts.com,info@shoppingnepal.com,sales@myshopasia.com,info@handicrafthome.com,contact@craftexport.com,krishna@creationnepal.com,info@nepalcraft.com,customercare@nepalmade.com";
		//String list = "gnewasanta@gmail.com,surkari@gmail.com,anujmanbjr@gmail.com,tiwaryhk@gmail.com,ankit@icloudtech.com,santa@icloudtech.com,suresh@icloudtech.com,himanshu@icloudtech.com,anuj@icloudtech.com,james@icloudtech.com,subedimanoz@gmail.com,mehtad@aetna.com,ankur.goyal@genpact.com,ananda2@aetna.com,tania_dasgupta@rediffmail.com,sanjayrgh@gmail.com,ankur.fbd@gmail.com,abhishek.bvp@gmail.com,divwesh@gmail.com,taniadasgupta84@gmail.com,Rajbin_Manandhar@infosys.com,Yash_Bajracharya@infosys.com,s-surajshrestha@hotmail.com,ram_an_9@hotmail.com,sabin1122@hotmail.com,swozneel@hotmail.com,sareetalk8@hotmail.com,situ.manandhar@bok.com.np,siza_shr@hotmail.com,sudha_sudha@hotmail.com,bbsingh007@hotmail.com,prabin.subedi@gmail.com,gyurme@royalrugs.net,niranjan@cellroti.com,nare.maharjan@gmail.com,7allgood@gmail.com,biplav@castnep.com,gopal_tags@yahoo.com,sushil@gispl.com,prabesh@richafoundation.org.np,himalr@mail.com.np";
		String[] contacts = list.split(",");
		String message = "<img src=\"http://ntytool.appspot.com/app/images/Greet2011$#MAILID#.jpg\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" bgcolor=\"#FDF6D9\" align=\"center\" style=\"padding:20px;color:#E65F2E\"><tr><td><img src=\"http://www.icloudtech.com/resources/images/misc/2011.png\" border=\"0\"></td></tr><tr><td align=\"center\">--------------------------------------------------------------------------------------------------------------------------- <br/>Warm Regards<br/><br/>iCloud Technologies Pvt Limited. <br/>Khusibu, Kathmandu-17, Nepal.<br/>P. O. Box: 3735<br/>LandLine: +977.1.4385074<br/>Fax : +977.1.4384517<br/><a href=\"mailto:info@icloudtech.com\" style=\"color:#D04512;\">info@icloudtech.com</a><br/><a href=\"http://www.icloudtech.com?refid=greetings\" target=\"_blank\" style=\"color:#D04512;\">www.icloudtech.com</a><br/>---------------------------------------------------------------------------------------------------------------------------<br/></td></tr></table>";
		String content="";
		
		MailObj mailObj = new MailObj();
		mailObj.from = USER;
		mailObj.subject = "Happy New Year's Greetings!!!";
		
		for(int i=0;i<contacts.length;i++){
			System.out.println("######## MAILING "+(i+1)+" of "+contacts.length);
			mailObj.to = contacts[i];
			content = message.replaceAll("#MAILID#", contacts[i]);
			mailObj.body = content;
			Test ob = new Test();
			try {
				ob.sendMail(mailObj);
				System.out.println("SUCCESSFULLY MAILED: "+mailObj.to);
			} catch (MailNotSentException e) {
				e.printStackTrace();
				System.out.println("###START### ERROR MAILING: "+mailObj.to+" ###END###");
			}
		}
	}
}
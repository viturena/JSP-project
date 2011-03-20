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

class Test2{

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
		String contacts[][] = {
{"thefreakstreet","The freak street sellers","singhjames008@hotmail.com"},
{"a107","Devarts","spritina@yahoo.com"},
{"alloclothing","Ninam Handicraft","ninambiohandicraft@yahoo.com"},
{"amritacraft","Amrita Handicraft Pvt. Ltd.","craft@mail.com.np"},
{"angelnepal","Angel Tours and Travel, Angel Treks and Expedition","anangam@hotmail.com"},
{"annapurna","Annapurna Handicraft","handicraft_annapurna@live.com"},
{"aoneexport","Manushi","manushi@ntc.net.np"},
{"arnico","Arnico Stone Carving","arnicostone@hotmail.com"},
{"arpote","AR Pote OE","beadshome@wlink.com.np"},
{"asiancrafts","Asian Crafts","asiancraft@mail.com.np"},
{"asianhands","Asian Hands","info@asianhands.com.np"},
{"atithitour","Atithi Tours (P) Ltd","atithi@mail.com.np"},
{"basketsafrica","Mahaguthi","mguthi@mos.com.np"},
{"batik","Batik Painting","dkayastha@mail.com.np"},
{"beadshome","Beads Home","beadshome@wlink.com.np"},
{"bestwaytravel","BEST WAY TRAVEL AND TOURS","bestwaytravel@mail.com.np"},
{"betterfelt","Glerups NP Private Ltd","glerupsnp@yahoo.com"},
{"billiumtrade","Billium Trade International (P) Ltd.","info@billiumgarment.com"},
{"chen-resik","Chen Resik Pashmina Industries","crpashmina@wlink.com.np"},
{"cheppu","Cheppu Himal","info@aidthroughtrade.com"},
{"cojolya","Cheppu Himal","info@aidthroughtrade.com"},
{"cosyhandicraft","NB Handicraft","info@cosyhandicraft.com"},
{"craftedinkathmandu","Crafted in Kathmadu / Worldwide Market Square","info@worldwidemarketsquare.com"},
{"designdestination","Design Destination","suman.artist@gmail.com"},
{"diltraders","Dil Traders","dilteje@enet.com.np"},
{"g.yolmo","Gyalpo Yolmo Arts","gyalpoyolma@yahoo.com"},
{"gurjusilvers","Gurju\'s Silver Smith","gurju06@gmail.com"},
{"handmade","Hand Made Collection","info@hmcnepal.com"},
{"haratiwear","HARATI WOOLLENS KNITWEAR (P).Ltd","info@haratiwear.com"},
{"heritagectt","Heritage Connection Travels and Tours","info@heritagectt.com.np"},
{"heritagett","Heritage Tours and Travels (P) Ltd","heritagett@mos.com.np"},
{"himalaya","Hotel Himalaya","himalhot@mos.com.np"},
{"humanwear","Human Wear Leather Garments And Leather Goods","humanwear@hotmail.com"},
{"kathmanduview","Hotel Kathmandu View","info@hotelkathmanduview.com"},
{"khukuricrafts","Khukuri Crafts","khukuricraft@info.com.np"},
{"kinternationaltrading","K. International Trading ( Nepalies Handicrafts )","jjoshi24@yahoo.com"},
{"knotcraft","Nepal Knotcraft Centre (NKC)","weavenepal@gmail.com"},
{"lama","Lama Thanka Center","info@thangkapainting.com"},
{"lokta","Lokta Paper Craft","loktapaper@live.com"},
{"lovely","Lovely Thanka","shyam.samtan@gmail.com"},
{"mahalaxmi","New Mahalaxmi Jewellers","anilsunar@yahoo.com"},
{"manjushreecarving","Manjushree Carving","raju.bajracharya10@gmail.com"},
{"metalcraftline","Metal Craft Line Nepal","vchakhun@ntc.net.np"},
{"mithila","Janakpur Handicraft Cente","rsaxena@mithilanepal.com"},
{"naturenepal","Nature Nepal Retail Store","info@naturenepalhs.com"},
{"nepal-produkte","Handicrafts from Nepal","udayalal.shrestha@gmail.com"},
{"nepalicrafts","Nepali Crafts","info@nepalicrafts.com"},
{"newaenterprises","Newa Enterprises","eaglehc@mail.com.np"},
{"newglobalnepal","New Global Nepal Industries","newglobalnp@wlink.com.np"},
{"newsadle","New SADLE","nepraev@wlink.com.np"},
{"ngcc","Nepal Girls Care Center","ngcc@mail.com.np"},
{"palanchowk","Palanchowk Bhagawati Pashmina Industry","pbpashmina@yahoo.com"},
{"paperworldnepal","Paper World Nepal Pvt. Ltd.","nepcraft@mos.com.np"},
{"pelage","Jampha Knitting Industries","pelage@wlink.com.np"},
{"prakriti","Prakriti Decor Art and Crafts","devesh@wlink.com.np"},
{"purna","Purna and Son\'s Handicraft","suraj_s2@hotmail.com"},
{"pwasyahandicrafts","Pwasya Handicrafts","ravitama@wlink.com.np"},
{"raptitrade","Rapti Trade Channels","rapti@rtc.enet.com.np"},
{"ratna","Ratna Silver Crafts","ratnaart@live.com"},
{"ritualhandicraft","RITUAL HANCRAFT","ritualhan@ntc.net.np"},
{"rosenepal","Rose Nepal","info@rosenepal.org"},
{"s3international","S3 International","s3int@mos.com.np"},
{"sama","Sama Travels and Tours","samatravels3@hotmail.com"},
{"seasky","Above Sea and Sky Trekking P. Ltd.","seasky@tvl.wlink.com.np"},
{"shangrila","Shangri - la Hotel","info@hotelshangrila.com"},
{"shirbandi","Shirbandi Jewellers","saassj@enet.com.np"},
{"sikali","Sikali Handicraft","sanubabu_m@yahoo.com"},
{"silverstar","Silver Star Crafts","silverstar@wlink.com.np"},
{"Sinchurihandicraft","Sinchuri Handicraft Production","sinchurihandicraft@gmail.com"},
{"skywomen","Nyatapola Craft","dkayastha@mail.com.np"},
{"srijansil","Srijansil Export-Import Pvt. Ltd.","info@ssbcnepal.com"},
{"stupaincense","Stupa Incense Industry","stupadhoop@yahoo.co.uk"},
{"tibetanhandicraft","Tibetan Handicraft and Paper P. Ltd.","thi@wlink.com.np"},
{"tibetexport","TIBET EXPORT","tibexport@wlink.com.np"},
{"tibethouse","Tibetan House Nepal(THN)","tibetimports@wlink.com.np"},
{"tibettours","Tibet Family Tours and Travels","info@tibetfamilytours.com"},
{"traditionalart","TRADITIONAL ART and HANDICRAFT","narayanstha@hotmail.com"},
{"tridentcraft","Trident Craft Pvt Ltd","info@tridentcraft.com"},
{"tushitaheaven","Tushita Heaven Thangka ( THT )","tushitaheaven@gmail.com"},
{"unitedtours","United Tours and Travels (P) Ltd","unitedtours@mail.com.np"},
{"womanpower","Woman Power","weancop@wlink.com.np"},
{"yakandyeti","Yak and Yeti Enterprises Pvt. Ltd.","yysilver@mos.com.np"},
{"yeticraft","Yeti WoodCraft Industry","yeti@ntc.net.np"},
{"zenzen","Folk Nepal","info@folknepal.com"}};
		
		String message = "<img src=\"http://ntytool.appspot.com/app/images/FIRST121$#MAILID#.jpg\"><div style=\"background-color:gray;color:#fff;\"><table cellspacing=\"0\" cellpadding=\"0\" border=\"0\" align=\"center\" bgcolor=\"#4D004D\" width=\"1024\"><tr><td><img src=\"http://www.icloudtech.com/resources/nty2011/images/flickr_4.jpg\"></td></tr><tr><td style=\"padding:20px;color:#fff;font-size:18px;font-family:Arial;\"><br/>Dear #NAME#,<br/>Have you checked out your ONLINE SHOP at the Nepal Tourism Year 2011 Online marketplace lately?<br/><br/><a href=\"http://www.nepaltourismyear2011.info/Vendors.html\" target=\"_blank\"><img src=\"http://www.icloudtech.com/resources/images/nty2011NM/shops.png\" border=\"0\"/></a><br/><br/><br/>We are pleased to inform you about the few latest changes on the website:<br/><br/>1. Facebook LIKE is extensively used. You can now particularly promote your own online shop yourself making use of the LIKE button for your online shop.<br/>Your online shop is located at:<br/><a href=\"http://nepaltourismyear2011.info/#CODE#/Products.html\" target=\"_blank\" style=\"color:#F39A22\">#NAME#</a><br/><br/>2. Included Google custom search which makes product search much more efficient</br/><br/>3. Included FACEBOOK Conversation box to enable better communication between interested people.<br/><a href = \"http://www.nepaltourismyear2011.info/Facebook\" target=\"_blank\" style=\"color:#F39A22\">Facebook Connect</a><br/><br/><br/>In addition, we will be spending around $1,000 towards Facebook Ads to promote the marketplace. We're seeing amazing number of referrals to many of the Online Shops from the marketplace website, and we're confident that the traffic will be increasing exponentially in the future.<br/><br/><br/>Best Regards,<br/><a href=\"http://nepaltourismyear2011.info?refid=first121\" target=\"_blank\" style=\"color:#F39A22\">Nepal Tourism Year 2011 Online Marketplace</a></td></tr><tr height=\"40\"><td/></tr></table></div>";
		String content="";
		
		MailObj mailObj = new MailObj();
		mailObj.from = USER;
		String subject = "Namaste, #NAME#!";
		
		for(int i=0;i<contacts.length;i++){
			System.out.println("######## MAILING "+(i+1)+" of "+contacts.length);
			mailObj.to = contacts[i][2];
			content = message.replaceAll("#MAILID#", contacts[i][2]);
			content = content.replaceAll("#NAME#", contacts[i][1]);
			content = content.replaceAll("#CODE#", contacts[i][0]);
			mailObj.body = content;
			mailObj.subject = subject.replaceAll("#NAME#", contacts[i][1]);
			Test2 ob = new Test2();
			try {
				ob.sendMail(mailObj);
				System.out.println("SUCCESSFULLY MAILED: "+mailObj.to);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("###START### ERROR MAILING: "+mailObj.to+" ###END###");
			}
		}
	}
}
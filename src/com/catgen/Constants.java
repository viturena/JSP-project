package com.catgen;

public class Constants {
	public static final String OE_URL = "http://nm.openentry.com";
	public static final String GOOGLE_TRANSLATE_URL = "http://translate.google.com/translate?u=http://"+com.catgen.Constants.OE_URL+"/openentry3/&amp;hl=en&amp;ie=UTF-8&amp;sl=en&amp;tl=";
	public static final int pagesize = 15;
	public static final String COMMON_MARKET_ID = "COMMON";
	
	// NM Update Change : March 2010 - Begin
	
	// Additional Update Change March 2010
	public static final int ALL = 0;
	
	// NMMS Changes [Registration and Login] : March 2010
	
	public static final int FREE_TRIAL = 1;
	public static final int SILVER_SCHEME = 2;
	public static final int GOLD_SCHEME = 3;
	public static final int PLATINUM_SCHEME = 4;
	
	public static final int CHARS_PER_CATEGORY = 3;
	
	public static final boolean VALIDATED = true;
	public static final int NONE = -1;

	public static final int NETWORK_MARKET_INFO = 1;
	public static final int MEMBERS_AND_PRODUCTS = 2;
	public static final int PAGES = 3;
	public static final int FEATURED_PRODUCTS = 4;
	public static final int CATEGORIES = 5;
	public static final int STYLE = 6;
	
	public static final int HOME = 7;
	public static final int LOGIN = 8;
	public static final int REGISTER = 9;
	public static final int LOGOUT = 10;
	public static final int REQUEST_ADD = 11;
	public static final int REQUEST_REM = 12;
	public static final int REFRESH = 13;
	//public static final int ERROR = 14;
	
	public static final int ADD_VENDOR = 15;
	public static final int REMOVE_VENDOR = 16;
	public static final int FEATURE_VENDOR = 17;
	public static final int UNFEATURE_VENDOR = 18;

	public static final int POPULATE_PAGES = 19;
	public static final int ADD_PAGE = 20;
	public static final int GET_PAGE_DETAILS = 21;
	public static final int UPDATE_PAGE = 22;
	public static final int REMOVE_PAGE = 23;
	public static final int MOVE_PAGE_UP = 24;
	public static final int MOVE_PAGE_DOWN = 25;
	
	public static final int REMOVE_FEATURED_PRODUCT = 26;
	public static final int LIST_FEATURED_PRODUCTS = 27;
	public static final int FEATURE_PRODUCT = 28;
	
	public static final int REMOVE_CATEGORY = 29;
	public static final int ADD_CATEGORY = 30;
	public static final int EDIT_CATEGORY = 290;
	
	public static final int UPDATE_STYLE = 31;
	
	public static final int VALIDATE_NM = 32;
	public static final int VALIDATE_VENDOR = 33;
	public static final int LOGIN_AS = 34;
	
	public static final int NETWORK_MARKET = 35;
	public static final int VENDOR = 36;
	public static final int SUPER_ADMIN = 37;
	
	public static final int COMPANY_LOADER = 38;
	public static final int MAKE_ASSOCIATION = 39;
	public static final int REMOVE_ASSOCIATION = 40;
	public static final int UPDATE_VENDOR_DATAFEED_LINK = 41;
	public static final int REFRESH_VENDOR_DATA = 42;
	public static final int GENERATE_USERID = 43;
	public static final int RESET_PASSWORD = 44;
	public static final int CHANGE_PASSWORD = 45;
	public static final int ERR = 46;
	
	public static final int COMMON = 47;
	public static final int USER_SESSION = 48;
	public static final int CHECK_AVAILABILITY = 49;
	public static final int INVALIDATE_NM_USER_ACCOUNT = 50;
	public static final int INVALIDATE_VENDOR_USER_ACCOUNT = 51;

	//	public static final int REFERRALS_NETWORK_MARKET = 52;
	//	public static final int REFERRALS_VENDOR = 53;
	//	public static final int REFERRALS_ADMIN = 54;
	
	public static final int REFERRALS = 55;
	public static final int FETCH_REFERRALS = 56;
	
	public static final int INFO_NM = 57;
	public static final int HELP = 58;
	public static final int SEND_AUTHENTICATION_CODE = 59;
	
	public static final int INFO_VENDOR = 60;
	
	public static final int THREADS = 61;
	public static final int IMAGE_CHECK = 62;
	public static final int MULTI_LEVEL_CATEGORY_LISTING = 63;
	public static final int IMPORT_CATEGORY_LIST_TXT = 64;
	public static final int FORGOT_PASSWORD = 65;
	public static final int MAIL_MANAGER = 66;
	//public static final int AUTO_IMPORT_FROM_GAE = 67;
	public static final int GAE_REQUEST = 68;
	public static final int AUTO_AMS_REGISTRATION = 69;
	public static final int REFERRER = 70;
	public static final int VALIDATE_REFERRER = 71;
	public static final int INVALIDATE_REFERRER_USER_ACCOUNT = 72;
	public static final int ADD_NM = 73;
	public static final int REMOVE_NM = 74;
	public static final int UPGRADE_TO_SUPERNM = 75;
	public static final int DEGRADE_FROM_SUPERNM = 76;
	public static final int ADD_VENDOR_DIRECTLY = 77;
	
	public static final int DELETE_SELECTED_COMPANIES = 78;
	public static final int DELETE_SELECTED_NM = 79;
	
	public static final int GAE_CATALOG_UPDATE = 80;
	public static final int GAE_FETCH_ALL_NM_LIST = 81;
	public static final int GAE_FETCH_ASSOCIATED_NM_LIST = 82;
	public static final int GAE_REMOVE_ASSOCIATION = 83;
	public static final int GAE_REQUEST_ASSOCIATION = 84;
	public static final int GAE_FETCH_REFERRALS = 85;
	
	public static final int REGISTER_NM = 86;
	public static final int REGISTER_REFERRER = 87;
	public static final int GET_PAGE_BUFFER = 88;
	public static final int HIDE_PAGE = 89;
	public static final int DOMAIN_MANAGER = 90;
	public static final int REFERRALS_SUMMARY = 91;
	public static final int CREDIT_PURCHASE = 92;
	public static final int GET_CREDIT_BALANCE = 93;
	public static final int GET_CREDIT_PURCHASE_HISTORY = 94;
	public static final int GENERATE_HASH_FOR_GAE = 95;
	public static final int GAE_DISABLE_HASH_FLAG = 96;
	
	public static final int REFERRAL_BILLING = 97;
	public static final int CREDIT_BALANCE_REPORT = 98;
	public static final int CREDIT_PURCHASE_REPORT = 99;
	public static final int BILLING_SUMMARY_REPORT = 100;
	public static final int DETAILED_BILLING_REPORT = 101;
	public static final int GET_REPORT_LIST = 102;
	public static final int CATEGORIES_FOR_GAE = 103;
	public static final int CHARGES = 104;
	public static final int FETCH_CHARGES = 105;
	public static final int UPDATE_CHARGES = 106;
	public static final int DEFAULT_CHARGES = 107;
	public static final int FUNDS = 108;
	public static final int FETCH_FUND = 109;
	public static final int ADD_FUND = 110;
	public static final int ADD_VENDOR_AFTER_PAYMENT = 111;
	public static final int FEATURE_VENDOR_AFTER_PAYMENT = 112;
	public static final int CHANGE_PREMIUM_STATUS = 113;
	public static final int LAZY_NM = 114;
	public static final int CATALOG_FREE_OR_PREMIUM = 115;
	
	public static final int CATEGORY_COLUMN_WIDTH = 260;
	public static final int CONTENT_PANEL_WIDTH = 762;
	
	public static final String[] SHEETS = {"NM Info","Vendors","Pages","Featured","Categories","Style"};
	public static final String[] USER_TYPE = {"Network Market", "Vendor", "Super Admin","Referrer"};
	public static final String[] SCHEME_TYPE = {"Free Trial Scheme","Silver Scheme", "Gold Scheme", "Platinum Scheme"};
	
	public static final String XML_DEF_OPEN = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root>";
	public static final String INTER_DOMAIN_XML_DEF_OPEN ="<?xml version=\"1.0\"?><!DOCTYPE cross-domain-policy SYSTEM \"http://www.adobe.com/xml/dtds/cross-domain-policy.dtd\"><cross-domain-policy><allow-access-from domain=\"*.yahoo.com\"/></cross-domain-policy><root>";
	public static final String XML_DEF_CLOSE = "</root>";
	public static final String XML_ERROR = "<error>1</error>";
	public static final String XML_NOERROR = "<error>0</error>";
	
	public static final String DEFAULT_FONT_COLOR_PAGE = "#666";
	public static final String DEFAULT_FONT_COLOR_SELECT = "#666";
	public static final String DEFAULT_FONT_COLOR_NAVBAR = "#EEE";
	public static final String DEFAULT_FONT_COLOR_TITLE = "#EEE";
	public static final String DEFAULT_FONT_COLOR_CATEGORYTITLE = "#666";
	public static final String DEFAULT_FONT_COLOR_CATEGORYLIST = "#666";
	public static final String DEFAULT_FONT_COLOR_HOVERLIST = "#CCC";
	
	public static final String DEFAULT_BG_COLOR_BODY = "#DDD";
	public static final String DEFAULT_BG_COLOR_PAGE = "#FFF";
	public static final String DEFAULT_BG_COLOR_NAVBAR = "#777";
	public static final String DEFAULT_BG_COLOR_TITLE = "#777";
	public static final String DEFAULT_BG_COLOR_CATEGORY = "#EEE";
	public static final String DEFAULT_BG_COLOR_CATEGORYTITLE = "#AAA";
	public static final String DEFAULT_BG_COLOR_CATEGORYLIST = "#CCC";
	public static final String DEFAULT_BG_COLOR_HOVERLIST = "#666";
	
	public static final String ACTIVE_LINK = "ACTIVE_LINK";
	public static final String COMPANY_UPDATE_KEY = "1234567890";
	public static final String NM_UPDATE_KEY = "JABCDEFGHIJKLMES";
	
	public static final String CATEGORY_XML_PATH = "http://dev.openentry.com/folder/categorylist.xml";
	public static final String CATEGORY_TXT_PATH = "http://dev.openentry.com/folder/categorylist.txt";
	public static final String XML_CATEGORY_ROOT_KEY = "C01";
	public static final String XML_CATEGORY_ROOT_NAME = "root";
	
	public static final String GOOGLE_SPREADSHEET_DATAFEED_FORMAT = "http://spreadsheets.google.com/feeds/cells/#KEY#/#SEQUENCE#/public/basic";
	public static final String KEY_VARIABLE = "#KEY#";
	public static final String SEQUENCE_VARIABLE = "#SEQUENCE#";
	public static final String GOOGLE_SPREADSHEET_URL_FORMAT = "https://spreadsheets.google.com/ccc?key=#KEY#&hl=en#gid=1";
	
	public static final int SEQUENCE_COMPANY_SHEET = 1;
	public static final int SEQUENCE_PRODUCT_SHEET = 2;
	public static final int SEQUENCE_STYLE_SHEET = 3;
	
	public static final int PAGE_SIZE = 1000;
	public static final String RELATIVE_PATH = "##RELATIVE_PATH##";
	
	public static final String[] HUMAN_KEYWORDS= {"windows","linux","macintosh","apple","samsung"};
	public static final String[] NON_HUMAN_KEYWORDS= {"bot","crawl","sitemap"};
	
	public static final String DEFAULT_REFERRER = "openentry";
	public static final float CHARGE_PER_REFERRAL = 0.10f;
	
	public static final double CHARGE_ADD_VENDOR = 0.000;
	public static final double CHARGE_FEATURE_VENDOR = 100.000;

	public static final int PERCENT_100 = 100;
	public static final int PERCENT_OPENENTRY = 30;
	public static final int PERCENT_OTHERS = (PERCENT_100 - PERCENT_OPENENTRY);
	
	public static final int FUND_TYPE_REGULAR = 1;
	public static final int FUND_TYPE_OUTREACH = 2;
	public static final int FUND_TYPE_COMPLETE = 3;
	
	public static final String[] FUND_TYPE = {"","TYPE:REGULAR","TYPE:OUTREACH","TYPE:OPENENTRY"};
	
	public static final int FUND_FINAL = 1;
	public static final int FUND_DEDUCTION = 2;
	
	public static final String STANDARD_DATE_FORMAT = "dd/MM/yyyy";
	public static final int FREE_NM_MAX_VENDOR_COUNT = 10;
	public static final String FREE_NM_LIMIT_EXCEEDED_MESSAGE = "<i>(You have exceeded the vendor limit on your FREE account.<br/>To add more, please upgrade to PREMIUM account.)</i>";
	public static final int FREE_CATALOG_PRODUCT_COUNT = 100;
	//public static final String[] 
	
	// NM Update Change : March 2010 - End
}

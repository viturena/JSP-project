////////////////////////////////////////////////////////////////////////////////////

/* 
 * and open the template in the editor.
 */

ERROR = 1;
INFO = 2;
MULTIPART_MESSAGE_LENGTH = 500;

function getAjaxRequest()
{
    try {
        req = new XMLHttpRequest();
    }
    catch(err1){
        try{
            req = new ActiveXObject("Msxml2.XMLHTTP");
        }
        catch (err2){
            try{
                req = new ActiveXObject("Microsoft.XMLHTTP");
            }
            catch (err3){
                req = false;
            }
        }
    }
    return req;
}

function setupAjaxCall(url, callback){
	var url = "URLProcessor?ajaxcall=true"+url;
	var ajaxRequest = getAjaxRequest();
	ajaxRequest.open("POST", url, true);

	ajaxRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	ajaxRequest.onreadystatechange = callback;
	ajaxRequest.send(null);
}

////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

function updateNMInfo(){
	getObjectById("message").innerHTML = "";
	var vName=getValueById("Name");
	var vAddress=getValueById("Address");
	var vCity=getValueById("City");
	var vState=getValueById("State");
	var vCountry=getValueById("Country");
	var vZip=getValueById("Zip");
	var vLogoImage=getValueById("LogoImage");
	var vHeader=getValueById("Header");
	var vDescription=getValueById("Description");
	var vFooter=getValueById("Footer");
	var vTemplate=getValueById("Template");
	var vKeywords=getValueById("Keywords");
	var vMetaDescription=getValueById("MetaDescription");
	var vGoogleAnalytics=getValueById("GoogleAnalytics");
	
	var hName=getValueById("hName");
	var hAddress=getValueById("hAddress");
	var hCity=getValueById("hCity");
	var hState=getValueById("hState");
	var hCountry=getValueById("hCountry");
	var hZip=getValueById("hZip");
	var hLogoImage=getValueById("hLogoImage");
	var hHeader=getValueById("hHeader");
	var hDescription=getValueById("hDescription");
	var hFooter=getValueById("hFooter");
	var hTemplate=getValueById("hTemplate");
	var hKeywords=getValueById("hKeywords");
	var hMetaDescription=getValueById("hMetaDescription");
	var hGoogleAnalytics=getValueById("hGoogleAnalytics");
	var module=getValueById("module");
	var url = "&module="+module;
	
	if(vName!=hName) url+="&name="+escape(vName);
	if(vAddress!=hAddress) url+="&address="+escape(vAddress);
	if(vCity!=hCity) url+="&city="+escape(vCity);
	if(vState!=hState) url+="&state="+escape(vState);
	if(vCountry!=hCountry) url+="&country="+vCountry;
	if(vZip!=hZip) url+="&zip="+escape(vZip);
	if(vLogoImage!=hLogoImage) url+="&logoimage="+escape(vLogoImage);
	if(vHeader!=hHeader) url+="&header="+escape(vHeader);
	if(vDescription!=hDescription) url+="&description="+escape(vDescription);
	if(vFooter!=hFooter) url+="&footer="+escape(vFooter);
	if(vTemplate!=hTemplate) url+="&template="+vTemplate;
	if(vKeywords!=hKeywords) url+="&keywords="+vKeywords;
	if(vMetaDescription!=hMetaDescription) url+="&metaDescription="+vMetaDescription;
	if(vGoogleAnalytics!=hGoogleAnalytics) url+="&googleAnalytics="+vGoogleAnalytics;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleNMResponse);
}

function updateNMInfoFooterEditor(myEditor,myEditorDesc,myEditorHeader,myEditorLogo){
	getObjectById("message").innerHTML = "";
	var vName=getValueById("Name");
	var vAddress=getValueById("Address");
	var vCity=getValueById("City");
	var vState=getValueById("State");
	var vCountry=getValueById("Country");
	var vZip=getValueById("Zip");
	if(myEditorLogo){
		myEditorLogo.saveHTML();
		var vLogoImage = myEditorLogo.get('element').value;
	}else{
		var vLogoImage=getValueById("LogoImage");
	}
	
	if(myEditorHeader){
		myEditorHeader.saveHTML();
		var vHeader = myEditorHeader.get('element').value;
	}else{
		var vHeader=getValueById("Header");
	}
	
	if(myEditorDesc){
		myEditorDesc.saveHTML();
		var vDescription = myEditorDesc.get('element').value;
	}else{
		var vDescription=getValueById("Description");
	}
	
	if(myEditor){
		myEditor.saveHTML();
		var vFooter = myEditor.get('element').value;
	}else{
		var vFooter=getValueById("Footer");
	}
	
	var vTemplate=getValueById("Template");
	var vKeywords=getValueById("Keywords");
	var vMetaDescription=getValueById("MetaDescription");
	var vGoogleAnalytics=getValueById("GoogleAnalytics");
	var vGSiteVerification=getValueById("GSiteVerification");
	
	var hName=getValueById("hName");
	var hAddress=getValueById("hAddress");
	var hCity=getValueById("hCity");
	var hState=getValueById("hState");
	var hCountry=getValueById("hCountry");
	var hZip=getValueById("hZip");
	var hLogoImage=getValueById("hLogoImage");
	var hHeader=getValueById("hHeader");
	var hDescription=getValueById("hDescription");
	var hFooter=getValueById("hFooter");
	var hTemplate=getValueById("hTemplate");
	var hKeywords=getValueById("hKeywords");
	var hMetaDescription=getValueById("hMetaDescription");
	var hGoogleAnalytics=getValueById("hGoogleAnalytics");
	var hGSiteVerification=getValueById("hGSiteVerification");
	
	var module=getValueById("module");
	var url = "&module="+module;
	
	if(vName!=hName) url+="&name="+escape(vName);
	if(vAddress!=hAddress) url+="&address="+escape(vAddress);
	if(vCity!=hCity) url+="&city="+escape(vCity);
	if(vState!=hState) url+="&state="+escape(vState);
	if(vCountry!=hCountry) url+="&country="+vCountry;
	if(vZip!=hZip) url+="&zip="+escape(vZip);
	if(vLogoImage!=hLogoImage) url+="&logoimage="+escape(vLogoImage);
	if(vHeader!=hHeader) url+="&header="+escape(vHeader);
	if(vDescription!=hDescription) url+="&description="+escape(vDescription);
	if(vFooter!=hFooter) url+="&footer="+escape(vFooter);
	if(vTemplate!=hTemplate) url+="&template="+vTemplate;
	if(vKeywords!=hKeywords) url+="&keywords="+vKeywords;
	if(vMetaDescription!=hMetaDescription) url+="&metaDescription="+vMetaDescription;
	if(vGoogleAnalytics!=hGoogleAnalytics) url+="&googleAnalytics="+vGoogleAnalytics;
	if(vGSiteVerification!=hGSiteVerification) url+="&GSiteVerification="+vGSiteVerification;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleNMResponse);
}

function addNewVendor(marketId, companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("addVendor");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&companycode="+companyCode;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCompanyResponse1);
}

function featureVendor(marketId, companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("featureVendor");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&companycode="+companyCode;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCompanyResponse2);
}

function featureDirectVendor(marketId, companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("featureVendor");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&companycode="+companyCode;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCompanyResponse2x);
}

function unfeatureVendor(marketId, companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("unfeatureVendor");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&companycode="+companyCode;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCompanyResponse3);
}

function unfeatureDirectVendor(marketId, companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("unfeatureVendor");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&companycode="+companyCode;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCompanyResponse3x);
}

function removeVendor(marketId, companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("rmvVendor");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&companycode="+companyCode;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCompanyResponse4);
}

function removeDirectVendor(marketId, companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("rmvVendor");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&companycode="+companyCode;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCompanyResponse4x);
}

function generatePagelist(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("generatepagelist");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId;

	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handlePageResponse1);
}

function addNewPage(marketId){
	getObjectById("message").innerHTML = "";
	var name=getValueById("newPageName");
	if(name!=null && name.length>0){
		var module=getValueById("module");
		var submodule=getValueById("addnewpage");
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&name="+name;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handlePageResponse2);
	}else{
		displayMessage(ERROR,"New Pagename Required!");
	}
}

function getPageDetails(marketId){
	getObjectById("message").innerHTML = "";
	var rowid=getValueById("pagelist");
	getObjectById("hideit").src="images/common/NotHidden.jpg";
	document.getElementById('pages_description').innerHTML='<textarea name="pagedesc" id="pagedesc" cols="160" rows="24"></textarea>';
	if(rowid>0){
		var module=getValueById("module");
		var submodule=getValueById("getpagedetails");
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&rowid="+rowid;

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handlePageResponse3);
	}else{
		getObjectById("pagedesc").value="";
	}
}


function getRichEditorNMInfoDesc(){
	var myEditor = new YAHOO.widget.Editor('Description', {
	    height: '320px',
	    width: '622px',
	    dompath: true, //Turns on the bar at the bottom
	    animate: true //Animates the opening, closing and moving of Editor windows
	});
	myEditor.render();
	return myEditor;
}

function getRichEditorNMInfoLogo(){
	var myEditor = new YAHOO.widget.Editor('LogoImage', {
	    height: '320px',
	    width: '622px',
	    dompath: true, //Turns on the bar at the bottom
	    animate: true //Animates the opening, closing and moving of Editor windows
	});
	myEditor.render();
	return myEditor;
}

function getRichEditorNMInfoHeader(){
	var myEditor = new YAHOO.widget.Editor('Header', {
	    height: '320px',
	    width: '622px',
	    dompath: true, //Turns on the bar at the bottom
	    animate: true //Animates the opening, closing and moving of Editor windows
	});
	myEditor.render();
	return myEditor;
}

function getRichEditorNMInfoFooter(){
	var myEditor = new YAHOO.widget.Editor('Footer', {
	    height: '320px',
	    width: '622px',
	    dompath: true, //Turns on the bar at the bottom
	    animate: true //Animates the opening, closing and moving of Editor windows
	});
	myEditor.render();
	return myEditor;
}

function getRichEditor(){
	var myEditor = new YAHOO.widget.Editor('pagedesc', {
	    height: '280px',
	    width: '822px',
	    dompath: true, //Turns on the bar at the bottom
	    animate: true //Animates the opening, closing and moving of Editor windows
	});
	myEditor.render();
	return myEditor;
}

function getPageBuffer(marketid, bufferid, cbmodule, cbsubmodule, seq){
	getObjectById("message").innerHTML = "";
	if(bufferid.length>0){
		var module=cbmodule;
		var submodule=cbsubmodule;
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketid+"&bufferid="+bufferid+"&seq="+seq;
		setupAjaxCall(url, handlePageBufferResponse);			
	}else{
		getObjectById("pagedesc").value="";
	}
}

function updatePage(marketId,myEditor){
	getObjectById("message").innerHTML = "";
	var rowid=getValueById("currentrow");
	if(rowid>0){
		var module=getValueById("module");
		var submodule=getValueById("updatepage");
		//var myEditor = new YAHOO.widget.Editor('pagedesc');
		if(myEditor){
			myEditor.saveHTML();
			var description = myEditor.get('element').value;
			if(description=="" || description== " "){
				description=getValueById("pagedesc");
			}
		}
		else{
			var description=getValueById("pagedesc");
		}
		var size = Math.ceil(description.length/MULTIPART_MESSAGE_LENGTH);
		var bufferId = getRandomString();
		var url="";
		var desc="";
		var pos = 0;
		var ctr=0;
		for(ctr=1;ctr<=size;ctr++){
			desc = encodeURIComponent(description.substring(pos, pos+MULTIPART_MESSAGE_LENGTH));
			url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&bufferid="+bufferId+"&size="+size+"&seq="+ctr+"&rowid="+rowid+"&description="+desc;
			setupAjaxCall(url, handlePageResponse4);
			pos+=MULTIPART_MESSAGE_LENGTH;
		}
		displayMessage(INFO,"Sending request. Please wait...");
	}else{
		displayMessage(ERROR,"No Page to SAVE!");
	}
}

function removePage(marketId){
	getObjectById("message").innerHTML = "";
	var rowid=getValueById("currentrow");
	if(rowid>0){
		var module=getValueById("module");
		var submodule=getValueById("removepage");
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&rowid="+rowid;

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handlePageResponse5);
	}else{
		displayMessage(ERROR,"No page to DELETE!");
	}
}

function hidePage(marketId){
	getObjectById("message").innerHTML = "";
	var rowid=getValueById("currentrow");
	if(rowid>0){
		var module=getValueById("module");
		var submodule=getValueById("hidepage");
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&rowid="+rowid;

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handlePageResponse5x);
	}else{
		displayMessage(ERROR,"No page to HIDE!");
	}
}

function movePageUp(marketId){
	getObjectById("message").innerHTML = "";
	var rowid=getValueById("currentrow");
	if(rowid>0){
		var module=getValueById("module");
		var submodule=getValueById("movepageup");
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&rowid="+rowid;

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handlePageResponse6);
	}else{
		displayMessage(ERROR,"Unable to move the page UP!");
	}
}

function movePageDown(marketId){
	getObjectById("message").innerHTML = "";
	var rowid=getValueById("currentrow");
	if(rowid>0){
		var module=getValueById("module");
		var submodule=getValueById("movepagedown");
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&rowid="+rowid;
	
		setupAjaxCall(url, handlePageResponse7);
	}else{
		displayMessage(ERROR,"Unable to move the page DOWN!");
	}
}

function removeFeaturedProduct(marketId, companyCode, productCode){
	getObjectById("message").innerHTML = "";
	if(companyCode!=null && companyCode.length>0 && productCode!=null && productCode.length>0){
		var module=getValueById("module");
		var submodule=getValueById("removefeaturedproduct");
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&companycode="+companyCode+"&productcode="+productCode;

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleFeatProdResponse1);
	}else{
		displayMessage(ERROR,"CompanyCode and ProductCode are required fields");
	}
}

function listVendorProducts(){
	getObjectById("message").innerHTML = "";
	var companyCode = getValueById("select_companies");
	if(companyCode!=null && companyCode.length>0){
		var module=getValueById("module");
		var submodule=getValueById("listfeaturedproducts");
		var url = "&module="+module+"&submodule="+submodule+"&companycode="+companyCode;

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleFeatProdResponse2);
	}else{
		displayMessage(ERROR,"Error!");
	}
}

function featureThisProduct(marketId){
	getObjectById("message").innerHTML = "";
	var companyCode = getValueById("select_companies");
	var productCode = getValueById("select_products");
	if(marketId!=null && marketId.length>0 && companyCode!=null && companyCode.length>0 && productCode!=null && productCode.length>0){
		var module=getValueById("module");
		var submodule=getValueById("featureproduct");
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&companycode="+companyCode+"&productcode="+productCode;

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleFeatProdResponse3);
	}else{
		displayMessage(ERROR,"Vendor and product should be selected");
	}
}

function editCategory(marketId, catKey){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var newValueId = "categoryValue"+catKey;
	var newValue = document.getElementById(newValueId).value;
	var submodule=getValueById("editcategory");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&catkey="+catKey+"&newvalue="+encodeURIComponent(newValue);
	var categoryValueId = "categoryValueOnly"+catKey;
	document.getElementById(categoryValueId).innerHTML = newValue;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCatResponseEdit);
}

function removeCategory(marketId, catKey){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("removecategory");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&catkey="+catKey;

	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCatResponse1);
}

function addCategory(marketId){
	getObjectById("message").innerHTML = "";
	var catKey = getValueById("catKey");
	var catValue = getValueById("catValue");
	if(catValue!=null && catValue.length>0){
		var module=getValueById("module");
		var submodule=getValueById("addcategory");
		var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&catkey="+catKey+"&catvalue="+encodeURIComponent(catValue);

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleCatResponse2);
	}else{
		displayMessage(ERROR,'Category Value is a required field');
	}
}

function updateStyle(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("updateStyleValues");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId;
	
	var fields = getObjectById('styleFrame').elements;
	for(var i=0;i<fields.length-1;i++){
		var fieldName = fields[i].name;
		var nextFieldName = fields[i+1].name;
		if (fieldName.startsWith("value_")){
			var fieldValue = fields[i].value;
			var hiddenField = "hidden_"+fieldName;
			var hiddenFieldValue = document.getElementsByName(hiddenField)[0].value;
			if(!(fieldValue==hiddenFieldValue || (("#"+fieldValue)==hiddenFieldValue))){
				url += "&"+fieldName+"="+fieldValue;
			}
		}
		if (fieldName.startsWith("radio_") && (fieldName!=nextFieldName)){
			var radio = document.getElementsByName(fieldName);
			var radioCount = radio.length;
			var index=-1;
			var radioValue="";
			for(var j=0;j<radioCount;j++){
				if(radio[j].checked==true){
					radioValue = radio[j].value;
					break;
				}
			}
			
			var hiddenRadioValue = document.getElementsByName("hidden_"+fieldName)[0].value;
			if(radioValue!=hiddenRadioValue){
				url += "&"+radio[0].name+"="+radioValue;
			}
		}
	}
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleStyleResponse1);
}

function generateList(){	
	getObjectById("message").innerHTML = "";
	var nm = getValueById("user_nm");
	var vendor = getValueById("user_vendor");
	var userType = getValueById("userList");
	if(userType==nm){
		getObjectById("loginAsDiv_"+nm).style.display = "inline";
		getObjectById("loginAsDiv_"+vendor).style.display = "none";
	}else if(userType==vendor){
		getObjectById("loginAsDiv_"+nm).style.display = "none";
		getObjectById("loginAsDiv_"+vendor).style.display = "inline";
	}
}

function loginAsUser(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("loginAs");
	var url = "&module="+module+"&submodule="+submodule;
	
	var type=getValueById("userList");
	var id=getValueById("UserList_"+type);
	
	url += "&type="+type+"&id="+id;

	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleSAResponse1);
}

function makeAssociation(companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("makeAssociation");
	var url = "&module="+module+"&submodule="+submodule;
	
	var marketId = getValueById("netmarket_list");
	if(marketId==null || marketId.length==0){
		displayMessage(ERROR,'Please select Network Market to associate with.');
	}else{
		url += "&marketid="+marketId+"&companycode="+companyCode;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleCompanyResponse5);
	}
}

function removeAssociation(marketId, companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("removeAssociation");
	var url = "&module="+module+"&submodule="+submodule;
	
	if(marketId==null || marketId.length==0){
		displayMessage(ERROR,'Market ID could not be identified');
	}else{
		url += "&marketid="+marketId+"&companycode="+companyCode;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleCompanyResponse6);
	}
}

function updateDatafeedURL(companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("updateVendorDatafeedLink");
	var url = "&module="+module+"&submodule="+submodule+"&companycode="+companyCode;
	
	var companyURL = getValueById("vendorfeedurl");
	var productURL = getValueById("productfeedurl");
	
	url += "&companyurl="+companyURL+"&producturl="+productURL;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCompanyResponse7);
}

function refreshVendorData(companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("refreshVendorData");
	var url = "&module="+module+"&submodule="+submodule+"&companycode="+companyCode;
	
	displayMessage(INFO,"Refreshing ... Please wait.");

	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCompanyResponse8);
}

function generateUserId(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("generateUserId");
	
	var obj = document.getElementsByName("radio_select");
	var size = obj.length;
	var business=0;
	for(var i=0;i<size;i++){
		if(obj[i].checked==true){
			business = obj[i].value;
			break;
		}
	}
	var businesscode=getValueById("select_business_"+business);
	if(businesscode==null || businesscode.length==0){
		displayMessage(ERROR,'Network Market/Vendor is a mandatory field');
	}else{
		var scheme=getValueById("schemes");
		var url = "&module="+module+"&submodule="+submodule+"&business="+business+"&businesscode="+businesscode+"&scheme="+scheme;

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleSAResponse2);
	}
}

function validateNM(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("validateNM");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId;

	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleSAResponse3);
}

function validateVendor(companyCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("validateVendor");
	var url = "&module="+module+"&submodule="+submodule+"&companycode="+companyCode;

	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleSAResponse4);
}

function validateReferrer(referrerCode){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("validateReferrer");
	var url = "&module="+module+"&submodule="+submodule+"&referrercode="+referrerCode;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleSAResponseRef1);
}

function resetPassword(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("resetPassword");
	
	var obj = document.getElementsByName("radio_select2");
	var size = obj.length;
	var business=0;
	for(var i=0;i<size;i++){
		if(obj[i].checked==true){
			business = obj[i].value;
			break;
		}
	}
	var businesscode=getValueById("select_business2_"+business);
	if(businesscode==null || businesscode.length==0){
		displayMessage(ERROR,'Network Market/Vendor is a mandatory field');
	}else{
		var url = "&module="+module+"&submodule="+submodule+"&business="+business+"&businesscode="+businesscode;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleSAResponse5);
	}
}

function changePassword(userId, type){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("changePassword");
	var url = "&module="+module+"&submodule="+submodule+"&userid="+userId+"&type="+type;
	var oldPwd = getValueById("oldPwd");
	var newPwd1 = getValueById("newPwd1");
	var newPwd2 = getValueById("newPwd2");
	
	if(!(oldPwd!=null && oldPwd.length>0 && newPwd1!=null && newPwd1.length>0 && newPwd2!=null && newPwd2.length>0)){
		displayMessage(ERROR, "All three fields must be populated.");
	}else{
		if(newPwd1!=newPwd2){
			displayMessage(ERROR, "New passwords do not match");
		}else{
			url += "&oldpwd=";
			url += oldPwd;
			url += "&newpwd=";
			url += newPwd1;
			displayMessage(INFO,"Sending request. Please wait...");
			setupAjaxCall(url, handleCommonResponse1);
		}
	}
}

function checkAvailability(){
	getObjectById("message").innerHTML = "";
	document.getElementById("div_chk").innerHTML="";
	var module=getValueById("module");
	var submodule=getValueById("checkAvailability");
	var reg_userId = getValueById("reg_userId");
	
	if(reg_userId!=null && reg_userId.length>0){
		var url = "&module="+module+"&submodule="+submodule+"&userid="+reg_userId;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleSessionResponse1);
	}
}

function registerUser(){
	getObjectById("message").innerHTML = "";
	document.getElementById("dispErr").innerHTML="";
	
	document.getElementById("regrow_userId").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_pwd").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_pwdrpt").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_businessType").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_marketname").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_address").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_city").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_country").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_landline").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_mobile").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_email").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_scheme").setAttribute("style", "background-color:#ffffff");
	document.getElementById("regrow_authentication").setAttribute("style", "background-color:#ffffff");
	
	var err="";
	var reg_userId = getValueById("reg_userId");
	var reg_pwd = getValueById("reg_pwd");
	var reg_pwdrpt = getValueById("reg_pwdrpt");
	var reg_businessType = getValueById("reg_businessType");
	var reg_marketname = getValueById("reg_marketname");
	var reg_address = getValueById("reg_address");
	var reg_city = getValueById("reg_city");
	var reg_state = getValueById("reg_city");
	var reg_zip = getValueById("reg_city");
	var reg_country = getValueById("reg_country");
	var reg_landline = getValueById("reg_landline");
	var reg_mobile = getValueById("reg_mobile");
	var reg_email = getValueById("reg_email");
	var reg_scheme = getValueById("reg_scheme");
	var reg_authentication = getValueById("reg_authentication");
	var authenticationCode = getValueById("authenticationCode");
	
	var notAllowed = "!@#$%^&*()+=[]\\\';,./{}|\":<>?~ ";
	var invalidUserId = !hasValidCharacters(reg_userId, notAllowed);
	if(reg_userId==null || reg_userId.length==0 || invalidUserId){
		err += ">> " + getValueById("help_userId")+"<br/>";
		document.getElementById("regrow_userId").setAttribute("style", "background-color:#ffcccc");
	}
	
	if(reg_pwd==null || reg_pwd.length==0 || reg_pwd!=reg_pwdrpt){
		err += ">> " + getValueById("help_pwd")+"<br/>";
		document.getElementById("regrow_pwd").setAttribute("style", "background-color:#ffcccc");
	}
	
	if(reg_pwdrpt==null || reg_pwdrpt.length==0 || reg_pwdrpt!=reg_pwd){
		err += ">> " + getValueById("help_pwdrpt")+"<br/>";
		document.getElementById("regrow_pwdrpt").setAttribute("style", "background-color:#ffcccc");
	}
	
	if(reg_businessType==null || reg_businessType.length==0){
		err += ">> " + getValueById("help_businessType")+"<br/>";
		document.getElementById("regrow_businessType").setAttribute("style", "background-color:#ffcccc");
	}
	
	if(reg_marketname==null || reg_marketname.length==0){
		err += ">> " + getValueById("help_marketname")+"<br/>";
		document.getElementById("regrow_marketname").setAttribute("style", "background-color:#ffcccc");
	}
	
	if(reg_address==null || reg_address.length==0){
		err += ">> " + getValueById("help_address")+"<br/>";
		document.getElementById("regrow_address").setAttribute("style", "background-color:#ffcccc");
	}
	
	if(reg_city==null || reg_city.length==0){
		err += ">> " + getValueById("help_city")+"<br/>";
		document.getElementById("regrow_city").setAttribute("style", "background-color:#ffcccc");
	}
	
	if(reg_country==null || reg_country.length==0){
		err += ">> " + getValueById("help_country")+"<br/>";
		document.getElementById("regrow_country").setAttribute("style", "background-color:#ffcccc");
	}
	
//	if(reg_landline==null || reg_landline.length==0 || isNaN(reg_landline)){
//		err += ">> " + getValueById("help_landline")+"<br/>";
//		document.getElementById("regrow_landline").setAttribute("style", "background-color:#ffcccc");
//	}
//	
//	if(reg_mobile==null || reg_mobile.length==0 || isNaN(reg_mobile)){
//		err += ">> " + getValueById("help_mobile")+"<br/>";
//		document.getElementById("regrow_mobile").setAttribute("style", "background-color:#ffcccc");
//	}
	
	var invalidEmailId = !isValidEmailId(reg_email);
	if(reg_email==null || reg_email.length==0 || invalidEmailId){
		err += ">> " + getValueById("help_email")+"<br/>";
		document.getElementById("regrow_email").setAttribute("style", "background-color:#ffcccc");
	}
	
	if(reg_scheme==null || reg_scheme.length==0){
		err += ">> " + getValueById("help_scheme")+"<br/>";
		document.getElementById("regrow_scheme").setAttribute("style", "background-color:#ffcccc");
	}
	
	if(reg_authentication==null || reg_authentication.length==0 || authenticationCode==null || authenticationCode.length==0 || authenticationCode!=reg_authentication){
		err += ">> " + getValueById("help_authentication")+"<br/>";
		document.getElementById("regrow_authentication").setAttribute("style", "background-color:#ffcccc");
	}

	if(err.length>0){
		document.getElementById("dispErr").innerHTML = "ERROR:<br/>"+err;
	}else{
		var module=getValueById("module");
		var submodule=getValueById("register");
		var url = "&module="+module+"&submodule="+submodule;
		
		url += "&userid="+reg_userId;
		url += "&pwd="+reg_pwd;
		url += "&businessType="+reg_businessType;
		url += "&marketname="+reg_marketname;
		url += "&address="+reg_address;
		url += "&city="+reg_city;
		url += "&state="+reg_state;
		url += "&zip="+reg_zip;
		url += "&country="+reg_country;
		url += "&landline="+reg_landline;
		url += "&mobile="+reg_mobile;
		url += "&email="+reg_email;
		url += "&scheme="+reg_scheme;

		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleSessionResponse2);
	}
}

function invalidateNM(userId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("invalidateNM");
	
	if(userId!=null && userId.length>0){
		var url = "&module="+module+"&submodule="+submodule+"&id="+userId;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleSessionResponse3);
	}
}

function invalidateVendor(userId, type){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("invalidateVendor");
	
	if(userId!=null && userId.length>0){
		var url = "&module="+module+"&submodule="+submodule+"&id="+userId;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleSessionResponse4);
	}
}

function getReferralData(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("fetchReferrals");
	var type=getValueById("userType");
	var userId=getValueById("userId");
	var startDate=getValueById("startdate");
	var endDate=getValueById("enddate");
	var url = "&module="+module+"&submodule="+submodule+"&type="+type+"&userid="+userId+"&startdate="+startDate+"&enddate="+endDate;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleReferralResponse1);
}

function getReferralReport(){
	getObjectById("message").innerHTML = "";
	getObjectById("referralTable1").innerHTML = "";
	getObjectById("referralCountDiv1").innerHTML = "";
	
	var type1=getValueById("creditBalanceReport");
	var type2=getValueById("creditPurchaseReport");
	var type3=getValueById("billingSummaryReport");
	var type4=getValueById("detailedBillingReport");
	
	var module=getValueById("module");
	var type=getValueById("userType1");
	var userId=getValueById("userId1");
	var startDate=getValueById("startdate");
	var endDate=getValueById("enddate");
	var submodule=getValueById("repolist");
	
	displayMessage(INFO,"Sending request. Please wait...");
	
	if(""==submodule || ""==type || ""==userId){
		displayMessage(INFO,"Mandatory values missing!");
	}else if(type1==submodule){
		getCreditBalanceReport(module,submodule,type,userId,startDate,endDate);
	}else if(type2==submodule){
		getCreditPurchaseReport(module,submodule,type,userId,startDate,endDate);
	}else if(type3==submodule){
		getBillingSummaryReport(module,submodule,type,userId,startDate,endDate);
	}else if(type4==submodule){
		getDetailedBillingReport(module,submodule,type,userId,startDate,endDate);
	}
		
}

function getCreditBalanceReport(module,submodule,type,userId,startDate,endDate){
	var url = "&module="+module+"&submodule="+submodule+"&userid="+userId;
	setupAjaxCall(url, handleReferralBillingResponse1);
}

function getCreditPurchaseReport(module,submodule,type,userId,startDate,endDate){
	var url = "&module="+module+"&submodule="+submodule+"&userid="+userId+"&startdate="+startDate+"&enddate="+endDate;
	setupAjaxCall(url, handleReferralBillingResponse2);
}

function getBillingSummaryReport(module,submodule,type,userId,startDate,endDate){
	var url = "&module="+module+"&submodule="+submodule+"&type="+type+"&userid="+userId+"&startdate="+startDate+"&enddate="+endDate;
	setupAjaxCall(url, handleReferralBillingResponse3);
}

function getDetailedBillingReport(module,submodule,type,userId,startDate,endDate){
	var url = "&module="+module+"&submodule="+submodule+"&type="+type+"&userid="+userId+"&startdate="+startDate+"&enddate="+endDate;
	setupAjaxCall(url, handleReferralBillingResponse4);
}

function getReferralSummary(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("referralsSummary");
	var type=getValueById("userType");
	var userId=getValueById("userId");
	var startDate=getValueById("startdate");
	var endDate=getValueById("enddate");
	var url = "&module="+module+"&submodule="+submodule+"&type="+type+"&userid="+userId+"&startdate="+startDate+"&enddate="+endDate;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleReferralSummaryResponse);
}

function sendAuthenticationCode(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("sendAuthCode");
	var email=getValueById("reg_email");
	var url = "&module="+module+"&submodule="+submodule+"&email="+email;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleRegisterResponse1);
}

function checkImage(){
	getObjectById("initDiv").style.display = "inline";
	getObjectById("preInitDiv").style.display = "none";
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("imagecheck");
	var url = "&module="+module+"&submodule="+submodule;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleThreadRequest1);
}

function importCategories(){
	getObjectById("initDiv2").style.display = "inline";
	getObjectById("preInitDiv2").style.display = "none";
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("importcategorylist");
	var url = "&module="+module+"&submodule="+submodule;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleCategoryImport);
}

function forgotPassword(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("forgotpassword");
	var url = "&module="+module+"&submodule="+submodule;
	
	var email=getValueById("forgot_email");
	var types=document.getElementsByName("forgot_type");
	var name=getValueById("forgot_name");
	var type;
	
	for(var i=0;i<types.length;i++){
		if(types[i].checked==true){
			type=types[i].value;
			break;
		}
	}
	url+="&email="+email+"&businessType="+type+"&userid="+name;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleForgottenPasswordRequest);
}

function mailManager(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("mailManager");
	var url = "&module="+module+"&submodule="+submodule;
	
	var options = document.getElementsByName("receiver_type");
	var optionCount = options.length;
	var checkedOption="-1";
	
	for(var j=0;j<optionCount;j++){
		if(options[j].checked==true){
			checkedOption = options[j].value;
			break;
		}
	}
	
	var subject = getValueById("mailManagerSubject");
	var message = getValueById("mailManagerMessage");
	
	url += "&marketid="+marketId+"&receiverType="+checkedOption+"&subject="+subject+"&message="+message;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleVendorMailingRequest);
}

function addNetworkMarket(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("addnm");
	var url = "&module="+module+"&submodule="+submodule;
	
	var subMarketId = getValueById("AddNmList");
	url += "&marketid="+marketId+"&subMarketId="+subMarketId;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleAddNMRequest);
}

function removeNM(marketId,subMarketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("removenm");
	var url = "&module="+module+"&submodule="+submodule;
	
	url += "&marketid="+marketId+"&subMarketId="+subMarketId;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleRemoveNMRequest);
}

function upgrade2SuperNM(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("upgradeNm");
	var marketId = getValueById("NonSuperNMList");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleUpgradeNMRequest);
}

function degrade2NM(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("degradeNM");
	var url = "&module="+module+"&submodule="+submodule+"&marketid="+marketId;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleDegradeNMRequest);
}

function addVendorDirectly(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("addVendorDirectly");
	var url = "&module="+module+"&submodule="+submodule;
	
	var companycode = getValueById("div_nonAvailableVendors");
	url += "&marketid="+marketId+"&companycode="+companycode;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleAddVendorDirectly);
}

function deleteSelectedCompanies(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("deleteSelectedCompanies");
	var url = "&module="+module+"&submodule="+submodule;
	
	var selectedCompanies = getSelectedCheckboxValue(document.getElementsByName("companies"));
	if (selectedCompanies.length <= 0){ 
		alert("No checkboxes selected"); 
	}else{
		if(selectedCompanies.length == 1){
			url += "&companycode="+selectedCompanies[0];
		}else{
			for(var i=0; i<selectedCompanies.length; i++){
				if(i == 0){
					url += "&companycode="+selectedCompanies[i]+":";
				}else if(i != selectedCompanies.length-1)
					url += selectedCompanies[i]+":";
				else{
					url += selectedCompanies[i];
				}
			}
		}
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleDeleteCompanies);
	}
	
}

function deleteSelectedNMs(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("deleteSelectedNMs");
	var url = "&module="+module+"&submodule="+submodule;
	
	var selectedNMs = getSelectedCheckboxValue(document.getElementsByName("networkmarkets"));
	if (selectedNMs.length <= 0){ 
		alert("No checkboxes selected"); 
	}else{
		if(selectedNMs.length == 1){
			url += "&marketid="+selectedNMs[0];
		}else{
			for(var i=0; i<selectedNMs.length; i++){
				if(i == 0){
					url += "&marketid="+selectedNMs[i]+":";
				}else if(i != selectedNMs.length-1)
					url += selectedNMs[i]+":";
				else{
					url += selectedNMs[i];
				}
			}
		}
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleDeleteNMs);
	}
}

function updateDomainName(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("updateDomainName");
	var domainName=getValueById("domainname_"+marketId);
	var url="&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&domainname="+domainName;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleUpdateDomainName);
}

function getReportList(){
	getObjectById("repolist").options.length=0;
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("getReportList");
	var type=getValueById("userType1");
	var url="&module="+module+"&submodule="+submodule+"&type="+type;
	setupAjaxCall(url, handleReportFetch);
}

function checkDateDiv(){
	var type1=getValueById("creditBalanceReport");
	var type2=getValueById("creditPurchaseReport");
	var type3=getValueById("billingSummaryReport");
	var type4=getValueById("detailedBillingReport");
	
	var sel = getValueById("repolist");
	
	if(type1==sel){
		getObjectById("optdiv").style.display="none";
	}else{
		getObjectById("optdiv").style.display="inline";
	}
}

function getSelectedMarketCharges(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("getCharges");
	var marketId=getValueById("charges-nm");
	if(marketId!=""){
		var url="&module="+module+"&submodule="+submodule+"&marketid="+marketId;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleFetchCharges);
	}else{
		displayMessage(ERROR,"Please select a market");
		getObjectById("market-charge").value="";
		getObjectById("add-vendor").value="";
		getObjectById("feature-vendor").value="";
	}
}

function updateCharges(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("updateCharges");
	var marketId=getValueById("market-charge");
	var addvendor=getValueById("add-vendor");
	var featurevendor=getValueById("feature-vendor");
	if(marketId==null || marketId.length==0 || addvendor==null || addvendor.length==0 || featurevendor==null || featurevendor.length==0){
		displayMessage(ERROR,"Required values not available");
	}else{
		var url="&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&add="+addvendor+"&feature="+featurevendor;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleUpdateCharges);		
	}
}

function defaultCharges(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("defaultCharges");
	var marketId=getValueById("market-charge");
	if(marketId!=""){
		var url="&module="+module+"&submodule="+submodule+"&marketid="+marketId;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleDefaultCharges);
	}else{
		displayMessage(ERROR,"Please select a market");
		getObjectById("market-charge").value="";
		getObjectById("add-vendor").value="";
		getObjectById("feature-vendor").value="";
	}
}

function getSelectedMarketFunds(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("fetchFund");
	var marketId=getValueById("nmlist4fund");
	if(marketId!=""){
		var url="&module="+module+"&submodule="+submodule+"&marketid="+marketId;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleFetchFunds);
	}else{
		displayMessage(ERROR,"Please select a market");
		getObjectById("selectedmarket").value="";
		getObjectById("regularfund").value="";
		getObjectById("outreachfund").value="";
	}
}

function addFund(){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("addFund");
	var marketId=getValueById("selectedmarket");
	var amount=getValueById("fund2add");
	if(marketId==null || marketId.length==0){
		displayMessage(ERROR,"Populate required fields first");
	}else{
		var url="&module="+module+"&submodule="+submodule+"&marketid="+marketId+"&fundamount="+amount;
		displayMessage(INFO,"Sending request. Please wait...");
		setupAjaxCall(url, handleAddFund);		
	}
}

function flipType(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("changePremiumStatus");
	var url="&module="+module+"&submodule="+submodule+"&marketid="+marketId;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleFlipType);
}

function flipLazyStatus(marketId){
	getObjectById("message").innerHTML = "";
	var module=getValueById("module");
	var submodule=getValueById("lazynm");
	var url="&module="+module+"&submodule="+submodule+"&marketid="+marketId;
	displayMessage(INFO,"Sending request. Please wait...");
	setupAjaxCall(url, handleFlipLazy);
}

////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

function handleNMResponse(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			displayMessage(INFO,"Completed.");
			//getObjectById("message").innerHTML="<br/>ERROR: "+message;
			if(!error()){
				displayMessage(INFO, getValue("message"));
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketid");
				var companyCode = getValue("companycode");
				var companyName = getValue("companyname");
					
				var preparedDiv = document.createElement("div");
				preparedDiv.setAttribute("id", "directx_"+companyCode);
				
				var table_element = document.createElement("table");
				table_element.setAttribute("border", "0");
				table_element.setAttribute("style","padding-top: 5px; background-color: #E8F2FE");
				
				var row_element = document.createElement("tr");
				row_element.setAttribute("valign", "middle");
				
				var column2_element = document.createElement("td");
				column2_element.setAttribute("width", "200");
				var column3_element = document.createElement("td");
				column3_element.setAttribute("width", "600");
				
				var text1_element = document.createTextNode(companyCode);
				var text2_element = document.createTextNode(companyName+" [URL: "+getValue("url")+"]");
				
				column2_element.appendChild(text1_element);
				column3_element.appendChild(text2_element);
				row_element.appendChild(column2_element);
				row_element.appendChild(column3_element);
				table_element.appendChild(row_element);
				preparedDiv.appendChild(table_element);
				getObjectById("new_"+companyCode).innerHTML="";
				getObjectById("vacant").appendChild(preparedDiv);
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse2(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				companyCode = getValue("companycode");
				getObjectById("featDiv_"+companyCode+"_0").style.display="none";
				getObjectById("featDiv_"+companyCode+"_1").style.display="inline";
				getObjectById("status_"+companyCode).value="1";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse2x(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				companyCode = getValue("companycode");
				marketId = getValue("marketid");
				var divObj = getObjectById("directfeat_"+companyCode);
				divObj.src="images/common/FeaturedBlue.jpg";
				divObj.setAttribute("onclick","unfeatureDirectVendor('"+marketId+"','"+companyCode+"')");
				divObj.setAttribute("title","Featured Vendor. Click to Unfeature");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse3(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				companyCode = getValue("companycode");
				getObjectById("featDiv_"+companyCode+"_0").style.display="inline";
				getObjectById("featDiv_"+companyCode+"_1").style.display="none";
				getObjectById("status_"+companyCode).value="0";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse3x(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				companyCode = getValue("companycode");
				marketId = getValue("marketid");
				var divObj = getObjectById("directfeat_"+companyCode);
				divObj.src="images/common/GeneralBlue.jpg";
				divObj.setAttribute("onclick","featureDirectVendor('"+marketId+"','"+companyCode+"')");
				divObj.setAttribute("title","Click to feature this Vendor");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse4(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				companyCode = getValue("companycode");
				getObjectById("old_"+companyCode).innerHTML="";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse4x(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				companyCode = getValue("companycode");
				getObjectById("vacant").removeChild(getObjectById("direct_"+companyCode));
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handlePageResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var select = document.createElement("select");
				select.setAttribute("id", "pagelist");
				select.setAttribute("onchange", "getPageDetails('"+getValue("marketid")+"')");
				
				var opt1 = document.createElement("option");
				opt1.appendChild(document.createTextNode("Select Page"));
				select.appendChild(opt1);
				
				var count = getValue("pagecount");
				
				var ctr;
				for(ctr=1; ctr<=count; ctr++){
					var opt = document.createElement("option");
					var rowId = getValue("rowid"+ctr);
					var name = getValue("name"+ctr);
					var displayText = document.createTextNode(name);
					opt.appendChild(displayText);
					opt.setAttribute("value", rowId);
					
					if(rowId==getValueById("currentrow"))
						opt.selected=true;
					select.appendChild(opt);
				}

				getObjectById("pagesDropdown").innerHTML="";
				getObjectById("pagesDropdown").appendChild(select);
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handlePageResponse2(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				//getObjectById("pages_name").value = getValue("pagename");
				getObjectById("currentrow").value = getValue("rowid");
				getObjectById("pagedesc").value = '';
				generatePagelist(getValue("marketid"));
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handlePageResponse3(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				getObjectById("pagedesc").value="";
				var marketId = getValue("marketid");
				var bufferId = getValue("bufferid");
				var size = getValue("size");
				var rowId = getValue("rowid");
				var cbModule = getValue("cbmodule");
				var cbSubmodule = getValue("cbsubmodule");
				getObjectById("pagebuffersize").value=size;
				getObjectById("currentrow").value=rowId;
				var hidden = getValue("hidden");
				if(true==hidden){
					getObjectById("hideit").src="images/common/Hidden.jpg";
				}else{
					getObjectById("hideit").src="images/common/NotHidden.jpg";
				}
				
				getPageBuffer(marketId, bufferId, cbModule, cbSubmodule, 1);
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handlePageBufferResponse(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				var marketId = getValue("marketid");
				var bufferId = getValue("bufferid");
				var cbModule = getValue("cbmodule");
				var cbSubmodule = getValue("cbsubmodule");
				var desc = getValue("desc");
				getObjectById("pagedesc").value+=desc;
				var seq = 1+parseInt(getValue("seq"));
				var size = parseInt(getValueById("pagebuffersize"));
				if(seq<=size){
					getPageBuffer(marketId, bufferId, cbModule, cbSubmodule, seq);
				}else{
					displayMessage(INFO,"Completed.");
				}
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handlePageResponse4(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				displayMessage(INFO,'Page saved');
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handlePageResponse5(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				//getObjectById("pages_name").value = "";
				getObjectById("pagedesc").value = '';
				getObjectById("currentrow").value = 0;
				generatePagelist(getValue("marketid"));				
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handlePageResponse5x(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var hidden = getValue("hidden");
				if(true==hidden){
					getObjectById("hideit").src="images/common/Hidden.jpg";
				}else{
					getObjectById("hideit").src="images/common/NotHidden.jpg";
				}
				generatePagelist(getValue("marketid"));				
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handlePageResponse6(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				getObjectById("currentrow").value = getValue("rowid");
				generatePagelist(getValue("marketid"));				
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handlePageResponse7(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				getObjectById("currentrow").value = getValue("rowid");
				generatePagelist(getValue("marketid"));				
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleFeatProdResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var divName="feat_"+getValue("marketid")+"_"+getValue("companycode")+"_"+getValue("productcode");
				getObjectById(divName).style.display="none";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleFeatProdResponse2(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var select = document.createElement("select");
				select.setAttribute("id", "select_products");
				select.setAttribute("onchange", "changeImage()");
				var count = getValue("productcount");
				
				var ctr;
				for(ctr=1; ctr<=count; ctr++){
					var opt = document.createElement("option");
					var code = getValue("code"+ctr);
					var name = getValue("name"+ctr)+" ["+code+"]";
					var imageurl = getValue("imageurl"+ctr);
					var displayText = document.createTextNode(name);
					opt.appendChild(displayText);
					opt.setAttribute("value", code);
					select.appendChild(opt);
					
					var hiddenElement = document.createElement("input");
					hiddenElement.setAttribute("type", "hidden");
					hiddenElement.setAttribute("id", "thImage_"+code);
					hiddenElement.setAttribute("value", decodeURIComponent(imageurl));
					getObjectById("div_imageUrlList").appendChild(hiddenElement);
				}

				getObjectById("span_products").innerHTML="";
				getObjectById("span_products").appendChild(select);
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleFeatProdResponse3(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketid");
				var companyCode = getValue("companycode");
				var productCode = getValue("productcode");				
					
				var div_element = document.createElement("div");
				div_element.setAttribute("id", "feat_"+marketId+"_"+companyCode+"_"+productCode);
				
				var table_element = document.createElement("table");
				table_element.setAttribute("border", "0");
				table_element.setAttribute("width", "100%");
				table_element.setAttribute("style","padding-top: 5px; background-color: #E8F2FE");
				
				var row_element = document.createElement("tr");
				row_element.setAttribute("valign", "middle");
				
				var column1_element = document.createElement("td");
				column1_element.setAttribute("width", "50");
				var column2_element = document.createElement("td");
				column2_element.setAttribute("width", "200");
				var column3_element = document.createElement("td");
				column3_element.setAttribute("width", "200");
				
				var input_element = document.createElement("input");
				input_element.setAttribute("type", "image");
				input_element.setAttribute("src", "images/common/RemoveBlue.jpg");
				input_element.setAttribute("id", "featremove");
				var fn = "removeFeaturedProduct('"+marketId+"','"+companyCode+"','"+productCode+"')";
				input_element.setAttribute("onclick", fn);
				
				var text1_element = document.createTextNode(getValue("companyname"));
				var text2_element = document.createTextNode(getValue("productname")+" ["+productCode+"]");
				
				column1_element.appendChild(input_element);
				column2_element.appendChild(text1_element);
				column3_element.appendChild(text2_element);
				row_element.appendChild(column1_element);
				row_element.appendChild(column2_element);
				row_element.appendChild(column3_element);
				table_element.appendChild(row_element);
				div_element.appendChild(table_element);
				getObjectById("topdiv").appendChild(div_element);
			} else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCatResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var divName = "cat_"+getValue("catkey");
				getObjectById(divName).style.display="none";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCatResponseEdit(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCatResponse2(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketid");
				var catKey = getValue("catkey");
				var catValue = getValue("catvalue");	
					
				var div_element = document.createElement("div");
				div_element.setAttribute("id", "cat_"+catKey);
				
				var table_element = document.createElement("table");
				table_element.setAttribute("border", "0");
				table_element.setAttribute("width", "100%");
				table_element.setAttribute("style","padding-top: 5px; background-color: #E8F2FE");
				
				var row_element = document.createElement("tr");
				row_element.setAttribute("valign", "middle");
				
				var column1_element = document.createElement("td");
				column1_element.setAttribute("width", "50");
				var column2_element = document.createElement("td");
				column2_element.setAttribute("width", "120");
				var column3_element = document.createElement("td");
				column3_element.setAttribute("width", "200");
				
				var input_element = document.createElement("input");
				input_element.setAttribute("type", "image");
				input_element.setAttribute("src", "images/common/RemoveBlue.jpg");
				var fn = "removeCategory('"+marketId+"','"+catKey+"')";
				input_element.setAttribute("onclick", fn);
				
				var text1_element = document.createTextNode(catKey);
				var text2_element = document.createTextNode(catValue);
				
				column1_element.appendChild(input_element);
				column2_element.appendChild(text1_element);
				column3_element.appendChild(text2_element);
				row_element.appendChild(column1_element);
				row_element.appendChild(column2_element);
				row_element.appendChild(column3_element);
				table_element.appendChild(row_element);
				div_element.appendChild(table_element);
				getObjectById("topdiv2").appendChild(div_element);
				
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleStyleResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,getValue("message"));
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSAResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO, "Session changed. <br/>Please click on HOME to continue.");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse5(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketid");
				var marketName = getValue("marketname");
				
				var autoDiv = document.createElement("div");
				autoDiv.setAttribute("id", "associatedNM_"+marketId);
				
				var autoTable = document.createElement("table");
				autoTable.setAttribute("border", "0");
				autoTable.setAttribute("cellspacing", "0");
				autoTable.setAttribute("cellpadding", "0");
				autoTable.setAttribute("width", "100%");
				autoTable.setAttribute("style","padding-top: 5px; background-color: #E8F2FE");
				
				var autoRow = document.createElement("tr");
				autoRow.setAttribute("valign", "middle");
				
				var autoColumn1 = document.createElement("td");
				autoColumn1.setAttribute("width", "80");
				
				var autoImg = document.createElement("img");
				autoImg.setAttribute("src", "images/common/PendingBlue.jpg");
				autoColumn1.appendChild(autoImg);
				
				var autoColumn2 = document.createElement("td");
				autoColumn2.setAttribute("width", "400");
				
				var textNode = document.createTextNode(marketName+" [Pending approval by NM]");
				autoColumn2.appendChild(textNode);
				
				autoRow.appendChild(autoColumn1);
				autoRow.appendChild(autoColumn2);
				
				autoTable.appendChild(autoRow);
				
				autoDiv.appendChild(autoTable);

				getObjectById("netmarket_list").remove(getObjectById("netmarket_list").selectedIndex);
				getObjectById("masterDiv").appendChild(autoDiv);
		
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse6(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketid");
				var marketName = getValue("marketname");
				getObjectById("associatedNM_"+marketId).style.display="none";
				var optn = document.createElement("OPTION");
				optn.text = marketName;
				optn.value = marketId;
				getObjectById("netmarket_list").options.add(optn);
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse7(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO, getValue("message"));
			}else{
				displayMessage(ERROR, getValue("errmsg"));
			}
		}
	}
}

function handleCompanyResponse8(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO, getValue("message"));
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSAResponse2(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				getObjectById("select_business_"+getValue("type")).remove(getObjectById("select_business_"+getValue("type")).selectedIndex);
				displayMessage(INFO, "Success! <br/>Userid: "+getValue("userid")+"<br/>Password:"+getValue("pwd"));
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSAResponse3(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var resultDiv = getObjectById("result_"+getValue("marketid"));
				resultDiv.setAttribute("style", "font-size: 14px; font-weight: bold; color: white; padding: 5px;background-color: green;");
				resultDiv.innerHTML = getValue("marketid")+" Validated!";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSAResponse4(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var resultDiv = getObjectById("result2_"+getValue("companycode"));
				resultDiv.setAttribute("style", "font-size: 14px; font-weight: bold; color: white; padding: 5px;background-color: green;");
				resultDiv.innerHTML = getValue("companycode")+" Validated!";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSAResponseRef1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var resultDiv = getObjectById("resultReferrer_"+getValue("referrercode"));
				resultDiv.setAttribute("style", "font-size: 14px; font-weight: bold; color: white; padding: 5px;background-color: green;");
				resultDiv.innerHTML = getValue("referrercode")+" Validated!";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSAResponse5(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO, "Password has been reset successfully, and sent to respective Email Id");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleCommonResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,'Password changed successfully!!!');
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSessionResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var status = getValue("status");
				var message = getValue("message");
				var chkdiv = document.getElementById("div_chk");
				if(status=="1"){
					chkdiv.setAttribute("style", "color:green");
				}else{
					chkdiv.setAttribute("style", "color:red");
				}
				chkdiv.innerHTML="&nbsp;&nbsp;"+message;
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSessionResponse2(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
//				var message = getValue("message");
//				var chkdiv = document.getElementById("registerMsg");
//				chkdiv.setAttribute("style", "color:green");
//				chkdiv.innerHTML=message;
				displayMessage(INFO,getValue("message"));
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSessionResponse3(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var userId = getValue("marketid");
				var resultDiv = getObjectById("result_"+userId);
				resultDiv.setAttribute("style", "font-size: 14px; font-weight: bold; color: white; padding: 5px;background-color: red;");
				resultDiv.innerHTML = userId+" Invalidated!";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleSessionResponse4(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var userId = getValue("companycode");
				var resultDiv = getObjectById("result2_"+userId);
				resultDiv.setAttribute("style", "font-size: 14px; font-weight: bold; color: white; padding: 5px;background-color: red;");
				resultDiv.innerHTML = userId+" Invalidated!";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleReferralResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				getObjectById("referralTable").innerHTML = "";
				var count = getValue("count");
				if (count>0){
					var xtr = document.createElement("tr");
					xtr.setAttribute("style", "background-color: #324F81;color: #FFFFFF;");
					
					var xtd1 = document.createElement("td");
					xtd1.appendChild(document.createTextNode("Timestamp"));
					
					var xtd2 = document.createElement("td");
					xtd2.appendChild(document.createTextNode("Referrer ID"));
					
					var xtd3 = document.createElement("td");
					xtd3.appendChild(document.createTextNode("Market ID"));
					
					var xtd4 = document.createElement("td");
					xtd4.appendChild(document.createTextNode("CompanyCode"));
					
					var xtd5 = document.createElement("td");
					xtd5.appendChild(document.createTextNode("Client IP"));
					
					var xtd6 = document.createElement("td");
					xtd6.appendChild(document.createTextNode("Event"));
					
					var xtd7 = document.createElement("td");
					xtd7.appendChild(document.createTextNode("Extra Data"));
					
					var xtd8 = document.createElement("td");
					xtd8.appendChild(document.createTextNode("Level"));
					
					var xtd9 = document.createElement("td");
					xtd9.appendChild(document.createTextNode("City"));
					
					var xtd10 = document.createElement("td");
					xtd10.appendChild(document.createTextNode("Country"));
					
					var xtd11 = document.createElement("td");
					xtd11.appendChild(document.createTextNode("Referral by"));
					
					xtr.appendChild(xtd1);
					xtr.appendChild(xtd2);
					xtr.appendChild(xtd3);
					xtr.appendChild(xtd4);
					xtr.appendChild(xtd5);
					xtr.appendChild(xtd6);
					xtr.appendChild(xtd7);
					xtr.appendChild(xtd8);
					xtr.appendChild(xtd9);
					xtr.appendChild(xtd10);
					xtr.appendChild(xtd11);
					
					var xReferralTable = getObjectById("referralTable");
					xReferralTable.appendChild(xtr);
				
					for(var ctr=1;ctr<=count;ctr++){
						var tr = document.createElement("tr");
						tr.setAttribute("style", "background-color:"+getColor()+";");
						
						var td1 = document.createElement("td");
						td1.appendChild(document.createTextNode(getValue("timestamp_"+ctr)));
						
						var td2 = document.createElement("td");
						td2.appendChild(document.createTextNode(getValue("email_"+ctr)));
						
						var td3 = document.createElement("td");
						td3.appendChild(document.createTextNode(getValue("marketId_"+ctr)));
						
						var td4 = document.createElement("td");
						td4.appendChild(document.createTextNode(getValue("companycode_"+ctr)));
						
						var td5 = document.createElement("td");
						td5.appendChild(document.createTextNode(getValue("clientip_"+ctr)));
						
						var td6 = document.createElement("td");
						td6.appendChild(document.createTextNode(getValue("referralevent_"+ctr)));
						
						var td7 = document.createElement("td");
						td7.appendChild(document.createTextNode(getValue("extradata_"+ctr)));
						
						var td8 = document.createElement("td");
						td8.appendChild(document.createTextNode(getValue("level_"+ctr)));
						
						var td9 = document.createElement("td");
						td9.appendChild(document.createTextNode(getValue("city_"+ctr)));
						
						var td10 = document.createElement("td");
						td10.appendChild(document.createTextNode(getValue("country_"+ctr)));
						
						var td11 = document.createElement("td");
						img = document.createElement("img");
						if(getValue("bot_"+ctr)=="false"){
							img.setAttribute("src","images/common/human.png");
							img.setAttribute("title","Human");
						}else{
							img.setAttribute("src","images/common/bot.gif");
							img.setAttribute("title","Bot");
						}
						td11.appendChild(img);
						
						tr.appendChild(td1);
						tr.appendChild(td2);
						tr.appendChild(td3);
						tr.appendChild(td4);
						tr.appendChild(td5);
						tr.appendChild(td6);
						tr.appendChild(td7);
						tr.appendChild(td8);
						tr.appendChild(td9);
						tr.appendChild(td10);
						tr.appendChild(td11);
						
						var referralTable = getObjectById("referralTable");
						referralTable.appendChild(tr);
					}
				}
				var mesg = count+" Referral(s) for "+getValue("userid")+" NM between "+getValue("startdate")+" and "+getValue("enddate")+".";
				getObjectById("referralCountDiv").innerHTML = mesg;
				displayMessage(INFO,mesg);
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleReferralBillingResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				getObjectById("referralCountDiv1").innerHTML ="<h3>Credit Balance Report</h3>Credit Balance: $"+getValue("balance");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleReferralBillingResponse2(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				getObjectById("referralTable1").innerHTML = "";
				getObjectById("referralCountDiv1").innerHTML = "<h3>Credit Purchase History Report - Net Amount $ "+getValue("net")+"</h3>";
				var count = getValue("count");
				if (count>0){
					var xtr = document.createElement("tr");
					xtr.setAttribute("style", "background-color: #324F81;color: #FFFFFF;");
					
					var xtd1 = document.createElement("td");
					xtd1.appendChild(document.createTextNode("Date"));
					
					var xtd2 = document.createElement("td");
					xtd2.appendChild(document.createTextNode("Amount"));
					
					var xtd3 = document.createElement("td");
					xtd3.appendChild(document.createTextNode("Note"));
					
					xtr.appendChild(xtd1);
					xtr.appendChild(xtd2);
					xtr.appendChild(xtd3);
					
					var xReferralTable = getObjectById("referralTable1");
					xReferralTable.appendChild(xtr);
				
					for(var ctr=1;ctr<=count;ctr++){
						var tr = document.createElement("tr");
						tr.setAttribute("style", "background-color:"+getColor()+";");
						
						var td1 = document.createElement("td");
						td1.appendChild(document.createTextNode(getValue("date_"+ctr)));
						
						var td2 = document.createElement("td");
						td2.appendChild(document.createTextNode(getValue("amount_"+ctr)));
						
						var td3 = document.createElement("td");
						td3.appendChild(document.createTextNode(getValue("note_"+ctr)));
						
						tr.appendChild(td1);
						tr.appendChild(td2);
						tr.appendChild(td3);
						
						var referralTable1 = getObjectById("referralTable1");
						referralTable1.appendChild(tr);
					}
				}
				displayMessage(INFO,"Complete");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleReferralBillingResponse3(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				getObjectById("referralTable1").innerHTML = "";
				getObjectById("referralCountDiv1").innerHTML = "<h3>Summary Billing Report - Net Amount $ "+getValue("net")+"</h3>";
				var count = getValue("count");
				if (count>0){
					var xtr = document.createElement("tr");
					xtr.setAttribute("style", "background-color: #324F81;color: #FFFFFF;");
					
					var xtd1 = document.createElement("td");
					xtd1.appendChild(document.createTextNode("Billing Date"));
					
					var xtd2 = document.createElement("td");
					xtd2.appendChild(document.createTextNode("Referral Count"));
					
					var xtd3 = document.createElement("td");
					xtd3.appendChild(document.createTextNode("Total Amount"));
					
					xtr.appendChild(xtd1);
					xtr.appendChild(xtd2);
					xtr.appendChild(xtd3);
					
					var xReferralTable = getObjectById("referralTable1");
					xReferralTable.appendChild(xtr);
				
					for(var ctr=1;ctr<=count;ctr++){
						var tr = document.createElement("tr");
						tr.setAttribute("style", "background-color:"+getColor()+";");
						
						var td1 = document.createElement("td");
						td1.appendChild(document.createTextNode(getValue("date_"+ctr)));
						
						var td2 = document.createElement("td");
						td2.appendChild(document.createTextNode(getValue("no_"+ctr)));
						
						var td3 = document.createElement("td");
						td3.appendChild(document.createTextNode(getValue("amount_"+ctr)));
						
						tr.appendChild(td1);
						tr.appendChild(td2);
						tr.appendChild(td3);
						
						var referralTable1 = getObjectById("referralTable1");
						referralTable1.appendChild(tr);
					}
				}
				displayMessage(INFO,"Complete");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleReferralBillingResponse4(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				getObjectById("referralTable1").innerHTML = "";
				getObjectById("referralCountDiv1").innerHTML = "<h3>Detail Referral Payment Report - Net Amount $ "+getValue("net")+"</h3>";
				var count = getValue("count");
				if (count>0){
					var xtr = document.createElement("tr");
					xtr.setAttribute("style", "background-color: #324F81;color: #FFFFFF;");
					
					var xtd1 = document.createElement("td");
					xtd1.appendChild(document.createTextNode("Payment Date"));
					
					var xtd2 = document.createElement("td");
					xtd2.appendChild(document.createTextNode("Payer ID"));
					
					var xtd3 = document.createElement("td");
					xtd3.appendChild(document.createTextNode("Payee ID"));
					
					var xtd4 = document.createElement("td");
					xtd4.appendChild(document.createTextNode("Payment ID"));
					
					var xtd5 = document.createElement("td");
					xtd5.appendChild(document.createTextNode("Amount"));
					
					xtr.appendChild(xtd1);
					xtr.appendChild(xtd2);
					xtr.appendChild(xtd3);
					xtr.appendChild(xtd4);
					xtr.appendChild(xtd5);
					
					var xReferralTable = getObjectById("referralTable1");
					xReferralTable.appendChild(xtr);
				
					for(var ctr=1;ctr<=count;ctr++){
						var tr = document.createElement("tr");
						tr.setAttribute("style", "background-color:"+getColor()+";");
						
						var td1 = document.createElement("td");
						td1.appendChild(document.createTextNode(getValue("date_"+ctr)));
						
						var td2 = document.createElement("td");
						td2.appendChild(document.createTextNode(getValue("payerid_"+ctr)));
						
						var td3 = document.createElement("td");
						td3.appendChild(document.createTextNode(getValue("payeeid_"+ctr)));
						
						var td4 = document.createElement("td");
						td4.appendChild(document.createTextNode(getValue("id_"+ctr)));
						
						var td5 = document.createElement("td");
						td5.appendChild(document.createTextNode(getValue("amount_"+ctr)));
						
						tr.appendChild(td1);
						tr.appendChild(td2);
						tr.appendChild(td3);
						tr.appendChild(td4);
						tr.appendChild(td5);
						
						var referralTable1 = getObjectById("referralTable1");
						referralTable1.appendChild(tr);
					}
				}
				displayMessage(INFO,"Complete");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleReferralSummaryResponse(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				getObjectById("referralTable").innerHTML = "";
				var count = getValue("count");
				if (count>0){
					var xtr = document.createElement("tr");
					xtr.setAttribute("style", "background-color: #324F81;color: #FFFFFF;");
					
					var xtd1 = document.createElement("td");
					xtd1.appendChild(document.createTextNode("Company Code"));
					
					var xtd2 = document.createElement("td");
					xtd2.appendChild(document.createTextNode("Real Person visit count"));
					
					var xtd3 = document.createElement("td");
					xtd3.appendChild(document.createTextNode("Bot visit count"));
					
					xtr.appendChild(xtd1);
					xtr.appendChild(xtd2);
					xtr.appendChild(xtd3);
					
					var xReferralTable = getObjectById("referralTable");
					xReferralTable.appendChild(xtr);
				
					for(var ctr=1;ctr<=count;ctr++){
						var tr = document.createElement("tr");
						tr.setAttribute("style", "background-color:"+getColor()+";");
						
						var td1 = document.createElement("td");
						td1.appendChild(document.createTextNode(getValue("company_"+ctr)));
						
						var td2 = document.createElement("td");
						td2.appendChild(document.createTextNode(getValue("nonbot_"+ctr)));
						
						var td3 = document.createElement("td");
						td3.appendChild(document.createTextNode(getValue("bot_"+ctr)));
						
						tr.appendChild(td1);
						tr.appendChild(td2);
						tr.appendChild(td3);
						
						var referralTable = getObjectById("referralTable");
						referralTable.appendChild(tr);
					}
				}
				var mesg = "Referral Summary for "+getValue("userid")+" NM between "+getValue("startdate")+" and "+getValue("enddate")+".";
				getObjectById("referralCountDiv").innerHTML = mesg;
				displayMessage(INFO,"Completed.");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleRegisterResponse1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Authentication code sent to your Email ID");
				getObjectById("authenticationCode").value=getValue("authcode");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleThreadRequest1(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				alert('Image Check Completed');
			}else{
				alert('Image Check Failed');
			}
		}
	}
}

function handleCategoryImport(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				getObjectById("initDiv2").innerHTML = "<h2>Category List Import Completed. </h2>";
				alert('Category Import Completed');
			}else{
				alert('Category Import Failed');
			}
		}
	}
}

function handleForgottenPasswordRequest(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,getValue("message"));
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleVendorMailingRequest(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,getValue("message"));
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleAddNMRequest(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,getValue("message"));
				var marketId = getValue("marketId");
				var subMarketId = getValue("subMarketId");
				var subMarketName = getValue("subMarketName");
					
				var preparedDiv = document.createElement("div");
				preparedDiv.setAttribute("id", "nm2nm_"+marketId+"_"+subMarketId);
				
				var table_element = document.createElement("table");
				table_element.setAttribute("border", "0");
				table_element.setAttribute("style","padding-top: 5px; background-color: #E8F2FE");
				
				var row_element = document.createElement("tr");
				row_element.setAttribute("valign", "middle");
				
				var column1_element = document.createElement("td");
				column1_element.setAttribute("width", "50");
				var column2_element = document.createElement("td");
				column2_element.setAttribute("width", "120");
				var column3_element = document.createElement("td");
				column3_element.setAttribute("width", "200");
				
				var input_element = document.createElement("input");
				input_element.setAttribute("type", "image");
				input_element.setAttribute("src", "images/common/RemoveBlue.jpg");
				var fn = "removeNM('"+marketId+"','"+subMarketId+"')";
				input_element.setAttribute("onclick", fn);
				
				var text1_element = document.createTextNode(subMarketId);
				var text2_element = document.createTextNode(subMarketName);
				
				column1_element.appendChild(input_element);
				column2_element.appendChild(text1_element);
				column3_element.appendChild(text2_element);
				row_element.appendChild(column1_element);
				row_element.appendChild(column2_element);
				row_element.appendChild(column3_element);
				table_element.appendChild(row_element);
				preparedDiv.appendChild(table_element);
				getObjectById("openListing").appendChild(preparedDiv);
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleRemoveNMRequest(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketId");
				var subMarketId = getValue("subMarketId");
				var div2Remove = getObjectById("nm2nm_"+marketId+"_"+subMarketId);
				div2Remove.innerHTML="";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleUpgradeNMRequest(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed");
				var marketId = getValue("marketId");
				var marketName = getValue("marketName");
					
				var upgradeNmDiv = document.createElement("div");
				upgradeNmDiv.setAttribute("id", "superNM_"+marketId);
				
				var table_element = document.createElement("table");
				table_element.setAttribute("border", "0");
				table_element.setAttribute("style","padding-top: 5px; background-color: #E8F2FE");
				
				var row_element = document.createElement("tr");
				row_element.setAttribute("valign", "middle");
				
				var column1_element = document.createElement("td");
				column1_element.setAttribute("width", "50");
				var column2_element = document.createElement("td");
				column2_element.setAttribute("width", "120");
				var column3_element = document.createElement("td");
				column3_element.setAttribute("width", "200");
				
				var input_element = document.createElement("input");
				input_element.setAttribute("type", "image");
				input_element.setAttribute("src", "images/common/RemoveBlue.jpg");
				var fn = "degrade2NM('"+marketId+"')";
				input_element.setAttribute("onclick", fn);
				
				var text1_element = document.createTextNode(marketId);
				var text2_element = document.createTextNode(marketName);
				
				column1_element.appendChild(input_element);
				column2_element.appendChild(text1_element);
				column3_element.appendChild(text2_element);
				row_element.appendChild(column1_element);
				row_element.appendChild(column2_element);
				row_element.appendChild(column3_element);
				table_element.appendChild(row_element);
				upgradeNmDiv.appendChild(table_element);
				getObjectById("superNmMasterDiv").appendChild(upgradeNmDiv);
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleDegradeNMRequest(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketId");
				var div2Remove = getObjectById("superNM_"+marketId);
				div2Remove.innerHTML="";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleAddVendorDirectly(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketid");
				var companyCode = getValue("companycode");
				var companyName = getValue("companyname");
				var vendorCount = getValue("vendorcount");
				var nmstatus = getValue("nmstatus");
				
				var preparedDiv = document.createElement("div");
				preparedDiv.setAttribute("id", "direct_"+companyCode);
				
				var table_element = document.createElement("table");
				table_element.setAttribute("border", "0");
				table_element.setAttribute("style","padding-top: 5px; background-color: #E8F2FE");
				
				var row_element = document.createElement("tr");
				row_element.setAttribute("valign", "middle");
				
				var column2_element = document.createElement("td");
				column2_element.setAttribute("width", "200");
				var column3_element = document.createElement("td");
				column3_element.setAttribute("width", "600");
				
				var text1_element = document.createTextNode(companyCode);
				var text2_element = document.createTextNode(companyName);
				column2_element.appendChild(text1_element);
				column3_element.appendChild(text2_element);
				row_element.appendChild(column2_element);
				row_element.appendChild(column3_element);
				table_element.appendChild(row_element);
				preparedDiv.appendChild(table_element);
				getObjectById("vacant").appendChild(preparedDiv);
				
				if(nmstatus != "premium" && vendorCount>=10){
					getObjectById("div-add-vendor-directly").innerHTML=getObjectById("limitexceededmessage").value;
				}
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleDeleteCompanies(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleDeleteNMs(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleUpdateDomainName(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				getObjectById("domaindiv_"+getValue("marketid")).innerHTML="<span style=\"color:#0E6B2D;\">Updated</span>";
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleReportFetch(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				var opt = document.createElement("option");
				var optvalue = "";
				var optname = "--- Select ---";
				var displayText = document.createTextNode(optname);
				opt.appendChild(displayText);
				opt.setAttribute("value", optvalue);
				getObjectById("repolist").options.add(opt);
				
				var count = getValue("count");
				for(var ctr=1;ctr<=count;ctr++){
					var opt = document.createElement("option");
					var optvalue = getValue("value_"+ctr);
					var optname = getValue("name_"+ctr);
					var displayText = document.createTextNode(optname);
					opt.appendChild(displayText);
					opt.setAttribute("value", optvalue);
					getObjectById("repolist").options.add(opt);
				}
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleFetchCharges(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				getObjectById("market-charge").value=getValue("marketid");
				getObjectById("add-vendor").value=getValue("add");
				getObjectById("feature-vendor").value=getValue("feature");
				if(getValue("default")=="true"){
					displayMessage(INFO,"Completed. Default charges applicable to the vendor");
				}else{
					displayMessage(INFO,"Completed. Charges are explicitly set for this vendor");
				}
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleUpdateCharges(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed. Charges are explicitly set for this vendor");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleDefaultCharges(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				getObjectById("market-charge").value=getValue("marketid");
				getObjectById("add-vendor").value=getValue("add");
				getObjectById("feature-vendor").value=getValue("feature");
				if(getValue("default")=="true"){
					displayMessage(INFO,"Completed. Default charges applicable to the vendor");
				}else{
					displayMessage(INFO,"Completed. Charges are explicitly set for this vendor");
				}
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleFetchFunds(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				getObjectById("selectedmarket").value=getValue("marketid");
				getObjectById("regularfund").value=getValue("regular");
				getObjectById("outreachfund").value=getValue("outreach");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleAddFund(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Success! Final OUTREACH FUND is now $"+getValue("outreach"));
				getObjectById("selectedmarket").value=getValue("marketid");
				getObjectById("regularfund").value=getValue("regular");
				getObjectById("outreachfund").value=getValue("outreach");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleFlipType(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketId");
				var premium = getValue("premium");
				var imageObject = getObjectById("spanid_"+marketId);
				if(premium==1){
					imageObject.src="images/common/premium.png";
				}else{
					imageObject.src="images/common/free.png";
				}
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function handleFlipLazy(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
				var marketId = getValue("marketId");
				var lazy = getValue("lazy");
				var lazyObject = getObjectById("lazy_"+marketId);
				if(lazy==1){
					lazyObject.value="Auto Display: TRUE";
				}else{
					lazyObject.value="Auto Display: FALSE";
				}
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}

function dummy(){
	if (req.readyState == 4) {
		if(req.status == 200) {
			if(!error()){
				displayMessage(INFO,"Completed.");
			}else{
				displayMessage(ERROR,getValue("errmsg"));
			}
		}
	}
}


////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

function switchBusiness(chosen, divId){
	var other = getObjectById("other_"+chosen).value;
	getObjectById(divId+chosen).style.display="inline";
	getObjectById(divId+other).style.display="none";
}

////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////

function error(){
	var xmlDoc=req.responseXML.documentElement;
	var error = xmlDoc.getElementsByTagName("error")[0].childNodes[0].nodeValue;
	if(error == 1 || getValue("errmsg") == 'Category already in the list.'){
		return true;
	}else{
		return false;
	}
}

function getValue(element){
	var xmlDoc=req.responseXML.documentElement;
	//var value = xmlDoc.getElementsByTagName(element)[0].childNodes[0].nodeValue;
	var v1 = xmlDoc.getElementsByTagName(element)[0];
	if(v1==null){
		return "";
	}else{
		var v2 = v1.childNodes[0];
		if(v2==null){
			return "";
		}else{
			var value = v2.nodeValue;
			if(value==null){
				return "";
			}else{
				return value;
			}
		}
	}
}

String.prototype.startsWith = function(str) {
	return (this.match("^"+str)==str);
};

function displayMessage(type, message){
	var msgElement = getObjectById("message");
	if(type==ERROR){
		msgElement.setAttribute("style", "color: #fff; background-color:#F44B4B; padding: 4px;");
		getObjectById("message").innerHTML="ERROR: "+message;
	}else if(type==INFO){
		msgElement.setAttribute("style", "color: #fff; background-color:#5EB253; padding: 4px;");
		getObjectById("message").innerHTML=""+message;
	}
}

function changeImage(){
	getObjectById("div_image").innerHTML="";
	var productCode = getValueById("select_products");
	var imageUrl = getValueById("thImage_"+productCode);
	var imageTag = document.createElement("img");
	imageTag.setAttribute("border", 1);
	imageTag.setAttribute("class", "thumbnailImage");
	imageTag.setAttribute("src", imageUrl);
	getObjectById("div_image").appendChild(imageTag);
}

function getValueById(id){
	return getObjectById(id).value;
}

function getObjectById(id){
	return document.getElementById(id);
}

function hasValidCharacters(validationString, notAllowed){
	for(var pos=0;pos<validationString.length;pos++){
		if (notAllowed.indexOf(validationString.charAt(pos)) != -1) {
			return false;
		}
	}
	return true;
}

function isValidEmailId(emailId){
	var indexOfAt = emailId.indexOf('@');
	var indexOfDot = emailId.lastIndexOf('.');
	if(indexOfAt!=-1 && indexOfDot!=-1 && indexOfDot > indexOfAt){
		return true;
	}
	return false;
}

function getColor(){
	var pointer = getValueById("pointer");
	var color = getValueById("color"+pointer);
	if (pointer=="1")
		pointer=2;
	else
		pointer=1;
	getObjectById("pointer").value=pointer;
	return color;
}

function changeSubmitButtonStatus(){
	var btnStatus = getObjectById("agreement");
	var submitBtn = getObjectById("submitButton");
	if(btnStatus.checked){
		submitBtn.disabled=false;
		submitBtn.setAttribute("style","color:#333;");
	}else{
		submitBtn.disabled=true;
		submitBtn.setAttribute("style","color:#999;");
	}
}

function getHelpScreen(screenId){
	var width=500;
	var height=400;
	var scrWidth=screen.width;
	var scrHeight=screen.height;
	var top=(scrHeight-height)/2;
	var left=(scrWidth-width)/2;
	var url="HelpScreen.jsp?screenId="+screenId;
	panel='toolbar=0,location=0, directories=0, status=0, menubar=0,scrollbars=1,resizable=1';
	panel=panel+',width='+width;
	panel=panel+',height='+height;
	panel=panel+',left='+left;
	panel=panel+',top='+top;
	newwindow2=window.open(url,'',panel);
}

var pageContentEditor = getHtmlEditor("pagedesk");

function getHtmlEditor(element){
	var myEditor = new YAHOO.widget.Editor(element, {
	height: '300px',
	width: '700px',
	dompath: true, //Turns on the bar at the bottom
	animate: true //Animates the opening, closing and moving of Editor windows
	});
	myEditor.render();
	return myEditor;
}

function saveHtml(myEditor){
	myEditor.saveHTML();
}

function getEditorValue(myEditor){
	return myEditor.get('element').value;
}

function getRandomString() {
	var chars = "0123456789abcdefghiklmnopqrstuvwxyz";
	var string_length = 8;
	var randomstring = '';
	for (var i=0; i<string_length; i++) {
		var rnum = Math.floor(Math.random() * chars.length);
		randomstring += chars.substring(rnum,rnum+1);
	}
	return randomstring;
}
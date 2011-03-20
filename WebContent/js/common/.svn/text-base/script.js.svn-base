// dd menu
var timeout         = 500;
var closetimer		= 0;
var ddmenuitem      = 0;

// open hidden layer
function mopen(id)  
{	
	// cancel close timer
	mcancelclosetime();
	
	// close old layer
	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';
	
	// get new layer and show it
	ddmenuitem = document.getElementById(id);
	ddmenuitem.style.visibility = 'visible';
}

// close showed layer
function mclose()
{
	if(ddmenuitem) ddmenuitem.style.visibility = 'hidden';
}

// go close timer
function mclosetime()
{
	closetimer = window.setTimeout(mclose, timeout);
}

// cancel close timer
function mcancelclosetime()
{
	if(closetimer)
	{
		window.clearTimeout(closetimer);
		closetimer = null;
	}
}

// close layer when click-out
document.onclick = mclose; 

var fixedImageActive = false;
var biggerImageActive = false;

function ShowHideFixedBiggerImage(div)
{
	if(fixedImageActive){
		HidePopup(div);
	}
	else{
		ShowPopup(div);
	}
}

function ShowPopup(div){
	ShowBiggerImage(div);
	fixedImageActive = true;
}
//image scroller
function ShowBiggerImage(div)
{
	if(!biggerImageActive){
		document.getElementById(div).style.left = (screen.width/2 - 150) + "px";
		document.getElementById(div).style.display = "inline";
		biggerImageActive = true;
	}
}
function ShowDefaultImage(div)
{
	if(!fixedImageActive){
		document.getElementById(div).style.display = "none";
		biggerImageActive = false;
	}
}
function HidePopup(div)
{
		document.getElementById(div).style.display = "none";
		fixedImageActive = false;
		biggerImageActive = false;
}

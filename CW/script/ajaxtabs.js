var bustcachevar=1 //bust potential caching of external pages after initial request? (1=yes, 0=no)
var loadstatustext="<img src='../images/loading.gif' /> Requesting content..."

////NO NEED TO EDIT BELOW////////////////////////
var loadedobjects=""
var defaultcontentarray=new Object()
var bustcacheparameter=""


//对返回的中文编码处理
function Rec_Html(Html)
{
	//alert(Html)
	var Rec=new ActiveXObject("ADODB.RecordSet");	
	Rec.Fields.Append("DDD",201,1);	
	Rec.Open();	
	Rec.AddNew();	
	Rec(0).AppendChunk(Html);	
	Rec.Update();	
	return Rec(0).Value;
	Rec.Close();
}
//设置焦点
function setFocus() {
	var frm = document.forms[0];
	//alert(frm);
	if (frm != null)
	{
	
		if ( typeof(frm.queryItemCode) != "undefined")
		{
		
			frm.queryItemCode.select();
			frm.queryItemCode.focus();
		}
	}
	
}

function ajaxpage2(url, containerid, myForm){ //用于表单post

	var paramString = getPostParameters(myForm);
	var page_request = false
	if (window.XMLHttpRequest) // if Mozilla, Safari etc
		page_request = new XMLHttpRequest()
	else if (window.ActiveXObject){ // if IE
		try {
			page_request = new ActiveXObject("Msxml2.XMLHTTP")
		} 
		catch (e){
			try{
				page_request = new ActiveXObject("Microsoft.XMLHTTP")
			}
			catch (e){}
		}
	}
	else {
		return false 
	}

	if (url.indexOf("#default")!=-1){ //if simply show default content within container (verus fetch it via ajax)
		document.getElementById(containerid).innerHTML=defaultcontentarray[containerid]
		return
	}

	document.getElementById(containerid).innerHTML=loadstatustext //显示等待图片
	
	page_request.onreadystatechange=function(){ //监听函数
		
		loadpage(page_request, containerid)
			
	}
		
	page_request.open('POST', url, true)
	page_request.setRequestHeader("Content-Type","application/x-www-form-urlencoded")
	//page_request.setrequestheader("content-length",paramString.length); 
	paramString = encodeURI(paramString);
	paramString = encodeURI(paramString);//两次，很关键
	//alert(paramString)
	page_request.send( paramString )
	
}

//拼装POST参数
function getPostParameters(formElements) {
	var parameterString = "";
	if (formElements )
	{
		for ( var i = 0; i < formElements.length; i ++)
		{
			
			if( formElements[i].type =='radio' || formElements[i].type=='checkbox' )
			{
				if ( !formElements[i].disabled && formElements[i].checked )
				{
					parameterString = parameterString + formElements[i].name + '=' + formElements[i].value + '&'; 
					
				} else {
					continue;
				}
			}  else {
				parameterString = parameterString + formElements[i].name + '=' + formElements[i].value + '&';   
			}

		}
		
		parameterString = parameterString + "temp=1"; 
	}
	
	return parameterString;
}

function ajaxpage(url, containerid, targetobj){

	var page_request = false
	if (window.XMLHttpRequest) // if Mozilla, Safari etc
		page_request = new XMLHttpRequest()
	else if (window.ActiveXObject){ // if IE
		try {
			page_request = new ActiveXObject("Msxml2.XMLHTTP")
		} 
		catch (e){
			try{
				page_request = new ActiveXObject("Microsoft.XMLHTTP")
			}
			catch (e){}
		}
	}
	else {
		return false 
	}

	if (typeof(targetobj)=="object")//不是从菜单提交
	{
		var ullist=targetobj.parentNode.parentNode.getElementsByTagName("li")
		//deselect all tabs
		for (var i=0; i<ullist.length; i++) {
			ullist[i].className=""  
		}
		targetobj.parentNode.className="selected"  //highlight currently clicked on tab
	}


	if (url.indexOf("#default")!=-1){ //if simply show default content within container (verus fetch it via ajax)
		document.getElementById(containerid).innerHTML=defaultcontentarray[containerid]
		return
	}

	document.getElementById(containerid).innerHTML=loadstatustext //显示等待图片

	page_request.onreadystatechange=function(){ //监听函数
	
		loadpage(page_request, containerid)
	}
		
	if (bustcachevar) {//if bust caching of external page
		bustcacheparameter=(url.indexOf("?")!=-1)? "&"+new Date().getTime() : "?"+new Date().getTime()
		
	}

	page_request.open('GET', url+bustcacheparameter, true)
	page_request.setRequestHeader("Content-Type","gb2312")
	page_request.send(null)
		
}


function loadpage(page_request, containerid){
	
	if (page_request.readyState == 4 && (page_request.status==200 || window.location.href.indexOf("http")==-1)) {
		
		//alert(page_request.responseText.length);
		document.getElementById(containerid).innerHTML = page_request.responseText
		//document.getElementById(containerid).innerHTML=bytes2BSTR(page_request.responseBody)
		setFocus();
	}
}

function loadobjs(revattribute) {

	if (revattribute!=null && revattribute!="") { //if "rev" attribute is defined (load external .js or .css files)
		var objectlist=revattribute.split(/\s*,\s*/) //split the files and store as array
		for (var i=0; i<objectlist.length; i++) {
			var file=objectlist[i]
			var fileref=""
			if (loadedobjects.indexOf(file)==-1) { //Check to see if this object has not already been added to page before proceeding
				if (file.indexOf(".js")!=-1) { //If object is a js file
					fileref=document.createElement('script')
					fileref.setAttribute("type","text/javascript");
					fileref.setAttribute("src", file);
				} else if (file.indexOf(".css")!=-1) { //If object is a css file
					fileref=document.createElement("link")
					fileref.setAttribute("rel", "stylesheet");
					fileref.setAttribute("type", "text/css");
					fileref.setAttribute("href", file);
				}
			}

			if (fileref!=""){
				document.getElementsByTagName("head").item(0).appendChild(fileref)
				loadedobjects+=file+" " //Remember this object as being already added to page
			}

		}
	}
}

function expandtab(tabcontentid, tabnumber){ //interface for selecting a tab (plus expand corresponding content)
	var thetab=document.getElementById(tabcontentid).getElementsByTagName("a")[tabnumber]
	if (thetab.getAttribute("rel")){
		ajaxpage(thetab.getAttribute("href"), thetab.getAttribute("rel"), thetab)
		//loadobjs(thetab.getAttribute("rev"))
	}
}

function savedefaultcontent(contentid){// save default ajax tab content
	if (typeof defaultcontentarray[contentid]=="undefined") { //if default content hasn't already been saved
		defaultcontentarray[contentid]=document.getElementById(contentid).innerHTML
	}
}

function startajaxtabs(){
	for (var i=0; i<arguments.length; i++){ //loop through passed UL ids
		var ulobj=document.getElementById(arguments[i])
		var ulist=ulobj.getElementsByTagName("li") //array containing the LI elements within UL
		for (var x=0; x<ulist.length; x++){ //loop through each LI element
			var ulistlink=ulist[x].getElementsByTagName("a")[0]

			if (ulistlink.getAttribute("rel")){
				var modifiedurl=ulistlink.getAttribute("href")
				ulistlink.setAttribute("href", modifiedurl) //replace URL's root domain with dynamic root domain, for ajax security sake
				savedefaultcontent(ulistlink.getAttribute("rel")) //save default ajax tab content
				ulistlink.onclick=function(){
					ajaxpage(this.getAttribute("href"), this.getAttribute("rel"), this)
					//loadobjs(this.getAttribute("rev"))
					return false
				}
				if (ulist[x].className=="selected"){ //显示页面li的class为select的tab页
					ajaxpage(ulistlink.getAttribute("href"), ulistlink.getAttribute("rel"), ulistlink) //auto load currenly selected tab content
					//loadobjs(ulistlink.getAttribute("rev")) //auto load any accompanying .js and .css files
				}
			}
		}
	}
}
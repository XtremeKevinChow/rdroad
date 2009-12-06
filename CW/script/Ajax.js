/**
* Ajax封装对象
* author:user
* date:2006-11-22
*/

function Ajax(_url, _queryString, _callBackMethod) {

	this.url = _url;
	this.queryString = _queryString;
	this.callBackMethod = _callBackMethod;

	this.createXMLRequest = function() {
		try {
		  this.xmlRequest = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
		  try {
			this.xmlRequest = new ActiveXObject("Microsoft.XMLHTTP");
		  } catch (e2) {
			this.xmlRequest = false;
		  }
		}
		
	}

	this.init = function() {
		var owner = this;
		this.createXMLRequest();
		this.xmlRequest.onreadystatechange = function(){ 
			owner.stateChange.call(owner); 
		}
	
	}

	this.postRequest = function(){
		this.init();
		this.xmlRequest.open("POST", this.url);
		this.xmlRequest.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
		this.xmlRequest.send(this.queryString);
	}

	this.getRequest = function(){
		this.init();
		this.xmlRequest.open("GET", this.url, true);
		this.xmlRequest.send(null);
	}
	
	this.stateChange = function(){
		if(this.xmlRequest.readyState == 4){
			if(this.xmlRequest.status == 200){
				this.callBackMethod.call(null, this.xmlRequest.responseText);
			}else{
				alert("请求失败!");
			}
		}
	}
}
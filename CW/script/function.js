function	check_phone (str)
{
  	var bflag=true
	var nLen=str.length;
	for(i=0;i<nLen;i++)
	{
		if(!(str.charAt(i)>='0' && str.charAt(i)<='9') && (str.charAt(i)!='-'))
			bflag=false
	}
	return bflag
	
}
function	is_phone (str)
{
	if (!(check_phone (str)))
	{
	alert("电话号码输入错误,格式应为(**-)*****!");
	return false;
	}
	return true;
}
function	is_fax (str)
{
	if (!(check_phone (str)))
	{
	alert("传真输入错误,格式应为(**-)*****!");
	return false;
	}
	return true;
}

function	check_zip (str)
{
  	var bflag=true
	var nLen=str.length
	for(i=0;i<nLen;i++)
	{
		if(!(str.charAt(i)>='0' && str.charAt(i)<='9'))
			bflag=false
	}
	return bflag
	
}

function	is_zip (str)
{
	if (!(check_zip (str)))
	{
	alert("邮政编码输入错误,格式应为999999!");
	return false;
	}
	return true;
}

function	check_number ( field, len1, len2 )
{
	var	re	= /^[0-9]{1,}\.{0,1}[0-9]{0,}0*$/;
	var js_value = field.value;
	if ( js_value.length == 0)
	{
		return true ;
	}
	if ( js_value.match(re) )
	{
		var dot_position = js_value.indexOf('.');
		var int_value, dec_value;
		if (dot_position >= 0)
		{
			int_value = js_value.substring(0,dot_position);
			dec_value = js_value.substring(dot_position + 1);
		}
		else
		{
			int_value = js_value;
			dec_value = "";
		}
		if (int_value.length > len1)
		{
			alert("输入的整数部分太大！应该是" + len1 + "位");
			field.value = "";
			field.focus();
			return false;
		}
		if (dec_value.length > len2)
		{
			alert("输入的小数位数太长！应该是" + len2 + "位");
			field.value = "";
			field.focus();
			return false;
		}
		return true ;
	}
	alert("输入的的不是数字！");
	field.value = "";
	field.focus();
	return false;
}

function	is_number ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if ( isNaN(js_value) )
	{
		return false ;
	}
	return true ;
}

function	is_integer ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}
	if ( isNaN(js_value) || js_value.indexOf('.',0) >= 0 )	
	{
		return false ;
	}
	return true ;
}

function	is_natural ( js_value )
{
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{ 
		return true ;
	}

	re = /^\+{0,1}[0-9]*$/ ;
	if ( !js_value.match(re) ) return false ;
	return true ;
}

//只能为数字、英文字母、下划线或中划线
function	is_validatestr (js_value)
{
	var	re ;
	
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}

	re = /^\+{0,1}([0-9]|[a-z]|-|_)*$/i ;
	if ( !js_value.match(re) ) return false ;
	return true ;
} 

function	is_email ( js_value )
{
	var	pos ;
	var	re ;
	re = /^\s*$/ ;

	if ( js_value.match(re) )
	{
		return true ;
	}

	pos = js_value.indexOf( '@',0 ) ;
	if ( js_value.length <= 5 ) return false ;
	if ( pos==-1 || pos==0 || pos==(js_value.length-1) ) return false ;

	pos = js_value.indexOf( '.',0 ) ;
	if ( pos<=0 || pos==(js_value.length-1) ) return false ;

	return true ;
}

function StrLength(str) 
{ 

var num1 = escape(str).split("%u").length-1;
var num2 = str.length-num1+num1*2;

return num2;

} 

function isEngChar(str)
{
	var bflag=true
	var nLen=str.length
	for(i=0;i<nLen;i++)
	{
		if(!((str.charAt(i)<'z' && str.charAt(i)>'a') || (str.charAt(i)<'Z' && str.charAt(i)>'A')))
			bflag=false
	}
	return bflag
}
function Cstr(inp)
{
  return(""+inp+"");
}

function replace(target,oldTerm,newTerm,caseSens,wordOnly) 
{ var wk ;
  var ind = 0; 
  var next = 0; 
  wk=Cstr(target); 
  if (!caseSens) {
  oldTerm = oldTerm.toLowerCase();    
  wk = target.toLowerCase();  }
  while ((ind = wk.indexOf(oldTerm,next)) >= 0) 
  {    if (wordOnly) {
      var before = ind - 1;     
	   var after = ind + oldTerm.length;
      if (!(space(wk.charAt(before)) && space(wk.charAt(after)))) {
        next = ind + oldTerm.length;     
		   continue;      }    }
 target = target.substring(0,ind) + newTerm + target.substring(ind+oldTerm.length,target.length);
 wk = wk.substring(0,ind) + newTerm + wk.substring(ind+oldTerm.length,wk.length);
 next = ind + newTerm.length;    
if (next >= wk.length) { break; }
  }
  return target;
  }

function Rep1(str,len)
{var str1;
 str1=str;
 str1=replace(str1,"'","`",1,0);
 str1=replace(str1,'"',"`",1,0);
 str1=replace(str1,"<","(",1,0);
 str1=replace(str1,">",")",1,0);
 str1=deletechar(str1,len);
 return str1;
}

function Rep1(str)
{var str1;
 str1=str;
 str1=replace(str1,"'","`",1,0);
 str1=replace(str1,'"',"`",1,0);
 str1=replace(str1,"<","(",1,0);
 str1=replace(str1,">",")",1,0);
 return str1;
}


function isCharsInBag (s, bag)
{  
  var i;
  // Search through string's characters one by one.
  // If character is in bag, append to returnString.

  for (i = 0; i < s.length; i++)
  {   
      // Check that current character isn't whitespace.
      var c = s.charAt(i);
      if (bag.indexOf(c) == -1) return false;
  }
  return true;
}



function CheckMail( email ){
   var isDot=0;
   var isAt=0;
   for(k=0; k<email.length; k++){
       if(!(((email.charAt(k) >= 'a' ) && (email.charAt(k) <='z'))||
	  ((email.charAt(k) >= 'A' ) && (email.charAt(k) <='Z'))||
	  (email.charAt(k) == '_')||(email.charAt(k) == '@')||
	  (email.charAt(k) == '.')||(email.charAt(k) == '-')||
	  ((email.charAt(k) >= '0' ) && (email.charAt(k) <='9')))){
	      return false;
	}
	if (email.charAt(k)=='@'){
	    isAt++;
	}	
	if (email.charAt(k)=='.'){
	    isDot++;
	}
    }
    if (isAt!=1){
       return false;
    }
    if (isDot==0){
       return false;
    }
    if ((email.indexOf('@') == -1)||(email.indexOf('@') == 0)){
       return false;
    }
    var dotIndex = email.lastIndexOf('.');
    if (email.indexOf('@') >= (dotIndex-1)){
       return false;
    }
    if (dotIndex>=(email.length-2)){
       return false;
    }
    if (dotIndex<=(email.length-5)){
       return false;
    }
	return true;
}



function is_date( date_val) {
	var	month_val, year_val, day_val ;
	var	hour_val, minute_val, second_val;
	var num1,num2,ret;
	hour_val = 1;
	minute_val = 1;
	second_val = 1;
	num1 = date_val.indexOf("-",0);
	num2 = date_val.lastIndexOf("-");
	year_val = date_val.substring(0,num1 );
	month_val = date_val.substring(num1 + 1,num2);
	day_val = date_val.substr(num2 + 1);
	
	ret = chk_date ( year_val.toString(), month_val.toString(), day_val.toString(), hour_val.toString(), minute_val.toString(), second_val.toString());
	
	return ret;
}
function	chk_date ( y_val, m_val, d_val, h_val, mi_val, s_val)
{	
	var	month_val, year_val, day_val ;
	var	hour_val, minute_val, second_val;
	var	re ;
	re = /^\s*$/ ;
	
	
	if ( y_val.match(re) && m_val.match(re) && d_val.match(re)
		&& h_val.match (re) && mi_val.match (re) && s_val.match (re))
	{
		return true ;
	}

	if ( !is_natural(y_val) || !is_natural(m_val) || !is_natural(d_val) ) return false ;
	if (!is_natural (h_val) || !is_natural (mi_val) || !is_natural (s_val))	return	false;
	year_val	= parseInt (y_val, 10);
	month_val	= parseInt (m_val, 10);
	day_val		= parseInt (d_val, 10);
	hour_val	= parseInt (h_val, 10);
	minute_val	= parseInt (mi_val, 10);
	second_val	= parseInt (s_val, 10);
	
	if ( isNaN(year_val)  || isNaN(month_val) || isNaN(day_val)) return false;	
	
	if ( year_val<1900 || year_val>3000 ) return false ;
	if ( month_val<1 || month_val>12 ) return false ;
	switch ( month_val )
	{
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			if(day_val<1 || day_val>31) return false ;
			break ;
		case 4:
		case 6:
		case 9:
		case 11:
			if(day_val<1 || day_val>30) return false ;
			break ;
		case 2 :
			if( (year_val%4==0 && year_val%100!=0) || year_val%400==0 ) 
			{
				if (day_val<1 || day_val>29 ) return false ;
			}
			else
			{
				if (day_val<1 || day_val>28 ) return false ;
			}
			break ;
		default :

	}
	if (hour_val < 0 || hour_val > 23)	return	false;
	if (minute_val < 0 || minute_val > 59)	return	false;
	if (second_val < 0 || second_val > 59)	return	false;

	return true ;
}

function StrLengthW(str)
{
	var i,total_size=0
    
	for (i=0;i<str.length;i++)
	{
		if (str.charCodeAt(i) > 255)
			total_size=total_size + 2;
		else
			total_size=total_size + 1;
	}

	return total_size;
}

function price_changed(index)
{
	var record_num;
	var str_to_check;
	
	var num_dot=0;
	var num_afterdot_number=0;
	var test_value;
	
	var check_result=true;
	
	record_num=new Number(document.forms[0].hidRecNum.value);
	if(record_num>1)
	{
		str_to_check=document.forms[0].price[index].value;
	}
	else
	{
		str_to_check=document.forms[0].price.value;
	}

	for(i=0;i<str_to_check.length;i++)
	{
		num_val=str_to_check.charAt(i);
		if ((num_val >= "0" && num_val<= "9") || num_val == "." )
		{
			if (num_dot>0)
				num_afterdot_number++;
			if (num_val==".")
				num_dot++;					
		}
		else
		{
			window.alert("必须为数字")
			check_result = false;
			break;
		}
	}

	if(check_result==true)
	{
		if(num_dot>1)
		{ 
			window.alert("不能多于一个小数点")
			check_result = false;
		}
	}

	if(check_result==true)
	{
		if(num_afterdot_number>4)
		{ 
			window.alert("最多精确到小数点后四位")
			check_result= false;
		}
	}
	
	if(check_result==true)
	{
		test_value=new Number(str_to_check);
		if(test_value==0)
		{ 
			window.alert("值不能为0")
			check_result = false;
		}
	}
	
	if(check_result==false)
	{
		if(record_num>1)
		{
			document.forms[0].price[index].value='';
		}
		else
		{
			document.forms[0].price.value='';
		}	
		return false;
	}	
	f_sum(index);
}
function qty_changed(index)
{
	var record_num;
	var str_to_check;
	
	var num_dot=0;
	var num_afterdot_number=0;
	var test_value;
	
	var check_result=true;

	record_num=new Number(document.forms[0].hidRecNum.value);
	if(record_num>1)
	{
		str_to_check=document.forms[0].quantity[index].value;
	}
	else
	{
		str_to_check=document.forms[0].quantity.value;
	}

	for(i=0;i<str_to_check.length;i++)
	{
		num_val=str_to_check.charAt(i);
		if ((num_val >= "0" && num_val<= "9") || num_val == "." )
		{
			if (num_dot>0)
				num_afterdot_number++;
			if (num_val==".")
				num_dot++;					
		}
		else
		{
			window.alert("必须为数字")
			check_result = false;
			break;
		}
	}

	if(check_result==true)
	{
		if(num_dot>1)
		{ 
			window.alert("不能多于一个小数点")
			check_result = false;
		}
	}

	if(check_result==true)
	{
		if(num_afterdot_number>4)
		{ 
			window.alert("最多精确到小数点后四位")
			check_result= false;
		}
	}

	if(check_result==true)
	{
		test_value=new Number(str_to_check);
		if(test_value==0)
		{ 
			// window.alert("值不能为0")
			// check_result = false;
		}
	}
	
	/*if(check_result==false)
	{
		if(record_num>1)
		{
			document.forms[0].quantity[index].value='';
		}
		else
		{
			document.forms[0].quantity.value='';
		}	
		return false;
	}	
	f_sum(index);*/

	// update by xjwang 031009
	if(check_result==false)
	{
		if(record_num>1)
		{			
			document.forms[0].quantity[index].value='';
			document.forms[0].quantity[index].focus();
		}
		else
		{			
			document.forms[0].quantity.value='';
			document.forms[0].quantity.focus();
		}
	}	

	f_sum(index);
}

function f_check_qty(str_to_check)
{
	var num_dot=0;
	var num_afterdot_number=0;
	var test_value;
	
	var check_result=true;
	
	for(i=0;i<str_to_check.length;i++)
	{
		num_val=str_to_check.charAt(i);
		if ((num_val >= "0" && num_val<= "9") || num_val == "." )
		{
			if (num_dot>0)
				num_afterdot_number++;
			if (num_val==".")
				num_dot++;					
		}
		else
		{
			check_result = false;
			break;
		}
	}
	if(num_dot>1)
	{ 
		check_result = false;
	}
	if(num_afterdot_number>4)
	{ 
		check_result= false;
	}
	test_value=new Number(str_to_check);
	if(test_value==0)
	{ 
		// check_result = false;
	}
	
	return check_result;
}

function f_check_price(str_to_check)
{
	var num_dot=0;
	var num_afterdot_number=0;
	var test_value;
	
	var check_result=true;
	
	for(i=0;i<str_to_check.length;i++)
	{
		num_val=str_to_check.charAt(i);
		if ((num_val >= "0" && num_val<= "9") || num_val == "." )
		{
			if (num_dot>0)
				num_afterdot_number++;
			if (num_val==".")
				num_dot++;					
		}
		else
		{
			check_result = false;
			break;
		}
	}
	if(num_dot>1)
	{ 
		check_result = false;
	}
	if(num_afterdot_number>4)
	{ 
		check_result= false;
	}
	test_value=new Number(str_to_check);
	if(test_value==0)
	{ 
		check_result = false;
	}
	
	return check_result;
}


function is_ten_num(to_check)
{
		var result, re;      			//Declare variables.
		re = /\d{10}/ig;    			//判断是否为十位数字
		if(to_check.match(re))
			return true;
		else
			return false;
}
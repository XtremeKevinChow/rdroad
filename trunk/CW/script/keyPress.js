/**
* user 2004-04-04
*说明：此方法主要用于键盘方向键操作页面上的各类控件
*用法：
*1.导入keyPress.js文件 
*<script language="JavaScript" src="fubang/tmplts/lib/scripts/keyPress.js"></script>
*2.在body标签unload事件中加上init(tableID)方法
*ctrl+z---显示新增页面
*ctrl+v---显示页面详情
*ctrl+m----显示修改页面
*ctrl+s----保存，修改记录
*ctrl+d----删除记录
*ctrl+t----返回按钮
*/

var tableID;
//初始化表
function init2(table) {
	
	//initHotKey();
	tableID = table.id;
	if(table != "") {
		initID(table);//初始化id数组
	}
	
	document.onkeydown=keyDown1 //键盘监听器

}
//初始化热键
function initHotKey() {
	
	var objList = document.all;
	var val;
	for(var i = 0; i < objList.length; i ++) {
		
		if(objList[i].type == "button"){
			//alert(objList[i].name);
			val = getAllTrim(objList[i].value);
			if(val == "提交" || val == "记录" || val == "确定"){
				objList[i].id = "saveBtn";
			}
			if(val == "查询"){
				objList[i].id = "queryBtn";
			}
			if(val == "清空"){
				objList[i].id = "clearBtn";
			}
			if(val == "返回"){
				objList[i].id = "backBtn";
			}
			
			if(val == "关闭"){
				objList[i].id = "closeBtn";
			}
			if(val == "打印"){
				objList[i].id = "printBtn";
			}
		}
		if(objList[i].tagName == "IMG"){
		//alert(objList[i].src);
			var pos, fix;
			pos = objList[i].src.lastIndexOf("/");
			fix = objList[i].src.substring(pos + 1);
			
			if(fix == "modify.gif"){
				objList[i].id = "showModBtn";
			}
			if(fix == "add.gif"){
				objList[i].id = "showAddBtn";
			}
			if(fix == "view.gif"){
				objList[i].id = "showViewBtn";
			}
			if(fix == "del.gif"){
				objList[i].id = "delBtn";
			}
			if(fix == "confirm.gif" || fix == "feedback.gif"){
				objList[i].id = "confirmBtn";
			}
			if(fix == "print.gif"){
				objList[i].id = "printBtn";
			}
			if(fix == "button_advancedsearch.gif"){
				objList[i].id = "highBtn";
			}
			if(fix == "button_search.gif"){
				//objList[i].id = "";
			}
		}
	}
	
}
//按键
function keyDown1(e) { 

	var ieKey=event.keyCode;
	var newID;//新产生的id
	var obj;
	id = document.activeElement.id;//当前活动元素
	//defineOperMethod();//定义快捷键
	//得到活动元素所在tableid
	//alert(id);
	//if(id != ""){
		//tableID = document.activeElement.parentElement.parentElement.parentElement.parentElement.id;
	//}
	
	//alert(tableID);
	
	if(ieKey==37) {//如果“左键”键按下 
		//alert('you have press left');
		newID = goLeft(id,tableID);	
		if(!hasTheElement(newID)){
			newID = setRowIDZero(newID,tableID);
		}
	}
	
	if(ieKey==38) {//如果“上键”键按下 
		//alert('you have press up');
		newID = goUp(id,tableID);
		if(!hasTheElement(newID)){
			
			newID = goLeft(newID,tableID);
		}
		
		//下拉框处理
		if(isSelectElement(id)){//是下拉框
			return;
		}
		
	}
	if(ieKey==13) {//如果“右键”键按下 39 回车13
		//alert('you have press right');
		
		newID = goRight(id,tableID);
		
		if(!hasTheElement(newID)){
			
			newID = setColIDZero(newID,tableID);
		}
		
	}
	if(ieKey==40) {//如果“下键”键按下 
		//alert('you have press down');
		newID = goDown(id,tableID);
		if(!hasTheElement(newID)){
			
			newID = setColIDZero(id,tableID);
		}
		
		if(isSelectElement(id)){//是下拉框
			return;
		}
	}
	setFocus(newID);//新数组设置焦点
}

//新对象设置焦点
function setFocus(newID) {
	
	obj = document.getElementById(newID);
	//alert(newID);
	if(hasTheElement(newID)){//如果新产生的数组存在,设置焦点	
		obj.focus();
		if(!isSelectElement(newID)){
			obj.select();
		}
		//if(hasClickMethod(newID)){//如果拥有onclick事件,触发它(用于日期控件)
		//	obj.onclick();
		//}
	}
}
//向右移
function goRight(id,tableID) {
	
	currR = getCurrRowID(id);//当前行号
	currC = getCurrColID(id);//当前列号
	
	changeC = addColID(currC);//增加列号
	
	//alert("光标将移动到:"+currR + changeC);
	newID = tableID+":"+currR +","+ changeC;
	
	return newID;
	
}
//向左移
function goLeft(id,tableID) {
	
	currR = getCurrRowID(id);//当前行号
	currC = getCurrColID(id);//当前列号
	
	changeC = reduColID(currC);//增加列号
	
	newID = tableID+":"+currR + ","+changeC;
	//alert("光标将移动到:"+currR + changeC);
	return newID;

}
//向下移
function goDown(id, tableID) {
	//alert(id);
	currR = getCurrRowID(id);//当前行号
	currC = getCurrColID(id);//当前列号
	
	changeR = addRowID(currR);//增加行号
	newID = tableID+":"+changeR + ","+currC;
	//alert("光标将移动到:"+changeR + currC);
	return newID;
	
}
//向上移
function goUp(id, tableID) {
	
	currR = getCurrRowID(id);//当前行号
	currC = getCurrColID(id);//当前列号
	changeR = reduRowID(currR);//增加行号
	newID = tableID+":"+changeR +","+ currC;
	//alert("光标将移动到:"+changeR + currC);
	
	return newID;
	
}

//得到当前元素行号
function getCurrRowID(id) {
	pos0= id.indexOf(":");
	pos = id.indexOf(",");
	r = id.substring(pos0+1,pos);
	//alert("当前行号:"+r);
	return r;
	
}
//得到当前元素列号
function getCurrColID(id) {
	pos = id.indexOf(",");
	c = id.substring(pos+1);
	//alert("当前列号:"+c);
	return c;
}
//行增1(按了下键)
function addRowID(r) {
	r = parseInt(r);
	r++;
	//alert("变化后行号:"+r);
	return r;
}
//行减1(按了上键)
function reduRowID(r) {
	r = parseInt(r);
	r--;
	//alert("变化后行号:"+r);
	return r;
}
//列增1(按了右键)
function addColID(c) {
	c = parseInt(c);
	c++;
	//alert("变化后列号:"+c);
	return c;
}//列减1(按了左键)
function reduColID(c) {
	c = parseInt(c);
	c--;
	//alert("变化后列号:"+c);
	return c;
}
//重置行(用于光标位于最左端按下左键时)
function setRowIDZero(id,tableID) {
	r = parseInt(getCurrRowID(id));//当前行号
	c = parseInt(getCurrColID(id));//当前列号
	r --;
	c ++;
	id = tableID+":"+r+","+c;
	return id;
}
//重置列(用于光标位于最右端按下右键时)
function setColIDZero(id,tableID) {
	r = parseInt(getCurrRowID(id));//当前行号
	r ++;
	c = "0";
	id = tableID+":"+r+","+c;
	return id;
}
//判断离开焦点的元素是否是select
function isSelectElement(id) {
	
	var obj;
	var flag = false;
	if(id != "") {//元素存在
		obj = document.getElementById(id);
		if(obj.tagName == "SELECT"){
			flag = true;
		}
	}else{
		flag = false;
	}
	//alert(flag);
	return flag;
}
//判断是否有这个元素(此方法为了避免数组越界时出现脚本错误提示)
function hasTheElement(id) {
	//alert(id);
	var flag = document.getElementById(id) == null;
	if (flag) {
		return false;
	}else{
		return true;
	}
	
}
//判断元素是否有onclick方法(针对日期控件)
function hasClickMethod(id) {
	var str = document.getElementById(id).check;

	if(typeof(str) != "undefined"){
		var pos = str.indexOf("d,");
		if(pos != "-1"){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}

//快捷操作公用方法(用于(显示)添加、删除、(显示)修改，查看)
function keyForOperate(keyCode,btnType) {
	if ((event.ctrlKey)&&(event.keyCode==keyCode)){
		var objList = document.getElementById(btnType);
		//alert("id"+objList);
		if(objList != null){
			objList.onclick();
		}
	}
}
//定义快捷操作
function defineOperMethod() {
	
	keyForOperate("83","saveBtn");//保存快捷键(ctrl+S)
	keyForOperate("90","showAddBtn");//显示新增页面快捷键(ctrl+Z)
	keyForOperate("90","showAddBtn1");//显示新增页面快捷键(ctrl+Z)
	keyForOperate("86","showViewBtn");//显示详情快捷键(ctrl+V)
	keyForOperate("77","showModBtn");//显示修改页面快捷键(ctrl+M)
	keyForOperate("68","delBtn");//删除记录快捷键(ctrl+D)
	keyForOperate("68","delBtn1");//删除记录快捷键(ctrl+D)
	keyForOperate("84","backBtn");//返回快捷键(ctrl+T)
	keyForOperate("67","closeBtn");//关闭子页面(ctrl+C)
	keyForOperate("81","queryBtn");//查询按钮(ctrl+Q)
	keyForOperate("71","confirmBtn");//确认(ctrl+G)
	keyForOperate("88","clearBtn");//清空(ctrl+X)
	keyForOperate("76","printBtn");//打印(ctrl+L)
	keyForOperate("78","highBtn");//高级查询按钮(ctrl+N)
}

//设置数组(将元素id自动编号)
function initID(tableID) {

	var rows, cols, elementOfRow, tag, i, j;
	var r = 0, c = 0;//计数器
	var flag = false;
	rows = tableID.rows.length;//总行数

	cols = tableID.rows[0].cells.length;//总列数
	for(i = 0; i < rows; i ++){
		
		elementOfRow = tableID.rows[i].all.length;//每行总的元素长度
		//alert(tableID.rows[i].style.display);
		if(tableID.rows[i].style.display == "none") {continue;};
		for(j = 0; j < elementOfRow; j ++) {
			tag = tableID.rows[i].all[j];
			
			if(((tag.type == "text" || tag.type=="password") && tag.readOnly==false) || (tag.tagName == "SELECT" && tag.disabled == false) || tag.tagName == "TEXTAREA"){
				tag.id = tableID.id+":"+r+","+c;
				//alert(tag.name+": id = "+tag.id);
				c ++;//列自增
				flag = true;//跳行标记
			}
		}
		
		c = 0;//列置0
		//alert(flag);
		if(flag){
			r ++;//行自增
			flag = false;
		}

	}
	//将第一个活动元素设置焦点
	
	//document.getElementById(tableID.id+":0,0").focus();
	
}
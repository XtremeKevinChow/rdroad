/**
* user 2004-04-04
*˵�����˷�����Ҫ���ڼ��̷��������ҳ���ϵĸ���ؼ�
*�÷���
*1.����keyPress.js�ļ� 
*<script language="JavaScript" src="fubang/tmplts/lib/scripts/keyPress.js"></script>
*2.��body��ǩunload�¼��м���init(tableID)����
*ctrl+z---��ʾ����ҳ��
*ctrl+v---��ʾҳ������
*ctrl+m----��ʾ�޸�ҳ��
*ctrl+s----���棬�޸ļ�¼
*ctrl+d----ɾ����¼
*ctrl+t----���ذ�ť
*/

var tableID;
//��ʼ����
function init2(table) {
	
	//initHotKey();
	tableID = table.id;
	if(table != "") {
		initID(table);//��ʼ��id����
	}
	
	document.onkeydown=keyDown1 //���̼�����

}
//��ʼ���ȼ�
function initHotKey() {
	
	var objList = document.all;
	var val;
	for(var i = 0; i < objList.length; i ++) {
		
		if(objList[i].type == "button"){
			//alert(objList[i].name);
			val = getAllTrim(objList[i].value);
			if(val == "�ύ" || val == "��¼" || val == "ȷ��"){
				objList[i].id = "saveBtn";
			}
			if(val == "��ѯ"){
				objList[i].id = "queryBtn";
			}
			if(val == "���"){
				objList[i].id = "clearBtn";
			}
			if(val == "����"){
				objList[i].id = "backBtn";
			}
			
			if(val == "�ر�"){
				objList[i].id = "closeBtn";
			}
			if(val == "��ӡ"){
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
//����
function keyDown1(e) { 

	var ieKey=event.keyCode;
	var newID;//�²�����id
	var obj;
	id = document.activeElement.id;//��ǰ�Ԫ��
	//defineOperMethod();//�����ݼ�
	//�õ��Ԫ������tableid
	//alert(id);
	//if(id != ""){
		//tableID = document.activeElement.parentElement.parentElement.parentElement.parentElement.id;
	//}
	
	//alert(tableID);
	
	if(ieKey==37) {//���������������� 
		//alert('you have press left');
		newID = goLeft(id,tableID);	
		if(!hasTheElement(newID)){
			newID = setRowIDZero(newID,tableID);
		}
	}
	
	if(ieKey==38) {//������ϼ��������� 
		//alert('you have press up');
		newID = goUp(id,tableID);
		if(!hasTheElement(newID)){
			
			newID = goLeft(newID,tableID);
		}
		
		//��������
		if(isSelectElement(id)){//��������
			return;
		}
		
	}
	if(ieKey==13) {//������Ҽ��������� 39 �س�13
		//alert('you have press right');
		
		newID = goRight(id,tableID);
		
		if(!hasTheElement(newID)){
			
			newID = setColIDZero(newID,tableID);
		}
		
	}
	if(ieKey==40) {//������¼��������� 
		//alert('you have press down');
		newID = goDown(id,tableID);
		if(!hasTheElement(newID)){
			
			newID = setColIDZero(id,tableID);
		}
		
		if(isSelectElement(id)){//��������
			return;
		}
	}
	setFocus(newID);//���������ý���
}

//�¶������ý���
function setFocus(newID) {
	
	obj = document.getElementById(newID);
	//alert(newID);
	if(hasTheElement(newID)){//����²������������,���ý���	
		obj.focus();
		if(!isSelectElement(newID)){
			obj.select();
		}
		//if(hasClickMethod(newID)){//���ӵ��onclick�¼�,������(�������ڿؼ�)
		//	obj.onclick();
		//}
	}
}
//������
function goRight(id,tableID) {
	
	currR = getCurrRowID(id);//��ǰ�к�
	currC = getCurrColID(id);//��ǰ�к�
	
	changeC = addColID(currC);//�����к�
	
	//alert("��꽫�ƶ���:"+currR + changeC);
	newID = tableID+":"+currR +","+ changeC;
	
	return newID;
	
}
//������
function goLeft(id,tableID) {
	
	currR = getCurrRowID(id);//��ǰ�к�
	currC = getCurrColID(id);//��ǰ�к�
	
	changeC = reduColID(currC);//�����к�
	
	newID = tableID+":"+currR + ","+changeC;
	//alert("��꽫�ƶ���:"+currR + changeC);
	return newID;

}
//������
function goDown(id, tableID) {
	//alert(id);
	currR = getCurrRowID(id);//��ǰ�к�
	currC = getCurrColID(id);//��ǰ�к�
	
	changeR = addRowID(currR);//�����к�
	newID = tableID+":"+changeR + ","+currC;
	//alert("��꽫�ƶ���:"+changeR + currC);
	return newID;
	
}
//������
function goUp(id, tableID) {
	
	currR = getCurrRowID(id);//��ǰ�к�
	currC = getCurrColID(id);//��ǰ�к�
	changeR = reduRowID(currR);//�����к�
	newID = tableID+":"+changeR +","+ currC;
	//alert("��꽫�ƶ���:"+changeR + currC);
	
	return newID;
	
}

//�õ���ǰԪ���к�
function getCurrRowID(id) {
	pos0= id.indexOf(":");
	pos = id.indexOf(",");
	r = id.substring(pos0+1,pos);
	//alert("��ǰ�к�:"+r);
	return r;
	
}
//�õ���ǰԪ���к�
function getCurrColID(id) {
	pos = id.indexOf(",");
	c = id.substring(pos+1);
	//alert("��ǰ�к�:"+c);
	return c;
}
//����1(�����¼�)
function addRowID(r) {
	r = parseInt(r);
	r++;
	//alert("�仯���к�:"+r);
	return r;
}
//�м�1(�����ϼ�)
function reduRowID(r) {
	r = parseInt(r);
	r--;
	//alert("�仯���к�:"+r);
	return r;
}
//����1(�����Ҽ�)
function addColID(c) {
	c = parseInt(c);
	c++;
	//alert("�仯���к�:"+c);
	return c;
}//�м�1(�������)
function reduColID(c) {
	c = parseInt(c);
	c--;
	//alert("�仯���к�:"+c);
	return c;
}
//������(���ڹ��λ������˰������ʱ)
function setRowIDZero(id,tableID) {
	r = parseInt(getCurrRowID(id));//��ǰ�к�
	c = parseInt(getCurrColID(id));//��ǰ�к�
	r --;
	c ++;
	id = tableID+":"+r+","+c;
	return id;
}
//������(���ڹ��λ�����Ҷ˰����Ҽ�ʱ)
function setColIDZero(id,tableID) {
	r = parseInt(getCurrRowID(id));//��ǰ�к�
	r ++;
	c = "0";
	id = tableID+":"+r+","+c;
	return id;
}
//�ж��뿪�����Ԫ���Ƿ���select
function isSelectElement(id) {
	
	var obj;
	var flag = false;
	if(id != "") {//Ԫ�ش���
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
//�ж��Ƿ������Ԫ��(�˷���Ϊ�˱�������Խ��ʱ���ֽű�������ʾ)
function hasTheElement(id) {
	//alert(id);
	var flag = document.getElementById(id) == null;
	if (flag) {
		return false;
	}else{
		return true;
	}
	
}
//�ж�Ԫ���Ƿ���onclick����(������ڿؼ�)
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

//��ݲ������÷���(����(��ʾ)��ӡ�ɾ����(��ʾ)�޸ģ��鿴)
function keyForOperate(keyCode,btnType) {
	if ((event.ctrlKey)&&(event.keyCode==keyCode)){
		var objList = document.getElementById(btnType);
		//alert("id"+objList);
		if(objList != null){
			objList.onclick();
		}
	}
}
//�����ݲ���
function defineOperMethod() {
	
	keyForOperate("83","saveBtn");//�����ݼ�(ctrl+S)
	keyForOperate("90","showAddBtn");//��ʾ����ҳ���ݼ�(ctrl+Z)
	keyForOperate("90","showAddBtn1");//��ʾ����ҳ���ݼ�(ctrl+Z)
	keyForOperate("86","showViewBtn");//��ʾ�����ݼ�(ctrl+V)
	keyForOperate("77","showModBtn");//��ʾ�޸�ҳ���ݼ�(ctrl+M)
	keyForOperate("68","delBtn");//ɾ����¼��ݼ�(ctrl+D)
	keyForOperate("68","delBtn1");//ɾ����¼��ݼ�(ctrl+D)
	keyForOperate("84","backBtn");//���ؿ�ݼ�(ctrl+T)
	keyForOperate("67","closeBtn");//�ر���ҳ��(ctrl+C)
	keyForOperate("81","queryBtn");//��ѯ��ť(ctrl+Q)
	keyForOperate("71","confirmBtn");//ȷ��(ctrl+G)
	keyForOperate("88","clearBtn");//���(ctrl+X)
	keyForOperate("76","printBtn");//��ӡ(ctrl+L)
	keyForOperate("78","highBtn");//�߼���ѯ��ť(ctrl+N)
}

//��������(��Ԫ��id�Զ����)
function initID(tableID) {

	var rows, cols, elementOfRow, tag, i, j;
	var r = 0, c = 0;//������
	var flag = false;
	rows = tableID.rows.length;//������

	cols = tableID.rows[0].cells.length;//������
	for(i = 0; i < rows; i ++){
		
		elementOfRow = tableID.rows[i].all.length;//ÿ���ܵ�Ԫ�س���
		//alert(tableID.rows[i].style.display);
		if(tableID.rows[i].style.display == "none") {continue;};
		for(j = 0; j < elementOfRow; j ++) {
			tag = tableID.rows[i].all[j];
			
			if(((tag.type == "text" || tag.type=="password") && tag.readOnly==false) || (tag.tagName == "SELECT" && tag.disabled == false) || tag.tagName == "TEXTAREA"){
				tag.id = tableID.id+":"+r+","+c;
				//alert(tag.name+": id = "+tag.id);
				c ++;//������
				flag = true;//���б��
			}
		}
		
		c = 0;//����0
		//alert(flag);
		if(flag){
			r ++;//������
			flag = false;
		}

	}
	//����һ���Ԫ�����ý���
	
	//document.getElementById(tableID.id+":0,0").focus();
	
}
/**************************************************************************************

								��ѯ������������¼��ҳ(new)
  
**************************************************************************************/
function goFirst() {
	document.forms[0].offset.value = 0;
	document.forms[0].submit();
}
function goPrior(offset) {
	document.forms[0].offset.value = offset;
	document.forms[0].submit();
}
function goPage(currPage) {
	
	document.forms[0].offset.value = currPage;
	document.forms[0].submit();
}
function goNext(offset) {
	document.forms[0].offset.value = offset;
	document.forms[0].submit();
}
function goLast(lastOffset) {
	document.forms[0].offset.value = lastOffset;
	document.forms[0].submit();
}
/**************************************************************************************

								��ѯ������������¼��ҳ
  
**************************************************************************************/
// ������ַΪurl�ĶԻ��򣬲�ѯ�����б�ѡ��һ����¼�������ֶ�fieldname��ֵ
function queryDialog(url, fieldname) {
	var fieldvalue = openDialog(url, fieldname, 650, 500);
	if(fieldvalue == null) fieldvalue = '';
	return fieldvalue;
}

var changedFlag = false;

function toFirst() {
	document.all("pageNo").value = 1;
	submitForm();
}

function toPrior() {
	document.all("pageNo").value = document.all("pageNo").value * 1 - 1;
	submitForm();
}

function toNext() {
	document.all("pageNo").value = document.all("pageNo").value * 1 + 1;
	submitForm();
}

function toLast() {
	document.all("pageNo").value = document.all("pageCount").value;
	submitForm();
}

function toPage(pageNo) {
	document.all("pageNo").value = pageNo;
	submitForm();
}

function changeQuery() {
	changedFlag = true;
}

function submitForm() {
	var theForm = document.forms[0];
	if(changedFlag) theForm.all("pageNo").value = 1;
	// �����Ľӿ�
	// ������ڱ��ӿ�validate(theForm)������øýӿ�
	// �ýӿڷ��ز���ֵ��trueͨ����falseδͨ��
	try {
		var blResult = validate(theForm);
		if(!blResult) return;
	} catch (e) {}
	
	theForm.submit();
}
/**************************************************************************************

								 RECORD DATA OPERATION
  
**************************************************************************************/
function doAdd(unitCh, unitEn) {
	openWin(unitEn + 'Add.whale', 'PopWin', 600, 400);
}

function doDetail(unitCh, unitEn) {
	if(getSelectedItem() == "") {
		alert("��ѡȡһ��" + unitCh + "��¼Ȼ��ִ�б�������");
		return;
	}
	
	openWin(unitEn + 'Detail.whale?' + unitEn + '.id=' + getSelectedItem().id.substr(4), 'PopWin', 600, 400);
}

function doView(unitCh, unitEn) {
	if(getSelectedItem() == "") {
		alert("��ѡȡһ��" + unitCh + "��¼Ȼ��ִ�б�������");
		return;
	}
	
	openWin(unitEn + 'View.whale?' + unitEn + '.id=' + getSelectedItem().id.substr(4), 'PopWin', 600, 400);
}

function doUpdate(unitCh, unitEn) {
	if(getSelectedItem() == "") {
		alert("��ѡȡһ��" + unitCh + "��¼Ȼ��ִ�б�������");
		return;
	}

	openWin(unitEn + 'Update.whale?' + unitEn + '.id=' + getSelectedItem().id.substr(4), 'PopWin', 600, 400);
}

function doDelete(unitCh, unitEn) {
	if(getSelectedItem() == "") {
		alert("��ѡȡһ��" + unitCh + "��¼Ȼ��ִ��ɾ����");
		return;
	}
	
	if(confirm("��ȷ��ɾ����" + unitCh + "��¼��")) {
		openWin(unitEn + 'Delete.whale?' + unitEn + '.id=' + getSelectedItem().id.substr(4), 'PopWin', 600, 400);
	}
}

/**************************************************************************************

								   TABLE LINE SELECT
  
**************************************************************************************/
var old_item = "";
var old_color = "";
var old_background = "#FFFFFF";

function changeItem() {
	
	var ele = event.srcElement.parentElement;

	// recover the style of old item
	if( old_item != "") {
		old_item.style.background = old_background;
		old_item.style.color = old_color;
	}

	// deal with the new item
	if(ele != null && trim(ele.id) != "") {
		old_color = ele.style.color;
		if( old_color == "" ) old_color = "#000000";
		ele.style.color = "#FFFFFF";
		old_background = ele.style.background;
		if( old_background == "" ) old_background = "#FFFFFF";
		ele.style.background = "#00008B";
	}

	old_item = ele;
	
}

function getSelectedItem() {
	return (old_item == "")?"":trim(old_item.id);
}

/**************************************************************************************

								   ELEMENT OPERATION
  
**************************************************************************************/
function openModalDialog(strURL,args,nwidth,nheight){
	var vReturnValue=showModalDialog("/mrm/openIndex.jsp?actn="+strURL, args, "dialogWidth:"+nwidth+"px;dialogHeight:"+nheight+"px;status:false;center:true;help:no; scrolling=yes;resizable:no;status:no;");
	return vReturnValue;
}

function openWin(strUrl, WinName, nWidth, nHeight){

	if (screen.Width <= nWidth)
		LeftP = 1;
	else
		LeftP = (screen.Width - nWidth)/2-18;
 
	TopP = (screen.Height - nHeight)/2-18;

	var feature = "height=" + nHeight + "px,width=" + nWidth + "px,top=" +
		TopP + "px,left=" + LeftP +
		"px,scrollbars=yes,toolbar=no,menubar=no,directories=no,location=no,resizable=no,status=yes";
	
	return window.open(strUrl, WinName, feature);
}

function getRadio(eleName) {
	var arrEle = document.all(eleName);
	if(arrEle == null) { // no item list
		return null;
	}
	
	if(arrEle.length == null || arrEle.length == 1) {
		if(arrEle.checked) {
			return arrEle.value;
		}
	} else {		
		for(var nIndex = 0; nIndex < arrEle.length; nIndex++) {
			if(arrEle[nIndex].checked) {
				return arrEle[nIndex].value;
			}
		}
	}
	
	return null;
}

function clearOption(selectEle) {
	while(selectEle.options.length > 0) selectEle.options.remove(0);
}

function addOption(selectEle, optValue, optText) {	
	if(optValue == null || optValue == "") {
		alert("�����ÿ�ֵ�����µ�ѡ�");
	} else {
		// check whether the item exist
		for(var i = 0; selectEle.length > 0 && i < selectEle.length; i++) {
			if(selectEle.options[i].value == optValue) {
				// alert("�����ѡ���Ѿ����ڣ�");
				return;
			}
		}
		// create option
		var opt = new Option();
		opt.value = optValue;
		opt.text = optText;
		
		selectEle.options.add(opt);
		// selectEle.options[selectEle.length - 1].selected = true;
	}
}

function selectOption(selectEle, optValue) {
	for(var i = 0; selectEle.length > 0 && i < selectEle.length; i++) {
		if(selectEle.options[i].value == optValue) {
			selectEle.selectedIndex = i;
			break;
		}
	}
}
/**************************************************************************************

								   VARIABLE PROCESS
  
**************************************************************************************/
function trim(strValue) {	
	return rightTrim(leftTrim(strValue));
}

function leftTrim(strValue) {
	if(strValue == null) return "";
	
	return strValue.replace(/^\s+/, "");
}

function rightTrim(strValue) {
	if(strValue == null) return "";
	
	return strValue.replace(/\s+$/, "");
}

/**************************************************************************************

								   ELEMENT VALUE CHECK
  
**************************************************************************************/
function checkPositiveInteger(ele) {
	var blResult = isPositiveInteger(ele.value);
	if(!blResult) {
		alert('��������������');
		ele.focus();
	}
	return blResult;
}

function isPositiveInteger(eleValue) {
	return !(eleValue == null || isNaN(eleValue) || eleValue.indexOf('.') >= 0 || eleValue <= 0);
}

function checkPositive(ele) {
	var blResult = isPositive(ele.value);
	if(!blResult) {
		alert('������������');
		ele.focus();
	}
	return blResult;
}

function isPositive(eleValue) {
	return !(eleValue == null || isNaN(eleValue) || eleValue <= 0);
}
// ��������
function roundMoney(fValue) {
	return Math.round(fValue * 100)/100;
}
// �����ַ� #.##��ʽ
function formatMoney(fValue) {
	var str = fValue + '';
	if(str.indexOf(".") < 0) str += ".00";
	else if(str.indexOf(".") == str.length - 2) str += "0";
	return str;
}
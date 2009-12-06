
var orderByType = "ASC";
var tableTosort, totalRows;

function getDataType(tableToSort, colIndex) {
	var tdValue;
	for (var i = 1; i < totalRows; i ++) {

		
		if (tableToSort.rows(i).cells(colIndex).children(0) != null) //含有元素
		{
			if(tableToSort.rows(i).cells(colIndex).children(0).tagName == "INPUT") {
				tdValue = tableToSort.rows(i).cells(colIndex).children(0).value;
			} else if(tableToSort.rows(i).cells(colIndex).children(0).tagName == "SELECT") {
				tdValue = tableToSort.rows(i).cells(colIndex).children(0).value;
				
			}
		} else {
			tdValue = tableToSort.rows(i).cells(colIndex).innerText;
		}
		
		if (isNaN(tdValue)) {
			return "string";
		} 
			
		
	}
	return "number"
}

function sortTable(colIndex) {
	
	var m, currTDValue, nextTDValue;
	var dataType = getDataType(tableToSort, colIndex);
	var headEventObject=event.srcElement;//鼠标点击对象
	while(headEventObject.tagName!="TR"){
		headEventObject=headEventObject.parentElement;//鼠标事件所在行
		
	}
	var eventTD = headEventObject.cells(colIndex);
	for (i=0;i<headEventObject.cells.length;i++){//表头所有列

		if (headEventObject.cells[i] != eventTD){
			headEventObject.cells[i].className ='listTableHead';//表头其他列的样式（除了点击的列）
		}else{
			eventTD.className='listHeadClicked0';
		}
	}

	setSortInfo();
	//选择排序
	
	for (var i = 1; i < totalRows; i ++) {
		m = i;
		
		for (var j = i + 1; j < totalRows ; j ++) {
			
			
			if (tableToSort.rows(m).cells(colIndex).children(0) != null) {
				if(tableToSort.rows(m).cells(colIndex).children(0).tagName == "A" ) {
					
					currTDValue = tableToSort.rows(m).cells(colIndex).innerText;
					nextTDValue = tableToSort.rows(j).cells(colIndex).innerText;
				}
				else if(tableToSort.rows(m).cells(colIndex).children(0).tagName == "INPUT" || tableToSort.rows(m).cells(colIndex).children(0).tagName == "SELECT") {
					currTDValue = tableToSort.rows(m).cells(colIndex).children(0).value;
					nextTDValue = tableToSort.rows(j).cells(colIndex).children(0).value;
				} 
			}
			else
			{
				currTDValue = tableToSort.rows(m).cells(colIndex).innerText;
				nextTDValue = tableToSort.rows(j).cells(colIndex).innerText;
			}
			
			
			if (orderByType == "ASC") { //顺序
				if (compareValue(currTDValue, nextTDValue, dataType)) { 
					m = j;
				}
			} else { //倒序
				if (compareValue(currTDValue, nextTDValue, dataType) == false) { 
					m = j;
				}
			}
		}
		
		tableToSort.moveRow(m, 1);
		
	}
}

 function compareValue(current, next, dateType) {
	 
	if (dateType == "string") {
		
		return (next > current)? true : false;
	} else if (dateType == "number") {
		
		return (next - current) > 0 ? true : false;
	}
 }

 function setSortInfo() {
	var tdObj = event.srcElement;
	while(tdObj.tagName != "TD") {
		tdObj = event.srcElement.parentElement;
	}
	
	resetTableHead(tableToSort);
	if(orderByType == "ASC") {
		orderByType = "DESC"
		tdObj.innerHTML = tdObj.innerText + "&nbsp;<img src='../images/desc.jpg'>";
		
	} else {
		orderByType = "ASC"
		
		tdObj.innerHTML = tdObj.innerText + "&nbsp;<img src='../images/asc.jpg'>";
		
		
	}
	
 }


 function resetTableHead(tableToSort) {

	for (var i = 0; i < tableToSort.rows(0).cells.length; i ++) {
		
		tableToSort.rows(0).cells(i).innerHTML = tableToSort.rows(0).cells(i).innerText;
	}
 }

 function loadSort(mytable) {
	 
	tableToSort = mytable;
	totalRows = tableToSort.rows.length;
	for(var i = 0; i < tableToSort.rows(0).cells.length; i ++) {
	
		if(tableToSort.rows(0).cells(i).getAttribute("sort") == "false") {
			
		}else{
			tableToSort.rows(0).cells(i).onclick = new Function("sortTable(" + i + ")");
		}
	}
 }
function loadSort2(mytable, rows) {
	 
	tableToSort = mytable;
	totalRows = rows;
	for(var i = 0; i < tableToSort.rows(0).cells.length; i ++) {
	
		if(tableToSort.rows(0).cells(i).getAttribute("sort") == "false") {
			
		}else{
			tableToSort.rows(0).cells(i).onclick = new Function("sortTable(" + i + ")");
		}
	}
 }
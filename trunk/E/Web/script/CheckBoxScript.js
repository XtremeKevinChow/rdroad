function SelectAll( chkVal, idVal, chkAllID, chkDeleteID ) 
{ 
	var thisfrm = document.forms[0];
	var deselectAll = false;
	var chkAllNum;
	var deselectAllNum;
	var flag = false;

	for ( i = 0; i < thisfrm.length; i++ ) 
	{
		if ( thisfrm.elements[i].id.indexOf( chkAllID ) != -1 )
			chkAllNum = i;
			
		if ( thisfrm.elements[i].id.indexOf( 'deselectAll' ) != -1 )
			deselectAllNum = i;

		if ( idVal.indexOf( chkAllID ) != -1 ) 
		{
			if( chkVal == true )
			{
				if ( ( thisfrm.elements[i].disabled == false ) && ( thisfrm.elements[i].id.indexOf( chkDeleteID ) != -1 ) )
					thisfrm.elements[i].checked = true;
			}
			else
			{
				if ( ( thisfrm.elements[i].disabled == false ) && ( thisfrm.elements[i].id.indexOf( chkDeleteID ) != -1 ) )
					thisfrm.elements[i].checked = false;		
				deselectAll = true;
			}
		}
		else if ( idVal.indexOf( chkDeleteID ) != -1 )
		{
			if ( ( thisfrm.elements[i].checked == false ) && ( thisfrm.elements[i].id.indexOf( chkDeleteID ) != -1 ) )
				flag = true;
		}
	} // end for
	if ( idVal.indexOf( chkAllID ) == -1 )
	{
		if ( flag == true )
		{
			thisfrm.elements[chkAllNum].checked = false;
		}
		else
		{
			thisfrm.elements[chkAllNum].checked = true;
		}
	}

} // end function SelectAll

function ConfirmDeletePaging ( checkedNum, chkDeleteID )
{
	var thisfrm = document.forms[0];
	
	for (i=0; i<thisfrm.length; i++) 
	{
		if (thisfrm.elements[i].name.indexOf(chkDeleteID) !=-1) 
		{
			if(thisfrm.elements[i].checked) 
			{
				return confirm ('确定要做该操作吗？')
			}
		}
	}
	
	if ( checkedNum <= 0 )
	{
		alert( "请选择记录。" );
		return false;
	}
	
	return confirm ('确定要做该操作吗?');
} // end of ConfirmDelete

function ConfirmDelete( chkDeleteID )
{
	var thisfrm = document.forms[0];
	
	for (i=0; i<thisfrm.length; i++) 
	{
		if (thisfrm.elements[i].name.indexOf(chkDeleteID) !=-1) 
		{
			if(thisfrm.elements[i].checked) 
			{
				return confirm ('确定要做该操作吗?')
			}
		}
	}
	
	alert( "请选择记录。" );
	return false;
	
} // end of ConfirmDelete



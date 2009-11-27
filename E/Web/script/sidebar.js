function CreateQuickLink(funcId,elementId)
{
    $.ajax({
        async: true,
        success: function(html) {
            $(elementId).html(html);
        },
        error: function(data, msg, e) {
            //alert(e + " " + msg + " " + data);
            // do nothing
        },
        data: {type:"1002",funcId:funcId,action:"QuickLink"},
        dataType: "html",
        timeout: 5000,
        type: "POST",
        url: "ajax.ashx"
    }); 
}

function QueryUserMsg(containerId)
{
   $.ajax({
        async: true,
        success: function(data) {
            if(data._error) 
            {
               // alert(data._error_msg);
             }
            else 
            {
                $(containerId).html(data.html);
            }
            
        },
        error: function(data, msg, e) {
            alert(e + " " + msg + " " + data);
            // do nothing
        },
        data: {action:"GetUnreadMsgByUser"},
        dataType: "json",
        timeout: 5000,
        type: "POST",
        url: "ajax.ashx"
    });
}

function DeleteMsg(receiverId)
{    
    $.ajax({
        async: true,
        success: function(data) {
            if(data._error) 
            {
               // alert(data._error_msg);
             }
            else 
            {
               // $(containerId).html(data.html);
              QueryMessage();
            }
            
        },
        error: function(data, msg, e) {
            //alert(e + " " + msg + " " + data);
            // do nothing
        },
        data: {action:"DeleteReceivedMsg",receiveid:receiverId},
        dataType: "json",
        timeout: 5000,
        type: "POST",
        url: "ajax.ashx"
    });
}
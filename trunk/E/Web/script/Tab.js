//控件Tab,由主体Body和用于导航的标签Label组成。
//使用该JS之前需要引用 jquery.js

//标签的数组Labels:
// var labelArray = [{index:1,text:"基本信息",body:"basic"},{index:2,text:"绑定列表",body:"index"},{index:3,text:"专题制作",body:"page"}];
//其中，index:标签Label的序号，从1开始,
//            text:标签Label显示的文本,
//           body:标签对应Tab的主体body 元素的ID

//要显示或隐藏的DIV的Class Name 必须有 TabDiv
//tab控件的标签容器元素的ID
//objInstanceID:Tab控件的ID,页面级的变量ID；labelArray:导航标签的数组

function Tab(strInstanceID, strLabelContainerID, labelArray)
{
    this.labels = labelArray;
    this.count = labelArray.length;
    this.labelContainer = document.getElementById(strLabelContainerID);
    this.html = "";
    this.isRedirect = false;
    this.selectedIndex = -1;
   this.selectedBodyID = ""; 
   this.InstanceID = strInstanceID; 
   
   //初始化函数
    this.initial = function(index)
    {
        var me = this;
      
       me.selectedIndex = index;
       
        for(var i=0;i<me.count;i++)
        {
            me.html += "<li class='nTab' id="+ GetChildLabelNodeID(me.InstanceID,i) +" onclick=\""+me.InstanceID+".doClick(this,'"+me.labels[i].body+"');return false\">"+me.labels[i].text+"</li>";
        }
        me.labelContainer.html(me.html);
        $("#"+GetChildLabelNodeID(me.InstanceID,index))[0].className = "activedTab";
        if(!me.isRedirect)
        {
            me.hideAll();
           $("#"+me.labels[index].body).show();           
         }
        
        this.selectedBodyID = me.labels[index].body;   
    }
    
    //隐藏所有Body
    this.hideAll = function()
    {
       //$("div.TabDiv").hide();       
       for(i=0;i<this.count;i++)
       {
            $("#"+this.labels[i].body).hide();
       } 
    }    
  
    //单击Label   
    this.Click = function(labelNode,showBodyID)
    {
        if(labelNode.className=="activedTab")
        return;
        
        var me = this;
       me.selectedBodyID = showBodyID;
       me.selectedIndex = me.Index(showBodyID);
       
        me.hideAll();
        $("#"+showBodyID).show();
        me.labelContainer.children().each(
            function()
            {   
                if(this.className=="activedTab")
                {
                    this.className="nTab";
                }
            }
        );
        labelNode.className ="activedTab";
     }
     
     //查看当前显示Body对应的标签Label的顺序
     this.Index = function(showBodyID)
     {
        var index = -1;
        var me = this;
        for(var i=0;i<me.labels.length;i++)
        {
            if(me.labels[i].body == showBodyID)
            {
                index = i;
                break; 
            }
        }
        return index;
     }
   //手动选择显示某个tab控件的Body
    this.Select = function(showBodyID)
   {
        this.doClick(document.getElementById(GetChildLabelNodeID(this.InstanceID,this.Index(showBodyID))),showBodyID);
   }   
}

Tab.prototype.doClick = function (instance,showBodyID)
{
    this.Click(instance,showBodyID);
}
//获取url中的querystring
function GetQueryString(url,key)
{
   var reg = new RegExp(".*?"+ key+"=([^&]*)?&.*?$|.*?"+key+"=([^&]*)?$|","i");
   if(reg.exec(url) == ",,") return ""; 
   return url.replace(reg,"$1$2");
}
//获取tab控件的子tag标签
function GetChildLabelNodeID(objInstanceID,labelIndex)
{
    return objInstanceID + "_Node_" + labelIndex;
}

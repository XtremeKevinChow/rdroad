//�ؼ�Tab,������Body�����ڵ����ı�ǩLabel��ɡ�
//ʹ�ø�JS֮ǰ��Ҫ���� jquery.js

//��ǩ������Labels:
// var labelArray = [{index:1,text:"������Ϣ",body:"basic"},{index:2,text:"���б�",body:"index"},{index:3,text:"ר������",body:"page"}];
//���У�index:��ǩLabel����ţ���1��ʼ,
//            text:��ǩLabel��ʾ���ı�,
//           body:��ǩ��ӦTab������body Ԫ�ص�ID

//Ҫ��ʾ�����ص�DIV��Class Name ������ TabDiv
//tab�ؼ��ı�ǩ����Ԫ�ص�ID
//objInstanceID:Tab�ؼ���ID,ҳ�漶�ı���ID��labelArray:������ǩ������

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
   
   //��ʼ������
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
    
    //��������Body
    this.hideAll = function()
    {
       //$("div.TabDiv").hide();       
       for(i=0;i<this.count;i++)
       {
            $("#"+this.labels[i].body).hide();
       } 
    }    
  
    //����Label   
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
     
     //�鿴��ǰ��ʾBody��Ӧ�ı�ǩLabel��˳��
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
   //�ֶ�ѡ����ʾĳ��tab�ؼ���Body
    this.Select = function(showBodyID)
   {
        this.doClick(document.getElementById(GetChildLabelNodeID(this.InstanceID,this.Index(showBodyID))),showBodyID);
   }   
}

Tab.prototype.doClick = function (instance,showBodyID)
{
    this.Click(instance,showBodyID);
}
//��ȡurl�е�querystring
function GetQueryString(url,key)
{
   var reg = new RegExp(".*?"+ key+"=([^&]*)?&.*?$|.*?"+key+"=([^&]*)?$|","i");
   if(reg.exec(url) == ",,") return ""; 
   return url.replace(reg,"$1$2");
}
//��ȡtab�ؼ�����tag��ǩ
function GetChildLabelNodeID(objInstanceID,labelIndex)
{
    return objInstanceID + "_Node_" + labelIndex;
}

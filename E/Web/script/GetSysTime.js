function setSysTime(elementId)
{
    window.setInterval("document.getElementById('"+ elementId+"').innerHTML=getsystime()",1); 
}

function getsystime()
{
    var date = new Date();
    //return date.getFullYear() + "-" +(parseInt(date.getMonth(),10)+1) + "-" + date.getDate() + "<br/>" + date.getDay() + "<br/>"+ date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
    return  date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds();
}
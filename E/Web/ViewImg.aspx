<%@ Page language="c#" ClassName="ViewImg" %>
<%@ Import Namespace="System.Drawing" %>
<%@ Import Namespace="System.Drawing.Imaging" %>
<%@ Import Namespace="System.IO" %>

<script runat="server" language="c#">
    private void Page_Load(object sender, EventArgs e)
    {
        CreateValidateGraphic();
    }

    private void CreateValidateGraphic()
    {
        string result = "";
        result = CreateValidateNumber(4);

        Session["ValidateCode"] = result;
        string strNum = result;
        string strFontName;
        int iFontSize;
        int iWidth;
        int iHeight;
        strFontName = "Arial";
        iFontSize = 9;
        iWidth = 10 * strNum.Length;
        iHeight = 18;

        //图片的颜色

        Color bgColor = Color.LightGray; //Color.FromArgb(eeeeee);
        Color foreColor = Color.Red;

        Font foreFont = new Font(strFontName, iFontSize, FontStyle.Bold);

        Bitmap Pic = new Bitmap(iWidth, iHeight, PixelFormat.Format32bppArgb);

        Graphics g = Graphics.FromImage(Pic);
        Rectangle r = new Rectangle(0, 0, iWidth, iHeight);

        g.FillRectangle(new SolidBrush(bgColor), r);

        g.DrawString(strNum, foreFont, new SolidBrush(foreColor), 2, 2);
        MemoryStream mStream = new MemoryStream();
        Pic.Save(mStream, ImageFormat.Gif);
        g.Dispose();
        Pic.Dispose();

        Response.ClearContent();
        Response.ContentType = "image/GIF";
        Response.BinaryWrite(mStream.ToArray());
        Response.End();

    }
    private string CreateRandomCode(int codeCount)
    {
        string allChar = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,W,X,Y,Z";
        string[] allCharArray = allChar.Split(',');
        string randomCode = "";
        int temp = -1;

        Random rand = new Random();
        for (int i = 0; i < codeCount; i++)
        {
            if (temp != -1)
            {
                rand = new Random(i * temp * ((int)DateTime.Now.Ticks));
            }
            int t = rand.Next(35);
            if (temp == t)
            {
                return CreateRandomCode(codeCount);
            }
            temp = t;
            randomCode += allCharArray[t];
        }
        return randomCode;
    }

    private string CreateValidateNumber(int length)
    {
        int[] randMembers = new int[length];
        int[] validateNums = new int[length];
        string validateNumberStr = "";
        //生成起始序列值
        int seekSeek = unchecked((int)DateTime.Now.Ticks);
        Random seekRand = new Random(seekSeek);
        int beginSeek = (int)seekRand.Next(0, Int32.MaxValue - length * 10000);
        int[] seeks = new int[length];
        for (int i = 0; i < length; i++)
        {
            beginSeek += 10000;
            seeks[i] = beginSeek;
        }
        //生成随机数字
        for (int i = 0; i < length; i++)
        {
            Random rand = new Random(seeks[i]);
            int pownum = 1 * (int)Math.Pow(10, length);
            randMembers[i] = rand.Next(pownum, Int32.MaxValue);
        }
        //抽取随机数字
        for (int i = 0; i < length; i++)
        {
            string numStr = randMembers[i].ToString();
            int numLength = numStr.Length;
            Random rand = new Random();
            int numPosition = rand.Next(0, numLength - 1);
            validateNums[i] = Int32.Parse(numStr.Substring(numPosition, 1));
        }
        //生成验证码
        for (int i = 0; i < length; i++)
        {
            validateNumberStr += validateNums[i].ToString();
        }
        return validateNumberStr;
    }		
 
    </script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
	</head>
	<body style="margin-bottom:0; margin-left:0; margin-right:0; margin-top:0;">
		<form id="Form1" method="post" runat="server">
			<asp:Image id="Image1" runat="server"　Width="48px" Height="24px"></asp:Image>
		</form>
	</body>
</html>

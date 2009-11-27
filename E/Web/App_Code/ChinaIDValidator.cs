using System;

/// <summary>
/// Summary description for ChinaIDValidator
/// </summary>
public class ChinaIDValidator
{
    public ChinaIDValidator()
    {
        
    }
    private static string[] aCity = new string[] 
        { null, null, null, null, null, null, null, null, null, null, null,
            "北京", "天津", "河北", "山西", "内蒙古", null,
            null, null, null, null, "辽宁", "吉林", "黑龙江", null, 
            null, null, null, null, null, null, "上海", "江苏", "浙江", "安微", 
            "福建", "江西", "山东", null, null, null, "河南", "湖北", "湖南", 
            "广东", "广西", "海南", null, null, null, "重庆", "四川", "贵州", "云南", "西藏", 
            null, null, null, null, null, null, "陕西", "甘肃", "青海", "宁夏", "新疆", null, null, null, null, null, 
            "台湾", null, null, null, null, null, null, null, null, null, "香港", "澳门", null, null, null, null, null, null, null, null, "国外" };

    /// <summary>
    /// 验证18位身份证
    /// </summary>
    /// <param name="cid"></param>
    /// <param name="province"></param>
    /// <param name="birthday"></param>
    /// <param name="gender"></param>
    /// <param name="errorMsg"></param>
    /// <returns></returns>
    public static bool CheckFor18(string cid,out string province, out DateTime birthday,bool validateIDNoBits, out bool gender, out string errorMsg)
    {
        province = string.Empty;
        birthday = DateTime.MinValue;
        gender = false;
        errorMsg = string.Empty;
        
        double iSum = 0;
        string info = "";
        System.Text.RegularExpressions.Regex rg = new System.Text.RegularExpressions.Regex(@"^\d{17}(\d|x)$");
        System.Text.RegularExpressions.Match mc = rg.Match(cid);
        if (!mc.Success)
        {
            errorMsg = "验证18位身份证";
            return false;
        }

        cid = cid.Replace("x", "a");
        province = aCity[int.Parse(cid.Substring(0, 2))] ;
        if (province== null)
        {
            errorMsg =  "非法地区";
            return false;
        }
        try
        {
           birthday = DateTime.Parse(cid.Substring(6, 4) + "-" + cid.Substring(10, 2) + "-" + cid.Substring(12, 2));
        }
        catch
        {
            errorMsg = "非法生日";
            return false;   
        }
        if (validateIDNoBits)
        {
            for (int i = 17; i >= 0; i--)
            {
                iSum += (System.Math.Pow(2, i) % 11) * int.Parse(cid[17 - i].ToString(), System.Globalization.NumberStyles.HexNumber);

            }
            if (iSum % 11 != 1)
            {
                errorMsg = ("非法证号");
                return false;
            }
        }

        gender = int.Parse(cid.Substring(16, 1)) % 2 == 1 ? true : false;

        return true;
    }

    }

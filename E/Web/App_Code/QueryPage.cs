using Magic.Web.UI;


/// <summary>
/// 查询页面的基类
/// </summary>
public class QueryPage : BasePage
{
    public QueryPage():base()
    {
    }

    protected string[] PagerIDArray = null;

    protected virtual int DoQuery(int fromIndex, int toIndex,int maxdatacount)
    {
        return 0;
    }

    protected virtual void BindPager(int currentPageIndex)
    {
        if (PagerIDArray != null && PagerIDArray.Length > 0)
        {
            MagicPager pager = this.FindControl(PagerIDArray[0]) as MagicPager;

            int pageSize = 0;
            int iPageCount = 0;
            if (pager != null)
            {
                pageSize = pager.PageSize;
                iPageCount = pager.MaxPageCount;
            }

            if (iPageCount < 0)
                iPageCount = 20;

            int maxdatacount = iPageCount * pageSize;

            int fromIndex = (currentPageIndex - 1) * pageSize;
            int toIndex = pageSize;//(currentPageIndex * pageSize);

            int datacount = DoQuery(fromIndex, pageSize, maxdatacount);

            maxdatacount = (maxdatacount > datacount) ? datacount : maxdatacount;

            foreach (string pagerId in PagerIDArray)
            {
                pager = this.FindControl(pagerId) as MagicPager;
                WebUtil.SetMagicPager(pager, pageSize, maxdatacount, currentPageIndex);
            }
        }
        else
        {
            DoQuery(-1, -1, -1);
        }

    }
}

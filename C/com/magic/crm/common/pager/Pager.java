/*
 * Created on 2006-9-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.magic.crm.common.pager;

/**
 * @author user
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Pager {

    /** 当前记录位置 **/
    private int offset;

    /** 记录总数 **/
    private int size;

    /** 每页记录数 **/
    private int length = Constants.PAGE_SIZE;

    public Pager() {
        
    }
    public Pager(int offset, int size) {
        this.offset = offset;
        this.size = size;

    }
    
    public Pager(int offset, int size, int length) {
        this(offset, size);
        this.length = length;
    }

    /**
     * 返回分页导航条
     * 
     * @param offset
     *            int 起始记录的位置
     * @param size
     *            int 总记录数
     * @param length
     *            int 步长
     * @param url
     *            String .do的url
     * @param pageHeader
     *            String 导航条的前缀文字提示
     * @return String
     */
    public String getPageNavigation() {
        String pageNavigation = null; //最终返回的分页导航条
        //记录数超过一页,需要分页
        if (size > length) {
            pageNavigation = "共" + size + "条记录 分"+ pageCount() + "显示     ";
            //如果导航条包含header
            
            //如果不是第一页,导航条将包含“<<”(第一页)和“<”(前一页)
            if (offset > 0) {
                pageNavigation += "<a href=javascript:goFirst() style=\"font-family:Webdings; font-size: 12pt\">9</a>\n" 
                    + "<a href=javascript:goPrior('"+(offset-length)+"') style=\"font-family:Webdings; font-size: 12pt\">7</a>\n";
            } else {
            	pageNavigation += "<a style=\"font-family:Webdings; color: #000000; font-size: 12pt\">9</a>\n" 
                    + "<a style=\"font-family:Webdings; color: #000000; font-size: 12pt\">7</a>\n";
            }
            //导航条中,排头的那一页的offset值
            int startOffset;
            //位于导航条中间的那一页的offset值(半径)
            int radius = Constants.MAX_PAGE_INDEX / 2 * length;
            //如果当前的offset值小于半径
            if (offset < radius || this.pageCount() <= Constants.MAX_PAGE_INDEX) {
                //那么第一页排头
                startOffset = 0;
            } else if (offset < size - radius) {
                startOffset = offset - radius;
            } else {
                startOffset = (pageCount() - Constants.MAX_PAGE_INDEX)
                        * length;
            }
            for (int i = startOffset; i < size
                    && i < startOffset + Constants.MAX_PAGE_INDEX * length; i += length) {
                if (i == offset) {
                    //当前页号,加粗显示
                    pageNavigation += "<b>" + (i / length + 1) + "</b>\n";
                } else {
                    //其他页号,包含超链接
                    pageNavigation += "<a href=javascript:goPage('"+i+"');>" + (i / length + 1) + "</a>\n";
                }
            }
            //如果不是最后一页,导航条将包含“>”(下一页)和“>>”(最后一页)
            if (offset < size - length) {
                pageNavigation += "<a href=javascript:goNext('"+(offset+length)+"') style=\"font-family:Webdings; font-size: 12pt\">8</a>\n" 
                + "<a href=javascript:goLast('"+lastPageOffset()+"') style=\"font-family:Webdings; font-size: 12pt\">:</a>\n";
            } else {
            	pageNavigation += "<a style=\"font-family:Webdings; color: #000000; font-size: 12pt\">8</a>\n" 
                + "<a style=\"font-family:Webdings; color: #000000; font-size: 12pt\">:</a>\n";
            }

            return pageNavigation;
        }
        //记录不超过一页,不需要分页
        else {

            return "";
        }
    }

    /**
     * 返回分页后的总页数
     * 
     * @param size
     *            int 总记录数
     * @param length
     *            int 每页的记录数
     * @return int
     */
    public int pageCount() {
        int pagecount = 0;
        if (size % length == 0) {
            pagecount = size / length;
        } else {
            pagecount = size / length + 1;
        }
        return pagecount;
    }

    /**
     * 返回最后一页的记录数
     * 
     * @param size
     *            int 总记录数
     * @param length
     *            int 每页的记录数
     * @return int
     */
    public int lastPageSize() {
        int lastpagesize = 0;
        if (size % length == 0) {
            lastpagesize = length;
        } else {
            lastpagesize = size % length;
        }
        return lastpagesize;
    }

    /**
     * 返回最后一页的起始记录位置
     * 
     * @param size
     *            int 总记录数
     * @param length
     *            int 每页的记录数
     * @return int
     */
    public int lastPageOffset() {
        return size - lastPageSize();
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

}

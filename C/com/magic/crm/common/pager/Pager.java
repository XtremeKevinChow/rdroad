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

    /** ��ǰ��¼λ�� **/
    private int offset;

    /** ��¼���� **/
    private int size;

    /** ÿҳ��¼�� **/
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
     * ���ط�ҳ������
     * 
     * @param offset
     *            int ��ʼ��¼��λ��
     * @param size
     *            int �ܼ�¼��
     * @param length
     *            int ����
     * @param url
     *            String .do��url
     * @param pageHeader
     *            String ��������ǰ׺������ʾ
     * @return String
     */
    public String getPageNavigation() {
        String pageNavigation = null; //���շ��صķ�ҳ������
        //��¼������һҳ,��Ҫ��ҳ
        if (size > length) {
            pageNavigation = "��" + size + "����¼ ��"+ pageCount() + "��ʾ     ";
            //�������������header
            
            //������ǵ�һҳ,��������������<<��(��һҳ)�͡�<��(ǰһҳ)
            if (offset > 0) {
                pageNavigation += "<a href=javascript:goFirst() style=\"font-family:Webdings; font-size: 12pt\">9</a>\n" 
                    + "<a href=javascript:goPrior('"+(offset-length)+"') style=\"font-family:Webdings; font-size: 12pt\">7</a>\n";
            } else {
            	pageNavigation += "<a style=\"font-family:Webdings; color: #000000; font-size: 12pt\">9</a>\n" 
                    + "<a style=\"font-family:Webdings; color: #000000; font-size: 12pt\">7</a>\n";
            }
            //��������,��ͷ����һҳ��offsetֵ
            int startOffset;
            //λ�ڵ������м����һҳ��offsetֵ(�뾶)
            int radius = Constants.MAX_PAGE_INDEX / 2 * length;
            //�����ǰ��offsetֵС�ڰ뾶
            if (offset < radius || this.pageCount() <= Constants.MAX_PAGE_INDEX) {
                //��ô��һҳ��ͷ
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
                    //��ǰҳ��,�Ӵ���ʾ
                    pageNavigation += "<b>" + (i / length + 1) + "</b>\n";
                } else {
                    //����ҳ��,����������
                    pageNavigation += "<a href=javascript:goPage('"+i+"');>" + (i / length + 1) + "</a>\n";
                }
            }
            //����������һҳ,��������������>��(��һҳ)�͡�>>��(���һҳ)
            if (offset < size - length) {
                pageNavigation += "<a href=javascript:goNext('"+(offset+length)+"') style=\"font-family:Webdings; font-size: 12pt\">8</a>\n" 
                + "<a href=javascript:goLast('"+lastPageOffset()+"') style=\"font-family:Webdings; font-size: 12pt\">:</a>\n";
            } else {
            	pageNavigation += "<a style=\"font-family:Webdings; color: #000000; font-size: 12pt\">8</a>\n" 
                + "<a style=\"font-family:Webdings; color: #000000; font-size: 12pt\">:</a>\n";
            }

            return pageNavigation;
        }
        //��¼������һҳ,����Ҫ��ҳ
        else {

            return "";
        }
    }

    /**
     * ���ط�ҳ�����ҳ��
     * 
     * @param size
     *            int �ܼ�¼��
     * @param length
     *            int ÿҳ�ļ�¼��
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
     * �������һҳ�ļ�¼��
     * 
     * @param size
     *            int �ܼ�¼��
     * @param length
     *            int ÿҳ�ļ�¼��
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
     * �������һҳ����ʼ��¼λ��
     * 
     * @param size
     *            int �ܼ�¼��
     * @param length
     *            int ÿҳ�ļ�¼��
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

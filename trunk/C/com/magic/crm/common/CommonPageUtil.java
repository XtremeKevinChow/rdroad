//Source file: C:\\j2sdk1.4\\lib\\CommonPageUtil.java

package com.magic.crm.common;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.HashMap;

/**
封装翻页功能的通用MODEL

@author kevin
@version v1.0
 */
public class CommonPageUtil implements Serializable
{
	/**
	 * 符合查询条件的总金额
	 */
	private float amount = 0 ;
   /**
   每页显示记录数
    */
   private int pageSize;

   /**
   当前页数
    */
   private int pageNo;

   /**
   记录总数
    */
   private int recordCount;

   private String sql;
   /**
   数据列表
    */
   private AbstractList modelList;

   /**
   记录查询条件
    */
   private HashMap condition;


   /**
   @roseuid 3EF5A10F0112
    */
   public CommonPageUtil()
   {
        this.pageSize = 20;
        this.pageNo = 0;
        this.recordCount = 0;
   }

   /**
   @return java.util.AbstractList
   @roseuid 3EF5A10F0113
    */
   public AbstractList getModelList()
   {
        return modelList;
   }

   /**
   @param modelList
   @roseuid 3EF5A10F0130
    */
   public void setModelList(java.util.AbstractList modelList)
   {
        this.modelList = modelList;
   }

   /**
   @return java.util.HashMap
   @roseuid 3EF5A10F014F
    */
   public HashMap getCondition()
   {
        return condition;
   }

   /**
   @param condition
   @roseuid 3EF5A10F016C
    */
   public void setCondition(HashMap condition)
   {
        this.condition = condition;
   }

   public float getAmount(){
   		return amount;
   }
   
   public void setAmount(float amount){
   		this.amount = amount;
   }
   /**
   @return int
   @roseuid 3EF5A10F0181
    */
   public int getPageNo()
   {
        return pageNo;
   }

   /**
   @return int
   @roseuid 3EF5A10F018A
    */
   public int getPageSize()
   {
        return pageSize;
   }

   /**
   @return int
   @roseuid 3EF5A10F0194
    */
   public int getRecordCount()
   {
        return recordCount;
   }

   /**
   @param pageNo
   @roseuid 3EF5A10F019E
    */
   public void setPageNo(int pageNo)
   {
        this.pageNo = pageNo;
   }

   /**
   @param pageSize
   @roseuid 3EF5A10F01B2
    */
   public void setPageSize(int pageSize)
   {
        this.pageSize = pageSize;
   }

   /**
   @param recordCount
   @roseuid 3EF5A10F01BC
    */
   public void setRecordCount(int recordCount)
   {
        this.recordCount = recordCount;
   }

   /**
   @param curPageNo
   @param defaultValue
   @roseuid 3EF5A10F01C7
    */
   public void setPageNo(String curPageNo, int defaultValue)
   {
        try {
            this.pageNo = Integer.parseInt(curPageNo);
        } catch (Exception e) {
            this.pageNo = defaultValue;
        }
   }

   /**
   @param pageSize
   @param defaultValue
   @roseuid 3EF5A10F01DA
    */
   public void setPageSize(String pageSize, int defaultValue)
   {
        try {
            this.pageSize = Integer.parseInt(pageSize);
        } catch ( Exception e) {
            this.pageSize = defaultValue;
        }
   }

   /**
   @param totalNum
   @param defaultValue
   @roseuid 3EF5A10F01E5
    */
   public void setRecordCount(String totalNum, int defaultValue)
   {
        try {
            this.recordCount = Integer.parseInt(totalNum);
        } catch (Exception e) {
            this.recordCount = defaultValue;
        }
   }

   /**
   每页的记录开始位置
   @return from
   @roseuid 3EF5A10F01F8
    */
   public int getFrom()
   {
        if (this.pageNo <= 0 || this.pageSize == -1)
            return 0;
        else
            return (this.pageNo - 1) * this.pageSize;
   }

   /**
   每页的记录结束位置
   @return to
   @roseuid 3EF5A10F0202
    */
   public int getTo()
   {
        if (this.pageNo <= 0 || this.pageSize == -1)
            return this.recordCount - 1;
        else
            return this.pageNo * this.pageSize - 1;
   }

   /**
   总页数
   @return totalPage
   @roseuid 3EF5A10F020C
    */
   public int getTotalPage()
   {
        int ret = 0;
        if (this.pageSize <= 0) return ret;
        ret = this.recordCount / this.pageSize;
        if (this.recordCount > ret * this.pageSize) ret ++;
        return ret;
   }
/**
 * @return Returns the sql.
 */
public String getSql() {
	return sql;
}
/**
 * @param sql The sql to set.
 */
public void setSql(String sql) {
	this.sql = sql;
}
}

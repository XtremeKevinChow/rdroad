package com.magic.crm.order.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 这个类用来保存多少元任选几件这种促销。
 * items保存指定的产品
 * money4qty保存多少元任选几件促销条数
 * @author zhuxiang
 *
 */
public class Proms2 implements Serializable {

		private long id ;
		private String name ;
		
		public long getId() {
			return id;
		}
		public String getName() {
			return name;
		}
		public void setId(long id) {
			this.id = id;
		}
		public void setName(String name) {
			this.name = name;
		}
		private ArrayList items = new ArrayList();
		
		private ArrayList money4qty = new ArrayList();
		
		public ArrayList getItems() {
			return items;
		}
		public ArrayList getMoney4qty() {
			return money4qty;
		}
		public void setItems(ArrayList items) {
			this.items = items;
		}
		public void setMoney4qty(ArrayList money4qty) {
			this.money4qty = money4qty;
		}
		
}

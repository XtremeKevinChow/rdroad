package com.magic.crm.order.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ����������������Ԫ��ѡ�������ִ�����
 * items����ָ���Ĳ�Ʒ
 * money4qty�������Ԫ��ѡ������������
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

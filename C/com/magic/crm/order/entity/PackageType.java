package com.magic.crm.order.entity;

import java.io.Serializable;

/**
 * ��װ����
 * @author Administrator
 *
 */
public class PackageType implements Serializable{

	/**
	 * ��װ����id
	 */
	private int id;
	
	/**
	 * ��װ��������
	 */
	private String name;
	
	/**
	 * ��װ��������
	 */
	private String description;
	
	/**
	 * ��װ����
	 */
	private double price;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	
}

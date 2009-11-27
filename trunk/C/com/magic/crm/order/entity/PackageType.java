package com.magic.crm.order.entity;

import java.io.Serializable;

/**
 * 包装类型
 * @author Administrator
 *
 */
public class PackageType implements Serializable{

	/**
	 * 包装类型id
	 */
	private int id;
	
	/**
	 * 包装类型名称
	 */
	private String name;
	
	/**
	 * 包装类型描述
	 */
	private String description;
	
	/**
	 * 包装费用
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

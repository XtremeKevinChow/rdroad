package com.magic.crm.schedule;

public class JobElement {
	String name;

	String description;

	String className;

	String scheduleType;

	String scheduleRule;

	int status;

	public JobElement() {
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getScheduleType() {
		return scheduleType;
	}

	public void setScheduleType(String scheduleType) {
		this.scheduleType = scheduleType;
	}

	public String getScheduleRule() {
		return scheduleRule;
	}

	public void setScheduleRule(String scheduleRule) {
		this.scheduleRule = scheduleRule;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
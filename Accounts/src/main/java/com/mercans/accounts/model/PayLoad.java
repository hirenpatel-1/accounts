package com.mercans.accounts.model;

import java.util.List;

public class PayLoad {
	
	private String employeeCode;
	private String action;
	private Data data;	
    
	private List<PayComponents> payComponents;
	
	public String getEmployeeCode() {
		return employeeCode;
	}
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public List<PayComponents> getPayComponents() {
		return payComponents;
	}
	public void setPayComponents(List<PayComponents> payComponents) {
		this.payComponents = payComponents;
	}	

	
}

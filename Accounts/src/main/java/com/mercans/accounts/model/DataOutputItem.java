package com.mercans.accounts.model;

import java.util.List;

public class DataOutputItem {
	
	private String uuid;
	private String fname;
	private List<String> errors;	
    private List<PayLoad> payload;
    
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	public List<PayLoad> getPayload() {
		return payload;
	}
	public void setPayload(List<PayLoad> payload) {
		this.payload = payload;
	}

}

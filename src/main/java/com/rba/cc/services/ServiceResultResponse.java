package com.rba.cc.services;


import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ServiceResultResponse{

	private String message;
	private boolean success;
	private Map result;
	
	
	@Override
	public String toString() {
		return "ServiceResultResponse [message=" + message + ", success=" + success + ", result=" + result + "]";
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccessFalse() {
		this.success = false;
	}
	
	public void setSuccessTrue() {
		this.success = true;
	}
	
	public Map getResult() {
		return result;
	}
	public void setResult(Map result) {
		this.result = result;
	}	
	
	
}

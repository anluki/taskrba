package com.rba.cc.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OsobaRequest {
	
	@JsonProperty(value = "id")
	private Integer id;
	
	@JsonProperty(value = "ime")
	private String ime;
	
	@JsonProperty(value = "prezime")
	private String prezime;
	
	@JsonProperty(value = "oib")
	private String oib;
	
	@JsonProperty(value = "status")
	private String status;


	@Override
	public String toString() {
		return "OsobaRequest [id=" + id + ", ime=" + ime + ", prezime=" + prezime + ", oib=" + oib + ", status="
				+ status + "]";
	}
	
	


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getOib() {
		return oib;
	}

	public void setOib(String oib) {
		this.oib = oib;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

}

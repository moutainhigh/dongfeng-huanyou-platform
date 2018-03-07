package com.navinfo.opentsp.dongfeng.rpws.webservice.dao.entity;

public class WebServiceEntity <T>{
	
	private T webService;
	
	private Long accessTocken = 0l;
	
	private String acckey;

	public T getWebService() {
		return webService;
	}

	public void setWebService(T webService) {
		this.webService = webService;
	}

	public Long getAccessTocken() {
		return accessTocken;
	}

	public void setAccessTocken(Long accessTocken) {
		this.accessTocken = accessTocken;
	}

	public String getAcckey() {
		return acckey;
	}

	public void setAcckey(String acckey) {
		this.acckey = acckey;
	}
	
}

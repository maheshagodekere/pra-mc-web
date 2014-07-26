package com.pradeya.cast.ui.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ManagedBean
@RequestScoped
public class NavBean {

	
	
	//private String activePage = "mediaListView";

	private String activePage = "memberDetail";

	public String getActivePage() {
		return activePage;
	}

	public void setActivePage(String activePage) {
		this.activePage = activePage;
	}

	public void clientDetail(){
		//logger.info("click clientDetail button");
		this.setActivePage("clientDetail");
	}	
	
	public void client(){
		//logger.info("click client button");
		this.setActivePage("client");
	}	
	
	public void member(){
		//logger.info("click member button");
		this.setActivePage("member");
	}	
	
	public void memberDetail(){
		//logger.info("click memberDetail button");
		this.setActivePage("memberDetail");
	}	
	
	
	public void goal() {
		this.setActivePage("goal");
	}
	
	public void goalDetails() {
		//logger.info("click goalDetails");
		this.setActivePage("goalDetails");
	}	
	

	
}
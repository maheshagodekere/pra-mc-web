package com.pradeya.cast.domain;

import org.primefaces.model.SelectableDataModel;

public class Option implements SelectableDataModel<Option>{
	
	private long id;
    
    private String name;
    
    public Option(long id,String name){
    	this.name = name;
    	
    }

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Option getRowData(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getRowKey(Option option) {
		// TODO Auto-generated method stub
		return option.getId();
	}

}

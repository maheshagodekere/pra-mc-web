package com.pradeya.cast.ui.bean;

import java.util.ArrayList;

import org.primefaces.event.SelectEvent;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pradeya.cast.domain.Option;
import com.pradeya.cast.domain.OptionDataModel;

@Component
@Scope("session")
public class ScheduleUIBean {
	private ArrayList<Option> options; 
	private Option selectedOption;  
	private OptionDataModel optionDataModel;  

	public ScheduleUIBean() {  
		populateOptions();  
    } 
	
	private void populateOptions(){
		options = new ArrayList<Option>();
		options.add((new Option(1L,"All day in this month")));
		options.add((new Option(2L,"All weekends in this month")));
		options.add((new Option(3L,"All weekdays in this month")));
		options.add((new Option(4L,"All day weekends")));
		options.add((new Option(5L,"All day week days")));
		
		optionDataModel = new OptionDataModel(options);  
	}
	
	public  ArrayList<Option> getOptions() {
		return options;
	}

	public  void setOptions(ArrayList<Option> options) {
		this.options = options;
	}


	public Option getSelectedOption() {
		return selectedOption;
	}


	public void setSelectedOption(Option selectedOption) {
		this.selectedOption = selectedOption;
	}

	public OptionDataModel getOptionDataModel() {  
        return optionDataModel;  
    } 
	public void onOptionSelect(SelectEvent event) {  
//        FacesMessage msg = new FacesMessage("Car Selected", ((Car) event.getObject()).getModel());  
//  
//        FacesContext.getCurrentInstance().addMessage(null, msg);  
    } 
	
}

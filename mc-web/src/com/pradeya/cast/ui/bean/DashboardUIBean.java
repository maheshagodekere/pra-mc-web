package com.pradeya.cast.ui.bean;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import org.primefaces.model.chart.DonutChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.pradeya.cast.domain.Organization;

  

@Component
@Scope("prototype")
public class DashboardUIBean implements Serializable {  

	private DonutChartModel donutModel;  
	private Organization selecetedOrg;
	public Organization getSelecetedOrg() {
		return selecetedOrg;
	}
	/*private int i=findClientCount();

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}
*/
	public void setSelecetedOrg(Organization selecetedOrg) {
		this.selecetedOrg = selecetedOrg;
	}

	public DashboardUIBean() {  
		createDonutModel();  
	}  

	public DonutChartModel getDonutModel() {  
		return donutModel;  
	}  

	private void createDonutModel() {  
		donutModel = new DonutChartModel();  

		Map<String, Number> circle1 = new LinkedHashMap<String, Number>();  
		circle1.put("Brand 1", 150);  
		circle1.put("Brand 2", 400);  
		circle1.put("Brand 3", 200);  
		circle1.put("Brand 4", 10);  
		donutModel.addCircle(circle1);  

		//        Map<String, Number> circle2 = new LinkedHashMap<String, Number>();  
		//        circle2.put("Brand 1", 540);  
		//        circle2.put("Brand 2", 125);  
		//        circle2.put("Brand 3", 702);  
		//        circle2.put("Brand 4", 421);  
		//        donutModel.addCircle(circle2);  
		//          
		//        Map<String, Number> circle3 = new LinkedHashMap<String, Number>();  
		//        circle3.put("Brand 1", 40);  
		//        circle3.put("Brand 2", 325);  
		//        circle3.put("Brand 3", 402);  
		//        circle3.put("Brand 4", 421);  
		//        donutModel.addCircle(circle3);  
	}  

}  
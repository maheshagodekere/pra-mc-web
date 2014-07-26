package com.pradeya.cast.domain;

import java.util.List;  
import javax.faces.model.ListDataModel;  
import org.primefaces.model.SelectableDataModel;  
  
public class OptionDataModel extends ListDataModel<Option> implements SelectableDataModel<Option> {    
  
    public OptionDataModel() {  
    }  
  
    public OptionDataModel(List<Option> data) {  
        super(data);  
    }  
      
    @Override  
    public Option getRowData(String rowKey) {  
        //In a real app, a more efficient way like a query by rowKey should be implemented to deal with huge data  
          
        List<Option> Options = (List<Option>) getWrappedData();  
          
        for(Option option : Options) {  
            if(option.getId() == Long.parseLong(rowKey))
                return option;  
        }  
          
        return null;  
    }  
  
    @Override  
    public Object getRowKey(Option option) {  
        return option.getId();  
    }  
}  
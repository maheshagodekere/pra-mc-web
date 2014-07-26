package com.pradeya.cast.ui.bean;

import java.io.Serializable;

import org.primefaces.model.CheckboxTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.stereotype.Component;

import com.pradeya.cast.domain.Document;
  
@Component
public class DisplayUIBean implements Serializable {  
      
    private TreeNode root;  
          
    private TreeNode[] selectedNodes;  
      
    public DisplayUIBean() {  
        root = new CheckboxTreeNode(new Document("Displays", "-", "Folder"), null);  
          
        TreeNode place1 = new CheckboxTreeNode(new Document("Bangalore", "-", "Folder"), root);  
        TreeNode place2 = new CheckboxTreeNode(new Document("Chennai", "-", "Folder"), root);  
        TreeNode place3 = new CheckboxTreeNode(new Document("Hyderabad", "-", "Folder"), root);  
          
        TreeNode subloc1 = new CheckboxTreeNode(new Document("Koramangala", "-", "Folder"), place1);  
        TreeNode subloc2 = new CheckboxTreeNode(new Document("Jayanagar", "-", "Folder"), place1);  
          
        //Documents  
        TreeNode expenses = new CheckboxTreeNode("lg-42in-display", new Document("lg-42in-display", "42 in", "LED Display"), subloc1);  
        TreeNode resume = new CheckboxTreeNode("samsung-52in-display", new Document("samsung-52in-display", "52 in", "LED display"), subloc1);  
        TreeNode refdoc = new CheckboxTreeNode("sony-imagewall-display", new Document("sony-imagewall-display", "200 in", "LED image wall"), subloc2);  
          
   }  
      
    public TreeNode getRoot() {  
        return root;  
    }  
      
    public TreeNode[] getSelectedNodes() {  
        return selectedNodes;  
    }  
  
    public void setSelectedNodes(TreeNode[] selectedNodes) {  
        this.selectedNodes = selectedNodes;  
    }  
}
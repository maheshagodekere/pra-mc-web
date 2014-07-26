package com.pradeya.cast.ui.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import com.pradeya.cast.domain.Member;
import com.pradeya.cast.domain.Organization;

import com.pradeya.cast.service.OrganizationService;
import com.pradeya.cast.util.CastUtil;

@Component
@Scope("session")
@SuppressWarnings("serial")
public class TreeBean implements Serializable {  

	private TreeNode root;  
	private TreeNode selectedNode;

	@Autowired
	OrganizationService organizationService;

	private long orgId;
	private String who;
	private String role ="ROLE_GOD";
	private String orgName;
	private ArrayList<String> members;

	//private ArrayList<String> members;

	@PostConstruct
	public void init() {
		try{
		orgId = ((Long) RequestContextHolder.currentRequestAttributes().getAttribute(CastUtil.ID, RequestAttributes.SCOPE_SESSION)).longValue();
		orgName = RequestContextHolder.currentRequestAttributes().getAttribute(CastUtil.ORG_NAME, RequestAttributes.SCOPE_SESSION).toString();
		who =  RequestContextHolder.currentRequestAttributes().getAttribute(CastUtil.WHO, RequestAttributes.SCOPE_SESSION).toString();
		role = RequestContextHolder.currentRequestAttributes().getAttribute(CastUtil.ROLE, RequestAttributes.SCOPE_SESSION).toString();
		}catch(Exception e){
			e.printStackTrace();
		}

		refreshMember();
		refreshTree();
	}  
	
	private void loadAllMember(){
		Organization organization=organizationService.findAllMember(orgId);
		Iterator<Member> it = organization.getMember().iterator();
		members = new ArrayList<String>();
		Member fp = null;
		while(it.hasNext()){
			fp = it.next();
			members.add(fp.getFirstName()+" "+fp.getLastName());
		}
	}
	
	private void loadAMember(){
		Organization organization = organizationService.findMember(orgId,who);
		Iterator<Member> it = organization.getMember().iterator();
		members = new ArrayList<String>();
		Member fp = null;
		while(it.hasNext()){
			fp = it.next();
			members.add(fp.getFirstName()+" "+fp.getLastName());
		}
	}
	


	private  void  refreshMember(){
		if(role.indexOf("ROLE_ADMIN")>-1){
			loadAllMember();
		}else if(role.indexOf("ROLE_MEMBER")>-1){
			loadAMember();
		}
	}
		
	public  void  refreshMemberOnly(){
		refreshMember();
		refreshTree();
	}

	
	private  void  refreshTree(){
		if(role.indexOf("ROLE_ADMIN")>-1){
			buildAdminTree();
		}else if(role.indexOf("ROLE_MEMBER")>-1){
			buildMemberTree();
		}		
	}
	
	private  void  buildAdminTree(){	
		root = new DefaultTreeNode("Root", null);  
		TreeNode organization = new DefaultTreeNode(new TreeObj("ORG",orgName), root);
//		TreeNode activity = new DefaultTreeNode(new TreeObj("ACTIVITY","Activity"), organization);
		TreeNode member = new DefaultTreeNode(new TreeObj("MEMBER","Members"),organization);

		for (String names : members) {
			TreeNode amem = new DefaultTreeNode(new TreeObj(names,names), member); 
		}    

		TreeNode upload = new DefaultTreeNode(new TreeObj("UPLOAD","Media Casting"),organization);
		//TreeNode media = new DefaultTreeNode(new TreeObj("MEDIA","Media"),organization);
		
		organization.setExpanded(true);
		member.setExpanded(true);
	}

	private  void  buildMemberTree(){	
		root = new DefaultTreeNode("Root", null);  
		TreeNode organization = new DefaultTreeNode(new TreeObj("ORG",orgName), root);
		TreeNode member = new DefaultTreeNode(new TreeObj("MEMBER","Member"),organization);

		for (String names : members) {
			TreeNode amem = new DefaultTreeNode(new TreeObj(names,names), member); 
		}    
		
		TreeNode upload = new DefaultTreeNode(new TreeObj("UPLOAD","Upload"),organization);
		TreeNode media = new DefaultTreeNode(new TreeObj("MEDIA","Media"),organization);
		
		organization.setExpanded(true);
		member.setExpanded(true);
	}
	


	public TreeNode getRoot() {  
		return root;
	}  

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public void onNodeSelect(NodeSelectEvent event) {
		
		event.getTreeNode().setSelected(true);		


		try {
			if(((TreeObj)event.getTreeNode().getData()).getIdentifier().equalsIgnoreCase("ORG")){
				FacesContext
				.getCurrentInstance()
				.getApplication()
				.getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(),
						"organization", "/member.xhtml");
			}
			if(((TreeObj)event.getTreeNode().getData()).getIdentifier().equalsIgnoreCase("UPLOAD")){
				//FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("orgId",orgId);

				FacesContext
				.getCurrentInstance()
				.getApplication()
				.getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(),
						"upload", "/upload.xhtml");

			}
			if(((TreeObj)event.getTreeNode().getData()).getIdentifier().equalsIgnoreCase("MEDIA")){
				//FacesContext.getCurrentInstance().getExternalContext().getRequestMap().put("orgId",orgId);
				
				FacesContext
				.getCurrentInstance()
				.getApplication()
				.getNavigationHandler()
				.handleNavigation(FacesContext.getCurrentInstance(),
						"media", "/media.xhtml");
			}			
			
		} catch (Exception e) {
			// logger.info("error "+e.getMessage());
			System.out.println("cannot load the page...."+e);
			// TODO: handle exception
		} 
	}
	public void onNodeExpand(NodeExpandEvent event) {  
		event.getTreeNode().setExpanded(true);
//
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Expanded", event.getTreeNode().toString());  
//
//		FacesContext.getCurrentInstance().addMessage(null, message);  
	}  

	public void onNodeCollapse(NodeCollapseEvent event) {  
		event.getTreeNode().setExpanded(false);

		//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Collapsed", event.getTreeNode().toString());  
//
//		FacesContext.getCurrentInstance().addMessage(null, message);  
	}  

	public void onNodeUnselect(NodeUnselectEvent event) {  
		event.getTreeNode().setSelected(false);		
	}  
	
	private void expand(TreeNode treeNode){
	    if (treeNode.getParent()!=null){
	        treeNode.getParent().setExpanded(true);
	        expand(treeNode.getParent());
	    }
	}

	public	class TreeObj implements Serializable {
		String identifier;
		String name;
		public String getIdentifier() {
			return identifier;
		}

		public void setIdentifier(String identifier) {
			this.identifier = identifier;
		}

		String fpIdentifier;
		String clientIdentifier;
		String memberIdentifier;
		String goalIdentifier;
		String profileIdentifier;
		
		public String getFpIdentifier() {
			return fpIdentifier;
		}


		public void setFpIdentifier(String fpIdentifier) {
			this.fpIdentifier = fpIdentifier;
		}


		public String getClientIdentifier() {
			return clientIdentifier;
		}


		public void setClientIdentifier(String clientIdentifier) {
			this.clientIdentifier = clientIdentifier;
		}


		public String getMemberIdentifier() {
			return memberIdentifier;
		}


		public void setMemberIdentifier(String memberIdentifier) {
			this.memberIdentifier = memberIdentifier;
		}


		public String getGoalIdentifier() {
			return goalIdentifier;
		}


		public void setGoalIdentifier(String goalIdentifier) {
			this.goalIdentifier = goalIdentifier;
		}


		public String getProfileIdentifier() {
			return profileIdentifier;
		}


		public void setProfileIdentifier(String profileIdentifier) {
			this.profileIdentifier = profileIdentifier;
		}

		public TreeObj(String identifier, String name){
			this.identifier= identifier;
			this.name = name;
		}	
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	} 

}  
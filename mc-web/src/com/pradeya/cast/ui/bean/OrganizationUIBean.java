package com.pradeya.cast.ui.bean;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.pradeya.cast.domain.Member;
import com.pradeya.cast.domain.Organization;
import com.pradeya.cast.domain.Role;
import com.pradeya.cast.service.OrganizationService;
import com.pradeya.cast.util.CastUtil;
import com.pradeya.cast.util.UserRole;

@Component
@Scope("session")
public class OrganizationUIBean implements Serializable {

	@Autowired
	OrganizationService organizationService;
	
	@Autowired
	TreeBean treeBean;

	private static final long serialVersionUID = 1L;

	private Organization selectedOrg = new Organization();

	private Member selectedMember = new Member();

	public Member getSelectedMember() {
		return selectedMember;
	}

	public void setSelectedMember(Member fp) {
		this.selectedMember = fp;
	}

	public Organization getSelectedOrganization() {
		return selectedOrg;
	}

	public void setSelectedOrganization(Organization selectedOrg) {
		this.selectedOrg = selectedOrg;
	}

	public ArrayList<Organization> getAllOrganization() {
		ArrayList<Organization> org = new ArrayList<Organization>(1);
		org.add(selectedOrg);
		return org;
	}

	@PostConstruct
	private void loadMember(){
		long sOrgId = ((Long) RequestContextHolder.currentRequestAttributes().getAttribute(CastUtil.ID, RequestAttributes.SCOPE_SESSION)).longValue();
		selectedOrg = organizationService.findAllMember(sOrgId);
	}
	
	public void refreshMember(){
		loadMember();
	}
	public ArrayList<Member> getAllMember() {
		return selectedOrg.getMember();
	}

	public String updateOrganization() {
		organizationService.update(selectedOrg);
		return "allOrganization";
	}

	public String editOrganization(Organization org) {
		return "editOrganization";
	}

	public String createOrganization() {
		selectedOrg = new Organization();
		return "createOrganization";
	}

	public String saveOrganization() {
		organizationService.create(selectedOrg);
		return "allOrganization";
	}

	public String memberList() {
		return "member";
	}

	public String createMember() {
		selectedMember = new Member();
		return "createMember";
	}

	public String saveMember() {
		selectedMember.addRole(new Role(UserRole.ROLE_ADMIN.name()));
		organizationService.createMember(selectedOrg.getId(),
				selectedMember);
		refreshMember();
		treeBean.refreshMemberOnly();
		return "member";
	}

	public String editMember(Member planner) {
		return "editMember";
	}

	public String updateMember() {
		organizationService.updateMember(selectedOrg.getId(),
				selectedMember);
		refreshMember();
		return "member";
	}
//	public int countOrg(){
//		return selectedOrg.count;
//	}

}

package com.pradeya.cast.domain;

import java.io.Serializable;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;

@SuppressWarnings("serial")
public class Organization implements Serializable {

	@Id
	private long id;
	private Detail detail=new Detail();
	private ArrayList<Member> member=new ArrayList<Member>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}


	public ArrayList<Member> getMember() {
		return member;
	}

	public void setMember(ArrayList<Member> member) {
		this.member = member;
	}

	public Detail getDetail() {
		return detail;
	}

	public void setDetail(Detail detail) {
		this.detail = detail;
	}

}

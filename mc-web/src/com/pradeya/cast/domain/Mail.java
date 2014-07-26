package com.pradeya.cast.domain;

import java.io.Serializable;
import java.util.Date;

import org.springframework.context.annotation.Scope;
import org.springframework.data.annotation.Id;
@Scope("request")
public class Mail implements Serializable {
	@Id
	private long id;
	private String to;
	private String typeOfMail;
	private String folder;
	public String from;
    public String subject;
    public String body;
    public Date date;
    private String password;
    private String title;
	
    public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getTypeOfMail() {
		return typeOfMail;
	}

	public void setTypeOfMail(String typeOfMail) {
		this.typeOfMail = typeOfMail;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	
    
    public Mail() {}

    public Mail(String from, String subject, String body, Date date) {
        this.from = from;
        this.subject = subject;
        this.body = body;
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
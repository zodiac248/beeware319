package com.panpal.Request;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.util.Date;

import com.panpal.Mail.Mail;
// import com.panpal.Building.Building;

@Entity // This tells Hibernate to make a table out of this class
public class Request {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String email;

	private String name;
	
	// @ManyToOne
	// private Building building;

	@OneToOne
	private Mail mail;

	private String phoneNumber;

	private String status;

	private String feedback;

	private String instructions;

	private String type;

	private Date submissionDate;

	private Date completionDate;

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// public Building getBuilding() {
	// 	return building;
	// }

	// public Integer getBuildingId() {
	// 	return building.getId();
	// }

	// public void setBuilding(Building building) {
	// 	this.building = building;
	// }

	public Mail getMail() {
		return mail;
	}

	public Integer getMailId() {
		return mail.getId();
	}

	public void setMail(Mail mail) {
		this.mail = mail;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date date) {
		this.submissionDate = date;
	}

	public Date getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(Date date) {
		this.completionDate = date;
	}
}

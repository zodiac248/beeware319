package com.panpal.Request;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import java.util.Date;

import com.panpal.Mail.Mail;

@Entity // This tells Hibernate to make a table out of this class
public class Request {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	// private String email;

	// @OneToOne
	// private Mail mail;

	private String instructionType;

	private String instructionDescription;

	private Date requestedCompletionDate;

	private String feedback;

	public Integer getId() {
		return id;
	}

	// public String getEmail() {
	// 	return email;
	// }

	// public void setEmail(String email) {
	// 	this.email = email;
	// }

	// public Mail getMail() {
	// 	return mail;
	// }

	// public Integer getMailId() {
	// 	return mail.getId();
	// }

	// public void setMail(Mail mail) {
	// 	this.mail = mail;
	// }

	public String getInstructionType() {
		return instructionType;
	}

	public void setInstructionType(String type) {
		this.instructionType = type;
	}

	public String getInstructionDescription() {
		return instructionDescription;
	}

	public void setInstructionDescription(String instruction) {
		this.instructionDescription = instruction;
	}

	public Date getRequestedCompletionDate() {
		return requestedCompletionDate;
	}

	public void setRequestedCompletionDate(Date date) {
		this.requestedCompletionDate = date;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
}

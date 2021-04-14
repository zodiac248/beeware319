package com.panpal.Request;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Column;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

import com.panpal.Mail.Mail;

@Entity // This tells Hibernate to make a table out of this class
@JsonIgnoreProperties(value = {"mail", "handler","hibernateLazyInitializer"}, allowSetters = true)
public class Request {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    // private String email;

    @OneToOne(fetch=FetchType.LAZY)
    private Mail mail;

    private String instructionType;

	@Column(columnDefinition = "varchar(512) default ''")
	private String instructionDescription;

    private LocalDate requestedCompletionDate;

	@Column(columnDefinition = "varchar(512) default ''")
	private String feedback;

    public Integer getId() {
        return id;
    }

    //public String getEmail() {
    //return email;
    //}

    //public void setEmail(String email) {
    //this.email = email;
    //}

    public Mail getMail() {
        return mail;
    }

    public Integer getMailId() {
        return mail.getId();
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

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

    public LocalDate getRequestedCompletionDate() {
        return requestedCompletionDate;
    }

    public void setRequestedCompletionDate(LocalDate date) {
        this.requestedCompletionDate = date;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

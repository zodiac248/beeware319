package com.panpal.Notification;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.util.Date;

import com.panpal.Posting.Posting;

@Entity // This tells Hibernate to make a table out of this class
public class Notification {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String email;

	@ManyToOne
	private Posting posting;

	private Date date;

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Posting getPosting() {
		return posting;
	}

	public Integer getPostingId() {
		return posting.getId();
	}

	public void setPosting(Posting posting) {
		this.posting = posting;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

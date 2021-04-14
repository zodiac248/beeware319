package com.panpal.Subscription;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.panpal.Topic.Topic;

@Entity // This tells Hibernate to make a table out of this class
public class Subscription {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String email;
	
	@ManyToOne
	private Topic topic;

	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Topic getTopic() {
		return topic;
	}

	public Integer getTopicId() {
		return topic.getId();
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
}

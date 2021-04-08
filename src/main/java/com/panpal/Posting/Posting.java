package com.panpal.Posting;

import javax.persistence.*;

import java.util.List;
import java.util.Date;

import com.panpal.Topic.Topic;
import com.panpal.Comment.Comment;
import com.panpal.Notification.Notification;

@Entity // This tells Hibernate to make a table out of this class
public class Posting {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String email;

	@ManyToOne
	private Topic topic;

	private Date date;

	private String title;

	private Integer likes;

	@Column(columnDefinition = "varchar(1024) default ''")
	private String content;

	@OneToMany(mappedBy="posting", cascade=CascadeType.REMOVE)
	private List<Comment> comments;

	@OneToMany(mappedBy="posting", cascade=CascadeType.REMOVE)
	private List<Notification> notifications;

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

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}

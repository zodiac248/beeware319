package com.panpal.Topic;

import javax.persistence.*;

import java.util.List;

import com.panpal.Subscription.Subscription;
import com.panpal.Posting.Posting;

@Entity // This tells Hibernate to make a table out of this class
@Table(
		name = "topic",
		uniqueConstraints = {
				@UniqueConstraint(columnNames={"name"})
		}
)
public class Topic {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(columnDefinition = "varchar(50) default ''")
	private String name;

	@OneToMany(mappedBy="topic", cascade=CascadeType.REMOVE)
	private List<Subscription> subscriptions;

	@OneToMany(mappedBy="topic", cascade=CascadeType.REMOVE)
	private List<Posting> postings;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

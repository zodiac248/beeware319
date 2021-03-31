package com.panpal.Topic;

import javax.persistence.*;

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

	private String name;

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

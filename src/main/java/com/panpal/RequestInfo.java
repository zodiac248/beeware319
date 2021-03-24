package com.panpal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class RequestInfo {

	private Integer id;

	private Integer deskId;

	private Integer floorId;

	private Integer buildingId;

	private Integer employeeId;

	private String date;

	private String name;

	private String address;

	private Integer floorNumber;

	private String deskNumbers;

	private String deskNumber;
	
	private String username;
	
	private String firstName;
	
	private String lastName;
	
	private Boolean isAdmin;

	private String email;

	private String password;

	private Integer range;

	private String imageUrl;

	private Integer topicId;

	private Integer subscriptionId;

	private Integer commentId;

	private Integer postingId;

	private String title;

	private Integer likes;

	private String content;
	
	private String code;


	public Integer getId() {
		return id;
	}

	public Integer getDeskId() {
		return deskId;
	}

	public Integer getFloorId() {
		return floorId;
	}

	public Integer getBuildingId() {
		return buildingId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public String getDate() {
		return date;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public String getDeskNumbers() {
		return deskNumbers;
	}

	public String getDeskNumber() {
		return deskNumber;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public Integer getRange() {
		return range;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Integer getTopicId() {
		return topicId;
	}

	public Integer getPostingId() {
		return postingId;
	}

	public Integer getSubscriptionId() {
		return subscriptionId;
	}
	
	public Integer getCommentId() {
		return commentId;
	}

	public String getTitle() {
		return title;
	}

	public Integer getLikes() {
		return likes;
	}

	public String getContent() {
		return content;
	}

	public String getCode() {
		return code;
	}
}

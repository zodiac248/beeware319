package com.panpal.Mail;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import java.time.LocalDateTime;

import com.panpal.Request.Request;
import com.panpal.Building.Building;

@Entity // This tells Hibernate to make a table out of this class
public class Mail {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String email;

	private String sender;

	private LocalDateTime date;

	private String status;
    
	@ManyToOne
	private Building building;

    @OneToOne(mappedBy= "mail", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private Request request;
    
	public Integer getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public Request getRequest() {
        return request;
    }

    public Integer getRequestId() {
        return request.getId();
    }

    public void setRequest(Request request) {
        this.request = request;
    }

	public Building getBuilding() {
		return building;
	}

	public Integer getBuildingId() {
		return building.getId();
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

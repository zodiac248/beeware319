package com.panpal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.util.Date;

import com.panpal.User;
import com.panpal.Desk;

@Entity
public class Booking {
	@Id

	@GeneratedValue(strategy=GenerationType.IDENTITY)
	
	private Integer id;

	@ManyToOne
	private Desk desk;

	@ManyToOne
	private User employee;

	private Date date;

	public Integer getId() {
		return id;
	}

	public Integer getDeskId() {
		return desk.getId();
	}

	public Desk getDesk() {
		return desk;
	}

	public void setDesk(Desk desk) {
		this.desk = desk;
	}

	public Integer getEmployeeId() {
		return employee.getId();
	}

	public User getEmployee() {
		return employee;
	}

	public void setEmployee(User employee) {
		this.employee = employee;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

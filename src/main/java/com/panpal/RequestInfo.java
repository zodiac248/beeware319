package com.panpal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import java.util.Date;

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
}

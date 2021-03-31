package com.panpal.Building;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

import java.util.Objects;

@Entity // This tells Hibernate to make a table out of this class
@Table(
	name = "building",
	uniqueConstraints = {
		@UniqueConstraint(columnNames={"name"}),
		@UniqueConstraint(columnNames={"address"})
	}
)
public class Building {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String name;

	private String address;

	private String code;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Building)) return false;

		Building building = (Building) o;
		return Objects.equals(name, building.name) &&
				Objects.equals(address, building.address);
	} 

	@Override
	public int hashCode() {
		return Objects.hash(name, address);
	}
}

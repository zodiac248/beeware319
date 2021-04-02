package com.panpal.Floor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import java.util.Objects;

import com.panpal.Building.Building;

@Entity
public class Floor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer floorNumber;

	private String imageUrl;

	@ManyToOne
	private Building building;

	public Integer getId() {
		return id;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Integer getBuildingId() {
		return building.getId();
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this) return true;
		if (!(o instanceof Floor)) return false;

		Floor floor = (Floor) o;
		return Objects.equals(floorNumber, floor.floorNumber) && 
				Objects.equals(imageUrl, floor.imageUrl) &&
				Objects.equals(building, floor.building);	
	}

	@Override
	public int hashCode() {
		return Objects.hash(floorNumber, imageUrl, building);
	}
}

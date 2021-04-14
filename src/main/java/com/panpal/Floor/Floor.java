package com.panpal.Floor;

import javax.persistence.*;

import java.util.List;
import java.util.Objects;

import com.panpal.Building.Building;
import com.panpal.Desk.Desk;

@Entity
@Table(
		name = "floor",
		uniqueConstraints = {
				@UniqueConstraint(columnNames={"floorNumber","building_id"})
		}
)
public class Floor {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private Integer floorNumber;

	@Lob
	@Column
	private String image;

	@ManyToOne
	private Building building;

	@OneToMany(mappedBy="floor", cascade=CascadeType.REMOVE)
	private List<Desk> desks;

	public Integer getId() {
		return id;
	}

	public Integer getFloorNumber() {
		return floorNumber;
	}

	public void setFloorNumber(Integer floorNumber) {
		this.floorNumber = floorNumber;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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
				Objects.equals(image, floor.image) &&
				Objects.equals(building, floor.building);	
	}

	@Override
	public int hashCode() {
		return Objects.hash(floorNumber, image, building);
	}
}

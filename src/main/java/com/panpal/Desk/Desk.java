package com.panpal.Desk;

import javax.persistence.*;
import java.util.List;

import com.panpal.Floor.Floor;
import com.panpal.Booking.Booking;

@Entity
@Table(
		name = "desk",
		uniqueConstraints = {
				@UniqueConstraint(columnNames={"deskNumber","floor_id"})
		}
)
public class Desk {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	private String deskNumber;

	@ManyToOne
	private Floor floor;

	@OneToMany(mappedBy="desk", cascade=CascadeType.REMOVE)
	private List<Booking> bookings;

	public Integer getId() {
		return id;
	}

	public String getDeskNumber() {
		return deskNumber;
	}

	public void setDeskNumber(String deskNumber) {
		this.deskNumber = deskNumber;
	}

	public Integer getFloorId() {
		return floor.getId();
	}

	public Floor getFloor() {
		return floor;
	}

	public void setFloor(Floor floor) {
		this.floor = floor;
	}
}

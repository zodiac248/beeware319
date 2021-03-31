package com.panpal.Booking;  

import javax.persistence.*;

import java.util.Date;

import com.panpal.Desk.Desk;

@Entity
@Table(
		name = "booking",
		uniqueConstraints = {
				@UniqueConstraint(columnNames={"desk_id","date"})
		}
)
public class Booking {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Desk desk;

	private String email;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

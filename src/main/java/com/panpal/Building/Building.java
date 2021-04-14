package com.panpal.Building;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import com.panpal.Floor.Floor;
import com.panpal.Mail.Mail;

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

	@Column(columnDefinition = "varchar(50) default ''")
	private String name;
    
	@Column(columnDefinition = "varchar(50) default ''")
	private String address;

	@Column(columnDefinition = "varchar(50) default ''")
	private String code;

	@OneToMany(mappedBy="building", cascade=CascadeType.REMOVE)
	private List<Floor> floors;

    @OneToMany(mappedBy="building", cascade=CascadeType.REMOVE)
    private List<Mail> mails;

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

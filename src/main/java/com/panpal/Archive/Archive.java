package com.panpal.Archive;

import javax.persistence.*;
import java.util.Date;
import com.panpal.Desk.Desk;

@Entity
public class Archive {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	private Desk desk;

	private String email;

	private Date date;
}


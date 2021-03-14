package com.panpal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.panpal.RequestInfo;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.text.ParseException;
import org.json.simple.JSONObject;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(path="/booking")
public class BookingController {
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private DeskRepository deskRepository;
	@Autowired
	private FloorRepository floorRepository;
	@Autowired
	private BuildingRepository buildingRepository;
	@Autowired
	private UserRepository userRepository;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	@PostMapping
	// public String addNewBooking (@RequestParam Integer deskId
	// , @RequestParam Integer employeeId
	// , @RequestParam String date) {
	public String addNewBooking (@RequestBody RequestInfo info) {
		Integer deskId = info.getDeskId();
		Integer employeeId = info.getEmployeeId();
		Desk desk = deskRepository.findDeskById(deskId);
		User employee = userRepository.findUserById(employeeId);

		String date = info.getDate();
		Date dateObj = new Date();
		try {  
			dateObj = dateFormatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Booking n = new Booking();
		n.setDesk(desk);
		n.setEmployee(employee);
		n.setDate(dateObj);
		bookingRepository.save(n);
		return "Booking Saved";
	}

	@PutMapping
	// public String updateBooking (@RequestParam Integer id
	// , @RequestParam(required = false) Integer deskId
	// , @RequestParam(required = false) Integer employeeId
	// , @RequestParam(required = false) String date) {
	public String updateBooking (@RequestBody RequestInfo info) {
		Integer id = info.getId();
		Integer deskId = info.getDeskId();
		Integer employeeId = info.getEmployeeId();
		String date = info.getDate();

		Date dateObj = new Date();
		if (date != null) {
			try {  
				dateObj = dateFormatter.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		Booking n = bookingRepository.findBookingById(id);
		
		if (n == null) {
			return "Booking does not exist";
		}
		
		if (deskId != null) {
			Desk desk = deskRepository.findDeskById(deskId);
			n.setDesk(desk);
		}
		if (employeeId != null) {
			User employee = userRepository.findUserById(employeeId);
			n.setEmployee(employee);
		}
		if (date != null) {
			n.setDate(dateObj);
		}
		bookingRepository.save(n);
		return "Booking Updated";
	}

	@DeleteMapping
	// public String deleteBooking (@RequestParam Integer id) {
	public String deleteBooking (@RequestBody RequestInfo info) {
		Integer id = info.getId();
		
		Booking n = bookingRepository.findBookingById(id);
		
		if (n == null) {
			return "Booking does not exist";
		}

		bookingRepository.delete(n);
		return "Booking Deleted";
	}

	@GetMapping(path="/all")
	public Iterable<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	@GetMapping(path="/deskId")
	public Iterable<Booking> getBookingsByDeskId(@RequestParam Integer deskId) {
		Desk desk = deskRepository.findDeskById(deskId);
		return bookingRepository.findByDesk(desk);
	}

	@GetMapping(path="/employeeId")
	public Iterable<Booking> getBookingsByEmployeeId(@RequestParam Integer employeeId) {
		User employee = userRepository.findUserById(employeeId);
		return bookingRepository.findByEmployee(employee);
	}

	@GetMapping(path="/date")
	public Iterable<Booking> getBookingsByDate(@RequestParam String date) {

		Date dateObj = new Date();
		try {  
			dateObj = dateFormatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		} 

		return bookingRepository.findByDate(dateObj);
	}

	@GetMapping
	public ArrayList<Booking> getBookings(@RequestParam String date
	, @RequestParam Integer floorId) {

		Date dateObj = new Date();
		try {  
			dateObj = dateFormatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		} 

		ArrayList<Booking> ret = new ArrayList<Booking>();
		Iterator<Booking> bookingIterator = bookingRepository.findByDate(dateObj).iterator();
		while (bookingIterator.hasNext()) {
			Booking booking = bookingIterator.next();
			Desk desk = deskRepository.findDeskById(booking.getDeskId());
			if (desk.getFloorId() == floorId) {
				ret.add(booking);
			}
		}

		return ret;
	}

	// @GetMapping(path="/info")
	// public JSONObject getBooking(@RequestParam Integer id) {

	// 	JSONObject ret = new JSONObject();

	// 	Booking booking = bookingRepository.findBookingById(id);
	// 	ret.put("booking", booking);
	// 	User employee = userRepository.findUserById(booking.getEmployeeId());
	// 	ret.put("employee", employee);
	// 	Desk desk = deskRepository.findDeskById(booking.getDeskId());
	// 	ret.put("desk", desk);
	// 	Floor floor = floorRepository.findFloorById(desk.getFloorId());
	// 	ret.put("floor", floor);
	// 	Building building = buildingRepository.findBuildingById(floor.getBuildingId());
	// 	ret.put("building", building);

	// 	return ret;
	// }
}

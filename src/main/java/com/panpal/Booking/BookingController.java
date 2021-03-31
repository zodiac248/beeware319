package com.panpal.Booking;

import com.panpal.Error.*;
import com.panpal.ResultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.panpal.RequestInfo;
import com.panpal.Desk.DeskRepository;
import com.panpal.Desk.Desk;
import com.panpal.Floor.FloorRepository;
import com.panpal.Building.BuildingRepository;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.text.ParseException;

@CrossOrigin(origins = "https://beeware319-front.azurewebsites.net")
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
	private ResultController resultController = new ResultController();

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	@PostMapping
	public ResponseEntity<String> addNewBooking (@RequestBody RequestInfo info) {
		try {
			Integer deskId = info.getDeskId();
			Desk desk = deskRepository.findDeskById(deskId);
			if (desk == null) {
				throw new DeskNoLongerExistsException();
			}
			String email = info.getEmail();
			String date = info.getDate();
			Date dateObj = new Date();

			try {
				dateObj = dateFormatter.parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Integer range = info.getRange();

			Date currentDate = new Date();
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			c.add(Calendar.MONTH, 6);
			Date farthestDate = c.getTime();
			LocalDate dateBefore = LocalDate.parse(dateFormatter.format(dateObj));
			LocalDate dateAfter = LocalDate.parse(dateFormatter.format(farthestDate));
			Long acceptableRangeLong = ChronoUnit.DAYS.between(dateBefore, dateAfter);
			Integer acceptableRange = acceptableRangeLong.intValue();

			if (range == 1) {
				Booking n = new Booking();
				n.setDesk(desk);
				n.setEmail(email);
				n.setDate(dateObj);
				try{
					bookingRepository.save(n);
				}catch (Exception e) {
					throw new BookingNotAvailableException("the booking with the desk number "+ n.getDesk().getDeskNumber()+" in floor "+n.getDesk().getFloor().getFloorNumber()+"in the "+ n.getDesk().getFloor().getBuilding().getName()+"has already been reserved");
				}
				return resultController.handleSuccess("booking Saved");
			} else if (range <= acceptableRange) {
				for (int i=0; i<range; i++) {
					Booking b = new Booking();
					b.setDesk(desk);
					b.setEmail(email);
					c.setTime(dateObj);
					c.add(Calendar.DATE, i);
					Date d = c.getTime();
					b.setDate(d);
					try{
						bookingRepository.save(b);
					}catch (Exception e) {
						throw new BookingNotAvailableException("the booking with the desk number "+ b.getDesk().getDeskNumber()+" in floor "+b.getDesk().getFloor().getFloorNumber()+"in the "+ b.getDesk().getFloor().getBuilding().getName()+"has already been reserved");
					}
				}
				return resultController.handleSuccess("booking Saved");
			} else {
				throw new ExceedRangeException();
			}


		} catch (Exception e){
			return resultController.handleError(e);
		}

	}

	@PutMapping
	public ResponseEntity<String> updateBooking (@RequestBody RequestInfo info) {
		try {
			Integer id = info.getId();
			Integer deskId = info.getDeskId();
			String email = info.getEmail();
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
				throw new BookingNotExistsException("Booking does not exist");
			}

			if (deskId != null) {
				Desk desk = deskRepository.findDeskById(deskId);
				if (desk == null) {
					throw new DeskNoLongerExistsException();
				}
				n.setDesk(desk);
			}
			if (email != null) {
				n.setEmail(email);
			}
			if (date != null) {
				n.setDate(dateObj);
			}
			try{
				bookingRepository.save(n);
			}catch (Exception e) {
				throw new BookingNotAvailableException("the booking with the desk number "+ n.getDesk().getDeskNumber()+" in floor "+n.getDesk().getFloor().getFloorNumber()+"in the "+ n.getDesk().getFloor().getBuilding().getName()+"has already been reserved");
			}
			return resultController.handleSuccess("booking Updated");
		} catch (Exception e){
			return resultController.handleError(e);
		}
	}

	@DeleteMapping
	public ResponseEntity<String> deleteBooking (@RequestParam Integer id) {
		try {
			Booking n = bookingRepository.findBookingById(id);

			if (n == null) {
				throw new BookingNotExistsException("Booking does not exist");
			}

			bookingRepository.delete(n);
			return resultController.handleSuccess("booking deleted");
		} catch (Exception e) {
			return resultController.handleError(e);
		}
	}

	@GetMapping(path="/all")
	public Iterable<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}

	@GetMapping(path="/deskId")
	public Iterable<Booking> getBookingsByDeskId(@RequestParam Integer deskId) {
		Desk desk = deskRepository.findDeskById(deskId);
		return bookingRepository.findByDeskOrderByDateAsc(desk);
		// return bookingRepository.findByDesk(desk);
	}

	@GetMapping(path="/email")
	public Iterable<Booking> getBookingsByEmail(@RequestParam String email) {
		return bookingRepository.findByEmailOrderByDateAsc(email);
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

	//		JSONObject ret = new JSONObject();

	//		Booking booking = bookingRepository.findBookingById(id);
	// 		ret.put("booking", booking);
	// 		User employee = userRepository.findUserById(booking.getEmployeeId());
	// 		ret.put("employee", employee);
	// 		Desk desk = deskRepository.findDeskById(booking.getDeskId());
	// 		ret.put("desk", desk);
	// 		Floor floor = floorRepository.findFloorById(desk.getFloorId());
	// 		ret.put("floor", floor);
	// 		Building building = buildingRepository.findBuildingById(floor.getBuildingId());
	// 		ret.put("building", building);

	//		return ret;
	// }
}

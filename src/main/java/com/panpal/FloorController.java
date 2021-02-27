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

import com.panpal.Desk;
import com.panpal.RequestInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.text.ParseException;  
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/floor")
public class FloorController {
	@Autowired
	private FloorRepository floorRepository;
	@Autowired
	private DeskRepository deskRepository;
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private BuildingRepository buildingRepository;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	@PostMapping
	// public String addNewFloor (@RequestBody Integer floorNumber
	// , @RequestParam Integer buildingId
	// , @RequestParam String deskIds) {
	public String addNewFloor (@RequestBody RequestInfo info) {
		Integer floorNumber = info.getFloorNumber();
		Integer buildingId = info.getBuildingId();
		Building building = buildingRepository.findBuildingById(buildingId);
		String deskNumbers = info.getDeskNumbers();

		Floor n = new Floor();
		n.setFloorNumber(floorNumber);
		n.setBuilding(building);
		floorRepository.save(n);

		if (deskNumbers != null) {
			List<String> desks = new LinkedList<String>(Arrays.asList(deskNumbers.split("[ ]*,[ ]*")));
			Integer floorId = n.getId();
			
			for (int i=0; i < desks.size(); i++) {
				Desk d = new Desk();
				d.setDeskNumber(desks.get(i));
				d.setFloor(n);
				deskRepository.save(d);
			}
		}

		return "Floor Saved";
	}

	@PutMapping
	// public String updateFloor (@RequestParam Integer id
	// , @RequestParam(required = false) Integer floorNumber
	// , @RequestParam(required = false) Integer buildingId) {
	public String updateFloor (@RequestBody RequestInfo info) {
		Integer id = info.getId();
		Integer floorNumber = info.getFloorNumber();
		Integer buildingId = info.getBuildingId();
		String deskNumbers = info.getDeskNumbers();

		Floor n = floorRepository.findFloorById(id);
		
		if (n == null) {
			return "Floor with id=" + id + " does not exist";
		}
		
		if (floorNumber != null) {
			n.setFloorNumber(floorNumber);
		}
		if (buildingId != null) {
			Building building = buildingRepository.findBuildingById(buildingId);
			n.setBuilding(building);
		}
		floorRepository.save(n);

		if (deskNumbers != null) {
			List<String> desks = new LinkedList<String>(Arrays.asList(deskNumbers.split("[ ]*,[ ]*")));
			Integer floorId = n.getId();

			Iterable<Desk> existingDesks = deskRepository.findByFloor(n);
			Iterator<Desk> existingDesksIterator = existingDesks.iterator();
			while (existingDesksIterator.hasNext()) {
				Desk currentDesk = existingDesksIterator.next();
				if (desks.contains(currentDesk.getDeskNumber())) {
					desks.remove(currentDesk.getDeskNumber());
				} else {
					// if (null == bookingRepository.findByDesk(currentDesk)) {
						deskRepository.delete(currentDesk);
					// } else {
					// 	return "Booking exists for desk with id=" + currentDesk.getDeskNumber();
					// }
				}
			}

			for (int i=0; i < desks.size(); i++) {
				Desk d = new Desk();
				d.setDeskNumber(desks.get(i));
				d.setFloor(n);
				deskRepository.save(d);
			}
		}

		return "Floor Updated";
	}

	@DeleteMapping
	// public String deleteFloor (@RequestParam Integer id) {
	public String deleteFloor (@RequestBody RequestInfo info) {
		Integer id = info.getId();
		
		Floor n = floorRepository.findFloorById(id);
		
		if (n == null) {
			return "Floor does not exist";
		}
		
		Iterator<Desk> deskIterator = deskRepository.findByFloor(n).iterator();
		while (deskIterator.hasNext()) {
			deskRepository.delete(deskIterator.next());
		}

		floorRepository.delete(n);
		return "Floor Deleted";
	}

	@GetMapping(path="/all")
	public Iterable<Floor> getAllFloors() {
		return floorRepository.findAll();
	}

	@GetMapping
	public Iterable<Floor> getFloors(@RequestParam Integer buildingId) {
		Building building = buildingRepository.findBuildingById(buildingId);
		return floorRepository.findByBuilding(building);
	}

	@GetMapping(path="/info")
	public JSONObject getFloorInfo(@RequestParam Integer id
	,@RequestParam String date) {

		JSONObject ret = new JSONObject();
		JSONArray desksInfo = new JSONArray();

		Date dateObj = new Date();
		try {  
			dateObj = dateFormatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}  

		ArrayList<Desk> desks = new ArrayList<Desk>();
		Floor floor = floorRepository.findFloorById(id);
		deskRepository.findByFloor(floor).forEach(desks::add);
		Integer totalDesks = desks.size();
		ret.put("totalDesks", totalDesks);

		Integer occupiedDesks = 0;
		for (int i=0; i < totalDesks; i++) {
			Desk currentDesk = desks.get(i);
			Integer deskId = currentDesk.getId();
			ArrayList<Booking> deskBookings = new ArrayList<Booking>();
			bookingRepository.findByDeskAndDate(currentDesk, dateObj).forEach(deskBookings::add);

			JSONObject currentDeskInfo = new JSONObject();
			currentDeskInfo.put("id", deskId);
			currentDeskInfo.put("deskNumber", currentDesk.getDeskNumber());
			currentDeskInfo.put("floorId", currentDesk.getFloorId());
			if (deskBookings.size() == 1) {
				currentDeskInfo.put("occupied", "true");
				occupiedDesks++;
			} else if (deskBookings.size() == 0) {
				currentDeskInfo.put("occupied", "false");
			} else {
				//error case, multiple bookings for the same desk and date
			}
			desksInfo.add(currentDeskInfo);
		}

		ret.put("freeDesks", totalDesks - occupiedDesks);
		ret.put("occupiedDesks", occupiedDesks);
		ret.put("desks", desksInfo);

		return ret;
	}
}

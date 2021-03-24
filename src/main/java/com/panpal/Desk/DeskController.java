package com.panpal.Desk;

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
import com.panpal.Floor.FloorRepository;
import com.panpal.Floor.Floor;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/desk")
public class DeskController {
	@Autowired
	private DeskRepository deskRepository;
	@Autowired
	private FloorRepository floorRepository;

	@PostMapping
	// public String addNewDesk (@RequestParam String deskNumber
	// , @RequestParam Integer floorId) {
	public String addNewDesk (@RequestBody RequestInfo info) {
		String deskNumber = info.getDeskNumber();
		Integer floorId = info.getFloorId();
		Floor floor = floorRepository.findFloorById(floorId);

		Desk n = new Desk();
		n.setDeskNumber(deskNumber);
		n.setFloor(floor);
		deskRepository.save(n);
		return "Desk Saved";
	}

	@PutMapping
	// public String updateDesk (@RequestParam Integer id
	// , @RequestParam(required = false) String deskNumber
	// , @RequestParam(required = false) Integer floorId) {
	public String updateDesk (@RequestBody RequestInfo info) {
		Integer id = info.getId();
		String deskNumber = info.getDeskNumber();
		Integer floorId = info.getFloorId();

		Desk n = deskRepository.findDeskById(id);
		
		if (n == null) {
			return "Desk does not exist";
		}
		
		if (deskNumber != null) {
			n.setDeskNumber(deskNumber);
		}
		if (floorId != null) {
			Floor floor = floorRepository.findFloorById(floorId);
			n.setFloor(floor);
		}
		deskRepository.save(n);
		return "Desk Updated";
	}

	@DeleteMapping
	// public String deleteDesk (@RequestParam Integer id) {
	public String deleteDesk (@RequestBody RequestInfo info) {
		Integer id = info.getId();
		
		Desk n = deskRepository.findDeskById(id);
		
		if (n == null) {
			return "Desk does not exist";
		}

		deskRepository.delete(n);
		return "Desk Deleted";
	}

	@GetMapping(path="/all")
	public Iterable<Desk> getAllDesks() {
		return deskRepository.findAll();
	}

	@GetMapping
	public Iterable<Desk> getDesks(@RequestParam Integer floorId) {
		Floor floor = floorRepository.findFloorById(floorId);
		return deskRepository.findByFloorOrderByDeskNumberAsc(floor);
		// return deskRepository.findByFloor(floor);
	}
}

package com.panpal.Desk;

import com.panpal.Error.*;
import com.panpal.Error.DeskNoLongerExistsException;
import com.panpal.ResultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
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
	private ResultController resultController = new ResultController();


	@PostMapping
	// public String addNewDesk (@RequestParam String deskNumber
	// , @RequestParam Integer floorId) {
	public ResponseEntity<String> addNewDesk (@RequestBody RequestInfo info) {
		try {
			String deskNumber = info.getDeskNumber();
			Integer floorId = info.getFloorId();
			Floor floor = floorRepository.findFloorById(floorId);
			if (floor == null) {
				throw new FloorNoLongerExists("Floor with id=" + floorId + " does not exist");
			}
			Desk n = new Desk();
			n.setDeskNumber(deskNumber);
			n.setFloor(floor);
			try {
				deskRepository.save(n);

			} catch (Exception e) {
				if (e instanceof DataIntegrityViolationException){
					DataIntegrityViolationException a = (DataIntegrityViolationException) e;
					String reason = a.getRootCause().getMessage();
					if (reason.contains("Data too long")) {
						throw new InputTooLongException();
					} else if (reason.contains("Duplicate")){
						throw new DuplicateDeskException("the desk with desk number "+n.getDeskNumber()+" and floor id "+n.getFloorId()+" already exists");
					}else {
						throw e;
					}

				}
			}
			return resultController.handleSuccess("Desks Saved");

		} catch (Exception e){
			System.out.println(e.getCause());
			return resultController.handleError(e);
		}
	}

	@PutMapping
	// public String updateDesk (@RequestParam Integer id
	// , @RequestParam(required = false) String deskNumber
	// , @RequestParam(required = false) Integer floorId) {
	public ResponseEntity<String> updateDesk (@RequestBody RequestInfo info) {
		try {
			Integer id = info.getId();
			String deskNumber = info.getDeskNumber();
			Integer floorId = info.getFloorId();

			Desk n = deskRepository.findDeskById(id);
			if (n == null) {
				throw new DeskNoLongerExistsException();
			}

			if (deskNumber != null) {
				n.setDeskNumber(deskNumber);
			}
			if (floorId != null) {
				Floor floor = floorRepository.findFloorById(floorId);
				if (floor == null) {
					throw new FloorNoLongerExists("floor id with "+floor+"no longer exists");
				}
				n.setFloor(floor);
			}
			try {
				deskRepository.save(n);

			} catch (Exception e) {
				if (e instanceof DataIntegrityViolationException){
					DataIntegrityViolationException a = (DataIntegrityViolationException) e;
					String reason = a.getRootCause().getMessage();
					if (reason.contains("Data too long")) {
						throw new InputTooLongException();
					} else if (reason.contains("Duplicate")){
						throw new DuplicateDeskException("the desk with desk number "+n.getDeskNumber()+" and floor id "+n.getFloorId()+" already exists");
					}else {
						throw e;
					}

				}			}
			return resultController.handleSuccess("Desks Updated");
		} catch (Exception e){
			System.out.println(e.getCause());

			return resultController.handleError(e);
		}
	}

	@DeleteMapping
	// public String deleteDesk (@RequestParam Integer id) {
	public ResponseEntity<String> deleteDesk (@RequestBody RequestInfo info) {
		try {
			Integer id = info.getId();

			Desk n = deskRepository.findDeskById(id);

			if (n == null) {
				throw new DeskNoLongerExistsException();
			}

			deskRepository.delete(n);
			return resultController.handleSuccess("Desks Deleted");
		} catch (Exception e) {
			return resultController.handleError(e);
		}

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

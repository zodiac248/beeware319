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

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/building")
public class BuildingController {
	@Autowired // This means to get the bean called userRepository
				// Which is auto-generated by Spring, we will use it to handle the data
	private BuildingRepository buildingRepository;

	@PostMapping
	// public String addNewBuilding (@RequestBody String name
	// , @RequestBody String address) {
	public String addNewBuilding (@RequestBody RequestInfo info) {
		String name = info.getName();
		String address = info.getAddress();

		Building n = new Building();
		n.setName(name);
		n.setAddress(address);
		buildingRepository.save(n);
		return "Building Saved";
	}

	@PutMapping
	// public String updateBuilding (@RequestParam Integer id
	// , @RequestParam(required = false) String name
	// , @RequestParam(required = false) String address) {
	public String updateBuilding (@RequestBody RequestInfo info) {

		Building n = buildingRepository.findBuildingById(info.getId());
		
		if (n == null) {
			return "Building does not exist";
		}
		
		String name = info.getName();
		String address = info.getAddress();
		if (name != null) {
			n.setName(name);
		}
		if (address != null) {
			n.setAddress(address);
		}

		buildingRepository.save(n);
		return "Building Updated";
	}

	@DeleteMapping
	// public String deleteBuilding (@RequestParam Integer id) {
	public String deleteBuilding (@RequestBody RequestInfo info) {
		
		Building n = buildingRepository.findBuildingById(info.getId());
		
		if (n == null) {
			return "Building does not exist";
		}

		buildingRepository.delete(n);
		return "Building Deleted";
	}
	@GetMapping(path="/all")
	public Iterable<Building> getAllBuildings() {
		return buildingRepository.findAll();
	}

	@GetMapping
	public Building getBuilding(@RequestParam Integer id) {
		return buildingRepository.findBuildingById(id);
	}
}

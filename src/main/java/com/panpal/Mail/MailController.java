package com.panpal.Mail;

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
import com.panpal.Mail.MailRepository;
import com.panpal.Mail.Mail;
import com.panpal.Request.RequestRepository;
import com.panpal.Request.Request;
import com.panpal.Building.BuildingRepository;
import com.panpal.Building.Building;

import java.time.LocalDateTime;
import java.util.Iterator;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/mail")
public class MailController {
	@Autowired
	private MailRepository mailRepository;
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private BuildingRepository buildingRepository;

	@PostMapping
	public Integer addNewMail (@RequestBody RequestInfo info) {
		String email = info.getEmail();
        Integer buildingId = info.getBuildingId();
		LocalDateTime arrivalDate = LocalDateTime.now();
		String sender = info.getSender();

		Mail n = new Mail();
		n.setEmail(email);
        if (buildingId != null) {
            Building building = buildingRepository.findBuildingById(buildingId);
            n.setBuilding(building);
        }
        n.setDate(arrivalDate);
		n.setSender(sender);

		Request request = new Request();
		requestRepository.save(request);
		n.setRequest(request);
        n.setStatus("Awaiting Request");
		mailRepository.save(n);

		return n.getId();
	}

	@PutMapping
	public String updateMail (@RequestBody RequestInfo info) {

		Mail n = mailRepository.findMailById(info.getId());
		
		if (n == null) {
			return "Mail does not exist";
		}

		String email = info.getEmail();
		if (email != null) {
            n.setEmail(email);
		}
		String sender = info.getSender();
		if (sender != null) {
            n.setSender(sender);
		}
        String status = info.getStatus();
		if (status != null) {
            n.setStatus(status);
		}
        Integer buildingId = info.getBuildingId();
		if (buildingId != null) {
            Building building = buildingRepository.findBuildingById(buildingId);
            n.setBuilding(building);
		}
		mailRepository.save(n);
		return "Mail Updated";
	}

	@DeleteMapping
	public String deleteMail (@RequestBody RequestInfo info) {
		
		Mail n = mailRepository.findMailById(info.getId());
		
		if (n == null) {
			return "Mail does not exist";
		}

		mailRepository.delete(n);
		return "Mail Deleted";
	}

	@GetMapping(path="/all")
	public Iterable<Mail> getAllMail() {
		return mailRepository.findByOrderByDateAsc();
	}

	@GetMapping(path="/id")
	public Mail getMailById(@RequestParam Integer id) {
		return mailRepository.findMailById(id);
	}

	@GetMapping(path="/byStatus")
	public Iterable<Mail> getMailByStatus(@RequestParam String status) {
		return mailRepository.findByStatusOrderByDateAsc(status);
	}

	@GetMapping(path="/byEmail")
	public Iterable<Mail> getMailByEmail(@RequestParam String email) {
		return mailRepository.findByEmailOrderByDateAsc(email);
	}

	@GetMapping
	public Iterable<Mail> getMail(@RequestParam String email
	, @RequestParam String status) {
		return mailRepository.findByEmailAndStatusOrderByDateAsc(email, status);
	}
}

package com.panpal.Request;

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
import com.panpal.Request.RequestRepository;
import com.panpal.Request.Request;
import com.panpal.Mail.MailRepository;
import com.panpal.Mail.Mail;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

@CrossOrigin(origins = "https://beeware319fe.azurewebsites.net")
@RestController
@RequestMapping(path="/request")
public class RequestController {
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private MailRepository mailRepository;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@PostMapping
	public Integer addNewRequest (@RequestBody RequestInfo info) {
		String email = info.getEmail();
		String name = info.getName();
        String phoneNumber = info.getPhoneNumber();
        String status = info.getStatus();
        String feedback = info.getFeedback();
        String instructions = info.getInstructions();
        String type = info.getType();
		Date submissionDate = new Date();
        Integer mailId = info.getMailId();

		Request n = new Request();
		n.setEmail(email);
        n.setName(name);
        n.setPhoneNumber(phoneNumber);
        n.setStatus(status);
        n.setFeedback(feedback);
        n.setInstructions(instructions);
        n.setType(type);
        n.setSubmissionDate(submissionDate);
        if (mailId != null) {
            Mail mail = mailRepository.findMailById(mailId);
            n.setMail(mail);
        }
		requestRepository.save(n);

		return n.getId();
	}

	@PutMapping
	public String updateRequest (@RequestBody RequestInfo info) {

		Request n = requestRepository.findRequestById(info.getId());
		
		if (n == null) {
			return "Request does not exist";
		}

		String name = info.getName();
		if (name != null) {
            n.setName(name);
		}
        String phoneNumber = info.getPhoneNumber();
		if (phoneNumber != null) {
            n.setPhoneNumber(phoneNumber);
		}
        String status = info.getStatus();
		if (status != null) {
            n.setStatus(status);
		}
        String feedback = info.getFeedback();
		if (feedback != null) {
            n.setFeedback(feedback);
		}
        String instructions = info.getInstructions();
		if (instructions != null) {
            n.setInstructions(instructions);
		}
        String type = info.getType();
		if (type != null) {
            n.setType(type);
		}
        Integer mailId = info.getMailId();
		if (mailId != null) {
			Mail mail = mailRepository.findMailById(mailId);
            n.setMail(mail);
		}

		requestRepository.save(n);
		return "Request Updated";
	}

	@PutMapping(path="/complete")
	public String completeRequest (@RequestBody RequestInfo info) {

		Request n = requestRepository.findRequestById(info.getId());
		
		if (n == null) {
			return "Request does not exist";
		}

		n.setStatus("complete");
        n.setCompletionDate(new Date());
        String feedback = info.getFeedback();
		if (feedback != null) {
            n.setFeedback(feedback);
		}
        Integer mailId = info.getMailId();
		if (mailId != null) {
			Mail mail = mailRepository.findMailById(mailId);
            n.setMail(mail);
		}

		requestRepository.save(n);
		return "Request Updated";
	}

	@DeleteMapping
	public String deleteRequest (@RequestBody RequestInfo info) {
		
		Request n = requestRepository.findRequestById(info.getId());
		
		if (n == null) {
			return "Request does not exist";
		}

		requestRepository.delete(n);
		return "Request Deleted";
	}

	@GetMapping
	public Iterable<Request> getAllRequest() {
		return requestRepository.findByOrderBySubmissionDateAsc();
	}

	@GetMapping(path="/id")
	public Request getRequest(@RequestParam Integer id) {
		return requestRepository.findRequestById(id);
	}

	// @GetMapping(path="/incomplete")
	// public Iterable<Request> getIncompleteRequest() {
	// 	Topic topic = topicRepository.findTopicById(topicId);
	// 	return requestRepository.findByTopicOrderByDateAsc(topic);
	// }

	@GetMapping(path="/byEmail")
	public Iterable<Request> getRequestByEmail(@RequestParam String email) {
		return requestRepository.findByEmailOrderBySubmissionDateAsc(email);
	}
}

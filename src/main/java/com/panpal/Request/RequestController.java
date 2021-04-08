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
import java.text.ParseException;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/request")
public class RequestController {
	@Autowired
	private RequestRepository requestRepository;
	@Autowired
	private MailRepository mailRepository;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

	@PostMapping
	public Integer addNewRequest (@RequestBody RequestInfo info) {
        String type = info.getInstructionType();
		String instructions = info.getInstructionDescription();
        String completionDate = info.getRequestedCompletionDate();
		Date dateObj = new Date();
		try {
			dateObj = dateFormatter.parse(completionDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String feedback = info.getFeedback();
		// Integer mailId = info.getMailId();
		// Mail mail = mailRepository.findMailById(mailId);

		Request n = new Request();
		n.setInstructionType(type);
		n.setInstructionDescription(instructions);
		n.setRequestedCompletionDate(dateObj);
		n.setFeedback(feedback);
		// n.setMail(mail);
		requestRepository.save(n);

		// mail.setStatus("Not Started");
		// mailRepository.save(mail);

		return n.getId();
	}

	@PutMapping
	public String updateRequest (@RequestBody RequestInfo info) {

		Request n = requestRepository.findRequestById(info.getId());
		
		if (n == null) {
			return "Request does not exist";
		}

        String type = info.getInstructionType();
		if (type != null) {
            n.setInstructionType(type);
		}
        String instructions = info.getInstructionDescription();
		if (instructions != null) {
            n.setInstructionDescription(instructions);
		}
        String completionDate = info.getRequestedCompletionDate();
		if (completionDate != null) {
			Date dateObj = new Date();
			try {
				dateObj = dateFormatter.parse(completionDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            n.setRequestedCompletionDate(dateObj);
		}
        String feedback = info.getFeedback();
		if (feedback != null) {
            n.setFeedback(feedback);
		}

		requestRepository.save(n);

		String status = info.getStatus();
		if (status != null) {
		Mail mail = mailRepository.findByRequest(n);
        		mail.setStatus(status);
        		mailRepository.save(mail);
		}
		return "Request Updated";
	}

	@PutMapping(path="/submit")
	public String submitRequest (@RequestBody RequestInfo info) {

		Request n = requestRepository.findRequestById(info.getId());
		
		if (n == null) {
			return "Request does not exist";
		}

        String type = info.getInstructionType();
		if (type != null) {
            n.setInstructionType(type);
		}
        String instructions = info.getInstructionDescription();
		if (instructions != null) {
            n.setInstructionDescription(instructions);
		}
        String completionDate = info.getRequestedCompletionDate();
		if (completionDate != null) {
			Date dateObj = new Date();
			try {
				dateObj = dateFormatter.parse(completionDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
            n.setRequestedCompletionDate(dateObj);
		}

		requestRepository.save(n);
		Mail mail = mailRepository.findByRequest(n);
		mail.setStatus("Not Started");
		mailRepository.save(mail);
		return "Request Submitted";
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
		return requestRepository.findAll();
	}

	@GetMapping(path="/id")
	public Request getRequest(@RequestParam Integer id) {
		return requestRepository.findRequestById(id);
	}
}

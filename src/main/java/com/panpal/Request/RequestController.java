package com.panpal.Request;

import com.panpal.Error.*;
import com.panpal.ResultController;
import org.springframework.http.ResponseEntity;
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

import java.time.LocalDate;
import java.util.Iterator;
import java.text.ParseException;
import java.time.format.DateTimeParseException;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/request")
public class RequestController {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private MailRepository mailRepository;
    private ResultController resultController = new ResultController();

    // @PostMapping
    // public Integer addNewRequest (@RequestBody RequestInfo info) {
    // 	String instructions = info.getInstructionDescription();
    // 	if (instructions.length() > 512) {
    // 		throw new RuntimeException("Instruction to long, should not exceed 512 characters");
    // 	}
    // 	String feedback = info.getFeedback();
    // 	if (feedback.length() > 512) {
    // 		throw new RuntimeException("Feedback to long, should not exceed 512 characters");
    // 	}
    //     String type = info.getInstructionType();
    //     String completionDate = info.getRequestedCompletionDate();
    //     LocalDate dateObj = LocalDate.now();
    // 	dateObj = LocalDate.parse(completionDate);
    // 	// Integer mailId = info.getMailId();
    // 	// Mail mail = mailRepository.findMailById(mailId);

    // 	Request n = new Request();
    // 	n.setInstructionType(type);
    // 	n.setInstructionDescription(instructions);
    // 	n.setRequestedCompletionDate(dateObj);
    // 	n.setFeedback(feedback);
    // 	// n.setMail(mail);
    // 	requestRepository.save(n);

    // 	// mail.setStatus("Not Started");
    // 	// mailRepository.save(mail);

    // 	return n.getId();
    // }

    @PutMapping
    public ResponseEntity<String> updateRequest (@RequestBody RequestInfo info) {
        try {
            Request n = requestRepository.findRequestById(info.getId());

            if (n == null) {
                throw new RequestNoLongerExistsException("Request with id = " + info.getId() + " no longer exists");
            }

            String type = info.getInstructionType();
            if (type != null) {
                n.setInstructionType(type);
            }
            
            String instructions = info.getInstructionDescription();
            if (instructions != null) {
                if (instructions.length() > 512) {
                    throw new InputTooLongException("Instruction too long, should not exceed 512 characters");
                } else {
                    n.setInstructionDescription(instructions);
                }
            }
            
            String completionDate = info.getRequestedCompletionDate();
            if (completionDate != null) {
                LocalDate dateObj = null;
                dateObj = LocalDate.parse(completionDate);
                n.setRequestedCompletionDate(dateObj);
            }

            String feedback = info.getFeedback();
            if (feedback != null) {
                if (feedback.length() > 512) {
                    throw new InputTooLongException("Feedback too long, should not exceed 512 characters");
                } else {
                    n.setFeedback(feedback);
                }
            }

            requestRepository.save(n);

            String status = info.getStatus();
            if (status != null) {
                Mail mail = mailRepository.findByRequest(n);
                mail.setStatus(status);
                mailRepository.save(mail);
            }

            return resultController.handleSuccess("Request Updated");
        } catch (Exception e) {
            return resultController.handleError(e);
        }
    }

    @PutMapping(path="/submit")
    public ResponseEntity<String> submitRequest (@RequestBody RequestInfo info) {
        try {
            Request n = requestRepository.findRequestById(info.getId());

            if (n == null) {
                throw new RequestNoLongerExistsException("Request with id = " + info.getId() + " no longer exists");
            }

            String type = info.getInstructionType();
            if (type != null) {
                n.setInstructionType(type);
            }
            String instructions = info.getInstructionDescription();
            if (instructions.length() > 512) {
                throw new InputTooLongException("Instruction too long, should not exceed 512 characters");
            }
            if (instructions != null) {
                n.setInstructionDescription(instructions);
            }
            String completionDate = info.getRequestedCompletionDate();
            if (completionDate != null) {
                LocalDate dateObj = null;
                dateObj = LocalDate.parse(completionDate);
                n.setRequestedCompletionDate(dateObj);
            }

            requestRepository.save(n);
            Mail mail = mailRepository.findByRequest(n);
            mail.setStatus("Not Started");
            mailRepository.save(mail);

            return resultController.handleSuccess("Request Submitted");
        } catch (Exception e) {
            return resultController.handleError(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteRequest (@RequestBody RequestInfo info) {
        try {
            Request n = requestRepository.findRequestById(info.getId());

            if (n == null) {
                throw new RequestNoLongerExistsException("Request with id = " + info.getId() + " no longer exists");
            }

            requestRepository.delete(n);

            return resultController.handleSuccess("Request Deleted");
        } catch (Exception e) {
            return resultController.handleError(e);
        }
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

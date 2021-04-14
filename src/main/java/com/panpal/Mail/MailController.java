package com.panpal.Mail;

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
import org.springframework.web.bind.annotation.RestController;

import com.panpal.RequestInfo;
import com.panpal.Request.RequestRepository;
import com.panpal.Request.Request;
import com.panpal.Building.BuildingRepository;
import com.panpal.Building.Building;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
    private ResultController resultController = new ResultController();

    @PostMapping
    public ResponseEntity<String> addNewMail (@RequestBody RequestInfo info) {
        try {
            String email = info.getEmail();
            Integer buildingId = info.getBuildingId();
            LocalDateTime arrivalDate = LocalDateTime.now();
            String sender = info.getSender();

            Mail n = new Mail();
            n.setEmail(email);
            if (buildingId != null) {
                Building building = buildingRepository.findBuildingById(buildingId);
                if (building == null) {
                    throw new BuildingNoLongerExistsException("Building with id=" + buildingId + " does not exist");
                }
                n.setBuilding(building);
            }
            n.setDate(arrivalDate);
            n.setSender(sender);

            Request request = new Request();
            n.setRequest(request);
            request.setMail(n);
            n.setStatus("Awaiting Request");
            mailRepository.save(n);

            return resultController.handleSuccess(n.getId().toString());
        } catch (Exception e) {
            return resultController.handleError(e);
        }
    }

    @PutMapping
    public ResponseEntity<String> updateMail (@RequestBody RequestInfo info) {
        try {
            Mail n = mailRepository.findMailById(info.getId());

            if (n == null) {
                throw new MailNoLongerExistsException("Mail with id = " + info.getId() + " does not exist");
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
                if (building == null) {
                    throw new BuildingNoLongerExistsException("Building with id=" + buildingId + " does not exist");
                }
                n.setBuilding(building);
            }
            mailRepository.save(n);

            return resultController.handleSuccess("Mail Updated");
        } catch (Exception e) {
            return resultController.handleError(e);
        }
    }

    @DeleteMapping
    public ResponseEntity<String> deleteMail (@RequestBody RequestInfo info) {
        try {
            Mail n = mailRepository.findMailById(info.getId());

            if (n == null) {
                throw new MailNoLongerExistsException("Mail with id = " + info.getId() + " does not exist");
            }

            mailRepository.delete(n);

            return resultController.handleSuccess("Mail Deleted");
        } catch (Exception e) {
            return resultController.handleError(e);
        }
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
    public ArrayList<Mail> getMailByStatus(@RequestParam String status) {
        ArrayList<Mail> ret = new ArrayList<Mail>();
        if (status.equals("Awaiting Request")) {
            mailRepository.findByStatusOrderByDateAsc(status).forEach(ret::add);
        } else {
            mailRepository.findByStatusOrderByDateAsc(status).forEach(ret::add);
            ret.sort((m1, m2) -> m1.getRequest().getRequestedCompletionDate().compareTo(m2.getRequest().getRequestedCompletionDate()));
        }
        return ret;
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

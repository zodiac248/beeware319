package com.panpal.Notification;

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
import com.panpal.Posting.PostingRepository;
import com.panpal.Posting.Posting;

import java.time.LocalDateTime;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/notification")
public class NotificationController {
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private PostingRepository postingRepository;

	@PostMapping
	public String addNewNotification (@RequestBody RequestInfo info) {
		String email = info.getEmail();
		String type = info.getType();
		Integer postingId = info.getPostingId();
		Posting posting = postingRepository.findPostingById(postingId);

		Notification n = new Notification();
		n.setEmail(email);
		n.setType(type);
		n.setPosting(posting);
		n.setDate(posting.getDate());
		notificationRepository.save(n);
		return "Notification Saved";
	}

	@DeleteMapping
	public String deleteNotification (@RequestBody RequestInfo info) {

		Notification n = notificationRepository.findNotificationById(info.getId());

		if (n == null) {
			return "Notification does not exist";
		}

		notificationRepository.delete(n);
		return "Notification Deleted";
	}

	@GetMapping(path="/all")
	public Iterable<Notification> getAllNotification() {
		return notificationRepository.findByOrderByEmailAsc();
	}

	@GetMapping(path="/id")
	public Notification getNotification(@RequestParam Integer id) {
		return notificationRepository.findNotificationById(id);
	}

	@GetMapping
	public Iterable<Notification> getNotificationByEmail(@RequestParam String email) {
		return notificationRepository.findByEmailOrderByDateAsc(email);
	}
}
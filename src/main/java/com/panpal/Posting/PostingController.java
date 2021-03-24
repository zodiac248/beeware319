package com.panpal.Posting;

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
import com.panpal.Topic.TopicRepository;
import com.panpal.Topic.Topic;
import com.panpal.Notification.NotificationRepository;
import com.panpal.Notification.Notification;
import com.panpal.Subscription.SubscriptionRepository;
import com.panpal.Subscription.Subscription;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/posting")
public class PostingController {
	@Autowired
	private PostingRepository postingRepository;
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private NotificationRepository notificationRepository;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@PostMapping
	public Integer addNewPosting (@RequestBody RequestInfo info) {
		String email = info.getEmail();
		Integer topicId = info.getTopicId();
		Topic topic = topicRepository.findTopicById(topicId);
		Date dateObj = new Date();
		String title = info.getTitle();
		String content = info.getContent();

		Posting n = new Posting();
		n.setEmail(email);
		n.setTopic(topic);
		n.setDate(dateObj);
		n.setTitle(title);
		n.setLikes(0);
		n.setContent(content);
		postingRepository.save(n);

		Iterator<Subscription> subsIterator = subscriptionRepository.findByTopicOrderByEmailAsc(topic).iterator();
		while (subsIterator.hasNext()) {
			Subscription sub = subsIterator.next();
			String subEmail = sub.getEmail();
			Notification note = new Notification();
			note.setEmail(subEmail);
			note.setPosting(n);
			note.setDate(dateObj);
			notificationRepository.save(note);
		}

		return n.getId();
	}

	@PutMapping
	public String updatePosting (@RequestBody RequestInfo info) {

		Posting n = postingRepository.findPostingById(info.getId());
		
		if (n == null) {
			return "Posting does not exist";
		}

		String title = info.getTitle();
		if (title != null) {
			n.setTitle(title);
		}
		Integer likes = info.getLikes();
		if (likes != null) {
			n.setLikes(likes);
		}
		String content = info.getContent();
		if (content != null) {
			n.setContent(content);
		}

		postingRepository.save(n);
		return "Posting Updated";
	}

	@PutMapping(path="/like")
	public String likePosting (@RequestBody RequestInfo info) {

		Posting n = postingRepository.findPostingById(info.getId());
		
		if (n == null) {
			return "Posting does not exist";
		}

		n.setLikes(n.getLikes() + 1);
		postingRepository.save(n);
		return "Posting liked";
	}

	@PutMapping(path="/unlike")
	public String unlikePosting (@RequestBody RequestInfo info) {

		Posting n = postingRepository.findPostingById(info.getId());
		
		if (n == null) {
			return "Posting does not exist";
		}

		n.setLikes(n.getLikes() - 1);
		postingRepository.save(n);
		return "Posting unliked";
	}

	@DeleteMapping
	public String deletePosting (@RequestBody RequestInfo info) {
		
		Posting n = postingRepository.findPostingById(info.getId());
		
		if (n == null) {
			return "Posting does not exist";
		}

		postingRepository.delete(n);
		return "Posting Deleted";
	}

	@GetMapping
	public Iterable<Posting> getAllPosting() {
		return postingRepository.findByOrderByEmailAsc();
	}

	@GetMapping(path="/id")
	public Posting getPosting(@RequestParam Integer id) {
		return postingRepository.findPostingById(id);
	}

	@GetMapping(path="/byTopic")
	public Iterable<Posting> getPostingByTopic(@RequestParam Integer topicId) {
		Topic topic = topicRepository.findTopicById(topicId);
		return postingRepository.findByTopicOrderByDateAsc(topic);
	}

	@GetMapping(path="/byEmail")
	public Iterable<Posting> getPostingByEmail(@RequestParam String email) {
		return postingRepository.findByEmailOrderByDateAsc(email);
	}
}

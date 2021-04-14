package com.panpal.Posting;

import com.panpal.Error.InputTooLongException;
import com.panpal.ResultController;
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

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

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

	private ResultController resultController = new ResultController();

	@PostMapping
	public Object addNewPosting (@RequestBody RequestInfo info) {
		try{
			String email = info.getEmail();
			Integer topicId = info.getTopicId();
			Topic topic = topicRepository.findTopicById(topicId);
			LocalDateTime dateObj = LocalDateTime.now();
			String title = info.getTitle();
			String content = info.getContent();
			if (content.length() > 1024 || title.length() > 1024) {
				throw new InputTooLongException();
			}

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
				if (subEmail.equals(email)) {
					continue;
				}
				Notification note = new Notification();
				note.setEmail(subEmail);
				note.setType("posting");
				note.setPosting(n);
				note.setDate(dateObj);
				notificationRepository.save(note);
			}

			return n.getId();
		} catch (Exception e){
			return resultController.handleError(e);
		}
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
		return postingRepository.findByTopicOrderByDateDesc(topic);
	}

    @GetMapping(path="/byEmailSubscriptions")
    public List<Posting> getPostingByEmailSubscription(@RequestParam String email) {
        List<Posting> postings = new ArrayList<Posting>();
        Iterator<Subscription> subsIterator = subscriptionRepository.findByEmailOrderByTopicAsc(email).iterator();
        while (subsIterator.hasNext()) {
            postingRepository.findTop50ByTopicOrderByDateDesc(subsIterator.next().getTopic()).forEach(postings::add);
        }
        postings.sort((p1,p2) -> p2.getDate().compareTo(p1.getDate()));
        return postings;
    }

	@GetMapping(path="/byEmail")
	public Iterable<Posting> getPostingByEmail(@RequestParam String email) {
		return postingRepository.findByEmailOrderByDateDesc(email);
	}
}

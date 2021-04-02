package com.panpal.Topic;

import com.panpal.Error.DuplicateTopicException;
import com.panpal.ResultController;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.panpal.Posting.PostingRepository;
import com.panpal.Posting.Posting;
import com.panpal.Comment.CommentRepository;
import com.panpal.Comment.Comment;
import com.panpal.Notification.NotificationRepository;
import com.panpal.Notification.Notification;
import com.panpal.Subscription.SubscriptionRepository;
import com.panpal.Subscription.Subscription;

import java.util.Iterator;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/topic")
public class TopicController {
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private PostingRepository postingRepository;
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private NotificationRepository notificationRepository;
	private ResultController resultController = new ResultController();

	@PostMapping
	public ResponseEntity<String> addNewTopic (@RequestBody RequestInfo info) {
		try {
			String name = info.getName();

			Topic n = new Topic();
			n.setName(name);
			try {
				topicRepository.save(n);
			} catch (Exception e){
				throw new DuplicateTopicException("the topic with the same name already exists");
			}
			return resultController.handleSuccess("Topic Saved");
		} catch (Exception e) {
			return resultController.handleError(e);

		}

	}

	@PutMapping
	public String updateTopic (@RequestBody RequestInfo info) {

		Topic n = topicRepository.findTopicById(info.getId());
		
		if (n == null) {
			return "Topic does not exist";
		}
		
		String name = info.getName();
		if (name != null) {
			n.setName(name);
		}

		try {
			topicRepository.save(n);
		} catch (Exception e){
			throw new DuplicateTopicException("the topic with the same name already exists");
		}
		return "Topic Updated";
	}

	@DeleteMapping
	public String deleteTopic (@RequestBody RequestInfo info) {
		
		Topic n = topicRepository.findTopicById(info.getId());
		
		if (n == null) {
			return "Topic does not exist";
		}

		deleteTopicCasc(n);
		topicRepository.delete(n);
		return "Topic Deleted";
	}

	@GetMapping
	public Iterable<Topic> getAllTopics() {
		return topicRepository.findByOrderByNameAsc();
	}

	@GetMapping(path="/byId")
	public Topic getTopicById(@RequestParam Integer id) {
		return topicRepository.findTopicById(id);
	}

	private void deleteTopicCasc(Topic topic) {
		Iterator<Subscription> subIterator = subscriptionRepository.findByTopic(topic).iterator();
		while (subIterator.hasNext()) {
			subscriptionRepository.delete(subIterator.next());
		}
		Iterator<Posting> postingIterator = postingRepository.findByTopic(topic).iterator();
		while (postingIterator.hasNext()) {
			Posting posting = postingIterator.next();
			deletePostingCasc(posting);
			postingRepository.delete(posting);
		}
	}

	private void deletePostingCasc(Posting posting) {
		Iterator<Comment> commentIterator = commentRepository.findByPosting(posting).iterator();
		while (commentIterator.hasNext()) {
			commentRepository.delete(commentIterator.next());
		}
		Iterator<Notification> noteIterator = notificationRepository.findByPosting(posting).iterator();
		while (noteIterator.hasNext()) {
			notificationRepository.delete(noteIterator.next());
		}
	}
}

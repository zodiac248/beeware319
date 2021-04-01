package com.panpal.Subscription;

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

@CrossOrigin(origins = "https://beeware319fe.azurewebsites.net")
@RestController
@RequestMapping(path="/subscription")
public class SubscriptionController {
	@Autowired
	private SubscriptionRepository subscriptionRepository;
	@Autowired
	private TopicRepository topicRepository;

	@PostMapping
	public Integer addNewSubscription (@RequestBody RequestInfo info) {
		String email = info.getEmail();
		Integer topicId = info.getTopicId();
		Topic topic = topicRepository.findTopicById(topicId);

		Subscription n = new Subscription();
		n.setEmail(email);
		n.setTopic(topic);
		subscriptionRepository.save(n);
		return n.getId();
	}

	// @PutMapping
	// public String updateSubscription (@RequestBody RequestInfo info) {

	// 	Subscription n = subscriptionRepository.findSubscriptionById(info.getId());
		
	// 	if (n == null) {
	// 		return "Subscription does not exist";
	// 	}
		
	// 	Integer employeeId = info.getEmployeeId();
	// 	User employee = userRepository.findUserById(employeeId);
	// 	if (employee != null) {
	// 		n.setEmployee(employee);
	// 	}
	// 	Integer topicId = info.getTopicId();
	// 	Topic topic = topicRepository.findTopicById(topicId);
	// 	if (topic != null) {
	// 		n.setTopic(topic);
	// 	}

	// 	subscriptionRepository.save(n);
	// 	return "Subscription Updated";
	// }

	@DeleteMapping
	public String deleteSubscription (@RequestBody RequestInfo info) {
		
		Subscription n = subscriptionRepository.findSubscriptionById(info.getId());
		
		if (n == null) {
			return "Subscription does not exist";
		}

		subscriptionRepository.delete(n);
		return "Subscription Deleted";
	}

	@GetMapping(path="/all")
	public Iterable<Subscription> getAllSubscription() {
		return subscriptionRepository.findByOrderByEmailAsc();
	}

	@GetMapping(path="/id")
	public Subscription getSubscription(@RequestParam Integer id) {
		return subscriptionRepository.findSubscriptionById(id);
	}

	@GetMapping
	public Iterable<Subscription> getSubscriptionByEmail(String email) {
		return subscriptionRepository.findByEmailOrderByTopicAsc(email);
	}

	@GetMapping(path="/byTopic")
	public Iterable<Subscription> getSubscriptionByTopic(Integer topicId) {
		Topic topic = topicRepository.findTopicById(topicId);
		return subscriptionRepository.findByTopicOrderByEmailAsc(topic);
	}
}

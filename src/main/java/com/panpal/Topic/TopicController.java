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
import com.panpal.User.User;

@CrossOrigin(origins = "https://beeware319fe.azurewebsites.net")
@RestController
@RequestMapping(path="/topic")
public class TopicController {
	@Autowired
	private TopicRepository topicRepository;
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
}

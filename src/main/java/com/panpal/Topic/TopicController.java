package com.panpal.Topic;

import com.panpal.Error.DuplicateFloorException;
import com.panpal.Error.DuplicateTopicException;
import com.panpal.Error.InputTooLongException;
import com.panpal.ResultController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.panpal.Error.TopicNoLongerExistsException;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
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
				if (e instanceof DataIntegrityViolationException){
					DataIntegrityViolationException a = (DataIntegrityViolationException) e;
					String reason = a.getRootCause().getMessage();
					if (reason.contains("Data too long")) {
						throw new InputTooLongException();
					} else if (reason.contains("Duplicate")){
						throw new DuplicateTopicException("the topic with the same name already exists");
					} else {
						throw e;
					}

				}
			}
			return resultController.handleSuccess("Topic Saved");
		} catch (Exception e) {
			return resultController.handleError(e);

		}

	}

	@PutMapping
	public ResponseEntity<String> updateTopic (@RequestBody RequestInfo info) {

		Topic n = topicRepository.findTopicById(info.getId());
		
		if (n == null) {
			return resultController.handleError(new TopicNoLongerExistsException());
		}
		
		String name = info.getName();
		if (name != null) {
			n.setName(name);
		}

		try {
			topicRepository.save(n);
		} catch (Exception e){
			if (e instanceof DataIntegrityViolationException) {
				DataIntegrityViolationException a = (DataIntegrityViolationException) e;
				String reason = a.getRootCause().getMessage();
				if (reason.contains("Data too long")) {
					return resultController.handleError(new InputTooLongException());
				} else if (reason.contains("Duplicate")) {
					return resultController.handleError(new DuplicateTopicException("the topic with the same name already exists"));
				}else {
					return resultController.handleError(e);
				}
			}
        }  
		return resultController.handleSuccess("Comment Updated");
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

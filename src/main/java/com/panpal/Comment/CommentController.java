package com.panpal.Comment;

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
import com.panpal.Notification.NotificationRepository;
import com.panpal.Notification.Notification;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

@CrossOrigin(origins = "https://beeware319-front.herokuapp.com")
@RestController
@RequestMapping(path="/comment")
public class CommentController {
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private PostingRepository postingRepository;
	@Autowired
	private NotificationRepository notificationRepository;

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@PostMapping
	public String addNewComment (@RequestBody RequestInfo info) {
		String email = info.getEmail();
		Integer postingId = info.getPostingId();
		Posting posting = postingRepository.findPostingById(postingId);
		LocalDateTime dateObj = LocalDateTime.now();
		String content = info.getContent();

		Comment n = new Comment();
		n.setEmail(email);
		n.setPosting(posting);
		n.setDate(dateObj);
		n.setContent(content);
		commentRepository.save(n);

		Notification note = new Notification();
		note.setEmail(posting.getEmail());
		note.setType("comment");
		note.setPosting(posting);
		note.setDate(dateObj);
		notificationRepository.save(note);

		ArrayList<String> commenters = new ArrayList<String>();
		commenters.add(posting.getEmail());
		Iterator<Comment> commentIterator = commentRepository.findByPostingOrderByDateDesc(posting).iterator();
		while (commentIterator.hasNext()) {
			String commentEmail = commentIterator.next().getEmail();
			if (!commenters.contains(commentEmail)) {
				commenters.add(commentEmail);
				note = new Notification();
				note.setEmail(commentEmail);
				note.setType("comment");
				note.setPosting(posting);
				note.setDate(dateObj);
				notificationRepository.save(note);
			}
		}

		return "Comment Saved";
	}

	@PutMapping
	public String updateComment (@RequestBody RequestInfo info) {

		Comment n = commentRepository.findCommentById(info.getId());

		if (n == null) {
			return "Posting does not exist";
		}

		String content = info.getContent();
		if (content != null) {
			n.setContent(content);
		}

		commentRepository.save(n);
		return "Comment Updated";
	}

	@DeleteMapping
	public String deleteComment (@RequestBody RequestInfo info) {

		Comment n = commentRepository.findCommentById(info.getId());

		if (n == null) {
			return "Comment does not exist";
		}

		commentRepository.delete(n);
		return "Comment Deleted";
	}

	@GetMapping(path="/all")
	public Iterable<Comment> getAllComment() {
		return commentRepository.findByOrderByEmailAsc();
	}

	@GetMapping(path="/id")
	public Comment getComment(@RequestParam Integer id) {
		return commentRepository.findCommentById(id);
	}

	@GetMapping
	public Iterable<Comment> getCommentByPosting(@RequestParam Integer postingId) {
		Posting posting = postingRepository.findPostingById(postingId);
		return commentRepository.findByPostingOrderByDateDesc(posting);
	}

	// @GetMapping(path="/byEmployee")
	// public Iterable<Comment> getCommentByEmployee(@RequestParam Integer employeeId) {
	// 	User employee = userRepository.findUserById(employeeId);
	// 	return commentRepository.findByEmployeeOrderByDateAsc(employee);
	// }
}
package com.panpal.User;

import com.panpal.security.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.panpal.RequestInfo;
import java.util.Optional;

@CrossOrigin(origins = "https://beeware319-front.azurewebsites.net")
@RestController
@RequestMapping(path="/user")
public class UserController {
	@Autowired
	private UserRepository userRepository;

	@PostMapping
	// public String addNewUser (@RequestParam String username
	// , @RequestParam String firstName
	// , @RequestParam String lastName
	// , @RequestParam Boolean isAdmin) {
	public String addNewUser (@RequestBody RequestInfo info) {
		String username = info.getUsername();
		String firstName = info.getFirstName();
		String lastName = info.getLastName();
		Boolean isAdmin = info.getIsAdmin();
		String email = info.getEmail();
		String password = info.getPassword();

		User n = new User();
		n.setUsername(username);
		n.setFirstName(firstName);
		n.setLastName(lastName);
		n.setIsAdmin(isAdmin);
		n.setEmail(email);
		n.setPassword(password);
		userRepository.save(n);
		return "User Saved";
	}

	@PutMapping
	// public String updateUser (@RequestParam Integer id
	// , @RequestParam(required = false) String username
	// , @RequestParam(required = false) String firstName
	// , @RequestParam(required = false) String lastName
	// , @RequestParam(required = false) Boolean isAdmin) {
	public String updateUser (@RequestBody RequestInfo info) {
		Integer id = info.getId();
		String username = info.getUsername();
		String firstName = info.getFirstName();
		String lastName = info.getLastName();
		Boolean isAdmin = info.getIsAdmin();
		String email = info.getEmail();
		String password = info.getPassword();

		User n = userRepository.findUserById(id);
		
		if (n == null) {
			return "User does not exist";
		}
		
		if (username != null) {
			n.setUsername(username);
		}
		if (firstName != null) {
			n.setFirstName(firstName);
		}
		if (lastName != null) {
			n.setLastName(lastName);
		}
		if (isAdmin != null) {
			n.setIsAdmin(isAdmin);
		}
		if (email != null) {
			n.setEmail(email);
		}
		if (password != null) {
			n.setPassword(password);
		}
		userRepository.save(n);
		return "User Updated";
	}

	@DeleteMapping
	// public String deleteUser (@RequestParam Integer id) {
	public String deleteUser (@RequestBody RequestInfo info) {
		Integer id = info.getId();
		
		User n = userRepository.findUserById(id);
		
		if (n == null) {
			return "User does not exist";
		}

		userRepository.delete(n);
		return "User Deleted";
	}

	@GetMapping(path="/all")
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping
	public User getUser(@RequestParam Integer id) {
		return userRepository.findUserById(id);
	}


	@GetMapping("/userinfo")
	public Authentication getAccount() {
		return SecurityContextHolder.getContext().getAuthentication();
  	}	
//	@GetMapping
//	public User getUser(@RequestParam Integer id) {
//		return userRepository.findUserById(id);
//	}
}

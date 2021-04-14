package com.panpal.User;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestUser {

	private User user = new User();
		
	@Test
	public void testUsername() {
		String sampleUserName = "derekusername";
		user.setUsername(sampleUserName);
		assertEquals(user.getUsername(), "derekusername");	
	} 

	@Test
	public void testEmail() {
		String sampleEmail = "derekemail@gmail.com";
		user.setEmail(sampleEmail);
		assertEquals(user.getEmail(), sampleEmail);
	}

	@Test
	public void testName() {
		String sampleFirstName = "Derek";
		String sampleLastName = "Yee";
		user.setFirstName(sampleFirstName);
		user.setLastName(sampleLastName);
		assertEquals(user.getFirstName(), sampleFirstName);
		assertEquals(user.getLastName(), sampleLastName);
		assertEquals(user.getName(), sampleFirstName + " " + sampleLastName);
	}

	@Test
	public void testIsAdmin() {
		user.setIsAdmin(true);
		assertTrue(user.getIsAdmin());
	}

	@Test
	public void testPassword() {
		String samplePassword = "derekpassword";
		user.setPassword(samplePassword);
		assertEquals(user.getPassword(), samplePassword);
	}
}

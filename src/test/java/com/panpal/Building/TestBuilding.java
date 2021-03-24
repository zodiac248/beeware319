package com.panpal.Building;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBuilding {

	private Building building = new Building();

	@Test
	public void testName() {
		String sampleName = "ICBC - UBC";
		building.setName(sampleName);
		assertEquals(building.getName(), sampleName);
	}

	@Test
	public void testAddress() {
		String sampleAddress = "1234 beeware street";
		building.setAddress(sampleAddress);
		assertEquals(building.getAddress(), sampleAddress);
	}	
}

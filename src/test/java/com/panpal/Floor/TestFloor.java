package com.panpal.Floor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.panpal.Building.Building;

public class TestFloor {

	private Floor floor = new Floor();

	@Test
	public void testFloorNumber() {
		Integer sampleFloorNumber = 7;
		floor.setFloorNumber(sampleFloorNumber);
		assertEquals(floor.getFloorNumber(), sampleFloorNumber);
	}

	@Test
	public void testBuilding() {
		Building sampleBuilding = new Building();
		sampleBuilding.setName("ICBC - Beeware");
		sampleBuilding.setAddress("Beeware street, Vancouver");

		floor.setBuilding(sampleBuilding);
		assertEquals(floor.getBuilding(), sampleBuilding);
	}
}	

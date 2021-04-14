package com.panpal.Desk;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.panpal.Floor.Floor;
import com.panpal.Building.Building;

public class TestDesk {
	
	private Desk desk = new Desk();

	@Test
	public void testDeskNumber() {
		String sampleDeskNumber = "010";
		desk.setDeskNumber(sampleDeskNumber);
		assertEquals(desk.getDeskNumber(), sampleDeskNumber);
	}  	

	@Test
	public void testFloor() {
		Floor sampleFloor = new Floor();
		Building sampleBuilding = new Building();
		
		sampleFloor.setFloorNumber(10);
		sampleBuilding.setName("ICBC - Beeware");
		sampleBuilding.setAddress("Beeware street, vancouver");
		sampleFloor.setBuilding(sampleBuilding);

		desk.setFloor(sampleFloor);
		assertEquals(desk.getFloor(), sampleFloor);
	}
}

// package com.panpal.Booking;

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.assertEquals;

// import com.panpal.Desk.Desk;
// import com.panpal.User.User;
// import com.panpal.Floor.Floor;

// import java.util.Date;

// public class TestBooking {

// 	private Booking booking = new Booking();

// 	@Test
// 	public void testDesk() {
// 		Desk sampleDesk = new Desk();
// 		sampleDesk.setFloor(new Floor());
// 		sampleDesk.setDeskNumber("010");
		
// 		booking.setDesk(sampleDesk);		
// 		assertEquals(booking.getDesk(), sampleDesk);
// 	} 

// 	@Test
// 	public void testEmployee() {
// 		User sampleEmployee = new User();
// 		sampleEmployee.setFirstName("Derek");
// 		sampleEmployee.setLastName("Yee");
// 		sampleEmployee.setUsername("derekusername");
// 		sampleEmployee.setPassword("derekpassword");
// 		sampleEmployee.setEmail("derekemail");
// 		sampleEmployee.setIsAdmin(true);

// 		booking.setEmployee(sampleEmployee);
// 		assertEquals(booking.getEmployee(), sampleEmployee);
// 	}

// 	@Test
// 	public void testDate() {
// 		Date sampleDate = new Date(2021, 03, 14);
		
// 		booking.setDate(sampleDate);
// 		assertEquals(booking.getDate(), sampleDate);	
// 	}	
// }

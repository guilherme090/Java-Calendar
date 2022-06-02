package db;

import org.junit.Test;

import appointments.Appointment;

import static org.junit.Assert.assertEquals;

public class DataStructureTest {
	@Test
	public void createAppointment() {
		Appointment appointmentTest = new Appointment();
		appointmentTest.setDate("20000101");
		appointmentTest.setDescription("My Appointment Test");
		appointmentTest.setTime("0100", "0230");
		assertEquals("20000101", appointmentTest.getDate());
		assertEquals("0100", appointmentTest.getStartTime());
		assertEquals("0230", appointmentTest.getEndTime());
		appointmentTest = null;
	}
	@Test
	public void createAnotherAppointment() {
		Appointment appointmentTest = new Appointment();
		appointmentTest.setDate("20000103");
		appointmentTest.setDescription("My Other Appointment Test");
		appointmentTest.setTime("0110", "0130");
		assertEquals("20000103", appointmentTest.getDate());
		assertEquals("0110", appointmentTest.getStartTime());
		assertEquals("0130", appointmentTest.getEndTime());
		appointmentTest = null;
	}
	
}
package com.cpung.data;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.cpung.beans.Rental;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class RentalDAOTest {
//	private static final String URL = "jdbc:mysql://localhost:3306/car_rentals?serverTimezone=UTC";
//	private static final String USER = "root";
//	private static final String PASSWORD = "root";
//	Connection connection;
	
	
	//before test
	//execute sql script
	
	
//	@BeforeClass
//	public void createConnection() {
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			connection = DriverManager.getConnection(URL, USER, PASSWORD);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//	
//	@BeforeClass
//	public void dropTable() {
//		try{
//			String sql = "drop table rentals";
//			PreparedStatement stmt = connection.prepareStatement(sql);
//			stmt.execute();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
//	@AfterClass
//	public void dropTableAfter() {
//		try{
//			String sql = "drop table rentals";
//			PreparedStatement stmt = connection.prepareStatement(sql);
//			stmt.execute();
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//	
	
	@Test
	public void getAllRentals() {
		
	List<Rental> rentals;
		try(RentalDAO dao = new RentalDAO()){
			rentals = dao.getAllRentals();
			assertNotNull(rentals);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void insertIntoRental() {
		Rental rental = new Rental(1,"Lexus", "LX", "2021-07-06", "2020-09-12", "Georgia", "Minnesota");

		try(RentalDAO dao = new RentalDAO()){
			
			assertEquals(dao.addRental(rental,1), rental);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void insertIntoRentalNoPrimaryKey() {
		Rental rental = new Rental(1,"Lexus", "LX", "2021-07-06", "2020-09-12", "Georgia", "Minnesota");

		try(RentalDAO dao = new RentalDAO()){
			
			assertEquals(dao.addRental(rental,1), rental);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void updateRental() {
		try(RentalDAO dao = new RentalDAO()){
			Rental rental = new Rental(2,"Lexus", "LX", "2021-07-06", "2020-09-12", "Georgia", "Minnesota");
			assertTrue(dao.updateRental(rental));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void revertUpdate() {
		try(RentalDAO dao = new RentalDAO()){
			dao.updateRental(new Rental(2, "Chevrolet", "G-Series G10", "2020-11-30", "2020-11-10", "Georgia", "Mississippi"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@After
	public void deleteRental() {
		try(RentalDAO dao = new RentalDAO()){
			assertTrue(dao.removeRental(1));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
	
	//after test
	//drop table
}


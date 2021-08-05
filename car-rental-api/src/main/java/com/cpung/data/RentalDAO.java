package com.cpung.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.cpung.beans.Rental;

public class RentalDAO implements AutoCloseable {
	
	private static final String URL = "jdbc:mysql://localhost:3306/car_rentals?serverTimezone=UTC";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	
	public RentalDAO() {
		connect();
	}
	
	private static Connection connection;
	
	
	public static  void connect() {
		
		
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public Rental addRental(Rental rental) throws SQLException{
		
		try {
			String sql = "insert into rentals (Make, Model, rent_date, return_date, rent_place, return_place) values ( ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, rental.getMake());
			stmt.setString(2, rental.getModel());
			stmt.setDate(3,  rental.getRentDate() != "" ? Date.valueOf(rental.getRentDate()) : Date.valueOf("2000-01-01"));
			stmt.setDate(4,  rental.getReturnDate() != "" ? Date.valueOf(rental.getReturnDate()) : Date.valueOf("2000-01-01") );
			stmt.setString(5, rental.getRentPlace());
			stmt.setString(6, rental.getReturnPlace());
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			rental.setId(rs.getInt(1));
			return rental;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;

		
		
	}
public Rental addRental(Rental rental, int rentalID) throws SQLException{
		
		try {
			String sql = "insert into rentals values(?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, rentalID);
			stmt.setString(2, rental.getMake());
			stmt.setString(3, rental.getModel());
			stmt.setString(4,  rental.getRentDate());
			stmt.setString(5,  rental.getReturnDate());
			stmt.setString(6, rental.getRentPlace());
			stmt.setString(7, rental.getReturnPlace());
			return rental;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		return null;

		
		
	}
	
	public boolean removeRental(int rentalId) throws SQLException{
		
		try {
			String sql = "delete from rentals where id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, rentalId);
			stmt.execute();
			return true;
		}catch(SQLException e) {
			connection.rollback();
		}
		
		return false;
	
	}
	
	public boolean updateRental(Rental rental) throws SQLException{
		
		try {
			String sql = "UPDATE rentals SET Make = ?, Model = ?, rent_date = ?, return_date = ?, rent_place = ?, return_place = ? WHERE (id = ?)";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, rental.getMake());
			stmt.setString(2, rental.getModel());
			stmt.setDate(3, Date.valueOf(rental.getRentDate()));
			stmt.setDate(4, Date.valueOf(rental.getReturnDate()));
			stmt.setString(5, rental.getRentPlace());
			stmt.setString(6, rental.getReturnPlace());
			stmt.setInt(7, rental.getId());
			stmt.executeUpdate();
			return true;
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	public List<Rental> getAllRentals() {
		List<Rental> rentals = new ArrayList<>();
		try {
			String sql = "select * from rentals";
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			
			while(rs.next()) {
				int id = rs.getInt("id");
				String make = rs.getString("Make");
				String model = rs.getString("Model");
				String rentDate = rs.getString("rent_date");
				String returnDate = rs.getString("return_date");
				String rentPlace = rs.getString("rent_place");
				String returnPlace = rs.getString("return_place");
				Rental rental = new Rental(id, make, model, rentDate, returnDate, rentPlace, returnPlace);
				rentals.add(rental);
			}
			return rentals;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList();
		
	}
	
	public Rental getARental(int rentalId) {
		Rental rental;
		try {
			String sql = "select * from rentals where id = ?";
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, rentalId);
			ResultSet rs = stmt.executeQuery();
			rs.next();
				int id = rs.getInt("id");
				String make = rs.getString("Make");
				String model = rs.getString("Model");
				String rentDate = rs.getString("rent_date");
				String returnDate = rs.getString("return_date");
				String rentPlace = rs.getString("rent_place");
				String returnPlace = rs.getString("return_place");
				rental = new Rental(id, make, model, rentDate, returnDate, rentPlace, returnPlace);
				
			return rental;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	public void close() throws Exception {
		if(connection != null && !connection.isClosed()) {
			connection.close();			
		}

		
	}
	

	
}

package com.cpung.beans;

import java.sql.Date;

public class Rental {

	private int id;
	private String make;
	private String model;
	private String rentDate;
	private String returnDate;
	private String rentPlace;
	private String returnPlace;
	
	
	
	public Rental() {
		super();
	}

	public Rental(String make, String model, String rentDate, String returnDate, String rentPlace, String returnPlace) {
		super();
		this.make = make;
		this.model = model;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.rentPlace = rentPlace;
		this.returnPlace = returnPlace;
	}
	
	public Rental(int id, String make, String model, String rentDate, String returnDate, String rentPlace,
			String returnPlace) { //converted from Date to String to make it easier to type
		super();
		this.id = id;
		this.make = make;
		this.model = model;
		this.rentDate = rentDate;
		this.returnDate = returnDate;
		this.rentPlace = rentPlace;
		this.returnPlace = returnPlace;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMake() {
		return make;
	}
	public void setMake(String make) {
		this.make = make;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getRentDate() {
		return rentDate;
	}
	public void setRentDate(String rentDate) {
		this.rentDate = rentDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(String returnDate) {
		this.returnDate = returnDate;
	}
	public String getRentPlace() {
		return rentPlace;
	}
	public void setRentPlace(String rentPlace) {
		this.rentPlace = rentPlace;
	}
	public String getReturnPlace() {
		return returnPlace;
	}
	public void setReturnPlace(String returnPlace) {
		this.returnPlace = returnPlace;
	}
	@Override
	public String toString() {
		return "Rental [id=" + id + ", make=" + make + ", model=" + model + ", rentDate=" + rentDate + ", returnDate="
				+ returnDate + ", rentPlace=" + rentPlace + ", returnPlace=" + returnPlace + "]";
	}

	
	
	
	
}

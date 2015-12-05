package edu.umn.csci5801;
import java.util.Date;

public class People {
	private String firstName;
	private String lastName;
	private int	   id;
	private String gender;
	private int    age;
	private String phoneNumber;
	private String address;
	private Date   dateOfBirth;
	private String x500Account;
	private String loginToken;
	private String role;
	
	public void updateFirstName(String newFirstName) {
		firstName = newFirstName;
		// TODO Update the database
	}
	
	public void updateLastName(String newLastName) {
		lastName = newLastName;
	}
	
	public void updateID(int newID) {
		id = newID;
	}
	
	public void updateDateOfBirth(Date newDateOfBirth) {
		dateOfBirth = newDateOfBirth;
	}
	
	public void updateGender(String newGender) {
		gender = newGender;
	}
	
	public void updateAge(int newAge) {
		age = newAge;
	}	

	public void updatePhoneNumber(String newPhoneNumber) {
		phoneNumber = newPhoneNumber;
	}
	
	public void updateAddress(String newAddress) {
		address = newAddress;
	}
	
	public void updateX500Account(String newX500Account) {
		x500Account = newX500Account;
	}
	
	public void updateLoginToken(String newLoginToken) {
		loginToken = newLoginToken;
	}
	
	public void updateRole(String newRole) {
		role = newRole;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public int getID() {
		return id;
	}
	
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public String getGender() {
		return gender;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getX500Account() {
		return x500Account;
	}
	
	public String getLoginToken() {
		return loginToken;
	}
	
	public String getRole() {
		return role;
	}
};


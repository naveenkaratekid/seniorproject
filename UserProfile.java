package com.cogswell.seniorproject;


/*
 * this class exists because this will return back to the UI details about the user's profile.
 * This is primarily used in the update profile section.
 * 
 * */
public class UserProfile 
{
	private static String username, firstName, lastName, password, email, address;
	int zipcode;
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		UserProfile.username = username;
	}
	public static String getFirstName() {
		return firstName;
	}
	public static void setFirstName(String firstName) {
		UserProfile.firstName = firstName;
	}
	public static String getLastName() {
		return lastName;
	}
	public static void setLastName(String lastName) {
		UserProfile.lastName = lastName;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		UserProfile.password = password;
	}
	public static String getEmail() {
		return email;
	}
	public static void setEmail(String email) {
		UserProfile.email = email;
	}
	public static String getAddress() {
		return address;
	}
	public static void setAddress(String address) {
		UserProfile.address = address;
	}
	public int getZipcode() {
		return zipcode;
	}
	public void setZipcode(int zipcode) {
		this.zipcode = zipcode;
	}


}

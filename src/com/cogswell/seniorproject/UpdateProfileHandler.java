package com.cogswell.seniorproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class UpdateProfileHandler implements EventHandler<ActionEvent> 
{

	TextField username, firstName, lastName, password, email, address, zipcode;
	SearchDAO db = null;
	Text usernameLogin;
	
	public TextField getUsername() {
		return username;
	}

	public void setUsername(TextField username) {
		this.username = username;
	}

	public TextField getFirstName() {
		return firstName;
	}

	public void setFirstName(TextField firstName) {
		this.firstName = firstName;
	}

	public TextField getLastName() {
		return lastName;
	}

	public void setLastName(TextField lastName) {
		this.lastName = lastName;
	}

	public TextField getPassword() {
		return password;
	}

	public void setPassword(TextField password) {
		this.password = password;
	}

	public TextField getEmail() {
		return email;
	}

	public void setEmail(TextField email) {
		this.email = email;
	}

	public TextField getAddress() {
		return address;
	}

	public void setAddress(TextField address) {
		this.address = address;
	}

	public TextField getZipcode() {
		return zipcode;
	}

	public void setZipcode(TextField zipcode) {
		this.zipcode = zipcode;
	}

	public Text getUserNameLogin() {
		return usernameLogin;
	}

	public void setUserNameLogin(Text userNameLogin) {
		this.usernameLogin = userNameLogin;
	}

	public UpdateProfileHandler(TextField username, TextField firstName, TextField lastName, TextField password, TextField zipcode, TextField email, TextField address, Text usernameLogin)
	{
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.zipcode = zipcode;
		this.email = email;
		this.address = address;
		this.usernameLogin = usernameLogin;
	    System.out.println(username.getText() + " " + firstName.getText() + " " + lastName.getText() + " " + password.getText() + " " + zipcode.getText() + " " + email.getText() + " " + address.getText());

		
		db = new SearchDAO();
	}
	
	public UpdateProfileHandler()
	{
		db = new SearchDAO();
	}
	
	

	@Override
	public void handle(ActionEvent ae) 
	{
		
		//System.out.println("Password: " + pw.getText());
			db.setUsername(username.getText());
			db.setFirstName(firstName.getText());
			db.setPassword(password.getText());
			db.setLastName(lastName.getText());
			db.setZipcode(Integer.parseInt(zipcode.getText()));
			if(email.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$"))
			{
				db.setEmail(email.getText());
			}
			else
			{
				System.out.println("Invalid email address");
			}
			//db.setEmail(emailField.getText());
			db.setAddress(address.getText());
			
			try
			{
			    System.out.println("Updating");
			    System.out.println(getFirstName() + " " + getLastName() + " " + getPassword() + " " + getZipcode() + " " + getEmail() + " " + getAddress());
			    db.updateUserProfile(username.getText(), firstName.getText(), lastName.getText(), password.getText(), Integer.parseInt(zipcode.getText()), email.getText(), address.getText());
                      
			}
			catch(Exception e)
			{
				e.printStackTrace();
				// create popup with error
			}
			usernameLogin.setText(firstName.getText() + " " + lastName.getText());
	}

}

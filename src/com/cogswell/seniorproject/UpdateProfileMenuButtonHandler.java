package com.cogswell.seniorproject;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UpdateProfileMenuButtonHandler implements EventHandler<ActionEvent> {

	SearchDAO db = null;
	TextField username, firstName, lastName, password, email, address, zipcode;
	Text usernameLogin;
	PasswordField pwBox;
	
	public Text getUsernameLogin() {
		return usernameLogin;
	}


	public void setUsernameLogin(Text usernameLogin) {
		this.usernameLogin = usernameLogin;
	}


	public PasswordField getPwBox() {
		return pwBox;
	}


	public void setPwBox(PasswordField pwBox) {
		this.pwBox = pwBox;
	}


	public UpdateProfileMenuButtonHandler(TextField username, TextField firstName, TextField lastName, TextField password, TextField email, TextField address, TextField zipcode, PasswordField pwBox, Text usernameLogin) 
	{
		// TODO Auto-generated constructor stub
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.zipcode = zipcode;
		this.email = email;
		this.address = address;
		this.pwBox = pwBox;
		this.usernameLogin = usernameLogin;
		db = new SearchDAO();
	}
	
	
	public UpdateProfileMenuButtonHandler() 
	{
		db = new SearchDAO();
	}
	
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


	@Override
	public void handle(ActionEvent event) 
	{
		System.out.println(username.getText());
		UserProfile up = db.getUserProfileDetails(username.getText());
		Stage s2 = new Stage();
		s2.setResizable(false);
		s2.setTitle("Update Profile");
		GridPane gp = new GridPane();
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(20);
		gp.setVgap(20);
		gp.setPadding(new Insets(25,25,25,25));
		Text updateProfileText = new Text("Update Profile");
		updateProfileText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
		gp.add(updateProfileText,0,0,2,1);
 
		PasswordField pw = new PasswordField(); // password
		pw.setText(up.getPassword());
 
		TextField firstNameField = new TextField(); // first name
		firstNameField.setText(up.getFirstName());

		TextField lastNameField = new TextField(); // last name
		lastNameField.setText(up.getLastName());
 
		TextField zipcodeField = new TextField(); // zipcode
		zipcodeField.setText(Integer.toString(up.getZipcode()));
 
		TextField emailField = new TextField(); // email
		emailField.setText(up.getEmail());
 
		TextField addressField = new TextField(); // address
		addressField.setText(up.getAddress());

		Label password = new Label("Password");
		pw.setPromptText("Password");
		gp.add(pw, 1,2);
		gp.add(password, 0,2);
 
		Label firstName = new Label("First Name");
		firstNameField.setPromptText("First Name");
		gp.add(firstNameField, 1,3);
		gp.add(firstName, 0,3);
 
		Label lastName = new Label("Last Name");
		lastNameField.setPromptText("Last Name");
		gp.add(lastNameField, 1,4);
		gp.add(lastName, 0,4);
 
		Label zipcode = new Label("Zipcode");
		zipcodeField.setPromptText("Zipcode");
		gp.add(zipcodeField, 1,5);
		gp.add(zipcode, 0,5);
		String code = zipcodeField.getText();

		if(code.matches("[0-9]*") )
		{
			if(zipcodeField.getLength() > 5)
			{
				Integer.parseInt(zipcodeField.getText());
			}	
		}	
		else
		{
			System.out.println("Zipcode must be numbers and not be longer than 5 digits");    
		}	
 
		Label email = new Label("Email");
		emailField.setPromptText("Email Address");
		gp.add(emailField, 1,6);
		gp.add(email, 0,6);
 
		Label address = new Label("Address");
		addressField.setPromptText("Home Address");
		gp.add(addressField, 1,7);
		gp.add(address, 0,7);
 
		Button updateProf = new Button("Update");
		updateProf.setTranslateX(175);
		updateProf.setTranslateY(-10);
		gp.add(updateProf, 0, 0);
		System.out.println(lastNameField.getText());
		UpdateProfileHandler uph = new UpdateProfileHandler(username, firstNameField, lastNameField, pw, zipcodeField, emailField, addressField, usernameLogin);
 
		updateProf.setOnAction(uph);
		//usernameLogin.setText(firstNameField.getText() + " " + lastNameField.getText());
 
		Scene scene = new Scene(gp);
		s2.setScene(scene);
		s2.show();
		
		// TODO Auto-generated method stub
		
		
		
	}

}

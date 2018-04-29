package com.cogswell.seniorproject;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SignUpHandler implements EventHandler<ActionEvent> {

	SearchDAO db = null;
	PasswordField pwBox;
	TextField un, fn, ln, zp, em, ad, pwd;
	Text usernameLogin;
	Button login, updateProfileMenuButton, loginOrEnroll;
	Stage s1, s2;
	
	public SignUpHandler() {
		
		db = new SearchDAO();
	}
	
	public SignUpHandler(TextField un, PasswordField pwBox, TextField fn, TextField ln, TextField zp, TextField em, TextField ad, TextField pwd, Text usernameLogin, Button login, Button updateProfileMenuButton, Button loginOrEnroll, Stage s1, Stage s2) {
		
		this.un = un;
		this.pwBox = pwBox;
		this.fn = fn;
		this.ln = ln;
		this.zp = zp;
		this.em = em;
		this.ad = ad;
		this.pwd = pwd;
		this.usernameLogin = usernameLogin;
		this.login = login;
		this.updateProfileMenuButton = updateProfileMenuButton;
		this.loginOrEnroll = loginOrEnroll;
		this.s1 = s1;
		this.s2 = s2;
		
		
		db = new SearchDAO();
	}
	
	public PasswordField getPwBox() {
		return pwBox;
	}

	public void setPwBox(PasswordField pwBox) {
		this.pwBox = pwBox;
	}

	public TextField getUn() {
		return un;
	}

	public void setUn(TextField un) {
		this.un = un;
	}

	public TextField getFn() {
		return fn;
	}

	public void setFn(TextField fn) {
		this.fn = fn;
	}

	public TextField getLn() {
		return ln;
	}

	public void setLn(TextField ln) {
		this.ln = ln;
	}

	public TextField getZp() {
		return zp;
	}

	public void setZp(TextField zp) {
		this.zp = zp;
	}

	public TextField getEm() {
		return em;
	}

	public void setEm(TextField em) {
		this.em = em;
	}

	public TextField getAd() {
		return ad;
	}

	public void setAd(TextField ad) {
		this.ad = ad;
	}

	public TextField getPwd() {
		return pwd;
	}

	public void setPwd(TextField pwd) {
		this.pwd = pwd;
	}

	public Text getUsernameLogin() {
		return usernameLogin;
	}

	public void setUsernameLogin(Text usernameLogin) {
		this.usernameLogin = usernameLogin;
	}

	public Button getLogin() {
		return login;
	}

	public void setLogin(Button login) {
		this.login = login;
	}

	public Button getUpdateProfileMenuButton() {
		return updateProfileMenuButton;
	}

	public void setUpdateProfileMenuButton(Button updateProfileMenuButton) {
		this.updateProfileMenuButton = updateProfileMenuButton;
	}

	public Button getLoginOrEnroll() {
		return loginOrEnroll;
	}

	public void setLoginOrEnroll(Button loginOrEnroll) {
		this.loginOrEnroll = loginOrEnroll;
	}

	public Stage getS1() {
		return s1;
	}

	public void setS1(Stage s1) {
		this.s1 = s1;
	}

	public Stage getS2() {
		return s2;
	}

	public void setS2(Stage s2) {
		this.s2 = s2;
	}

	@Override
	public void handle(ActionEvent event) 
	{
		

        /*db.setUsername(un.getText());
        db.setPassword(pwBox.getText());
        db.setFirstName(fn.getText());
        db.setLastName(ln.getText());
        db.setZipcode(Integer.parseInt(zp.getText()));
        if(em.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[A-Z][a-z]{2,6}$"))
			{
				db.setEmail(em.getText());
			}
			else
			{
				System.out.println("Invalid email address");
			}
        db.setAddress(ad.getText());*/
        //try
        //{
            	try
    			{
            		db.setUsername(un.getText());
            		db.setPassword(pwBox.getText());
            		db.setFirstName(fn.getText());
            		db.setLastName(ln.getText());
            		db.setZipcode(Integer.parseInt(zp.getText()));
            		if(em.getText().matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[A-Z][a-z]{2,6}$"))
            		{
        				db.setEmail(em.getText());
        			}
        			else
        			{
        				System.out.println("Invalid email address");
        			}
            		db.setAddress(ad.getText());
            		
            		System.out.println(un.getText() + " " +  fn.getText() + " " +  ln.getText() + " " + pwBox.getText() + " " + Integer.parseInt(zp.getText()) + " " + em.getText() + " " + ad.getText());
        			
            		db.storeUserProfile(un.getText(), fn.getText(), ln.getText(), pwBox.getText(), Integer.parseInt(zp.getText()), em.getText(), ad.getText());
        			login.setVisible(false);
        			usernameLogin.setText(fn.getText() + " " + ln.getText());
        			String data = db.getUserProfile(un.getText(), pwBox.getText());
        			
        			usernameLogin.setText(data);
                
                	UpdateProfileMenuButtonHandler upmh = new UpdateProfileMenuButtonHandler(un, fn, ln, pwd, em, ad, zp, pwBox, usernameLogin);
                	updateProfileMenuButton.setOnAction(upmh);
                	updateProfileMenuButton.setVisible(true);
         	   
         	   	loginOrEnroll.setVisible(false);
         	   	s2.close();
         	   	s1.close();
    			}
    			catch (Exception e)
    			{
    				e.printStackTrace();
    			}
                /*usernameLogin.setText(fn.getText() + " " + ln.getText());
                String data = db.getUserProfile(un.getText(), pwBox.getText());
                
                usernameLogin.setText(data);
                
                UpdateProfileMenuButtonHandler upmh = new UpdateProfileMenuButtonHandler(un, fn, ln, pwd, em, ad, zp, pwBox, usernameLogin);
                updateProfileMenuButton.setOnAction(upmh);
                updateProfileMenuButton.setVisible(true);
         	   
         	   loginOrEnroll.setVisible(false);
                s2.close();
                s1.close();*/          
        //}
        //catch (Exception e)
        //{
            	
        //}
	}
}

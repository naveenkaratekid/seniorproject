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

public class EnrollButtonHandler implements EventHandler<ActionEvent> 
{
	Stage s1, s2;
	
	TextField un, pwd, fn, ln, zp, em, ad;
    Text usernameLogin;
    Button login, updateProfileMenuButton, loginOrEnroll;
    PasswordField pwBox;
    
    public PasswordField getPwBox() {
		return pwBox;
	}

	public void setPwBox(PasswordField pwBox) {
		this.pwBox = pwBox;
	}

	SearchDAO db = null;
    public EnrollButtonHandler() {
    		db = new SearchDAO();
		// TODO Auto-generated constructor stub
	}
	
    public EnrollButtonHandler(TextField un, TextField pwd, TextField fn, TextField ln, TextField zp, TextField em, TextField ad, Stage s1, Stage s2, Button updateProfileMenuButton, Button login, Button loginOrEnroll, PasswordField pwBox) 
    {
    		this.un = un;
    		this.pwd = pwd;
    		this.fn = fn;
    		this.ln = ln;
    		this.zp = zp;
    		this.em = em;
    		this.ad = ad;
    		this.s1 = s1;
    		this.s2 = s2;
    		this.updateProfileMenuButton = updateProfileMenuButton;
    		this.login = login;
    		this.loginOrEnroll = loginOrEnroll;
    		this.updateProfileMenuButton = updateProfileMenuButton;
    		this.pwBox = pwBox;
    		db = new SearchDAO();
    		
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

	public TextField getUn() {
		return un;
	}

	public void setUn(TextField un) {
		this.un = un;
	}

	public TextField getPwd() {
		return pwd;
	}

	public void setPwd(TextField pwd) {
		this.pwd = pwd;
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

	@Override
	public void handle(ActionEvent event) 
	{
        Stage s2 = new Stage();
        s2.setTitle("Sign Up");

        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(20);
        gp.setVgap(20);
        gp.setPadding(new Insets(25,25,25,25));
        
        Text t = new Text("Sign Up");
        t.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        gp.add(t,0,0,2,1);
        
        Label userName = new Label("Username");
        un.setPromptText("Username");
        gp.add(userName, 0,1);
        gp.add(un, 1,1);
        
        Label password = new Label("Password");
        pwd.setPromptText("Password");
        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Password");
        gp.add(pwBox, 1,2);
        gp.add(password, 0,2);
        
        Label firstName = new Label("First Name");
        fn.setPromptText("First Name");
        gp.add(fn, 1,3);
        gp.add(firstName, 0,3);
        
        Label lastName = new Label("Last Name");
        ln.setPromptText("Last Name");
        gp.add(ln, 1,4);
        gp.add(lastName, 0,4);
        
        Label zipcode = new Label("Zipcode");
        zp.setPromptText("Zipcode");
        gp.add(zp, 1,5);
        gp.add(zipcode, 0,5);
        String code = zp.getText();
        if(code.matches("[0-9]*") )
        {
            if(zp.getLength() > 5)
            {
                Integer.parseInt(zp.getText());
            }
        }
        else
        {
            System.out.println("Zipcode must be numbers and not be longer than 5 digits");    
        }

        Label email = new Label("Email");
        em.setPromptText("Email Address");
        gp.add(em, 1,6);
        gp.add(email, 0,6);
        
        Label address = new Label("Address");
        ad.setPromptText("Home Address");
        gp.add(ad, 1,7);
        gp.add(address, 0,7);

        Button signUp = new Button("Sign Up");
        signUp.setTranslateX(275);
        signUp.setTranslateY(375);
        SignUpHandler suh = new SignUpHandler(un, pwBox, fn, ln, zp, em, ad, pwd, usernameLogin, login, updateProfileMenuButton, loginOrEnroll, s1, s2);
        signUp.setOnAction(suh);
        s2.close();
        gp.add(signUp, 0, 0);
        login.setVisible(false);
        s2.setWidth(500);
        s2.setHeight(500);
        Scene scene = new Scene(gp);
        s2.setScene(scene);
        s2.show();
    
		
	}

}

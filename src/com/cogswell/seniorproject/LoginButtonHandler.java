package com.cogswell.seniorproject;

import java.util.ArrayList;
import java.util.Set;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginButtonHandler implements EventHandler<ActionEvent> {

	SearchDAO db = null;
	TextField username;
	Text usernameLogin;
	PasswordField pwBox;
	MenuButton history;
	TextField searchField;
	TextField zipCodeOrCity;
	MenuButton ratingMenu, price;
	CheckBox googlePlaces, yelp, foursquare;
	Set<String> filters;
	Stage s1;
	Button updateProfileMenuButton;
	
	public Button getUpdateProfileMenuButton() {
		return updateProfileMenuButton;
	}

	public void setUpdateProfileMenuButton(Button updateProfileMenuButton) {
		this.updateProfileMenuButton = updateProfileMenuButton;
	}

	public Stage getS1() {
		return s1;
	}

	public void setS1(Stage s1) {
		this.s1 = s1;
	}

	public LoginButtonHandler(TextField username, Text usernameLogin, PasswordField pwBox, MenuButton history, TextField searchField, TextField zipCodeOrCity, MenuButton ratingMenu, MenuButton price, CheckBox googlePlaces, CheckBox yelp, CheckBox foursquare, Set<String> filters, Stage s1, Button updateProfileMenuButton)
	{
		this.username = username;
		this.usernameLogin = usernameLogin;
		this.pwBox = pwBox;
		this.history = history;
		this.searchField = searchField;
		this.zipCodeOrCity = zipCodeOrCity;
		this.ratingMenu = ratingMenu;
		this.price = price;
		this.googlePlaces = googlePlaces;
		this.yelp = yelp;
		this.foursquare = foursquare;
		this.filters = filters;
		this.s1 = s1;
		this.updateProfileMenuButton = updateProfileMenuButton;
		db = new SearchDAO();
		// TODO Auto-generated constructor stub
	}
	
	public LoginButtonHandler() {
		db = new SearchDAO();
		// TODO Auto-generated constructor stub
	}
	
	public Set<String> getFilters() {
		return filters;
	}

	public void setFilters(Set<String> filters) {
		this.filters = filters;
	}
	
	public TextField getUsername() {
		return username;
	}

	public void setUsername(TextField username) {
		this.username = username;
	}

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

	public MenuButton getHistory() {
		return history;
	}

	public void setHistory(MenuButton history) {
		this.history = history;
	}

	public TextField getSearchField() {
		return searchField;
	}

	public void setSearchField(TextField searchField) {
		this.searchField = searchField;
	}

	public TextField getZipCodeOrCity() {
		return zipCodeOrCity;
	}

	public void setZipCodeOrCity(TextField zipCodeOrCity) {
		this.zipCodeOrCity = zipCodeOrCity;
	}

	public MenuButton getRatingMenu() {
		return ratingMenu;
	}

	public void setRatingMenu(MenuButton ratingMenu) {
		this.ratingMenu = ratingMenu;
	}

	public MenuButton getPrice() {
		return price;
	}

	public void setPrice(MenuButton price) {
		this.price = price;
	}

	public CheckBox getGooglePlaces() {
		return googlePlaces;
	}

	public void setGooglePlaces(CheckBox googlePlaces) {
		this.googlePlaces = googlePlaces;
	}

	public CheckBox getYelp() {
		return yelp;
	}

	public void setYelp(CheckBox yelp) {
		this.yelp = yelp;
	}

	public CheckBox getFoursquare() {
		return foursquare;
	}

	public void setFoursquare(CheckBox foursquare) {
		this.foursquare = foursquare;
	}

	@Override
	public void handle(ActionEvent event) 
	{
		// TODO Auto-generated method stub
        String data = db.getUserProfile(username.getText(), pwBox.getText());
        if(data != null)
        {
        	   usernameLogin.setText(data);
        	   db.setUsername(username.getText());
        	   
        	   ArrayList<String> historyList = db.getUserSearchHistory(db.getUsername());
        	   history.setVisible(true);
   			
        	   for(String s1: historyList)
        	   {
        		   	MenuItem item = new MenuItem();
        	        item.setText(s1);
        	        item.setStyle("	 -fx-font-size: 15pt;\n" +
        	        		"}");
      			history.getItems().addAll(item);
      			String[] strArray = s1.split("\\| ");
          		item.setOnAction(new EventHandler<ActionEvent>()
   				{
          			public void handle(ActionEvent ae)
          			{
          				System.out.println(strArray[0]);
          				System.out.println(strArray[1]);
          				System.out.println(strArray[2]);
          				System.out.println(strArray[3]);
          				System.out.println(strArray[4]);
          				
          				searchField.setText(strArray[0]);
          				zipCodeOrCity.setText(strArray[1]);
          				ratingMenu.setText(strArray[2]);
          				price.setText(strArray[4]);
          				String strArray3 = strArray[3].replaceAll("\\[|\\,|\\]", "");
          				System.out.println(strArray3);
          				if(strArray3.contains("Google") || strArray3.contains("google places"))
          				{
          					googlePlaces.setSelected(true);
      						filters.add("Google");
      						
          				}
          				if(strArray3.contains("Yelp")|| strArray3.contains("yelp"))
          				{
          					yelp.setSelected(true);
      						filters.add("Yelp");
          				}
          				
          				if(strArray3.contains("Foursquare") || strArray3.contains("foursquare"))
          				{
          					foursquare.setSelected(true);
      						filters.add("Foursquare");
          				}
          				
          				///
          				
          				if(strArray3.equals("Google") || strArray3.equals("google places"))
          				{
          					googlePlaces.setSelected(true);
          					yelp.setSelected(false);
          					foursquare.setSelected(false);
      						filters.add("Google");
      						
          				}
          				if(strArray3.equals("Yelp")|| strArray3.equals("yelp"))
          				{
          					yelp.setSelected(true);
          					googlePlaces.setSelected(false);
          					foursquare.setSelected(false);
      						filters.add("Yelp");
          				}
          				
          				if(strArray3.equals("Foursquare") || strArray3.equals("foursquare"))
          				{
          					foursquare.setSelected(true);
          					googlePlaces.setSelected(false);
          					yelp.setSelected(false);
      						filters.add("Foursquare");
          				}
          				
          				
          				price.setText(strArray[4]);
          			}
   				}
          		);                 		
        	   }
        	   
        	   updateProfileMenuButton.setVisible(true);
        	   
     	   s1.close();
        }
        
        else
        {  	
        	Stage s11 = new Stage();
    		s11.setResizable(false);
    		GridPane gp = new GridPane();
         gp.setAlignment(Pos.CENTER);
         gp.setHgap(20);
         gp.setVgap(20);
         gp.setPadding(new Insets(25,25,25,25));   
         Text t = new Text("This account does not exist");
         t.setTranslateX(10);
         t.setTranslateY(10);
         t.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
         gp.add(t,0,0);
         
         s11.setWidth(500);
         s11.setHeight(100);
         Scene scene = new Scene(gp);
         s11.setScene(scene);
         s11.show(); 	   
        }           
    
		
	}

}

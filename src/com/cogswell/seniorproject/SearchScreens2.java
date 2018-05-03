package com.cogswell.seniorproject;


import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;

import java.text.SimpleDateFormat;
import java.util.*;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
import javafx.stage.*;
 // API: Application Programming Interface: Set of routines and definitions for building app software
// JSON: Java Script Object Notation
/**
 * Mixed Reviews
 * 
 * @author (Naveen Krishnamurthy) 
 * @version (Version 2.1)
 */
@SuppressWarnings("unchecked")
public class SearchScreens2 extends Application
{
	private static int rating = 0;
	GridPane gp;
	int priceRange = 0;
	int distance = 0;
    // instance variables - replace the example below with your own
	//private static TestHttp test = new TestHttp();
	private static APICaller test = new APICaller();
	SearchDAO db = new SearchDAO();

	private static Date date = new Date();
	private static SimpleDateFormat df = new SimpleDateFormat("E MM-dd-yyyy h:mm:ss a");
	static String dateStr = df.format(date);

	public void start(Stage s)
	{ 
	    gp = new GridPane();
	    gp.setAlignment(Pos.CENTER);
	   
	    Label clock = new Clock(); // using polymorphism
	    
	    clock.setVisible(true);
	    clock.setTextFill(Color.BLACK);
	    clock.setFont(new Font("Arial", 20));
	    clock.setTranslateX(15);
	    clock.setTranslateY(-50);
	    gp.add(clock, 0, 0);
	    
	    Button searchButton = new Button("Search");
	    searchButton.setTranslateX(450);
	    searchButton.setTranslateY(500);
	    gp.add(searchButton, 0,0);
	
	    // Price
	    MenuButton price = new MenuButton("Select Price Range");
	    price.setMinWidth(200);
	    price.setMaxWidth(100);
	    MenuItem price1 = new MenuItem("$");
	    price1.setStyle(".menu-item .label {\n" + 
	    		"    -fx-padding: 0em 0em 0em 0em;\n" + 
	    		"    -fx-text-fill: #008000;\n" + 
	    		"	 -fx-font-size: 15pt;\n" +
	    		"}");
	    price1.setOnAction(event -> 
	    {
	        db.setPrice(price1.getText());
	        price.setText(price1.getText());
	        price.setTextFill(Color.GREEN);
	    });
	    
	    MenuItem price2 = new MenuItem("$$");
	    price2.setStyle(".menu-item .label {\n" + 
	    		"    -fx-padding: 0em 0em 0em 0em;\n" + 
	    		"    -fx-text-fill: #FFA500;\n" + 
	    		"	 -fx-font-size: 15pt;\n" + 
	    		"}");
	    price2.setOnAction(event -> 
	    {
	        db.setPrice(price2.getText());
	        price.setText(price2.getText());
	        price.setTextFill(Color.ORANGE);
	    });
	    MenuItem price3 = new MenuItem("$$$");
	    price3.setStyle(".menu-item .label {\n" + 
	    		"    -fx-padding: 0em 0em 0em 0em;\n" + 
	    		"    -fx-text-fill: #FF0000;\n" + 
	    		"	 -fx-font-size: 15pt;\n" +
	    		"}");
	    price3.setOnAction(event -> 
	    {
	        db.setPrice(price3.getText());
	        price.setText(price3.getText());
	        price.setTextFill(Color.RED);
	    });
	    
	    MenuItem price4 = new MenuItem("$$$$");
	    price4.setStyle(".menu-item .label {\n" + 
	    		"    -fx-padding: 0em 0em 0em 0em;\n" + 
	    		"    -fx-text-fill: #00008B;\n" + 
	    		"	 -fx-font-size: 15pt;\n" +
	    		"}");
	    price4.setOnAction(event -> 
	    {
	        db.setPrice(price4.getText());
	        price.setText(price4.getText());
	        price.setTextFill(Color.DARKBLUE);
	    });
	    
	    switch(price.getText())
	    {
	    	case ("$"):
	    		priceRange = 1;
	    		break;
	    	case ("$$"):
	    		priceRange = 2;
	    		break;
	    	case ("$$$"):
	    		priceRange = 3;
	    		break;
	    	case ("$$$$"):
	    		priceRange = 4;
	    	
	    	default:
	    		priceRange = 1;	
	    }
	    
	    price.getItems().addAll(price1, price2, price3, price4);
	    price.setTranslateX(-200);
	    price.setTranslateY(325); // 475
	    gp.add(price, 0,0);
	    Label priceLabel = new Label("Price");
	    priceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    priceLabel.setTranslateX(-260);
	    priceLabel.setTranslateY(325);
	    gp.add(priceLabel,0,0);
	    
	    // Rating
	    MenuButton ratingMenu = new MenuButton("Rating");
	    ratingMenu.setMinWidth(100);
	    ratingMenu.setMaxWidth(100);
	    ratingMenu.setTextFill(Color.BLACK);
	    
	    MenuItem oneStar = new MenuItem("★");
	    oneStar.setStyle(".menu-item .label {\n" + 
	    		"    -fx-padding: 0em 0em 0em 0em;\n" + 
	    		"    -fx-text-fill: #FF0000;\n" + 
	    		"	 -fx-font-size: 15pt;\n" +
	    		"}");
	    // I'm gonna use lamba for the fun of it
	    oneStar.setOnAction(event -> 
	    {
	        db.setRating(oneStar.getText());
	        ratingMenu.setText(oneStar.getText());
	        ratingMenu.setTextFill(Color.RED);
	       
	    });
	          
	    MenuItem twoStar = new MenuItem("★★");
	    twoStar.setStyle(".menu-item .label {\n" + 
	    		"    -fx-padding: 0em 0em 0em 0em;\n" + 
	    		"    -fx-text-fill: #FF8000;\n" + 
	    		"	 -fx-font-size: 15pt;\n" +
	    		"}");
	    twoStar.setOnAction(event -> 
	    {
	        db.setRating(twoStar.getText());
	        ratingMenu.setText(twoStar.getText());
	        ratingMenu.setTextFill(Color.ORANGE);
	        
	    });
	    
	    MenuItem threeStar = new MenuItem("★★★");
	    threeStar.setStyle(".menu-item .label {\n" + 
	    		"    -fx-padding: 0em 0em 0em 0em;\n" + 
	    		"    -fx-text-fill: #9ACD32;\n" + 
	    		"	 -fx-font-size: 15pt;\n" +
	    		"}");
	    threeStar.setOnAction(event -> 
	    {
	        db.setRating(threeStar.getText());
	        ratingMenu.setText(threeStar.getText());
	        ratingMenu.setTextFill(Color.YELLOWGREEN);
	        
	    });
	       
	    MenuItem fourStar = new MenuItem("★★★★");
	    fourStar.setStyle(".menu-item .label {\n" + 
	    		"    -fx-padding: 0em 0em 0em 0em;\n" + 
	    		"    -fx-text-fill: #008000;\n" + 
	    		"	 -fx-font-size: 15pt;\n" +
	    		"}");
	    fourStar.setOnAction(event -> 
	    {
	        db.setRating(fourStar.getText());
	        ratingMenu.setText(fourStar.getText());
	        ratingMenu.setTextFill(Color.GREEN);
	        
	    });
	    
	    MenuItem fiveStar = new MenuItem("★★★★★");
	    fiveStar.setStyle(".menu-item .label {\n" + 
	    		"    -fx-padding: 0em 0em 0em 0em;\n" + 
	    		"    -fx-text-fill: #004080;\n" + 
	    		"	 -fx-font-size: 15pt;\n" +
	    		"}");
	    fiveStar.setOnAction(event -> 
	    {
	        db.setRating(fiveStar.getText());
	        ratingMenu.setText(fiveStar.getText());
	        ratingMenu.setTextFill(Color.DARKBLUE);	
	        
	    });
	    
	    Label ratingLabel = new Label("Rating");
	    ratingLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    ratingLabel.setTranslateX(240);
	    ratingLabel.setTranslateY(175); // 
	    gp.add(ratingLabel,0,0);
	    
	    ratingMenu.getItems().addAll(oneStar, twoStar, threeStar, fourStar, fiveStar);
	    ratingMenu.setTranslateX(300);
	    ratingMenu.setTranslateY(175); 
	    gp.add(ratingMenu,0,0);
	    
	   switch(ratingMenu.getText())
	   {
	   	case ("★"):
	   		rating = 1;
	   		break;
	   	case ("★★"):
	   		rating = 2;
	   		break;
	   	case ("★★★"):
	   		rating = 3;
	   		break;
	   	case ("★★★★"):
	   		rating = 4;
	   		break;
	   	case ("★★★★★"):
	   		rating = 5;
	   		break;
	   	default:
	   		rating = 3;	
	   }
	   
	   Set<String> filters = new HashSet<String>();
	   CheckBox yelp = new CheckBox(); //Users/Naveen/Desktop/docs/Senior Project/Mixed Reviews
	   yelp.setDisable(true);
	   Image yelpLogo = new Image((getClass().getResource("yelp.png")).toExternalForm());
	   ImageView yelpView = new ImageView(yelpLogo);
	   
	   yelpView.setOnMouseClicked(new EventHandler<MouseEvent>()
	   {
	       public void handle(MouseEvent me) 
	       {
	       		if(!yelp.isSelected())
	       		{
	       			yelp.setSelected(true);	
	       			filters.add("Yelp");
	       			System.out.println("Yelp selected");
	       		}
	       		else
	       		{
	       			yelp.setSelected(false);
	       			filters.remove("Yelp");
	       			System.out.println("Yelp unselected");
	       		}
	       }
	   }
	   );
	
	   	yelpView.setTranslateX(-260);
	   	yelpView.setTranslateY(500);
	   
	   	CheckBox googlePlaces = new CheckBox();
	   	googlePlaces.setDisable(true);
	   	
	   	Image googleLogo = new Image((getClass().getResource("powered_by_google_on_white.png")).toExternalForm());
	   	ImageView googleView = new ImageView(googleLogo);
	   	
	   	googleView.setOnMouseClicked(new EventHandler<MouseEvent>()
	   	{
	       public void handle(MouseEvent me) 
	       {
	       		if(!googlePlaces.isSelected())
	       		{
	       			googlePlaces.setSelected(true);
	       			filters.add("Google");
	       			System.out.println("Google Places selected");   
	       		}
	       		else
	       		{
	       			googlePlaces.setSelected(false);		
	       			filters.remove("Google");
	       			System.out.println("Google Places unselected");
	       		}
	       }
	   	}
	   );
	   
	   	googleView.setTranslateX(0);
	   	googleView.setTranslateY(500);
	   
	   	CheckBox foursquare = new CheckBox();
	   	foursquare.setDisable(true);
	   	Image foursquareLogo = new Image((getClass().getResource("foursquare.png")).toExternalForm());
	   	ImageView foursquareView = new ImageView(foursquareLogo);
	   
	   	foursquareView.setOnMouseClicked(new EventHandler<MouseEvent>()
	   	{
	   		public void handle(MouseEvent me) 
	   		{	
	       		if(!foursquare.isSelected())
	       		{
	       			foursquare.setSelected(true);
	       			
	       			filters.add("Foursquare");
	               System.out.println("Foursquare selected");
	       		}
	       		else
	       		{
	       			foursquare.setSelected(false);
	       			
	       			filters.remove("Foursquare");
	               System.out.println("Foursquare unselected");
	       		}
	   		}
	   	}
	   );
	   
	   	foursquareView.setTranslateX(290);
	   	foursquareView.setTranslateY(500);
	
	   	yelp.setTranslateX(-225);
	   	yelp.setTranslateY(500);
	   
	   	googlePlaces.setTranslateX(-30);
	   	googlePlaces.setTranslateY(500);
	   
	   	foursquare.setTranslateX(265);
	   	foursquare.setTranslateY(500);
	   
	   	gp.add(yelp, 0, 0);
	   	//gp.add(tripAdvisor, 0, 0);
	   	gp.add(googlePlaces, 0, 0);
	   	gp.add(foursquare, 0, 0);
	   
	   	gp.add(yelpView, 0, 0);
	   	gp.add(googleView, 0, 0);
	   	gp.add(foursquareView, 0, 0);
	   
	
	    Button updateProfileMenuButton = new Button("Update Profile");
	    
	    updateProfileMenuButton.setTranslateX(600);
	    updateProfileMenuButton.setTranslateY(0);
	    updateProfileMenuButton.setVisible(false);
	    
	    gp.add(updateProfileMenuButton, 0, 0);
	    

	    // Distance
	    MenuButton distanceMenu = new MenuButton("Distance");
	    MenuItem mile5 = new MenuItem("5 miles");
	    mile5.setStyle("	 -fx-font-size: 15pt;\n" +
	    		"}");
	    mile5.setOnAction(event -> 
	    {
	        String str = mile5.getText().replaceAll("[^\\d.]", ""); // truncate non numeric characters
	        distanceMenu.setText(mile5.getText());
	        db.setDistance(Integer.parseInt(str));
	        
	    });
	    
	    MenuItem mile10 = new MenuItem("10 miles");
	    mile10.setStyle(" -fx-font-size: 15pt;\n" +
	    		"}");
	    mile10.setOnAction(event -> 
	    {
	        String str = mile10.getText().replaceAll("[^\\d.]", "");
	        distanceMenu.setText(mile10.getText());
	        db.setDistance(Integer.parseInt(str));
	        
	    });
	    
	    MenuItem mile25 = new MenuItem("25 miles");
	    mile25.setStyle(" -fx-font-size: 15pt;\n" +
	    		"}");
	    mile25.setOnAction(event -> 
	    {
	        String str = mile25.getText().replaceAll("[^\\d.]", "");
	        distanceMenu.setText(mile25.getText());
	        db.setDistance(Integer.parseInt(str));
	        
	    });
	            
	    MenuItem mile50 = new MenuItem("50 miles");
	    mile50.setStyle(" -fx-font-size: 15pt;\n" +
	    		"}");
	    mile50.setOnAction(event -> 
	    {
	        String str = mile50.getText().replaceAll("[^\\d.]", "");
	        distanceMenu.setText(mile50.getText());
	        db.setDistance(Integer.parseInt(str));
	        
	    });
	   
	    MenuItem mile100 = new MenuItem("100 miles");
	    mile100.setStyle(" -fx-font-size: 15pt;\n" +
	    		"}");
	    mile100.setOnAction(event -> 
	    {
	        String str = mile100.getText().replaceAll("[^\\d.]", "");
	        distanceMenu.setText(mile100.getText());
	        db.setDistance(Integer.parseInt(str));
	        
	    });
	    distanceMenu.getItems().addAll(mile5, mile10, mile25, mile50, mile100);
	    distanceMenu.setTranslateX(309);
	    distanceMenu.setTranslateY(250);
	    
	    switch(distanceMenu.getText())
	    {
	    		case "5 miles":
	    			distance = 5;
	    			break;
	    		case "10 miles":
	    			distance = 10;
	    			break;
	    		case "25 miles":
	    			distance = 25;
	    			break;
	    		case "50 miles":
	    			distance = 50;
	    			break;
	    		case "100 miles":
	    			distance = 100;
	    			break;
	    		default:
	    			distance = 5;
	
	    }
	    
	    gp.add(distanceMenu,0,0);
	    
	    Label distanceLabel = new Label("Distance");
	    distanceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    distanceLabel.setTranslateX(240);
	    distanceLabel.setTranslateY(250);
	    gp.add(distanceLabel,0,0);
	    
	    Label searchLabel = new Label("Search * ");
	    searchLabel.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    searchLabel.setTranslateX(-267);
	    searchLabel.setTranslateY(175);
	    gp.add(searchLabel,0, 0);
	    
	    TextField searchField = new TextField();
	    searchField.setPromptText("Search for a place or category");
	    searchField.setMaxWidth(300);
	    searchField.setMinWidth(300);
	    searchField.setTranslateX(-200);
	    searchField.setTranslateY(175);
	    gp.add(searchField, 0, 0);
	    
	    
	    Label zipOrCity = new Label("City or Zip * ");
	    zipOrCity.setFont(Font.font("Arial", FontWeight.BOLD, 15));
	    zipOrCity.setTranslateX(-292);
	    zipOrCity.setTranslateY(250);
	    gp.add(zipOrCity, 0, 0); 
	    
	    TextField zipCodeOrCity = new TextField();
	    zipCodeOrCity.setPromptText("City or ZIP");
	    zipCodeOrCity.setTranslateX(-200);
	    zipCodeOrCity.setTranslateY(250);
	    gp.add(zipCodeOrCity, 0, 0);
	    
	    Text usernameLogin = new Text(); // displays user name
	    usernameLogin.setText("Not Logged In");
	    usernameLogin.setStyle("	 -fx-font-size: 15pt;\n" +
	    		"}");
	    usernameLogin.setTranslateX(600);
	    usernameLogin.setTranslateY(40);
	    gp.add(usernameLogin, 0, 0);
	    
	    MenuButton history = new MenuButton("Search History");
	    history.setTranslateX(-300);
	    history.setTranslateY(0);
	    history.setVisible(false);
	    
		gp.add(history, 0, 0);
		
		//////////////////////////////////////////////
		Text loginPageText = new Text("Login Page");
	    loginPageText.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
	    
	    Label usernameLabel = new Label("Username");
	    
	    TextField username = new TextField();
	    username.setPromptText("Username");
	
	    TextField password = new TextField();
	    Label pw = new Label("Password");
	    password.setPromptText("Password");
	     
	    PasswordField pwBox = new PasswordField(); // password
	
		TextField firstNameField = new TextField(); // first name
	
		TextField lastNameField = new TextField(); // last name
	
		TextField zipcodeField = new TextField(); // zipcode
		
		TextField emailField = new TextField(); // email
	
		TextField addressField = new TextField(); // address
		UpdateProfileMenuButtonHandler upmb = new UpdateProfileMenuButtonHandler(username, firstNameField, lastNameField, password, emailField, addressField, zipcodeField, pwBox,usernameLogin);
		updateProfileMenuButton.setOnAction(upmb);	
	    
	    Button loginOrEnroll = new Button("Login / Enroll");
	    loginOrEnroll.setOnAction(new EventHandler<ActionEvent>()
	    {
	        public void handle(ActionEvent ae)
	        {
	            Stage s1 = new Stage();
	            s1.setTitle("Login");
	            GridPane gp = new GridPane();
	            gp.setAlignment(Pos.CENTER);
	            gp.setHgap(20);
	            gp.setVgap(20);
	            gp.setPadding(new Insets(25,25,25,25));
	            gp.add(loginPageText,0,0,2,1);                       
	            gp.add(usernameLabel,0, 1);	                            
	            gp.add(username, 1,1);
	            gp.add(pw, 0,2);   
	            gp.add(pwBox, 1,2);
	             
	            Button login = new Button("Login");
	            login.setTranslateX(250);
	            login.setTranslateY(10);
	            LoginButtonHandler lgb = new LoginButtonHandler(username, usernameLogin, pwBox, history, searchField, zipCodeOrCity, ratingMenu, price, googlePlaces, yelp, foursquare, filters, s1, updateProfileMenuButton);
	            login.setOnAction(lgb);
	            gp.add(login,0,0);
	
	            Button enroll = new Button("Enroll");
	            enroll.setOnAction(new EventHandler<ActionEvent>()
	            {
	                public void handle(ActionEvent ae)
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
	                    
	                    TextField un  = new TextField(); // username
	                    TextField pwd = new TextField(); // password
	                    TextField fn = new TextField(); // first name
	                    TextField ln = new TextField(); // last name
	                    TextField zp = new TextField(); // zipcode
	                    TextField em = new TextField(); // email
	                    TextField ad = new TextField(); // address
	                    
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
	            );
	            gp.add(enroll, 5, 2);
	            Scene scene = new Scene(gp);
	            s1.setScene(scene);
	            s1.show();
	        }
	    }
	    );
	    
	    loginOrEnroll.setTranslateX(600);
	    loginOrEnroll.setTranslateY(75);
	    gp.add(loginOrEnroll, 0, 0);
	    
	    searchButton.setOnAction(new EventHandler<ActionEvent>()
	    {
	        public void handle(ActionEvent ae)
	        {
	        		if((searchField.getText() == null || searchField.getText().isEmpty()) || (zipCodeOrCity.getText() == null || zipCodeOrCity.getText().isEmpty() || ((!googlePlaces.isSelected() && !foursquare.isSelected() && !yelp.isSelected()))))
	        		{
	        			Stage s11 = new Stage();
	        			s11.setResizable(false);
	        			GridPane gp = new GridPane();
	        		     gp.setAlignment(Pos.CENTER);
	        		     gp.setHgap(20);
	        		     gp.setVgap(20);
	        		     gp.setPadding(new Insets(25,25,25,25));   
	        		     Text t = new Text("You must enter a search criteria,\nlocation, and sites to search from");
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
	        		else
	        		{
	        			System.out.println(usernameLogin.getText());
		        		if(usernameLogin.getText() == "Not Logged In")
		        		{
		        	   		try
		        	   		{
		        	   			if(ratingMenu.getText() == "Rating")
		        	   			{
		        	   				ratingMenu.setText(test.getStar(rating));
		        	   				ratingMenu.setTextFill(Color.YELLOWGREEN);
		        	   			}
		        	   			
		        	   			if(price.getText() == "Select Price Range")
		        	   			{
		        	   				price.setText(test.getPriceSymbol(priceRange));
		        	   				price.setTextFill(Color.GREEN);
		        	   			}
		        	   			//db.storeGeneralSearch(searchField.getText(), zipCodeOrCity.getText(), distance, ratingMenu.getText(), filters, test.getPriceSymbol(priceRange));	
		        	   			
		        	   			db.storeGeneralSearch(searchField.getText(), zipCodeOrCity.getText(), distance, ratingMenu.getText(), filters, test.getPriceSymbol(priceRange));	
		        	   			
		        	   			test.search(usernameLogin.getText(), searchField.getText(), zipCodeOrCity.getText(), ratingMenu.getText(), distance, test.getPrice(price.getText()), filters);
		        	   			//db.storeGeneralSearch(searchField.getText(), zipCodeOrCity.getText(), distance, ratingMenu.getText(), filters, test.getPriceSymbol(priceRange));
		        	   			
		        	   			//db.storeGeneralResults(searchField.getText());
		        	   		}
		        	   		catch(Exception e)
		        	   		{
		        	   			e.printStackTrace();
		        	   		}
		           }
		           
		           else if(usernameLogin.getText() != "Not Logged In")
		           {
		        	   		System.out.println(usernameLogin.getText());
		        	   		try
		        	   		{		
		    	   				if(ratingMenu.getText() == "Rating")
		    	   				{
		    	   					ratingMenu.setText(test.getStar(rating));
		    	   					ratingMenu.setTextFill(Color.YELLOWGREEN);
		    	   				}
		    	   				db.storeUserSearch(db.getUsername(), searchField.getText(), zipCodeOrCity.getText(), ratingMenu.getText(), filters, test.getPriceSymbol(priceRange), dateStr);		

	    	   					test.search(usernameLogin.getText(), searchField.getText(), zipCodeOrCity.getText(), ratingMenu.getText(), distance, priceRange, filters);
		    	   				//test.search(usernameLogin.getText(), searchField.getText(), zipCodeOrCity.getText(), ratingMenu.getText(), distance, test.getPrice(price.getText()), filters);
		        	   		}
		        	   		catch(Exception e)
		        	   		{
		        	   			e.printStackTrace();
		        	   		}
		           }
	        		}
	        }
	    }
	    );
	
	    VBox root = new VBox(10);
	    //////////////////////////////////////////////////////
	    
	    Image icons = new Image((getClass().getResource("icons2.png")).toExternalForm());
	    ImageView iconView = new ImageView(icons);
	    iconView.setTranslateX(400);
		iconView.setTranslateY(-250);
		
	    //////////////////////////////////////////////////////
	    root.getChildren().add(gp);
	    root.setTranslateY(0);
	    root.getChildren().add(iconView);
	    s.setTitle("Mixed Reviews");
	    
	    s.setWidth(1920);
	    s.setHeight(1000);
	    s.show();
	    Scene scene = new Scene(root);
	   
	    //root.setId("root");
	    scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
	    s.setScene(scene);
	    //s.setMaximized(true);
	   
	    // This piece of code will kill any running threads after the application is terminated
	        s.setOnCloseRequest(new EventHandler<WindowEvent>()
	        {
	            public void handle(WindowEvent we)
	            {
	                Platform.exit();
	                System.exit(0);
	            }
	        }
	        );
	    }
  
    public static void main(String[] args)
    {
        launch(args);
    }
}


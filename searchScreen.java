import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.beans.value.*;
import javafx.util.*;

import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.control.TabPane.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.scene.text.*;

import javafx.scene.media.*;
import javafx.animation.*;
import javafx.scene.control.cell.*;
import java.io.File;
import java.util.*;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import java.io.FilenameFilter;
import javafx.stage.*;

import java.sql.*;

/**
 * Write a description of class searchScreen here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
@SuppressWarnings("unchecked")
public class searchScreen extends Application
{
	private String rating = "";
    // instance variables - replace the example below with your own
    SearchDAO db = new SearchDAO();
    public void start(Stage s)
    { 
    		
        GridPane gp = new GridPane();
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(20);
        gp.setVgap(20);
        gp.setPadding(new Insets(25,25,25,25));
       
    
        Button searchButton = new Button("Search");
        
        searchButton.setTranslateX(325);
        searchButton.setTranslateY(180);
        gp.add(searchButton, 0,0);
        
        Text t = new Text("Mixed Reviews");
        t.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
        t.setTranslateX(75);
        t.setTranslateY(0);
        gp.add(t, 0, 0);
        
        // Price
        MenuButton price = new MenuButton("Price");
        price.setMinWidth(100);
        price.setMaxWidth(100);
        MenuItem price1 = new MenuItem("$");
        price1.setOnAction(event -> 
        {
            db.setPrice(price1.getText());
            price.setText(price1.getText());
        });
        
        MenuItem price2 = new MenuItem("$$");
        price2.setOnAction(event -> 
        {
            db.setPrice(price2.getText());
            price.setText(price2.getText());
        });
        MenuItem price3 = new MenuItem("$$$");
        price3.setOnAction(event -> 
        {
            db.setPrice(price3.getText());
            price.setText(price3.getText());
        });
        price.getItems().addAll(price1, price2, price3);
        price.setTranslateX(300);
        price.setTranslateY(200);
        gp.add(price, 0,0);
        Label priceLabel = new Label("Price");
        priceLabel.setTranslateX(260);
        priceLabel.setTranslateY(200);
        gp.add(priceLabel,0,0);
        
        // Rating
        MenuButton mb = new MenuButton("Rating");
        mb.setMinWidth(100);
        mb.setMaxWidth(100);
        MenuItem m1 = new MenuItem("☆");
        // I'm gonna use lamba for the fun of it
        m1.setOnAction(event -> 
        {
            db.setRating(m1.getText());
            mb.setText(m1.getText());
        });
        
        MenuItem m2 = new MenuItem("☆☆");
        m2.setOnAction(event -> 
        {
            db.setRating(m2.getText());
            mb.setText(m2.getText());
        });
        
        MenuItem m3 = new MenuItem("☆☆☆");
        m3.setOnAction(event -> 
        {
            db.setRating(m3.getText());
            mb.setText(m3.getText());
        });
        
        MenuItem m4 = new MenuItem("☆☆☆☆");
        m4.setOnAction(event -> 
        {
            db.setRating(m4.getText());
            mb.setText(m4.getText());
        });
       
        MenuItem m5 = new MenuItem("☆☆☆☆☆");
        m5.setOnAction(event -> 
        {
            db.setRating(m5.getText());
            mb.setText(m5.getText());
        });
        
        if(mb.getText() == "☆")
        {
        		rating = "1";
        }
        
        if(mb.getText() == "☆☆")
        {
        		rating = "2";
        }
        
        if(mb.getText() == "☆☆☆")
        {
        		rating = "3";
        }
        
        if(mb.getText() == "☆☆☆☆")
        {
        		rating = "4";
        }
        
        if(mb.getText() == "☆☆☆☆☆")
        {
        		rating = "5";
        }
        
        Button update = new Button("Update Profile");
        update.setTranslateX(300);
        update.setTranslateY(0);
        update.setVisible(false);
        
        gp.add(update, 0, 0);
        
        mb.getItems().addAll(m1, m2, m3, m4, m5);
        mb.setTranslateX(-100);
        mb.setTranslateY(200);
        gp.add(mb,0,0);
        
        Label ratingLabel = new Label("Rating");
        ratingLabel.setTranslateX(-150);
        ratingLabel.setTranslateY(200);
        gp.add(ratingLabel,0,0);
        
        // Distance
        MenuButton mb2 = new MenuButton("Distance");
        MenuItem mile5 = new MenuItem("5 miles");
        mile5.setOnAction(event -> 
        {
            String str = mile5.getText().replaceAll("[^\\d.]", ""); // truncate non numeric characters
            mb2.setText(mile5.getText());
            db.setDistance(Integer.parseInt(str));
            
        });
        
        MenuItem mile10 = new MenuItem("10 miles");
        mile10.setOnAction(event -> 
        {
            String str = mile10.getText().replaceAll("[^\\d.]", "");
            mb2.setText(mile10.getText());
            db.setDistance(Integer.parseInt(str));
            
        });
        
        MenuItem mile25 = new MenuItem("25 miles");
        mile25.setOnAction(event -> 
        {
            String str = mile25.getText().replaceAll("[^\\d.]", "");
            mb2.setText(mile25.getText());
            db.setDistance(Integer.parseInt(str));
            
        });
                
        MenuItem mile50 = new MenuItem("50 miles");
        mile50.setOnAction(event -> 
        {
            String str = mile50.getText().replaceAll("[^\\d.]", "");
            mb2.setText(mile50.getText());
            db.setDistance(Integer.parseInt(str));
            
        });
       
        MenuItem mile100 = new MenuItem("100 miles");
        mile100.setOnAction(event -> 
        {
            String str = mile100.getText().replaceAll("[^\\d.]", "");
            mb2.setText(mile100.getText());
            db.setDistance(Integer.parseInt(str));
            
        });
        mb2.getItems().addAll(mile5, mile10, mile25, mile50, mile100);
        mb2.setTranslateX(115);
        mb2.setTranslateY(200);
        gp.add(mb2,0,0);
        
        Label distanceLabel = new Label("Distance");
        distanceLabel.setTranslateX(50);
        distanceLabel.setTranslateY(200);
        gp.add(distanceLabel,0,0);
        
        Label searchLabel = new Label("Search");
        searchLabel.setTranslateX(-250);
        searchLabel.setTranslateY(82);
        gp.add(searchLabel,0, 0);
        
        TextField tf = new TextField();
        tf.setPromptText("Search for a place or category");
        tf.setMaxWidth(300);
        tf.setMinWidth(300);
        tf.setTranslateX(-200);
        tf.setTranslateY(82);
        gp.add(tf, 0, 0);
        
        
        Label zipOrCity = new Label("City or Zip");
        zipOrCity.setTranslateX(130);
        zipOrCity.setTranslateY(82);
        gp.add(zipOrCity, 0, 0); 
        
        TextField zipCodeOrCity = new TextField();
        zipCodeOrCity.setPromptText("City or ZIP");
        zipCodeOrCity.setTranslateX(200);
        zipCodeOrCity.setTranslateY(82);
        gp.add(zipCodeOrCity, 0, 0);
        
        Text usernameLogin = new Text(); // displays user name
        usernameLogin.setText("Not Logged In");
        usernameLogin.setTranslateX(300);
        usernameLogin.setTranslateY(-20);
        gp.add(usernameLogin, 0, 0);
        
        Button login = new Button("Login");
        login.setOnAction(new EventHandler<ActionEvent>()
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
                
                
                Text t = new Text("Login Page");
                t.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
                gp.add(t,0,0,2,1);
                
                Label searchBox = new Label("Username");
                gp.add(searchBox,0, 1);
                
                TextField tf = new TextField();
                tf.setPromptText("Username");
                gp.add(tf, 1,1);
                
                TextField tf1 = new TextField();
                Label pw = new Label("Password");
                tf1.setPromptText("Password");
                gp.add(pw, 0,2);
                
                PasswordField pwBox = new PasswordField();
                gp.add(pwBox, 1,2);
                
                Text invalidUserName = new Text();
                
                Button login = new Button("Login");
                login.setTranslateX(200);
                login.setTranslateY(10);
                login.setOnAction(new EventHandler<ActionEvent>()
                {
                    public void handle(ActionEvent ae)
                    {
                        
                        String data = db.getUserProfile(tf.getText(), pwBox.getText());
                        if(data != null)
                        {
                        	   usernameLogin.setText(data);
                        	   update.setVisible(true);
                        	   update.setOnAction(new EventHandler<ActionEvent>()
                			   {
                        		   public void handle(ActionEvent ae)
                        		   {
                        			   System.out.println(tf.getText());
                        			   userProfile up = db.getUserProfileDetails(tf.getText());
                        			   Stage s2 = new Stage();
                        			   s2.setResizable(false);
                                    s2.setTitle("Update Profile");
                                    GridPane gp = new GridPane();
                                    gp.setAlignment(Pos.CENTER);
                                    gp.setHgap(20);
                                    gp.setVgap(20);
                                    gp.setPadding(new Insets(25,25,25,25));
                                    Text t = new Text("Update Profile");
                                    t.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
                                    gp.add(t,0,0,2,1);
                                    
                                    PasswordField pw = new PasswordField(); // password
                                    pw.setText(up.getPassword());
                                    
                                    TextField tf2 = new TextField(); // first name
                                    tf2.setText(up.getFirstName());
                                   
                                    TextField tf3 = new TextField(); // last name
                                    tf3.setText(up.getLastName());
                                    
                                    TextField tf4 = new TextField(); // zipcode
                                    tf4.setText(Integer.toString(up.getZipcode()));
                                    
                                    TextField tf5 = new TextField(); // email
                                    tf5.setText(up.getEmail());
                                    
                                    TextField tf6 = new TextField(); // address
                                    tf6.setText(up.getAddress());
                                    
                                    
                                    Label password = new Label("Password");
                                    pw.setPromptText("Password");
                                    gp.add(pw, 1,2);
                                    gp.add(password, 0,2);
                                    
                                    Label firstName = new Label("First Name");
                                    tf2.setPromptText("First Name");
                                    gp.add(tf2, 1,3);
                                    gp.add(firstName, 0,3);
                                    
                                    Label lastName = new Label("Last Name");
                                    tf3.setPromptText("Last Name");
                                    gp.add(tf3, 1,4);
                                    gp.add(lastName, 0,4);
                                    
                                    Label zipcode = new Label("Zipcode");
                                    tf4.setPromptText("Zipcode");
                                    gp.add(tf4, 1,5);
                                    gp.add(zipcode, 0,5);
                                    String code = tf4.getText();

                                    
                                    if(code.matches("[0-9]*") )
                                    {
                                        if(tf4.getLength() > 5)
                                        {
                                            Integer.parseInt(tf4.getText());
                                        }
                                    }
                                    else
                                    {
                                        System.out.println("Zipcode must be numbers and not be longer than 5 digits");    
                                    }
                                    
                                    Label email = new Label("Email");
                                    tf5.setPromptText("Email Address");
                                    gp.add(tf5, 1,6);
                                    gp.add(email, 0,6);
                                    
                                    Label address = new Label("Address");
                                    tf6.setPromptText("Home Address");
                                    gp.add(tf6, 1,7);
                                    gp.add(address, 0,7);
                                    
                                    Button update = new Button("Update");
                                    update.setTranslateX(175);
                                    update.setTranslateY(-10);
                                    gp.add(update, 0, 0);
                                    update.setOnAction(new EventHandler<ActionEvent>()
                                		{
                                    		public void handle(ActionEvent ae)
                                    		{
                                    			System.out.println("Password: " + pw.getText());
                                    			db.setUsername(tf.getText());
                                    			db.setFirstName(tf2.getText());
                                    			db.setPassword(pw.getText());
                                    			db.setLastName(tf3.getText());
                                    			db.setZipcode(Integer.parseInt(tf4.getText()));
                                    			db.setEmail(tf5.getText());
                                    			db.setAddress(tf6.getText());
                                    			
                                    			try
                                    			{
                                    			    System.out.println("Updating");
                                                db.updateUserProfile(tf.getText(), tf2.getText(), tf3.getText(), pw.getText(), Integer.parseInt(tf4.getText()), tf5.getText(), tf6.getText());
                                                             
                                    			}
                                    			catch(Exception e)
                                    			{
                                    				//e.printStackTrace();
                                    				// create popup with error
                                    			}
                                    		}
                                		}
                                    );
                                    Scene scene = new Scene(gp);
                                    s2.setScene(scene);
                                    s2.show();
                        		   }
                			   }
                           );

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
                );
                
                gp.add(login,0,0);
              
                
                // This is important because we are checking to see if the name from tf.getText() exists.
                /*
                 *  String checkForUserName = "select * from userProfile where name = " + tf.getText();
                 *  Statement userNameStmt = connection.createStatement();
                 *  ResultSet rs = userNameStmt.executeQuery(checkForUserName);
                 *  if(rs.absolute(1)
                 *  {
                 *      usernameLogin.setText(tf.getText());
                 *  }
                 *  else
                 *  {
                 *      println("Username not found");    
                 *  }
                 */
                
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
                        
                        
                        TextField tf  = new TextField(); // username
                        TextField tf1 = new TextField(); // password
                        TextField tf2 = new TextField(); // first name
                        TextField tf3 = new TextField(); // last name
                        TextField tf4 = new TextField(); // zipcode
                        TextField tf5 = new TextField(); // email
                        TextField tf6 = new TextField(); // address
                        
                        Label userName = new Label("Username");
                        tf.setPromptText("Username");
                        gp.add(userName, 0,1);
                        gp.add(tf, 1,1);
                        
                        Label password = new Label("Password");
                        tf1.setPromptText("Password");
                        PasswordField pwBox = new PasswordField();
                        pwBox.setPromptText("Password");
                        gp.add(pwBox, 1,2);
                        gp.add(password, 0,2);
                        
                        Label firstName = new Label("First Name");
                        tf2.setPromptText("First Name");
                        gp.add(tf2, 1,3);
                        gp.add(firstName, 0,3);
                        
                        Label lastName = new Label("Last Name");
                        tf3.setPromptText("Last Name");
                        gp.add(tf3, 1,4);
                        gp.add(lastName, 0,4);
                        
                        Label zipcode = new Label("Zipcode");
                        tf4.setPromptText("Zipcode");
                        gp.add(tf4, 1,5);
                        gp.add(zipcode, 0,5);
                        String code = tf4.getText();
                        if(code.matches("[0-9]*") )
                        {
                            if(tf4.getLength() > 5)
                            {
                                Integer.parseInt(tf4.getText());
                            }
                        }
                        else
                        {
                            System.out.println("Zipcode must be numbers and not be longer than 5 digits");    
                        }

                        Label email = new Label("Email");
                        tf5.setPromptText("Email Address");
                        gp.add(tf5, 1,6);
                        gp.add(email, 0,6);
                        
                        Label address = new Label("Address");
                        tf6.setPromptText("Home Address");
                        gp.add(tf6, 1,7);
                        gp.add(address, 0,7);
 
                        Button signUp = new Button("Sign Up");
                        signUp.setTranslateX(275);
                        signUp.setTranslateY(375);
                        signUp.setOnAction(new EventHandler<ActionEvent>()
                        {
                            public void handle(ActionEvent ae)
                            {
                                db.setUsername(tf.getText());
                                db.setPassword(pwBox.getText());
                                db.setFirstName(tf2.getText());
                                db.setLastName(tf3.getText());
                                db.setZipcode(Integer.parseInt(tf4.getText()));
                                db.setEmail(tf5.getText());
                                db.setAddress(tf6.getText());
                                try
                                {
                                        db.storeUserProfile(tf.getText(), tf2.getText(), tf3.getText(), pwBox.getText(), Integer.parseInt(tf4.getText()), tf5.getText(), tf6.getText());
                                        s2.close();
                                        s1.close();          
                                }
                                catch (MySQLIntegrityConstraintViolationException e)
                                {
	                                	Stage s11 = new Stage();
	                            		s11.setResizable(false);
	                            		GridPane gp = new GridPane();
	                                 gp.setAlignment(Pos.CENTER);
	                                 gp.setHgap(20);
	                                 gp.setVgap(20);
	                                 gp.setPadding(new Insets(25,25,25,25));   
	                                 Text t = new Text("This account already exists");
	                                 t.setTranslateX(10);
	                                 t.setTranslateY(10);
	                                 t.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
	                                 gp.add(t,0,0);
                                 
	                                 s11.setWidth(500);
	                                 s11.setHeight(100);
	                                 Scene scene = new Scene(gp);
	                                 s11.setScene(scene);
	                                 s11.show();
                                			
                                        // create pop up that contains the error message
                                }
                            }
                        }
                        );
                        s2.close();
                        gp.add(signUp, 0, 0);
                        
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
        
       
        login.setTranslateX(315);
        login.setTranslateY(35);
        gp.add(login, 0, 0);
        
        ArrayList<String> filter = new ArrayList<String>();
        
        CheckBox yelp = new CheckBox("Yelp");
        yelp.selectedProperty().addListener(new ChangeListener<Boolean>() 
        {
                public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean newVal)
                {
                    if(yelp.isSelected())
                    {
                    	   filter.add(yelp.getText());
                        db.storeFilter(yelp.getText());
                        System.out.println("Yelp selected");
                    }
                    else
                    {
                    		filter.remove(yelp.getText());
                        db.storeFilter(yelp.getText()).remove(yelp.getText());
                        System.out.println("Yelp unselected");
                    }
                    
                }
        }
        );
               
        CheckBox tripAdvisor = new CheckBox("TripAdvisor");
        tripAdvisor.selectedProperty().addListener(new ChangeListener<Boolean>() 
        {
                public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean newVal)
                {
                    if(tripAdvisor.isSelected())
                    {
                    		filter.add(tripAdvisor.getText());
                        db.storeFilter(tripAdvisor.getText());
                        System.out.println("tripAdvisor selected");
                    }
                    else
                    {
                     	filter.remove(tripAdvisor.getText());
                        db.storeFilter(tripAdvisor.getText()).remove(tripAdvisor.getText());
                        System.out.println("tripAdvisor unselected");
                    }
                    
                }
        }
        );

        CheckBox googlePlaces = new CheckBox("Google Places");
        googlePlaces.selectedProperty().addListener(new ChangeListener<Boolean>() 
        {
                public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean newVal)
                {
                    if(googlePlaces.isSelected())
                    {
                    	filter.add(googlePlaces.getText());
                        db.storeFilter(googlePlaces.getText());
                        System.out.println("Google Places selected");
                    }
                    else
                    {
                    	filter.remove(googlePlaces.getText());
                        db.storeFilter(googlePlaces.getText()).remove(googlePlaces.getText());
                        System.out.println("Google Places unselected");
                    }
                    
                }
        }
        );
        
        
        CheckBox foursquare = new CheckBox("FourSquare");
        foursquare.selectedProperty().addListener(new ChangeListener<Boolean>() 
        {
                public void changed(ObservableValue<? extends Boolean> obs, Boolean old, Boolean newVal)
                {
                    if(foursquare.isSelected())
                    {
                    		filter.add(foursquare.getText());
                        db.storeFilter(foursquare.getText());
                        System.out.println("Foursquare selected");
                    }
                    else
                    {
                    		filter.remove(foursquare.getText());
                        db.storeFilter(foursquare.getText()).remove(foursquare.getText());
                        //listOfFilters.remove(foursquare.getText());
                        System.out.println("Foursquare unselected");
                    }
                    
                }
        }
        );
        
        yelp.setTranslateX(-250);
        yelp.setTranslateY(375);
        
        tripAdvisor.setTranslateX(-125);
        tripAdvisor.setTranslateY(375);
        
        googlePlaces.setTranslateX(50);
        googlePlaces.setTranslateY(375);
        
        foursquare.setTranslateX(250);
        foursquare.setTranslateY(375);
        
        gp.add(yelp, 0, 0);
        gp.add(tripAdvisor, 0, 0);
        gp.add(googlePlaces, 0, 0);
        gp.add(foursquare, 0, 0);
        
        searchButton.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
               if(usernameLogin.getText() == "Not Logged In")
               {
            	   		try
            	   		{
            	   			db.storeGeneralSearch(tf.getText(), zipCodeOrCity.getText(), Integer.parseInt(rating), filter, price.getText());
            	   		}
            	   		catch(Exception e)
            	   		{
            	   			e.printStackTrace();
            	   		}
            	   		
               }
            }
        }
        );
        
        VBox root = new VBox(10);
        //root.getStylesheets().add(this.getClass().getResource("searchButton.css").toExternalForm());
        

        root.getChildren().addAll(searchButton, gp);
        
        s.setWidth(875);
        s.setHeight(500);
        s.show();
        Scene scene = new Scene(root);
        s.setScene(scene);
        
        
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
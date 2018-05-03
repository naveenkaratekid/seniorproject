package com.cogswell.seniorproject;

import java.sql.*;
import java.util.Date;
//import java.sql.Date;
import java.text.*;
import java.util.*;

import com.cogswell.seniorproject.APICaller.SearchType;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class SearchDAO
{
	private static String dr = "com.mysql.jdbc.Driver", url = "jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8";
    private static String name = "root", pwd = "39808";
	private static Connection c1;
	private static String username, firstName, lastName, password, email, address;
	private static int zipcode, distance;
	private static String rating;
	private static String price;
	private static String selectAddressFromGeneralResults = "select * from generalResults where searchID = ? order by address, siteName;";
	private static String selectAddressFromUserResults = "select * from userResults where searchID = ? order by address, siteName;";
	private static String selectFromUserResultsOffline = "select max(searchID) from userResults where searchName = ? and siteName = ? and username = ?;";
	private static String insertIntoUserResults = "insert into userResults values (?,?,?,?,?,?,?,?,?,?,?);";
	private static String insertIntoGeneralResult = "insert into generalResults values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
	private static String selectIDFromGeneralSearch = "select max(id) from generalSearch where name = ? and rating = ? and city = ?;";
	private static String selectIDFromUserSearch = "select max(id) from userSearch where username = ? and name = ? and rating = ? and zipcodeOrCity  = ?";
	private static String insertIntoPopularSearch = "insert into popularSearch values (?,?,?);";
	private static String selectFromUserProfile = "select * from userProfile where username = ? and password = ?;";
	private static String selectFromUserProfileDetails = "select * from userProfile where username = ?;";
	private static String insertIntoUserSearch = "insert into userSearch (name, zipcodeOrCity, rating, listOfSites, userID, price, date, username)values (?, ?, ?, ?, ?, ?, ?, ?)";
	private static String updateUserProfile = "update userProfile set username = ?, firstname = ?, lastName = ?, password = ?, zipcode = ?, email = ?, address = ? where username = ?;";	
	private static String insertIntoUserProfile = "insert into userProfile (username, firstName, lastName, password, zipcode, email, address) values (?, ?, ?, ?, ?, ?, ?);";
	private static String insertIntoGeneralSearch = "insert into generalSearch (name, rating, siteName, distance, city, price, date) values (?, ?, ?, ?, ?, ?, ?)";
	private static String selectFromUserProfileID = "select userID from userProfile where username = ?;";
	private static ArrayList<String> searchHistoryList = new ArrayList<String>();
	private static String getUserSearchHistory = "select * from userSearch where userID = ?;";
	private static String getCountOfUserSearch = "select count(*) from userSearch where userID = ? and username = ?;";
	private static String getCountOfGeneralSearch = "select count(*) from generalSearch where name = ?;";
	private static Date date = new Date();
    private static SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
	private static String dateStr = df.format(date);
	private static String selectFromUserProfileEnroll = "select * from userProfile where username = ?;";
	
	public SearchDAO()
    {
    		
    }
    
	
	public static String getPrice() {
		return price;
	}

	public static void setPrice(String price) {
		SearchDAO.price = price;
	}

	public static String getRating() {
		return rating;
	}

	public static void setRating(String rating) {
		SearchDAO.rating = rating;
	}

	public static int getDistance() {
		return distance;
	}

	public static void setDistance(int distance) {
		SearchDAO.distance = distance;
	}

	public static String getUsername() 
	{
		return username;
	}

	public static void setUsername(String username) 
	{
		SearchDAO.username = username;
	}


	public static String getFirstName() 
	{
		return firstName;
	}


	public static void setFirstName(String firstName) 
	{
		SearchDAO.firstName = firstName;
	}



	public static String getLastName() 
	{
		return lastName;
	}



	public static void setLastName(String lastName) 
	{
		SearchDAO.lastName = lastName;
	}



	public static String getPassword() 
	{
		return password;
	}



	public static void setPassword(String password) 
	{
		SearchDAO.password = password;
	}


	
	
	public static int getCountOfUserIDs(int userID)
	{
		try
		{
			c1 = getConnection();
   			PreparedStatement ps1 = c1.prepareStatement(getCountOfUserSearch);
			ps1.setInt(1, userID);
			ps1.setString(2, username);
			System.out.println(ps1);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			int count = rs1.getInt(1);
			System.out.println(count);
			return count;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return 0;
		}
	}
	
	public static int getUserID(String userName)
   	{
   		try
   		{
   			c1 = getConnection();
   			PreparedStatement ps1 = c1.prepareStatement(selectFromUserProfileID);
			ps1.setString(1, userName);
			ResultSet rs1 = ps1.executeQuery();
			rs1.next();
			int count = rs1.getInt(1);
			System.out.println(count);
			return count;
   		}
   		
   		catch (Exception e)
   		{
   			e.printStackTrace();
   			
   			return 0;
   		}	
   		finally
   		{
   			
   		}
   	}
	
	public static String getEmail() {
		return email;
	}



	public static void setEmail(String email) {
		SearchDAO.email = email;
	}



	public static String getAddress() {
		return address;
	}



	public static void setAddress(String address) {
		SearchDAO.address = address;
	}



	public static int getZipcode() {
		return zipcode;
	}



	public static void setZipcode(int zipcode) {
		SearchDAO.zipcode = zipcode;
	}

     
    public static void storeUserSearch(String userName, String searchName, String zipcodeOrCity, String rating, /*ArrayList<String>*/Set<String> listOfFilters, String price, String date)
    {
    		try
    		{			
    			   date = dateStr;
                c1 = getConnection();
                //String insertIntoUserSearch = "insert into userSearch values ('" + username + "', '" + searchName + "', '" + zipcodeOrCity + "', " + rating + " ,'" + listOfFilters.toString() + ", '" + price + "', '" + date + "');";
                PreparedStatement ps1 = c1.prepareStatement(selectFromUserProfileID);
                PreparedStatement ps = c1.prepareStatement(insertIntoUserSearch);
                ps1.setString(1, userName);
                System.out.println(ps1);
                
                int count = getCountOfUserSearch(searchName, userName);
                if(count > 3)
                {
                		storePopularSearch(searchName, count, userName);
                }
                
                ResultSet rs = ps1.executeQuery();
	    		    if(rs.next())
	    		    {
	    		    		int userID = rs.getInt(1);
	    		    		System.out.println(userID);
	    		    		ps.setString(1, searchName);
	    		    		ps.setString(2, zipcodeOrCity);
	    		    		ps.setString(3, rating);
	    		    		ps.setString(4, listOfFilters.toString());
	    		    		ps.setInt(5, userID);
	    		    		ps.setString(6, price);
	    		    		ps.setString(7, date);
	    		    		ps.setString(8, userName);
	    		    		
	    		    		
    	                System.out.println(ps);
    	                
    	                try
    	                {
    	                		ps.executeUpdate();
    	                }
    	                catch(Exception e)
    	                {
    	                		e.printStackTrace();
    	                } 		
	    		    }
	    		    else 
	    		    {
	    		    		System.out.println("empty");
	    		    }     
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		finally
    		{
    			try
    			{
    				c1.close();
    			}
    			catch(Exception e)
    			{
    				
    			}	
    		}
    }
    
    public static int getCountOfGeneralSearch(String searchName)
    {
    		int count = 0;
    		try
    		{
    			c1 = getConnection();
    			PreparedStatement ps = c1.prepareStatement(getCountOfGeneralSearch);
    			ps.setString(1, searchName);
    			ResultSet rs = ps.executeQuery();
    			if(rs.next())
    			{
    				count = rs.getInt("count(*)");
    				System.out.println(count);
    				return count;
    			}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		finally
    		{
    			try
    			{
    				c1.close();
    			}
    			catch(Exception e)
    			{
    				
    			}	
    		}

    		return count;
    }
    
    public static int getCountOfUserSearch(String searchName, String username)
    {
    		int count = 0;
    		try
    		{
    			c1 = getConnection();
    			PreparedStatement ps = c1.prepareStatement(getCountOfUserSearch);
    			ps.setString(1, searchName);
    			ps.setString(2,  username);
    			System.out.println(ps);
    			ResultSet rs = ps.executeQuery();
    			if(rs.next())
    			{
    				count = rs.getInt("count(*)");
    				System.out.println(count);
    				return count;
    			}
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    		}
    		finally
    		{
    			try
    			{
    				c1.close();
    			}
    			catch(Exception e)
    			{
    				
    			}	
    		}
    		return count;
    }
    
    public static void storePopularSearch(String searchName, int count, String username)
    {
    	try
		{
            c1 = getConnection();
            PreparedStatement ps = c1.prepareStatement(insertIntoPopularSearch);
            ps.setString(1, searchName);
            ps.setInt(2, count);
            ps.setString(3,  username);
            System.out.println(ps);
            //Statement s = c1.createStatement();
            try
            {
            		ps.execute();
            }
            catch(SQLException se)
            {
            		se.printStackTrace();
            }        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	    	finally
			{
				try
				{
					c1.close();
				}
				catch(Exception e)
				{
					
				}	
			}
    }
    
    
    public static UserProfile getUserProfileDetails(String username)
    {
    	try
		{
            c1 = getConnection();
            //String selectFromUserProfile = "select * from userProfile where username = '" + username + "';";
            PreparedStatement ps = c1.prepareStatement(selectFromUserProfileDetails);
            ps.setString(1,  username);
            System.out.println(ps);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
		    {
            		UserProfile up = new UserProfile();
            		up.setFirstName(rs.getString(2));
            		
            		up.setLastName(rs.getString(3));
            		
            		up.setPassword(rs.getString(4));
            		
            		up.setZipcode(rs.getInt(5));
            		
            		up.setEmail(rs.getString(6));
            		
            		up.setAddress(rs.getString(7));

		    		return up;
		    }
		    else 
		    {
		    		return null;
		    }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	    	finally
		{
			try
			{
				c1.close();
			}
			catch(Exception e)
			{
				
			}	
		}
    		return null;
    }
   
    
   public static void updateUserProfile(String username, String firstName, String lastName, String password, int zipcode, String email, String address)
    {
    		try
		{
            c1 = getConnection();
            PreparedStatement ps = c1.prepareStatement(updateUserProfile, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, username);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, password);
            ps.setInt(5, zipcode);
            ps.setString(6,  email);
            ps.setString(7, address);
            ps.setString(8, username);
            
            System.out.println(ps);
            
            try
            {
            		ps.executeUpdate();
            }
            catch(Exception e)
            {
            	
            }
            
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    		finally
    		{
    			try
    			{
    				c1.close();
    			}
    			catch(Exception e)
    			{
    				
    			}	
    		}
    }
    
   //public static ArrayList<String> getUserSearchHistory(int userID) throws Exception
   	public static ArrayList<String> getUserSearchHistory(String userName)
   	{
   		try
   		{
   			int userID = SearchDAO.getUserID(userName);
   			System.out.println(userID);
   			
   			int count = SearchDAO.getCountOfUserIDs(userID);
   			
   			PreparedStatement ps = c1.prepareStatement(getUserSearchHistory);
   			ps.setInt(1, userID);
   			System.out.println(ps);
			ResultSet rs = ps.executeQuery();
   			
			for(int i = 0; i < count; i++)
			{
				if(rs.next())
			    {
			    		String searchName = rs.getString(1);
			    		String zipcodeOrCity = rs.getString(2);
			    		String rating = rs.getString(3);
			    		String sites = rs.getString(4);
			    		String price = rs.getString(6);
			    		
			    		String search =  searchName + " | " + zipcodeOrCity + " | " + rating + " | " + sites + " | " + price;
			    		/*System.out.println(searchName);
			    		System.out.println(zipcodeOrCity);
			    		System.out.println(rating);
			    		System.out.println(sites);
			    		System.out.println(price);*/
			    		
			    		searchHistoryList.add(search);
			    		
			    		/*searchHistoryList.add(searchName);
			    		searchHistoryList.add(zipcodeOrCity);
			    		searchHistoryList.add(rating);
			    		searchHistoryList.add(sites);
			    		searchHistoryList.add(price);*/
			    		
			    }
			    else 
			    {
			    		return null;
			    }
				
			}
			/*if(rs.next())
		    {
		    		String searchName = rs.getString(1);
		    		String zipcodeOrCity = rs.getString(2);
		    		String rating = rs.getString(3);
		    		String sites = rs.getString(4);
		    		String price = rs.getString(6);
		    		
		    		System.out.println(searchName);
		    		System.out.println(zipcodeOrCity);
		    		System.out.println(rating);
		    		System.out.println(sites);
		    		System.out.println(price);
		    		
		    		searchHistoryList.add(searchName);
		    		searchHistoryList.add(zipcodeOrCity);
		    		searchHistoryList.add(rating);
		    		searchHistoryList.add(sites);
		    		searchHistoryList.add(price);
		    		return searchHistoryList;
		    }
		    else 
		    {
		    		return null;
		    }*/
   			
   			/*PreparedStatement ps = c1.prepareStatement(getUserSearchHistory);
   			ps.setInt(1, userID);
   			
   			ResultSet rs = ps.executeQuery();
		    
		    if(rs.next())
		    {
		    		String searchName = rs.getString(1);
		    		String zipcodeOrCity = rs.getString(2);
		    		String rating = rs.getString(3);
		    		String sites = rs.getString(4);
		    		String price = rs.getString(6);
		    		searchHistoryList.add(searchName);
		    		searchHistoryList.add(zipcodeOrCity);
		    		searchHistoryList.add(rating);
		    		searchHistoryList.add(sites);
		    		searchHistoryList.add(price);
		    	
		    }
		    else 
		    {
		    		return null;
		    }*/
			return searchHistoryList;	
   		}
   		catch(Exception e)
   		{
   			e.printStackTrace();
   			
   		}
   		finally
		{
			try
			{
				c1.close();
			}
			catch(Exception e)
			{
				
			}	
		}
   		return null;
   		
   	}
   	// This is used to show all reviews based on address from different sites
   	// This will return a result set
   	// The Jackson class will then iterate through the result set in the createHTML()
   	
   	public static ResultSet getResultsFromAddress(int ID, SearchType type)
   	{
   		try
   		{
   			PreparedStatement ps = null;
   			c1 = getConnection();
   			
   			switch(type)
   			{
   				case general:
   					ps = c1.prepareStatement(selectAddressFromGeneralResults);
   					ps.setInt(1, ID);
   					break;
   				case user:
   					ps = c1.prepareStatement(selectAddressFromUserResults);
   					ps.setInt(1,  ID);	
   					
   			}
   			
   			//PreparedStatement ps = c1.prepareStatement(selectAddressFromGeneralResults);
   			//ps.setInt(1, ID);
   			System.out.println(ps);
   			ResultSet rs = ps.executeQuery();
   			return rs;
   		}
   		
   		catch(Exception e)
   		{
   			return null;
   		}
   	}
   	
   	
   	
   	public static int getID(String term, String rating, String cityOrZip, SearchType type)
   	{
   		int col = 0;
   		try
   		{
   			c1 = getConnection();
   			PreparedStatement ps= null;
   			switch(type)
   			{
   				case general:
   					ps = c1.prepareStatement(selectIDFromGeneralSearch);
   					ps.setString(1, term);
   		   			ps.setString(2, rating);
   		   			ps.setString(3, cityOrZip);
   		   			System.out.println(ps);
   		   			break;
   		   			
   				case user:
   					ps = c1.prepareStatement(selectIDFromUserSearch);
   					ps.setString(1, username);
   					ps.setString(2, term);
   		   			ps.setString(3, rating);
   		   			ps.setString(4, cityOrZip);
   		   			System.out.println(ps);
   		   			break;
   			}	
   			ResultSet rs = ps.executeQuery();
   			if(rs.next())
   			{
   				col = rs.getInt(1);
   				return col;
   			}
   		}
   		catch(Exception e)
   		{
   			e.printStackTrace();
   		}
   		finally
		{
			try
			{
				c1.close();
			}
			catch(Exception e)
			{
				
			}	
		}
   		return col;
   	}
   	
   	public void storeUserResults(String username, String searchName, String results, String placeName, String avgRating, String priceRange, String phone, String address, String sourceName, String businessHours, int searchID)
   	{
   		try
		{
    			//System.out.println("Rating is: " + rating);
    			// String searchName, String results, String placeName, String avgRating, String priceRange, String phone, String address
            c1 = getConnection();
            PreparedStatement ps = c1.prepareStatement(insertIntoUserResults);
            
            ps.setString(1, username);
            ps.setString(2, searchName);
            ps.setString(3, results);
            ps.setString(4, placeName);
            ps.setString(5, avgRating);
            ps.setString(6, priceRange);
            ps.setString(7, phone);
            ps.setString(8,  address);
            ps.setString(9, sourceName);
            ps.setString(10,  businessHours);
            ps.setInt(11, searchID);
            
            System.out.println(ps);
            
            try
            {
            		ps.execute();
            }
            catch(SQLException se)
            {
            		se.printStackTrace();
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
   		finally
		{
			try
			{
				c1.close();
			}
			catch(Exception e)
			{
				
			}	
		}
   	}

   	   	public void storeGeneralResults(String searchName, String results, String placeName, String avgRating, String priceRange, String phone, String address, String sourceName, String businessHours, int searchID)
   	{
   		try
		{
    			
    			
            c1 = getConnection();
            PreparedStatement ps = c1.prepareStatement(insertIntoGeneralResult);
            ps.setString(1, searchName);
            ps.setString(2, results);
            ps.setString(3, placeName);
            ps.setString(4, avgRating);
            ps.setString(5, priceRange);
            ps.setString(6, phone);
            ps.setString(7, address);
            ps.setString(8, sourceName);
            ps.setString(9, businessHours);
            ps.setInt(10, searchID);
            
            System.out.println(ps);
            
            try
            {
            		ps.execute();
            }
            catch(SQLException se)
            {
            		
            		se.printStackTrace();
            }
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
   		finally
		{
			try
			{
				c1.close();
			}
			catch(Exception e)
			{
				
			}	
		}
   	}

   	public static int getResultsOffline(String searchName, String siteName, String username) throws Exception
   	{
   		int col = 0;
   		try
   		{
		    c1 = getConnection();
		    PreparedStatement ps = c1.prepareStatement(selectFromUserResultsOffline);
    			ps.setString(1, searchName);
    		    ps.setString(2, siteName);
    		    ps.setString(3, username);	
    		    System.out.println(ps);
    		    ResultSet rs = ps.executeQuery();
		    if(rs.next())
		    {
		    		col = rs.getInt(1);
		    		return col;
		    		
		    }
		   
   		}
	    catch(Exception e)
	    	{
	    		e.printStackTrace();
	    	}
   		finally
		{
			try
			{
				c1.close();
			}
			catch(Exception e)
			{
				
			}	
		}
	    	return col;
   	}
   		
   	
   	
   	
    /*
     *	storeGeneralSearch() will store the general search history onto the database. Note that this IS NOT pertained to the user. 
     */
    
    public static void storeGeneralSearch(String searchName, String zipcodeOrCity, int distance, String rating, /*ArrayList*/Set<String> listOfFilters, String price) throws Exception
    {
    		try
		{
    			
    			System.out.println("Rating is: " + rating);
    			
            c1 = getConnection();
            PreparedStatement ps = c1.prepareStatement(insertIntoGeneralSearch);
            ps.setString(1, searchName);
            ps.setString(2, rating);           
            ps.setString(3, listOfFilters.toString());
            ps.setInt(4, distance);
            ps.setString(5, zipcodeOrCity);
            ps.setString(6, price);
            ps.setString(7, dateStr);
            System.out.println(ps);
            
            int count = getCountOfGeneralSearch(searchName);
            if(count > 3)
            {
            		storePopularSearch(searchName, count, " ");
            }
            
            try
            {
            		ps.execute();
            }
            catch(Exception e)
            {
            		e.printStackTrace();
            }
            
                
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    		finally
    		{
    			try
    			{
    				c1.close();
    			}
    			catch(Exception e)
    			{
    				
    			}	
    		}
    }
    
    public static void accountExists()
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
	     
    }

    /*
     * storeUserProfile() will store the user's profile data containing:
     * firstName
     * lastName
     * password
     * zipcode
     * email
     * address
     * 
     * into the database table userProfile
     */
    public static void storeUserProfile(String username, String firstName, String lastName, String password, int zipcode, String email, String address) throws MySQLIntegrityConstraintViolationException
    {
        try
        {	
            c1 = getConnection();
            
            PreparedStatement ps1 = c1.prepareStatement(selectFromUserProfileEnroll);
            ps1.setString(1, username);
            System.out.println(ps1);
            ResultSet rs = ps1.executeQuery();
            
            if(!rs.next())
            {
            	    PreparedStatement ps = c1.prepareStatement(insertIntoUserProfile);
                ps.setString(1, username);
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, password);
                ps.setInt(5, zipcode);
                ps.setString(6, email);
                ps.setString(7, address);
                System.out.println(ps);
                //ps.execute(); 
            }
            
            PreparedStatement ps = c1.prepareStatement(insertIntoUserProfile);
            ps.setString(1, username);
            ps.setString(2, firstName);
            ps.setString(3, lastName);
            ps.setString(4, password);
            ps.setInt(5, zipcode);
            ps.setString(6,  email);
            ps.setString(7, address);
            System.out.println(ps);
            
            try
            {
            	 	ps.execute(); 
            }
            catch(Exception e)
            {
            		throw new MySQLIntegrityConstraintViolationException("This account already exists"); 		
            }   
        }
        catch(Exception e)
        {
        		//e.printStackTrace();
        		accountExists();
        }
        finally
		{
			try
			{
				c1.close();
			}
			catch(Exception e)
			{
				
			}	
		}
    }
    
    /*
     * getUserProfile() will look to see if there is a particular username matching what the user enters.
     * 
     * */
    public static String getUserProfile(String username, String password)
    {
	    	try
	    	{
		    c1 = getConnection();
		    //String selectFromUserProfile = "select * from userProfile where username = '" + username + "' and password = '" + password + "';";
		    PreparedStatement ps = c1.prepareStatement(selectFromUserProfile);
		    ps.setString(1, username);
		    ps.setString(2,  password);
		    ResultSet rs = ps.executeQuery();    
		    if(rs.next())
		    {
		    		String firstName = rs.getString(2);
		    		String lastName = rs.getString(3);
		    		String result = firstName + " " + lastName;
		    		System.out.println(result);
		    		return result;
		    }
		    else 
		    {
		    		return null;
		    }
	    	}
        catch(Exception e)
	    	{
        		e.printStackTrace();
	    	}
	    	return null;
    }
    
    
    public static Connection getConnection() throws Exception
    {
        try 
        {
            Class.forName(dr);
            
            c1 = DriverManager.getConnection(url, name, pwd);
            
            return c1;
        }
        
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        return null;
    }
    
    public static ArrayList<String> numOfTimesSearched(String curDate) throws Exception
    {
    		try
    		{
    			ArrayList<String> list = new ArrayList<String>();
    			c1 = getConnection();
    		    //String numOfTimesSearchedCall = "call numOfTimesSearched (@" + curDate + ", @" + year + ", @" + name + ", @" + count + ");";
    		    String numOfTimesSearchedCall = "{call numOfTimesSearched (?,?,?,?)};";

    		    CallableStatement cs = c1.prepareCall(numOfTimesSearchedCall);
    		    cs.setString(1,  curDate);
    		    cs.registerOutParameter(2, java.sql.Types.VARCHAR);
    		    cs.registerOutParameter(3, java.sql.Types.VARCHAR);
    		    cs.registerOutParameter(4, java.sql.Types.INTEGER);
    		    
    		    ResultSet rs = cs.executeQuery();
    		    while(rs.next())
    		    {
    		    		String yearOut = rs.getString(1);
        		    String username = rs.getString(2);
        		    int counter = rs.getInt(3);
        		    
        		    System.out.println(yearOut);
        		    System.out.println(username);
        		    System.out.println(curDate);
        		    
        		    list.add(username);
        		    list.add(yearOut);
        		    list.add(Integer.toString(counter));
    		    }
    		    
    		    return list;
    		}
    		catch(Exception e)
    		{
    			e.printStackTrace();
    			return null;
    		}
    		finally
    		{
    			try
    			{
    				c1.close();
    			}
    			catch(Exception e)
    			{
    				
    			}	
    		}
    }
    
    public static void main(String[] args) throws Exception
    {
        getConnection();
    }
}

import java.sql.*;
import java.util.*;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
public class SearchDAO
{
    // instance variables - replace the example below with your own
	private static List<String> listOfFilteredSites;
	private static String dr = "com.mysql.jdbc.Driver", url = "jdbc:mysql://localhost:3306/test";
    private static String name = "root", pwd = "39808";
	private static Connection c1;
	private static String username, firstName, lastName, password, email, address;
	private static int zipcode, distance;
	private static String rating;
	
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

	
    public SearchDAO()
    {
        
    		listOfFilteredSites = new ArrayList<String>();
    }
    
    /*
     * storeFilter() will store the site name if the site filter is checked by the user via checkbox.
     * 
     * */
    public static List<String> storeFilter(String siteName)
    {
    		listOfFilteredSites.add(siteName);
    		return listOfFilteredSites;
    }
    
    public static void storeUserSearch(String username, String searchName, String zipcodeOrCity, int rating, ArrayList<String> listOfFilters)
    {
    		try
    		{
                c1 = getConnection();
                String insertIntoUserSearch = "insert into userSearch values ('" + username + "', '" + searchName + "', '" + zipcodeOrCity + "', " + rating + " ,'" + listOfFilters.toString() + "');";
                PreparedStatement ps = c1.prepareStatement(insertIntoUserSearch);
                //Statement s = c1.createStatement();
                
                
                int rs = ps.executeUpdate(insertIntoUserSearch);
                //int rs = s.executeUpdate(insertIntoUserProfile);
               
    		}
    		catch(Exception e)
    		{
    			
    		}
    }
    
    public static void storePopularSearch(String search)
    {
    	try
		{
            c1 = getConnection();
            String insertIntoPopularSearch = "insert into userSearch values ('" + search + "');";
            PreparedStatement ps = c1.prepareStatement(insertIntoPopularSearch);
            //Statement s = c1.createStatement();
            
            
            int rs = ps.executeUpdate(insertIntoPopularSearch);
            //int rs = s.executeUpdate(insertIntoUserProfile);
           
		}
		catch(Exception e)
		{
			
		}
    		
    }
    
    /*
     *	storeGeneralSearch() will store the general search history onto the database. Note that this IS NOT pertained to the user. 
     */
    
    public static void storeGeneralSearch(String searchName, String zipcodeOrCity, int rating, ArrayList<String> listOfFilters) throws Exception
    {
    	try
		{
            c1 = getConnection();
            String insertIntoUserSearch = "insert into searchHistory values ('" + searchName + "', '" + zipcodeOrCity + "', " + rating + " ,'" + listOfFilters.toString() + "');";
            PreparedStatement ps = c1.prepareStatement(insertIntoUserSearch);
            //Statement s = c1.createStatement();
            
            
            int rs = ps.executeUpdate(insertIntoUserSearch);
            //int rs = s.executeUpdate(insertIntoUserProfile);
           
		}
		catch(Exception e)
		{
			
		}
    }
    
    
    public static void storeUserProfile(String username, String firstName, String lastName, String password, int zipcode, String email, String address) throws Exception
    {
        try
        {
            c1 = getConnection();
            String insertIntoUserProfile = "insert into userProfile values ('" + username + "', '" + firstName + "', '" + lastName + "', '" + password + "', " + zipcode + ", '" + email + "', '" + address + "');";
            PreparedStatement ps = c1.prepareStatement(insertIntoUserProfile);
            Statement s = c1.createStatement();
            
            try
            {
            	 int rs = s.executeUpdate(insertIntoUserProfile);
            }
            catch(MySQLIntegrityConstraintViolationException me)
            {
            		throw new Exception("This email already exists");
            }
            
            //ResultSet rs = s.executeQuery(insertIntoUserProfile);
           
           
            
           /* if(rs.next())
            {
            		String selectUserNameFromUserProfile = "select * from userProfile where exists (select * from userProfile where username = '" + username + "');";
            		ps = c1.prepareStatement(selectUserNameFromUserProfile);
            		rs = ps.executeQuery(selectUserNameFromUserProfile);
            }
            
            if(rs.next())
            {
            		String selectEmailFromUserProfile = "select * from userProfile where exists (select * from userProfile where email = '" + email + "');";
            		ps = c1.prepareStatement(selectEmailFromUserProfile);
            		rs = ps.executeQuery(selectEmailFromUserProfile);
            }*/
            
           /* else
            {
            		if(rs.equals(username))
            		{
            			System.out.println("Username is taken");
            		}
            		if(rs.equals(email))
            		{
            			System.out.println("An account associated with " + email + " already exists");
            		}
            		
            }*/
            	

            /*while(rs.next())
            {
            		
            }*/
            
        }
        catch(Exception e)
        {
            System.out.println(e);
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
		    String selectFromUserProfile = "select * from userProfile where username = '" + username + "' and password = '" + password + "';";
		    Statement s = c1.createStatement();
		    ResultSet rs = s.executeQuery(selectFromUserProfile);
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
    
    
    public static void main(String[] args) throws Exception
    {
        getConnection();
    }
}

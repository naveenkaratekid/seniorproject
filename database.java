import java.sql.*;
import java.util.*;
public class database
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
		database.rating = rating;
	}

	public static int getDistance() {
		return distance;
	}

	public static void setDistance(int distance) {
		database.distance = distance;
	}

	public static String getUsername() 
	{
		return username;
	}

	public static void setUsername(String username) 
	{
		database.username = username;
	}


	public static String getFirstName() 
	{
		return firstName;
	}



	public static void setFirstName(String firstName) 
	{
		database.firstName = firstName;
	}



	public static String getLastName() 
	{
		return lastName;
	}



	public static void setLastName(String lastName) 
	{
		database.lastName = lastName;
	}



	public static String getPassword() 
	{
		return password;
	}



	public static void setPassword(String password) 
	{
		database.password = password;
	}



	public static String getEmail() {
		return email;
	}



	public static void setEmail(String email) {
		database.email = email;
	}



	public static String getAddress() {
		return address;
	}



	public static void setAddress(String address) {
		database.address = address;
	}



	public static int getZipcode() {
		return zipcode;
	}



	public static void setZipcode(int zipcode) {
		database.zipcode = zipcode;
	}

	
    public database()
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
    
    public static void storeUserSearch(String searchName, String zipcodeOrCity, String rating, ArrayList<String> listOfFilters)
    {
    		try
    		{
    			Class.forName(dr);
                
                c1 = DriverManager.getConnection(url, name, pwd);
                String insertIntoUserProfile = "insert into userSearch values ('" + username + "', '" + searchName + "', '" + zipcodeOrCity + "', '" + rating + "');";
                PreparedStatement ps = c1.prepareStatement(insertIntoUserProfile);
                //Statement s = c1.createStatement();
                
                
                ResultSet rs = ps.executeQuery(insertIntoUserProfile);
                //int rs = s.executeUpdate(insertIntoUserProfile);
               
                
                if(rs.next())
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
                }
    		}
    		catch(Exception e)
    		{
    			
    		}
    }
    
    public static void storeUserProfile(String username, String firstName, String lastName, String password, int zipcode, String email, String address) throws Exception
    {
        try
        {
            Class.forName(dr);
            
            c1 = DriverManager.getConnection(url, name, pwd);
            String insertIntoUserProfile = "insert into userProfile values ('" + username + "', '" + firstName + "', '" + lastName + "', '" + password + "', " + zipcode + ", '" + email + "', '" + address + "') where not exists (select username from userProfile where username = " + username + ");";
            PreparedStatement ps = c1.prepareStatement(insertIntoUserProfile);
            //Statement s = c1.createStatement();
            
            
            //ResultSet rs = s.executeQuery(insertIntoUserProfile);
            int rs = ps.executeUpdate(insertIntoUserProfile);
           
            
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
    public static Connection getUserProfile(String username, String password)
    {
	    	try
	    	{
		    	Class.forName(dr);        
		    c1 = DriverManager.getConnection(url, name, pwd);
		    String selectFromUserProfile = "select * from userProfile where username = '" + username + "' and password = '" + password + "';";
		    Statement s = c1.createStatement();
		    ResultSet rs = s.executeQuery(selectFromUserProfile);
		    if(rs.next())
		    {
		    		
		    }
		    return c1;
	    	}
        catch(Exception e)
	    	{
        		System.out.println(e);
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

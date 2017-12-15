import java.sql.*;
import java.util.Date;
//import java.sql.Date;
import java.text.*;
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
	private static String price;
	private static Date date;
	/*private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy h::mm a");
	static String dateStr = df.format(date);*/
	
	public static Date getDate() {
		return date;
	}

	public static void setDate(Date date) {
		SearchDAO.date = date;
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
    
    public static void storeResults(String result)
    {
    	try
		{
            c1 = getConnection();
            String insertIntoResult = "insert into userSearch values ('" + result + "');";
            PreparedStatement ps = c1.prepareStatement(insertIntoResult);
            //Statement s = c1.createStatement();
            
            
            int rs = ps.executeUpdate(insertIntoResult);
            //int rs = s.executeUpdate(insertIntoUserProfile);
           
		}
		catch(Exception e)
		{
			
		}
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
    
    public static void storeUserSearch(String username, String searchName, String zipcodeOrCity, int rating, ArrayList<String> listOfFilters, String price, String date)
    {
    		try
    		{
    			    //date = dateStr;
                c1 = getConnection();
                String insertIntoUserSearch = "insert into userSearch values ('" + username + "', '" + searchName + "', '" + zipcodeOrCity + "', " + rating + " ,'" + listOfFilters.toString() + ", '" + price + "', '" + date + "');";
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
    
    
    public static userProfile getUserProfileDetails(String username)
    {
    	try
		{
            c1 = getConnection();
            String selectFromUserProfile = "select * from userProfile where username = '" + username + "';";
            PreparedStatement ps = c1.prepareStatement(selectFromUserProfile);
            ResultSet rs = ps.executeQuery(selectFromUserProfile);
            if(rs.next())
		    {
            		userProfile up = new userProfile();
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
			
		}
    		return null;
    }
   
    
   public static void updateUserProfile(String username, String firstName, String lastName, String password, int zipcode, String email, String address)
    {
    		try
		{
    			System.out.println(username);
    			System.out.println(firstName);
    			System.out.println(lastName);
    			System.out.println(password);
    			System.out.println(zipcode);
    			System.out.println(email);
    			System.out.println(address);
            c1 = getConnection();
            String insertIntoUserProfile = "update userProfile set firstname = '" + firstName + "', lastName = '" + lastName + "', password = '" + password + "', zipcode = " + zipcode + ", email = '" + email + "', address = '" + address + "';";
            PreparedStatement ps = c1.prepareStatement(insertIntoUserProfile);
            //Statement s = c1.createStatement();
            System.out.println(insertIntoUserProfile);
            
            int rs = ps.executeUpdate(insertIntoUserProfile);
            //int rs = s.executeUpdate(insertIntoUserProfile);
           
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
    }
    
    /*
     *	storeGeneralSearch() will store the general search history onto the database. Note that this IS NOT pertained to the user. 
     */
    
    public static void storeGeneralSearch(String searchName, String zipcodeOrCity, int rating, ArrayList<String> listOfFilters, String price) throws Exception
    {
    	try
		{
            c1 = getConnection();
            String insertIntoUserSearch = "insert into generalSearch values ('" + searchName + "', '" + zipcodeOrCity + "', " + rating + " ,'" + listOfFilters.toString() + ", '" + price + "');";
            PreparedStatement ps = c1.prepareStatement(insertIntoUserSearch);
            //Statement s = c1.createStatement();
            
            
            int rs = ps.executeUpdate(insertIntoUserSearch);
            //int rs = s.executeUpdate(insertIntoUserProfile);
           
		}
		catch(Exception e)
		{
			
		}
    }
    
    
    public static void storeUserProfile(String username, String firstName, String lastName, String password, int zipcode, String email, String address) throws MySQLIntegrityConstraintViolationException
    {
        try
        {
        		int i = 0; // ID
            c1 = getConnection();
            String insertIntoUserProfile = "insert into userProfile values ('" + username + "', '" + firstName + "', '" + lastName + "', '" + password + "', " + zipcode + ", '" + email + "', '" + address + "'," + i + ");";
           // PreparedStatement ps = c1.prepareStatement(insertIntoUserProfile);
            Statement s = c1.createStatement();
            
            try
            {
            	 int rs = s.executeUpdate(insertIntoUserProfile);
            	 
            }
            catch(MySQLIntegrityConstraintViolationException me)
            {
            		throw new Exception("This account already exists");
            }
            
            
            i++;
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
    }
    
    public static void main(String[] args) throws Exception
    {
        getConnection();
    }
}

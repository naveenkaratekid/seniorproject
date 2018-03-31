package com.cogswell.seniorproject;

import java.awt.Desktop;
import java.io.File;
//import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

//import java.util.Map.Entry;
import okhttp3.*;
//import okio.*;
//import org.json.*;
//import java.net.*;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//import java.text.*;
//import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerationException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonToken;
//import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestJackson 
{
	
	public static void testFoursquare(String username, String search, String location) throws Exception
    {
        // Foursquare OAuth2 access token: P14YL1YOGUDWTKBWER14IDBWV4BYBW4ASKCC2GRGDY3F5BZQ
        OkHttpClient client = new OkHttpClient();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String date = sdf.format(new Date());
        String url = "https://api.foursquare.com/v2/venues/search?near=" + location + "&query=" + search + "&v=" + date + "&client_secret=SNZBRYNWZ0YPETJKXGW4LGGEX0KHM2Q2JOOJJH1UBAJ2CQD0&client_id=RZRA3MOZSBX1R3Y1ZUFXUO3YT2BSKBWSSRKXX3G3LKUQDBEU";
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        JSONObject jo = new JSONObject(s);
        JSONObject jo1 = jo.getJSONObject("response");
       
        ArrayList<String> list = new ArrayList<String>();
        String a,b,c,d;
        try
        {
             JSONArray ja = jo1.getJSONArray("venues");
             for(int i = 0; i < ja.length(); i++)
             {
                 JSONObject jo2 = ja.getJSONObject(i);
                 JSONObject jo5 = jo2.getJSONObject("location");
                 JSONObject jo4 = jo2.getJSONObject("contact");
                 
                 try
                 {
                     b = jo2.getString("name");
                     System.out.println("Name is: " + b);
                     list.add(b);
                 }
                 catch(Exception e)
                 {
                     System.out.println("No name");
                     e.printStackTrace();
                 }
                 
                 try
                 {
                     c = jo5.getJSONArray("formattedAddress").toString();
                     System.out.println("Address is: " + c);
                     list.add(c);
                 }
                 catch(Exception e)
                 {
                     System.out.println("No address");
                 }
                 
                 try
                 {
                     d = jo4.getString("formattedPhone");
                     System.out.println("Phone number is: " + d);
                     list.add(d);
                 }
                 catch(Exception e)
                 {
                     System.out.println("No phone number");
                 }
                 
                 try
                 {
                     a = jo2.getString("id");
                     System.out.println("Id is: " + a);
                     list.add(a);
                     
                 }
                 catch(Exception e)
                 {
                     System.out.println("No id");
                 }
                 System.out.println();
             }
             System.out.println();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        foursquareReview(username, list);  
    }
        
	
	public static void foursquareReview(String username, List<String> list) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Foursquare Reviews-------------");
        String url = "";
        
        for(String id: list)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
            String date = sdf.format(new Date());
            
            url = "https://api.foursquare.com/v2/venues/"+id+"/tips?oauth_token=P14YL1YOGUDWTKBWER14IDBWV4BYBW4ASKCC2GRGDY3F5BZQ&v="+date; 
            Request request = new Request.Builder()
              .url(url)
              .get()
              .build();
            
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            System.out.print(id + "\n");
            JSONObject jo = new JSONObject(s);
            String b; 
            
            try
            {
            		JSONObject jo11 = jo.getJSONObject("meta");
    	        		if(jo11.getInt("code") == 400)
    	        		{
    	        			throw new JSONException("Quota rate exceeded");
    	        		}
	            	
                JSONObject jo1 = jo.getJSONObject("response").getJSONObject("tips");
                JSONArray ja = jo1.getJSONArray("items");
                for(int i = 0; i < ja.length(); i++)
                {
                    JSONObject jo2 = ja.getJSONObject(i);
                    b = jo2.getString("text"); 
                    System.out.println(b + "\n");               
                }
                System.out.println("-------------------------------");
            }
            catch(Exception e)
            {

            }
        }
    }

	///////////////////////////////////////////////////////////////////////////////
	public static void testYelp(String username, String term, String location, int rating) throws Exception
	{
		OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Yelp Test-------------");
        String url = "https://api.yelp.com/v3/businesses/search?term="+term+"&location="+location;
        
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .build();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        
        try {

			ObjectMapper mapper = new ObjectMapper();

			JsonNode jsonNode = mapper.readTree(s);
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("businesses");
			for(int i = 0 ; i < ln2.size(); i++)
			{
				List<JsonNode> ln3 = (ArrayList<JsonNode>)jsonNode.findValues("id");
				List<JsonNode> ln4 = (ArrayList<JsonNode>)jsonNode.findValues("name");
				List<JsonNode> ln5 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
				List<JsonNode> ln6 = (ArrayList<JsonNode>)jsonNode.findValues("price");
				List<JsonNode> ln7 = (ArrayList<JsonNode>)jsonNode.findValues("display_address");
				List<JsonNode> ln8 = (ArrayList<JsonNode>)jsonNode.findValues("display_phone");
				for(int j = 0; j < ln3.size(); j++)
				{
					System.out.println("---------------------------------------------");
					System.out.println("ID: " + ln3.get(j) + "\n\nName" + ln4.get(j) + "\n\nAverage Rating: "+ ln5.get(j) + "\n\nPrice Range: " + ln6.get(j) + "\n\nAddress: " + ln7.get(j) + "\n\nPhone Number: " + ln8.get(j) );
					System.out.println();
										
					yelpReview(username, ln3.get(j).toString().replace("\"", ""), rating);
				}  
			}
		} 
        catch (JsonGenerationException e) 
        {
			e.printStackTrace();
		} 
        catch (JsonMappingException e) 
        {
			e.printStackTrace();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}
	}
	
	public static void yelpReview(String username, String ID, int rating) throws Exception
    {
		OkHttpClient client = new OkHttpClient();
        String url = "https://api.yelp.com/v3/businesses/" + ID + "/reviews"; 
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .build();

        //
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        try {
        		Map<String, Integer> review = new HashMap<String, Integer>();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(s);
			
			List<JsonNode> ln2 = jsonNode.findValues("reviews");
			
			int a = 0;
			for(int i = 0; i < ln2.size(); i++)
			{
			    List<JsonNode> ln3 = (ArrayList<JsonNode>) jsonNode.findValues("rating");
			    List<JsonNode> ln4 = (ArrayList<JsonNode>) jsonNode.findValues("text");
	    			List<JsonNode> ln5 = (ArrayList<JsonNode>) jsonNode.findValues("time_created");
	    			
			    for(int j = 0; j < ln4.size(); j++)
			    {
			    	 	a = Integer.parseInt(ln3.get(j).toString());
			    		review.put(ln4.get(j).toString() + "  " + ln5.get(j).toString(), a);
			    }
			    
			    for(Map.Entry<String, Integer> m1: review.entrySet())
		    		{
		    			String key = m1.getKey();
		    			double value = m1.getValue();
		    			if(value >= rating)
		    			{
		    				System.out.println(getStar((int) value) + " : " + key);
		    			}
		    		}
			}
			System.out.println();

		} 
        catch (JsonGenerationException e) 
        {
			e.printStackTrace();
		} 
        catch (JsonMappingException e) 
        {
			e.printStackTrace();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}   
    }
	
    public static void testGoogle(String username, String searchTerm, String location, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        String query = searchTerm + " " + location;
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+query+"&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw";
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        try {

			ObjectMapper mapper = new ObjectMapper();

			JsonNode jsonNode = mapper.readTree(s);
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("formatted_address");
			List<JsonNode> ln3 = (ArrayList<JsonNode>)jsonNode.findValues("place_id");
			List<JsonNode> ln4 = (ArrayList<JsonNode>)jsonNode.findValues("name");
			List<JsonNode> ln5 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
			for(int i = 0 ; i < ln5.size(); i++)
			{
			    System.out.println("Address: " + ln2.get(i) + "\n" + ln3.get(i) + "\n"+ ln4.get(i) + "\n" + "Average Rating: " + ln5.get(i) );
			    System.out.println();
			    googleReview(username, ln3.get(i).toString().replace("\"", ""), rating);
			}
		} 
        catch (JsonGenerationException e) 
        {
			e.printStackTrace();
		} 
        catch (JsonMappingException e) 
        {
			e.printStackTrace();
		} 
        catch (IOException e)
        {
			e.printStackTrace();
		}  
    }
    
    public static void googleReview(String username, String placeID, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        String url = String.format("https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw", placeID);
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        //
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        try 
        {
        		WriteToHTML wth = new WriteToHTML();
        		ArrayList<String> placeInfo = new ArrayList<String>();
        		Map<String, Double> review = new HashMap<String, Double>();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(s);
			List<JsonNode> ln1 = (ArrayList<JsonNode>)jsonNode.findValues("formatted_phone_number");
			List<JsonNode> ln5 = (ArrayList<JsonNode>)jsonNode.findValues("formatted_address");
			
			
			// Here write header of HTML file
			StringBuilder hsb = new StringBuilder();
			String fileName = "testfile.html";
			hsb.append("<html><head><title>Results</title></head>");
            hsb.append("<h1>Results</h1>");
            hsb.append("<p>__________________________________________________________</p>");           
            hsb.append("<h2>Place information</h2>");
            
			for(int i = 0; i < ln1.size(); i++)
			{
				System.out.println("Phone Number: " + ln1.get(i));
				System.out.println("Address: " + ln5.get(i));
				//placeInfo.add(ln1.get(i) + " " + ln5.get(i));	
				hsb.append("<h2>" + ln1.get(i) + "</h2>");
				hsb.append("<h3>" + ln5.get(i) + "</h3>");
			}
			
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("reviews");
			double a = 0;
			
			/*------------------------------------------------------------------------------------*/
			hsb.append("<body>");
			for(int i = 0; i < ln2.size(); i++)
			{
			    List<JsonNode> ln3 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
	    			List<JsonNode> ln4 = (ArrayList<JsonNode>)jsonNode.findValues("text");
	    			
			    for(int j = 0; j < ln4.size(); j++)
			    {
			    	 	a = Double.parseDouble(ln3.get(j+1).toString());
			    		review.put(ln4.get(j).toString(), a);
			    }
			    
			    for(Map.Entry<String, Double> m1: review.entrySet())
		    		{
		    			String key = m1.getKey();
		    			double value = m1.getValue();
		    			if(value >= rating)
		    			{
		    				String item = getStar((int) value) + " : " + key;
		    				//System.out.println(getStar((int) value) + " : " + key);
		    				//System.out.println(item);
		    				
		    				//wth.testHTML(username, item, placeInfo);
		    				// Here write body of HTML into same file
		    				//append body
		    				hsb.append("<p>" + item + "</p>");
		    			}
		    		} 
			    hsb.append("<p>------------------------------------------------------------------------</p>");
			}

			// Here write footer of HTML			
			hsb.append("</body></html>");
			wth.WriteToFile(hsb.toString(),fileName);
            File f = new File(fileName);
            Desktop.getDesktop().browse(f.toURI());
			// do browser call here
			//System.out.println();
			//System.out.println("---------------------------------------------------------------------");
			/*for(String id: placeInfo)
			{
				System.out.println(id);
			}*/
		} 
        catch (JsonGenerationException e) 
        {
			e.printStackTrace();
		} 
        catch (JsonMappingException e) 
        {
			e.printStackTrace();
		} 
        catch (IOException e) 
        {
			e.printStackTrace();
		}   
    }
    
    
    /*
     * 	getStar() returns the ★ string based on number rating
     */
    public static String getStar(int rating)
    {
        switch(rating)
        {
            case 1:
                return "★";
            case 2:
                return "★★";
            case 3:
                return "★★★";
            case 4:
                return "★★★★";
            case 5:
                return "★★★★★";
            default:
                return "★★★";
        }
    }
    
    /*
     *	getRating() returns the rating number based on the ★ string
     */
    public static int getRating(String rating)
    {
        switch(rating)
        {
            case "★":
                return 1;
            case "★★":
                return 2;
            case "★★★":
                return 3;
            case "★★★★":
                return 4;
            case "★★★★★":
                return 5;
            default:
                return 3;
        }
    }
    
    public static int getPrice(String price)
    {
        switch(price)
        {
            case "$":
            return 1;
            
            case "$$":
            return 2;
            
            case "$$$":
            return 3;

            default:
            return 1;
        }
    }
   
    public static String getPriceSymbol(int price)
    {
        switch(price)
        {
            case 1:
            return "$";
            
            case 2:
            return "$$";
            case 3:
            return "$$$";       
            
            case 4:
            return "$$$$";
            
            default:
            return "$";
        }
    }
    
    
    public static void search(String username, String searchTerm, String cityOrZip, String rating, int distance, int price, Set<String> listOfFilters) throws Exception
    {
        int r = getRating(rating);
        for(String searchFilterName: listOfFilters)
        {
            switch(searchFilterName.toLowerCase())
            {
                case "yelp":
                    //testYelp(searchTerm, cityOrZip, r, price, distance);
                    testYelp(username, searchTerm, cityOrZip, r);
                    break;
                    
                case "foursquare":
                    testFoursquare(username, searchTerm, cityOrZip);
                    break;
                    
                case "google": case "google places":
                    testGoogle(username, searchTerm, cityOrZip, r);
                    break;
                    
                default:
                    System.out.println("Sorry. Your search criteria is not valid");
                    break;
            }
        }
    }
    
    public static void main(String[] args) throws Exception
    {
        // TODO Auto-generated method stub
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a search term: ");
        String term = scanner.nextLine();
        
        System.out.println("Enter a location (City | zipcode, | specific address: ");
        String location = scanner.nextLine();
        
        System.out.println("Filter by rating: ");
        int rating = scanner.nextInt();
        
        testGoogle("", term, location, rating);
        //testFoursquare(term, location);
        //testYelp(term, location, rating);
        scanner.close();
        //googleReview();
    }
    

}

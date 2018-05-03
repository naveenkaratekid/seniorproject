package com.cogswell.seniorproject;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TextNode;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APICaller 
{	
	public static SearchDAO db = new SearchDAO();
	static OkHttpClient client = new OkHttpClient();
	public static enum SearchType {user, general};
	
	public static int foursquareSearch(String username, String searchName, String location, String rating) throws Exception
    {
		
        // Foursquare OAuth2 access token: P14YL1YOGUDWTKBWER14IDBWV4BYBW4ASKCC2GRGDY3F5BZQ
        
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String date = sdf.format(new Date());
        Response response = null;
        int idFromResults = 0;
        String url = "https://api.foursquare.com/v2/venues/search?near=" + location + "&query=" + searchName + "&v=" + date + "&client_secret=SNZBRYNWZ0YPETJKXGW4LGGEX0KHM2Q2JOOJJH1UBAJ2CQD0&client_id=RZRA3MOZSBX1R3Y1ZUFXUO3YT2BSKBWSSRKXX3G3LKUQDBEU";
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        
        try
        {
        		response = client.newCall(request).execute();
        }
        catch(Exception e)
        {
        		System.out.println("No connection");
        		idFromResults = db.getResultsOffline(searchName, "Foursquare", username);
        		return idFromResults;
        		
        }
       
        String s = response.body().string();
        JSONObject jo = new JSONObject(s);
        JSONObject jo1 = jo.getJSONObject("response");
        
        String id,name,address,phone;
        try
        {
             JSONArray ja = jo1.getJSONArray("venues");
             for(int i = 0; i < ja.length(); i++)
             {
                 JSONObject jo2 = ja.getJSONObject(i);
                 JSONObject jo5 = jo2.getJSONObject("location");
                 JSONObject jo4 = jo2.getJSONObject("contact"); 
             
                
                	 	 id = jo2.getString("id");
                     System.out.println("Id is: " + id);
                     
                     
                     name = jo2.getString("name");
                     System.out.println("Name is: " + name);
                    
                     
                     address = jo5.getJSONArray("formattedAddress").toString().replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\"", "").replaceAll("Parkway", "Pkwy").replaceAll("\\(.*\\)", "");
                     System.out.println("Address is: " + address);
                     
                     phone = jo4.isNull("formattedPhone") ? "No phone number available" : jo4.getString("formattedPhone");
                     System.out.println("Phone number is: " + phone);
                     
                     idFromResults = foursquareReview(searchName, location, username, id, name, address, phone, rating);
             }

	         System.out.println();

             System.out.println();
             
             System.out.println();
             return idFromResults;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return idFromResults;
    }

	public static int foursquareReview(String searchName, String location, String username, String id, String placeName, String address, String phone, String rating) throws Exception
    {
        
        System.out.println("-------------Foursquare Reviews-------------");
        String url = "";
        
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String date = sdf.format(new Date());
        int idFromSearch = 0;
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
        
        JSONArray ja = jo.getJSONObject("response").getJSONObject("tips").getJSONArray("items");
        
        if(username == null || username.isEmpty() || username.equals("Not Logged In"))
        {
        		idFromSearch = db.getID(searchName, rating, location, SearchType.general);
        }
        else
        {
        		idFromSearch = db.getID(searchName, rating, location, SearchType.user);	
        }
        
        for(int i = 0; i < ja.length(); i++)
        {
            JSONObject jo2 = ja.getJSONObject(i);
            b = jo2.getString("text");   
            System.out.println(b);
            
            if(username == null || username.isEmpty() || username.equals("Not Logged In"))
            {
                db.storeGeneralResults(searchName, b, placeName, "", "", phone, address, "Foursquare", "", idFromSearch);   
            }
            else
            {
				db.storeUserResults(username, searchName, b, placeName, "", "", phone, address, "Foursquare", "", idFromSearch);
            }
        }    
        System.out.println("-------------------------------");
        System.out.println();
        return idFromSearch;
    }

	///////////////////////////////////////////////////////////////////////////////
	public static int yelpSearch(String username, String term, String location, int rating, String priceRangeStr) throws Exception
	{
		int price = getPrice(priceRangeStr);
		System.out.println("-------------Yelp Test-------------");
        String url = "https://api.yelp.com/v3/businesses/search?term="+term+"&location="+location + "&price="+price;
        Response response = null;
        String searchTerm = term + "-" + location;
        int idFromResults = 0;
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .build();

        try
        {
        		response = client.newCall(request).execute();
        }
        
        catch(Exception e)
        {
        		System.out.println("No connection");
        		idFromResults = db.getResultsOffline(searchTerm, "Yelp", username);
        		return idFromResults;
        		
        }
        
        String s = response.body().string();
        
        try 
        {
			ObjectMapper mapper = new ObjectMapper();
			double ratingAvg = 0;
			Map<String, Double> avgRatingMap = new HashMap<String, Double>();
			JsonNode jsonNode = mapper.readTree(s);
			
			List<JsonNode> ln5 = new ArrayList<JsonNode>();
			List<JsonNode> ln6 = new ArrayList<JsonNode>();
			
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("businesses");
			List<JsonNode> ln3 = (ArrayList<JsonNode>)jsonNode.findValues("id");
			List<JsonNode> ln4 = (ArrayList<JsonNode>)jsonNode.findValues("name");

			if(jsonNode.findValues("rating") != null) {
			
				if(!(jsonNode.findValues("rating").equals(Collections.<Object>emptyList())))
				{
					ln5 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
					System.out.println(ln5.size());
				}
			}
			
			else
			{
				System.out.println("No average rating available");
			}
			
			
			if(!(jsonNode.findValues("price").equals(Collections.<Object>emptyList())))
			{	
				ln6 = (ArrayList<JsonNode>)jsonNode.findValues("price");
				System.out.println(ln6.size());
			}

			else
			{
				System.out.println("No price range available");
			}
			List<JsonNode> ln7 = new ArrayList<JsonNode>();
			List<JsonNode> ln8 = new ArrayList<JsonNode>();
			
			if(!(jsonNode.findValues("display_address").equals(Collections.<Object>emptyList())))
			{	
				ln7 = (ArrayList<JsonNode>)jsonNode.findValues("display_address");
			}

			else
			{
				System.out.println("No Address available");
			}
			
			if(!(jsonNode.findValues("display_phone").equals(Collections.<Object>emptyList())))
			{	
				ln8 = (ArrayList<JsonNode>)jsonNode.findValues("display_phone");
			}

			else
			{
				System.out.println("No Contact info available");
			}
			
			
			System.out.println("ID size:" + ln3.size());
			System.out.println("name size:" + ln4.size());
			System.out.println("Rating size:" + ln5.size());
			System.out.println("Price size:" + ln6.size());
			System.out.println("address size:" + ln7.size());
			System.out.println("Phone size: " + ln8.size());
			
			if (ln6.size() < ln3.size())
			{
				for (int i = ln6.size(); i < ln3.size(); i++)
				{
					JsonNode node = ln6.isEmpty() ? new TextNode("$") : ln6.get(ln6.size()-1).deepCopy();
					ln6.add(i,node );
				}
		
			}
			
			if (ln5.size() < ln3.size())
			{
				for (int i = ln5.size(); i < ln3.size(); i++)
				{
					JsonNode node = ln5.isEmpty() ? new DoubleNode(3.0) : ln5.get(ln5.size()-1).deepCopy();
					ln5.add(i,node );
				}
		
			}
			
			if (ln4.size() < ln3.size())
			{
				for (int i = ln4.size(); i < ln3.size(); i++)
				{
					JsonNode node = ln4.get(ln4.size()-1).deepCopy();
					ln4.add(i,node );
				}
		
			}
			
			if (ln7.size() < ln3.size())
			{
				for (int i = ln7.size(); i < ln3.size(); i++)
				{
					JsonNode node = ln7.get(ln7.size()-1).deepCopy();
					ln6.add(i,node );
				}
		
			}
			
			for(int i = 0 ; i < ln2.size(); i++)
			{
				System.out.println("---------------------------------------------");
				for(int j = 0; j < ln3.size(); j++)
				{
					ratingAvg = Double.parseDouble(ln5.get(j).toString().replaceAll("\"", " "));
				    
					avgRatingMap.put((ln3.isEmpty() ? "" : ln3.get(j).toString()) + " : " +  (ln4.isEmpty() ? "" : ln4.get(j).toString()) + " : " + (ln5.isEmpty() ? "" :  ln5.get(j).toString()) + " : " + (ln6.isEmpty() ? "": ln6.get(j).toString()) + " : " + (ln7.isEmpty() ? "" : ln7.get(j).toString()) + " : " + (ln8.isEmpty() ? "" : ln8.get(j).toString()), ratingAvg);
				}  
			}
			
			for(Map.Entry<String, Double> m1: avgRatingMap.entrySet())
		    {
		    		String tx = m1.getKey();
		    		double rt = m1.getValue();
		    		if(rt >= rating)
	    			{	
	    				String[] itemArray = tx.split(" : ");
	    				String ID = itemArray[0].replace("\"", "");
	    				String placeName = itemArray[1].replace("\"", "");
	    				String avgRating = itemArray[2].replace("\"", "");
	    				String priceRange = itemArray[3].replace("\"", "");
	    				String address = itemArray[4].replace("\"", "");
	    				String phone = itemArray[5].replace("\"", "");
	    				
	    				System.out.println(ID); // ID
	    				System.out.println(placeName); // Place Name
	    				System.out.println(avgRating); // Avg Rating
	    				System.out.println(priceRange); // Price Range
	    				System.out.println(address); // Address
	    				System.out.println(phone); // Phone
	    				
	    				idFromResults = yelpReview(term + "-" + location, username, placeName, ID, rating, address, phone, priceRange, rt);
	    				
	    				System.out.println();
	    			} 		
		    }	
		} 
        
        catch(Exception e)
        {
        		//e.printStackTrace();
        		System.out.println("Unexpected Error! Please Try Again");
        }
        
        return idFromResults;
	}
	
	/////////////////////////////////////////////////////////////////////////////
	public static int yelpReview(String term, String username, String placeName, String ID, int rating, String address, String phoneNumber, String priceRange, double avgRating) throws Exception
    {
        String url = "https://api.yelp.com/v3/businesses/" + ID + "/reviews"; 
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .build();

        
		int idFromSearch = 0;
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        try 
        {
        		Map<String, Double> review = new HashMap<String, Double>();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(s);
			
			String[] termLocation = term.split("-");
			
            double a = 0;
			List<JsonNode> ln2 = jsonNode.findValues("reviews");
            
			for(int i = 0; i < ln2.size(); i++)
			{
	            	List<JsonNode> ln3 = (ArrayList<JsonNode>) jsonNode.findValues("rating");
			    List<JsonNode> ln4 = (ArrayList<JsonNode>) jsonNode.findValues("text");
	    			List<JsonNode> ln5 = (ArrayList<JsonNode>) jsonNode.findValues("time_created");
	    			String hours = getYelpBusinessHours(ID).trim();
			    
	    			for(int j = 0; j < ln3.size(); j++)
			    {
			    	 	a = Double.parseDouble(ln3.get(j).toString());
			    		review.put(ln4.get(j).toString() + "  " + ln5.get(j).toString(), a);
			    }
	    		
			    for(Map.Entry<String, Double> m1: review.entrySet())
		    		{
		    			String key = m1.getKey();
		    			double value = m1.getValue();
		    			
	    				if(username == null | username.isEmpty() || username.equals("Not Logged In"))
	    				{
	    					idFromSearch = db.getID(termLocation[0], getStar(rating), termLocation[1], SearchType.general);
		    				db.storeGeneralResults(term, (getStar((int)value) + " : " + key.replace("\"", "")), placeName, Double.toString(avgRating), priceRange, phoneNumber, address.replaceAll("\\[", "").replaceAll("\\]","").replace(",",  ", ").replaceAll(",  ", ", "), "Yelp", hours, idFromSearch);
	    				}
	    				else
	    				{
	    					idFromSearch = db.getID(termLocation[0], getStar(rating), termLocation[1], SearchType.user);
    						db.storeUserResults(username, term, (getStar((int)value) + " : " + key.replace("\"", "")), placeName, Double.toString(avgRating), priceRange, phoneNumber, address.replaceAll("\\[", "").replaceAll("\\]","").replace(",",  ", ").replaceAll(",  ", ", "), "Yelp", hours, idFromSearch);
	    				}
		    		}
			}
			return idFromSearch;
			
		} 
        catch(Exception e)
        {
        		System.out.println("Unexpected Error! Please Try Again");
        }
        
        return idFromSearch;
    }
	/*----------------------------------------------------------------------------------------------------------------*/
	
    public static int googleSearch(String username, String searchTerm, String location, int rating) throws Exception
    {
        String query = searchTerm + "-" + location;
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+query+"&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw";
        Response response = null;
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        int getIDFromResults = 0;
        try
        {
        		response = client.newCall(request).execute();
        }
        catch(Exception e)
        {
        		getIDFromResults = db.getResultsOffline(query, "Google", username);
        		System.out.println("No connection");
        		return getIDFromResults;
        		
        		
        }
       
        String s = response.body().string();
        try 
        {
        		double ratingAvg = 0;
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Double> avgRatingMap = new HashMap<String, Double>();
			JsonNode jsonNode = mapper.readTree(s);
			
			List<JsonNode> ln2 = new ArrayList<JsonNode>();
			List<JsonNode> ln3 = new ArrayList<JsonNode>();
			List<JsonNode> ln4 = new ArrayList<JsonNode>();
			List<JsonNode> ln5 = new ArrayList<JsonNode>();
			
			if(!(jsonNode.findValues("formatted_address").equals(Collections.<Object>emptyList())))
			{
				ln2 = (ArrayList<JsonNode>)jsonNode.findValues("formatted_address");
			}
			else
			{
				System.out.println("No address available at this time");
			}
					
			if(!(jsonNode.findValues("place_id").equals(Collections.<Object>emptyList())))
			{
				ln3 = (ArrayList<JsonNode>)jsonNode.findValues("place_id");
			}
			else
			{
				System.out.println("No place ID available at this time");
			}
			
			if(!(jsonNode.findValues("name").equals(Collections.<Object>emptyList())))
			{
				
				ln4 = (ArrayList<JsonNode>)jsonNode.findValues("name");
			}
			else
			{
				System.out.println("No name available at this time");
			}
			
			if(!(jsonNode.findValues("rating").equals(Collections.<Object>emptyList())))
			{
				
				ln5 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
			}
			else
			{
				System.out.println("No rating available at this time");
			}
			
			for(int i = 0 ; i < ln5.size(); i++)
			{
				ratingAvg = Double.parseDouble(ln5.get(i).toString().replaceAll("\"", ""));			
				avgRatingMap.put(ln2.isEmpty() ? "": ln2.get(i).toString().replace("\"", "") + " : " + (ln3.isEmpty() ? "" : ln3.get(i).toString().replace("\"", "")) + " : " +  (ln4.isEmpty() ? "" : ln4.get(i).toString().replace("\"", "")), ratingAvg);
			    System.out.println();
			}
			for(Map.Entry<String, Double> m1: avgRatingMap.entrySet())
		    {
		    		String tx = m1.getKey();
		    		double rt = m1.getValue();
		    		if((int)rt >= rating)
	    			{	
	    				String[] itemArray = tx.split(" : ");
	    				System.out.println(itemArray[0]); // Address
	    				System.out.println(itemArray[1]); // Place ID
	    				System.out.println(itemArray[2]); // PlaceName
	    				System.out.println(rt + " : " +  getStar((int) rt));
	    				
	    				System.out.println();

	    				getIDFromResults = googleReview(query, username, itemArray[1], rating, itemArray[2], rt);
	    			}
		    }
		} 
         
        catch (Exception e)
        {
        		System.out.println("Unexpected Error! Please Try Again");
		}  
       
        return getIDFromResults;
    }
    //										
    public static int googleReview(String searchName, String username, String placeID, int rating, String placeName, double averageRating) throws Exception
    {
        String url = String.format("https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw", placeID);
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        //
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        int idFromSearch = 0;
        try 
        {
        		Map<String, Double> review = new HashMap<String, Double>();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(s);
			List<JsonNode> ln6 = new ArrayList<JsonNode>(); 
			List<JsonNode> ln1 = new ArrayList<JsonNode>(); 
			List<JsonNode> ln5 = new ArrayList<JsonNode>();
			
			if(!(jsonNode.findValues("weekday_text").equals(Collections.<Object>emptyList())))
			{
				ln6 = (ArrayList<JsonNode>)jsonNode.findValues("weekday_text");
			}
			else
			{
				System.out.println("No business hours available at this time");
			}
			
			if(!(jsonNode.findValues("formatted_phone_number").equals(Collections.<Object>emptyList())))
			{
				ln1 = (ArrayList<JsonNode>) jsonNode.findValues("formatted_phone_number");
			}
			else
			{
				System.out.println("No phone number available at this time");
			}
			
			if(!(jsonNode.findValues("formatted_address").equals(Collections.<Object>emptyList())))
			{
				ln5 = (ArrayList<JsonNode>) jsonNode.findValues("formatted_address");
			}
			else
			{
				System.out.println("No business address available at this time");
			}
			
			System.out.println(ln1.size());
			System.out.println(ln6.size());
			System.out.println(ln5.size());
			
			String[] termLocation = searchName.split("-");
			
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("reviews");
			System.out.println(ln2.size());
			double a = 0;
				
			/*------------------------------------------------------------------------------------*/
			
			for(int i = 0; i < ln2.size(); i++)
			{
				System.out.println(ln6.isEmpty()? "" : ln6.get(i).toString());
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
	    				String item = getStar((int) value) + " : " + key;	
	    				System.out.println(item);
	    				
	    				for(int j = 0; j < ln2.size(); j++)
	    				{
	    					if(username == null || username.isEmpty() || username.equals("Not Logged In"))
	    					{
	    						idFromSearch = db.getID(termLocation[0], getStar(rating), termLocation[1], SearchType.general);
		    					db.storeGeneralResults(searchName, item, placeName, Double.toString(averageRating), "", (ln1.isEmpty() ? "" : ln1.get(j).toString().replaceAll("\"", "")), (ln5.isEmpty() ? "" : ln5.get(i).toString().replace("\"" ,  ""))/*.replaceAll("^[0-9]{*}$,.*","")*/, "Google", (ln6.isEmpty()? "" : ln6.get(j).toString()), idFromSearch);			
	    					}
	    					else
	    					{
	    						idFromSearch = db.getID(termLocation[0], getStar(rating), termLocation[1], SearchType.user);
	    						db.storeUserResults(username, searchName, item, placeName, Double.toString(averageRating), "", (ln1.isEmpty() ? "" : ln1.get(j).toString().replaceAll("\"", "")), (ln5.isEmpty() ? "" : ln5.get(i).toString().replace("\"" ,  ""))/*.replaceAll("^[0-9]{*}$,.*","")*/, "Google", (ln6.isEmpty()? "" : ln6.get(j).toString()), idFromSearch);
	    					}
	    				}
		    		}   
			}
			return idFromSearch;
		} 
 
        catch (Exception e) 
        {
        		System.out.println("Unexpected Error! Please Try Again");
		} 
        
        return idFromSearch;
    }
    
    
    
 // This method will be used by all websites for the HTML
 	private static void createHTML(int id, String username, String setOfSites) throws Exception
 	{
 		String fileName = "./src/com/cogswell/seniorproject/testfile.html";
 		StringBuilder hsb = new StringBuilder();
 		hsb.append("<!DOCTYPE html>\n");
 		hsb.append("<html><head>\n");
 		hsb.append("<meta charset=\"UTF-8\">\n");
 		hsb.append("<link rel=\"stylesheet\" href=\"htmlstyle.css\">\n");
 		
 		hsb.append("<title>Search Results</title>\n");
 		hsb.append("</head>\n");
		hsb.append("<h1><span style=\"color: #346DF1\">R</span><span style = \"color: #E23E3E\">e</span><span style = \"color: #F8B823\">s</span><span style = \"color: #2D9B42\">u</span></span><span style = \"color: #F8B823\">l</span><span style = \"color: #E23E3E\">t</span><span style = \"color: #002C8E\">s</span></h1>\n");
 		
		hsb.append("<p>__________________________________________________________</p>");           
        String previousAddress = "", previousPlaceName= "", previousSiteName = "";
        hsb.append("<body style =\"background-color: #D2D2D2;\">");
         ResultSet rs = null;
        
         if(username == null || username.isEmpty() || username.equals("Not Logged In"))
         {
        	 	rs = db.getResultsFromAddress(id, SearchType.general);
         }
         
         else
         {
        	 	rs = db.getResultsFromAddress(id, SearchType.user);
         }
         
         
         System.out.println("ID is : " + id);
         double avgRatingForGoogle = 0, avgRatingForYelp = 0, avgRatingForFoursquare = 0;
         boolean first = true;
         hsb.append("<h2> <font size=\"5\" face = \"Arial\"> Place Information</font> </h2>");
         
         while(rs.next())
         {	
         		String placeName = rs.getString("placeName");	
         		String siteName = rs.getString("siteName").replaceAll("\\[", "").replaceAll("\\]","").replaceAll("\"", "");
 				double avgRating = rs.getString("avgRating").isEmpty() ? 0 : Double.parseDouble(rs.getString("avgRating"));

 				String priceRange = rs.getString("priceRange");
 				String phoneNumber = rs.getString("phoneNumber");
 				String address = rs.getString("address");
 				
 				if(!(setOfSites.contains(siteName)))
 				{
 					continue;
 				}
 				String results = rs.getString("results");
 				String ratingStar = "";
 				
 				if(results.indexOf(":") > 0)
 				{
 					ratingStar = results.substring(0, results.indexOf(":") - 1); // -1 because we don't want the string to show the :
 				}
 				String businessHours = rs.getString("businessHours").replaceAll(",", "<br>").replaceAll("\"", "").replaceAll("\\[", "").replaceAll("\\]","");
 				String text = results.substring(results.indexOf(":") + 1);
 				
 				if(first)
 				{
 					hsb.append("<h3>Place Name:"+ placeName + "</h3>");	
 				}
 				
 				if(!placeName.contains(previousPlaceName))
 				{
 					if (!first && (!(address.contains(previousAddress))))
					{
						hsb.append("</table>");
						
						if(setOfSites.contains("Google"))
						{
	    	            			hsb.append("<h3>Google's Average Rating : " + getStar((int)avgRatingForGoogle) /*+ " alt = " + avgRatingForGoogle +*/+ "</h3>");
						}
						
						if(setOfSites.contains("Yelp"))
						{
 	    						hsb.append("<h3>Yelp's Average Rating : <img src=\"" + getYelpRatingImage(avgRatingForYelp) + "\"" + "alt=\"" + avgRatingForYelp + "\"></h3>");
						}
						
						if(setOfSites.contains("Foursquare"))
						{
 	    						hsb.append("<h3>Foursquare's Average Rating : " + avgRatingForFoursquare + /*"alt=\"" + avgRatingForFoursquare +*/ "></h3>");
						}
					}
 					
 					if(!(address.contains(previousAddress)))	
 							hsb.append("<hr>");
 					
 					hsb.append("<h3>Place Name:"+ placeName + "</h3>");	
 				}
 				
 				
    	            
    				if(!(address.contains(previousAddress)) || first)
 				{
    					hsb.append("<h3>Price Range:"+ priceRange + "</h3>");	
        				hsb.append("<h3>Phone Number:"+ phoneNumber + "</h3>");	
	            		hsb.append("<h3>Address: <a href=\"https://www.google.com/maps/place/"+ address + "\" target=\"_blank\">"+ address + "</a></h3>");	
	            		
	            		
	 	    			if(!(businessHours.isEmpty()) || businessHours != null)
	 	    			{
	 	    				hsb.append("<div id = \"businessHoursHeader\">\n");
	 	    				hsb.append("<h3>Business Hours</h3>");
		 	    			hsb.append("</div>\n");
		 	    			hsb.append("<h4>" + businessHours + "</h4>");
	 	    			}
	 	    			avgRatingForGoogle = 0;
	 	    			avgRatingForYelp = 0;
	 	    			avgRatingForFoursquare = 0;
 				}
    				
    				switch(siteName)
    				{
	    				case "Google":
	    					avgRatingForGoogle = avgRating;
	    					break;
	    				case "Yelp":
	    					avgRatingForYelp = avgRating;
	    					break;
	    				case "Foursquare":
	    					avgRatingForFoursquare = avgRating;
	    					break;
    				}
    				
    	            	if(!siteName.equals(previousSiteName))
    	            	{
    	 	    			hsb.append("<div id = \"reviews\">\n");
    	            	}
    	            
    	            	if(!(address.contains(previousAddress)) || first)
 				{
    	            		hsb.append("<table>");
	 	    			hsb.append("<tr>");
	 	    			hsb.append("<th width = \"100\">Rating</th>");
	 	    			hsb.append("<th width = \"200\">Review</th>");
					hsb.append("<th width=\"100\">Site Name</th>");
					hsb.append("</tr>");
 				}
      	
 	    						
				first = false;
 	    			switch(siteName)
 	    			{
 	    				case "Google":
 	    					hsb.append("<tr>");    	            		
 	    					hsb.append("<td>" + ratingStar + "</td>");
 	    					hsb.append("<td width = \"200\" height = \"50\">" + text + "</td>");	    					
 	    					hsb.append("<td><img src=\"powered_by_google_on_white1.png\" alt=\"Google\" align=\"center\"> &nbsp; </td>");
 	    					hsb.append("</tr>");		
 		    	            break;
 	    				
 	    				case "Yelp":
 	    					hsb.append("<tr>");	
 	    					hsb.append("<td><img src=\"" + getYelpStarImage(ratingStar) + "\" alt=\"" + ratingStar + "\"></td>");
 	    					hsb.append("<td width = \"200\" height = \"50\">" + text + "</td>");
 	    					hsb.append("<td><img src=\"Untitled2.png\" alt=\"Yelp\" align=\"center\"> &nbsp;</td>");
 	    					hsb.append("</tr>");
 		    				break;
 		    	            
 	    				case "Foursquare":
 	    					//#b3b3b3
 	    					hsb.append("<tr>");
 	    					hsb.append("<td></td>");
 	    					hsb.append("<td width = \"200\" height = \"50\">" + text + "</td>");
 	    					hsb.append("<td><img src=\"foursquare.png\" alt=\"Foursquare\" align=\"center\"> &nbsp;</td>");
 	    					hsb.append("</tr>");		
 	    			}
 	    			hsb.append("</div>");
 	    			
 	    			previousAddress = address;
 	    			previousPlaceName = placeName;
 	    			previousSiteName = siteName;
         }
        
         hsb.append("<hr>");

         hsb.append("</body>");
 		 hsb.append("</html>");
         File f = new File(fileName);
         Desktop.getDesktop().browse(f.toURI());
 	}
    
 	private static String getWeekday(int day)
	{
		switch(day)
		{
			case 0:
				return "Sunday";
				
			case 1:
				return "Monday";
				
			case 2:
				return "Tuesday";
		
			case 3:
				return "Wednesday";
			
			case 4:
				return "Thursday";
			
			case 5:
				return "Friday";
			
			case 6:
				return "Saturday";
			
			default:
				return "";
		}
	}
	 
 	public static String getYelpRatingImage(double rating) 
    {
    		if(rating >= 0.0 && rating < 1.0)
    		{
    			return "small_0.png";
    		}
    		else if(rating >= 1.0 && rating <= 1.4)
    		{
    			return "small_1.png";
    		}
    		else if(rating >= 1.5 && rating < 2.0)
    		{
    			return "small_1_half.png";
    		}
    		else if(rating >= 2.0 && rating < 2.5)
    		{
    			return "small_2.png";
    		}
    		else if(rating >= 2.5 && rating < 3.0)
    		{
    			return "small_2_half.png";
    		}
    		else if(rating >= 3.0 && rating < 3.5)
    		{
    			return "small_3.png";
    		}
    		else if(rating >= 3.5 && rating < 4.0)
    		{
    			return "small_3_half.png";
    		}
    		else if(rating >= 4.0 && rating < 4.5)
    		{
    			return "small_4.png";
    		}
    		else if(rating >= 4.5 && rating < 5.0)
    		{
    			return "small_4_half.png";
    		}
    		else if(rating >= 5.0)
    		{
    			return "small_5.png";
    		}
    		else
    		{
    			return "small_3.png";
    		}
    }
 	
	private static String getYelpBusinessHours(String ID) throws Exception
	{
        String url = "https://api.yelp.com/v3/businesses/" + ID; 
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .build();

        //
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        try
        {
        		ArrayList<String> hours = new ArrayList<String>();
        		String resp;
	        	
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(s);
			
			List<JsonNode> ln5 = (ArrayList<JsonNode>)jsonNode.findValues("day");
			List<JsonNode> ln6 = (ArrayList<JsonNode>)jsonNode.findValues("start");
			List<JsonNode> ln7 = (ArrayList<JsonNode>)jsonNode.findValues("end");
			
			for (int j = 0; j < ln5.size(); j++)
			{
				String open  = ln6.get(j).toString().replaceAll("\"", "").replaceAll("..(?!$)", "$0:").trim();
				String close = ln7.get(j).toString().replaceAll("\"", "").replaceAll("..(?!$)", "$0:").trim();
				
				resp = getWeekday(Integer.parseInt(ln5.get(j).toString().trim())) +  " " + open + " -- " + close;
				hours.add(resp);	
			}
			return hours.toString().replaceAll("\\[", "").replaceAll("\\]","");		
        }
        catch(Exception e)
        {
        		return "";
        }
	}
 	
 	public static String getYelpStarImage(String rating)
    {
        switch(rating)
        {
            case "★":
            		return "small_1.png";
            case "★★":
            		return "small_2.png";
            case "★★★":
            		return "small_3.png";
            case "★★★★":
            		return "small_4.png";
            case "★★★★★":
            		return "small_5.png";
            default:
            	return "small_3.png";   
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
            case "$$$$":
            		return 4;

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
        
        int id = 0;
        for(String searchFilterName: listOfFilters)
        {
            switch(searchFilterName)
            {
                case "Yelp":
                    id = yelpSearch(username, searchTerm, cityOrZip, r, getPriceSymbol(price));                   
                    break;
                    
                case "Foursquare":                   
                		id = foursquareSearch(username, searchTerm, cityOrZip, rating);
                    break;
                    
                case "Google":
                    id = googleSearch(username, searchTerm, cityOrZip, r);
                    break;
                    
                default:
                    id = googleSearch(username, searchTerm, cityOrZip, r);
                		//System.out.println("Sorry. Your search criteria is not valid");
                    break;
            } 
        }
        createHTML(id, username, listOfFilters.toString());
    }
}

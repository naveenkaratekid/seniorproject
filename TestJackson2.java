package com.cogswell.seniorproject;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestJackson2 
{	
	public static SearchDAO db = new SearchDAO();
	static OkHttpClient client = new OkHttpClient();
	
	
	public static StringBuilder testFoursquare(String username, String search, String location) throws Exception
    {
		
        // Foursquare OAuth2 access token: P14YL1YOGUDWTKBWER14IDBWV4BYBW4ASKCC2GRGDY3F5BZQ
        
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
        String date = sdf.format(new Date());
        String url = "https://api.foursquare.com/v2/venues/search?near=" + location + "&query=" + search + "&v=" + date + "&client_secret=SNZBRYNWZ0YPETJKXGW4LGGEX0KHM2Q2JOOJJH1UBAJ2CQD0&client_id=RZRA3MOZSBX1R3Y1ZUFXUO3YT2BSKBWSSRKXX3G3LKUQDBEU";
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        
        StringBuilder HTMLBodyContent = new StringBuilder();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        JSONObject jo = new JSONObject(s);
        JSONObject jo1 = jo.getJSONObject("response");
       
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
                	 	a = jo2.getString("id");
                	 	System.out.println("Id is: " + a);
                     
                     
                     b = jo2.getString("name");
                     System.out.println("Name is: " + b);
                     
                     
                     c = jo5.getJSONArray("formattedAddress").toString().replaceAll("\\[", "").replaceAll("\\]","");
                     System.out.println("Address is: " + c);
                     
                     
                     d = jo4.getString("formattedPhone");
                     System.out.println("Phone number is: " + d);
                     
                     
                     StringBuilder hsb = foursquareReview(username, a, b, c, d); 
                     HTMLBodyContent.append(hsb);
                 }
                 catch(Exception e)
                 {
                     System.out.println("No info");
                 }
                 System.out.println();
             }
             
             System.out.println();
             
             System.out.println();
             
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return HTMLBodyContent;
    }

	public static StringBuilder foursquareReview(String username, String id, String placeName, String address, String phone) throws Exception
    {
        
        System.out.println("-------------Foursquare Reviews-------------");
        String url = "";
        
        StringBuilder hsb = new StringBuilder();
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
        hsb.append("<h4>" + placeName + "</h4>");
        hsb.append("<h4>" + address + "</h4>");
        hsb.append("<h4>" + phone + "</h4>");
        
        JSONObject jo = new JSONObject(s);
        String b; 
        
    		hsb.append("<img src=\"Powered-by-Foursquare-full-color-300_preview.png\" alt=\"Powered by Foursquare\" align=\"left\">\n");
		hsb.append("<div id = \"reviewsTitle\">\n");
        
        hsb.append("<h1><span style=\"color: #060606\">R</span><span style = \"color: #C60C1B\">e</span><span style = \"color: #060606\">v</span><span style = \"color: #C60C1B\">i</span></span><span style = \"color: #060606\">e</span><span style = \"color: #C60C1B\">w</span><span style = \"color: #060606\">s</span></h1>\n");
        hsb.append("</div>\n");
        JSONObject jo1 = jo.getJSONObject("response").getJSONObject("tips");
        JSONArray ja = jo1.getJSONArray("items");
        hsb.append("<br>");
	    hsb.append("<h3>Reviews</h3>");
        hsb.append("<div id = \"reviews\">\n");
        for(int i = 0; i < ja.length(); i++)
        {
            JSONObject jo2 = ja.getJSONObject(i);
            b = jo2.getString("text"); 
            hsb.append("<p>" + b + "</p>");
            //hsb.append("<p><img id =\"yLogo\" src=\"foursquare.png\" alt=\"Foursquare\"+  "\"</p>");
            hsb.append("<p>----------------------------------</p>");
            
            System.out.println(b + "\n"); 
        }  
       
        hsb.append("</div>");
        System.out.println("-------------------------------");
        System.out.println();
        return hsb;
    }

	// This method will be used by all websites for the HTML
	private static void createHTML(StringBuilder content) throws Exception
	{
		String fileName = "./src/com/cogswell/seniorproject/testfile.html";
		StringBuilder hsb = new StringBuilder();
		hsb.append("<!DOCTYPE html>\n");
		hsb.append("<html><head>\n");
		hsb.append("<meta charset=\"UTF-8\">\n");
		hsb.append("<link rel=\"stylesheet\" href=\"htmlstyle.css\">\n");
		hsb.append("<title>Search Results</title>\n");
		hsb.append("</head>\n");
        	hsb.append("<h1 align=\"center\">Results</h1>");
        hsb.append("<p>__________________________________________________________</p>");           
        hsb.append("<h2>Place information</h2>");
        
        hsb.append("<body>");
        
        hsb.append(content);
        
        hsb.append("</body>");
		hsb.append("</html>");
        WriteToHTML wth = new WriteToHTML(); 
        wth.WriteToFile(hsb.toString(),fileName);
        File f = new File(fileName);
        Desktop.getDesktop().browse(f.toURI());
	}
	
	///////////////////////////////////////////////////////////////////////////////
	public static StringBuilder testYelp(String username, String term, String location, int rating) throws Exception
	{
        System.out.println("-------------Yelp Test-------------");
        String url = "https://api.yelp.com/v3/businesses/search?term="+term+"&location="+location;
        
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .build();
        
        StringBuilder HTMLBodyContent = new StringBuilder();
        HTMLBodyContent.append("<img src=\"yelp1.png\" alt=\"Yelp\" align=\"left\">\n");
        HTMLBodyContent.append("<br><br>");
        
        Response response = client.newCall(request).execute();
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
			
			if(jsonNode.findValues("rating") != null)
			{
				ln5 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
				
			}
			if(jsonNode.findValues("price") != null || !(jsonNode.findValues("price").isEmpty()) || jsonNode.findValues("price").getClass().isInstance(Collections.EMPTY_LIST))
			{
				System.out.println(jsonNode.findValues("price"));
				List<JsonNode> l = jsonNode.findValues("price");
				ln6 = (ArrayList<JsonNode>)jsonNode.findValues("price");
				
			}
			else
			{
				System.out.println("No price range available");
			}
			List<JsonNode> ln7 = (ArrayList<JsonNode>)jsonNode.findValues("display_address");
			List<JsonNode> ln8 = (ArrayList<JsonNode>)jsonNode.findValues("display_phone");
			for(int i = 0 ; i < ln2.size(); i++)
			{
				System.out.println("---------------------------------------------");
				for(int j = 0; j < ln5.size(); j++)
				{
					ratingAvg = Double.parseDouble(ln5.get(j).toString().replaceAll("\"", " "));
				    avgRatingMap.put(ln3.get(j).toString() + " : " +  ln4.get(j).toString() + " : " + ln5.get(j).toString() + " : " + ln6.get(j).toString() + " : " + ln7.get(j).toString() + " : " + ln8.get(j).toString(), ratingAvg);
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
	    				/*
	    				System.out.println(itemArray[0]); // ID
	    				System.out.println(itemArray[1]); // Place Name
	    				System.out.println(itemArray[2]); // Avg Rating
	    				System.out.println(itemArray[3]); // Price Range
	    				System.out.println(itemArray[4]); // Address
	    				System.out.println(itemArray[5]); // Phone
	    				*/
	    				StringBuilder hsb = yelpReview(term + "-" + location, username, placeName, ID, rating, address, phone, priceRange, rt);

	    				//StringBuilder hsb = yelpReview(term + " " + location, username, itemArray[1], itemArray[0], rating, itemArray[4], itemArray[5], itemArray[3], rt);
	    				HTMLBodyContent.append(hsb);
	    				System.out.println();
	    			} 		
		    }	
		} 
        catch(UnknownHostException uhe)
        {
        		HTMLBodyContent = db.getResultsOffline(term);
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
        return HTMLBodyContent;
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
	
	/////////////////////////////////////////////////////////////////////////////
	public static StringBuilder yelpReview(String term, String username, String placeName, String ID, int rating, String address, String phoneNumber, String priceRange, double avgRating) throws Exception
    {
        String url = "https://api.yelp.com/v3/businesses/" + ID + "/reviews"; 
        StringBuilder hsb = new StringBuilder();
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .build();

        
		
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        try 
        {
        		Map<String, Double> review = new HashMap<String, Double>();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(s);
			
            hsb.append("<h3>Place Name: <a href=\"https://www.yelp.com/biz/"+ ID + "\" target=\"_blank\">"+ placeName + "</a></h3>");	
            hsb.append("<h3>Price Range: " + priceRange + "</h3>");			
            hsb.append("<h3> Phone Number: " + phoneNumber + " </h3>");
            hsb.append("<h3>Address: <a href=\"https://www.google.com/maps/place/"+ address.replaceAll("\\[", "").replaceAll("\\]","") + "\" target=\"_blank\">"+ address.replaceAll("\\[", "").replaceAll("\\]","") + "</a></h3>");
            hsb.append("<h3>Average Rating : <img src=\"" + getYelpRatingImage(avgRating) + "\" alt=\"" + avgRating + "\"></h3>");
            double a = 0;
			List<JsonNode> ln2 = jsonNode.findValues("reviews");
            
			for(int i = 0; i < ln2.size(); i++)
			{
	            	List<JsonNode> ln3 = (ArrayList<JsonNode>) jsonNode.findValues("rating");
			    List<JsonNode> ln4 = (ArrayList<JsonNode>) jsonNode.findValues("text");
	    			List<JsonNode> ln5 = (ArrayList<JsonNode>) jsonNode.findValues("time_created");
	    			String hours = getYelpBusinessHours(ID).trim();
	    			String strArray[] = hours.split(",");	
	    			
	    			hsb.append("<div id = \"businessHoursHeader\">\n");
	    			hsb.append("<h3>Business Hours</h3>");
	    			hsb.append("</div>\n");
	    			
	    			hsb.append("<div class = \"businessHours\">\n");
	    			for(int j = 0; j < strArray.length; j++)
	    			{
	    				hsb.append("<h4>" + strArray[j].trim() + "</h4>");
	    				
	    			}
	    			hsb.append("</div>\n");
			    
	    			for(int j = 0; j < ln3.size(); j++)
			    {
			    	 	a = Double.parseDouble(ln3.get(j).toString());
			    		review.put(ln4.get(j).toString() + "  " + ln5.get(j).toString(), a);
			    }
	    			hsb.append("<div id = \"reviewsTitle\">\n");
	    			hsb.append("<h1><span style=\"color: #060606\">R</span><span style = \"color: #C60C1B\">e</span><span style = \"color: #060606\">v</span><span style = \"color: #C60C1B\">i</span></span><span style = \"color: #060606\">e</span><span style = \"color: #C60C1B\">w</span><span style = \"color: #060606\">s</span></h1>\n");
	    	        hsb.append("<br>");
	    			hsb.append("</div>\n");
	    			hsb.append("<div id = \"reviews\">\n");
			    for(Map.Entry<String, Double> m1: review.entrySet())
		    		{
		    			String key = m1.getKey();
		    			double value = m1.getValue();
		    			
	    				hsb.append("<p><img id =\"yLogo\" src=\"Untitled2.png\" alt=\"Yelp\" align=\"left\"> &nbsp; <img id = \"yelpStars\"src=\"" + getYelpRatingImage(value) + "\" alt=\"" + value + "\" align=\"center\"> &nbsp; : &nbsp; " +  key.replace("\"", "") +  "\"</p>");
	    				hsb.append("<br>");
	    				hsb.append("<style>#yLogo{	transform: translateY(-13px);	}</style>");
	    				hsb.append("<style>#yelpStars{	transform: translateX(10px);	}</style>");
	    				String[] termLocation = term.split("-");
	    				
	    				int idFromGeneralSearch = db.getID(termLocation[0], getStar(rating), termLocation[1]);
	    				db.storeGeneralResults(term, (getStar((int)value) + " : " + key.replace("\"", "")), placeName, Double.toString(avgRating), priceRange, phoneNumber, address.replaceAll("\\[", "").replaceAll("\\]","").replace(",",  ", ").replaceAll(",  ", ", "), "Yelp", hours, idFromGeneralSearch);
		    		}
			    hsb.append("</div>");
			    hsb.append("<hr>");
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
        return hsb;
    }
	/*----------------------------------------------------------------------------------------------------------------*/
	
    public static StringBuilder testGoogle(String username, String searchTerm, String location, int rating) throws Exception
    {
        String query = searchTerm + "-" + location;
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+query+"&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw";
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        StringBuilder HTMLBodyContent = new StringBuilder();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        try 
        {
        		double ratingAvg = 0;
			ObjectMapper mapper = new ObjectMapper();
			Map<String, Double> avgRatingMap = new HashMap<String, Double>();
			JsonNode jsonNode = mapper.readTree(s);
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("formatted_address");
			List<JsonNode> ln3 = (ArrayList<JsonNode>)jsonNode.findValues("place_id");
			List<JsonNode> ln4 = (ArrayList<JsonNode>)jsonNode.findValues("name");
			List<JsonNode> ln5 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
			
			for(int i = 0 ; i < ln5.size(); i++)
			{
				ratingAvg = Double.parseDouble(ln5.get(i).toString().replaceAll("\"", ""));
			    avgRatingMap.put(ln2.get(i).toString().replace("\"", "") + " : " + ln3.get(i).toString().replace("\"", "") + " : " +  ln4.get(i).toString().replace("\"", ""), ratingAvg);
			    System.out.println();

			}
			for(Map.Entry<String, Double> m1: avgRatingMap.entrySet())
		    {
		    		String tx = m1.getKey();
		    		double rt = m1.getValue();
		    		if((int)rt >= rating)
	    			{
	    				//String item = tx + " : " + getStar((int) rt);
	    				String[] itemArray = tx.split(" : ");
	    				System.out.println(itemArray[0]); // Address
	    				System.out.println(itemArray[1]); // Place ID
	    				System.out.println(itemArray[2]); // PlaceName
	    				System.out.println(rt + " : " +  getStar((int) rt));
	    				
	    				System.out.println();
	    				StringBuilder hsb = googleReview(query, username, itemArray[1], rating, itemArray[2], rt);

	    				HTMLBodyContent.append(hsb);

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
        return HTMLBodyContent;
    }
    //										
    public static StringBuilder googleReview(String searchName, String username, String placeID, int rating, String placeName, double averageRating) throws Exception
    {
        String url = String.format("https://maps.googleapis.com/maps/api/place/details/json?placeid=%s&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw", placeID);
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        //
        StringBuilder hsb = new StringBuilder();
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        try 
        {
        		//WriteToHTML wth = new WriteToHTML();
        		
        		Map<String, Double> review = new HashMap<String, Double>();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(s);
			List<JsonNode> ln6 = (ArrayList<JsonNode>)jsonNode.findValues("weekday_text");
			List<JsonNode> ln1 = (ArrayList<JsonNode>)jsonNode.findValues("formatted_phone_number");
			List<JsonNode> ln5 = (ArrayList<JsonNode>)jsonNode.findValues("formatted_address");
			
			
			// Here write header of HTML file
			
			for(int i = 0; i < ln1.size(); i++)
			{
				hsb.append("<h3>Place Name: " + placeName + "</h3>");
				
				hsb.append("<h3> Phone Number: " + ln1.get(i).toString().replace("\"", "") + " </h3>\n");
				hsb.append("<h3> Address: \n<a href=\"https://www.google.com/maps/place/"+ ln5.get(i).toString().replace("\"", "") + "\" target=\"_blank\">"+ ln5.get(i).toString().replace("\"", "") + "</a></h3>\n");
				hsb.append("<h3>Average Rating: &nbsp " + getStar((int) averageRating) + "</h3>");			
			}
			
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("reviews");
			double a = 0;
				
			/*------------------------------------------------------------------------------------*/
			hsb.append("<p>-------------------------------------------------------------</p>\n");
			hsb.append("<img src=\"powered_by_google_on_white.png\" alt=\"Powered by Google\" align=\"left\">\n");
			hsb.append("<br>");
            	hsb.append("</br>");
            	hsb.append("<div id = \"reviews\">\n");
            
			for(int i = 0; i < ln2.size(); i++)
			{
				System.out.println(ln6.get(i).toString());
			    List<JsonNode> ln3 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
	    			List<JsonNode> ln4 = (ArrayList<JsonNode>)jsonNode.findValues("text");
	    			hsb.append("<div id = \"googleHours\">");
	    			hsb.append("<h3>Business Hours</h3>");	 
	    			hsb.append("<h4>" + ln6.get(i).toString().replaceAll("\\[", "").replaceAll("\\]","").replace("\"", "\n").replaceAll(",", "<br>") + "</h4>");
	    			hsb.append("</div>");
	    			for(int j = 0; j < ln4.size(); j++)
			    {
			    	 	a = Double.parseDouble(ln3.get(j+1).toString());
			    		review.put(ln4.get(j).toString(), a);
			    }
			    hsb.append("<br>");
			    hsb.append("<div id = \"reviewsTitle\">\n");
	            hsb.append("<h1><span style=\"color: #346DF1\">R</span><span style = \"color: #E23E3E\">e</span><span style = \"color: #F8B823\">v</span><span style = \"color: #2D9B42\">i</span></span><span style = \"color: #F8B823\">e</span><span style = \"color: #E23E3E\">w</span><span style = \"color: #346DF1\">s</span></h1>\n");
	            hsb.append("</div>\n");
			    hsb.append("<h3>Reviews</h3>");
			    for(Map.Entry<String, Double> m1: review.entrySet())
		    		{
		    			String key = m1.getKey();
		    			double value = m1.getValue();
	    				String item = getStar((int) value) + " : " + key;	
	    				System.out.println(item);
	    				hsb.append("<p>" + "<img src=\"powered_by_google_on_white_mini.png\" alt=\"Powered by Google\" align=\"left\"> &nbsp; " + item + "</p>\n");
	    				for(int j = 0; j < ln1.size(); j++)
	    				{
	    					String[] termLocation = searchName.split("-");
		    				int idFromGeneralSearch = db.getID(termLocation[0], getStar(rating), termLocation[1]);
	    					
	    					db.storeGeneralResults(searchName, item, placeName, Double.toString(averageRating), "", ln1.get(j).toString(), ln5.get(i).toString().replace("\"" ,  "")/*.replaceAll("^[0-9]{*}$,.*","")*/, "Google", ln6.get(j).toString(), idFromGeneralSearch);
	    				}
		    		} 
			   hsb.append("<hr>");
			   //hsb.append("<p>------------------------------------------------------------------------</p>\n");
			}
			hsb.append("</div>\n");

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
        return hsb;
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
    
    // TRY TO ADD ICON OF SITE NEXT TO EACH REVIEW
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
        StringBuilder search;
        for(String searchFilterName: listOfFilters)
        {
            switch(searchFilterName.toLowerCase())
            {
                case "yelp":
                    //testYelp(searchTerm, cityOrZip, r, price, distance);
                    search = testYelp(username, searchTerm, cityOrZip, r);
                    createHTML(search);
                    break;
                    
                case "foursquare":
                    search = testFoursquare(username, searchTerm, cityOrZip);
                    createHTML(search);
                    break;
                    
                case "google": case "google places":
                    search = testGoogle(username, searchTerm, cityOrZip, r);
                    createHTML(search);
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
        if(rating >= 0 && rating <= 5)
        {
        		//String x = getYelpBusinessHours("ldPsoxeD3bPgwXWwF2_QJA");
        		
        		 //StringBuilder search = testYelp("", term, location, rating);
        		 //search = testGoogle("", term, location, rating);
        		 StringBuilder search =  testGoogle("", term, location, rating);
        		 //createHTML(search);
        		 //StringBuilder 
        		 //search = testFoursquare("", term, location);
        		 //createHTML(search);
        		 
        }
        else
        {
        		System.out.println("Invalid rating");
        }
        scanner.close();
    }
}

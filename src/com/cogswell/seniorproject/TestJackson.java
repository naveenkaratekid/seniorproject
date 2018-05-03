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
			//ArrayList<String> placeInfo = new ArrayList<String>();
			
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
					System.out.println("ID: " + ln3.get(j) + "\n\nName:  " + ln4.get(j) + "\n\nAverage Rating: "+ ln5.get(j) + "\n\nPrice Range: " + ln6.get(j) + "\n\nAddress: " + ln7.get(j) + "\n\nPhone Number: " + ln8.get(j) );
					System.out.println();
					
					//placeInfo.add("Address: " + ln7.get(j) + "\n\nPhone Number: " + ln8.get(j));
				
					//yelpReview(username, ln3.get(j).toString().replace("\"", ""), rating);
					//yelpReview(username, ln3.get(j).toString().replace("\"", ""), rating, ln4.get(j).toString().replace("\"", ""), ln7.get(j).toString().replace("\"", ""), ln8.get(j).toString().replace("\"", ""));
					yelpReview(username, ln4.get(j).toString().replace("\"", ""), ln3.get(j).toString().replace("\"", ""), rating, ln7.get(j).toString().replace("\"", ""), ln8.get(j).toString().replace("\"", ""), ln6.get(j).toString().replace("\"", ""), Double.parseDouble(ln5.get(j).toString().replace("\"", "")));
					//(username, ln3.get(j).toString().replace("\"", ""), rating, ln4.get(j).toString().replace("\"", ""), ln7.get(j).toString().replace("\"", ""), String phoneNumber)
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
	
	
	/*
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
	 */
	
	 
	/////////////////////////////////////////////////////////////////////////////
	public static void yelpReview(String username, String placeName, String ID, int rating, String address, String phoneNumber, String priceRange, double avgRating) throws Exception
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
        		WriteToHTML wth = new WriteToHTML();
        		Map<String, Double> review = new HashMap<String, Double>();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(s);
			System.out.println("Address of place: " + address.replaceAll("\\[", "").replaceAll("\\]",""));
			System.out.println("Contact info: " + phoneNumber);
			System.out.println("Place name: " + placeName);
			System.out.println("ID is: " + ID);

			// Here write header of HTML file
			StringBuilder hsb = new StringBuilder();
			String fileName = "./src/com/cogswell/seniorproject/testfile.html";
			hsb.append("<!DOCTYPE html>\n");
			hsb.append("<html><head>\n");
			hsb.append("<meta charset=\"UTF-8\">\n");
			hsb.append("<link rel=\"stylesheet\" href=\"htmlstyle.css\">\n");
			hsb.append("<title>Search Results</title>\n");
			hsb.append("</head>\n");
            hsb.append("<h1 align=\"center\">Results</h1>");
            hsb.append("<p>__________________________________________________________</p>");           
            hsb.append("<h2>Place information</h2>");
            //hsb.append("<h3>" + placeName + "</h3>");
            hsb.append("<h3>Place Name: <a href=\"https://www.yelp.com/biz/"+ ID + "\" target=\"_blank\">"+ placeName + "</a></h3>");
			//hsb.append("<body>");
            hsb.append("<h3>Price Range: " + priceRange + "</h3>");
			//System.out.println("Phone Number: " + ln1.get(i).toString().replace("\"", ""));
			//System.out.println("Address: " + ln5.get(i).toString().replace("\"", ""));
            hsb.append("<h3> Phone Number: " + phoneNumber + " </h3>");
            hsb.append("<h3>Address: <a href=\"https://www.google.com/maps/place/"+ address.replaceAll("\\[", "").replaceAll("\\]","") + "\" target=\"_blank\">"+ address.replaceAll("\\[", "").replaceAll("\\]","") + "</a></h3>");
			hsb.append("<h3>Average Rating : <img src=\"" + getYelpRatingImage(avgRating) + "\" alt=\"" + avgRating + "\"></h3>");
            hsb.append("<p>-------------------------------------------------------------</p>");
            
            
            
            double a = 0;
			
			List<JsonNode> ln2 = jsonNode.findValues("reviews");
			
			hsb.append("<img src=\"yelp1.png\" alt=\"Yelp\" align=\"left\">\n");
			hsb.append("<div id = \"reviewsTitle\">\n");
            //hsb.append("<h1>Reviews</h1>\n");
            hsb.append("<h1><span style=\"color: #060606\">R</span><span style = \"color: #C60C1B\">e</span><span style = \"color: #060606\">v</span><span style = \"color: #C60C1B\">i</span></span><span style = \"color: #060606\">e</span><span style = \"color: #C60C1B\">w</span><span style = \"color: #060606\">s</span></h1>\n");
            hsb.append("</div>\n");
			
            hsb.append("<br>");
            hsb.append("</br>");
			for(int i = 0; i < ln2.size(); i++)
			{
				
	            List<JsonNode> ln3 = (ArrayList<JsonNode>) jsonNode.findValues("rating");
			    List<JsonNode> ln4 = (ArrayList<JsonNode>) jsonNode.findValues("text");
	    			List<JsonNode> ln5 = (ArrayList<JsonNode>) jsonNode.findValues("time_created");
	    			
			    for(int j = 0; j < ln4.size(); j++)
			    {
			    	 	a = Double.parseDouble(ln3.get(j).toString());
			    		review.put(ln4.get(j).toString() + "  " + ln5.get(j).toString(), a);
			    }

	            hsb.append("<body>\n");
	            hsb.append("<div id = \"reviews\">\n");
			    for(Map.Entry<String, Double> m1: review.entrySet())
		    		{
		    			String key = m1.getKey();
		    			double value = m1.getValue();
		    			if(value >= rating)
		    			{
		    				System.out.println(value + " : " + key);
		    				//String item = getStar((int) value) + " : " + key;
		    				String item1 = getYelpRatingImage(value) + " : " + key + "\n";
		    				
		    				System.out.println(item1);
		    				//System.out.println(item);
		    				//hsb.append("<p>" + item + "</p>");
		    				//System.out.println("<img src=\"" + getYelpRatingImage(value) + "\" alt=\"" + value + "\" align=\"left\"><p>\"" + key.replace("\"", "") +  "\"</p>");
		    				hsb.append("<img src=\"" + getYelpRatingImage(value) + "\" alt=\"" + value + "\" align=\"left\"> <p> &nbsp : &nbsp " +  key.replace("\"", "") +  "\"</p>");
		    				//System.out.println(hsb.append("<img src=\"" + getYelpRatingImage(value) + "\" alt=\"" + value + "\" align=\"left\"> <p>  :  " +  key.replace("\"", "") +  "\"</p>"));
		    			}
		    		}
			    hsb.append("<p>------------------------------------------------------------------------</p>");
			}
			
			
			System.out.println();
			hsb.append("</div>");
			hsb.append("</body>");
			hsb.append("</html>");
			wth.WriteToFile(hsb.toString(),fileName);
            File f = new File(fileName);
            Desktop.getDesktop().browse(f.toURI());
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
	/*----------------------------------------------------------------------------------------------------------------*/
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
			    googleReview(username, ln3.get(i).toString().replace("\"", ""), rating, ln4.get(i).toString().replace("\"", ""), Double.parseDouble(ln5.get(i).toString().replace("\"", "")));
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
    
    public static void googleReview(String username, String placeID, int rating, String placeName, double averageRating) throws Exception
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
			List<JsonNode> ln6 = (ArrayList<JsonNode>)jsonNode.findValues("weekday_text");
			List<JsonNode> ln1 = (ArrayList<JsonNode>)jsonNode.findValues("formatted_phone_number");
			List<JsonNode> ln5 = (ArrayList<JsonNode>)jsonNode.findValues("formatted_address");
			
			
			// Here write header of HTML file
			StringBuilder hsb = new StringBuilder();
			String fileName = "./src/com/cogswell/seniorproject/testfile.html";
			hsb.append("<!DOCTYPE html>\n");		
			hsb.append("<html><head>\n");
			hsb.append("<meta charset=\"UTF-8\">\n");
			hsb.append("<link rel=\"stylesheet\" href=\"htmlstyle.css\">\n");
			hsb.append("<title>Search Results</title>\n");
			hsb.append("</head>\n");
			hsb.append("<div id = \"header\">\n");
            hsb.append("<h1>Results</h1>\n");
            hsb.append("</div>\n");
            
            
            hsb.append("<p>---------------------------------------------------------</p>\n");           
            hsb.append("<h2>Place information</h2>\n");
            
			for(int i = 0; i < ln1.size(); i++)
			{
				hsb.append("<h3>Place Name: " + placeName + "</h3>");
				//System.out.println("Phone Number: " + ln1.get(i).toString().replace("\"", ""));
				//System.out.println("Address: " + ln5.get(i).toString().replace("\"", ""));
				
				hsb.append("<h3> Phone Number: " + ln1.get(i).toString().replace("\"", "") + " </h3>\n");
				hsb.append("<h3> Address: \n<a href=\"https://www.google.com/maps/place/"+ ln5.get(i).toString().replace("\"", "") + "\" target=\"_blank\">"+ ln5.get(i).toString().replace("\"", "") + "</a></h3>\n");
				hsb.append("<h3>Average Rating: &nbsp " + getStar((int) averageRating) + "</h3>");
						//+ "<img src=\"" + getYelpRatingImage((int)averageRating) + "\" alt=\"" + averageRating + "\"></h3>");
			}
			
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("reviews");
			double a = 0;
				
			/*------------------------------------------------------------------------------------*/
			hsb.append("<p>-------------------------------------------------------------</p>\n");
			hsb.append("<img src=\"powered_by_google_on_white.png\" alt=\"Powered by Google\" align=\"left\">\n");
			hsb.append("<div id = \"reviewsTitle\">\n");
            //hsb.append("<h1>Reviews</h1>\n");
            hsb.append("<h1><span style=\"color: #346DF1\">R</span><span style = \"color: #E23E3E\">e</span><span style = \"color: #F8B823\">v</span><span style = \"color: #2D9B42\">i</span></span><span style = \"color: #F8B823\">e</span><span style = \"color: #E23E3E\">w</span><span style = \"color: #346DF1\">s</span></h1>\n");
            hsb.append("</div>\n");
            
            //hsb.append("<img src=\"powered_by_google_on_white.png\" alt=\"Powered by Google\" align=\"left\">\n");
            hsb.append("<br>");
            hsb.append("</br>");
            hsb.append("<body>\n");
            hsb.append("<div id = \"reviews\">\n");
            
			for(int i = 0; i < ln2.size(); i++)
			{
				System.out.println(ln6.get(i).toString());
			    List<JsonNode> ln3 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
	    			List<JsonNode> ln4 = (ArrayList<JsonNode>)jsonNode.findValues("text");
	    			hsb.append("<h3>Business Hours</h3>");
	    			hsb.append("<h4>" + ln6.get(i).toString().replaceAll("\\[", "").replaceAll("\\]","").replace("\"", "\n").replaceAll(",", "<br>") + "</h4>");
			    for(int j = 0; j < ln4.size(); j++)
			    {
			    	 	a = Double.parseDouble(ln3.get(j+1).toString());
			    		review.put(ln4.get(j).toString(), a);
			    }
			    hsb.append("<br>");
			    hsb.append("<h3>Reviews</h3>");
			    for(Map.Entry<String, Double> m1: review.entrySet())
		    		{
		    			String key = m1.getKey();
		    			double value = m1.getValue();
		    			if(value >= rating)
		    			{
		    				String item = getStar((int) value) + " : " + key;
		    				//System.out.println(getStar((int) value) + " : " + key);
		    				System.out.println(item);
		    				
		    				//wth.testHTML(username, item, placeInfo);
		    				// Here write body of HTML into same file
		    				//append body
		    				hsb.append("<p>" + item + "</p>\n");
		    			}
		    		} 
			   hsb.append("<p>------------------------------------------------------------------------</p>\n");
			}
			hsb.append("</div>\n");

			// Here write footer of HTML			
			hsb.append("</body>\n");
			hsb.append("</html>\n");
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
        if(rating >= 0 && rating <= 5)
        {
        		testYelp("", term, location, rating);
        		//testGoogle("", term, location, rating);
        		 //testFoursquare(term, location);
        }
        else
        {
        		System.out.println("Invalid rating");
        }
        //testGoogle("", term, location, rating);
        //testFoursquare(term, location);
        //testYelp("", term, location, rating);
        scanner.close();
        //googleReview();
    }
    

}

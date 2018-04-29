package com.cogswell.seniorproject;
import java.awt.Desktop;
import java.io.*;
import java.text.SimpleDateFormat;
import okhttp3.*;
import java.util.*;

import javax.swing.text.html.HTML;

import org.json.*;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJackson1 
{
	/*public static void testFoursquare(String username, String search, String location) throws Exception
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
        try 
        {
        		double ratingAvg = 0;
			ObjectMapper mapper = new ObjectMapper();
			Map<String, String> idName = new HashMap<String, String>();
			Map<String, String> addressPhone = new HashMap<String, String>();
			JsonNode jsonNode = mapper.readTree(s);
			//List<JsonNode> ln1 = (ArrayList<JsonNode>)jsonNode.findValues("venues");
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("id"); // 15
			System.out.println(ln2.size());
			
			List<JsonNode> ln4 = (ArrayList<JsonNode>)jsonNode.findValues("name"); // 15
			System.out.println(ln4.size());
			
			List<JsonNode> ln3 = (ArrayList<JsonNode>)jsonNode.findValues("formattedAddress"); // 7
			System.out.println(ln3.size());
			
			
			List<JsonNode> ln5 = (ArrayList<JsonNode>)jsonNode.findValues("formattedPhone"); // 5
			System.out.println(ln5.size());
			for(int j = 0; j < ln2.size(); j+=2)
			{
				System.out.println(ln2.get(j) + " " + ln4.get(j));
				idName.put(ln2.get(j).toString(), ln4.get(j).toString());
		        //foursquareReview(username, ln2.get(j).toString());  
				
			}
			
			for(int j = 0; j < ln3.size()-2; j++)
			{
				System.out.println(ln3.get(j) + " " + ln5.get(j));
				//idName.put(ln2.get(j).toString(), ln4.get(j).toString());
		        //foursquareReview(username, ln2.get(j).toString());  
				
			}
			
		
			for(Map.Entry<String, String> m1: idName.entrySet())
		    {
		    		String id = m1.getKey();
		    		String name = m1.getValue();
		    		
		    		
		    		
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
        //foursquareReview(username, list);  
    }*/
	
	public static StringBuilder testFoursquare(String username, String search, String location) throws Exception
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
                     //list.add(a + ":"); 
                     
                     b = jo2.getString("name");
                     System.out.println("Name is: " + b);
                     //list.add(b + ":");
                     
                     c = jo5.getJSONArray("formattedAddress").toString().replaceAll("\\[", "").replaceAll("\\]","");
                     System.out.println("Address is: " + c);
                     //list.add(c + ":");
                     
                     d = jo4.getString("formattedPhone");
                     System.out.println("Phone number is: " + d);
                     //list.add(d + ":");
                     
                     //idMap.put(a + ":", b + ": " + c + ": " + d);
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
	/// WORKING METHOD///////
	
	/*public static void testFoursquare(String username, String search, String location) throws Exception
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
                     //list.add(a + ":"); 
                     
                     b = jo2.getString("name");
                     System.out.println("Name is: " + b);
                     //list.add(b + ":");
                     
                     c = jo5.getJSONArray("formattedAddress").toString().replaceAll("\\[", "").replaceAll("\\]","");
                     System.out.println("Address is: " + c);
                     //list.add(c + ":");
                     
                     d = jo4.getString("formattedPhone");
                     System.out.println("Phone number is: " + d);
                     //list.add(d + ":");
                     
                     //idMap.put(a + ":", b + ": " + c + ": " + d);
                     foursquareReview(username, a, b, c, d); 
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
        
    }*/
	
	public static StringBuilder foursquareReview(String username, String id, String placeName, String address, String phone) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Foursquare Reviews-------------");
        String url = "";
        
        StringBuilder hsb = new StringBuilder();
		//String fileName = createHTML(hsb);

        
        System.out.println();
        
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
            
        //hsb.append("<body>");
        //try
        //{	 
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
                hsb.append("<p>----------------------------------</p>");
                System.out.println(b + "\n"); 
            }  
       // }
        
        //catch(Exception e)
        //{

        //}
        hsb.append("</div>");
        System.out.println("-------------------------------");
        System.out.println();
        //createHTML(hsb);
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
	
	// WORKING METHOD//
	/*public static void foursquareReview(String username, String id, String placeName, String address, String phone) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Foursquare Reviews-------------");
        String url = "";
        WriteToHTML wth = new WriteToHTML();
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

        
        System.out.println();
        
       //for(String id: list)
        //{
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
            
            hsb.append("<body>");
            try
            {	 
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
                    System.out.println(b + "\n"); 
                }
                
            }
            
            catch(Exception e)
            {

            }
            hsb.append("</div>");
            System.out.println("-------------------------------");
            System.out.println();
    			
    			hsb.append("</body>");
    			hsb.append("</html>");
    			wth.WriteToFile(hsb.toString(),fileName);
            File f = new File(fileName);
            Desktop.getDesktop().browse(f.toURI());
        //}
    }*/
	
	/*public static void testFoursquare(String username, String search, String location) throws Exception
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
                     list.add(b + ":");
                 }
                 catch(Exception e)
                 {
                     System.out.println("No name");
                     e.printStackTrace();
                 }
                 
                 try
                 {
                     c = jo5.getJSONArray("formattedAddress").toString().replaceAll("\\[", "").replaceAll("\\]","");
                     System.out.println("Address is: " + c);
                     list.add(c + ":");
                 }
                 catch(Exception e)
                 {
                     System.out.println("No address");
                 }
                 
                 try
                 {
                     d = jo4.getString("formattedPhone");
                     System.out.println("Phone number is: " + d);
                     list.add(d + ":");
                 }
                 catch(Exception e)
                 {
                     System.out.println("No phone number");
                 }
                 
                 try
                 {
                     a = jo2.getString("id");
                     System.out.println("Id is: " + a);
                     list.add(a + ":");                    
                 }
                 catch(Exception e)
                 {
                     System.out.println("No id");
                 }
                 System.out.println();
             }
             System.out.println();
             
             for(String id: list)
             {
            	 	System.out.println(id);
            	 	
            	 	System.out.println();
             }
             System.out.println();
             
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //foursquareReview(username, list);  
    }*/

	/*public static void foursquareReview(String username, List<String> list) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Foursquare Reviews-------------");
        String url = "";
        WriteToHTML wth = new WriteToHTML();
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
        String item = list.toString().replaceAll("\"", " ").replaceAll("\\[", "").replaceAll("\\]","").replaceAll(",", "").replace("  ", " ");
        //System.out.println(item);
        String[] items = item.split(":");
        System.out.println(items[0].trim());
        System.out.println(items[1].trim());
        System.out.println(items[2].trim());
        System.out.println(items[3].trim());
        for(int i = 0; i < items.length; i++)
        {
        		System.out.println(items[i].trim());	
        		//System.out.println(items[i+4]);
        		
        		System.out.println("-----------------------------------");
        }
        System.out.println();
        
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
            hsb.append("<h4>" + id + "</h4>");
            JSONObject jo = new JSONObject(s);
            String b; 
            
            hsb.append("<body>");
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
                    //hsb.append("<p>" + b + "</p>");
                    System.out.println(b + "\n"); 
                }
                System.out.println("-------------------------------");
                System.out.println();
	    			
	    			hsb.append("</body>");
	    			hsb.append("</html>");
	    			wth.WriteToFile(hsb.toString(),fileName);
                File f = new File(fileName);
                Desktop.getDesktop().browse(f.toURI());
            }
            
            catch(Exception e)
            {

            }
        }
    }*/

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
        
        try 
        {
			ObjectMapper mapper = new ObjectMapper();
			double ratingAvg = 0;
			Map<String, Double> avgRatingMap = new HashMap<String, Double>();
			JsonNode jsonNode = mapper.readTree(s);
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("businesses");
			List<JsonNode> ln3 = (ArrayList<JsonNode>)jsonNode.findValues("id");
			List<JsonNode> ln4 = (ArrayList<JsonNode>)jsonNode.findValues("name");
			List<JsonNode> ln5 = (ArrayList<JsonNode>)jsonNode.findValues("rating");
			List<JsonNode> ln6 = (ArrayList<JsonNode>)jsonNode.findValues("price");
			List<JsonNode> ln7 = (ArrayList<JsonNode>)jsonNode.findValues("display_address");
			List<JsonNode> ln8 = (ArrayList<JsonNode>)jsonNode.findValues("display_phone");
			for(int i = 0 ; i < ln2.size(); i++)
			{
				System.out.println("---------------------------------------------");
				for(int j = 0; j < ln5.size(); j++)
				{
					ratingAvg = Double.parseDouble(ln5.get(j).toString().replaceAll("\"", " "));
				    avgRatingMap.put(ln3.get(j).toString().replace("\"", "") + " : " +  ln4.get(j).toString().replace("\"", "") + " : " + ln5.get(j).toString().replace("\"", "") + " : " + ln6.get(j).toString().replace("\"", "") + " : " + ln7.get(j).toString().replace("\"", "") + " : " + ln8.get(j).toString().replace("\"", ""), ratingAvg);
				}  
			}
			
			for(Map.Entry<String, Double> m1: avgRatingMap.entrySet())
		    {
		    		String tx = m1.getKey();
		    		double rt = m1.getValue();
		    		if(rt >= rating)
	    			{
	    				
	    				String[] itemArray = tx.split(" : ");
	    				System.out.println(itemArray[0]); // ID
	    				System.out.println(itemArray[1]); // Place Name
	    				System.out.println(itemArray[2]); // Avg Rating
	    				System.out.println(itemArray[3]); // Price Range
	    				System.out.println(itemArray[4]); // Address
	    				System.out.println(itemArray[5]); // Phone		
	    				yelpReview(username, itemArray[1], itemArray[0], rating, itemArray[4], itemArray[5], itemArray[3], rt);
	    				System.out.println();
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
		OkHttpClient client = new OkHttpClient();
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
        try 
        {
        		WriteToHTML wth = new WriteToHTML();
        		Map<String, Double> review = new HashMap<String, Double>();
			ObjectMapper mapper = new ObjectMapper();
			JsonNode jsonNode = mapper.readTree(s);

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
            
            hsb.append("<h3>Place Name: <a href=\"https://www.yelp.com/biz/"+ ID + "\" target=\"_blank\">"+ placeName + "</a></h3>");
			
            hsb.append("<h3>Price Range: " + priceRange + "</h3>");
			
            hsb.append("<h3> Phone Number: " + phoneNumber + " </h3>");
            hsb.append("<h3>Address: <a href=\"https://www.google.com/maps/place/"+ address.replaceAll("\\[", "").replaceAll("\\]","") + "\" target=\"_blank\">"+ address.replaceAll("\\[", "").replaceAll("\\]","") + "</a></h3>");
            hsb.append("<h3>Average Rating : <img src=\"" + getYelpRatingImage(avgRating) + "\" alt=\"" + avgRating + "\"></h3>");
            hsb.append("<p>-------------------------------------------------------------</p>");

            double a = 0;
			
			List<JsonNode> ln2 = jsonNode.findValues("reviews");
			
			hsb.append("<img src=\"yelp1.png\" alt=\"Yelp\" align=\"left\">\n");
			hsb.append("<div id = \"reviewsTitle\">\n");
            
            hsb.append("<h1><span style=\"color: #060606\">R</span><span style = \"color: #C60C1B\">e</span><span style = \"color: #060606\">v</span><span style = \"color: #C60C1B\">i</span></span><span style = \"color: #060606\">e</span><span style = \"color: #C60C1B\">w</span><span style = \"color: #060606\">s</span></h1>\n");
            hsb.append("</div>\n");
			
            hsb.append("<br>");
            //hsb.append("</br>");
			for(int i = 0; i < ln2.size(); i++)
			{
	            	List<JsonNode> ln3 = (ArrayList<JsonNode>) jsonNode.findValues("rating");
			    List<JsonNode> ln4 = (ArrayList<JsonNode>) jsonNode.findValues("text");
	    			List<JsonNode> ln5 = (ArrayList<JsonNode>) jsonNode.findValues("time_created");
	    			String hours = getYelpBusinessHours(ID);
	    			String strArray[] = hours.split(",");
	    			
	    			hsb.append("<div id = \"businessHoursHeader\">\n");
	    			hsb.append("<h3>Business Hours</h3>");
	    			hsb.append("</div>\n");
	    			
	    			hsb.append("<div id = \"businessHours\">\n");
	    			for(int j = 0; j < strArray.length; j++)
	    			{
	    				//System.out.println(strArray[j]);
	    				hsb.append("<h4>" + strArray[j] + "</h4>");
	    			}
	    			hsb.append("<br>");
	    			hsb.append("</div>\n");
			    
	    			for(int j = 0; j < ln3.size(); j++)
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
	    				hsb.append("<img src=\"" + getYelpRatingImage(value) + "\" alt=\"" + value + "\" align=\"left\"> <p> &nbsp : &nbsp " +  key.replace("\"", "") +  "\"</p>");			
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
	    				String item = tx + " : " + getStar((int) rt);
	    				String[] itemArray = tx.split(" : ");
	    				System.out.println(itemArray[0]);
	    				System.out.println(itemArray[1]);
	    				System.out.println(itemArray[2]);
	    				System.out.println(rt + " : " +  getStar((int) rt));
	    				
	    				System.out.println();
	    				googleReview(username, itemArray[1], rating, itemArray[2], rt);

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
				
				hsb.append("<h3> Phone Number: " + ln1.get(i).toString().replace("\"", "") + " </h3>\n");
				hsb.append("<h3> Address: \n<a href=\"https://www.google.com/maps/place/"+ ln5.get(i).toString().replace("\"", "") + "\" target=\"_blank\">"+ ln5.get(i).toString().replace("\"", "") + "</a></h3>\n");
				hsb.append("<h3>Average Rating: &nbsp " + getStar((int) averageRating) + "</h3>");			
			}
			
			List<JsonNode> ln2 = (ArrayList<JsonNode>)jsonNode.findValues("reviews");
			double a = 0;
				
			/*------------------------------------------------------------------------------------*/
			hsb.append("<p>-------------------------------------------------------------</p>\n");
			hsb.append("<img src=\"powered_by_google_on_white.png\" alt=\"Powered by Google\" align=\"left\">\n");
			hsb.append("<div id = \"reviewsTitle\">\n");
            hsb.append("<h1><span style=\"color: #346DF1\">R</span><span style = \"color: #E23E3E\">e</span><span style = \"color: #F8B823\">v</span><span style = \"color: #2D9B42\">i</span></span><span style = \"color: #F8B823\">e</span><span style = \"color: #E23E3E\">w</span><span style = \"color: #346DF1\">s</span></h1>\n");
            hsb.append("</div>\n");
            
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
	    				String item = getStar((int) value) + " : " + key;	
	    				System.out.println(item);
	    				hsb.append("<p>" + item + "</p>\n");
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
        		//String x = getYelpBusinessHours("ldPsoxeD3bPgwXWwF2_QJA");
        		
        		//testYelp("", term, location, rating);
        		//testGoogle("", term, location, rating);
        		 StringBuilder search = testFoursquare("", term, location);
        		 createHTML(search);
        		 
        }
        else
        {
        		System.out.println("Invalid rating");
        }
        scanner.close();
    }
}

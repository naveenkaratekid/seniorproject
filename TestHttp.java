package com.cogswell.seniorproject;

import okhttp3.*;
import okio.*;
import org.json.*;
import java.util.*;
import java.net.*;
import java.text.*;

/**
 * Write a description of class testhttp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestHttp
{
    private static Scanner scanner;
    SearchDAO sd = new SearchDAO();
    // Methods containing the word 'test' are the ones that set up the API call and perform the actual search
    // Methods containing the word 'review' are the ones that display review text based on rating number and what was retrieved from the search.
    
    ///////////////////////////////////
    public static void testFoursquare(String search, String location) throws Exception
    {
        // Foursquare OAuth2 access token: P14YL1YOGUDWTKBWER14IDBWV4BYBW4ASKCC2GRGDY3F5BZQ
        OkHttpClient client = new OkHttpClient();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
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
             }
             System.out.println("----------------");
             System.out.println();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        foursquareReview(list);  
    }
    
    public static void foursquareReview(List<String> list) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Foursquare Reviews-------------");
        String url = "";
        
        for(String id: list)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
            String date = sdf.format(new Date());
            
            url = "https://api.foursquare.com/v2/venues/"+id+"/tips?oauth_token=P14YL1YOGUDWTKBWER14IDBWV4BYBW4ASKCC2GRGDY3F5BZQ&v="+date; 
            Request request = new Request.Builder()
              .url(url)
              .get()
              .build();
            
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            System.out.print(id + "\n");
            //System.out.println(s);
            //"\"Hello\""
            // {"meta":{"code":400,"errorType":"param_error","errorDetail"
            //"{" + "\"meta\"" + ":{""\code\""+":400," + "\"errorType\""+ ":" + "\"param_error\""+"," + "\"errorDetail\""
            //"{""\"meta\""":{""\code\""":400,""\"errorType\""":""\"param_error\""",""\"errorDetail\""
            //String err = "{"+"\"meta\"" + ":{"+"\"code\"" + ":400," + "\"errorType\""+ ":" + "\"param_error\""+ "," + "\"errorDetail\""; 
            //if(s.contains(err))
            //{
                //System.out.println("Ignore");
            //}
            JSONObject jo = new JSONObject(s);
            String b; 
            
            try
            {
                JSONObject jo1 = jo.getJSONObject("response").getJSONObject("tips");
                JSONArray ja = jo1.getJSONArray("items");
                try
                {
                    for(int i = 0; i < ja.length(); i++)
                    {
                        JSONObject jo2 = ja.getJSONObject(i);
                        b = jo2.getString("text");
                        //"\"Hello\""
                        
                        System.out.println("\n" + b);                      
                    }
                    System.out.println();
                    
                }
                catch(Exception e)
                {
                    System.out.println("No text available");
                }
                System.out.println("-------------------------------");
            }
            catch(Exception e)
            {
                
            }
        }
    }
    
    
    ///////////////////////////////////
    public static String testGoogle(String search, String location, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Google Test-------------");
        String searchLocation = search + " " + location;
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+searchLocation+"&radius=500&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw";
        Request request = new Request.Builder()
          .url(url)
          .get()
          .build();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        JSONObject jo = new JSONObject(s);
        ArrayList<String> list = new ArrayList<String>();
        Map<String, ArrayList<String>> addressMap = new HashMap<String, ArrayList<String>>();
        int a;
        String b, c, d;
        try
        {
            JSONArray ja1 = jo.getJSONArray("results");
            
            for(int i = 0; i < ja1.length(); i++)
            {
                JSONObject jo1 = ja1.getJSONObject(i);
                
                try
                {
                    a = jo1.getInt("rating");
                    b = jo1.getString("place_id");
                    list.add(b);
                    
                    // create method that takes in the place ID and generate reviews
                    c = jo1.getString("name");
                    d = jo1.getString("formatted_address");
                    addressMap.put(d, list);
                    System.out.println("Average Rating: " + a + " " + getStar(a));
                    System.out.println("Place ID: "  + b);
                    System.out.println("Name: "  + c);
                    System.out.println("Address: "  + d);
                }
                catch(Exception e)
                {
                    System.out.println("No information available");
                }
                System.out.println();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("There is no info");
        }
        googleReview(list, rating); 
        return "";
    }
    
    public static void googleReview(List<String> list, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Google Reviews-------------");
        String url = "";
        Map<String, Integer> ratingText = new HashMap<String, Integer>();
        System.out.println(String.format("These are all %d %s star reviews\n", rating, getStar(rating)));
        
        for(String id: list)
        {
            url = "https://maps.googleapis.com/maps/api/place/details/json?placeid="+id+"&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw";
            Request request = new Request.Builder()
              .url(url)
              .get()
              .build();
            
            Response response = client.newCall(request).execute();
            System.out.println();
            System.out.println("Id is: " + id);
            System.out.println("URL is: " + url);
            String s = response.body().string();
            
            JSONObject jo = new JSONObject(s);
          
            int a = 0;
            String b = "";
            JSONObject jo2 = jo.getJSONObject("result"); 
            try
            {
                JSONArray ja2 = jo2.getJSONArray("reviews");
                for(int i = 0; i < ja2.length(); i++)
                {
                    JSONObject jo1 = ja2.getJSONObject(i);
                    a = jo1.getInt("rating");
                    if(a >= rating)
                    {
                    		b = jo1.getString("text");
                    		System.out.println(getStar(a) + " " + b);
                    }
                    b = jo1.getString("text");
                    
                    ratingText.put(b, (int)a); 
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    //////////////////////////
    
    public static String testYelp(String term, String location, int rating) throws Exception
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
        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("businesses");
        String a,b,c,d;
        double e = 0;
        
        ArrayList<String> idList = new ArrayList<String>();
        for(int i = 0; i < ja.length(); i++)
        {
            JSONObject jo1 = ja.getJSONObject(i);
            
            try
            {
                a = jo1.getString("id");
                System.out.println("Id: " + a);
                idList.add(a);
            }
            catch(Exception ex)
            {
                System.out.println("There is no id name for this business");
            }
            
            try
            {
                JSONObject jo2 = jo1.getJSONObject("location");
                b = jo2.getJSONArray("display_address").toString();
                System.out.println(b);
            }
            catch(Exception ex)
            {
                System.out.println("There is no location information for this business");
            }

            try
            {
                c = jo1.getString("display_phone");
                System.out.println("Phone Number: " + c);
                
            }
            catch(Exception ex)
            {
                System.out.println("There is no phone number for this business");
            }
            
            try
            {
                d = jo1.getString("price");
                System.out.println("Price Range: " + d);
                
            }
            catch(Exception ex)
            {
                System.out.println("There is no price range for this business");
            }
            
            try
            {
                e = jo1.getDouble("rating");
                System.out.println("Average Rating: " + e + " " + getStar((int)e));
            }
            catch(Exception ex)
            {
                System.out.println("There are no ratings for this business");
            }
            
            System.out.println();
            System.out.println();
        }
        yelpReview(idList, rating);
        return "";
    }  
    
    /*
     * testYelp() will perform a general search using Yelp's API. Note that this method will not
     * return user ratings and reviews. Those will be done in the yelpReview() method below
     */
    public static void testYelp(String term, String location, double rating, int price, int radius) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Yelp Test-------------");
        int radiusMeters = radius * (int)1609.34;
        // miles
        System.out.println("Radius: " + radius + " meters");
        String url = "https://api.yelp.com/v3/businesses/search?term="+term+"&location="+location+"&price="+price+"&radius="+radiusMeters;
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .build();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("businesses");
        String a,b,c,d;
        double e = 0;
        ArrayList<String> idList = new ArrayList<String>();
        for(int i = 0; i < ja.length(); i++)
        {
            JSONObject jo1 = ja.getJSONObject(i);
            
            try
            {
                a = jo1.getString("id");
                System.out.println("Id: " + a);
                idList.add(a);
            }
            catch(Exception ex)
            {
                System.out.println("There is no id name for this business");
            }
            
            try
            {
                JSONObject jo2 = jo1.getJSONObject("location");
                b = jo2.getJSONArray("display_address").toString();
                System.out.println(b);
            }
            catch(Exception ex)
            {
                System.out.println("There is no location information for this business");
            }

            try
            {
                c = jo1.getString("display_phone");
                System.out.println("Phone Number: " + c);
            }
            catch(Exception ex)
            {
                System.out.println("There is no phone number for this business");
            }
            
            try
            {
                d = jo1.getString("price");
                if (price >= getPrice(d))
                {
                    System.out.println("Price Range: " + d);
                }
            }
            catch(Exception ex)
            {
                System.out.println("There is no price range for this business");
            }
            
            try
            {
                e = jo1.getInt("rating");
                System.out.println("Average Rating: " + e + " " + getStar((int)e));
            }
            catch(Exception ex)
            {
                System.out.println("There are no ratings for this business");
            }
            
            System.out.println();
            System.out.println();
        }
        yelpReview(idList, (int)rating);
    }
    
    /*
     * yelpReview() will take in a list of IDs as well as the rating from user's input
     * and return the appropriate ratings >= the user's preferred rating. 
     * Plus, it will return text based on that rating
     */
    public static void yelpReview(List<String> list, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Yelp Reviews-------------");
        String url = "";
        System.out.println(String.format("These are all reviews >= %d %s stars\n", rating, getStar(rating)));
        for(String id: list)
        {
            url = "https://api.yelp.com/v3/businesses/" + id + "/reviews"; 
            Request request = new Request.Builder()
              .url(url)
              .get()
              .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
              .build();
     
            Response response = client.newCall(request).execute();
            System.out.println();
            System.out.println("Id is :" + id);
            System.out.println("URL is :" + url);
            String s = response.body().string();
            JSONObject jo = new JSONObject(s);
            try
            {
                JSONArray ja = jo.getJSONArray("reviews");
                double a = 0;
                String b = "";
                for(int i = 0; i < ja.length(); i++)
                {
                    JSONObject jo1 = ja.getJSONObject(i);
                    a = jo1.getInt("rating");
                    b = jo1.getString("text");
                    if(a >= rating)
                    {
                        System.out.println(a + " : Rating: " + getStar((int)a) + String.format("\n") + b);
                        System.out.println();
                    }
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("There are no reviews for this place yet");
            } 
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
    
    // create search method to save into database the criteria ONLY and to call the appropriate APIs for search sites
    public static void search(String searchTerm, String cityOrZip, String rating, int distance, int price, ArrayList<String> listOfFilters) throws Exception
    {
        int r = getRating(rating);
        for(String searchFilterName: listOfFilters)
        {
            switch(searchFilterName)
            {
                case "yelp":
                    //testYelp(searchTerm, cityOrZip, r, price, distance);
                    testYelp(searchTerm, cityOrZip, r);
                    break;
                    
                case "foursquare":
                    testFoursquare(searchTerm, cityOrZip);
                    break;
                    
                case "google": case "google places":
                    testGoogle(searchTerm, cityOrZip, r);
                    break;
                    
                default:
                    System.out.println("Sorry. Your search criteria is not valid");
                    break;
            }
        }
    }
    
    public static void main(String[] args) throws Exception
    {
    		System.out.println(System.getProperty("user.dir"));
    		
        scanner = new Scanner(System.in);
        System.out.println("Enter a search term: ");
        String term = scanner.nextLine();
        
        System.out.println("Enter a location (City | zipcode, | specific address: ");
        String location = scanner.nextLine();

        System.out.println("Enter a price range. If no input is given, program will default to $: ");
        String price = scanner.nextLine();
        int priceRange;
        switch(price)
        {
            case "$":
            priceRange = 1;
            break;
            
            case "$$":
            priceRange = 2;
            break;
            
            case "$$$":
            priceRange = 3;
            break;
            
            default:
            priceRange = 1;
        }
        
        System.out.println("Enter a radius in miles. If no input is given, it will default to 1 mile: ");
        String r = scanner.nextLine();
        
        int radius = 0; 
        if(r.equals(""))
        {
            radius = 5;
        }
        else
        {
            radius = Integer.parseInt(r);
        }

        System.out.println("Filter by rating. If no input is given, it will default to ratings >= 3 stars: ");
        String ratingNum = scanner.nextLine();
        int rating;
        if(ratingNum.equals(""))
        {
            rating = 3;
        }
        else
        {
            rating = Integer.parseInt(ratingNum);
        }
        
        ArrayList<String> listOfFilters = new ArrayList<String>();
        
        for(int i = 0; i < 3; i++)
        {
            System.out.println("Enter a list of filters: ");
            String filter = scanner.nextLine();
            if(filter.equals(""))
            {
                System.out.println("You didn't enter anything");
            }
            else
            {
                filter.toLowerCase();
                listOfFilters.add(filter);
            }
            
        }
        search(term, location, getStar(rating), radius, priceRange, listOfFilters);
    }
}

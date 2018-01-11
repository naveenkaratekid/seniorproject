import okhttp3.*;
import okio.*;
import org.json.*;
import java.util.*;
import java.net.*;
/**
 * Write a description of class testhttp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class testhttp
{
    // instance variables - replace the example below with your own
    private static Scanner scanner;
    public static String testGoogle(String search, String location) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        String searchLocation = search + " " + location;
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + searchLocation + "&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw";
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw")
          .addHeader("Cache-Control", "no-cache")
          .addHeader("Postman-Token", "3c584ef2-382a-4214-d26b-3f2a96908862")
          .build();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("html_attributions");
        ArrayList<String> list = new ArrayList<String>();
        int a;
        String b, c, d,e;
        try
        {
            JSONArray ja1 = jo.getJSONArray("results");
            
            for(int i = 0; i < ja1.length(); i++)
            {
                JSONObject jo1 = ja1.getJSONObject(i);
                a = jo1.getInt("rating");
                b = jo1.getString("place_id");
                list.add(b);
                
                // create method that takes in the place ID and generate reviews
                c = jo1.getString("name");
                d = jo1.getString("formatted_address");
                
                System.out.println("Rating: " + a);
                System.out.println("Place ID: "  + b);
                System.out.println("Name: "  + c);
                System.out.println("Address: "  + d);
                System.out.println();
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            System.out.println("There is no info");
        }
        googleReview(list);
        return "";
    }
    
    public static String googleReview(List<String> list) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Reviews-------------");
        String url = "";
        for(String id: list)
        {
            url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + id + "&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw"; 
            Request request = new Request.Builder()
              .url(url)
              .get()
              .addHeader("Authorization", "AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw")
              .addHeader("Cache-Control", "no-cache")
              .addHeader("Postman-Token", "d7074597-b2ad-ee30-71c9-34cabb7548a7")
              .build();
            
            Response response = client.newCall(request).execute();
            System.out.println();
            System.out.println("Id is: " + id);
            System.out.println("URL is: " + url);
            String s = response.body().string();
            
            JSONObject jo = new JSONObject(s);
            JSONArray ja = jo.getJSONArray("html_attributions");
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
                    b = jo1.getString("text");
                    System.out.println("Rating: " + a);
                    System.out.println("Text :"  + b);
                    
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                //System.out.println("There are no reviews for this place yet");
            }
            
            
        }
        
        //String url = "https://api.yelp.com/v3/businesses/" + id + "/reviews";
        
        return "";
    }

   
    //////////////////////////
    
    public static String testYelp(String term, String location) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        String url = "https://api.yelp.com/v3/businesses/search?term=" + "\"" + term + "\"" + "&location=\"" + location + "\"";
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .addHeader("Cache-Control", "no-cache")
          .addHeader("Postman-Token", "28467295-872e-1d4b-2d95-1468cfbc9110")
          .build();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("businesses");
        String a,b,c,d,f,g;
        int e;
        ArrayList<String> list = new ArrayList<String>();
        for(int i = 0; i < ja.length(); i++)
        {
            JSONObject jo1 = ja.getJSONObject(i);
            
            try
            {
                a = jo1.getString("id");
                System.out.println("Id: " + a);
                list.add(a);
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
                c = jo1.getString("phone");
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
                e = jo1.getInt("rating");
                System.out.println("Rating: " + e);
            }
            catch(Exception ex)
            {
                System.out.println("There are no ratings for this business");
            }
            
            System.out.println();
            System.out.println();
        }
        yelpReview(list);
        return list.toString();
    }
    
    public static String yelpReview(List<String> list) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Reviews-------------");
        String url = "";
        for(String id: list)
        {
            url = "https://api.yelp.com/v3/businesses/" + id + "/reviews"; 
            Request request = new Request.Builder()
              .url(url)
              .get()
              .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
              .addHeader("Cache-Control", "no-cache")
              .addHeader("Postman-Token", "dcd71075-0aff-d401-06dd-f4f2d418ea43")
              .build();
            
            Response response = client.newCall(request).execute();
            System.out.println();
            System.out.println("Id is :" + id);
            System.out.println("URL is :" + url);
            String s = response.body().string();
            
            JSONObject jo = new JSONObject(s);
            //JSONArray ja1 = jo.getJSONArray("result");
            try
            {
                JSONArray ja = jo.getJSONArray("reviews");
                int a = 0; 
                String b = "";
                for(int i = 0; i < ja.length(); i++)
                {
                    JSONObject jo1 = ja.getJSONObject(i);
                    a = jo1.getInt("rating");
                    b = jo1.getString("text");
                    System.out.println("Rating: " + a);
                    System.out.println("Text :"  + b);
                    
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
                System.out.println("There are no reviews for this place yet");
            }
            
            
        }
        
        //String url = "https://api.yelp.com/v3/businesses/" + id + "/reviews";
        
        return "";
    }
    
    
    public static void main(String[] args) throws Exception
    {
        scanner = new Scanner(System.in);
        System.out.println("Enter a search term: ");
        String term = scanner.nextLine();
        
        System.out.println("Enter a location: ");
        String location = scanner.nextLine();
       
        testYelp(term, location);
        //testGoogle(term, location);
    }
}

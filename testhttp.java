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
public class testhttp
{
    // instance variables - replace the example below with your own
    private static Scanner scanner;
    
    ///////////////////////////////////
    public static String testFoursquare(String search, String location) throws Exception
    {
        // Foursquare OAuth2 access token: P14YL1YOGUDWTKBWER14IDBWV4BYBW4ASKCC2GRGDY3F5BZQ
        OkHttpClient client = new OkHttpClient();
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
        String date = sdf.format(new Date());
        String url = "https://api.foursquare.com/v2/venues/search?near=" + location + "&query=" + search + "&v=" + date + "&client_secret=SNZBRYNWZ0YPETJKXGW4LGGEX0KHM2Q2JOOJJH1UBAJ2CQD0&client_id=RZRA3MOZSBX1R3Y1ZUFXUO3YT2BSKBWSSRKXX3G3LKUQDBEU";
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Cache-Control", "no-cache")
          .addHeader("Postman-Token", "78e7c7fe-5b16-f5dc-d84d-c94d3041627a")
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
                     a = jo2.getString("id");
                     System.out.println("Id is: " + a);
                     list.add(a);
                 }
                 catch(Exception e)
                 {
                     System.out.println("No id");
                 }
                 
                 try
                 {
                     b = jo2.getString("name");
                     System.out.println("Name is: " + b);
                 }
                 catch(Exception e)
                 {
                     System.out.println("No name");
                 }
                 
                 try
                 {
                     c = jo5.getJSONArray("formattedAddress").toString();
                     System.out.println("Address is: " + c);
                 }
                 catch(Exception e)
                 {
                     System.out.println("No address");
                 }
                 
                 try
                 {
                     d = jo4.getString("formattedPhone");
                     System.out.println("Phone number is: " + d);
                 }
                 catch(Exception e)
                 {
                     System.out.println("No phone number");
                 }
                 System.out.println();
                 
             }
             
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        foursquareReview(list);
        return "";
    }
    
    public static String foursquareReview(List<String> list) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Reviews-------------");
        String url = "";
        for(String id: list)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMDD");
            String date = sdf.format(new Date());
            url = "https://api.foursquare.com/v2/venues/"+id+"/tips?oauth_token=P14YL1YOGUDWTKBWER14IDBWV4BYBW4ASKCC2GRGDY3F5BZQ&v="+date; 
            Request request = new Request.Builder()
              .url(url)
              .get()
              .addHeader("Cache-Control", "no-cache")
              .addHeader("Postman-Token", "82f7de10-1cc3-4a61-2200-2557d46eb74a")
              .build();
            
            Response response = client.newCall(request).execute();
            String s = response.body().string();
            JSONObject jo = new JSONObject(s);
            String b,c,d; 
            
            try
            {
                JSONObject jo1 = jo.getJSONObject("response").getJSONObject("tips");
              
                try
                {
                    JSONArray ja = jo1.getJSONArray("items");
                    
                    for(int i = 0; i < ja.length(); i++)
                    {
                        JSONObject jo2 = ja.getJSONObject(i);
                        b = jo2.getString("text");
                        System.out.println(b);
                        System.out.println();
                        
                    }
                
                    
                }
                catch(Exception e)
                {
                    System.out.println("No items");
                }
            }
            catch(Exception e)
            {
                System.out.println("No tips available");
            }
            
            
            /*JSONObject jo1 = jo.getJSONObject("response").getJSONObject("tips");
              
            try
            {
                JSONArray ja = jo1.getJSONArray("items");
                
                for(int i = 0; i < ja.length(); i++)
                {
                    JSONObject jo2 = ja.getJSONObject(i);
                    b = jo2.getString("text");
                    System.out.println(b);
                    System.out.println();
                    
                }
            
                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }*/

        }
        System.out.println("------------------------------------------------------------------------");
        return "";
    }
    
    ///////////////////////////////////
    
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
    
    public static String testYelp(String term, String location, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        
        String url = "https://api.yelp.com/v3/businesses/search?term="+term+"&location="+location;
        
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
        int e = 0;
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
                System.out.println("Rating: " + rating);
                //System.out.println("Rating: " + rating);
                
            }
            catch(Exception ex)
            {
                System.out.println("There are no ratings for this business");
            }
            //System.out.println("The rating is " + e);
            //yelpReview(list, rating);
            
            System.out.println();
            System.out.println();
        }
        //yelpReview(list);
        yelpReview(list, rating);
        //System.out.println("The rating is " + e);
        return list.toString();
    }
    
    public static String testYelp(String term, String location, int price, int radius) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        radius *= 1609.34;
        //String url = "https://api.yelp.com/v3/businesses/search?term=" + "\"" + term + "\"" + "&location=\"" + location + "\"";
        String url = "https://api.yelp.com/v3/businesses/search?term="+term+"&location="+location+"&price="+price+"&radius="+radius;
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
        int e = 0;
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
        //yelpReview(list);
        return list.toString();
    }
    
    public static String yelpReview(List<String> list, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Reviews-------------");
        System.out.println();
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
                //e.printStackTrace();
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

        System.out.println("Enter a price range: ");
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
            priceRange = 2;
        }
        
        /*System.out.println("Enter a radius: ");
        String r = scanner.nextLine();
        try
        {
            int radius = Integer.parseInt(r);
        }
        catch(NumberFormatException nfe)
        {
            System.out.println("You didn't enter a number");
        }*/
        System.out.println("Filter by rating: ");
        int rating = scanner.nextInt();
        testYelp(term, location, rating);
        
        //testFoursquare(term, location);
        //testGoogle(term, location);
    }
}

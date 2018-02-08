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
    
    // Methods containing the word 'review' are the ones that display review text based on rating number and what was retrieved from the search.
    // Methods containing the word 'test' are the ones that set up the API call and perform the actual search
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
    public static String testGoogle(String search, String location, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        String searchLocation = search + " " + location;
        //String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + searchLocation + "&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw";
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query="+searchLocation+"&radius=500&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw";
        Request request = new Request.Builder()
          .url(url)
          .get()
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
                
                System.out.println("Average Rating: " + a + " " + getStar(a));
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
        googleReview(list, rating);
        return "";
    }
    
    public static String googleReview(List<String> list, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Google Reviews-------------");
        String url = "";
        //HashMap<Integer, String> ratingText = new HashMap<Integer, String>();
        Map<String, Integer> ratingText = new HashMap<String, Integer>();
        for(String id: list)
        {
            //url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + id + "&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw"; 
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
                    ratingText.put(b, (int)a);
                    
                }

                for(HashMap.Entry<String, Integer> m1: ratingText.entrySet())
                {
                    int r = m1.getValue();
                    if(r == rating)
                    {
                        System.out.println(r + ": Rating: " + getStar(r)+ "," + m1.getKey());
                        System.out.println();
                    }
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
          .build();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("businesses");
        String a,b,c,d,f,g;
        double e = 0;
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
        yelpReview(list, rating);
        return "";
    }
    
    
    public static String testYelp(String term, String location, double rating, int price, int radius) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        //double radius = radiusMiles * 1609.34;
        radius *= 1609.34;
        // miles
        System.out.println("Radius: " + radius + " meters");
        
        String url = "https://api.yelp.com/v3/businesses/search?term="+term+"&location="+location+"&price="+price+"&radius="+radius;
        Request request = new Request.Builder()
          .url(url)
          .get()
          .addHeader("Authorization", "Bearer ImCmvvA0XcBaU7KsxqVJYelpHHW1ftRTH8rOaTbYHph-7JfrXLhlGVWmtz3aIkhcrVebKlkII5YDnsmsOCynMvA4vACvehTV6mDhIt0E1Kq6dmorjJNrsalilge3WXYx")
          .build();
        
        Response response = client.newCall(request).execute();
        String s = response.body().string();
        JSONObject jo = new JSONObject(s);
        JSONArray ja = jo.getJSONArray("businesses");
        String a,b,c,d,f,g;
        double e = 0;
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
                System.out.println("Average Rating: " + e + " " + getStar((int)e));
            }
            catch(Exception ex)
            {
                System.out.println("There are no ratings for this business");
            }
            
            System.out.println();
            System.out.println();
        }
        yelpReview(list, (int)rating);
        return "";
    }
    
    public static String yelpReview(List<String> list, int rating) throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Yelp Reviews-------------");
        System.out.println();
        String url = "";
        /* ratingText will store
         * the rating and the text associated with that particular rating,
         * making it easier to retrieve a particular string of text based on the rating number
         */
        
        Map<Integer, String> ratingText = new HashMap<Integer, String>(); 
        
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
            //JSONArray ja1 = jo.getJSONArray("result");
            
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
                    ratingText.put((int)a, b);
                }
                
                for(HashMap.Entry<Integer, String> m1: ratingText.entrySet())
                {
                    int r = m1.getKey();
                    if(r == rating)
                    {
                        System.out.println(r + ": Rating: " + getStar(r) + String.format("\n") + m1.getValue());
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
        
        return "";
    }
    
    public static int getRating(String rating)
    {
        switch(rating)
        {
            case "☆":
                return 1;
            case "☆☆":
                return 2;
            case "☆☆☆":
                return 3;
            case "☆☆☆☆":
                return 4;
            case "☆☆☆☆☆":
                return 5;
            default:
                return 3;
        }
    }
    
    public static String getStar(int rating)
    {
        switch(rating)
        {
            case 1:
                return "☆";
            case 2:
                return "☆☆";
            case 3:
                return "☆☆☆";
            case 4:
                return "☆☆☆☆";
            case 5:
                return "☆☆☆☆☆";
            default:
                return "☆☆☆";
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
                    testYelp(searchTerm, cityOrZip, r, price, distance);
                    break;
                    
                case "foursquare":
                    testFoursquare(searchTerm, cityOrZip);
                    break;
                    
                
                case "google":
                    testGoogle(searchTerm, cityOrZip, r);
                    break;
            }
            
            // store general search or user search
        }
        
        
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
            priceRange = 1;
        }
        
        System.out.println("Enter a radius in miles: ");
        String r = scanner.nextLine();
        
        int radius = 0;
        switch(r)
        {
            case "1":
            radius = 1;
            break;
            
            case "2":
            radius = 2;
            break;
            
            case "3":
            radius = 3;
            break;
            
            case "4":
            radius = 4;
            break;
            
            case "5":
            radius = 5;
            break;
            
            default:
            radius = 3;
        }
        
        
        System.out.println("Filter by rating: ");
        String ratingNum = scanner.nextLine();
        int rating;
        switch(ratingNum)
        {
            case "1":
            rating = 1;
            break;
            
            case "2":
            rating = 2;
            break;
            
            case "3":
            rating = 3;
            break;
            
            case "4":
            rating = 4;
            break;
            
            case "5":
            rating = 5;
            break;
            
            default:
            rating = 3;
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

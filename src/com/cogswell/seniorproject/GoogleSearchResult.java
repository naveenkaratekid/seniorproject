package com.cogswell.seniorproject;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.core.type.*;
import com.fasterxml.jackson.databind.*;
import okhttp3.*;
import okio.*;
import org.json.*;
import java.util.*;
import java.net.*;
import java.text.*;
import java.io.*;
import java.util.*;
/**
 * Write a description of class GoogleSearchResult here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GoogleSearchResult
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class GoogleSearchResult
     */
    public GoogleSearchResult()
    {
        // initialise instance variables
        x = 0;
    }

    public static void google() throws Exception
    {
        OkHttpClient client = new OkHttpClient();
        System.out.println("-------------Google Test-------------");
        String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=hertz millbrae,CA&radius=500&key=AIzaSyD5c5wzVL8X9RCspE6aA_BOGU5W8UjS0Pw";
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
        
        try {

            ObjectMapper mapper = new ObjectMapper();
            String json = s;

            Map<String, Object> map = new HashMap<String, Object>();

            // convert JSON string to Map
            map = mapper.readValue(json,    new TypeReference<Map<String, Object>>(){});
            
            Object values = map.get("results");
            ArrayList<LinkedHashMap<String, String>> result = (ArrayList<LinkedHashMap<String, String>>) map.get("results");
            
            for(Map<String, String> m1: result)
            {
            		for(Map.Entry<String, String> x1: m1.entrySet())
            		{
            			String key = x1.getKey();
            			String value = x1.getValue();
            			System.out.println(key + " " + value);
            		}
            }
            
            for(Map.Entry<String, Object> x: map.entrySet())
            {
                //System.out.println(x);
                String key = x.getKey();
                //Object value = x.getValue();
                //LinkedHashMap<String, Object> values = (LinkedHashMap<String, Object>) x.getValue();
                for(Map.Entry<String, Object> y: map.entrySet())
                {
                		
                		System.out.println(key + " " + y);
                }
                //System.out.println(key + " " + value);
                System.out.println();
            }
            //System.out.println(map);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    public static void main(String[] args) throws Exception
    {
        google();
    }
}

package com.example.physicswallah;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private static final String LOG_TAG=QueryUtils.class.getSimpleName();
    public static List<PhysicsWallah> fetchResponseFromInternet(String stringUrl) {
        URL url = createUrl(stringUrl);
        String JSONresponse = "";
        try {
            JSONresponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error in making connection", e);
        }
        List<PhysicsWallah> list = extractFeaturesfromJson(JSONresponse);
        Log.v(LOG_TAG, "fetchResponseFromInternet() exectued....");

        return list;
    }
    public static URL createUrl(String stringUrl){
        URL url=null;
        try{
            url=new URL(stringUrl);
        }
        catch (MalformedURLException e){
            Log.e(LOG_TAG,"Url creating error",e);
        }
        return url;
    }
    public static String makeHttpRequest(URL url) throws IOException {
        HttpURLConnection urlConnection=null;
        InputStream inputStream=null;
        String JSON="";
        if(url==null)return JSON;
        try{
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setReadTimeout(50000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                inputStream=urlConnection.getInputStream();
                JSON= readerStream(inputStream);
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"URL Connection Error",e);
        }finally {
            if(urlConnection!=null)urlConnection.disconnect();
            if(inputStream!=null)inputStream.close();
        }

        return JSON;
    }
    public static ArrayList<PhysicsWallah> extractFeaturesfromJson(String json) {
        if(TextUtils.isEmpty(json))return null;
        // Create an empty ArrayList that we can start adding physicsWallahs to
        ArrayList<PhysicsWallah> physicsWallahs = new ArrayList<PhysicsWallah>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject baseJsonObj=new JSONObject("{\"key\":"+json+"}");
            JSONArray physicsArray=baseJsonObj.getJSONArray("key");
            for(int i=0;i<physicsArray.length();i++)
            {
                JSONObject current=physicsArray.getJSONObject(i);
                int id=current.getInt("id");
                String name=current.getString("name");
                ArrayList<String> subjects=new ArrayList<>();
                ArrayList<String> qualifications=new ArrayList<>();
                JSONArray subjectArray=current.getJSONArray("subjects");
                JSONArray qualificationArray=current.getJSONArray("qualification");
                for(int j=0;j<subjectArray.length();j++){
                    String sub = subjectArray.getString(j);
                    subjects.add(sub);
                }
                for(int j=0;j<qualificationArray.length();j++){
                    String sub = qualificationArray.getString(j);
                    qualifications.add(sub);
                }
                String profileImageUrl=current.getString("profileImage");
                physicsWallahs.add(new PhysicsWallah(id,name,subjects,qualifications,profileImageUrl));
            }

            // build up a list of objects with the corresponding data.

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the physicsWallah JSON results", e);
        }

        // Return the list of physicsWallahs
        return physicsWallahs;
    }
    private static String readerStream(InputStream inputStream) throws IOException{
        StringBuilder output=new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}

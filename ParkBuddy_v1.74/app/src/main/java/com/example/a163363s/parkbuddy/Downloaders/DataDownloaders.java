package com.example.a163363s.parkbuddy.Downloaders;

import android.renderscript.ScriptGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import com.example.a163363s.parkbuddy.DataModels.Location;

public class DataDownloaders {

    ArrayList<Location> locationInfo = new ArrayList<Location>();


    public String startDownload(String strUrl) {
        try
        {
            String json = "";
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.connect();

            int responseCode = conn.getResponseCode();

            if(responseCode == HttpURLConnection.HTTP_OK)
            {
                InputStream inputStream = conn.getInputStream();

                BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                String s = "";

                //By this point in time, we will already have successfully opened the connection
                //
                while((s = buffer.readLine()) != null)
                {
                    json += s;
                }
                    return json;
                /*
                try
                {
                    JSONObject jsonObject = new JSONObject(json);

                    JSONArray jsonArray = jsonObject.getJSONArray("statuses");

                    for(int i = 0, length = jsonArray.length(); i < length; i++)
                    {
                        JSONObject attribute = jsonArray.getJSONObject(i);
                        String id = attribute.getString("text");
                        String title = attribute.getString("created_at");
                        Integer cameraimageResource = attribute.getInt("camaraImage");
                        Integer imageResource = attribute.getInt("Image");

                        JSONObject userAttribute = attribute.getJSONObject("user");
                       //String cameraimageResource = userAttribute.getString("profile_background_image_url");

                        Location newlocation = new Location(id, title, imageResource, cameraimageResource);
                        locationInfo.add(newlocation);
                    }
                }
                catch(Exception e)
                {

                }
*/

            }
        }
        catch(Exception e)
        {

        }
        return "";
    }
}

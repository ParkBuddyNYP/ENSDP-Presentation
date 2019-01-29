package com.example.a163363s.parkbuddy;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a163363s.parkbuddy.Adapters.LocationGridViewAdapter;
import com.example.a163363s.parkbuddy.Adapters.LocationListViewAdapter;
import com.example.a163363s.parkbuddy.DataManagers.LocationDataManager;
import com.example.a163363s.parkbuddy.DataModels.Location;
import com.example.a163363s.parkbuddy.Downloaders.DataDownloaders;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class CameraFragment extends Fragment {

    ProgressDialog progressDialog;
    ArrayList<Location> locationInfo = new ArrayList<Location>();

    public interface LocationSelectedListener
    {
        void onLocationSelected(Location product);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)

    {
        View view = inflater.inflate(R.layout.fragment_camera , container, false);

        LocationDataManager locationDataManager = new LocationDataManager();
        final Location[] locations = locationDataManager.getLocations();

        ListView listViewlocations = view.findViewById(R.id.listViewLocations);
        LocationListViewAdapter productAdapter = new LocationListViewAdapter(this.getContext(), locations);

        listViewlocations.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Location selectedLocation = locations[position];
                Activity parentActivity = getActivity();
                if (parentActivity instanceof LocationSelectedListener)
                {
                    ((LocationSelectedListener)parentActivity)
                            .onLocationSelected(selectedLocation);
                }
            }
        });

        listViewlocations.setAdapter(productAdapter);

        return view;

        //return inflater.inflate(R.layout.fragment_camera , container, false);
    }

    public class GetLocationInfoJsonTask extends AsyncTask<Void, String, Boolean>
    {
        @Override
        protected void onPreExecute()
        {
            progressDialog = ProgressDialog.show(getContext(), "Download", "Fetching twitter data...", true);
        }

        @Override
        protected Boolean doInBackground(Void... voids)
        {
            try
            {
                DataDownloaders downloaders = new DataDownloaders();
                String json = downloaders.startDownload("url");

                JSONObject jsonObject = new JSONObject(json);

                JSONArray jsonArray = jsonObject.getJSONArray("locationInfo");

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
            return true;
        }

        @Override
        protected void onProgressUpdate(String... values)
        {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean)
        {
            progressDialog.dismiss();

            if(locationInfo.size() > 0)
            {
                Location[] statusArray = new Location[0];
                //TwitterStatusAdapter adapter = new TwitterStatusAdapter(MainActivity.this, statusList.toArray(statusArray));

                //listViewData.setAdapter(adapter);
            }
            else
            {
                Toast.makeText(getActivity(), "Twitter data is currently unavailable.", Toast.LENGTH_LONG).show();
            }
        }
    }

}

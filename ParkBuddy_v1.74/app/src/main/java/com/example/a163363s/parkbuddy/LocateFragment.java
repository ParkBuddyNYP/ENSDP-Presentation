package com.example.a163363s.parkbuddy;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a163363s.parkbuddy.Adapters.LocationGridViewAdapter;
import com.example.a163363s.parkbuddy.DataManagers.LocationDataManager;
import com.example.a163363s.parkbuddy.DataModels.Location;
import com.example.a163363s.parkbuddy.Downloaders.DataDownloaders;
import com.example.a163363s.parkbuddy.Slider.IntromapActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class LocateFragment extends Fragment {

    public interface LocationSelectedListener
    {
        void onLocationSelected(Location product);
    }

    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    ProgressDialog progressDialog;
    ArrayList<Location> locationInfo = new ArrayList<Location>();


    //ArrayList<Location> statusList;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        {

            View view = inflater.inflate(R.layout.fragment_locate , container, false);

            LocationDataManager locationDataManager = new LocationDataManager();
            final Location[] products = locationDataManager.getLocations();

            GridView gridViewProducts = view.findViewById(R.id.gridViewLocations);
            LocationGridViewAdapter productAdapter = new LocationGridViewAdapter(this.getContext(), products);

            gridViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   //region GET BY ID
                    //Location selectedProduct = products[position];
                    //Activity parentActivity = getActivity();
                    //if (parentActivity instanceof CameraFragment.LocationSelectedListener)
                    //{
                        //((CameraFragment.LocationSelectedListener)parentActivity)
                                //.onLocationSelected(selectedProduct);
                    //}

                    //endregion

                    final Intent intent;
                    switch(position)
                    {
                        case 0:
                            intent =  new Intent(getActivity(), LocationInfoActivity.class);
                            break;

                        default:
                            intent =  new Intent(getActivity(), LocationInfoActivity.class);
                            break;
                    }
                    startActivity(intent);
                }
            });

            gridViewProducts.setAdapter(productAdapter);

            Button btnMap = view.findViewById(R.id.btnMap);
            btnMap.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isServicesOK()) {
                        Intent intent = new Intent(getContext(), IntromapActivity.class);
                        startActivity(intent);
                    }

                }
            });

            return view;

            //return inflater.inflate(R.layout.fragment_camera , container, false);
        }
    }


    public boolean isServicesOK(){
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getContext());

        if(available == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog((Activity)getContext(), available, ERROR_DIALOG_REQUEST);
            dialog.show();
        }else{
            Toast.makeText(getContext(), "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
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

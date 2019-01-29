package com.example.a163363s.parkbuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a163363s.parkbuddy.Adapters.ParkingDataAdapter;

import com.example.a163363s.parkbuddy.Bookmark.ActivityViewBookmark;
import com.example.a163363s.parkbuddy.DataModels.ParkingLotData;
import com.example.a163363s.parkbuddy.Httphandler.HttpHandler;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationInfoActivity extends AppCompatActivity {
    BarChart barChart;
    ImageView btnreset;
    ImageButton btnfav;
    ImageButton btnview;
    DataSet dataSet;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView.Adapter adapter;
    private ListView lv;

    private static String url = "https://parkbuddiesitp371.azurewebsites.net/api/Carparks";

    //ArrayList<HashMap<String, String>> Userlist;
    ArrayList<ParkingLotData> lotData = new ArrayList<ParkingLotData>();

    private String TAG = LocationInfoActivity.class.getSimpleName();

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_info);
        btnreset = (ImageView) findViewById(R.id.btnreset);
        barChart = (BarChart) findViewById(R.id.bargraph);
        btnfav = (ImageButton) findViewById(R.id.btnfav);
        btnview = (ImageButton) findViewById(R.id.btnview);


        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(ContextCompat.getColor(this, R.color.grey));
        colors.add(ContextCompat.getColor(this, R.color.lightpurple));
        colors.add(ContextCompat.getColor(this, R.color.grey));
        colors.add(ContextCompat.getColor(this, R.color.lightpurple));
        colors.add(ContextCompat.getColor(this, R.color.grey));
        colors.add(ContextCompat.getColor(this, R.color.lightpurple));
        colors.add(ContextCompat.getColor(this, R.color.grey));
        colors.add(ContextCompat.getColor(this, R.color.lightpurple));
        colors.add(ContextCompat.getColor(this, R.color.grey));


        //ArrayList<Integer> colors = new ArrayList<>();
        //colors.add(Color.rgb(156,254,230));
        //colors.add(Color.rgb(159,185,235));
        //colors.add(Color.rgb(143,231,161));
        //colors.add(Color.rgb(160,239,136));
        //colors.add(Color.rgb(200,246,139));
        //colors.add(Color.rgb(176,219,233));
        //colors.add(Color.rgb(183,176,253));


        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(44, 0));
        barEntries.add(new BarEntry(88f, 1));
        barEntries.add(new BarEntry(50f, 2));
        barEntries.add(new BarEntry(24f, 3));
        barEntries.add(new BarEntry(64f, 4));
        barEntries.add(new BarEntry(66f, 5));
        barEntries.add(new BarEntry(66f, 6));
        barEntries.add(new BarEntry(64f, 7));
        barEntries.add(new BarEntry(66f, 8));


        BarDataSet barDataSet = new BarDataSet(barEntries, "Lot Availability");

        ArrayList<String> theDates = new ArrayList<>();
        theDates.add("1am-4am");
        theDates.add("4am-8am");
        theDates.add("8am-12pm");
        theDates.add("12pm-3pm");
        theDates.add("3pm-7pm");
        theDates.add("7pm-10pm");
        theDates.add("10pm-12am");
        theDates.add("12am-2am");
        theDates.add("2am-4am");


        BarData theData = new BarData(theDates, barDataSet);
        barChart.setData(theData);
        barDataSet.setColors(colors);
        barChart.animateY(2000);
        barChart.setTouchEnabled(true);
        barChart.setDragEnabled(true);
        barChart.setScaleEnabled(true);
        barChart.setPinchZoom(true);
        barChart.setDrawHighlightArrow(true);

        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                barChart.fitScreen();
            }
        });

        ImageView imageView= (ImageView) findViewById(R.id.item_image);
        lv = (ListView) findViewById(R.id.listViewParkingInfo);


        new GetLotData().execute();

        btnfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LocationInfoActivity.this, ActivityViewBookmark.class);

                startActivity(in);
            }
        });
        btnview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LocationInfoActivity.this, CarparkLayoutActivity.class);

                startActivity(in);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_card_demo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetLotData extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(LocationInfoActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    //JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray lot = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < lot.length(); i++) {
                        //JSONObject attribute = users.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject attribute = lot.getJSONObject(i);


                        String carparkID = attribute.getString("carparkID");
                        String carparkName = attribute.getString("carparkName");
                        String carparkLat = attribute.getString("carparkLat");
                        String carparkLng = attribute.getString("carparkLng");
                        String totalNumberOfLotsAvailable = attribute.getString("numberOfLotsAvailable");
                        String carparkStatus = attribute.getString("carparkStatus");
                        String totalNumberOfLots = attribute.getString("totalNumberOfLots");
                        String companyName = attribute.getString("companyName");
                        String company = attribute.getString("company");
                        String carparkLots = attribute.getString("carparkLots");
                        String users = attribute.getString("users");
                        //String carParkGraphData = attribute.getString("carParkGraphData");

                        // Phone node is JSON Object
                        //JSONObject phone = attribute.getJSONObject("phone");
                        //String mobile = phone.getString("mobile");
                        //String home = phone.getString("home");
                        // String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> attr = new HashMap<>();

                        // adding each child node to HashMap key => value
                        attr.put("numberOfLotsAvailable", totalNumberOfLotsAvailable);
                        attr.put("totalNumberOfLots", totalNumberOfLots);
                        attr.put("carparkName", carparkName);

                        // adding contact to contact list
                        ParkingLotData parking = new ParkingLotData(carparkID, carparkName, carparkLat, carparkLng,  totalNumberOfLotsAvailable, carparkStatus, totalNumberOfLots,carparkStatus, companyName, company, carparkLots);
                        lotData.add(parking);
                    }
                }
                catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            ParkingLotData[] statusArray = new ParkingLotData[0];

            //ParkingDataAdapter adapter = new ParkingDataAdapter();
           ParkingDataAdapter adapter = new ParkingDataAdapter(LocationInfoActivity.this, lotData.toArray(statusArray));
            //recyclerView.setAdapter(adapter);

            lv.setAdapter(adapter);
        }

    }



}

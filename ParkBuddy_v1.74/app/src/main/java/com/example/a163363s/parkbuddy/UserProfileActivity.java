package com.example.a163363s.parkbuddy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import com.example.a163363s.parkbuddy.Adapters.UserProfileAdapter;
import com.example.a163363s.parkbuddy.DataModels.UserData;
import com.example.a163363s.parkbuddy.Httphandler.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class UserProfileActivity extends AppCompatActivity {

    private String TAG = UserProfileActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private ListView lv;
    private RecyclerView sv;

    // URL to get contacts JSON
    private static String url = "https://parkbuddiesitp371.azurewebsites.net/api/users";


    //ArrayList<HashMap<String, String>> Userlist;
    ArrayList<UserData> userlist = new ArrayList<UserData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);


       // lv = (ListView) findViewById(R.id.listViewProfile);

        //new GetContacts().execute();

        Button btnqr = findViewById(R.id.btnqr);
        btnqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(UserProfileActivity.this, QRActivity.class);
                startActivity(in);
            }
        });

    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(UserProfileActivity.this);
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
                    JSONArray users = new JSONArray(jsonStr);

                    // looping through All Contacts
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject attribute = users.getJSONObject(i);
                        HashMap<String, String> map = new HashMap<String, String>();
                        //JSONObject attribute = users.getJSONObject(i);


                        String userID = attribute.getString("userID");
                        String userName = attribute.getString("userName");
                        String name = attribute.getString("name");
                        String passwordHash = attribute.getString("passwordHash");
                        String passwordSalt = attribute.getString("passwordSalt");
                        String userProfileImage = attribute.getString("userProfileImage");
                        String userRole = attribute.getString("userRole");
                        String userToken = attribute.getString("userToken");
                        String userEmail = attribute.getString("userEmail");
                        String carparkName = attribute.getString("carparkName");
                        String carpark = attribute.getString("carpark");
                        String userReports = attribute.getString("userReports");

                        String adminRFID = attribute.getString("adminRFID");
                        String companyName = attribute.getString("companyName");
                        String companies = attribute.getString("companies");

                        // Phone node is JSON Object
                        //JSONObject phone = attribute.getJSONObject("phone");
                        //String mobile = phone.getString("mobile");
                        //String home = phone.getString("home");
                        // String office = phone.getString("office");

                        // tmp hash map for single contact
                        HashMap<String, String> attr = new HashMap<>();

                        // adding each child node to HashMap key => value
                        attr.put("userName", userName);
                        attr.put("name", name);
                        attr.put("userEmail", userEmail);


                        // adding contact to contact list
                        UserData user = new UserData(userID,userName,name,passwordHash,passwordSalt,userProfileImage,userRole,userToken,userEmail,carparkName,carpark, userReports,adminRFID,companyName,companies);
                        userlist.add(user);
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

            UserData[] statusArray = new UserData[0];
            UserProfileAdapter adapter = new UserProfileAdapter(UserProfileActivity.this, userlist.toArray(statusArray));

            lv.setAdapter(adapter);
        }

    }
}

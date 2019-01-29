package com.example.a163363s.parkbuddy;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.a163363s.parkbuddy.Adapters.CustomInfoWindowAdapter;
import com.example.a163363s.parkbuddy.Adapters.PlaceAutocompleteAdapter;
import com.example.a163363s.parkbuddy.App.AppController;
import com.example.a163363s.parkbuddy.DataModels.PlaceInfo;
import com.example.a163363s.parkbuddy.Helper.ApiInterface;
import com.example.a163363s.parkbuddy.Helper.DataModel;
import com.example.a163363s.parkbuddy.Helper.GPSTracker;
import com.example.a163363s.parkbuddy.utils.HttpConnection;
import com.example.a163363s.parkbuddy.utils.PathJsonParser;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback,
        GoogleApiClient.OnConnectionFailedListener {
    private Location mLocation;
    private ArrayList<Polyline> polyLineList;
    DataModel dataModel;
    ProgressDialog pDialog;

    private GoogleMap mMap;
    ApiInterface apiService;
    LatLng latLng;
    private static final String TAG = "MapActivity";

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final int PLACE_PICKER_REQUEST = 1;
    private static final int PLACE_INFO_REQUEST = 1;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds (
            new LatLng ( -40, -168 ), new LatLng ( 71, 136 ) );
    //widgets
    private AutoCompleteTextView mSearchText;
    private ImageView mGps, mInfo, mPlacePicker, mParking, mPark;
    //vars
    private Boolean mLocationPermissionsGranted = false;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private PlaceAutocompleteAdapter mPlaceAutocompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private PlaceInfo mPlace;
    private Marker mMarker;
    public static HashMap<Integer, String> route_details;
    Address obj;
    ArrayList<DataModel> modelArrayList = new ArrayList<> ( );
    ArrayList<ArrayList<DataModel>> dataModelslat = new ArrayList<ArrayList<DataModel>> ( );
    ;
    ArrayList<String> modelArraytlong = new ArrayList<String> ( );
    ;
    Call<DataModel> call;
    Double latitude, longitude;
    public double selected_distance;
    public double selected_distance_inMile;
    public String selected_track_duration;
    public String distance_route;
    private Button btn_view;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_map );
        mSearchText = findViewById ( R.id.input_search );
        mGps = findViewById ( R.id.ic_gps );
        mInfo = findViewById ( R.id.place_info );
        mPlacePicker = findViewById ( R.id.place_picker );
        mParking = findViewById ( R.id.place_parking );
        mPark = findViewById(R.id.ic_park);
        pDialog = new ProgressDialog ( getApplicationContext ( ) );
        pDialog.setMessage ( "please wait" );
        currentLocation ( );
        getLocationPermission ( );
        dtaresonse ( );


    }


    private void dtaresonse() {
        Toast.makeText ( MapActivity.this, "test", Toast.LENGTH_SHORT ).show ( );

        String URL_MAIN = "https://parkbuddiesitp371.azurewebsites.net/api/Carparks";
        pDialog.isShowing ();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( Request.Method.GET, URL_MAIN, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            for(int i=0;i<response.length();i++){
                            //    dataModel.setArrayList ( response );
                                // Get current json object
                                DataModel dataModel = new DataModel ();
                                JSONObject student = response.getJSONObject(i);
                                String carparkID = student.getString("carparkID");
                                String carparkName = student.getString("carparkName");
                                String carparkLat = student.getString("carparkLat");
                                String carparkLng = student.getString("carparkLng");
                                String numberOfLotsAvailable = student.getString("numberOfLotsAvailable");
                                String carparkStatus = student.getString("carparkStatus");
                                String totalNumberOfLots = student.getString("totalNumberOfLots");
                                String companyName = student.getString("companyName");
                                String company = student.getString("company");
                                String carparkLots = student.getString("carparkLots");
                                String users = student.getString("users");

                                if(!carparkLat.equals ( "" ) || !carparkLng.equals ( "" )) {
                                    if(carparkID.equals ( "" )){ carparkID="no value";}
                                    if(carparkName.equals ( "" )){ carparkName="no value";}
                                    if(numberOfLotsAvailable.equals ( "" )){ numberOfLotsAvailable="no value";}
                                    if(carparkStatus.equals ( "" )){ carparkStatus="no value";}
                                    latLng = new LatLng ( Double.parseDouble ( carparkLat ), Double.parseDouble ( carparkLng ) );
//                                    mMap.addMarker ( new MarkerOptions ( ).position ( latLng ).title ( "Location!" )
//                                                             .icon ( BitmapDescriptorFactory.fromResource ( R.drawable.parking ) ) );

                                    mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapActivity.this));
                                    try {
                                            String snippet = "Carpark Name: " + " " +carparkName + "\n" +
                                                    "Number Of Lots Available: " +" " + numberOfLotsAvailable + "\n" +
                                                    "Total Number Of Lots: " + " " + totalNumberOfLots + "\n" +
                                                    "Carpark Status: " + " " + carparkStatus + "\n";

                                            MarkerOptions options = new MarkerOptions ( )
                                                    .position ( latLng )
                                                    .icon ( BitmapDescriptorFactory.fromResource ( R.drawable.parking ))
                                                    .title ( String.valueOf ( student ) )
                                                    .snippet ( snippet );
                                            mMarker = mMap.addMarker ( options );
//                                        mMap.addMarker ( new MarkerOptions ( ).position ( latLng ).title ( "position!" )
//                                                             .icon ( BitmapDescriptorFactory.fromResource ( R.drawable.parking ) ) );

                                        } catch (Exception e) {
                                            Toast.makeText ( MapActivity.this, "some thing wrong", Toast.LENGTH_SHORT ).show ( );
                                        }
                                    }else {
                                    Toast.makeText ( MapActivity.this, "lat long have empty values", Toast.LENGTH_SHORT ).show ( );
                                }
                                dataModel.setCarparkID (carparkID  );
                                dataModel.setCarparkLat ( carparkLat );
                                dataModel.setCarparkLng ( carparkLng );
                                dataModel.setTotalNumberOfLots ( numberOfLotsAvailable );
                                dataModel.setCarparkName ( carparkName );
                                dataModel.setTotalNumberOfLots ( totalNumberOfLots );
                                dataModel.setCompanyName ( companyName );
                                dataModel.setCompany ( company );
                                dataModel.setCarparkStatus ( carparkStatus );
                                dataModel.setCarparkLots ( carparkLots );

                                Toast.makeText ( MapActivity.this, carparkID+"....", Toast.LENGTH_SHORT ).show ( );
                            }
                        }catch (JSONException e){
                            e.printStackTrace();

                            Toast.makeText ( MapActivity.this, ""+e, Toast.LENGTH_SHORT ).show ( );
                        }
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error){
                        Toast.makeText ( MapActivity.this, ""+error, Toast.LENGTH_SHORT ).show ( );

                    }
                }
        );jsonArrayRequest.setRetryPolicy(new RetryPolicy () {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) {

            }
        });


        // Add JsonArrayRequest to the RequestQueue
        AppController.getInstance ( ).addToRequestQueue ( jsonArrayRequest );
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        //  getlatLngData();
        Log.d(TAG, "onMapReady: map is ready");
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.getUiSettings ( ).isCompassEnabled ();
        mMap.getUiSettings ( ).setRotateGesturesEnabled (true);
        mMap.getUiSettings ( ).setMyLocationButtonEnabled (true);
        mMap.getUiSettings ( ).setZoomControlsEnabled (true);
        mMap.addMarker ( new MarkerOptions ( ).position ( latLng ).title ("Your Starting Location!" )
                                 .icon ( BitmapDescriptorFactory.fromResource (R.drawable.icon_map )));

        if (mLocationPermissionsGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                                                                                               Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            geoLocate();
            init();
        }
        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Log.i("POLYLINE", polyline.toString());
                for (Polyline pline : polyLineList) {
                    if (pline.getId().equals(polyline.getId())) {
                        pline.setWidth(20);
                        pline.setColor(Color.BLUE);
                    } else {
                        pline.setWidth(10);
                        pline.setColor(Color.DKGRAY);
                    }
                }
                String[] value = ((String) polyline.getTag()).split("--");
                String distance = value[0].replaceAll("\\D+\\.\\D+", "");
                selected_track_duration = value[1].split("___and__")[0];
                if (distance.contains("mi"))
                    distance = String.valueOf(Double.valueOf(distance.replace("mi", "")) * 1.609344);
                else if (distance.contains(("km"))){
                    distance = String.valueOf(Double.valueOf(distance.replace("km", "")));
                    String distanceParts[] = distance.split(" ");
                    double dbl = Double.parseDouble(distanceParts[0]);
                    selected_distance = dbl;
                    selected_distance_inMile = 0.621371* dbl;
                }
                else if (distance.contains("m"))
                    distance = String.valueOf(Double.valueOf(distance.replace("m", "")) / 1000);

                distance_route = "Distance : ".concat(distance).concat(" km");
                String toast = "Duration : ".concat(selected_track_duration);
                toast = toast.concat("\n");
                toast = toast.concat("Distance : ").concat(distance);
                Toast.makeText(MapActivity.this,toast,Toast.LENGTH_SHORT).show();
            }
        });
    }
    double myLat;
    double myLng;
    double otherLat;
    double otherLng;
    private void currentLocation() {
        GPSTracker mGPS = new GPSTracker(getApplicationContext ());
        mLocation = mGPS.getLocation();
        if(mLocation !=null) {
             String latitude = String.valueOf ( mLocation.getLatitude ( ) );
             String longitude = String.valueOf ( mLocation.getLongitude ( ) );
             myLat = mLocation.getLatitude();
             myLng = mLocation.getLongitude();
            MarkerOptions options = new MarkerOptions ( ).position ( new LatLng(myLat,myLng) ).title ("Your Starting Location!" )
                    .icon ( BitmapDescriptorFactory.fromResource (R.drawable.icon_map ));
            if(mMap!=null)
            mMap.addMarker(options);
            getAddress ( Double.parseDouble ( latitude ), Double.parseDouble ( longitude ) );
            latLng = new LatLng ( Double.parseDouble (  latitude),Double.parseDouble (  longitude) );

        }else {
            LocationListener mLocationListener = new LocationListener ( ) {
                @Override
                public void onLocationChanged(final Location location) {
                    latitude = location.getLatitude ( );
                    longitude = location.getLongitude ( );
                    getAddress(latitude,longitude);
                    latLng = new LatLng ( latitude,longitude );
                    myLat = latLng.latitude;
                    myLng = latLng.longitude;
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
                @Override
                public void onProviderEnabled(String provider) {
                }
                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            LocationManager mLocationManager = (LocationManager)getApplicationContext ().getSystemService ( LOCATION_SERVICE );

            if (ActivityCompat.checkSelfPermission ( getApplicationContext (), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission ( getApplicationContext (), android.Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mLocationManager.requestLocationUpdates ( LocationManager.GPS_PROVIDER, 1,
                                                      1, mLocationListener
            );
        }
    }
    public void getAddress(double lat, double lng) {
        Geocoder geocoder = new Geocoder( getApplicationContext (), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation( lat, lng, 1);
            obj = addresses.get(0);
            String add = obj.getAddressLine(0);
            add = add + "," + obj.getLatitude ();
            add = add + "," + obj.getLongitude ();
            Log.v("IGA", "Address" + add);
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Toast.makeText(getApplicationContext (), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void init(){
        Log.d(TAG, "init: initializing");
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        mSearchText.setOnItemClickListener(mAutocompleteClickListener);
        mPlaceAutocompleteAdapter = new PlaceAutocompleteAdapter(this, mGoogleApiClient,
                LAT_LNG_BOUNDS, null);
        mSearchText.setAdapter(mPlaceAutocompleteAdapter);
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){
                    //execute our method for searching
                    geoLocate();
                }
                return false;
            }
        });
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked gps icon");
                getDeviceLocation();
            }
        });
        mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked place info");
                try{
                    if(mMarker.isInfoWindowShown()){
                        mMarker.hideInfoWindow();
                    }else{
                        Log.d(TAG, "onClick: place info: " + mPlace.toString());
                        mMarker.showInfoWindow();
                    }
                }catch (NullPointerException e){
                    Log.e(TAG, "onClick: NullPointerException: " + e.getMessage() );
                }
            }
        });
        mPlacePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(MapActivity.this), PLACE_PICKER_REQUEST);
                }
                catch (GooglePlayServicesRepairableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesRepairableException: " + e.getMessage() );
                }
                catch (GooglePlayServicesNotAvailableException e) {
                    Log.e(TAG, "onClick: GooglePlayServicesNotAvailableException: " + e.getMessage() );
                }
            }
        });
        mParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: clicked place info");
                try{
                    if(mMarker.isInfoWindowShown()){
                        mMarker.hideInfoWindow();
                    }else{
                        Intent intent = new Intent(MapActivity.this, LocationInfoActivity.class);
                        startActivity(intent);
                    }
                }catch (NullPointerException e){
                    Log.e(TAG, "onClick: NullPointerException: " + e.getMessage() );
                }
            }
        });

        mPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MapActivity.this, CarparkLayoutActivity.class);
                startActivity(intent);
            }
        });

        hideSoftKeyboard();
     }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);

                PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient, place.getId());
                placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
            }
        }
    }
    private void geoLocate(){
        Log.d(TAG, "geoLocate: geolocating");

        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapActivity.this);
        List<Address> list = new ArrayList<>();
        try{
            list = geocoder.getFromLocationName(searchString, 1);
        }catch (IOException e){
            Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
        }
        if(list.size() > 0){
            Address address = list.get(0);

            Log.d(TAG, "geoLocate: found a location: " + address.toString());
            //Toast.makeText(this, address.toString(), Toast.LENGTH_SHORT).show();

            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM,
                    address.getAddressLine(0));
            latLng = new LatLng ( address.getLatitude(),  address.getLongitude());


        }
    }
    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionsGranted){
                final Task location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful() && task.getResult() != null){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM,
                                    "My Location");
                            latLng = new LatLng ( currentLocation.getLatitude(), currentLocation.getLongitude());

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(MapActivity.this, "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }




    private void moveCamera(LatLng latLng, float zoom, PlaceInfo placeInfo){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        mMap.clear();
     //   mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapActivity.this));

        if(placeInfo != null){
            try{
                String snippet = "Address: " + placeInfo.getAddress () + "\n" +
                        "Name: " + placeInfo.getName () + "\n" +
                        "Contact : " + placeInfo.getPhoneNumber () + "\n" +
                        "link: " + placeInfo.getWebsiteUri () + "\n";

                MarkerOptions options = new MarkerOptions()
                        .position(latLng)
                        .title(placeInfo.getName())
                        .snippet(snippet);
                mMarker = mMap.addMarker(options);
            }catch (NullPointerException e){
                Log.e(TAG, "moveCamera: NullPointerException: " + e.getMessage() );
            }
        }else{
            mMap.addMarker(new MarkerOptions().position(latLng));

        }

        hideSoftKeyboard();
    }

    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        if(!title.equals("My Location")){
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }

        hideSoftKeyboard();
    }

    private void initMap(){
        Log.d(TAG, "initMap: initializing map");
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);
    }

    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
                initMap();
            }else{
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }else{
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for(int i = 0; i < grantResults.length; i++){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            mLocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    mLocationPermissionsGranted = true;
                    //initialize our map
                    initMap();
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Change the map type based on the user's selection.
        switch (item.getItemId()) {
            case R.id.normal_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void hideSoftKeyboard(){
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    /*
        --------------------------- google places API autocomplete suggestions -----------------
     */

    private AdapterView.OnItemClickListener mAutocompleteClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            hideSoftKeyboard();

            final AutocompletePrediction item = mPlaceAutocompleteAdapter.getItem(i);
            final String placeId = item.getPlaceId();

            PendingResult<PlaceBuffer> placeResult = Places.GeoDataApi
                    .getPlaceById(mGoogleApiClient, placeId);
            placeResult.setResultCallback(mUpdatePlaceDetailsCallback);
        }
    };

    private ResultCallback<PlaceBuffer> mUpdatePlaceDetailsCallback = new ResultCallback<PlaceBuffer>() {
        @Override
        public void onResult(@NonNull PlaceBuffer places) {
            if(!places.getStatus().isSuccess()){
                Log.d(TAG, "onResult: Place query did not complete successfully: " + places.getStatus().toString());
                places.release();
                return;
            }
            final Place place = places.get(0);

            try{
                mPlace = new PlaceInfo();
                mPlace.setName(place.getName().toString());
                Log.d(TAG, "onResult: name: " + place.getName());
                mPlace.setAddress(place.getAddress().toString());
                Log.d(TAG, "onResult: address: " + place.getAddress());
//                mPlace.setAttributions(place.getAttributions().toString());
//                Log.d(TAG, "onResult: attributions: " + place.getAttributions());
                mPlace.setId(place.getId());
                Log.d(TAG, "onResult: id:" + place.getId());
                mPlace.setLatlng(place.getLatLng());
                otherLat = mPlace.getLatlng().latitude;
                otherLng = mPlace.getLatlng().longitude;
                Log.d(TAG, "onResult: latlng: " + place.getLatLng());
                mPlace.setRating(place.getRating());
                Log.d(TAG, "onResult: rating: " + place.getRating());
                mPlace.setPhoneNumber(place.getPhoneNumber().toString());
                Log.d(TAG, "onResult: phone number: " + place.getPhoneNumber());
                mPlace.setWebsiteUri(place.getWebsiteUri());
                Log.d(TAG, "onResult: website uri: " + place.getWebsiteUri());

                Log.d(TAG, "onResult: place: " + mPlace.toString());
            }catch (NullPointerException e){
                Log.e(TAG, "onResult: NullPointerException: " + e.getMessage() );
            }

            moveCamera(new LatLng(place.getViewport().getCenter().latitude,
                    place.getViewport().getCenter().longitude), DEFAULT_ZOOM, mPlace);

            places.release();
            //
            MarkerOptions options = new MarkerOptions ( ).position ( new LatLng(myLat,myLng) ).title ("Your Starting Location!" )
                    .icon ( BitmapDescriptorFactory.fromResource (R.drawable.icon_map ));
            if(mMap!=null)
                mMap.addMarker(options);
            getMapsApiDirectionsUrl(myLat,myLng,otherLat,otherLng);
        }
    };
    public String getMapsApiDirectionsUrl(Double pickup_lat,Double pickup_long,Double dropoff_lat,Double dropoff_long) {
        String addresses = "optimize:true&origin="
                + pickup_lat.toString().concat(",") + pickup_long
                + "&destination=" + dropoff_lat + ","
                + dropoff_long;
        String sensor = "sensor=false";
        String params = addresses + "&" + sensor+"&alternatives=true";
        String output = "json";
        //&alternatives=true&key=
        String url = "https://maps.googleapis.com/maps/api/directions/"
                + output + "?" + params + "&key=" + getString(R.string.google_maps_api);
        ReadTask downloadTask = new ReadTask();
        downloadTask.execute(url);
        return url;
    }
//add polyline
private class ReadTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... url) {
        String data = "";
        try {
            HttpConnection http = new HttpConnection();
            data = http.readUrl(url[0]);
        } catch (Exception e) {
            Log.d("Background Task", e.toString());
        }
        return data;
    }
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        new ParserTask().execute(result);
    }
}
Polyline polyline;
    private class ParserTask extends
            AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(
                String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                route_details = new HashMap<>();
                PathJsonParser parser = new PathJsonParser();
                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> routes) {
            if (routes == null)
                return;
            ArrayList<LatLng> points = null;
            PolylineOptions polyLineOptions = null;

            // traversing through routes
            for (int i = 0; i < routes.size(); i++) {
                points = new ArrayList<LatLng>();
                polyLineOptions = new PolylineOptions();
                List<HashMap<String, String>> path = routes.get(i);
                String distance = route_details.get(i + 1).split("--")[0];
                //String duration = route_details.get(i + 1).split("--")[1];
                String temp_duration = route_details.get(i + 1).split("--")[1];
                String duration = temp_duration.split("___and__")[0];
                distance = distance.replaceAll("\\D+\\.\\D+", "").replace(",","");
                if (distance.contains("mi"))
                    distance = String.valueOf(Double.valueOf(distance.replace("mi", "")) * 1.609344);
                else if (distance.contains(("km")))
                {
                    distance = String.valueOf(Double.valueOf(distance.replace("km", "")));
                    if (i == 0) {
                        String distanceParts[] = distance.split(" ");
                        double dbl = Double.parseDouble(distanceParts[0]);
                        selected_distance = dbl;
                        selected_distance_inMile = 0.621371* dbl;
                        selected_track_duration = route_details.get(i + 1).split("--")[1].split("___and__")[0];
                    }
                }
                else if (distance.contains("m"))
                    distance = String.valueOf(Double.valueOf(distance.replace("m", "")) / 1000);

                for (int j = 0; j < path.size(); j++) {
                    HashMap<String, String> point = path.get(j);

                    double lat = Double.parseDouble(point.get("lat"));
                    double lng = Double.parseDouble(point.get("lng"));

                    LatLng position = new LatLng(lat, lng);

                    points.add(position);
                }

                polyLineOptions.addAll(points);
                if (i == 0) {
                    polyLineOptions.width(20);
                    polyLineOptions.color(Color.BLUE);
                } else {
                    polyLineOptions.width(10);
                    polyLineOptions.color(Color.DKGRAY);
                }
                polyLineOptions.clickable(true);
                if (polyLineList == null)
                    polyLineList = new ArrayList<Polyline>();
                polyline = mMap.addPolyline(polyLineOptions);
                polyline.setTag(route_details.get(i + 1));
                if (i == 0)
                {
                }

                polyLineList.add(polyline);
            }
        }
    }
}




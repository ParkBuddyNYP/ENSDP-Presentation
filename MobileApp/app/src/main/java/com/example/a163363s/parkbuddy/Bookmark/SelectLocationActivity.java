package com.example.a163363s.parkbuddy.Bookmark;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a163363s.parkbuddy.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;

public class SelectLocationActivity extends AppCompatActivity {

    private GoogleMap map;
    private Button btnConfirm;
    private LatLng mPosition;
    private BookmarkDB db;
    private FusedLocationProviderClient locationProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_location);
        SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mMap);
        btnConfirm = findViewById(R.id.btn_bookmark_this_location);
        mPosition = null;
        btnConfirm.setVisibility(View.INVISIBLE);
        db = new BookmarkDB(this, "Bookmark_DB");
        locationProvider = LocationServices.getFusedLocationProviderClient(SelectLocationActivity.this);

        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                permissionCheck();
                map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                    @Override
                    public void onMarkerDragStart(Marker marker) {

                    }

                    @Override
                    public void onMarkerDrag(Marker marker) {
                        map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)));
                    }

                    @Override
                    public void onMarkerDragEnd(Marker marker) {
                        mPosition = marker.getPosition();
                        btnConfirm.setVisibility(View.VISIBLE);
                        map.animateCamera(CameraUpdateFactory.newLatLng(new LatLng(marker.getPosition().latitude, marker.getPosition().longitude)));
                    }
                });
            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    View view = getLayoutInflater().inflate(R.layout.layout_location_info, null, false);
                    Button btnSave, btnCancel;
                    final EditText edtLocationName, edtLocationDescription, edtLatitude, edtLongitude;
                    edtLocationName = view.findViewById(R.id.edt_location_title);
                    edtLocationDescription = view.findViewById(R.id.edt_location_description);
                    edtLatitude = view.findViewById(R.id.edt_location_latitude);
                    edtLongitude = view.findViewById(R.id.edt_location_longitude);
                    btnSave = view.findViewById(R.id.btn_save_location);
                    btnCancel = view.findViewById(R.id.btn_cancel);

                    Geocoder geocoder = new Geocoder(SelectLocationActivity.this);
                    List<Address> addresses = geocoder.getFromLocation(mPosition.latitude, mPosition.longitude, 1);
                    edtLocationName.setText(addresses.get(0).getThoroughfare());
                    edtLatitude.setText("Lat: " + String.valueOf(addresses.get(0).getLatitude()));
                    edtLongitude.setText("Lng: " + String.valueOf(addresses.get(0).getLongitude()));

                    AlertDialog.Builder locationInfoDialog = new AlertDialog.Builder(SelectLocationActivity.this);
                    locationInfoDialog.setCancelable(false);
                    locationInfoDialog.setView(view);
                    final AlertDialog dialog = locationInfoDialog.create();
                    dialog.show();

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!edtLocationName.getText().toString().equals("") && !edtLocationDescription.getText().toString().equals("")) {
                                long result = db.addBookmark(edtLocationName.getText().toString(), edtLocationDescription.getText().toString(), mPosition.latitude, mPosition.longitude);
                                if (result == -1) {
                                    Toast.makeText(SelectLocationActivity.this, "Insertion error", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                } else {
                                    AlertDialog.Builder mDialog = new AlertDialog.Builder(SelectLocationActivity.this);
                                    mDialog.setTitle("Success!");
                                    mDialog.setMessage("Bookmark saved successfully");
                                    mDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dl, int which) {
                                            dl.cancel();
                                        }
                                    });
                                    mDialog.show();
                                    dialog.cancel();
                                }
                            } else {
                                Toast.makeText(SelectLocationActivity.this, "Fill all fields first", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.cancel();
                        }
                    });
                } catch (Exception exc) {

                }
            }
        });
    }

    private void permissionCheck() {
        if (ContextCompat.checkSelfPermission(SelectLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(SelectLocationActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(SelectLocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 69);
        } else {
            setMarkerToCurrentLocation();
        }
    }

    private void setMarkerToCurrentLocation() {
        Task task = locationProvider.getLastLocation();
        task.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Location location = (Location) task.getResult();
                    if(location!=null)
                    {
                        MarkerOptions marker = new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude()));
                        marker.draggable(true);
                        map.addMarker(marker);
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 10f));
                        Toast.makeText(SelectLocationActivity.this, "Tap and hold the marker to drag it and drop it over your desired location", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(SelectLocationActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SelectLocationActivity.this, ActivityViewBookmark.class));
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 69 && grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(SelectLocationActivity.this, "Permission are required to access device's location", Toast.LENGTH_LONG).show();
                    return;
                }
            }
            setMarkerToCurrentLocation();
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
                map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                return true;
            case R.id.hybrid_map:
                map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                return true;
            case R.id.satellite_map:
                map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                return true;
            case R.id.terrain_map:
                map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

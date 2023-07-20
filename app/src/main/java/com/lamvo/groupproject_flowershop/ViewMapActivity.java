package com.lamvo.groupproject_flowershop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationRequest;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ViewMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private GoogleMap ggMap;
    private Marker userMarker;
    private MapView mapView;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationCallback locationCallback;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_map);

        mapView = findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    Location location = locationResult.getLastLocation();
                    if (location != null) {
                        updateCurrentLocationMarker(location);
                    }
                }
            }
        };
        MapsInitializer.initialize(getApplicationContext());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menu_map);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menu_home) {
                startActivity(new Intent(ViewMapActivity.this, FlowersList.class));
            }
            if (item.getItemId() == R.id.menu_order) {
                startActivity(new Intent(ViewMapActivity.this, IndividualOrderActivity.class));
            }
            if (item.getItemId() == R.id.menu_map) {
                startActivity(new Intent(ViewMapActivity.this, ViewMapActivity.class));
            }
            return true;
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        ggMap = googleMap;
        ggMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        ggMap.setOnMapClickListener(this);

        List<LatLng> markerLocations = new ArrayList<>();
        markerLocations.add(new LatLng(10.875169809132379, 106.80090390746861));
        markerLocations.add(new LatLng(10.80139303764417, 106.61866451134182));
        markerLocations.add(new LatLng(10.835592562884592, 106.65875205907219));
        markerLocations.add(new LatLng(10.740697737263517, 106.70222782340555));
        markerLocations.add(new LatLng(10.772025667031546, 106.72718620239048));

        int i = 1;
        for (LatLng location : markerLocations) {
            String title = "Chi nhánh " + i;
            i++;
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title(title)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.flower_map_marker));
            ggMap.addMarker(markerOptions);
        }

        LatLng initialCameraPosition = markerLocations.get(0); // Set the camera position to the first marker location
        ggMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialCameraPosition, 10));

        // Check for location permission
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        } else {
            // Request location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    private void getCurrentLocation() {
//        // Check if the device is running on Android 12 (API level 31) or higher
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            LocationRequest locationRequest = new LocationRequest.Builder(LocationRequest.QUALITY_HIGH_ACCURACY)
//                    .setMinUpdateIntervalMillis(500)
//                    .setMaxUpdateDelayMillis(1000)
//                    .build();
//
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
//                    == PackageManager.PERMISSION_GRANTED) {
//                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, ContextCompat.getMainExecutor(this));
//            }
//        }

    }


    private void updateCurrentLocationMarker(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (userMarker == null) {
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(latLng)
                    .title("Current Location");
            userMarker = ggMap.addMarker(markerOptions);
        } else {
            userMarker.setPosition(latLng);
        }
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sub_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_cart) {
            // start view cat activity
            startActivity(new Intent(ViewMapActivity.this, ViewCartActivity.class));
        } else if (item.getItemId() == R.id.menu_logout) {
            // process for logout feature
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(ViewMapActivity.this, SignInActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
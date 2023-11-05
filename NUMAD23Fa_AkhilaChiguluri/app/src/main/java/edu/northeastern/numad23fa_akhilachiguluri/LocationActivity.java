package edu.northeastern.numad23fa_akhilachiguluri;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LocationActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_LOCATION_PERMISSION = 100;
    private static final String TOTAL_DISTANCE_KEY = "total_distance";

    private TextView latitude;
    private TextView longitude;
    private TextView distance;
    private Button reset_button;
    private double total_distance = 0.0;

    private FusedLocationProviderClient fusedLocationClient;
    private Location latestLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latitude = findViewById(R.id.latitude);
        longitude = findViewById(R.id.longitude);
        distance = findViewById(R.id.total_distance);
        reset_button = findViewById(R.id.reset);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);



        validateLocationPermission();
        reset_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                total_distance = 0.0;
                updateTotalDistanceDisplay();
            }
        });

        if (savedInstanceState != null) {
            total_distance = savedInstanceState.getDouble(TOTAL_DISTANCE_KEY);
            updateTotalDistanceDisplay();
        }
    }

    private void validateLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_CODE_LOCATION_PERMISSION);
        } else {
            initialCoordinates();
        }
    }

    private void initialCoordinates() {
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000)
                .setFastestInterval(10000);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext()).checkLocationSettings(builder.build());
        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                } catch (ApiException e) {
                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(LocationActivity.this, 1001);
                            } catch (IntentSender.SendIntentException ex) {

                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                            break;
                    }
                }
            }
        });

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            switch (resultCode) {
                case Activity.RESULT_OK:

                    break;
                case Activity.RESULT_CANCELED:
                    Toast.makeText(this, "Gps is necessary for the operation", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void updateTotalDistanceDisplay() {
        distance.setText(String.format("%.2f meters", total_distance));
    }

    private void updateCoordinates(Location location) {
        if (location != null) {
            latestLocation = location;
            latitude.setText("Latitude: "+String.format("%.6f", location.getLatitude()));
            longitude.setText("Longitude: "+String.format("%.6f", location.getLongitude()));
        }
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }

            for (Location location : locationResult.getLocations()) {
                if (latestLocation != null) {
                    float change_in_distance = latestLocation.distanceTo(location);
                    if (change_in_distance > 1.0) {
                        total_distance += change_in_distance;
                        updateTotalDistanceDisplay();
                    }
                }
                updateCoordinates(location);
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int code_requested, @NonNull String[] grant_permissions, @NonNull int[] result_permissions) {
        super.onRequestPermissionsResult(code_requested, grant_permissions, result_permissions);
        if (code_requested == REQUEST_CODE_LOCATION_PERMISSION) {
            if (result_permissions.length > 0 && result_permissions[0] == PackageManager.PERMISSION_GRANTED) {
                initialCoordinates();
            } else {
                askPermissionWhenDenied();
            }
        }
    }

    private void askPermissionWhenDenied() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Please grant access to your location before proceeding.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(TOTAL_DISTANCE_KEY, total_distance);
    }

    @Override
    public void onBackPressed() {
        askBeforeExit();
    }

    private void askBeforeExit() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your progress will be lost.Please confirm if you want to exit.")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();

    }
}
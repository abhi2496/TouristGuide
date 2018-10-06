package com.example.abhishekkoranne.guide;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;


public class AttractionsFragment extends Fragment implements LocationListener {

    private RecyclerView rv;
    private PlacesAdapter adapter;
    private ArrayList<Places> placesList = new ArrayList<>();
    double lat, lon;
    public Criteria criteria;
    public String bestProvider;

    LocationManager locationManager;
    Location location, locTajMahal, locZoo, locMuseum, locPictureGallery, locAkshardham, locKumbalgadh;

    public AttractionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attractions, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        placesList.clear();
        adapter = new PlacesAdapter(getContext(), placesList);

        rv = (RecyclerView) getActivity().findViewById(R.id.recyclerViewAttractions);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        getLocation();

        locTajMahal = new Location("");
        locZoo = new Location("");
        locAkshardham = new Location("");
        locMuseum = new Location("");
        locPictureGallery = new Location("");
        locKumbalgadh = new Location("");

        locTajMahal.setLatitude(27.1750);
        locTajMahal.setLongitude(78.0422);
        locZoo.setLatitude(22.31337863);
        locZoo.setLongitude(73.19018833);
        locMuseum.setLatitude(22.31121486);
        locMuseum.setLongitude(73.18999522);
        locPictureGallery.setLatitude(22.31206846);
        locPictureGallery.setLongitude(73.18875067);
        locAkshardham.setLatitude(20.8880);
        locAkshardham.setLongitude(70.4012);
        locKumbalgadh.setLatitude(25.1528);
        locKumbalgadh.setLongitude(73.5870);

/*
        if (location == null) {

            gpsSettings();
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true));

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                gpsSettings();
                return;
            }
            long minTime = 1000;
            float distance = 0;
            locationManager.requestLocationUpdates(bestProvider, minTime, distance, this);
            Log.d("map", "location Latitude: " + lat);

        }
*/
        float[][] distance = new float[6][1];
        Location.distanceBetween(lat, lon, locTajMahal.getLatitude(), locTajMahal.getLongitude(), distance[0]);
        Location.distanceBetween(lat, lon, locZoo.getLatitude(), locZoo.getLongitude(), distance[1]);
        Location.distanceBetween(lat, lon, locMuseum.getLatitude(), locMuseum.getLongitude(), distance[2]);
        Location.distanceBetween(lat, lon, locPictureGallery.getLatitude(), locPictureGallery.getLongitude(), distance[3]);
        Location.distanceBetween(lat, lon, locAkshardham.getLatitude(), locAkshardham.getLongitude(), distance[4]);
        Location.distanceBetween(lat, lon, locKumbalgadh.getLatitude(), locKumbalgadh.getLongitude(), distance[5]);

        placesList.add(new Places(getString(R.string.taj_mahal_name), getString(R.string.taj_mahal_description), R.drawable.taj_mahal, Integer.toString((int) distance[0][0] / 1000)));
        placesList.add(new Places(getString(R.string.zoo_name), getString(R.string.zoo_description), R.drawable.animal_zoo, Integer.toString((int) distance[1][0] / 1000)));
        placesList.add(new Places(getString(R.string.museum_name), getString(R.string.museum_description), R.drawable.museum, Integer.toString((int) distance[2][0] / 1000)));
        placesList.add(new Places(getString(R.string.gallery_name), getString(R.string.gallery_description), R.drawable.picture_gallery, Integer.toString((int) distance[3][0] / 1000)));
        placesList.add(new Places(getString(R.string.akshardham_name), getString(R.string.sample_description), R.drawable.akshardham, Integer.toString((int) distance[4][0] / 1000)));
        placesList.add(new Places(getString(R.string.kumbalgadh_name), getString(R.string.kumbalgadh_description), R.drawable.kumbalgadh, Integer.toString((int) distance[5][0] / 1000)));
    }

    public boolean isLocationEnabled(Context context) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            gpsSettings();
            return true;
        }
        return true;
    }

    private void getLocation() {
        if (isLocationEnabled(getContext())) {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            //You can still do this if you like, you might get lucky:
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            long minTime = 1000;
            float distance = 0;
            locationManager.requestLocationUpdates(bestProvider, minTime, distance, this);
            Location location = locationManager.getLastKnownLocation(bestProvider);

            if (location != null) {
                Log.e("TAG", "GPS is on");
                lat = location.getLatitude();
                lon = location.getLongitude();

            } else {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    gpsSettings();
                    return;
                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, this);
            }
        }
    }

    public void gpsSettings() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());

        // Setting Dialog Title
        alertDialog.setTitle(getString(R.string.gps_settings));

        // Setting Dialog Message
        alertDialog
                .setMessage(getString(R.string.gps_not_enabled));

        // On pressing Settings button
        alertDialog.setPositiveButton(getString(R.string.settings),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        getContext().startActivity(intent);
                    }
                });

        // on pressing cancel button
        alertDialog.setNegativeButton(getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        // Showing Alert Message
        alertDialog.show();
        return;
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        lon = location.getLongitude();
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
}

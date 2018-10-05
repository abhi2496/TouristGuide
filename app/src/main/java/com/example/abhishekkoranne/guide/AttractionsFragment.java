package com.example.abhishekkoranne.guide;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class AttractionsFragment extends Fragment {

    private RecyclerView rv;
    private PlacesAdapter adapter;
    private ArrayList<Places> placesList = new ArrayList<>();
    double lat, lon;

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

        placesList.add(new Places(getString(R.string.taj_mahal_name), getString(R.string.taj_mahal_description), R.drawable.taj_mahal, Integer.toString((int) location.distanceTo(locTajMahal) / 1000)));
        placesList.add(new Places(getString(R.string.zoo_name), getString(R.string.zoo_description), R.drawable.animal_zoo, Integer.toString((int) location.distanceTo(locZoo) / 1000)));
        placesList.add(new Places(getString(R.string.museum_name), getString(R.string.museum_description), R.drawable.museum, Integer.toString((int) location.distanceTo(locMuseum) / 1000)));
        placesList.add(new Places(getString(R.string.gallery_name), getString(R.string.gallery_description), R.drawable.picture_gallery, Integer.toString((int) location.distanceTo(locPictureGallery) / 1000)));
        placesList.add(new Places(getString(R.string.akshardham_name), getString(R.string.sample_description), R.drawable.akshardham, Integer.toString((int) location.distanceTo(locAkshardham) / 1000)));
        placesList.add(new Places(getString(R.string.kumbalgadh_name), getString(R.string.kumbalgadh_description), R.drawable.kumbalgadh, Integer.toString((int) location.distanceTo(locKumbalgadh) / 1000)));

    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

            //Location settings check
            //Source: https://stackoverflow.com/questions/17591147/how-to-get-current-location-in-android
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

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
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            locAkshardham = new Location(location);
            locKumbalgadh = new Location(location);
            locMuseum = new Location(location);
            locPictureGallery = new Location(location);
            locTajMahal = new Location(location);
            locZoo = new Location(location);

            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

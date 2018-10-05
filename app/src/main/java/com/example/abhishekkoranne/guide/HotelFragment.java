package com.example.abhishekkoranne.guide;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
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

public class HotelFragment extends Fragment {

    private RecyclerView rv;
    private PlacesAdapter adapter;
    private ArrayList<Places> placesList = new ArrayList<>();
    double lat, lon;

    LocationManager locationManager;
    Location location, locHotel;


    public HotelFragment() {
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
        return inflater.inflate(R.layout.fragment_hotel, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        placesList.clear();
        adapter = new PlacesAdapter(getContext(), placesList);

        rv = (RecyclerView) getActivity().findViewById(R.id.recyclerViewHotels);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        getLocation();

        locHotel.setLatitude(22.31027684);
        locHotel.setLongitude(73.16915987);

        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int)location.distanceTo(locHotel)/1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int)location.distanceTo(locHotel)/1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int)location.distanceTo(locHotel)/1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int)location.distanceTo(locHotel)/1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int)location.distanceTo(locHotel)/1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int)location.distanceTo(locHotel)/1000)));

    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);

            //Location settings check
            //Source: https://stackoverflow.com/questions/17591147/how-to-get-current-location-in-android
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

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
            locHotel=new Location(location);
            if (location != null) {
                lat = location.getLatitude();
                lon = location.getLongitude();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
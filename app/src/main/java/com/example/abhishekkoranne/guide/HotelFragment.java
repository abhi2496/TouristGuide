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

public class HotelFragment extends Fragment implements LocationListener {

    private RecyclerView rv;
    private PlacesAdapter adapter;
    private ArrayList<Places> placesList = new ArrayList<>();
    double lat, lon;
    public Criteria criteria;
    public String bestProvider;

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

        locHotel = new Location("");

        locHotel.setLatitude(22.31027684);
        locHotel.setLongitude(73.16915987);

        if (lat == 0 || lon == 0) {
            lat = 22.3311940;
            lon = 73.1343920;
        }

        float[][] distance = new float[1][1];
        Location.distanceBetween(lat, lon, locHotel.getLatitude(), locHotel.getLongitude(), distance[0]);

        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int) distance[0][0] / 1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int) distance[0][0] / 1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int) distance[0][0] / 1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int) distance[0][0] / 1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int) distance[0][0] / 1000)));
        placesList.add(new Places(getString(R.string.hotel_express_name), getString(R.string.sample_description), R.drawable.hotel, Integer.toString((int) distance[0][0] / 1000)));

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

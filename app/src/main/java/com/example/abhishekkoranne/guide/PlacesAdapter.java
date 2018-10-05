package com.example.abhishekkoranne.guide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Places> placesList = new ArrayList<>();


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name, description, location;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            description = (TextView) view.findViewById(R.id.description);
            location = (TextView) view.findViewById(R.id.location);
            image = (ImageView) view.findViewById(R.id.image);

        }
    }

    public PlacesAdapter(Context context, ArrayList<Places> placesList) {
        this.context = context;
        this.placesList = placesList;
    }


    @NonNull
    @Override
    public PlacesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull PlacesAdapter.MyViewHolder myViewHolder, int position) {

        Places places = placesList.get(position);
        myViewHolder.name.setText(places.getName());
        myViewHolder.location.setText(places.getDistance()+context.getString(R.string.km));
        myViewHolder.description.setText(places.getDescription());
        myViewHolder.image.setImageResource(places.getImage());
    }

    @Override
    public int getItemCount() {
        return placesList.size();
    }


}

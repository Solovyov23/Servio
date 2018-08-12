package com.example.gentl.servio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gentl.servio.R;

import java.util.ArrayList;

public class PlacesAdapter extends ArrayAdapter<Place>
{

    public PlacesAdapter(Context context, ArrayList<Place> users)
    {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Place place = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_place, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tv_place);
        // Populate the data into the template view using the data object
        tvName.setText(place.name);
        // Return the completed view to render on screen
        return convertView;
    }
}
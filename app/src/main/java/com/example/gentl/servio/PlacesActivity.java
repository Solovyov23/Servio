package com.example.gentl.servio;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gentl.servio.Adapters.Place;
import com.example.gentl.servio.Adapters.PlacesAdapter;
import com.example.gentl.servio.Helpful.NetworkHelper;

import java.util.ArrayList;

public class PlacesActivity extends AppCompatActivity
{
    //хранит загруженные Places
    ListView listViewPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);
        setTitle("Places Activity");
        // Get ListView object from xml
        listViewPlaces = (ListView) findViewById(R.id.placesListView);

        String parentNamePlaceGroup = getIntent().getStringExtra("parentNamePlaceGroup");
        String parentPlaceGroupSchemas = getIntent().getStringExtra("parentPlaceGroupSchemas");
        String parentPlace = getIntent().getStringExtra("parentPlace");

        // ListView Item Click Listener
        listViewPlaces.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                Place  itemValue = (Place) listViewPlaces.getItemAtPosition(position);


                PlaceFragment placeFragment =  PlaceFragment.newInstance(itemValue);
                placeFragment.show(getSupportFragmentManager(), "place");
            }
        });

        //если интернет есть, то начать загрузку мест
        if(NetworkHelper.isOnline(getApplicationContext())) loadPlacesFromServer(parentNamePlaceGroup, parentPlaceGroupSchemas, parentPlace);
    }

    //начать загрузку мест и записи его в listViewPlaceUnions
    private void loadPlacesFromServer(String parentNamePlaceGroup, String parentNamePlaceGroupSchemas, String parentPlace)
    {
        NetworkHelper helper = new NetworkHelper();
        //LOAD Places
        helper.GetAllPlaces(new NetworkHelper.LoadPlacesListenerLikeObject()
        {
            @Override
            public void OnPlacesLoaded(ArrayList<Place> arrayOfPlaces)
            {
                if(arrayOfPlaces != null)
                {
                    // Create the adapter to convert the array to views
                    PlacesAdapter adapter = new PlacesAdapter(getApplicationContext(), arrayOfPlaces);
                    // Attach the adapter to a ListView
                    listViewPlaces.setAdapter(adapter);
                }
            }
        }, parentNamePlaceGroup, parentNamePlaceGroupSchemas, parentPlace);
    }
}


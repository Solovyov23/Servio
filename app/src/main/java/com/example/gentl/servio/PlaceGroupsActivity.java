package com.example.gentl.servio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.gentl.servio.Helpful.NetworkHelper;

import java.util.ArrayList;

public class PlaceGroupsActivity extends AppCompatActivity {

    //хранит загруженные PlaceGroups
    ListView listViewPlaceGroup;
    ArrayAdapter<String> placesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Place Groups Activity");

        setContentView(R.layout.activity_place_group);

        final String parentNamePlaceGroup = getIntent().getStringExtra("parentNamePlaceGroup");

        // Get ListView object from xml
        listViewPlaceGroup = (ListView) findViewById(R.id.placeGroup);

        // ListView Item Click Listener
        listViewPlaceGroup.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String  parentPlaceGroupSchemas = (String) listViewPlaceGroup.getItemAtPosition(position);

                Intent newIntent = new Intent(getApplicationContext(), PlaceGroupSchemasActivity.class);
                newIntent.putExtra("parentNamePlaceGroup", parentNamePlaceGroup);
                newIntent.putExtra("parentPlaceGroupSchemas", parentPlaceGroupSchemas);
                getApplicationContext().startActivity(newIntent);
            }
        });

        //если интернет есть, то начать загрузку мест
        if(NetworkHelper.isOnline(getApplicationContext())) loadPlacesFromServer(parentNamePlaceGroup);
    }

    //начать загрузку мест и записи его в listViewPlaceUnions
    private void loadPlacesFromServer(String parentNamePlaceGroup)
    {
        NetworkHelper helper = new NetworkHelper();
        //LOAD Places
        helper.GetAllPlaceGroup(new NetworkHelper.LoadPlacesListener()
        {
            @Override
            public void OnPlacesLoaded(ArrayList<String> listOutput)
            {
                if(listOutput != null)
                {
                    // Define a new Adapter
                    // First parameter - Context
                    // Second parameter - Layout for the row
                    // Third parameter - ID of the TextView to which the data is written
                    // Forth - the Array of data

                    placesAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            R.layout.item_place, R.id.tv_place, listOutput);

                    // Assign adapter to ListView
                    listViewPlaceGroup.setAdapter(placesAdapter);
                }
            }
        }, parentNamePlaceGroup);
    }
}


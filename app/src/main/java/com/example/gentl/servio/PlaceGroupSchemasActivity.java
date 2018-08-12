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

public class PlaceGroupSchemasActivity extends AppCompatActivity {

    //хранит загруженные PlaceGroupSchemas
    ListView listViewPlaceGroupSchemas;
    ArrayAdapter<String> placesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Place Group Schemas Activity");

        setContentView(R.layout.activity_place_group_schemas);
        // Get ListView object from xml
        listViewPlaceGroupSchemas = (ListView) findViewById(R.id.placeGroupSchemas);

        final String parentNamePlaceGroup = getIntent().getStringExtra("parentNamePlaceGroup");
        final String parentPlaceGroupSchemas = getIntent().getStringExtra("parentPlaceGroupSchemas");

        // ListView Item Click Listener
        listViewPlaceGroupSchemas.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String  parentPlace = (String) listViewPlaceGroupSchemas.getItemAtPosition(position);

                Intent newIntent = new Intent(getApplicationContext(), PlacesActivity.class);
                newIntent.putExtra("parentNamePlaceGroup", parentNamePlaceGroup);
                newIntent.putExtra("parentPlaceGroupSchemas", parentPlaceGroupSchemas);
                newIntent.putExtra("parentPlace", parentPlace);
                getApplicationContext().startActivity(newIntent);
            }
        });

        //если интернет есть, то начать загрузку мест
        if(NetworkHelper.isOnline(getApplicationContext())) loadPlacesFromServer(parentNamePlaceGroup, parentPlaceGroupSchemas);
    }

    //начать загрузку мест и записи его в listViewPlaceUnions
    private void loadPlacesFromServer(String parentNamePlaceGroup, String parentNamePlaceGroupSchemas)
    {
        NetworkHelper helper = new NetworkHelper();
        //LOAD Places
        helper.GetAllPlaceGroupSchemas(new NetworkHelper.LoadPlacesListener()
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
                    listViewPlaceGroupSchemas.setAdapter(placesAdapter);
                }
            }
        }, parentNamePlaceGroup, parentNamePlaceGroupSchemas);
    }
}


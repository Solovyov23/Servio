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

public class MainActivity extends AppCompatActivity
{
    //хранит загруженные PlaceUnions
    ListView listViewPlaceUnions;
    ArrayAdapter<String> placesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Place Unions");

        // Get ListView object from xml
        listViewPlaceUnions = (ListView) findViewById(R.id.placeUnionsListView);

        // ListView Item Click Listener
        listViewPlaceUnions.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String  parentNamePlaceGroup = (String) listViewPlaceUnions.getItemAtPosition(position);

                //показать все PlaceGroups текущей, нажатой PlaceUnion
                Intent newIntent = new Intent(getApplicationContext(), PlaceGroupsActivity.class);
                newIntent.putExtra("parentNamePlaceGroup", parentNamePlaceGroup);
                getApplicationContext().startActivity(newIntent);
            }
        });

        //если интернет есть, то начать загрузку мест
        if(NetworkHelper.isOnline(getApplicationContext())) loadPlacesFromServer();
    }

    //начать загрузку мест и записи его в listViewPlaceUnions
    private void loadPlacesFromServer()
    {
        NetworkHelper helper = new NetworkHelper();
        //LOAD Places
        helper.GetAllPlaceUnions(new NetworkHelper.LoadPlacesListener()
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
                    listViewPlaceUnions.setAdapter(placesAdapter);
                }
            }
        });
    }
}

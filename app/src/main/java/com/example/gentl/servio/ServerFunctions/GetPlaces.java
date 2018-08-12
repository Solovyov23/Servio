package com.example.gentl.servio.ServerFunctions;

import android.os.AsyncTask;

import com.example.gentl.servio.Adapters.Place;
import com.example.gentl.servio.Helpful.NetworkHelper;

import java.util.ArrayList;

public class GetPlaces extends AsyncTask<Void, Boolean, Boolean> {

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(ArrayList<Place> output);
    }

    //хранит подписчика на событие
    public GetPlaces.AsyncResponse delegate = null;
    //хранит имя открытого PlaceUnion
    private String parentNamePlaceGroup = "";
    //хранит имя открытого PlaceGroup
    private String parentNamePlaceGroupSchemas = "";
    //хранит имя открытого PlaceGroupSchema
    private String parentPlace = "";

    public GetPlaces(GetPlaces.AsyncResponse delegate, String parentNamePlaceGroup, String parentNamePlaceGroupSchemas, String parentPlace){
        this.delegate = delegate;
        this.parentNamePlaceGroup = parentNamePlaceGroup;
        this.parentNamePlaceGroupSchemas = parentNamePlaceGroupSchemas;
        this.parentPlace = parentPlace;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if(success != null) if(success) delegate.processFinish(listOfObjects);
    }

    private static ArrayList<Place> listOfObjects;
    //private static String json = "";

    @Override
    protected Boolean doInBackground(Void... paramsUserLoginTask) {
        // TODO: attempt authentication against a network service.

        NetworkHelper networkHelper = new NetworkHelper();
        listOfObjects = networkHelper.GetPlaces(parentNamePlaceGroup, parentNamePlaceGroupSchemas, parentPlace);

        if (listOfObjects != null)
        {
            return true;
        }
        else return false;
    }

    // Получение параметров от потока во время его работы
    @Override
    protected void onProgressUpdate(Boolean... values) {
        super.onProgressUpdate(values);
        onPreExecute();
    }
}

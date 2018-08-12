package com.example.gentl.servio.ServerFunctions;

import android.os.AsyncTask;

import com.example.gentl.servio.Helpful.NetworkHelper;

import java.util.ArrayList;

public class GetPlaceGroups extends AsyncTask<Void, Boolean, Boolean> {

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(ArrayList<String> output);
    }

    //хранит подписчика на событие
    public AsyncResponse delegate = null;
    //хранит имя открытого PlaceUnion
    private String parentNamePlaceGroup = "";

    public GetPlaceGroups(AsyncResponse delegate, String parentNamePlaceGroup){
        this.delegate = delegate;
        this.parentNamePlaceGroup = parentNamePlaceGroup;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if(success != null) if(success) delegate.processFinish(listOfObjects);
    }

    private static ArrayList<String> listOfObjects;
    //private static String json = "";

    @Override
    protected Boolean doInBackground(Void... paramsUserLoginTask) {
        // TODO: attempt authentication against a network service.

        NetworkHelper networkHelper = new NetworkHelper();
        listOfObjects = networkHelper.GetPlaceGroups(parentNamePlaceGroup);

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


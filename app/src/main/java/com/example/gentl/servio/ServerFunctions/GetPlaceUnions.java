package com.example.gentl.servio.ServerFunctions;
import android.os.AsyncTask;

import com.example.gentl.servio.Helpful.NetworkHelper;
import java.util.ArrayList;

public class GetPlaceUnions extends AsyncTask<Void, Boolean, Boolean> {

    // you may separate this or combined to caller class.
    public interface AsyncResponse {
        void processFinish(ArrayList<String> output);
    }

    public AsyncResponse delegate = null;
    private ArrayList<String> listOfObjects;
    public GetPlaceUnions(AsyncResponse delegate){
        this.delegate = delegate;
    }

    @Override
    protected void onPostExecute(final Boolean success) {
        if(success != null) if(success) delegate.processFinish(listOfObjects);
    }

    @Override
    protected Boolean doInBackground(Void... paramsUserLoginTask)
    {
        NetworkHelper networkHelper = new NetworkHelper();
        listOfObjects = networkHelper.GetListPlaceUnions();

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


package com.example.gentl.servio.Helpful;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.gentl.servio.Adapters.Place;
import com.example.gentl.servio.MainActivity;
import com.example.gentl.servio.Parsers.JSONParser;
import com.example.gentl.servio.R;
import com.example.gentl.servio.ServerFunctions.GetPlaceGroupSchemas;
import com.example.gentl.servio.ServerFunctions.GetPlaceGroups;
import com.example.gentl.servio.ServerFunctions.GetPlaceUnions;
import com.example.gentl.servio.ServerFunctions.GetPlaces;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;

//проверяет интернет подключение и возвращает данные из
public class NetworkHelper
{
    public AsyncTask<Void, Boolean, Boolean> placeAsyncTask;
    public static final String url = "http://sms.servio.support:32892/GetPlaces";

    public NetworkHelper() { }

    //подписаться на BroadcastReceiver, чтобы он сообщал, есть ли подключение к интернету или нет.
    public static void SubscribeToNetworkEvents(final Context context)
    {
        NetworkChangeReceiver NetworkChangeReceiver = new NetworkChangeReceiver(new NetworkChangeReceiver.DelegateNetworkChange() {
            @Override
            public void networkAvailable(boolean output) {
                if(!output)
                {
                    Toast.makeText(context.getApplicationContext(),
                            context.getResources().getString(R.string.not_internet_connection) + "", Toast.LENGTH_SHORT)
                            .show();
                }
                //открывать главную активность, если подключение к интернету изменило статус
                Intent newIntent = new Intent(context, MainActivity.class);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            }
        });

        IntentFilter filter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        context.registerReceiver(NetworkChangeReceiver, filter);
    }

    //проверить подключение к интернету один раз
    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
    ///////////////////////////////

    //список возвращаемых обьектов
    private ArrayList<String> listOfObjects;
    //используем в качестве ключа
    private final String TAG_NAME = "Name";

    //основной запрос к серверу
    public JSONObject GetJsonPlaceUnions()
    {
        // Будет хранить параметры
        FormBody.Builder params = new FormBody.Builder().add("GetPlaces", "H");
        // получаем JSON строк с URL
        JSONParser jParser = new JSONParser();
        JSONObject jsonPlaceUnions = jParser.makeHttpRequest(NetworkHelper.url, "GET", params);
        return jsonPlaceUnions;
    }

    //вернуть JSON всех PlaceUnions
    public JSONArray GetPlaceUnionArray()
    {
        JSONObject jsonPlaceUnions = GetJsonPlaceUnions();
        if(jsonPlaceUnions == null) return null;

        listOfObjects = new ArrayList<String>();

        try {
            if (jsonPlaceUnions.getJSONArray("PlaceUnions") != null) {
                // продукт найден
                // Получаем масив из Продуктов
                return jsonPlaceUnions.getJSONArray("PlaceUnions");
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //вернуть JSON всехPlaceGroups
    public JSONArray GetPlaceGroupArray(String parentNamePlaceGroup) throws JSONException
    {
        JSONArray placeUnions = GetPlaceUnionArray();

        // перебор всех продуктов
        for (int i = 0; i < placeUnions.length(); i++)
        {
            //если PlaceUnions не равен parentNamePlaceGroupSchemas(место, на которое нажал пользователь) то пропустить.
            JSONObject currentPlaceUnion = placeUnions.getJSONObject(i);

            if (!currentPlaceUnion.getString(TAG_NAME).equalsIgnoreCase(parentNamePlaceGroup)) {
                continue;
            }

            //показать все placeGroups определенного PlaceUnion
            JSONArray placeGroups = currentPlaceUnion.getJSONArray("PlaceGroups");
            return  placeGroups;
        }

        return  null;
    }

    //вернуть JSON всех GroupSchemas
    public JSONArray GetPlaceGroupSchemasArray(String parentNamePlaceGroup, String parentNamePlaceGroupSchemas) throws JSONException
    {
        JSONArray placeGroups = GetPlaceGroupArray(parentNamePlaceGroup);

        // перебор всех продуктов
        for (int i = 0; i < placeGroups.length(); i++)
        {
            //если PlaceUnions не равен parentNamePlaceGroupSchemas(место, на которое нажал пользователь) то пропустить.
            JSONObject currentPlaceGroup = placeGroups.getJSONObject(i);

            if (!currentPlaceGroup.getString(TAG_NAME).equalsIgnoreCase(parentNamePlaceGroupSchemas))
            {
                continue;
            }

            //показать все  groupSchemas определенного placeGroup
            JSONArray groupSchemas = currentPlaceGroup.getJSONArray("PlaceGroupSchemas");
            return  groupSchemas;
        }

        return  null;
    }

    //////////Вернуть данные готовым списком///////

    //получить список всех PlaceUnions
    public ArrayList<String> GetListPlaceUnions()
    {
        JSONObject jsonPlaceUnions = GetJsonPlaceUnions();
        if(jsonPlaceUnions == null) return null;

        listOfObjects = new ArrayList<String>();

        try {
            if (jsonPlaceUnions.getJSONArray("PlaceUnions") != null) {
                // продукт найден
                // Получаем масив из Продуктов
                JSONArray listResults = jsonPlaceUnions.getJSONArray("PlaceUnions");

                // перебор всех продуктов
                for (int i = 0; i < listResults.length(); i++) {
                    JSONObject current = listResults.getJSONObject(i);

                    // Сохраняем каждый jsonPlaceUnions елемент в переменную
                    listOfObjects.add(current.getString(TAG_NAME) + "");
                }
                return listOfObjects;
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    //получить список всех групп по ключу parentNamePlaceGroup
    public ArrayList<String> GetPlaceGroups(String parentNamePlaceGroup)
    {
        JSONObject jsonPlaceUnions = GetJsonPlaceUnions();
        if(jsonPlaceUnions == null) return null;

        listOfObjects = new ArrayList<String>();

        try
        {
            //получить JSONArray всех групп по ключу parentNamePlaceGroup
            JSONArray placeGroups = GetPlaceGroupArray(parentNamePlaceGroup);
            for (int currentPlaceGroupIndex = 0; currentPlaceGroupIndex < placeGroups.length(); currentPlaceGroupIndex++)
            {
                JSONObject currentPlaceGroup = placeGroups.getJSONObject(currentPlaceGroupIndex);
                listOfObjects.add(currentPlaceGroup.getString(TAG_NAME) + "");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return listOfObjects;
    }

    //получить список всех схем по ключам parentNamePlaceGroup и parentNamePlaceGroupSchemas
    public ArrayList<String> GetPlaceGroupSchemas(String parentNamePlaceGroup, String parentNamePlaceGroupSchemas)
    {
        //показать все PlaceGroupSchemas определенного placeGroups
        JSONArray placeGroupSchemas = null;
        try
        {
            //получить JSONArray всех схем по ключам parentNamePlaceGroup и parentNamePlaceGroupSchemas
            placeGroupSchemas = GetPlaceGroupSchemasArray(parentNamePlaceGroup, parentNamePlaceGroupSchemas);
            // перебор всех PlaceGroupSchemas
            for (int currentPlaceGroupSchemaIndex = 0; currentPlaceGroupSchemaIndex < placeGroupSchemas.length(); currentPlaceGroupSchemaIndex++)
            {
                JSONObject currentPlaceGroupSchema= placeGroupSchemas.getJSONObject(currentPlaceGroupSchemaIndex);
                listOfObjects.add(currentPlaceGroupSchema.getString(TAG_NAME) + "");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return listOfObjects;
    }

    //Вернуть список всех столов
    public ArrayList<Place> GetPlaces(String parentNamePlaceGroup, String parentNamePlaceGroupSchemas, String parentPlace)
    {
        //показать все PlaceGroupSchemas определенного placeGroups
        JSONArray placeGroupSchemas = null;
        ArrayList<Place> placesList = new ArrayList<Place>();
        try
        {
            //получить массив JSON всех схем по ключам parentNamePlaceGroup и parentNamePlaceGroupSchemas
            placeGroupSchemas = GetPlaceGroupSchemasArray(parentNamePlaceGroup, parentNamePlaceGroupSchemas);
            // перебор всех PlaceGroupSchemas
            for (int currentPlaceGroupSchemaIndex = 0; currentPlaceGroupSchemaIndex < placeGroupSchemas.length(); currentPlaceGroupSchemaIndex++)
            {
                JSONObject currentPlaceGroupSchema= placeGroupSchemas.getJSONObject(currentPlaceGroupSchemaIndex);
                //если текущая схема не является открытой, то
                if(!currentPlaceGroupSchema.getString(TAG_NAME).equalsIgnoreCase(parentPlace))
                {
                    continue;
                }

                //показать все places определенной PlaceGroupSchema
                JSONArray places = currentPlaceGroupSchema.getJSONArray("Places");
                for (int currentPlaceIndex = 0; currentPlaceIndex < places.length(); currentPlaceIndex++)
                {
                    JSONObject currentPlace= places.getJSONObject(currentPlaceIndex);
                    placesList.add(new Place(currentPlace.getString(TAG_NAME) + "",
                            currentPlace.getString("Code"),
                            currentPlace.getInt("Left"),
                            currentPlace.getInt("Top"),
                            currentPlace.getInt("Width"),
                            currentPlace.getInt("Height"),
                            currentPlace.getInt("Corner"),
                            currentPlace.getInt("ShapeType"),
                            currentPlace.getInt("ShapeOrient"),
                            currentPlace.getInt("Color"),
                            currentPlace.getInt("Style"),
                            currentPlace.getInt("FrameColor"),
                            currentPlace.getInt("FontColor")));
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return placesList;
    }

    //////////END Вернуть данные готовым списком///////

    /////////Загрузка данных не в основном потоке////////

    public interface LoadPlacesListener {
        void OnPlacesLoaded(ArrayList<String> output);
    }

    public interface LoadPlacesListenerLikeObject {
        void OnPlacesLoaded(ArrayList<Place> output);
    }

    public void GetAllPlaceUnions(final LoadPlacesListener listener)
    {
        placeAsyncTask = new GetPlaceUnions(new GetPlaceUnions.AsyncResponse(){

            @Override
            public void processFinish(ArrayList<String> output)
            {
                if(output != null) listener.OnPlacesLoaded(output);
            }
        }).execute();
    }

    public void GetAllPlaceGroup(final LoadPlacesListener listener, String parentNamePlaceGroupSchemas)
    {
        placeAsyncTask = new GetPlaceGroups(new GetPlaceGroups.AsyncResponse(){

            @Override
            public void processFinish(ArrayList<String> output)
            {
                if(output != null) listener.OnPlacesLoaded(output);
            }
        }, parentNamePlaceGroupSchemas).execute();
    }

    public void GetAllPlaceGroupSchemas(final LoadPlacesListener listener, String parentNamePlaceGroup, String parentNamePlaceGroupSchemas)
    {
        placeAsyncTask = new GetPlaceGroupSchemas(new GetPlaceGroupSchemas.AsyncResponse(){

            @Override
            public void processFinish(ArrayList<String> output)
            {
                if(output != null) listener.OnPlacesLoaded(output);
            }
        }, parentNamePlaceGroup, parentNamePlaceGroupSchemas).execute();
    }

    public void GetAllPlaces(final LoadPlacesListenerLikeObject listener, String parentNamePlaceGroup, String parentNamePlaceGroupSchemas, String parentPlace)
    {
        placeAsyncTask = new GetPlaces(new GetPlaces.AsyncResponse(){

            @Override
            public void processFinish(ArrayList<Place> output)
            {
                if(output != null) listener.OnPlacesLoaded(output);
            }
        }, parentNamePlaceGroup, parentNamePlaceGroupSchemas, parentPlace).execute();
    }

    /////////END Загрузка данных не в основном потоке////////
}

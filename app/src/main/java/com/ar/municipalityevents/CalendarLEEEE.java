package com.ar.municipalityevents;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ar.municipalityevents.dto.Event;
import com.ar.municipalityevents.translator.EventTranslator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Calendar extends AppCompatActivity {/*

    private RequestQueue queue;
    private String url = "http://localhost:3000/events";
    private String monthSelected;
    private String yearSelected;
    private List<String> eventDataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        queue= Volley.newRequestQueue(this);
        this.getApiData("11", "2021");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, eventDataList);

    }

    private void getApiData(String month, String year){

        eventDataList = new ArrayList<>();

        String getEventsUrl = getFormat(month, year);
        JsonArrayRequest request = new JsonArrayRequest(
                getEventsUrl, response -> {
                    if(response.length() >0){
                        this.setEvents(response);
                    }
                }, error -> {

                });

        queue.add(request);
    }
    
    private List<Event> setEvents(JSONArray apiResponse){
        List<Event> result = new ArrayList<>();

        for (int i = 0; i < apiResponse.length(); i++) {
            try{
                JSONObject object = apiResponse.getJSONObject(0);
                Event event = EventTranslator.toDto(object);
                result.add(event);
                eventDataList.add(event.getName());
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    @NonNull
    private String getFormat(String month, String year) {
        return String.format(url + "?year=%s&month=%s", year, month);
    }


}

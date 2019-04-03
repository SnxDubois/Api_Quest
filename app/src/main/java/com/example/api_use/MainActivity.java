package com.example.api_use;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "4fc631ceb2280860b558a7d09aa30498";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        // TODO : URL de la requête vers l'API
        //String url = "https://api.openweathermap.org/data/2.5/weather?q=Toulouse&appid=" + API_KEY;
        String urlForecast = "https://api.openweathermap.org/data/2.5/forecast?q=Toulouse&appid=" + API_KEY;

        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        final JsonObjectRequest weatherForecast = new JsonObjectRequest(Request.Method.GET, urlForecast, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray list = response.getJSONArray("list");
                    for (int i = 0; i < 5; i++) {
                        JSONObject weatherList = list.getJSONObject(i);
                        JSONArray weatherForecastToulouse = weatherList.getJSONArray("weather");
                        for (int j = 0; j < weatherForecastToulouse.length(); j++) {
                            JSONObject dailyForecast = weatherForecastToulouse.getJSONObject(j);
                            String dailyDescription = dailyForecast.getString("description");
                            Toast.makeText(MainActivity.this, dailyDescription, Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Afficher l'erreur
                Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());

            }
        });

        // On ajoute la requête à la file d'attente
        requestQueue.add(weatherForecast);

        /*
        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO : traiter la réponse
                        try {
                            JSONArray weather = response.getJSONArray("weather");
                            for (int i = 0; i < weather.length(); i++) {
                                JSONObject weatherInfo = (JSONObject) weather.get(i);
                                String description = (String) weatherInfo.get("description");
                                Toast.makeText(MainActivity.this, description, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);

        */
    }
}

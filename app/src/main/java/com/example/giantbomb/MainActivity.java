package com.example.giantbomb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
public class MainActivity extends AppCompatActivity {

    private EditText rechercheEditText;
    private Button rechercherButton;
    private Button viderButton;
    private ListView compagniesListView;

    private ArrayAdapter<Compagnie> compagniesAdapter;
    private List<Compagnie> compagniesList;

    private static final String API_KEY = "90f011305c53c1ec3340214868ba42eaf9ce6fe8";
    private static final String API_URL = "https://www.giantbomb.com/api/games/?api_key=" + API_KEY + "&format=json&filter=name:%s&limit=10";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rechercheEditText = findViewById(R.id.recherche_edittext);
        rechercherButton = findViewById(R.id.rechercher_button);
        viderButton = findViewById(R.id.vider_button);
        compagniesListView = findViewById(R.id.compagnies_listview);

        compagniesList = new ArrayList<>();
        compagniesAdapter = new CompagnieAdapter(this, compagniesList);
        compagniesListView.setAdapter(compagniesAdapter);

        rechercherButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechercherCompagnies();
            }
        });

        viderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rechercheEditText.setText("");
                compagniesList.clear();
                compagniesAdapter.notifyDataSetChanged();
            }
        });

        compagniesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Compagnie compagnie = compagniesList.get(position);
                // Lancer l'activité des détails de la compagnie en passant les informations nécessaires
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
                intent.putExtra("nom", compagnie.getNom());
                intent.putExtra("date", compagnie.getDateCreation());
                intent.putExtra("logo", compagnie.getLogo());
                intent.putExtra("description", compagnie.getDescription());
                startActivity(intent);
            }
        });
    }

    private void rechercherCompagnies() {
        String recherche = rechercheEditText.getText().toString().trim();
        if (TextUtils.isEmpty(recherche)) {
            return;
        }

        try{
            String encodage = URLEncoder.encode(recherche, StandardCharsets.UTF_8.toString());
            String url = String.format(API_URL, encodage);
            new RechercheCompagniesTask().execute(url);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);

        }

    }

    private class RechercheCompagniesTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String urlString = urls[0];
            //On verifie si tout se passe correctemetn
            Log.d("RechercheCompagniesTask", "URL: " + urlString);
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                InputStream inputStream = connection.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                // StringBuffer httpRequestResponse = new StringBuffer(); ne fonctionne pas

                String line;
                while ((line = in.readLine()) != null) {

                    stringBuilder.append(line);
                }
                in.close();
                return stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "erreur: "+e.getMessage();
            }catch (IOException e) {
                e.printStackTrace();
                return "IO Exception: " + e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("RechercheCompagniesTask", "Result: " + result);
                try {
                    JSONObject response = new JSONObject(result);
                    JSONArray resultsArray = response.getJSONArray("results");

                    compagniesList.clear();

                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject compagnieObject = resultsArray.getJSONObject(i);
                        String nom = compagnieObject.getString("name");
                        String dateCreation = compagnieObject.getString("date_last_updated");
                        String logo = compagnieObject.getString("image");
                        String description = compagnieObject.getString("deck");

                        // Créer un objet Compagnie à partir des informations obtenues
                        Compagnie compagnie = new Compagnie(nom, dateCreation, logo, description);
                        compagniesList.add(compagnie);
                    }

                    compagniesAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("RechercheCompagniesTask", "Résultat null.");
            }

        }
    }
}

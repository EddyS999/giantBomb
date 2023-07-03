package com.example.giantbomb;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    private TextView nomTextView;
    private TextView dateCreationTextView;
    private ImageView logoImageView;
    private WebView descriptionWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        nomTextView = findViewById(R.id.nom_textview);
        dateCreationTextView = findViewById(R.id.date_creation_textview);
        logoImageView = findViewById(R.id.logo_imageview);
        descriptionWebView = findViewById(R.id.description_webview);

        // Récupérer les informations passées depuis l'activité précédente
        String nom = getIntent().getStringExtra("nom");
        String dateCreation = getIntent().getStringExtra("date");
        String logo = getIntent().getStringExtra("logo");
        String description = getIntent().getStringExtra("description");

        if(nom != null) {
            nomTextView.setText(nom);
        }
        if(dateCreation != null) {
            dateCreationTextView.setText(dateCreation);
        }

        if (description != null) {
            descriptionWebView.loadDataWithBaseURL(null, description, "text/html", "utf-8", null);
        }



        // Charger l'image du logo dans l'ImageView (vous devez utiliser une bibliothèque de chargement d'images telle que Picasso ou Glide)

        // Afficher la description dans le WebView
        if (!TextUtils.isEmpty(description)) {
            descriptionWebView.loadDataWithBaseURL(null, description, "text/html", "utf-8", null);
        }

        Intent intent = getIntent();
        String date = intent.getStringExtra("date");
        // Vous pouvez récupérer plus d'information si vous le souhaitez

        /*
        // Afficher les informations récupérées dans vos vues. Par exemple, si vous avez un TextView pour le nom :
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView nomView = findViewById(R.id.nom);
        nomView.setText(nom);
        */
    }
}
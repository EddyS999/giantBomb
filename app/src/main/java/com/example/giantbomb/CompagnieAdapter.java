package com.example.giantbomb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CompagnieAdapter extends ArrayAdapter<Compagnie> {
    public CompagnieAdapter(Context context, List<Compagnie> compagnies){
        super(context, 0, compagnies);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        Compagnie compagnie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_compagnie, parent, false);
        }


        ImageView logo = (ImageView) convertView.findViewById(R.id.logo);
        TextView nom = (TextView) convertView.findViewById(R.id.nom);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView description = (TextView) convertView.findViewById(R.id.description);


        nom.setText(compagnie.getNom());
        date.setText(compagnie.getDateCreation());
        description.setText(compagnie.getDescription());


        return convertView;

    }
}

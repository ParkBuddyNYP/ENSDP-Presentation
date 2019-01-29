package com.example.a163363s.parkbuddy.Adapters;
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.a163363s.parkbuddy.R;
import com.example.a163363s.parkbuddy.DataModels.Location;

public class LocationGridViewAdapter extends ArrayAdapter {

    private Location [] locations;
    public LocationGridViewAdapter(Context context, Location[] locations)
    {
        super(context, 0, locations);
        this.locations = locations;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View itemView = convertView;
        if (itemView == null)
            itemView = ((Activity)this.getContext()).getLayoutInflater().inflate(R.layout.gridview_location, null);

        ImageView imageView = itemView.findViewById(R.id.imageView);
        imageView.setImageResource(locations[position].getImageResource());

        TextView textViewTitle = itemView.findViewById(R.id.textViewTitle);
        textViewTitle.setText(locations[position].getTitle());



        return itemView;
    }
}



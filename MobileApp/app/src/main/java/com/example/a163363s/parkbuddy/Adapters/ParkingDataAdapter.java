package com.example.a163363s.parkbuddy.Adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a163363s.parkbuddy.DataModels.ParkingLotData;
import com.example.a163363s.parkbuddy.DataModels.UserData;
import com.example.a163363s.parkbuddy.LocationInfoActivity;
import com.example.a163363s.parkbuddy.R;

public class ParkingDataAdapter extends ArrayAdapter<ParkingLotData> {

    private final Activity context;
    private final ParkingLotData[] data;

    public ParkingDataAdapter(Activity context, ParkingLotData[] data) {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null)
        {
            itemView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.parking_cardview_layout, null, true);
        }

        TextView lotAvail = itemView.findViewById(R.id.lotAvail);
        TextView totalLot = itemView.findViewById(R.id.totalLot);
        TextView Name = itemView.findViewById(R.id.lotName);

        lotAvail.setText(data[position].getTotalNumberOfLotsAvailable());
        totalLot.setText(data[position].getTotalNumberOfLots());
        Name.setText(data[position].getCarparkName());


        //new DownloadImageTask(userProfileImage).execute(data[position].getUserProfileImage());
        return itemView;
    }
}
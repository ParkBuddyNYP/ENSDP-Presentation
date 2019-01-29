package com.example.a163363s.parkbuddy.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import com.example.a163363s.parkbuddy.R;
import com.example.a163363s.parkbuddy.DataModels.Task;

public class TasksAdapter extends ArrayAdapter {
    private Task[] data;

    public TasksAdapter(Context context, Task[] data)
    {
        super(context, 0 ,data);
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null)
            itemView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.gridview_task, null);

        final Task task = data[position];

        LinearLayout linearLayoutTagColour = itemView.findViewById(R.id.linearLayoutTagColor);
        if (task.getColour().equals("G"))
            linearLayoutTagColour.setBackgroundResource(android.R.color.holo_green_light);
        if (task.getColour().equals("B"))
            linearLayoutTagColour.setBackgroundResource(android.R.color.holo_blue_light);
        if (task.getColour().equals("R"))
            linearLayoutTagColour.setBackgroundResource(android.R.color.holo_red_light);

        TextView textViewDateDue = itemView.findViewById(R.id.textViewDateDue);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        textViewDateDue.setText(dateFormat.format(task.getDatedue()));

        TextView textViewDescription = itemView.findViewById(R.id.textViewDescription);
        textViewDescription.setText(task.getDescription());

        return itemView;
    }
}

package com.example.a163363s.parkbuddy.Bookmark;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a163363s.parkbuddy.R;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.mHolder> {

    List<bookmarkModel> mList = new ArrayList<>();
    Context context;
    BookmarkDB db;

    public Adapter(List<bookmarkModel> mList, Context context) {
        this.mList = mList;
        this.context = context;
        db = new BookmarkDB(context, "Bookmark_DB");
    }

    @NonNull
    @Override
    public mHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new mHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookmark_row_design, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final mHolder holder, final int i) {
        final bookmarkModel obj = mList.get(i);
        holder.txtLocationName.setText(obj.location_name);
        holder.txtDesc.setText(obj.location_description);
        holder.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewBookmarkOnMap.class);
                Bundle bundle = new Bundle();
                bundle.putString("latitude", String.valueOf(obj.lat));
                bundle.putString("longitude", String.valueOf(obj.lng));
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder delDialog = new AlertDialog.Builder(context);
                delDialog.setTitle("Warning!");
                delDialog.setMessage("Location data will be permanently deleted.\nDo you want to proceed?");
                delDialog.setCancelable(false);
                delDialog.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deleteBookmark(obj.lat);
                        holder.layout.setMaxHeight(0);
                        Toast.makeText(context, "Bookmark deleted", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                delDialog.show();
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(context).inflate(R.layout.layout_location_info, null, false);
                AlertDialog.Builder editDialog = new AlertDialog.Builder(context);
                editDialog.setCancelable(false);
                final EditText edtLocationName, edtLocationDescription, edtLatitude, edtLongitude;
                Button btnSave, btnCancel;
                edtLocationName = view.findViewById(R.id.edt_location_title);
                edtLocationDescription = view.findViewById(R.id.edt_location_description);
                edtLatitude = view.findViewById(R.id.edt_location_latitude);
                edtLongitude = view.findViewById(R.id.edt_location_longitude);
                btnSave = view.findViewById(R.id.btn_save_location);
                btnCancel = view.findViewById(R.id.btn_cancel);
                editDialog.setView(view);
                edtLocationName.setText(obj.location_name);
                edtLocationDescription.setText(obj.location_description);
                edtLatitude.setText("Lat: " + String.valueOf(obj.lat));
                edtLongitude.setText("Lng: " + String.valueOf(obj.lng));


                final AlertDialog dialog = editDialog.create();
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!edtLocationName.getText().toString().equals("") && !edtLocationDescription.getText().toString().equals("")) {
                            db.updateBookmark(edtLocationName.getText().toString(), edtLocationDescription.getText().toString(), obj.lat);
                            Toast.makeText(context, "Data updated successfully", Toast.LENGTH_SHORT).show();
                            ((Activity) context).recreate();
                            dialog.cancel();
                        } else {
                            Toast.makeText(context, "Fill all fields first", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                dialog.show();
            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class mHolder extends RecyclerView.ViewHolder {

        TextView txtLocationName, txtDesc;
        ImageView imgLocation, imgEdit, imgDelete;
        ConstraintLayout layout;

        public mHolder(@NonNull View itemView) {
            super(itemView);
            txtLocationName = itemView.findViewById(R.id.txt_location_name);
            txtDesc = itemView.findViewById(R.id.txtLocationDescription);
            imgLocation = itemView.findViewById(R.id.img_goto_location);
            imgDelete = itemView.findViewById(R.id.imgDeleteLocation);
            imgEdit = itemView.findViewById(R.id.imgEditLocation);
            layout = itemView.findViewById(R.id.root_layout);
        }
    }
}

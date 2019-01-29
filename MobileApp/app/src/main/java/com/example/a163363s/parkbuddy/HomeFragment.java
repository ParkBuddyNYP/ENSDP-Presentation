package com.example.a163363s.parkbuddy;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.example.a163363s.parkbuddy.Bookmark.ActivityViewBookmark;


public class HomeFragment extends Fragment {

    Dialog myDialog;
    ProgressDialog dialog;
    ViewFlipper v_Flipper;
    int [] Images = {R.drawable.carr, R.drawable.car1, R.drawable.car2};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home , container, false);

        myDialog = new Dialog(getContext());
        ImageButton imageViewRating = view.findViewById(R.id.btnRate);
        imageViewRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TextView txtclose;
                Button btnsubmit;

                myDialog.setContentView(R.layout.popup_rating);
                txtclose =(TextView) myDialog.findViewById(R.id.txtclose);
                txtclose.setText("X");
                btnsubmit = (Button) myDialog.findViewById(R.id.btnsubmit);
                btnsubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getContext(), "Thank You for rating!", Toast.LENGTH_LONG).show();
                        myDialog.dismiss();
                    }
                });

                txtclose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                myDialog.show();
            }
        });

        ImageButton imageUserProfile = view.findViewById(R.id.btnProfile);
        imageUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), UserProfileActivity.class);
                startActivity(in);


            }
        });

        ImageButton imageBookmark = view.findViewById(R.id.btnBookmark);
        imageBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), ActivityViewBookmark.class);
                startActivity(in);


            }
        });



        ImageButton imageLocation = view.findViewById(R.id.btnLocate);
        imageLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                //Fragment fragment = new tasks();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.push_left_in, R.anim.push_left_out);
                fragmentTransaction.replace(R.id.frame_layout, new LocateFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });



        return view;
    }




}


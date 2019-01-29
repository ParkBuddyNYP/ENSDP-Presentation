package com.example.a163363s.parkbuddy.LotLayout;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ImageViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.Objects;

import com.example.a163363s.parkbuddy.Activity.MainActivity;
import com.example.a163363s.parkbuddy.QRActivity;
import com.example.a163363s.parkbuddy.R;
import com.example.a163363s.parkbuddy.TrackActivity;
import com.example.a163363s.parkbuddy.UserProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class BaseOneFragment extends Fragment {

    private ImageView Car1, Car2, Car3, Car4, Car5, Car6, Car7, Car8, Car9, Car10;
    private Button btn;
    int signal1 = 0;
    int signal2 = 0;
    int signal3 = 0;
    int signal4 = 0;
    int signal5 = 0;
    int NOTIFICATION_ID;
    private ProgressDialog pDialog;

    public BaseOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_one, container, false);


    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onStart() {
        super.onStart();

        btn = (Button) getActivity().findViewById(R.id.butp);




        Car1 = (ImageView) getActivity().findViewById(R.id.imgb11);
        Car3 = (ImageView) getActivity().findViewById(R.id.imgb16);


        Car4 = (ImageView) getActivity().findViewById(R.id.imgb18);

        Car2 = (ImageView) getActivity().findViewById(R.id.imgb20);


        Car5 = (ImageView) getActivity().findViewById(R.id.imgb14);


        Car1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signal1 > 0) {
                    signal1 = 0;

                    Car1.setImageResource(R.drawable.gcarl);
                    btn.setText("Proceed with Spot");
                    Car2.setEnabled(true);
                    Car3.setEnabled(true);
                    Car4.setEnabled(true);
                    Car5.setEnabled(true);
                } else {
                    Car1.setImageResource(R.drawable.bcarl);
                    btn.setText("Proceed with Spot AO1 (Base One)");
                    Car2.setEnabled(false);
                    Car3.setEnabled(false);
                    Car4.setEnabled(false);
                    Car5.setEnabled(false);
                    signal1++;
                    Notification();
                }
            }
        });
        Car2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signal2 > 0) {

                    signal2 = 0;
                    Car2.setImageResource(R.drawable.gcarr);
                    btn.setText("Proceed with Spot");
                    Car1.setEnabled(true);
                    Car3.setEnabled(true);
                    Car4.setEnabled(true);
                    Car5.setEnabled(true);


                } else {
                    Car2.setImageResource(R.drawable.bcarr);
                    btn.setText("Proceed with Spot BO5 (Base One)");
                    Car1.setEnabled(false);
                    Car3.setEnabled(false);
                    Car4.setEnabled(false);
                    Car5.setEnabled(false);
                    signal2++;
                    Notification();
                }
            }
        });
        Car3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signal3 > 0) {
                    signal3 = 0;

                    Car3.setImageResource(R.drawable.gcarr);
                    btn.setText("Proceed with Spot");
                    Car1.setEnabled(true);
                    Car2.setEnabled(true);
                    Car4.setEnabled(true);
                    Car5.setEnabled(true);


                } else {

                    Car3.setImageResource(R.drawable.bcarr);
                    btn.setText("Proceed with Spot BO1 (Base One)");
                    Car2.setEnabled(false);
                    Car1.setEnabled(false);
                    Car4.setEnabled(false);
                    Car5.setEnabled(false);
                    signal3++;
                    Notification();
                }
            }
        });
        Car4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signal4 > 0) {
                    signal4 = 0;
                    Car4.setImageResource(R.drawable.gcarr);
                    btn.setText("Proceed with Spot");
                    Car1.setEnabled(true);
                    Car3.setEnabled(true);
                    Car2.setEnabled(true);
                    Car5.setEnabled(true);
                } else {
                    Car4.setImageResource(R.drawable.bcarr);
                    btn.setText("Proceed with Spot BO3 (Base One)");
                    Car2.setEnabled(false);
                    Car3.setEnabled(false);
                    Car1.setEnabled(false);
                    Car5.setEnabled(false);
                    signal4++;
                    Notification();
                }
            }
        });
        Car5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (signal5 > 0) {
                    signal5 = 0;
                    Car5.setImageResource(R.drawable.gcarl);
                    btn.setText("Proceed with Spot");
                    Car1.setEnabled(true);
                    Car3.setEnabled(true);
                    Car4.setEnabled(true);
                    Car2.setEnabled(true);
                } else {
                    Car5.setImageResource(R.drawable.bcarl);
                    btn.setText("Proceed with Spot AO4 (Base One)");
                    Car2.setEnabled(false);
                    Car3.setEnabled(false);
                    Car4.setEnabled(false);
                    Car1.setEnabled(false);
                    signal5++;
                    Notification();
                }
            }
        });


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void Notification() {
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
                        for (int i = 0; i <= 100; i = i + 10) {
                            Notification notification = new NotificationCompat.Builder(getActivity(), "")
                                    .setContentTitle("Park buddy Journey Preparation")
                                    .setContentText("You can count on us...")
                                    .setSmallIcon(R.drawable.parking)
                                    .setProgress(100, i, false)
                                    // any other attributes
                                    .build();

                            notificationManager.notify(NOTIFICATION_ID, notification);


                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {

                            }

                        }
                        Notification notification = new NotificationCompat.Builder(getActivity(), "")
                                .setContentTitle("All set and ready to go!")
                                .setContentText("Have a safe trip!")
                                .setSmallIcon(R.drawable.parking)
                                .build();

                        notificationManager.notify(NOTIFICATION_ID, notification);
                    }
                }).start();


                pDialog = new ProgressDialog(getActivity());
                pDialog.setTitle("Journey Tracker");
                pDialog.setMessage("Please wait as we prepare you journey.....");
                pDialog.setCancelable(false);
                pDialog.show();

                final Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {
                        pDialog.cancel();
                    }
                };

                Handler pdCanceller = new Handler();
                pdCanceller.postDelayed(progressRunnable, 8000);

                Intent in = new Intent(getActivity(), TrackActivity.class);
                startActivity(in);
            }

        });
    }
}
package com.example.a163363s.parkbuddy.FingerPrintHandler;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.content.ContextCompat;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a163363s.parkbuddy.QRActivity;
import com.example.a163363s.parkbuddy.R;
import com.example.a163363s.parkbuddy.UserProfileActivity;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandler extends FingerprintManager.AuthenticationCallback {

    private Context context;

    public FingerprintHandler(Context context){

        this.context = context;

    }

    public void startAuth(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject){

        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);

    }

    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {

        this.update("There was an Authentication Error. " + errString, false);

    }

    @Override
    public void onAuthenticationFailed() {

        this.update("Authentication Failed. ", false);

    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {

        this.update("Error: " + helpString, false);

    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {

        this.update("You can now access the ParkBuddy!.", true);



    }

    private void update(String s, boolean b) {

        TextView paraLabel = (TextView) ((Activity)context).findViewById(R.id.paraLabel);
        ImageView imageView = (ImageView) ((Activity)context).findViewById(R.id.fingerprintImage);
        Button button = (Button) ((Activity)context).findViewById(R.id.btnAuth);

        paraLabel.setText(s);


        if(b == false){

            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.instabug_grey_hint_dark));
            button.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_view_style4));
            button.setText("Please Try Again!");
            button.setTextColor(ContextCompat.getColor(context, R.color.instabug_annotation_color_red));
            button.setEnabled(false);

        } else {

            paraLabel.setTextColor(ContextCompat.getColor(context, R.color.green));
            imageView.setImageResource(R.mipmap.action_done);

            button.setText("Start Using Parkbuddy!");
            button.setTextColor(ContextCompat.getColor(context, R.color.green));
            button.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_view_style3));
            button.setEnabled(true);



                }

    }
}


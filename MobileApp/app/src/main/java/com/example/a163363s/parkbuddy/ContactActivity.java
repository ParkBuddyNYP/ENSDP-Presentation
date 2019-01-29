package com.example.a163363s.parkbuddy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        TextView email = (TextView) findViewById(R.id.textViewEmail);
        email.setText(Html.fromHtml("<a href=\"abcd1234chen@hotmail.com\">Email: abcd1234chen@hotmail.com</a>"));
        email.setMovementMethod(LinkMovementMethod.getInstance());


    }


}

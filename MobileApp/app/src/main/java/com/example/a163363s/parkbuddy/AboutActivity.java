package com.example.a163363s.parkbuddy;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.Calendar;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;


public class AboutActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simulateDayNight(0);
        Element adsElement=new Element();
        adsElement.setTitle("Advertise with us");
        View aboutPage= new AboutPage(this)
                .setDescription("Park Buddies is a parking lot appplication when users are able to find a nearby Carpark easily!")
                .isRTL(false)
                .addItem(new Element().setTitle("Version 9.2"))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("parkbuddy@gmail.com")
                .addWebsite("parkbuddy.com")
                .addFacebook("facebook.com")
                .addTwitter("twitter.com")
                .addPlayStore("https://play.google.com/store/apps")
                .addInstagram("https://www.instagram.com/")
                .addItem(getCopyRightElement())
                .create();
        setContentView(aboutPage);


    }

    private void simulateDayNight(int currentsetting) {
        int DAY=0;
        int NIGHT=1;
        int FOLLOW_SYSTEM=3;
        int currentNightmode=getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentsetting==DAY && currentNightmode!=Configuration.UI_MODE_NIGHT_NO){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }
        else if (currentsetting==NIGHT && currentNightmode!=Configuration.UI_MODE_NIGHT_YES){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }
        else if (currentsetting==FOLLOW_SYSTEM){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);

        }
    }

    Element getCopyRightElement(){
        Element copyRightsElement=new Element();
        @SuppressLint({"StringFormatInvalid", "LocalSuppress"}) final String copyrights=String.format(getString(R.string.app_name), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutActivity.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;

    }

}

package com.example.a163363s.parkbuddy.Adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import com.example.a163363s.parkbuddy.DataModels.UserData;
import com.example.a163363s.parkbuddy.R;

public class UserProfileAdapter extends ArrayAdapter<UserData>{

    private final Activity context;
    private final UserData[] data;
    public UserProfileAdapter(Activity context, UserData[] data)
    {
        super(context, 0, data);
        this.context = context;
        this.data = data;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null)
        {
            itemView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.listview_user_profile, null, true);
        }

        //TextView id = itemView.findViewById(R.id.textuserID);
        TextView name = itemView.findViewById(R.id.textViewName);
        TextView username = itemView.findViewById(R.id.textViewUserName);
        TextView email = itemView.findViewById(R.id.textViewEmail);
        //TextView userPassword = itemView.findViewById(R.id.textuserPassword);
        ImageView userProfileImage = itemView.findViewById(R.id.imageView);
        //TextView Lot = itemView.findViewById(R.id.textusercarparkLot);
       // TextView currentLotUsed = itemView.findViewById(R.id.textcurrentLotUsed);
        //TextView carparkid = itemView.findViewById(R.id.textcarparkid);
        //TextView userReports = itemView.findViewById(R.id.textuserReports);


        //id.setText(data[position].getUserID());
        username.setText(data[position].getUserName());
        name.setText(data[position].getName());
        email.setText(data[position].getUserEmail());

        //userPassword.setText(data[position].getUserPassword());
        //currentLotUsed.setText(data[position].getCurrentLotUsed());
       // Lot.setText(data[position].getCarparkLot());
        //carparkid.setText(data[position].getCarparkid());
       // userReports.setText(data[position].getUserReports());


        //new DownloadImageTask(userProfileImage).execute(data[position].getUserProfileImage());
        return itemView;
    }

   /* private class DownloadImageTask extends AsyncTask <String, Void, Bitmap>
    {
        ImageView imageViewToUpdate;

        public DownloadImageTask(ImageView imageView)
        {
            this.imageViewToUpdate = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {

            String urlDisplay = urls[0];

            Bitmap bitmap = null;

            try
            {
                InputStream inputStream = new URL(urlDisplay).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            }
            catch (Exception e)
            {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap)
        {
            imageViewToUpdate.setImageBitmap(bitmap);
        }
    }*/


}

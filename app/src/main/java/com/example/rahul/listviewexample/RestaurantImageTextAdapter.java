package com.example.rahul.listviewexample;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class RestaurantImageTextAdapter extends ArrayAdapter{

    public RestaurantImageTextAdapter(Context context, String[] values) {
        super(context, R.layout.restaurant_image_row_layout, R.id.restaurantImageTextView ,values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());

        View theView = layoutInflater.inflate(R.layout.restaurant_image_row_layout, parent, false);

        String selectedRestaurant = String.valueOf(getItem(position));

        TextView restaurantImageTextView = (TextView) theView.findViewById(R.id.restaurantImageTextView);

        restaurantImageTextView.setText(selectedRestaurant);

        ImageView imageView = (ImageView) theView.findViewById(R.id.restaurantImage);


        new DownloadImageTask(imageView).execute("https://s3-ap-southeast-1.amazonaws.com/hungrypapa-restaurant-images/Images/rzXMA4jD5zD4M3JAc-butterbeans.jpg");

        return theView;
    }

    class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            bmImage.setImageBitmap(result);
        }
    }
}

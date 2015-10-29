package controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieunguyen725.myplaces.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import model.Place;
import parsers.JSONParser;

public class PlaceInfoActivity extends AppCompatActivity {

    public static final String TAG = "PlaceInfoActivity";
    private static final String API_KEY = "&key=AIzaSyCYoO7HjswFyU9zNWR7kP_kJoWs_IlQIuI";
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/details/json?";

    private ProgressBar progressBar;

    private Place currentPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_info);
//        getSupportActionBar().hide();
//        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.placeInfo_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        retrieveInfo();
    }

    private void retrieveInfo() {
        if (isOnline()) {
            String placeID = "placeid=" + getIntent().getStringExtra("placeID");
            String URL = BASE_URL + placeID + API_KEY;
            Log.i(TAG, "URL = " + URL);
            SearchTask task = new SearchTask();
            task.execute(URL);
        } else {
            Toast.makeText(this, "Network connection is not available", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_place_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    private class SearchTask extends AsyncTask<String, String, Place> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Starting task");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Place doInBackground(String... params) {
            ConnectionManager connectionManager = new ConnectionManager();
            String content = null;
            try {
                content = connectionManager.getData(params[0]);
                if (content != null) {
                    Log.i(TAG, "content is not null");
                    Log.i(TAG, "content length: " + content.length());
                    Place place = new JSONParser().infoParse(content);
                    if (place != null && !place.getImageReference().equals(null)) {
                        place = loadImage(place);
                    }
                    return place;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private Place loadImage(Place place) {
            try {
                String imageURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400"
                        + "&photoreference=" + place.getImageReference() + API_KEY;
                InputStream inputStream = (InputStream)
                        new URL(imageURL).getContent();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                place.setPlaceImage(bitmap);
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return place;
        }

        @Override
        protected void onPostExecute(Place place) {
            if (place != null) {
                // set username for place here
                Log.i(TAG, place.toString());
                displayInfo(place);
                currentPlace = place;
            } else {
                Log.i(TAG, "can't parse, place is null");
            }
            progressBar.setVisibility(View.INVISIBLE);
            Log.i(TAG, "task finished");
        }
    }

    private void displayInfo(Place place) {
        TextView placeName = (TextView) findViewById(R.id.placeInfo_name_tv);
        TextView placeAddress = (TextView) findViewById(R.id.placeInfo_address_tv);
        TextView placeType = (TextView) findViewById(R.id.placeInfo_placeType_tv);
        TextView placePhone = (TextView) findViewById(R.id.placeInfo_phone_tv);
        TextView placeDescription = (TextView) findViewById(R.id.placeInfo_description_tv);
        ImageView placeImage = (ImageView) findViewById(R.id.placeInfo_place_image);
        TextView openingHours = (TextView) findViewById(R.id.placeInfo_opening_hours_tv);
        TextView reviews = (TextView) findViewById(R.id.placeInfo_reviews_tv);

        placeName.setText("Name: " + place.getName());
        placeAddress.setText("Address: " + place.getAddress());
        placeType.setText("Place type: " + place.getMainType());
        placePhone.setText("Phone: " + place.getPhoneNumber());
        if (place.getImageReference() != null) {
            placeImage.setImageBitmap(place.getPlaceImage());
        }
        if (!place.getDescription().equals("Not available")) {
            placeDescription.setText("Description: " + place.getDescription());
        }
        if (place.getOpeningHours() != null) {
            openingHours.setText("Opening hours: \n\n" + place.getOpeningHours());
        }
        if (place.getReviews() != null) {
            reviews.setText("Reviews: \n\n" + place.getReviews());
        }
    }

    public void viewWebOnClick(View view) {
        if (!currentPlace.getWebsiteURL().equals("Not available")) {
            Uri URL = Uri.parse(currentPlace.getWebsiteURL());
            Intent intent = new Intent(Intent.ACTION_VIEW, URL);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Place has no website", Toast.LENGTH_LONG).show();
        }
    }

    public void backButtonOnClick(View view) {
        finish();
    }



}

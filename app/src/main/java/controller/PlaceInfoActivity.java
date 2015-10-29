package controller;

import android.app.ActionBar;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hieunguyen725.myplaces.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import database.PlacesDataSource;
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
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.placeInfo_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        retrieveInfo();
    }

    private void retrieveInfo() {
        String contentSource = getIntent().getStringExtra("placeSource");
        if (contentSource.equals("onlineContent")) {
            if (isOnline()) {
                String placeID = "placeid=" + getIntent().getStringExtra("placeID");
                String URL = BASE_URL + placeID + API_KEY;
                Log.i(TAG, "URL = " + URL);
                SearchTask task = new SearchTask();
                task.execute(URL);
            } else {
                Toast.makeText(this, "Network connection is not available", Toast.LENGTH_LONG).show();
            }
        } else {
            Bundle extras = getIntent().getExtras();
            currentPlace = new Place();
            currentPlace.setName(extras.getString("placeName"));
            currentPlace.setAddress(extras.getString("placeAddress"));
            currentPlace.setMainType(extras.getString("placeType"));
            currentPlace.setPhoneNumber(extras.getString("placePhone"));
            currentPlace.setDescription(extras.getString("placeDescription"));
            displayInfo();
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                // User chose the "Settings" item, show the app settings UI...
                return true;

            case R.id.nearby_search:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intentNearby = new Intent(this, NearbySearchActivity.class);
                MainActivity.currentIntent = intentNearby;
                startActivity(intentNearby);
                return true;

            case R.id.related_search:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intentRelated = new Intent(this, RelatedSearchActivity.class);
                MainActivity.currentIntent = intentRelated;
                startActivity(intentRelated);
                return true;

            case R.id.my_places:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intentPlaces = new Intent(this, MyPlacesActivity.class);
                MainActivity.currentIntent = intentPlaces;
                startActivity(intentPlaces);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
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
                    if (place != null && place.getImageReferences() != null) {
                        Log.i(TAG, place.getImageReferences().toString());
                        place = loadImages(place);
                    }
                    return place;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private Place loadImages(Place place) {
            try {
                place.setPlaceImages(new ArrayList<Bitmap>());
                for (String reference : place.getImageReferences()) {
                    String imageURL = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400"
                            + "&photoreference=" + reference + API_KEY;
                    Log.i(TAG, imageURL);
                    InputStream inputStream = (InputStream)
                            new URL(imageURL).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Log.i(TAG, "Bitmap height = " + bitmap.getHeight());
                    place.getPlaceImages().add(bitmap);
                }
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
                currentPlace = place;
                displayInfo();
            } else {
                Log.i(TAG, "can't parse, place is null");
            }
            progressBar.setVisibility(View.INVISIBLE);
            Log.i(TAG, "task finished");
        }
    }

    private void displayInfo() {
        TextView placeName = (TextView) findViewById(R.id.placeInfo_name_tv);
        TextView placeAddress = (TextView) findViewById(R.id.placeInfo_address_tv);
        TextView placeType = (TextView) findViewById(R.id.placeInfo_placeType_tv);
        TextView placePhone = (TextView) findViewById(R.id.placeInfo_phone_tv);
        TextView placeDescription = (TextView) findViewById(R.id.placeInfo_description_tv);
        TextView openingHours = (TextView) findViewById(R.id.placeInfo_opening_hours_tv);
        TextView reviews = (TextView) findViewById(R.id.placeInfo_reviews_tv);

        placeName.setText("Name: " + currentPlace.getName());
        placeAddress.setText("Address: " + currentPlace.getAddress());
        placeType.setText("Place type: " + currentPlace.getMainType());
        placePhone.setText("Phone: " + currentPlace.getPhoneNumber());
        if (currentPlace.getPlaceImages() != null) {
            int height = getAverageSize(currentPlace);
            Log.i(TAG, currentPlace.getPlaceImages().size() + "");
            LinearLayout imagesHolder = (LinearLayout) findViewById(R.id.placeInfo_images_list);
            for (Bitmap image : currentPlace.getPlaceImages()) {

                ImageView imageView = new ImageView(this);
                imageView.setImageBitmap(image);
                imageView.setLayoutParams(new ActionBar.LayoutParams(height + 250, height));
                imagesHolder.addView(imageView);
            }
        }
        if (!currentPlace.getDescription().equals("Not available")) {
            placeDescription.setText("Description: " + currentPlace.getDescription());
        }
        if (currentPlace.getOpeningHours() != null) {
            openingHours.setText("Opening hours: \n\n" + currentPlace.getOpeningHours());
        }
        if (currentPlace.getReviews() != null) {
            reviews.setText("Reviews: \n\n" + currentPlace.getReviews());
        }
    }

    private int getAverageSize(Place place) {
        int totalHeight = 0;
        for (Bitmap image : place.getPlaceImages()) {
            totalHeight += image.getHeight();
        }
        return (int) totalHeight / place.getPlaceImages().size();
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

    public void saveButtonOnClick(View view) {
        currentPlace.setUsername(LogInActivity.user);
        PlacesDataSource placesDataSource = new PlacesDataSource(this);
        placesDataSource.create(currentPlace);
        Toast.makeText(this, "Place Saved", Toast.LENGTH_LONG).show();
    }




}

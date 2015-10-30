package controller;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hieunguyen725.myplaces.R;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

import database.PlacesDataSource;
import model.Place;

public class MyPlacesActivity extends AppCompatActivity {

    public static final String TAG = "MyPlacesActivity";

    private PlacesDataSource placesDataSource;

    private ProgressBar progressBar;

    private List<Place> currentPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.myPlaces_progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        placesDataSource = new PlacesDataSource(this);
        loadPlaceData();
    }

    private void loadPlaceData() {
        currentPlaces = placesDataSource.findUserPlaces("username = " + "'" + LogInActivity.user + "'");
        if (!isOnline()) {
            Toast.makeText(this, "No network connection", Toast.LENGTH_LONG).show();
        } else if (currentPlaces.size() > 0) {
            GetIcons task = new GetIcons();
            task.execute(currentPlaces);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPlaceData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_places, menu);
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


    private class GetIcons extends AsyncTask<List<Place>, String, List<Place>> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Starting task");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Place> doInBackground(List<Place>... params) {
            List<Place> places = params[0];
            if (places != null && places.size() > 0) {
                Log.i(TAG, "Places size = " + places.size());
//                return getIcons(places);
                return places;
            } else {
                Log.i(TAG, "Place is null");
            }
            return null;
        }

        private List<Place> getIcons(List<Place> places) {
            Log.i(TAG, "Get icons called with places size of " + places.size());
            for (Place place : places) {
                Log.i(TAG, "" + place.getName() + " called");
                try {
                    Log.i(TAG, "" + place.getName() + " called in try catch");
                    Log.i(TAG, "" + place.getIconURL());
                    String iconURL = place.getIconURL();
                    InputStream inputStream = (InputStream)
                            new URL(iconURL).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    Log.i(TAG, "Place " + place.getName() + " has icon height of " + bitmap.getHeight());
                    place.setIcon(bitmap);
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return places;
        }

        @Override
        protected void onPostExecute(List<Place> places) {
            if (places != null) {
                currentPlaces = places;
//                Log.i(TAG, places.toString());
                displayList();
            } else {
                Log.i(TAG, "can't parse, places is null");
            }
            progressBar.setVisibility(View.INVISIBLE);
            Log.i(TAG, "task finished");
        }
    }

    private void displayList() {
        ListAdapter listAdapter = new MyAdapter(this, currentPlaces);
        ListView listView = (ListView) findViewById(R.id.myPlaces_listView);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = (Place) parent.getItemAtPosition(position);

                // View page info activity
                Intent intent = new Intent(MyPlacesActivity.this, PlaceInfoActivity.class);
                intent.putExtra("placeID", place.getPlaceID());
                intent.putExtra("placeSource", place.getContentResource());
                if (place.getContentResource().equals("userContent")) {
                    intent.putExtra("placeName", place.getName());
                    intent.putExtra("placeAddress", place.getAddress());
                    intent.putExtra("placeType", place.getMainType());
                    intent.putExtra("placePhone", place.getPhoneNumber());
                    intent.putExtra("placeDescription", place.getDescription());
                }
                startActivity(intent);
            }
        });
    }


    public void addPlaceOnClick(View view) {
        Intent intent = new Intent(this, AddPlaceActivity.class);
        startActivity(intent);
    }


}

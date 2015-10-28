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
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hieunguyen725.myplaces.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import model.Place;
import parsers.JSONParser;

public class RelatedSearchActivity extends AppCompatActivity {

    public static final String TAG = "RelatedSearchActivity";
    private static final String API_KEY = "&key=AIzaSyCYoO7HjswFyU9zNWR7kP_kJoWs_IlQIuI";
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/textsearch/json?";

    private ProgressBar progressBar;
    private EditText searchPhrase;
    private ListView listview;

    private static List<Place> currentPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_related_search);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar = (ProgressBar) findViewById(R.id.relatedSearch_progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }

    protected void onResume() {
        super.onResume();
        if (currentPlaces != null) {
            displayList();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_related_search, menu);
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
                startActivity(intentNearby);
                return true;

            case R.id.related_search:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intentRelated = new Intent(this, RelatedSearchActivity.class);
                startActivity(intentRelated);
                return true;

            case R.id.my_places:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent intentPlaces = new Intent(this, MyPlacesActivity.class);
                startActivity(intentPlaces);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    public void searchButtonOnClick(View view) {
        if (isOnline()) {
            searchPhrase = (EditText) findViewById(R.id.relatedSearch_searchPhrase);
            if (searchPhrase.getText().toString().equals("")) {
                Toast.makeText(this, "Invalid search phrase", Toast.LENGTH_LONG).show();
            } else {
                String queryText = "query=" + searchPhrase.getText().toString().replace(" ", "%20");
                String URL = BASE_URL + queryText +  API_KEY;
                SearchTask task = new SearchTask();
                task.execute(URL);
            }
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

    private class SearchTask extends AsyncTask<String, String, List<Place>> {

        @Override
        protected void onPreExecute() {
            Log.i(TAG, "Starting task");
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Place> doInBackground(String... params) {
            ConnectionManager connectionManager = new ConnectionManager();
            String content = null;
            try {
                content = connectionManager.getData(params[0]);
                if (content != null) {
                    List<Place> places = new JSONParser().searchParse(content, "related");
                    if (places != null) {
                        places = getIcons(places);
                    }
                    return places;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        private List<Place> getIcons(List<Place> places) {
            for (Place place : places) {
                try {
                    String iconURL = place.getIconURL();
                    InputStream inputStream = (InputStream)
                            new URL(iconURL).getContent();
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
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
        ListView listView = (ListView) findViewById(R.id.relatedSearch_viewlist);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = (Place) parent.getItemAtPosition(position);
                String placePicked = "You selected " + place.getMainType();
                Toast.makeText(RelatedSearchActivity.this, placePicked, Toast.LENGTH_SHORT).show();

                // View page info activity
                Intent intent = new Intent(RelatedSearchActivity.this, PlaceInfoActivity.class);
                intent.putExtra("placeID", place.getPlaceID());
                startActivity(intent);
            }
        });
    }
}

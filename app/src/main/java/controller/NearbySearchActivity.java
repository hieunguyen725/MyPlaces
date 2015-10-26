package controller;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.hieunguyen725.myplaces.R;

import java.io.IOException;
import java.util.List;

import model.Place;
import parsers.JSONParser;

public class NearbySearchActivity extends AppCompatActivity {

    public static final String TAG = "NearbySearchActivity";
    private static final String API_KEY = "&key=AIzaSyCYoO7HjswFyU9zNWR7kP_kJoWs_IlQIuI";
    private static final String BASE_URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";

    private ProgressBar progressBar;
    private EditText keyword;
    private EditText radius;
    private ListView listView;

    private Location myLocation;

    private List<Place> currentPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_search);
        getSupportActionBar().hide();

        progressBar = (ProgressBar) findViewById(R.id.nearbySearch_progressBar);
        progressBar.setVisibility(View.INVISIBLE);

        checkPermission();
    }

    private void getLocation() {
        if(checkPermission()) {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            final LocationListener locationListener = new LocationListener() {
                public void onLocationChanged(Location location) {
                    myLocation = location;
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                    Log.i(TAG, "location status changed");
                }

                @Override
                public void onProviderEnabled(String provider) {
                    Log.i(TAG, "location provider enabled");
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Log.i(TAG, "location provider disabled");
                }
            };
            if (locationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)
                    && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 20, locationListener);
            }
        }
    }

    /**
     * Required runtime permission check for android 6.0 or API 23 and higher
     */
    public boolean checkPermission() {
        int gpsPermission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (gpsPermission == PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "permissions granted");
            return true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_nearby_search, menu);
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


    public void searchButtonOnClick(View view) {
        if (isOnline()) {
            getLocation();
            if (myLocation != null) {
                keyword = (EditText) findViewById(R.id.nearbySearch_keyword);
                radius = (EditText) findViewById(R.id.nearbySearch_radius);
                if (keyword.getText().toString().equals("") || radius.getText().toString().equals("")) {
                    Toast.makeText(this, "Invalid Keyword/Radius", Toast.LENGTH_LONG).show();
                } else {
                    String locationText = "&location=" + myLocation.getLatitude() + "," + myLocation.getLongitude();
                    double metersPerMile = 1609.34;
                    int myRadius = (int) (Double.parseDouble(radius.getText().toString()) * metersPerMile);
                    String radiusText = "&radius=" + myRadius;
                    String keywordText = "&keyword=" + keyword.getText().toString().replace(" ", "_");
                    String URL = BASE_URL + locationText + radiusText + keywordText + API_KEY;
                    SearchTask task = new SearchTask();
                    task.execute(URL);
                }
            } else {
                Toast.makeText(this, "Device location is not enabled", Toast.LENGTH_LONG).show();
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
                    return new JSONParser().nearbySearchParse(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<Place> places) {
            if (places != null) {
                currentPlaces = places;
                Log.i(TAG, places.toString());
                displayList();
            } else {
                Log.i(TAG, "can't parse, places is null");
            }
            progressBar.setVisibility(View.INVISIBLE);
            Log.i(TAG, "task finished");
        }
    }

    public void displayList() {
        ListAdapter listAdapter = new ArrayAdapter<Place>(this, android.R.layout.simple_list_item_1,
                currentPlaces);
        ListView listView = (ListView) findViewById(R.id.nearbySearch_listview);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Place place = (Place) parent.getItemAtPosition(position);
                String placePicked = "You selected " + place.getName();
                Toast.makeText(NearbySearchActivity.this, placePicked, Toast.LENGTH_SHORT).show();
            }
        });
    }
}

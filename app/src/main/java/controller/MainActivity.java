package controller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hieunguyen725.myplaces.R;

import java.util.List;

import database.PlacesDataSource;
import model.Place;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";

//    protected static String user;
    protected static Intent currentIntent;

    PlacesDataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        if (currentIntent == null) {
            Intent intentNearby = new Intent(this, NearbySearchActivity.class);
            startActivity(intentNearby);
        } else {
            startActivity(currentIntent);
        }


//        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
//        TabHost tabHost = getTabHost();
//
//        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
//        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
//        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third Tab");
//
//        tab1.setIndicator("Nearby Search");
//        tab2.setIndicator("Related Search");
//        tab3.setIndicator("My Places");
//
//        tab1.setContent(new Intent(this, NearbySearchActivity.class));
//        tab2.setContent(new Intent(this, RelatedSearchActivity.class));
//        tab3.setContent(new Intent(this, MyPlacesActivity.class));
//
//        tabHost.addTab(tab1);
//        tabHost.addTab(tab2);
//        tabHost.addTab(tab3);

        testDB();
    }

    public void testDB() {
        dataSource = new PlacesDataSource(this);
        createData();
    }

    private void createData() {
//        Place place = new Place("id = 3", "Test Location 3", "1234 44th ave s", "local address", -11.0, 11.0);
//        place.setUsername("Hieu");
//        dataSource.create(place);
//        place = new Place("id = 4", "Test Location 4", "4321 99th ave s", "local address", -101.0, 1111.0);
//        place.setUsername("Hieu");
//        dataSource.create(place);
//        place = new Place("id = 5", "Test Location 5", "4321 99th ave s", "local address", -101.0, 1111.0);
//        place.setUsername("Nguyen");
//        dataSource.create(place);
        List<Place> places = dataSource.findUserPlaces("username = 'Hieu'");
        Log.i(TAG, places.toString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}

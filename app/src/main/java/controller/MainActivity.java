package controller;

import android.Manifest;
import android.app.TabActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.hieunguyen725.myplaces.R;

import java.util.List;

import database.PlacesDataSource;
import model.Place;

public class MainActivity extends TabActivity {

    public static final String TAG = "MainActivity";

    PlacesDataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);
//        TabHost tabHost = getTabHost();
//
        TabHost.TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabHost.TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabHost.TabSpec tab3 = tabHost.newTabSpec("Third Tab");

        tab1.setIndicator("Nearby Search");
        tab2.setIndicator("Related Search");
        tab3.setIndicator("My Places");

        tab1.setContent(new Intent(this, NearbySearchActivity.class));
        tab2.setContent(new Intent(this, RelatedSearchActivity.class));
        tab3.setContent(new Intent(this, MyPlacesActivity.class));

        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);

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
}

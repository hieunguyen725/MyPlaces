package controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.hieunguyen725.myplaces.R;

public class MyPlacesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_places);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

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
}

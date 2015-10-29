package controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hieunguyen725.myplaces.R;

import database.PlacesDataSource;
import model.Place;

public class AddPlaceActivity extends AppCompatActivity {

    public static final String ICON_URL = "https://maps.gstatic.com/mapfiles/place_api/icons/geocode-71.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_place, menu);
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

    public void saveButtonOnClick(View view) {
        EditText nameInput = (EditText) findViewById(R.id.addPlace_name_input);
        EditText addressInput = (EditText) findViewById(R.id.addPlace_address_input);
        EditText typeInput = (EditText) findViewById(R.id.addPlace_type_input);
        EditText phoneInput = (EditText) findViewById(R.id.addPlace_phone_input);
        EditText descriptionInput = (EditText) findViewById(R.id.addPlace_description_input);

        String name = nameInput.getText().toString();
        String address = addressInput.getText().toString();
        String type = typeInput.getText().toString();
        String phone = phoneInput.getText().toString();
        String description = descriptionInput.getText().toString();

        if (name.equals("") || address.equals("") | type.equals("")
                || phone.equals("") || description.equals("")) {
            Toast.makeText(this, "Please fill in all inputs", Toast.LENGTH_LONG).show();
        } else {
            String placeID = "" + name.hashCode() + address.hashCode() + type.hashCode()
                    + phone.hashCode() + description.hashCode();
            Place place = new Place(placeID, LogInActivity.user, name, address, type,
                    ICON_URL, description, phone);
            place.setContentResource("userContent");
            PlacesDataSource placesDataSource = new PlacesDataSource(this);
            placesDataSource.create(place);
            Toast.makeText(this, "Place Saved", Toast.LENGTH_LONG).show();
            finish();
        }

    }
}

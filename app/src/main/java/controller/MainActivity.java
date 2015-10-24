package controller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;

import com.example.hieunguyen725.myplaces.R;

import database.UserDataSource;

public class MainActivity extends TabActivity {

    public static final String TAG = "MainActivity";

    UserDataSource dataSource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
//        dataSource = new UserDataSource(this);
//        createData();
    }

    private void createData() {
//        User user = new User("Hieu", "password1");
//        dataSource.create(user);
//        user = new User("Trung", "password2");
//        dataSource.create(user);
//        user = new User("Nguyen", "password3");
//        dataSource.create(user);
//        List<User> users = dataSource.findAll();
//        Log.i(TAG, users.toString());
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        dataSource.open();
//    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        dataSource.close();
//    }

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

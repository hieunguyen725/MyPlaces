package controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hieunguyen725.myplaces.R;

import java.util.List;

import database.UserDataSource;
import model.User;

public class LogInActivity extends AppCompatActivity {
    protected static String user;

    private EditText username;
    private EditText password;
    private Button loginButton;
    private Button createAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_log_in, menu);
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

    /**
     * Validate the user's username and password from the database once
     * the login button is clicked.
     * @param view
     */
    public void loginOnClick(View view) {
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        UserDataSource dataSource = new UserDataSource(this);
        List<User> users = dataSource.findAll();
        boolean validUser = false;
        for (User user : users) {
            if (user.getUsername().equals(username.getText().toString())
                    && user.getPassword().equals(password.getText().toString())) {
                validUser = true;

            }
        }
        if (validUser) {
            user = username.getText().toString();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("username", username.getText().toString());
            Toast.makeText(this, "Logging in", Toast.LENGTH_LONG).show();
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Incorrect Username/Password", Toast.LENGTH_LONG).show();
        }
    }

    public void createAccountOnClick(View view) {
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        username.setText("");
        password.setText("");
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}

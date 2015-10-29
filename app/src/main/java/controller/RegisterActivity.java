package controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hieunguyen725.myplaces.R;

import java.util.List;

import database.UserDataSource;
import model.User;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private EditText confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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


    public void createButtonOnClick(View view) {
        username = (EditText) findViewById(R.id.register_username);
        password = (EditText) findViewById(R.id.register_password);
        confirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        if (password.getText().toString().equals("") ||
                confirmPassword.getText().toString().equals("") ||
                username.getText().toString().equals("")) {
            Toast.makeText(this, "Please fill in all inputs", Toast.LENGTH_LONG).show();
        } else if (username.getText().toString().length() < 5 || password.getText().toString().length() < 5) {
            Toast.makeText(this, "Username/Password must be at least 5 characters", Toast.LENGTH_LONG).show();
        } else if (!password.getText().toString().equals(confirmPassword.getText().toString())) {
            Toast.makeText(this, "Password/Confirm Password do not match", Toast.LENGTH_LONG).show();
        } else {
            UserDataSource dataSource = new UserDataSource(this);
            List<User> users = dataSource.findAll();
            boolean validAccount = true;
            for (User user : users) {
                if (user.getUsername().equals(username.getText().toString())) {
                    validAccount = false;
                }
            }
            if (validAccount) {
                dataSource.create(new User(username.getText().toString(),
                        password.getText().toString()));
                finish();
                Toast.makeText(this, "Account created, please log in.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Username already exist", Toast.LENGTH_LONG).show();
            }
        }
    }
}

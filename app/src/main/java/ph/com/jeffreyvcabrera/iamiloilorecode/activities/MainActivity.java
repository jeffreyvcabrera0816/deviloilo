package ph.com.jeffreyvcabrera.iamiloilorecode.activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONObject;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LiveAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LocalAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Md5;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements AsyncTaskListener{
    boolean isChecked;
    String userEmail;
    String userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        UsersModel um = new SharedPrefManager(MainActivity.this).userGet();

        if (um.getLogged()) {

            new LiveAPI(MainActivity.this, MainActivity.this, false).execute("POST", "/api_users/login2/1/"+um.getEmail()+"/"+ um.getPassword());
        } else {

            Intent intent = new Intent(MainActivity.this, Welcome.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void onTaskComplete(String result) {
        try {
            JSONObject jObj = new JSONObject(result);
            boolean success = jObj.getBoolean("success");

            if (success) {
                UsersModel um = new UsersModel();

                JSONObject activity_data = jObj.optJSONObject("user_data");

                Integer id = activity_data.optInt("id");
                String email = activity_data.optString("email");
                String firstname = activity_data.optString("firstname");
                String lastname = activity_data.optString("lastname");
                String mobile = activity_data.optString("mobile");
                String birthdate = activity_data.optString("birthdate");

                um.setId(id);
                um.setEmail(email);
                um.setFirstname(firstname);
                um.setLastname(lastname);
                um.setBirthdate(birthdate);
                um.setMobile(mobile);
                um.setPassword(um.getPassword());
                um.setLogged(true);

                SharedPrefManager sm = new SharedPrefManager(MainActivity.this);
                sm.userSaverModel(um);

                Intent intent = new Intent(MainActivity.this, SwipeActivity.class);
                startActivity(intent);
                finish();
            }
            else{
                Intent intent = new Intent(MainActivity.this, Welcome.class);
                startActivity(intent);
            }
        }
        catch (Exception e){

        }
    }
}

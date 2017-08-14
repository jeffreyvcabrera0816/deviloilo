package ph.com.jeffreyvcabrera.iamiloilorecode.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Md5;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.SharedPrefManager;

/**
 * Created by Jeffrey on 3/14/2017.
 */

public class Welcome extends AppCompatActivity {

    RelativeLayout option_local;
    RelativeLayout option_tourist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.welcome_screen);

        option_local = (RelativeLayout) findViewById(R.id.wc_local_relative);
        option_tourist = (RelativeLayout) findViewById(R.id.wc_tourist_relative);

        option_local.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Welcome.this, LoginPage.class);
                startActivity(intent);
                finish();
            }
        });

        option_tourist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UsersModel um = new UsersModel();

                um.setId(0);
                um.setLogin_type(0);
                um.setEmail("");
                um.setFirstname("Tourist");
                um.setLastname("Tourist");
                um.setBirthdate("");
                um.setMobile("");
                um.setPassword("");
                um.setLogged(false);

                SharedPrefManager sm = new SharedPrefManager(Welcome.this);
                sm.userSaverModel(um);

                Intent intent = new Intent(Welcome.this, SwipeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

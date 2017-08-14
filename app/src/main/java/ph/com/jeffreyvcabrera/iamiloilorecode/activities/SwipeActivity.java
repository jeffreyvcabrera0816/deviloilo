package ph.com.jeffreyvcabrera.iamiloilorecode.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.TabFragment;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.MyCoupons;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.PrimaryFragment;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.TermsAndConditions;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.SharedPrefManager;

public class SwipeActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
    NavigationView mNavigationView;
    FragmentManager mFragmentManager;
    FragmentTransaction mFragmentTransaction;
    UsersModel um;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swipe_activity);

        /**
         *Setup the DrawerLayout and NavigationView
         */
//        um.getEmail();
//        Log.d("firstnametest",String.valueOf(um.getFirstname()));
        um = new SharedPrefManager(SwipeActivity.this).userGet();
//        Toast.makeText(this, String.valueOf(um.getEmail()), Toast.LENGTH_SHORT).show();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mNavigationView = (NavigationView) findViewById(R.id.shitstuff) ;

        Menu menu = mNavigationView.getMenu();
//        if (um.getId() == 0) {
//            menu.findItem(R.id.nav_item_coupons).setVisible(false);
//            menu.findItem(R.id.nav_item_profile).setVisible(false);
//        }

        /**
         * Lets inflate the very first fragment
         * Here , we are inflating the TabFragment as the first Fragment
         */

        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();
        /**
         * Setup click events on the Navigation View Items.
         */

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();



                if (menuItem.getItemId() == R.id.main) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new TabFragment()).commit();

                }

//                if (menuItem.getItemId() == R.id.nav_item_profile) {
//                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//                    fragmentTransaction.replace(R.id.containerView,new PrimaryFragment()).commit();
//                    TextView title = (TextView) findViewById(R.id.toolbar_title);
//                    title.setText("My Profile");
//                }

                if (menuItem.getItemId() == R.id.nav_item_coupons) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new MyCoupons()).commit();
                    TextView title = (TextView) findViewById(R.id.toolbar_title);
                    title.setText("My Coupons");
                }

                if (menuItem.getItemId() == R.id.nav_item_policy) {
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.containerView,new TermsAndConditions()).commit();
                    TextView title = (TextView) findViewById(R.id.toolbar_title);
                    title.setText("Privacy Policy");
                }

                if (menuItem.getItemId() == R.id.nav_logout) {

                    LayoutInflater li = LayoutInflater.from(SwipeActivity.this);
                    View promptsView = li.inflate(R.layout.prompt_logout, null);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            SwipeActivity.this);

                    alertDialogBuilder.setView(promptsView);

                    final TextView serial = (TextView) promptsView.findViewById(R.id.message_mid);

                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Logout",
                                    new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {

                                                SharedPrefManager sm = new SharedPrefManager(SwipeActivity.this);
                                                sm.clearUser();

//                                                if (um.getLogin_type() == 2) {
                                                    LoginManager.getInstance().logOut();    //facebook sign out
//                                                }

                                                Intent intent = new Intent(SwipeActivity.this, MainActivity.class);
                                                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                        }
                                    });

                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        } });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }

                return false;
            }

        });

        /**
         * Setup Drawer Toggle of the Toolbar
         */

        Toolbar toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout, toolbar,R.string.app_name,
                R.string.app_name);

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        mDrawerToggle.syncState();

    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
//        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
//            return;
//        }
//
//        this.doubleBackToExitPressedOnce = true;
//        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
//
//        new Handler().postDelayed(new Runnable() {
//
//            @Override
//            public void run() {
//                doubleBackToExitPressedOnce=false;
//            }
//        }, 2000);
    }

}

package ph.com.jeffreyvcabrera.iamiloilorecode.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.HotelsListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.News;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.HotelsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ListViewAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LiveAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LocalAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Md5;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.SharedPrefManager;

public class PromoPage extends AppCompatActivity implements AsyncTaskListener {
    private AQuery aq;
    TextView price, date_end, coupons_left, discounted_price, discount, title, description;
    EditText quantity;
    Button buy_button;
    UsersModel um;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.catalogue_page_activity);

        um = new SharedPrefManager(PromoPage.this).userGet();

        this.aq = new AQuery(PromoPage.this);
        String imgaq = Settings.base_url + "/assets/images/coupons/" + getIntent().getExtras().getString("image") ;
        aq.id(R.id.promo_image).image(imgaq, true, true);

        quantity = (EditText) findViewById(R.id.quantity);
        title = (TextView) findViewById(R.id.title);
        price = (TextView) findViewById(R.id.promo_price);
        description = (TextView) findViewById(R.id.promo_description);
        discount = (TextView) findViewById(R.id.promo_discount);
        discounted_price = (TextView) findViewById(R.id.promo_discount_price);
        coupons_left = (TextView) findViewById(R.id.coupons_left);
        buy_button = (Button) findViewById(R.id.buy_button);

        title.setText(getIntent().getExtras().getString("title"));
        price.setText("P" + getIntent().getExtras().getString("discounted_price"));
        description.setText(getIntent().getExtras().getString("description"));
        discount.setText("Save " + getIntent().getExtras().getString("discount") + "% off");
        discounted_price.setText("P" + getIntent().getExtras().getString("price"));
        coupons_left.setText("Only " + getIntent().getExtras().getString("coupons_left") + " tickets left");

        final Typeface Antonio_regular = Typeface.createFromAsset(PromoPage.this.getAssets(), "Antonio-Regular.ttf");
        buy_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                LayoutInflater li = LayoutInflater.from(PromoPage.this);
                View promptsView = li.inflate(R.layout.prompts, null);
                CheckBox chkbox = (CheckBox) promptsView.findViewById(R.id.chkbox);
                chkbox.setTypeface(Antonio_regular);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                        PromoPage.this);

                alertDialogBuilder.setView(promptsView);

                final TextView serial = (TextView) promptsView.findViewById(R.id.message_mid);
                serial.setText(randomStringOfLength(7).toUpperCase());

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OKAY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
//                                        Intent intent = new Intent(PromoPage.this, SwipeActivity.class);
//                                        startActivity(intent);
//                                        new LiveAPI(PromoPage.this, PromoPage.this, false).execute("POST", "/api_catalogue/buycoupon/"+um.getId()+"/"+getIntent().getExtras().getString("id")+"/"+quantity.getText());
                                        new ListViewAPI(PromoPage.this, PromoPage.this, Settings.base_url+"/api_catalogue/buycoupon/"+um.getId()+"/"+getIntent().getExtras().getString("id")+"/"+quantity.getText());
                                        finish();
                                    }
                                });

                AlertDialog alertDialog = alertDialogBuilder.create();

                alertDialog.show();

            }
        });

    }

    public static String randomStringOfLength(int length) {
        StringBuffer buffer = new StringBuffer();
        while (buffer.length() < length) {
            buffer.append(uuidString());
        }

        //this part controls the length of the returned string
        return buffer.substring(0, length);
    }


    private static String uuidString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public void onTaskComplete(String result) {
            JSONObject jObj = null;
        try {
            jObj = new JSONObject(result);
            boolean success = jObj.getBoolean("success");

            if (success) {
                String message = jObj.getString("msg");

                Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            } else {

                String message = jObj.getString("error");

                Toast.makeText(this, message, Toast.LENGTH_LONG).show();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

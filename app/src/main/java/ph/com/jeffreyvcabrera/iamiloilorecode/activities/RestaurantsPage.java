package ph.com.jeffreyvcabrera.iamiloilorecode.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.RestaurantImagesAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.RestaurantImages;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ListViewAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LocalAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

public class RestaurantsPage extends AppCompatActivity implements AsyncTaskListener {

    ImageView image;
    TextView name, address, contact, open_hours, description;
    private AQuery aq;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_page_activity_list);
        new ListViewAPI(this, this, Settings.base_url+"/api_restaurants/images/"+getIntent().getExtras().getInt("id"));
        initView();
    }

    public void initView() {
        this.aq = new AQuery(this);
        listView = (ListView) findViewById(R.id.CarouselRooms);
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.restaurant_page_activity_header, listView,false);
        ViewGroup footerView = (ViewGroup)getLayoutInflater().inflate(R.layout.restaurant_page_activity_footer, listView,false);
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
        image = (ImageView) findViewById(R.id.rest_image);
        name = (TextView) findViewById(R.id.name);
        address = (TextView) findViewById(R.id.address);
        open_hours = (TextView) findViewById(R.id.opens);
        contact = (TextView) findViewById(R.id.tell_no);
        description = (TextView) findViewById(R.id.description);

        String imgaq = Settings.base_url + "/assets/images/restaurants/" + getIntent().getExtras().getString("image");
        aq.id(R.id.rest_image).image(imgaq, false, true);

        description.setText(getIntent().getExtras().getString("description"));
        name.setText(getIntent().getExtras().getString("name"));
        address.setText(getIntent().getExtras().getString("address"));
        open_hours.setText(getIntent().getExtras().getString("open_hours"));
        contact.setText(getIntent().getExtras().getString("contact"));

    }

    @Override
    public void onTaskComplete(String result) {
        Log.d("ssss", result);
        ArrayList<RestaurantImages> sampleModel = new ArrayList<>();
        try {
            JSONObject jObj = new JSONObject(result);
            boolean success = jObj.getBoolean("success");

            if (success) {

                JSONArray activities_data = jObj.optJSONArray("data");
                for(int x=0; x<activities_data.length();x++){
                    JSONObject a = activities_data.getJSONObject(x);
                    RestaurantImages cma1 = new RestaurantImages();
                    String image = a.optString("name");
                    cma1.setImage(Settings.base_url+"/assets/images/restaurants/"+image);
                    sampleModel.add(cma1);

                }
                RestaurantImagesAdapter restaurantImagesAdapter = new RestaurantImagesAdapter(this, sampleModel);
                listView.setAdapter(restaurantImagesAdapter);

            }

        } catch (JSONException e) {
            Log.e("error", "Error parsing data" + e.toString());
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

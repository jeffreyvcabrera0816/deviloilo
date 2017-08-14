package ph.com.jeffreyvcabrera.iamiloilorecode.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.HotelRoomsAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.HotelsListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.fragments.Hotels;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.HotelRoomsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.RestaurantImages;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ListViewAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

public class HotelsPage extends AppCompatActivity implements AsyncTaskListener {

    ListView listView;
    ImageView image;
    TextView name, address, contact, open_hours, description;
    private AQuery aq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hotel_page_activity_list);

        initView();

    }

    public void initView() {
        this.aq = new AQuery(this);
        listView = (ListView) findViewById(R.id.listviewRooms);
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.hotel_page_activity_header, listView,false);
        ViewGroup footerView = (ViewGroup)getLayoutInflater().inflate(R.layout.hotel_page_activity_footer, listView,false);
        image = (ImageView) headerView.findViewById(R.id.rest_image);
        name = (TextView) headerView.findViewById(R.id.name);
        address = (TextView) headerView.findViewById(R.id.address);
        open_hours = (TextView) headerView.findViewById(R.id.opens);
        contact = (TextView) headerView.findViewById(R.id.tell_no);
        description = (TextView) footerView.findViewById(R.id.description);
        listView.addHeaderView(headerView);
        listView.addFooterView(footerView);
        String imgaq = Settings.base_url + "/assets/images/hotels/" + getIntent().getExtras().getString("image");
        aq.id(R.id.rest_image).image(imgaq, false, true);

        description.setText(getIntent().getExtras().getString("description"));
        name.setText(getIntent().getExtras().getString("name"));
        address.setText(getIntent().getExtras().getString("address"));
        open_hours.setText(getIntent().getExtras().getString("open_hours"));
        contact.setText(getIntent().getExtras().getString("contact"));
        Integer id = getIntent().getExtras().getInt("id");

        new ListViewAPI(HotelsPage.this, HotelsPage.this, Settings.base_url+"/api_hotels/rooms/"+id);
    }

    @Override
    public void onTaskComplete(String result) {
        Log.d("test", result);
        final ArrayList<HotelRoomsModel> roomsModel = new ArrayList<>();
        try {
            JSONObject jObj = new JSONObject(result);
            boolean success = jObj.getBoolean("success");

            if (success) {

                JSONArray activities_data = jObj.optJSONArray("data");
                for(int x=0; x<activities_data.length();x++){
                    JSONObject a = activities_data.getJSONObject(x);
                    HotelRoomsModel cma1 = new HotelRoomsModel();
                    cma1.setId(a.getInt("id"));
                    cma1.setHotelId(a.getInt("hotel_id"));
                    cma1.setName(a.getString("name"));
                    cma1.setImage(Settings.base_url+"/assets/images/hotels/"+a.getString("image"));

                    roomsModel.add(cma1);

                }

                HotelRoomsAdapter hotelRoomsAdapter = new HotelRoomsAdapter(HotelsPage.this, roomsModel);
                listView.setAdapter(hotelRoomsAdapter);

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

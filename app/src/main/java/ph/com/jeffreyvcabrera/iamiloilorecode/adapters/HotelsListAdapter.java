package ph.com.jeffreyvcabrera.iamiloilorecode.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.HotelsPage;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.RestaurantsPage;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.HotelsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.RestaurantsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

/**
 * Created by Jeffrey on 2/19/2017.
 */

public class HotelsListAdapter extends BaseAdapter {
    ImageView image;
    TextView name, address, contact, open_hours;
    RelativeLayout row_restaurant;
    private AQuery aq;
    Context c;
    ArrayList<HotelsModel> cm;
    private LayoutInflater inflater;

    public HotelsListAdapter(Context c, ArrayList<HotelsModel> cm) {
        this.c = c;
        this.cm = cm;
    }

    @Override
    public int getCount() {
        return cm.size();
    }

    @Override
    public Object getItem(int i) {
        return cm.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
//        final ViewHolder holder;


        View v;
        if(convertView==null){

            LayoutInflater li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.hotels_row, viewGroup, false);

        }else {
            v=convertView;
        }

        this.aq = new AQuery(v);

        image = (ImageView) v.findViewById(R.id.rest_image);
        name = (TextView) v.findViewById(R.id.name);
        address = (TextView) v.findViewById(R.id.address);
        open_hours = (TextView) v.findViewById(R.id.opens);
        contact = (TextView) v.findViewById(R.id.tell_no);
        row_restaurant = (RelativeLayout) v.findViewById(R.id.row_restaurant);

        String imgaq = Settings.base_url + "/assets/images/hotels/" + cm.get(i).getImage();
        aq.id(R.id.rest_image).image(imgaq, false, true);

        name.setText(cm.get(i).getName());
        address.setText(cm.get(i).getAddress());
        open_hours.setText(cm.get(i).getOpen_hours());
        contact.setText(cm.get(i).getContact());

        row_restaurant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, HotelsPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("name", cm.get(i).getName());
                bundle.putString("address", cm.get(i).getAddress());
                bundle.putString("open_hours", cm.get(i).getOpen_hours());
                bundle.putString("contact", cm.get(i).getContact());
                bundle.putString("image", cm.get(i).getImage());
                bundle.putString("description", cm.get(i).getDescription());
                bundle.putInt("coupon_id", cm.get(i).getCoupon_id());
                bundle.putInt("id", cm.get(i).getId());
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });

        return v;
    }

}

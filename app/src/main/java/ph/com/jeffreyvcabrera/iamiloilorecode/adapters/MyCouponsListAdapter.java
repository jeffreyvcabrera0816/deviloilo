package ph.com.jeffreyvcabrera.iamiloilorecode.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.MyCouponsPage;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.PromoPage;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.CatalogueModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.MyCouponsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

/**
 * Created by Jeffrey on 2/19/2017.
 */

public class MyCouponsListAdapter extends BaseAdapter {
    ImageView image;
    TextView title, description, order_count, price, order_status;
    RelativeLayout row_coupon;
    private AQuery aq;
    Context c;
    ArrayList<MyCouponsModel> cm;
    private LayoutInflater inflater;

    public MyCouponsListAdapter(Context c, ArrayList<MyCouponsModel> cm) {
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
            v = li.inflate(R.layout.my_coupon_row, viewGroup, false);

        }else {
            v=convertView;
        }

        this.aq = new AQuery(v);

        image = (ImageView) v.findViewById(R.id.coupon_image);
        title = (TextView) v.findViewById(R.id.name);
        description = (TextView) v.findViewById(R.id.description);
        order_count = (TextView) v.findViewById(R.id.order_count);
        order_status = (TextView) v.findViewById(R.id.status);
        price = (TextView) v.findViewById(R.id.price);
        row_coupon = (RelativeLayout) v.findViewById(R.id.row_coupon);

        String imgaq = Settings.base_url + "/assets/images/coupons/" + cm.get(i).getImage();
        aq.id(R.id.coupon_image).image(imgaq, false, true);

        final double y = 100.0f;
        final double disc = cm.get(i).getDiscount();
        final double orig_price = cm.get(i).getPrice();
        final double x = orig_price-((disc/y)*orig_price);
        final Integer discounted_price = (int)x;

        String status = "";

        if (cm.get(i).getStatus() == 0) {
            status = "Cancelled";
        } else if (cm.get(i).getStatus() == 1) {
            status = "Pending";
        } else if (cm.get(i).getStatus() == 2) {
            status = "Paid";
        } else if (cm.get(i).getStatus() == 3) {
            status = "Claimed";
        }

        title.setText(cm.get(i).getName());
        description.setText(cm.get(i).getDescription());
        price.setText("Promo Price: P"+discounted_price.toString());
        order_count.setText("Order Count: "+cm.get(i).getOrder_count().toString());
        order_status.setText("Order Status: "+status);

        return v;
    }

}

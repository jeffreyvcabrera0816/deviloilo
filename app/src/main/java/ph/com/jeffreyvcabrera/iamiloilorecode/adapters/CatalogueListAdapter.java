package ph.com.jeffreyvcabrera.iamiloilorecode.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.facebook.login.LoginManager;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.MainActivity;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.PromoPage;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.SwipeActivity;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.Welcome;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.CatalogueModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.MyToast;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.SharedPrefManager;

/**
 * Created by Jeffrey on 2/19/2017.
 */

public class CatalogueListAdapter extends BaseAdapter {
    ImageView image;
    TextView title, description, date_end, coupons_left, discount;
    Button buy_button;
    RelativeLayout row_coupon;
    private AQuery aq;
    Context c;
    ArrayList<CatalogueModel> cm;
    private LayoutInflater inflater;
    UsersModel um;

    public CatalogueListAdapter(Context c, ArrayList<CatalogueModel> cm) {
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
            v = li.inflate(R.layout.catalogue_row, viewGroup, false);

        }else {
            v=convertView;
        }
        um = new SharedPrefManager(c).userGet();

        this.aq = new AQuery(v);

        image = (ImageView) v.findViewById(R.id.coupon_image);
        title = (TextView) v.findViewById(R.id.title);
        description = (TextView) v.findViewById(R.id.description);
        coupons_left = (TextView) v.findViewById(R.id.coupon_count);
        date_end = (TextView) v.findViewById(R.id.coupon_date_end);
        discount = (TextView) v.findViewById(R.id.discount);
        row_coupon = (RelativeLayout) v.findViewById(R.id.row_coupon);
        buy_button = (Button) v.findViewById(R.id.buy_now);

        String imgaq = Settings.base_url + "/assets/images/coupons/" + cm.get(i).getImage();
        aq.id(R.id.coupon_image).image(imgaq, false, true);

        title.setText(cm.get(i).getName());
        description.setText(cm.get(i).getDescription());
        date_end.setText("Promo until: "+cm.get(i).getPromo_date());
        coupons_left.setText("Coupons Left: "+cm.get(i).getCoupons_left().toString());
        discount.setText(cm.get(i).getDiscount().toString()+"%");

        final double y = 100.0f;
        final double disc = cm.get(i).getDiscount();
        final double orig_price = cm.get(i).getPrice();
        final double x = orig_price-((disc/y)*orig_price);
        final Integer discounted_price = (int)x;
//        MyToast.showToast( c, discounted_price.toString());

        row_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (um.getId() == 0) {
                    LayoutInflater li = LayoutInflater.from(c);
                    View promptsView = li.inflate(R.layout.prompt_goto_welcome, null);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            c);

                    alertDialogBuilder.setView(promptsView);

                    final TextView serial = (TextView) promptsView.findViewById(R.id.message_mid);

                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Go to login page",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            SharedPrefManager sm = new SharedPrefManager(c);
                                            sm.clearUser();

//                                                if (um.getLogin_type() == 2) {
                                            LoginManager.getInstance().logOut();    //facebook sign out
//                                                }

                                            Intent intent = new Intent(c, Welcome.class);
                                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            c.startActivity(intent);

                                        }
                                    });

                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        } });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(c, PromoPage.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", cm.get(i).getName());
                    bundle.putString("description", cm.get(i).getDescription());
                    bundle.putString("image", cm.get(i).getImage());
                    bundle.putString("date_end", cm.get(i).getPromo_date());
                    bundle.putString("id", cm.get(i).getId().toString());
                    bundle.putString("discount", cm.get(i).getDiscount().toString());
                    bundle.putString("price", cm.get(i).getPrice().toString());
                    bundle.putString("coupons_left", cm.get(i).getCoupons_left().toString());
                    bundle.putString("discounted_price", discounted_price.toString());
                    intent.putExtras(bundle);
                    c.startActivity(intent);
                }

            }
        });

        buy_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (um.getId() == 0) {
                    LayoutInflater li = LayoutInflater.from(c);
                    View promptsView = li.inflate(R.layout.prompt_goto_welcome, null);
                    final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            c);

                    alertDialogBuilder.setView(promptsView);

                    final TextView serial = (TextView) promptsView.findViewById(R.id.message_mid);

                    alertDialogBuilder
                            .setCancelable(false)
                            .setPositiveButton("Agree",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                            SharedPrefManager sm = new SharedPrefManager(c);
                                            sm.clearUser();

//                                                if (um.getLogin_type() == 2) {
                                            LoginManager.getInstance().logOut();    //facebook sign out
//                                                }

                                            Intent intent = new Intent(c, Welcome.class);
                                            intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            c.startActivity(intent);

                                        }
                                    });

                    alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        } });

                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(c, PromoPage.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("title", cm.get(i).getName());
                    bundle.putString("description", cm.get(i).getDescription());
                    bundle.putString("image", cm.get(i).getImage());
                    bundle.putString("date_end", cm.get(i).getPromo_date());
                    bundle.putString("id", cm.get(i).getId().toString());
                    bundle.putString("discount", cm.get(i).getDiscount().toString());
                    bundle.putString("price", cm.get(i).getPrice().toString());
                    bundle.putString("coupons_left", cm.get(i).getCoupons_left().toString());
                    bundle.putString("discounted_price", discounted_price.toString());
                    intent.putExtras(bundle);
                    c.startActivity(intent);
                }
            }
        });

        return v;
    }

}

package ph.com.jeffreyvcabrera.iamiloilorecode.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.RestaurantsPage;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.HotelRoomsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.HotelsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.RestaurantImages;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.RestaurantsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Jeffrey on 2/19/2017.
 */

public class HotelRoomsAdapter extends BaseAdapter {
    Context c;
    ArrayList<HotelRoomsModel> cm;
    private LayoutInflater inflater;

    CarouselView customCarouselView;
    int[] sampleImages = {R.mipmap.placeholder};
//    String[] sampleTitles = {"Orange", "Grapes", "Strawberry", "Cherry", "Apricot"};
//    String[] twins = {
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/twin1.jpg",
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/twin2.jpg",
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/twin3.jpg"
//    };
//
//    String[] deluxe = {
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/deluxe1.jpg",
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/deluxe2.jpg",
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/deluxe3.jpg"
//    };
//
//    String[] doubles = {
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/double1.jpg",
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/double2.jpg",
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/double3.jpg"
//    };
//
//    String[] family = {
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/family1.jpg",
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/family2.jpg",
//            "http://iamiloilodev.tidalsolutions.com.ph/assets/images/hotels/family3.jpg"
//    };

    public HotelRoomsAdapter(Context c, ArrayList<HotelRoomsModel> cm) {
        this.c = c;
        this.cm = cm;
    }

    @Override
    public int getCount() {
        return 1;
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
        if (convertView == null) {

            LayoutInflater li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = li.inflate(R.layout.hotel_rooms_row, viewGroup, false);

        } else {
            v = convertView;
        }

        customCarouselView = (CarouselView) v.findViewById(R.id.customCarouselView);

        customCarouselView.setPageCount(cm.size());
        customCarouselView.setSlideInterval(4000);

        customCarouselView.setViewListener(viewListener);

        return v;
    }

    ViewListener viewListener = new ViewListener() {
        @Override
        public View setViewForPosition(int position) {
            LayoutInflater li = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View customView = li.inflate(R.layout.view_custom, null);

            TextView labelTextView = (TextView) customView.findViewById(R.id.labelTextView);
            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);

            Picasso.with(getApplicationContext()).load(cm.get(position).getImage()).placeholder(sampleImages[0]).error(sampleImages[0]).fit().centerCrop().into(fruitImageView);
            labelTextView.setText(cm.get(position).getName());

            return customView;
        }
    };

}

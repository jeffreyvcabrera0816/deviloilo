package ph.com.jeffreyvcabrera.iamiloilorecode.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ViewListener;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.HotelRoomsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.RestaurantImages;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by Jeffrey on 2/19/2017.
 */

public class RestaurantImagesAdapter extends BaseAdapter {
    Context c;
    ArrayList<RestaurantImages> cm;
    private LayoutInflater inflater;

    CarouselView customCarouselView;
    int[] sampleImages = {R.mipmap.placeholder};

    public RestaurantImagesAdapter(Context c, ArrayList<RestaurantImages> cm) {
        this.c = c;
        this.cm = cm;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int i) {
        return cm.get(0);
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
            v = li.inflate(R.layout.restaurant_rooms_row, viewGroup, false);

        }else {
            v=convertView;
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
            View customView = li.inflate(R.layout.view_custom_resto, null);
            ImageView fruitImageView = (ImageView) customView.findViewById(R.id.fruitImageView);

            Picasso.with(getApplicationContext()).load(cm.get(position).getImage()).placeholder(sampleImages[0]).error(sampleImages[0]).fit().centerCrop().into(fruitImageView);

            return customView;
        }
    };

}

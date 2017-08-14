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
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.AttractionsPage;
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.NewsPage;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.AttractionsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.NewsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;


/**
 * Created by Jeffrey on 2/19/2017.
 */

public class AttractionsListAdapter extends BaseAdapter {
    ImageView image;
    TextView title, date;
    RelativeLayout row_news;
    private AQuery aq;
    Context c;
    ArrayList<AttractionsModel> cm;
    private LayoutInflater inflater;

    public AttractionsListAdapter(Context c, ArrayList<AttractionsModel> cm) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View v;
        if (view == null) {
            inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.attractions_row, viewGroup, false);

        } else {
            v = view;
        }
        this.aq = new AQuery(v);
        String imgaq = Settings.base_url + "/assets/images/attractions/" + cm.get(i).getImage();
        aq.id(R.id.news_image).image(imgaq, false, true);

        image = (ImageView) v.findViewById(R.id.news_image);
        title = (TextView) v.findViewById(R.id.title);
//        date = (TextView) v.findViewById(R.id.date);
        row_news = (RelativeLayout) v.findViewById(R.id.row_news);

        title.setText(cm.get(i).getName());
//        date.setText("");

        row_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, AttractionsPage.class);
                Bundle bundle = new Bundle();
                bundle.putString("title", cm.get(i).getName());
                bundle.putString("description", cm.get(i).getDescription());
                bundle.putString("post_date", cm.get(i).getPost_date());
                bundle.putString("image", cm.get(i).getImage());
                intent.putExtras(bundle);
                c.startActivity(intent);
            }
        });

        return v;
    }

}

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
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.UUID;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

public class NewsPage extends AppCompatActivity {
    private AQuery aq;
    TextView date, title, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_page_activity);

        initView();

    }

    public void initView() {

        this.aq = new AQuery(NewsPage.this);
        String imgaq = Settings.base_url + "/assets/images/announcements/" + getIntent().getExtras().getString("image") ;
        aq.id(R.id.image).image(imgaq, true, true);

        title = (TextView) findViewById(R.id.title);
        date = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);

        title.setText(getIntent().getExtras().getString("title"));
        date.setText(getIntent().getExtras().getString("post_date"));
        description.setText(getIntent().getExtras().getString("description"));

    }

}

package ph.com.jeffreyvcabrera.iamiloilorecode.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.androidquery.AQuery;

import java.util.UUID;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

public class AttractionsPage extends AppCompatActivity {
    private AQuery aq;
    TextView date, title, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attractions_page_activity);
        this.aq = new AQuery(AttractionsPage.this);
        String imgaq = Settings.base_url + "/assets/images/attractions/" + getIntent().getExtras().getString("image") ;
        aq.id(R.id.image).image(imgaq, true, true);

        title = (TextView) findViewById(R.id.title);
//        date = (TextView) findViewById(R.id.date);
        description = (TextView) findViewById(R.id.description);

        title.setText(getIntent().getExtras().getString("title"));
//        date.setText("");
        description.setText(getIntent().getExtras().getString("description"));

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
}

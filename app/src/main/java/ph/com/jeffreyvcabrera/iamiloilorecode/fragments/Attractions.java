package ph.com.jeffreyvcabrera.iamiloilorecode.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.AttractionsListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.NewsListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.AttractionsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.NewsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ListViewAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

public class Attractions extends Fragment implements AsyncTaskListener, SwipeRefreshLayout.OnRefreshListener {

    ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static Attractions newInstance(Context c){

        Attractions fa = new Attractions();

        return fa;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.attractions_fragment_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeAttractions);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) v.findViewById(R.id.listview_attractions);
        init();
        return v;
    }

    public void init() {
        swipeRefreshLayout.setRefreshing(true);
        new ListViewAPI(getActivity(), Attractions.this, Settings.base_url+"/api_attractions/list/");
//        new LiveAPI(getActivity(), News.this, false).execute("POST", "/api_news/list/");
    }

    @Override
    public void onTaskComplete(String result) {
        JSONObject jObj = null;
        if (result == "error") {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        } else {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            final ArrayList<AttractionsModel> attractionsModels = new ArrayList<>();

            try {
                jObj = new JSONObject(result);
                boolean success = jObj.getBoolean("success");

                if (success) {
                    JSONArray data = jObj.optJSONArray("data");
//                Log.d("response", data.toString());
                    for (int x = 0; x < data.length(); x++) {
                        JSONObject a = data.getJSONObject(x);

                        AttractionsModel nm = new AttractionsModel();
                        nm.setImage(a.getString("image"));
                        nm.setName(a.getString("title"));
                        nm.setDescription(a.getString("description"));
                        nm.setPost_date(a.getString("post_date"));
                        nm.setId(a.getInt("id"));

                        attractionsModels.add(nm);
                    }


                    AttractionsListAdapter attractionsListAdapter = new AttractionsListAdapter(getActivity(), attractionsModels);
                    listView.setAdapter(attractionsListAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        }
                    });

                } else {

                    if (swipeRefreshLayout.isRefreshing()) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                    String message = jObj.getString("error");
                    if (message.equals("No Records Found")) {
                        listView.setAdapter(null);
//                        Toast.makeText(act, "No Activity Found", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    @Override
    public void onRefresh() {
        init();
    }
}

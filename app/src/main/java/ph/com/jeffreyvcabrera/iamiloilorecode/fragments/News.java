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
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.NewsListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.CatalogueModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.NewsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.API;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ListViewAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LiveAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.MyToast;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

public class News extends Fragment implements AsyncTaskListener, SwipeRefreshLayout.OnRefreshListener {

    ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public static News newInstance(Context c){

        News fa = new News();

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
        View v = inflater.inflate(R.layout.news_fragment_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeNews);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) v.findViewById(R.id.listview_news);
        init();
        return v;
    }

    public void init() {
        swipeRefreshLayout.setRefreshing(true);
        new ListViewAPI(getActivity(), News.this, Settings.base_url+"/api_news/list/");
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
            final ArrayList<NewsModel> newsModel = new ArrayList<>();

            try {
                jObj = new JSONObject(result);
                boolean success = jObj.getBoolean("success");

                if (success) {
                    JSONArray data = jObj.optJSONArray("data");
//                Log.d("response", data.toString());
                    for (int x = 0; x < data.length(); x++) {
                        JSONObject a = data.getJSONObject(x);

                        NewsModel nm = new NewsModel();
                        nm.setImage(a.getString("image"));
                        nm.setName(a.getString("title"));
                        nm.setDescription(a.getString("description"));
                        nm.setPost_date(a.getString("post_date"));
                        nm.setId(a.getInt("id"));

                        newsModel.add(nm);
                    }


                    NewsListAdapter newsListAdapter = new NewsListAdapter(getActivity(), newsModel);
                    listView.setAdapter(newsListAdapter);

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

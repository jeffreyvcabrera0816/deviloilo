package ph.com.jeffreyvcabrera.iamiloilorecode.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ph.com.jeffreyvcabrera.iamiloilorecode.R;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.HotelsListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.RestaurantsListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.HotelsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.RestaurantsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ListViewAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

public class Hotels extends Fragment implements AsyncTaskListener, SwipeRefreshLayout.OnRefreshListener {

    ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static Hotels newInstance(Context c){

        Hotels fa = new Hotels();

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
        View v = inflater.inflate(R.layout.hotels_fragment_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRestaurants);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) v.findViewById(R.id.listview_hotels);
        init();

        return v;
    }

    public void init() {
        swipeRefreshLayout.setRefreshing(true);
        new ListViewAPI(getActivity(), Hotels.this, Settings.base_url+"/api_hotels/list/");
//        new LiveAPI(getActivity(), Restaurants.this, false).execute("POST", "/api_restaurants/list/");
    }

    @Override
    public void onTaskComplete(String result) {

        if (result == "error") {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
        } else {
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
            }
            JSONObject jObj = null;
//            Log.d("response", result);

            final ArrayList<HotelsModel> hotelsModel = new ArrayList<>();

            try {
                jObj = new JSONObject(result);
                boolean success = jObj.getBoolean("success");

                if (success) {
                    JSONArray data = jObj.optJSONArray("data");

                    for (int x = 0; x < data.length(); x++) {
                        JSONObject a = data.getJSONObject(x);

                        HotelsModel rm = new HotelsModel();
                        rm.setImage(a.getString("image"));
                        rm.setName(a.getString("name"));
                        rm.setAddress(a.getString("address"));
                        rm.setId(a.getInt("id"));
                        rm.setContact(a.getString("contact"));
                        rm.setOpen_hours(a.getString("open_hours"));
                        rm.setDescription(a.getString("description"));
                        rm.setCoupon_id(a.getInt("coupon_id"));

                        hotelsModel.add(rm);
                    }


                    HotelsListAdapter hotelsListAdapter = new HotelsListAdapter(getActivity(), hotelsModel);
                    listView.setAdapter(hotelsListAdapter);

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

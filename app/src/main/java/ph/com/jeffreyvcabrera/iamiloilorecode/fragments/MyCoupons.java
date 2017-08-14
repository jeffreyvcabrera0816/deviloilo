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
import ph.com.jeffreyvcabrera.iamiloilorecode.activities.SwipeActivity;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.CatalogueListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.MyCouponsListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.CatalogueModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.MyCouponsModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.UsersModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ListViewAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.SharedPrefManager;

public class MyCoupons extends Fragment implements AsyncTaskListener, SwipeRefreshLayout.OnRefreshListener {

    ListView listView;
    UsersModel um;

    private SwipeRefreshLayout swipeRefreshLayout;
    public static MyCoupons newInstance(Context c){

        MyCoupons fa = new MyCoupons();

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
        View v = inflater.inflate(R.layout.my_coupons_fragment_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeCatalogue);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) v.findViewById(R.id.listview_catalogue);
        init();

        return v;
    }

    public void init() {
        swipeRefreshLayout.setRefreshing(true);
        um = new SharedPrefManager(getActivity()).userGet();
        new ListViewAPI(getActivity(), MyCoupons.this, Settings.base_url+"/api_catalogue/mycoupons/"+um.getId());
//        new LiveAPI(getActivity(), Coupon.this, false).execute("POST", "/api_catalogue/list/");
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

            final ArrayList<MyCouponsModel> myCouponsModel = new ArrayList<>();

            try {
                jObj = new JSONObject(result);
                boolean success = jObj.getBoolean("success");

                if (success) {
                    JSONArray data = jObj.optJSONArray("data");

                    for (int x = 0; x < data.length(); x++) {
                        JSONObject a = data.getJSONObject(x);

                        MyCouponsModel cm = new MyCouponsModel();
                        cm.setImage(a.getString("image"));
                        cm.setName(a.getString("title"));
                        cm.setDescription(a.getString("description"));
                        cm.setShort_description(a.getString("short_description"));
                        cm.setDiscount(a.getInt("discount"));
                        cm.setPrice(a.getInt("price"));
                        cm.setStatus(a.getInt("status"));
                        cm.setOrder_count(a.getInt("order_count"));
                        cm.setDate_added(a.getString("date_added"));
                        cm.setId(a.getInt("id"));
                        cm.setPrice(a.getInt("price"));

                        myCouponsModel.add(cm);
                    }


                    MyCouponsListAdapter myCouponsListAdapter = new MyCouponsListAdapter(getActivity(), myCouponsModel);
                    listView.setAdapter(myCouponsListAdapter);

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

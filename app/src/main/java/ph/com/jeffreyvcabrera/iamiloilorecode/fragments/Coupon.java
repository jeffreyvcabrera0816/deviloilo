package ph.com.jeffreyvcabrera.iamiloilorecode.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
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
import ph.com.jeffreyvcabrera.iamiloilorecode.adapters.CatalogueListAdapter;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;
import ph.com.jeffreyvcabrera.iamiloilorecode.models.CatalogueModel;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.API;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.ListViewAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.LiveAPI;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.MyToast;
import ph.com.jeffreyvcabrera.iamiloilorecode.utils.Settings;

public class Coupon extends Fragment implements AsyncTaskListener, SwipeRefreshLayout.OnRefreshListener {

    ListView listView;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static Coupon newInstance(Context c){

        Coupon fa = new Coupon();

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
        View v = inflater.inflate(R.layout.catalogue_fragment_list, container, false);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeCatalogue);
        swipeRefreshLayout.setOnRefreshListener(this);
        listView = (ListView) v.findViewById(R.id.listview_catalogue);
        init();

        return v;
    }

    public void init() {
        swipeRefreshLayout.setRefreshing(true);
        new ListViewAPI(getActivity(), Coupon.this, Settings.base_url+"/api_catalogue/list/");
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

            final ArrayList<CatalogueModel> catalogueModel = new ArrayList<>();

            try {
                jObj = new JSONObject(result);
                boolean success = jObj.getBoolean("success");

                if (success) {
                    JSONArray data = jObj.optJSONArray("data");

                    for (int x = 0; x < data.length(); x++) {
                        JSONObject a = data.getJSONObject(x);

                        CatalogueModel cm = new CatalogueModel();
                        cm.setImage(a.getString("image"));
                        cm.setName(a.getString("title"));
                        cm.setDescription(a.getString("description"));
                        cm.setCoupons_left(a.getInt("coupons_left"));
                        cm.setDiscount(a.getInt("discount"));
                        cm.setPromo_date(a.getString("end_date"));
                        cm.setId(a.getInt("id"));
                        cm.setPrice(a.getInt("price"));

                        catalogueModel.add(cm);
                    }


                    CatalogueListAdapter catalogueListAdapter = new CatalogueListAdapter(getActivity(), catalogueModel);
                    listView.setAdapter(catalogueListAdapter);

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

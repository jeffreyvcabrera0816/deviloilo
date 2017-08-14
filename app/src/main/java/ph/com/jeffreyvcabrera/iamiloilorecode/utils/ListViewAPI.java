package ph.com.jeffreyvcabrera.iamiloilorecode.utils;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import ph.com.jeffreyvcabrera.iamiloilorecode.app.AppController;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;

/**
 * Created by Jeffrey on 2/19/2017.
 */

public class ListViewAPI {

    final String TAG = "response";
    AsyncTaskListener callback;
    Context context;
    String url;

    public ListViewAPI(final Context context, AsyncTaskListener cb, String url) {
        this.callback = cb;
        this.context = context;
        this.url = url;

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Log.d(TAG, response.toString());
                        callback.onTaskComplete(response.toString());
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onTaskComplete("error");
                String message = null;
                if (volleyError instanceof NetworkError) {
                    message = "Cannot connect to the Internet. Please check your connection.";
                } else if (volleyError instanceof ServerError) {
                    message = "The server could not be found. Please try again later.";
                } else if (volleyError instanceof AuthFailureError) {
                    message = "Cannot connect to the Internet. Please check your connection.";
                } else if (volleyError instanceof ParseError) {
                    message = "Connection Error. Please try again later.";
                } else if (volleyError instanceof NoConnectionError) {
                    message = "Cannot connect to the Internet. Please check your connection.";
                } else if (volleyError instanceof TimeoutError) {
                    message = "Connection TimeOut. Please check your internet connection.";
                }

                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }
}


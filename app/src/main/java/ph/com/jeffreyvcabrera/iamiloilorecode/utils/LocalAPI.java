package ph.com.jeffreyvcabrera.iamiloilorecode.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ph.com.jeffreyvcabrera.iamiloilorecode.app.AppController;
import ph.com.jeffreyvcabrera.iamiloilorecode.interfaces.AsyncTaskListener;

/**
 * Created by Jeffrey on 3/14/2017.
 */

public class LocalAPI extends AsyncTask<String, Void, String> {
    Context activity;
    Boolean refresh_count;
    String method, api_url;
    AsyncTaskListener callback;
    ProgressDialog progressDialog;

    public LocalAPI(Context activity, AsyncTaskListener cb, Boolean refresh_count) {
        this.activity = activity;
        this.callback = cb;
        this.refresh_count = refresh_count;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        if (refresh_count == false) {
//
//            progressDialog = new ProgressDialog(activity);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
//            if (!progressDialog.isShowing()) {
//                progressDialog.show();
//            }
//        }

        ConnectivityManager cm =
                (ConnectivityManager)activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if (!isConnected) {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected String doInBackground(String... params) {
        if (params[0] == null && params[1] == null && params[2] == null)  return null;
        method = params[0];
        api_url = params[1];
        return fetchAPI();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (result != null)
            callback.onTaskComplete(result);
    }

    String fetchAPI() {
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(Settings.local_url + api_url);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            inputStream.close();
            return sb.toString();
        } catch (Exception e) {
            Log.d("hey", e.toString());
        } finally {
            if (urlConnection != null)  urlConnection.disconnect();
        }
        return null;
    }
}

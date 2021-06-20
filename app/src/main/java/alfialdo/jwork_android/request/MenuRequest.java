package alfialdo.jwork_android.request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Request untuk mengambil list data job pada database jwork
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class MenuRequest extends StringRequest {
    private static final String URL = "http://10.0.2.2:8080/job";

    public MenuRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });
    }
}
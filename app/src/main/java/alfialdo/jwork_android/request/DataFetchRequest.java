package alfialdo.jwork_android.request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Request untuk mengambil data dari database jwork
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class DataFetchRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/";

    public DataFetchRequest(String database, Response.Listener<String> listener) {
        super(Request.Method.GET, URL + database, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });
    }
}

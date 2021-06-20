package alfialdo.jwork_android.request;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Request untuk menghapus data dari database jwork
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class DataRemoveRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/";

    public DataRemoveRequest(String database,int id,  Response.Listener<String> listener) {
        super(Request.Method.DELETE, URL + database + id, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });
    }
}

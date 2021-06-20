package alfialdo.jwork_android.request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Request untuk mendapatkan invoice berdasarkan id jobseeker
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class JobFetchRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/invoice/jobseeker/";

    public JobFetchRequest(int jobseekerId, Response.Listener<String> listener) {
        super(Method.GET, URL + jobseekerId, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });
    }
}

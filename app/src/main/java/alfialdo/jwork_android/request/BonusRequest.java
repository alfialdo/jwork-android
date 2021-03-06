package alfialdo.jwork_android.request;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

/**
 * Request untuk mendapatkan bonus berdasarkan referral code
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class BonusRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/bonus/";

    public BonusRequest(String referralCode, Response.Listener<String> listener) {
        super(Method.GET, URL + referralCode, listener, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROR", error.toString());
            }
        });
    }
}

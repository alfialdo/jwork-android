package alfialdo.jwork_android.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request untuk melakukan registrasi jobseeker
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class RegisterRequest extends StringRequest
{
    private static final String URL = "http://10.0.2.2:8080/jobseeker/register";
    private final Map<String, String> params;

    public RegisterRequest(String name,
                           String email,
                           String password,
                           Response.Listener<String> listener)
    {
        super(Method.POST, URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("password", password);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}

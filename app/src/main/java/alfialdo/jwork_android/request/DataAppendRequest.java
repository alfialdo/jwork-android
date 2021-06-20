package alfialdo.jwork_android.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Request untuk menambahkan data ke database sistem jwork
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class DataAppendRequest extends StringRequest
{
    private final Map<String, String> params;

    /**
     * Method untuk menambahkan data bonus ke database jwrok
     * @param referralCode
     * @param extraFee
     * @param minTotalFee
     * @param listener
     */
    public DataAppendRequest(String referralCode,
                             String extraFee,
                             String minTotalFee,
                             Response.Listener<String> listener)
    {
        super(Method.POST, "http://10.0.2.2:8080/bonus/", listener, null);
        params = new HashMap<>();
        params.put("extraFee", extraFee);
        params.put("minTotalFee", minTotalFee);
        params.put("referralCode", referralCode);
        params.put("active", "true");

    }

    /**
     * Method untuk menambahkan data job ke database jwrok
     * @param name
     * @param fee
     * @param category
     * @param recruiterId
     * @param listener
     */
    public DataAppendRequest(String name,
                             String fee,
                             String category,
                             String recruiterId,
                             Response.Listener<String> listener)
    {
        super(Method.POST, "http://10.0.2.2:8080/job/", listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("fee", fee);
        params.put("category", category);
        params.put("recruiterId", recruiterId);

    }

    /**
     * Method untuk menambahkan data recruiter ke database jwrok
     * @param name
     * @param email
     * @param phoneNumber
     * @param province
     * @param city
     * @param description
     * @param listener
     */
    public DataAppendRequest(String name,
                             String email,
                             String phoneNumber,
                             String province,
                             String city,
                             String description,
                             Response.Listener<String> listener)
    {
        super(Method.POST, "http://10.0.2.2:8080/recruiter/", listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("phoneNumber", phoneNumber);
        params.put("province", province);
        params.put("city", city);
        params.put("description", description);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}



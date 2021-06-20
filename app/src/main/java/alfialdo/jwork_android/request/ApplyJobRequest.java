package alfialdo.jwork_android.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Request untuk apply job
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class ApplyJobRequest extends StringRequest
{
    private final Map<String, String> params;

    /**
     * Method untuk apply job dengan payment type e-wallet payment
     * @param jobId
     * @param jobseekerId
     * @param referralCode
     * @param listener
     */
    public ApplyJobRequest(int jobId,
                           int jobseekerId,
                           String referralCode,
                           Response.Listener<String> listener)
    {
        super(Method.POST, "http://10.0.2.2:8080/invoice/createEwalletPayment", listener, null);
        params = new HashMap<>();
        params.put("jobIdList", String.valueOf(jobId));
        params.put("jobseekerId", String.valueOf(jobseekerId));
        params.put("referralCode", referralCode);

    }

    /**
     * Method untuk apply job dengan payment type bank payment
     * @param jobId
     * @param jobseekerId
     * @param adminFee
     * @param listener
     */
    public ApplyJobRequest(int jobId,
                           int jobseekerId,
                           int adminFee,
                           Response.Listener<String> listener)
    {
        super(Method.POST, "http://10.0.2.2:8080/invoice/createBankPayment", listener, null);
        params = new HashMap<>();
        params.put("jobIdList", String.valueOf(jobId));
        params.put("jobseekerId", String.valueOf(jobseekerId));
        params.put("adminFee", String.valueOf(adminFee));

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}



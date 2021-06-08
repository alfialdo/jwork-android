package alfialdo.jwork_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FinishedJobActivity extends AppCompatActivity
{
    private ResultListener listener;
    private int userInvoiceId;
    private int jobseekerId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_job);

        TextView tvInvoiceId = findViewById(R.id.invoiceId);
        TextView tvInvoiceDate = findViewById(R.id.invoiceDate);
        TextView tvInvoiceStatus = findViewById(R.id.invoiceStatus);
        TextView tvJobseekerName = findViewById(R.id.jobseekerName);
        TextView tvPaymentType = findViewById(R.id.paymentType);
        TextView tvReferralCode = findViewById(R.id.referralCode2);
        TextView tvJobName = findViewById(R.id.jobName);
        TextView tvJobFee = findViewById(R.id.jobFee);
        TextView tvTotalFee = findViewById(R.id.totalFee);
        tvInvoiceStatus.setVisibility(View.VISIBLE);


        Button btnCancelled = findViewById(R.id.btnCancelled);
        Button btnFinished = findViewById(R.id.btnFinished);

        Intent i = getIntent();
        jobseekerId = i.getExtras().getInt("jobseekerId");

        fetchJob();

        setResultListener(new ResultListener()
        {
            @Override
            public void onResult(String invoiceId,
                                 String invoiceDate,
                                 String invoiceStatus,
                                 String jobseekerName,
                                 String paymentType,
                                 String referralCode,
                                 String jobName,
                                 String jobFee,
                                 String totalFee)
            {

                userInvoiceId = Integer.valueOf(invoiceId);
                tvInvoiceId.setText(invoiceId);
                tvInvoiceDate.setText(invoiceDate);
                tvInvoiceStatus.setText(invoiceStatus);
                tvJobseekerName.setText(jobseekerName);
                tvPaymentType.setText(paymentType);
                tvReferralCode.setText(referralCode);
                tvJobName.setText(jobName);
                tvJobFee.setText(jobFee);
                tvTotalFee.setText(totalFee);
            }
        });


        btnCancelled.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null) {
                                Toast.makeText(FinishedJobActivity.this, "Job Cancelled", Toast.LENGTH_LONG).show();
                            }
                        } catch(JSONException e) {
                            Toast.makeText(FinishedJobActivity.this, "Error in Cancelling Job", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                JobCancelledRequest jobCancelledRequest = new JobCancelledRequest(userInvoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FinishedJobActivity.this);
                queue.add(jobCancelledRequest);
            }
        });

        btnFinished.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject != null) {
                                Toast.makeText(FinishedJobActivity.this, "Job Finished", Toast.LENGTH_LONG).show();
                            }
                        } catch(JSONException e) {
                            Toast.makeText(FinishedJobActivity.this, "Error in Finishing Job", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                JobFinishedRequest jobFinishedRequest = new JobFinishedRequest(userInvoiceId, responseListener);
                RequestQueue queue = Volley.newRequestQueue(FinishedJobActivity.this);
                queue.add(jobFinishedRequest);
            }
        });

    }

    private void fetchJob() {
        Response.Listener<String> responseListener = new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    boolean condition = true;
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject invoice = jsonArray.getJSONObject(i);
                        JSONObject jobseeker = invoice.getJSONObject("jobseeker");
                        JSONArray jobs = invoice.getJSONArray("jobs");
                        JSONObject job = jobs.getJSONObject(0);

                        if(invoice.getString("invoiceStatus").equals("Ongoing")) {
                            condition = false;
                            String paymentType = invoice.getString("paymentType");

                            if(paymentType.equals("EwalletPayment")) {
                                JSONObject bonus = invoice.getJSONObject("bonus");
                                listener.onResult(
                                        invoice.getString("id"),
                                        invoice.getString("date"),
                                        invoice.getString("invoiceStatus"),
                                        jobseeker.getString("name"),
                                        paymentType,
                                        bonus.getString("referralCode"),
                                        job.getString("name"),
                                        job.getString("fee"),
                                        invoice.getString("totalFee"));
                            }
                            else {
                                listener.onResult(
                                        invoice.getString("id"),
                                        invoice.getString("date"),
                                        invoice.getString("invoiceStatus"),
                                        jobseeker.getString("name"),
                                        paymentType,
                                        "-",
                                        job.getString("name"),
                                        job.getString("fee"),
                                        invoice.getString("totalFee"));
                            }

                            break;
                        }
                    }

                    if(condition) {
                        Intent i = new Intent(FinishedJobActivity.this, MainActivity.class);
                        i.putExtra("jobseekerId", jobseekerId);
                        Toast.makeText(FinishedJobActivity.this, "Try to Apply Job First", Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                } catch (JSONException e) {
                    Intent i = new Intent(FinishedJobActivity.this, MainActivity.class);
                    i.putExtra("jobseekerId", jobseekerId);
                    e.printStackTrace();
                    Toast.makeText(FinishedJobActivity.this, "No Applied Job", Toast.LENGTH_LONG).show();
                    startActivity(i);
                }
            }
        };

        JobFetchRequest jobFetchRequest = new JobFetchRequest(jobseekerId, responseListener);
        RequestQueue queue = Volley.newRequestQueue(FinishedJobActivity.this);
        queue.add(jobFetchRequest);
    }

    private interface ResultListener {
        public void onResult(String invoiceId,
                             String invoiceDate,
                             String invoiceStatus,
                             String jobseekerName,
                             String paymentType,
                             String referralCode,
                             String jobName,
                             String jobFee,
                             String totalFee);
    }

    public void setResultListener(ResultListener listener) {
        this.listener = listener;
    }
}
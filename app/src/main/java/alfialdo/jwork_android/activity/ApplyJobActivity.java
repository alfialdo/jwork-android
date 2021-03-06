package alfialdo.jwork_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import alfialdo.jwork_android.object.Bonus;
import alfialdo.jwork_android.request.ApplyJobRequest;
import alfialdo.jwork_android.request.BonusRequest;
import alfialdo.jwork_android.object.Job;
import alfialdo.jwork_android.R;
import alfialdo.jwork_android.request.DataRemoveRequest;

/**
 * Acitivity untuk tampilan jobseeker melakukan apply job
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class ApplyJobActivity extends AppCompatActivity
{
    private int jobseekerId;
    private int jobId;
    private String jobName;
    private String jobCategory;
    private double jobFee;
    private int bonus;
    private int selectedPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_job);

        Intent i = getIntent();
        jobseekerId = i.getExtras().getInt("jobseekerId");
        Job tempJob = (Job) i.getParcelableExtra("Job");
        jobId = tempJob.getId();
        jobName = tempJob.getName();
        jobCategory = tempJob.getCategory();
        jobFee = tempJob.getFee();

        Button btnApply = findViewById(R.id.btnExistingRecruiter);
        TextView staticReferralCode = findViewById(R.id.staticReferralCode);
        EditText referralCode = findViewById(R.id.referral_code);
        btnApply.setVisibility(View.GONE);
        staticReferralCode.setVisibility(View.INVISIBLE);
        referralCode.setVisibility(View.INVISIBLE);

        TextView fieldJobName = findViewById(R.id.job_name);
        fieldJobName.setText(jobName);
        TextView fieldJobCategory = findViewById(R.id.job_category);
        fieldJobCategory.setText(jobCategory);
        TextView fieldJobFee = findViewById(R.id.job_fee);
        fieldJobFee.setText(String.valueOf(jobFee));
        TextView fieldTotalFee = findViewById(R.id.total_fee);
        fieldTotalFee.setText(String.valueOf(0.0));

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button btnCount = findViewById(R.id.btnCount);
        Button btnRemoveJob = findViewById(R.id.btnRemoveJob);
        TextView staticPayMethod = findViewById(R.id.staticPayMethod);
        TextView staticTotalFee = findViewById(R.id.staticTotalFee);
        TextView staticTitle = findViewById(R.id.pesanan);

        if(jobseekerId == 0) {
            radioGroup.setVisibility(View.GONE);
            btnCount.setVisibility(View.GONE);
            staticPayMethod.setVisibility(View.GONE);
            staticReferralCode.setVisibility(View.GONE);
            staticTotalFee.setVisibility(View.GONE);
            referralCode.setVisibility(View.GONE);
            fieldTotalFee.setVisibility(View.GONE);
            staticTitle.setText("Job Information");
            btnRemoveJob.setVisibility(View.VISIBLE);

        } else {
            radioGroup.setVisibility(View.VISIBLE);
            btnCount.setVisibility(View.VISIBLE);
            staticPayMethod.setVisibility(View.VISIBLE);
            staticReferralCode.setVisibility(View.INVISIBLE);
            staticTotalFee.setVisibility(View.VISIBLE);
            referralCode.setVisibility(View.INVISIBLE);
            fieldTotalFee.setVisibility(View.VISIBLE);
            staticTitle.setText("Apply Now");
            btnRemoveJob.setVisibility(View.GONE);
        }

        btnRemoveJob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Response.Listener<String> responseListener = new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        if(response.equals("true")) {
                            Toast.makeText(ApplyJobActivity.this, "Job has been Deleted!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(ApplyJobActivity.this, MainActivity.class);
                            i.putExtra("jobseekerId", jobseekerId);
                            startActivity(i);
                            finish();
                        } else {
                            Toast.makeText(ApplyJobActivity.this, "Failed to Delete Job", Toast.LENGTH_LONG).show();
                        }

                    }
                };

                DataRemoveRequest removeJob = new DataRemoveRequest(
                        "job/",
                        jobId,
                        responseListener);
                RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                queue.add(removeJob);
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId)
            {
                if(checkedId == R.id.ewallet) {
                    staticReferralCode.setVisibility(View.VISIBLE);
                    referralCode.setVisibility(View.VISIBLE);
                }
                else if (checkedId == R.id.bank){
                    staticReferralCode.setVisibility(View.INVISIBLE);
                    referralCode.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnCount.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                selectedPayment = radioGroup.getCheckedRadioButtonId();

                if(selectedPayment == R.id.bank) {
                    fieldTotalFee.setText(String.valueOf(jobFee));
                }
                else if(selectedPayment == R.id.ewallet) {
                    if(referralCode.getText().toString().matches("")) {
                        fieldTotalFee.setText(String.valueOf(jobFee));
                    }
                    else {
                        Response.Listener<String> responseListener = new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if(jsonObject != null) {

                                        if(jsonObject.getBoolean("active") && jobFee > jsonObject.getInt("minTotalFee")) {
                                            bonus = jsonObject.getInt("extraFee");
                                            double total = jobFee + bonus;
                                            fieldTotalFee.setText(String.valueOf(total));
                                            Toast.makeText(ApplyJobActivity.this, "Referral Code Exist!", Toast.LENGTH_LONG).show();
                                        }
                                        else {
                                            fieldTotalFee.setText(String.valueOf(jobFee));
                                            Toast.makeText(ApplyJobActivity.this, "You Can't Use This Code!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch(JSONException e) {
                                    fieldTotalFee.setText(String.valueOf(jobFee));
                                    Toast.makeText(ApplyJobActivity.this, "Referral Code Doesn't Exist!", Toast.LENGTH_LONG).show();
                                }
                            }
                        };

                        BonusRequest bonusRequest = new BonusRequest(referralCode.getText().toString(), responseListener);
                        RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                        queue.add(bonusRequest);
                    }
                }

                btnCount.setVisibility(View.GONE);
                btnApply.setVisibility(View.VISIBLE);
            }
        });

        btnApply.setOnClickListener(new View.OnClickListener()
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
                                Toast.makeText(ApplyJobActivity.this, "Applied!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ApplyJobActivity.this, MainActivity.class);
                                i.putExtra("jobseekerId", jobseekerId);
                                startActivity(i);
                                finish();
                            }
                        } catch(JSONException e) {
                            Toast.makeText(ApplyJobActivity.this, "Failed to Apply", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                ApplyJobRequest applyJobRequest = null;

                if(selectedPayment == R.id.ewallet) {
                    applyJobRequest = new ApplyJobRequest(jobId, jobseekerId, referralCode.getText().toString(), responseListener);
                }
                else if(selectedPayment == R.id.bank) {
                    applyJobRequest = new ApplyJobRequest(jobId, jobseekerId, 0, responseListener);
                }

                RequestQueue queue = Volley.newRequestQueue(ApplyJobActivity.this);
                queue.add(applyJobRequest);
            }
        });

    }
}
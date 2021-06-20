package alfialdo.jwork_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import alfialdo.jwork_android.R;
import alfialdo.jwork_android.object.Bonus;
import alfialdo.jwork_android.object.Recruiter;
import alfialdo.jwork_android.request.DataAppendRequest;
import alfialdo.jwork_android.request.DataRemoveRequest;

/**
 * Acitivity untuk tampilan admin melihat info dari job, recruiter, atau bonus
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class InfoPageActivity extends AppCompatActivity
{
    Object info;
    int jobseekerId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_page);

        Button btnNewJob = findViewById(R.id.btnNewJob);
        Button btnRemove = findViewById(R.id.btnRemove);
        TextView tvTitle = findViewById(R.id.tvInfoTitle);
        TextView tvLine1 = findViewById(R.id.tvLine1);
        TextView tvLine2 = findViewById(R.id.tvLine2);
        TextView tvLine3 = findViewById(R.id.tvLine3);
        TextView tvLine4 = findViewById(R.id.tvLine4);
        TextView tvLine5 = findViewById(R.id.tvLine5);
        TextView tvLineText1 = findViewById(R.id.tvLineText1);
        TextView tvLineText2 = findViewById(R.id.tvLineText2);
        TextView tvLineText3 = findViewById(R.id.tvLineText3);
        TextView tvLineText4 = findViewById(R.id.tvLineText4);
        TextView tvLineText5 = findViewById(R.id.tvLineText5);

        Intent i = getIntent();
        info = i.getParcelableExtra("Object");
        jobseekerId = i.getExtras().getInt("jobseekerId");

        if(info instanceof Bonus) {
            tvTitle.setText("Bonus Information");
            tvLine1.setText("Referral Code:");
            tvLine2.setText("Extra Fee:");
            tvLine3.setText("Min Total Fee:");
            tvLine4.setText("Active:");

            tvLineText1.setText(((Bonus) info).getReferralCode());
            tvLineText2.setText(String.valueOf(((Bonus) info).getExtraFee()));
            tvLineText3.setText(String.valueOf(((Bonus) info).getMinTotalFee()));
            tvLineText4.setText("Yes");

            tvLine5.setVisibility(View.GONE);
            tvLineText5.setVisibility(View.GONE);
            btnNewJob.setVisibility(View.GONE);

        } else if(info instanceof Recruiter) {
            tvTitle.setText("Recruiter Information");
            tvLine1.setText("Name:");
            tvLine2.setText("Email:");
            tvLine3.setText("Phone Number:");
            tvLine4.setText("Location:");
            tvLine5.setText("Description");

            tvLineText1.setText(((Recruiter) info).getName());
            tvLineText2.setText(((Recruiter) info).getEmail());
            tvLineText3.setText(((Recruiter) info).getPhoneNumber());
            String loc = ((Recruiter) info).getLocation().getProvince() + ", " +
                    ((Recruiter) info).getLocation().getCity();
            tvLineText4.setText(loc);
            tvLineText5.setText(((Recruiter) info).getLocation().getDescription());
        }

        btnNewJob.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(InfoPageActivity.this, AddJobActivity.class);
                intent.putExtra("recruiterId", ((Recruiter) info).getId());
                i.putExtra("jobseekerId", jobseekerId);
                startActivity(intent);
                finish();
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(info instanceof Bonus) {
                    Response.Listener<String> responseListener = new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("true")) {
                                Toast.makeText(InfoPageActivity.this, "Bonus has been Deleted!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(InfoPageActivity.this, MainActivity.class);
                                i.putExtra("jobseekerId", jobseekerId);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(InfoPageActivity.this, "Failed to Delete Bonus", Toast.LENGTH_LONG).show();
                            }

                        }
                    };

                    DataRemoveRequest removeBonus = new DataRemoveRequest(
                            "bonus/",
                            ((Bonus) info).getId(),
                            responseListener);
                    RequestQueue queue = Volley.newRequestQueue(InfoPageActivity.this);
                    queue.add(removeBonus);

                } else if(info instanceof Recruiter) {
                    Response.Listener<String> responseListener = new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            if(response.equals("true")) {
                                Toast.makeText(InfoPageActivity.this, "Recruiter has been Deleted!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(InfoPageActivity.this, MainActivity.class);
                                i.putExtra("jobseekerId", jobseekerId);
                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(InfoPageActivity.this, "Failed to Delete Recruiter", Toast.LENGTH_LONG).show();
                            }

                        }
                    };

                    DataRemoveRequest removeRecruiter = new DataRemoveRequest(
                            "recruiter/",
                            ((Recruiter) info).getId(),
                            responseListener);
                    RequestQueue queue = Volley.newRequestQueue(InfoPageActivity.this);
                    queue.add(removeRecruiter);
                }
            }
        });




    }
}
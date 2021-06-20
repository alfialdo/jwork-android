package alfialdo.jwork_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import alfialdo.jwork_android.R;
import alfialdo.jwork_android.request.DataAppendRequest;

/**
 * Acitivity untuk tampilan penambahan data Bonus baru pada aplikasi
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class AddRecruiterActivity extends AppCompatActivity
{
    int jobseekerId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recruiter);

        Button btnAddRecruiter = findViewById(R.id.btnAddRecruiter);
        EditText etRecName = findViewById(R.id.etRecName);
        EditText etRecEmail = findViewById(R.id.etRecEmail);
        EditText etRecPhoneNum = findViewById(R.id.etRecPhoneNum);
        EditText etRecProvince = findViewById(R.id.etRecProvince);
        EditText etRecCity = findViewById(R.id.etRecCity);
        EditText etRecDesc = findViewById(R.id.etRecDesc);

        Intent i = getIntent();
        jobseekerId = i.getExtras().getInt("jobseekerId");

        btnAddRecruiter.setOnClickListener(new View.OnClickListener()
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
                            Toast.makeText(AddRecruiterActivity.this, "Recruiter has been Added!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(AddRecruiterActivity.this, MainActivity.class);
                            i.putExtra("jobseekerId", jobseekerId);
                            startActivity(i);
                            finish();

                        } catch(JSONException e) {
                            Toast.makeText(AddRecruiterActivity.this, "Recruiter already exist!", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                DataAppendRequest addRecruiter = new DataAppendRequest(
                        etRecName.getText().toString(),
                        etRecEmail.getText().toString(),
                        etRecPhoneNum.getText().toString(),
                        etRecProvince.getText().toString(),
                        etRecCity.getText().toString(),
                        etRecDesc.getText().toString(),
                        responseListener);

                RequestQueue queue = Volley.newRequestQueue(AddRecruiterActivity.this);
                queue.add(addRecruiter);
            }
        });

    }
}
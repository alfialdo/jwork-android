package alfialdo.jwork_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import alfialdo.jwork_android.R;
import alfialdo.jwork_android.request.DataAppendRequest;

/**
 * Acitivity untuk tampilan penambahan data Job baru pada aplikasi
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class AddJobActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    int recruiterId;
    int jobseekerId;
    String jobCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);


        Button btnAddJob = findViewById(R.id.btnAddJob);
        EditText etJobName = findViewById(R.id.etJobName);
        EditText etJobFee = findViewById(R.id.etJobFee);
        Spinner spJobCategory = findViewById(R.id.categories);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.job_categories,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJobCategory.setAdapter(adapter);
        spJobCategory.setOnItemSelectedListener(this);

        Intent i = getIntent();
        recruiterId = i.getExtras().getInt("recruiterId");
        jobseekerId = i.getExtras().getInt("jobseekerId");

        btnAddJob.setOnClickListener(new View.OnClickListener()
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
                            Toast.makeText(AddJobActivity.this, "Job has been Added!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(AddJobActivity.this, MainActivity.class);
                            i.putExtra("jobseekerId", jobseekerId);
                            startActivity(i);
                            finish();

                        } catch(JSONException e) {
                            Toast.makeText(AddJobActivity.this, "Failed to Add Job", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                DataAppendRequest addJob = new DataAppendRequest(
                        etJobName.getText().toString(),
                        etJobFee.getText().toString(),
                        jobCategory,
                        String.valueOf(recruiterId),
                        responseListener);

                RequestQueue queue = Volley.newRequestQueue(AddJobActivity.this);
                queue.add(addJob);
            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        jobCategory = parent.getItemAtPosition(position).toString().replaceAll("\\s+", "");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent)
    {

    }
}
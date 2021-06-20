package alfialdo.jwork_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
public class AddBonusActivity extends AppCompatActivity
{
    int jobseekerId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bonus);

        Button btnAddBonus = findViewById(R.id.btnAddBonus);
        EditText etBonusRef = findViewById(R.id.etBonusRef);
        EditText etBonusExtra = findViewById(R.id.etBonusExtra);
        EditText etBonusMinTotal = findViewById(R.id.etBonusMinTotal);

        Intent i = getIntent();
        jobseekerId = i.getExtras().getInt("jobseekerId");

        btnAddBonus.setOnClickListener(new View.OnClickListener()
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
                            Toast.makeText(AddBonusActivity.this, "Bonus has been Added!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(AddBonusActivity.this, MainActivity.class);
                            i.putExtra("jobseekerId", jobseekerId);
                            startActivity(i);
                            finish();
                        } catch(JSONException e) {
                            Toast.makeText(AddBonusActivity.this, "Referral Code Already Exist!", Toast.LENGTH_LONG).show();
                        }
                    }
                };

                DataAppendRequest addBonus = new DataAppendRequest(
                        etBonusRef.getText().toString(),
                        etBonusExtra.getText().toString(),
                        etBonusMinTotal.getText().toString(),
                        responseListener);

                RequestQueue queue = Volley.newRequestQueue(AddBonusActivity.this);
                queue.add(addBonus);
            }
        });

    }
}
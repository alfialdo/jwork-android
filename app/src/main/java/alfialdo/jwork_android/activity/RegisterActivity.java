package alfialdo.jwork_android.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

import alfialdo.jwork_android.R;
import alfialdo.jwork_android.request.RegisterRequest;

/**
 * Acitivity untuk tampilan registrasi jobseeker
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class RegisterActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button buttonRegister = findViewById(R.id.btnAddRecruiter);
        EditText inputName = findViewById(R.id.inputRegisterName);
        EditText inputEmail = findViewById(R.id.inputRegisterEmail);
        EditText inputPassword = findViewById(R.id.inputRegisterPassword);

        buttonRegister.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = inputName.getText().toString();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();

                String pattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$";
                Pattern r = Pattern.compile(pattern);
                Matcher m_password = r.matcher(password);

                pattern = "(?!.*\\.{2,})(?!\\.)[0-9A-z.&*_~]+@(?!-)[0-9A-z&*_~.-]+";
                r = Pattern.compile(pattern);
                Matcher m_email = r.matcher(email);

                if(m_email.find()) {
                    System.out.println("Pass 1");
                    if(m_password.find()) {
                        Response.Listener<String> responseListener = new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response)
                            {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject != null) {
                                        Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    Toast.makeText(RegisterActivity.this, "Register Failed", Toast.LENGTH_LONG).show();
                                }
                            }
                        };

                        RegisterRequest registerRequest = new RegisterRequest(name, email, password, responseListener);
                        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                        queue.add(registerRequest);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Password must contains uppercase, lowercase, and number.", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Email format is wrong.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
package alfialdo.jwork_android;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    private final ArrayList<Recruiter> listRecruiter = new ArrayList<>();
    private final ArrayList<Job> jobIdList = new ArrayList<>();
    private final HashMap<Recruiter, ArrayList<Job>> childMapping = new HashMap<>();
    private int jobseekerId;

    MainListAdapter listAdapter;
    ExpandableListView expandableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent i = getIntent();
        jobseekerId = i.getExtras().getInt("jobseekerId");
        Button btnApplied = findViewById(R.id.btnApplied);

        expandableListView = (ExpandableListView) findViewById(R.id.expLv);
        refreshList();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                Job selectedJob = childMapping.get(listRecruiter.get(groupPosition)).get(childPosition);
                Intent intent = new Intent(MainActivity.this, ApplyJobActivity.class);
                intent.putExtra("Job", (Parcelable) selectedJob);
                intent.putExtra("jobseekerId", jobseekerId);
                startActivity(intent);
                return false;
            }
        });

        btnApplied.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(MainActivity.this, FinishedJobActivity.class);
                i.putExtra("jobseekerId", jobseekerId);
                startActivity(i);
            }
        });
    }

    protected void refreshList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    if (jsonResponse != null) {
                        for (int i = 0; i < jsonResponse.length(); i++){
                            JSONObject job = jsonResponse.getJSONObject(i);
                            JSONObject recruiter = job.getJSONObject("recruiter");
                            JSONObject location = recruiter.getJSONObject("location");

                            Location loc = new Location(location.getString("province"), location.getString("description"), location.getString("city"));
                            Recruiter newRecruiter = new Recruiter(recruiter.getInt("id"), recruiter.getString("name"), recruiter.getString("email"), recruiter.getString("phoneNumber"), loc);

                            if(listRecruiter!=null){
                                boolean condition = false;
                                for (Recruiter rec:listRecruiter) {
                                    if(rec.getEmail().equals(newRecruiter.getEmail())){
                                        condition = true;
                                        break;
                                    }
                                }
                                if(!condition){
                                    listRecruiter.add(newRecruiter);
                                }
                            }

                            Job newJob = new Job(job.getInt("id"), job.getString("name"), newRecruiter, job.getInt("fee"), job.getString("category"));
                            jobIdList.add(newJob);
                        }

                        for (Recruiter rec : listRecruiter) {
                            ArrayList<Job> temp = new ArrayList<>();
                            for (Job jobs : jobIdList) {
                                if (jobs.getRecruiter().getName().equals(rec.getName()) ||
                                        jobs.getRecruiter().getEmail().equals(rec.getEmail()) ||
                                        jobs.getRecruiter().getPhoneNumber().equals(rec.getPhoneNumber())) {
                                    temp.add(jobs);
                                }
                            }
                            childMapping.put(rec, temp);
                        }
                        System.out.println(listRecruiter);
                        System.out.println(childMapping);
                        listAdapter = new MainListAdapter(MainActivity.this, listRecruiter, childMapping);
                        expandableListView.setAdapter(listAdapter);
                    }
                } catch (JSONException e) {
                    System.out.println("ERROR");
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}
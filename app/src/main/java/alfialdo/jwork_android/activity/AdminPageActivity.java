package alfialdo.jwork_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import alfialdo.jwork_android.R;
import alfialdo.jwork_android.adapter.EditPageListAdapter;
import alfialdo.jwork_android.object.Bonus;
import alfialdo.jwork_android.object.Job;
import alfialdo.jwork_android.object.Location;
import alfialdo.jwork_android.object.Recruiter;
import alfialdo.jwork_android.request.DataFetchRequest;

/**
 * Acitivity untuk menampilkan admin page pada aplikasi
 * @author Muhammad Alfi A
 * @version Final Project - 20 June 2021
 */
public class AdminPageActivity extends AppCompatActivity
{

    ExpandableListView expandableListView;
    ExpandableListAdapter listAdapter;
    ArrayList<String> group;
    HashMap<String, ArrayList<Object>> itemList;
    int jobseekerId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        Intent i = getIntent();
        jobseekerId = i.getExtras().getInt("jobseekerId");

        Button btnNewRecruiter = findViewById(R.id.btnAddRecruiter);
        Button btnNewBonus = findViewById(R.id.btnAddBonus);

        expandableListView = (ExpandableListView) findViewById(R.id.expListView);
        refreshList();

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
            {
                Object selectedItem = itemList.get(group.get(groupPosition)).get(childPosition);

                Intent intent = new Intent(AdminPageActivity.this, InfoPageActivity.class);
                intent.putExtra("Object", (Parcelable) selectedItem);
                i.putExtra("jobseekerId", jobseekerId);
                startActivity(intent);
                finish();

                return false;
            }
        });

        btnNewRecruiter.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(AdminPageActivity.this, AddRecruiterActivity.class);
                i.putExtra("jobseekerId", jobseekerId);
                startActivity(i);
                finish();
            }
        });

        btnNewBonus.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(AdminPageActivity.this, AddBonusActivity.class);
                i.putExtra("jobseekerId", jobseekerId);
                startActivity(i);
                finish();
            }
        });
    };

    /**
     * Method untuk update expandable list view pada admin page
     */
    private void refreshList() {
        group = new ArrayList<>();
        itemList = new HashMap<>();

        ArrayList<Object> recruiters = new ArrayList<>();
        ArrayList<Object> bonuses = new ArrayList<>();

        group.add("List of Recruiters");
        group.add("List of Bonuses");

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);

                    for (int i = 0; i < jsonResponse.length(); i++){
                        JSONObject recruiter = jsonResponse.getJSONObject(i);
                        JSONObject location = recruiter.getJSONObject("location");

                        Location loc = new Location(location.getString("province"), location.getString("description"), location.getString("city"));
                        Recruiter newRecruiter = new Recruiter(recruiter.getInt("id"), recruiter.getString("name"), recruiter.getString("email"), recruiter.getString("phoneNumber"), loc);
                        recruiters.add(newRecruiter);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        DataFetchRequest recruiterRequest = new DataFetchRequest("recruiter/", responseListener);
        RequestQueue queue = Volley.newRequestQueue(AdminPageActivity.this);
        queue.add(recruiterRequest);

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);

                    for (int i = 0; i < jsonResponse.length(); i++){
                        JSONObject bonus = jsonResponse.getJSONObject(i);

                        Bonus newBonus = new Bonus(bonus.getInt("id"), bonus.getInt("extraFee"), bonus.getInt("minTotalFee"), bonus.getString("referralCode"), bonus.getBoolean("active"));
                        bonuses.add(newBonus);
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        DataFetchRequest bonusRequest = new DataFetchRequest("bonus/", responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(AdminPageActivity.this);
        queue2.add(bonusRequest);

        itemList.put(group.get(0), recruiters);
        itemList.put(group.get(1), bonuses);

        listAdapter = new EditPageListAdapter(this, group, itemList);
        expandableListView.setAdapter(listAdapter);
    }
}
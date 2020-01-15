package com.vinsol.scheduler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.vinsol.scheduler.Adapter.ListAdapter;
import com.vinsol.scheduler.Models.SchedulerModel;
import com.vinsol.scheduler.Services.Schedulerservice;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    TextView scheduledDate;
    String formattedDate;
    RecyclerView recyclerView;
    ArrayList<SchedulerModel> model;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scheduledDate = findViewById(R.id.schedule_date);
        formattedDate = Utility.todayDate();
        scheduledDate.setText(formattedDate);
        recyclerView = findViewById(R.id.list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getData();

    }

    public void prevClicked(View view){
        formattedDate = Utility.addDateToString(formattedDate,-1);
        scheduledDate.setText(formattedDate);
        getData();
    }

    public void nextClicked(View view){
        formattedDate = Utility.addDateToString(formattedDate,1);
        scheduledDate.setText(formattedDate);
        getData();
    }

    public void setMeeting(View view){
        Intent setMeetingActivity = new Intent(this,ScheduleMeeting.class);
        setMeetingActivity.putExtra("CHOSEN_DATE", formattedDate);
        startActivity(setMeetingActivity);
    }

    private void getData(){
        Schedulerservice serviceClass = new Schedulerservice();
        serviceClass.fetchData(formattedDate).enqueue(new Callback<ArrayList<SchedulerModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SchedulerModel>> call, Response<ArrayList<SchedulerModel>> response) {
                model = response.body();
                ListAdapter adapter = new ListAdapter(model);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<SchedulerModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this,R.string.error,Toast.LENGTH_LONG).show();
            }
        });
    }
}

package com.vinsol.scheduler;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.vinsol.scheduler.Models.SchedulerModel;
import com.vinsol.scheduler.Services.Schedulerservice;

import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleMeeting extends AppCompatActivity {

    DatePicker datePicker;
    TimePicker timePicker;
    Button startMeetingText;
    Button startTimeText;
    Button endTimeText;
    EditText descriptionText;
    Button okButton;
    Button submitButton;
    TextView startMeetingDate;
    TextView startTimesText;
    TextView endTimesText;
    String chosenDateString = null;
    int startHour = -1;
    int startMinute = -1;
    int endHour = -1;
    int endMinute= -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_meeting);
        datePicker =  findViewById(R.id.date_picker);
        timePicker =  findViewById(R.id.time_picker);
        timePicker.setIs24HourView(true);
        startMeetingText = findViewById(R.id.meeting_date_btn);
        startTimeText =  findViewById(R.id.start_time_btn);
        startMeetingDate = findViewById(R.id.meeting_date);
        endTimeText = findViewById(R.id.end_time_btn);
        descriptionText =  findViewById(R.id.description_text);
        okButton = findViewById(R.id.ok_btn);
        submitButton = findViewById(R.id.submit_btn);
        startTimesText = findViewById(R.id.start_time);
        endTimesText = findViewById(R.id.end_time);
        chosenDateString = getIntent().getStringExtra("CHOSEN_DATE");
        startMeetingDate.setText(chosenDateString);
    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            if(Utility.stringToDate(chosenDateString).before(Utility.todayInDate())){
                submitButton.setBackgroundResource(R.color.gray);
                submitButton.setEnabled(false);
            }else{
                submitButton.setBackgroundResource(R.color.colorPrimary);
                submitButton.setEnabled(true);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void goBack (View view){
        onBackPressed();
    }

    public void setNewMeeting(View view){
        if(startHour == -1 || startMinute == -1 || endHour ==-1 || endMinute == -1 || chosenDateString == null){
            Toast.makeText(this,"Fields Required",Toast.LENGTH_LONG).show();
            return;
        }
        Schedulerservice service = new Schedulerservice();
        service.fetchData(chosenDateString).enqueue(new Callback<ArrayList<SchedulerModel>>() {
            @Override
            public void onResponse(Call<ArrayList<SchedulerModel>> call, Response<ArrayList<SchedulerModel>> response) {
               ArrayList<SchedulerModel> models = response.body();
               if(models != null){
                   for (int i = 0; i < models.size(); i++){
                       if((Utility.compareTiming(models.get(i).startTime,startHour+":"+startMinute) && Utility.compareTiming(startHour+":"+startMinute+":"+startMinute,models.get(i).endTime)) ||
                               (Utility.compareTiming(models.get(i).startTime,endHour+":"+endMinute) && Utility.compareTiming(endHour+":"+endMinute+":"+startMinute,models.get(i).endTime))){
                           Toast.makeText(ScheduleMeeting.this,R.string.slots_not_available_text,Toast.LENGTH_LONG).show();
                           return;
                       }
                   }
               }
               Toast.makeText(ScheduleMeeting.this,R.string.slots_available_text,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ArrayList<SchedulerModel>> call, Throwable t) {
                Toast.makeText(ScheduleMeeting.this,R.string.error,Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showDatePicker (View view){
        hideButtons();
        datePicker.setVisibility(View.VISIBLE);
        okButton.setTag("date_clicked");
    }

    public void showTimePicker (View view){
        hideButtons();
        if(view.getTag().equals("start_time_clicked")){
            okButton.setTag("start_time_clicked");
        }
        if(view.getTag().equals("end_time_clicked")){
            okButton.setTag("end_time_clicked");
        }
        timePicker.setVisibility(View.VISIBLE);
    }
    public void okButtonPressed(View view) throws ParseException {
        showButtons();
        Date chosenDate = getDateFromDatePicker(datePicker);
        String chosenDateString = Utility.dateFormatToString(chosenDate);
        if(Utility.stringToDate(chosenDateString).before(Utility.todayInDate())){
            submitButton.setBackgroundResource(R.color.gray);
            submitButton.setEnabled(false);
        }else{
            submitButton.setBackgroundResource(R.color.colorPrimary);
            submitButton.setEnabled(true);
        }
        startMeetingDate.setText(chosenDateString);
        int hour = timePicker.getCurrentHour();
        int minute = timePicker.getCurrentMinute();
        if(view.getTag().equals("start_time_clicked")){
            startHour = hour;
            startMinute = minute;
            startTimesText.setText(hour+":"+minute);
        }
        if(view.getTag().equals("end_time_clicked")){
            endHour = hour;
            endMinute = minute;
            endTimesText.setText(hour+":"+minute);
        }
    }

    private void hideButtons(){
        startMeetingText.setVisibility(View.GONE);
        startTimeText.setVisibility(View.GONE);
        endTimeText.setVisibility(View.GONE);
        submitButton.setVisibility(View.GONE);
        descriptionText.setVisibility(View.GONE);
        okButton.setVisibility(View.VISIBLE);
    }

    private void showButtons(){
        startMeetingText.setVisibility(View.VISIBLE);
        startTimeText.setVisibility(View.VISIBLE);
        endTimeText.setVisibility(View.VISIBLE);
        submitButton.setVisibility(View.VISIBLE);
        descriptionText.setVisibility(View.VISIBLE);
        datePicker.setVisibility(View.GONE);
        timePicker.setVisibility(View.GONE);
        okButton.setVisibility(View.GONE);
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }


}

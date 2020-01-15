package com.vinsol.scheduler.Services;

import android.support.v7.widget.LinearLayoutManager;
import android.widget.Toast;

import com.vinsol.scheduler.Adapter.ListAdapter;
import com.vinsol.scheduler.Interface.ApiInterface;
import com.vinsol.scheduler.MainActivity;
import com.vinsol.scheduler.Models.SchedulerModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Schedulerservice {
    ArrayList<SchedulerModel> model;

    public Call<ArrayList<SchedulerModel>> fetchData(String date){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fathomless-shelf-5846.herokuapp.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiService = retrofit.create(ApiInterface.class);
        return apiService.listRepos(date);
    }
}

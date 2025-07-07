package com.example.fieldtrainingproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerJobs;
    JobAdapter jobAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        recyclerJobs = findViewById(R.id.jobsRecyclerView);
        BottomNavigationView bottomNav = findViewById(R.id.bottomNavigationView);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_settings) {
                    startActivity(new Intent(MainActivity.this, SettingActivity.class));
                    return true;
                } else if (id == R.id.nav_bookmark) {
                    startActivity(new Intent(MainActivity.this, bookmarkScreen.class));
                    return true;
                }
                return false;
            }
        });




        fetchJobs();




    }

    private void fetchJobs() {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.getAllJobs().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    List<job> jobs = response.body().data;
                    if (jobs != null) {
                        Log.d("API_RESPONSE", "Jobs count: " + jobs.size());
                        jobAdapter = new JobAdapter(MainActivity.this, jobs);
                        recyclerJobs.setAdapter(jobAdapter);
                        Log.d("FETCH_JOBS", "Response: " + response.toString());
                        Log.d("FETCH_JOBS", "Body: " + new Gson().toJson(response.body()));
                    } else {
                        Log.e("FETCH_JOBS", "API Response successful but data is null.");
                        Toast.makeText(MainActivity.this, "لا توجد بيانات متاحة", Toast.LENGTH_SHORT).show();
                        jobAdapter = new JobAdapter(MainActivity.this, new java.util.ArrayList<>());
                        recyclerJobs.setAdapter(jobAdapter);
                    }
                } else {
                    String errorBody = "N/A";
                    try {
                        if (response.errorBody() != null) {
                            errorBody = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        Log.e("FETCH_JOBS", "Error parsing errorBody: " + e.getMessage());
                    }
                    Log.e("FETCH_JOBS", "Response unsuccessful or empty: Code " + response.code() + ", Message: " + response.message() + ", Error Body: " + errorBody);
                    Toast.makeText(MainActivity.this, "فشل في جلب البيانات: " + response.message(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("FETCH_JOBS", "Failure: " + t.getMessage(), t);
                Toast.makeText(MainActivity.this, "خطأ في الاتصال: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




}
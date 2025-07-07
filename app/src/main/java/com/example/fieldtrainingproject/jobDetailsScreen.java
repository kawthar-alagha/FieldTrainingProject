package com.example.fieldtrainingproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class jobDetailsScreen extends AppCompatActivity {

    TextView textJobTitle, textCompanyName, textJobType, textWorkField,
            textCountryEmployment, textSalaryWage, textExperience,
            textDescription, textNationality, textResidence, textGender;

    MaterialButton buttonApply;
    ImageView bookmarkIcon;

    boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_screen);

        // ربط العناصر
        textJobTitle = findViewById(R.id.text_job_title_details);
        textCompanyName = findViewById(R.id.text_company_name_details);
        textJobType = findViewById(R.id.text_job_type);
        textWorkField = findViewById(R.id.text_work_field);
        textCountryEmployment = findViewById(R.id.text_country_of_employment);
        textSalaryWage = findViewById(R.id.text_salary_wage);
        textExperience = findViewById(R.id.text_required_experience);
        textDescription = findViewById(R.id.text_job_description_details);
        textNationality = findViewById(R.id.text_nationality);
        textResidence = findViewById(R.id.text_country_residence);
        textGender = findViewById(R.id.text_gender);
        buttonApply = findViewById(R.id.button_apply_job);
        bookmarkIcon = findViewById(R.id.icon_bookmark_details);

        findViewById(R.id.icon_back).setOnClickListener(v -> finish());

        int jobId = getIntent().getIntExtra("job_id", -1);
        if (jobId != -1) {
            getJobDetails(jobId);
        } else {
            Toast.makeText(this, "Invalid Job ID", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void getJobDetails(int jobId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        Call<JobDetailsResponse> call = apiService.getJobDetails(jobId);

        call.enqueue(new Callback<JobDetailsResponse>() {
            @Override
            public void onResponse(Call<JobDetailsResponse> call, Response<JobDetailsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    job jobData = response.body().data;

                    textJobTitle.setText(jobData.title);
                    textCompanyName.setText(jobData.company_name);
                    textJobType.setText(jobData.salary);
                    if (jobData.work_field != null) {
                        if (jobData.work_field.isJsonObject()) {
                            String workField = jobData.work_field.getAsJsonObject().has("en")
                                    ? jobData.work_field.getAsJsonObject().get("en").getAsString()
                                    : jobData.work_field.getAsJsonObject().toString();
                            textWorkField.setText(workField);
                        } else if (jobData.work_field.isJsonPrimitive()) {
                            textWorkField.setText(jobData.work_field.getAsString());
                        } else {
                            textWorkField.setText("N/A");
                        }
                    }
                    if (jobData.country_of_employment != null && jobData.country_of_employment.isJsonObject()) {
                        String countryName = jobData.country_of_employment.getAsJsonObject().get("en").getAsString();
                        textCountryEmployment.setText(countryName);
                    } else if (jobData.country_of_employment.isJsonPrimitive()) {
                        textCountryEmployment.setText(jobData.country_of_employment.getAsString());
                    }
                    textSalaryWage.setText(jobData.salary);
                    textExperience.setText(jobData.experience);
                    textDescription.setText(jobData.description);
                    textNationality.setText(jobData.nationality);
                    textResidence.setText(jobData.residence);
                    textGender.setText(jobData.gender);

                    isFavorite = jobData.is_favorite;
                    updateBookmarkIcon();

                    bookmarkIcon.setOnClickListener(v -> toggleFavorite(jobData.id));

                    buttonApply.setOnClickListener(v -> applyToJob(jobData.id));
                } else {
                    Toast.makeText(jobDetailsScreen.this, "Failed to load job details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JobDetailsResponse> call, Throwable t) {
                Toast.makeText(jobDetailsScreen.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateBookmarkIcon() {
        if (isFavorite) {
            bookmarkIcon.setImageResource(R.drawable.ic_bookmark_filled);
        } else {
            bookmarkIcon.setImageResource(R.drawable.ic_bookmark_border);
        }
    }

    private void toggleFavorite(int jobId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.markFavorite(jobId).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    isFavorite = !isFavorite;
                    updateBookmarkIcon();
                    Toast.makeText(jobDetailsScreen.this, "تم تحديث المفضلة", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(jobDetailsScreen.this, "فشل في تحديث المفضلة", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(jobDetailsScreen.this, "خطأ: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void applyToJob(int jobId) {
        ApiService apiService = RetrofitClient.getInstance().create(ApiService.class);
        apiService.applyJob(jobId).enqueue(new Callback<GenericResponse>() {
            @Override
            public void onResponse(Call<GenericResponse> call, Response<GenericResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().success) {
                    Toast.makeText(jobDetailsScreen.this, "تم التقديم بنجاح", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(jobDetailsScreen.this, companyProfile.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(jobDetailsScreen.this, "فشل في التقديم", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(jobDetailsScreen.this, "خطأ: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

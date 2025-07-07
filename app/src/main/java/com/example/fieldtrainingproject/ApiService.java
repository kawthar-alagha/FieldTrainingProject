package com.example.fieldtrainingproject;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @GET("job-seeker/all-jobs")
    Call<ApiResponse> getAllJobs();

    @GET("job-seeker/job-details/{id}")
    Call<JobDetailsResponse> getJobDetails(@Path("id") int id);

    @POST("job-seeker/jobs/{id}/mark-favorite")
    Call<GenericResponse> markFavorite(@Path("id") int id);

    @GET("job-seeker/favorite-jobs")
    Call<ApiResponse> getFavoriteJobs();

    @Multipart
    @POST("job-seeker/jobs/applied/{id}")
    Call<GenericResponse> applyJob(
            @Path("id") int id,
            @Part MultipartBody.Part vedio
    );

    @POST("job-seeker/jobs/applied/{id}")
    Call<GenericResponse> applyJob(@Path("id") int id);

    @GET("all-companies")
    Call<CompanyResponse> getAllCompanies();

    @GET("faqs")
    Call<FaqResponse> getFaqs();

    @GET("policies")
    Call<PoliciesResponse> getPolicies();
}

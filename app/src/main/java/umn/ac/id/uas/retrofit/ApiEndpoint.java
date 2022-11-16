package umn.ac.id.uas.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiEndpoint {
    @GET("index.php")
    Call<GymModel> getGym();
}

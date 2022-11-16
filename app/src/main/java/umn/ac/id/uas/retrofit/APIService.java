package umn.ac.id.uas.retrofit;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {
    private static String base_URL = "https://nabil.atras.my.id/";
    private static Retrofit retrofit;

    public static ApiEndpoint endpoint(){
        retrofit = new Retrofit.Builder().baseUrl(base_URL).addConverterFactory(GsonConverterFactory.create()).build();

        return retrofit.create(ApiEndpoint.class);
    }
}

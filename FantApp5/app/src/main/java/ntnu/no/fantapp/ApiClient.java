package ntnu.no.fantapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private final static String BASE_URL = "http://10.0.2.2:8080/Oblig1H2v2/resources/";
    private static ApiClient SINGLETON;
    private Retrofit retrofit;

    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public static synchronized ApiClient getSingleton(){
        if(SINGLETON == null){
            SINGLETON = new ApiClient();
        }
        return SINGLETON;
    }
    public FantApi getApi(){
        return retrofit.create(FantApi.class);
    }
}

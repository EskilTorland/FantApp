package ntnu.no.fantapp;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface FantApi {

    @FormUrlEncoded
    @POST("auth/create")
    Call<ResponseBody> createUser(
            @Field("uid") String name,
            @Field("pwd") String password,
            @Field("email") String email);

    @GET("auth/login")
    Call<ResponseBody> loginUser(
            @Query("uid") String name,
            @Query ("pwd") String password
    );

}

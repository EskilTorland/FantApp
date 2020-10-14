package ntnu.no.fantapp;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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
            @Query("pwd") String password
    );

    @FormUrlEncoded
    @POST("fant/add")
    Call<ResponseBody> addItem(@Header("Authorization") String token,
                               @Field("title") String title,
                               @Field("description") String description,
                               @Field("price") String price);

    @GET("fant/items")
    Call<List<Item>> getAllItems();

    @PUT("fant/purchase")
    Call<ResponseBody> purchaseItem(@Header("Authorization") String token,
                                    @Query("itemid") Long itemid);

}
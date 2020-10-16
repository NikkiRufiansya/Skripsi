package com.example.chatbot_stiki.Api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserService {
    @GET("selectTblMhs.php")
    Call<ResponseBody> getMahasiswa(@Query("id") String id);

    @FormUrlEncoded
    @POST("update_profile.php")
    Call<ResponseBody> updateProfile(@Field("id") String id,
                                     @Field("first_name") String first_name,
                                     @Field("last_name") String last_name,
                                     @Field("user_email") String user_email,
                                     @Field("image_url") String imageurl);
}

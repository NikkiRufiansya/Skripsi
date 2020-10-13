package com.example.chatbot_stiki.Api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {
    @GET("selectTblMhs.php")
    Call<ResponseBody> getMahasiswa(@Query("id") String id);
}

package com.example.kaixuan.worryfreetutor.net;

import retrofit2.Call;
import retrofit2.http.*;

public interface MeProtocol {


    @GET("user/info")
    Call<String> get_info(@Header("Authorization") String auth);

    @FormUrlEncoded
    @POST("user/update")
    Call<String> update_info(@Header("Authorization") String auth,@Field("nickname") String nn,@Field("sex") String sex, @Field("per_signature") String ps);

}

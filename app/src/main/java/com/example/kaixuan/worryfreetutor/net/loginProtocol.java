package com.example.kaixuan.worryfreetutor.net;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
// http://49.234.6.109
public interface loginProtocol{

    @FormUrlEncoded
    @POST("user/register")
    Call<String> mRegister(@Field("account") String ac, @Field("password") String pw);

    @FormUrlEncoded
    @POST("user/login")
    Call<String> mLogin(@Field("account") String ac, @Field("password") String pw);


}

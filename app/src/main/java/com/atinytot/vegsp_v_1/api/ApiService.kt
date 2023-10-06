package com.atinytot.vegsp_v_1.api

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    /**
     * TODO 登录
     */
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("username") username: String,
        @Field("password") password: String
    ): Call<ApiResponse>

    /**
     * TODO 注册
     */
    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("nickname") nickname: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("phone") phone: String
    ): Call<ApiResponse>
}
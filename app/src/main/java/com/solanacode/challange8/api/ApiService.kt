package com.solanacode.challange8.api

import com.solanacode.challange8.model.ItemResponseItem
import com.solanacode.challange8.model.UserResponseItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("user")
    fun signIn(): Call<ArrayList<UserResponseItem>>

    @POST("user")
    fun signUp(@Body user : UserResponseItem): Call<UserResponseItem>

    @GET("user/{id}")
    fun getDetailUser(@Path("id") id : String): Call<UserResponseItem>

    @PUT("user/{id}")
    fun updateUser(@Path("id") id : String,
                    @Body user : UserResponseItem
    ): Call<UserResponseItem>

//    film
    @GET("films/{id}")
    fun getDetailFilm(@Path("id") id : String): Call<ItemResponseItem>

    @GET("films")
    fun getAllfilm() : Call<List<ItemResponseItem>>



}
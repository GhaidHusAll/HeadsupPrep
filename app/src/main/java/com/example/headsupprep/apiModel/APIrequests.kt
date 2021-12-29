package com.example.headsupprep.apiModel

import retrofit2.Call
import retrofit2.http.*

interface APIrequests {
    @GET("/celebrities/")
    fun fetchData(): Call<Celebrities>

    @POST("/celebrities/")
    fun addData(@Body data: CelebritiesItem): Call<CelebritiesItem>

    @PUT("/celebrities/{id}")
    fun updateData(@Path ("id") id:Int , @Body data: CelebritiesItem) : Call<CelebritiesItem>

    @DELETE("/celebrities/{id}")
    fun deleteData(@Path ("id") id:Int) : Call<Void>
}
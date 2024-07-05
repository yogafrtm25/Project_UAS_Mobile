package co.id.kasrt.network

import co.id.kasrt.model.ResponseUser
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(".")
    fun getUsers(): Call<ResponseUser>
    @GET(".")
    fun getPemanfaatan(): Call<ResponseUser>

}
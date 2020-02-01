package com.mlm09kdev.superHeroDB.model.network

import com.google.gson.GsonBuilder
import com.mlm09kdev.superHeroDB.BuildConfig
import com.mlm09kdev.superHeroDB.model.network.response.SearchResponse
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Manuel Montes de Oca on 1/28/2020.
 */
const val BASE_URL ="https://www.superheroapi.com/api.php/"+ BuildConfig.API_TOKEN+"/"

interface SuperHeroAPIService {

   /* @GET("{id}")
    suspend fun getSuperHeroById(@Path(value = "id") id: String): SuperHeroResponse
*/

    @GET("search/{name}")
    suspend fun getSuperHeroList(@Path(value = "name") name: String): SearchResponse

    companion object{
        operator fun invoke(connectionInterceptor: ConnectionInterceptor): SuperHeroAPIService {

            //Check Connecting using connectionInterceptor then build client with the result
            val okHttpClient = OkHttpClient.Builder().addInterceptor(connectionInterceptor).build()

            return Retrofit.Builder().client(okHttpClient).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build().create(SuperHeroAPIService::class.java)
        }
    }

}
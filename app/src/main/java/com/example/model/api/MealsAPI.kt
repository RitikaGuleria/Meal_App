package com.example.model.api

import com.example.model.response.MealsCategoriesResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


//Meals api is class that will actually return the retrofit response from the actual request
class MealsAPI {
    private lateinit var api:Mealsapi  //instantite it later
    init {
        val retrofit= Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api=retrofit.create(Mealsapi::class.java)
    }

//    fun getMeals(): Call<MealsCategoriesResponse>{
//        return api.getMeals()
//    }
//
//    //interface
//    interface Mealsapi{
//        @GET("categories.php")
//        fun getMeals(): Call<MealsCategoriesResponse>
//    }


    //retrofit supports coroutines
    suspend fun getMeals(): MealsCategoriesResponse{
        return api.getMeals()
    }

    //interface
    interface Mealsapi{
        @GET("categories.php")
        suspend fun getMeals(): MealsCategoriesResponse
    }
}
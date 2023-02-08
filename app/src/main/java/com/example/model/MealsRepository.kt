package com.example.model


import com.example.model.api.MealsAPI
import com.example.model.response.MealsCategoriesResponse
import com.example.model.response.MealsReponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MealsRepository(private val webservice:MealsAPI=MealsAPI()) {

    private var cachedMeals = listOf<MealsReponse>()

    suspend fun getMeals(): MealsCategoriesResponse
    {
//        return webservice.getMeals()
        val response=webservice.getMeals()
        cachedMeals=response.categories
        return response
    }

    fun getMeal(id: String): MealsReponse?{
        return cachedMeals.firstOrNull(){
            it.id==id
        }
    }

    companion object{
        @Volatile
        private var instance: MealsRepository?=null
        fun getInstance() = instance?: synchronized(this){
            instance ?: MealsRepository().also { instance=it }
        }
    }
}



//inside arraylistof we have to make request of the data,then response will be passed to
// viewmodel class

//class MealsRepository(private val webservice:MealsAPI=MealsAPI()) {
//    fun getMeals(successCallback:(response:MealsCategoriesResponse?)-> Unit){   // beacuse this seems sync  :MealsCategoriesResponse?{=MealsCategoriesResponse(arrayListOf())
//        return webservice.getMeals().enqueue(object: Callback<MealsCategoriesResponse>{ //.execute().body()
//            override fun onResponse(
//                call: Call<MealsCategoriesResponse>,
//                response: Response<MealsCategoriesResponse>
//            ) {
//                    //we will vet response here only
//                    if(response.isSuccessful)
//                        successCallback(response.body())
//            }
//
//            override fun onFailure(call: Call<MealsCategoriesResponse>, t: Throwable) {
//                //ToDo treat failure
//            }
//
//        })
//    }
//}
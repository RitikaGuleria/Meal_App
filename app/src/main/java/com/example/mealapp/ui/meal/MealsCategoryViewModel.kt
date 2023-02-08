package com.example.mealapp.ui.meal


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.MealsRepository
import com.example.model.response.MealsCategoriesResponse
import com.example.model.response.MealsReponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

//viewmodel holds a reference of repository class which here we do in parameter

class MealsCategoriesViewModel(private val repository: MealsRepository= MealsRepository.getInstance()):ViewModel()
{
    //    private val mealsJob= Job()
    init {
//        val scope = CoroutineScope(mealsJob + Dispatchers.IO)
        Log.d("TAG_COROUTINES","We're about to launch coroutines") //1
        viewModelScope.launch {
            Log.d("TAG_COROUTINES","We've launched to launch coroutines") //3
            val meals = getMeals()
            Log.d("TAG_COROUTINES","We've receievd the async data") //4
            mealsState.value = meals
        }
        Log.d("TAG_COROUTINES","Other work") //2
    }
    val mealsState: MutableState<List<MealsReponse>> = mutableStateOf(emptyList<MealsReponse>())

//    override fun onCleared() {
//        super.onCleared()
//        mealsJob.cancel()
//    }

    private suspend fun getMeals(): List<MealsReponse> //here MealsResponse is used than MelasCategoryResponse,bcy viewmodel is responsible for preparing a data here
    {
        return repository.getMeals().categories  //here we want content as the response so categories have added after dot
    }
}

//class MealsCategoriesViewModel(private val repository: MealsRepository= MealsRepository()):ViewModel()
//{
//    fun getMeals(successCallback:(response: MealsCategoriesResponse?)-> Unit){ //: List<MealsReponse>
//        repository.getMeals(){ response ->
//            successCallback(response)
//        }//?.categories.orEmpty()
//    }
//}
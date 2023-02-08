package com.example.mealapp.ui.detailsSecondScreen

import android.view.View
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.model.MealsRepository
import com.example.model.response.MealsReponse

class MealDetailsViewModel(private val savedStateHandle: SavedStateHandle): ViewModel()
{
    //savedstatehandle has information of navigation
    private val repository: MealsRepository= MealsRepository.getInstance()

    var mealState= mutableStateOf<MealsReponse?>(null)

    init {
        val mealId=savedStateHandle.get<String>("meal_category_id")?:""
        mealState.value= repository.getMeal(mealId)
    }
}
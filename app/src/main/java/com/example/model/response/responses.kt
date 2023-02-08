package com.example.model.response

import com.google.gson.annotations.SerializedName


//for Deserialization(converting json data format to data class) to take the data from json and add
// in our data model classes, this sep should be taken

data class MealsCategoriesResponse(val categories:List<MealsReponse>)

data class MealsReponse(
    @SerializedName("idCategory") val id:String,
    @SerializedName("strCategory") val name:String,
    @SerializedName("strCategoryDescription") val description:String,
    @SerializedName("strCategoryThumb") val imageUrl:String
)
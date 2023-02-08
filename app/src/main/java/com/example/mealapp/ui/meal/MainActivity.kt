package com.example.mealapp.ui.meal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberAsyncImagePainter
import com.example.mealapp.ui.detailsSecondScreen.MealDetailScreen
import com.example.mealapp.ui.detailsSecondScreen.MealDetailsViewModel
import com.example.mealapp.ui.theme.MealAppTheme
import com.example.model.response.MealsReponse

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MealAppTheme {
                FoodApp()
            }
        }
    }
}

@Composable
private fun FoodApp()
{
    val navController= rememberNavController()
    NavHost(navController = navController, startDestination = "destination_meals_list")
    {
        composable(route="destination_meals_list"){
            MealsCatorgiesScreen{ navigationMealId->  //it should listen the update from 1st screen when click happens id will be know then
                navController.navigate("destination_meals_details/$navigationMealId")
            }
        }
        composable(route="destination_meals_details/{meal_category_id}",arguments= listOf(navArgument("meal_category_id"){
            type= NavType.StringType })) {

            val viewModel: MealDetailsViewModel= viewModel()
            MealDetailScreen(viewModel.mealState.value,navController)
        }
    }
}


@Composable
fun MealsCatorgiesScreen(navigationCallBack:(String)->Unit)
{
    val viewModel: MealsCategoriesViewModel = viewModel() //view holds reference of viewModel

    //hoisting up the coroutines
    val meals= viewModel.mealsState.value

    Scaffold(topBar = { AppBar(title = "Meals Of Different Categories", icon = Icons.Filled.Home) })
    {

        LazyColumn(contentPadding = PaddingValues(16.dp))
        {
            items(meals) { meal ->
                MealCategoryUIDesign(meal, navigationCallBack)
            }
        }
    }


    //using coroutines instead of callbacks

//    val rememberedMeals: MutableState<List<MealsReponse>> = remember{ mutableStateOf(emptyList<MealsReponse>())}

//    val coroutineScope= rememberCoroutineScope()
//
//    LaunchedEffect(key1 ="GET_MEALS"){
//        coroutineScope.launch(Dispatchers.IO){
//            val meals=viewModel.getMeals()
//            rememberedMeals.value=meals
//        }
//    }
//        LazyColumn{
//        items(rememberedMeals.value){ meal->
//            Text(text = meal.name)
//        }
//    }

//view has to display the data which api has taken from the model or repository
//    viewModel.getMeals(){ response->
//        val mealsFromTheAPI= response?.categories
//        rememberedMeals.value=mealsFromTheAPI.orEmpty()
//    }
//    LazyColumn{
//        items(rememberedMeals.value){ meal->
//            Text(text = meal.name)
//        }
//    }

}

@Composable
fun MealCategoryUIDesign(meal: MealsReponse,navigationCallBack:(String)->Unit){

    var isExpanded by remember{ mutableStateOf(false)}


        Card(shape = RoundedCornerShape(8.dp),elevation=4.dp, modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clickable { navigationCallBack(meal.id) })
        {
            Row(modifier=Modifier.animateContentSize())
            {
                //Image
                Image(painter = rememberAsyncImagePainter(meal.imageUrl), contentDescription =null,
                    modifier= Modifier
                        .size(88.dp)
                        .padding(4.dp)
                        .align(Alignment.CenterVertically)
                )

                Column(modifier= Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.8f)
                    .padding(16.dp))
                {
                    Text(text = meal.name,style= MaterialTheme.typography.h6)
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(text=meal.description,style=MaterialTheme.typography.subtitle2,
                            textAlign = TextAlign.Start,
                            overflow= TextOverflow.Ellipsis, maxLines = if (isExpanded) 16 else 4)
                    }
                }
                Icon(imageVector = if(isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown, contentDescription = "Expand icon",
                    modifier= Modifier
                        .padding(16.dp)
                        .align(
                            if (isExpanded) Alignment.Bottom
                            else Alignment.CenterVertically)
                        .clickable { isExpanded = !isExpanded }
                )
            }
        }

}

@Composable
fun AppBar(title:String, icon: ImageVector)
{
    TopAppBar(navigationIcon = { icon}, title = { Text(text = title)})
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MealAppTheme {
        MealsCatorgiesScreen({})
    }
}
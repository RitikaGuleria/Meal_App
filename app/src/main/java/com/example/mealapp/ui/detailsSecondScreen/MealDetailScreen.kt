package com.example.mealapp.ui.detailsSecondScreen

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.min
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.model.response.MealsReponse
import kotlin.math.min

@Composable
fun MealDetailScreen(meal:MealsReponse?,navController: NavHostController)
{
//    var isExpanded by remember { mutableStateOf(false) }
//    val imageSizeDp: Dp by animateDpAsState(
//        targetValue = if (isExpanded) 200.dp else 60.dp
//    )


//    var profilePictureState by remember{ mutableStateOf(MealProfilePictureState.Normal) }
//    val transition= updateTransition(targetState = profilePictureState,label="")
//    val imageSizeDP by transition.animateDp(targetValueByState = { it.size }, label="")
//    val color by transition.animateColor(targetValueByState = { it.color },label="")
//    val widthSize by transition.animateDp(targetValueByState = { it.borderWidth}, label="")


    val scrollState= rememberLazyListState()
    val offset= min(1f,1-(scrollState.firstVisibleItemScrollOffset / 600f + scrollState.firstVisibleItemIndex ))

    val size by animateDpAsState(targetValue = max(100.dp,140.dp*offset))

    Scaffold(topBar = { AppBar(title = "Meal Detail Screen", icon = Icons.Filled.ArrowBack) {
        navController?.navigateUp()
    }})
    {
        Surface(color=MaterialTheme.colors.background)
        {
            Column()
            {
                Surface(elevation = 4.dp)
                {
                    Row()
                    {
                        Card(modifier = Modifier.padding(16.dp),
                            shape = CircleShape,
                            border = BorderStroke(width = 2.dp, color = Color.Green))
                        {
                            Image(painter = rememberImagePainter(data = meal?.imageUrl, builder = {
                                CircleCropTransformation()
                            }), contentDescription = null, modifier = Modifier
                                .size(size)
                                .padding(8.dp))
                        }
                        Text(text = meal?.name ?: "default name", modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.CenterVertically))
                    }
//            Button(modifier=Modifier.padding(16.dp),onClick = { profilePictureState = if (profilePictureState == MealProfilePictureState.Normal)
//                MealProfilePictureState.Expand else MealProfilePictureState.Normal })
//            {
//                Text(text = "Zoom Image of meal",modifier=Modifier.wrapContentSize(Alignment.Center))
//            }
                }

                val dummyContentList = (0..100).map { it.toString() }
                LazyColumn(state=scrollState) {
                    items(dummyContentList) { dummyItem ->
                        Text(text = dummyItem, modifier = Modifier.padding(24.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun AppBar(title:String, icon: ImageVector, iconClickAction:()->Unit)
{
    TopAppBar(navigationIcon = { icon},modifier=Modifier.clickable { iconClickAction.invoke() }
        , title = { Text(text = title)})
}

enum class MealProfilePictureState(val color:Color, val size: Dp, val borderWidth: Dp){
    Normal(Color.LightGray,100.dp,2.dp),
    Expand(Color.Green,200.dp,3.dp)
}
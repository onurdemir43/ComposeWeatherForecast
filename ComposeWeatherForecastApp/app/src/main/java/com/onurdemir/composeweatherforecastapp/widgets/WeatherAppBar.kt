package com.onurdemir.composeweatherforecastapp.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {} ) {

    TopAppBar(title = {
        Text(text = title,
            color = Color(0xFFFFC400),
            style = TextStyle(fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp),
        )
    },
        actions = {
                  if (isMainScreen) {
                      IconButton(onClick = { /*TODO*/ }) {
                          Icon(
                              imageVector = Icons.Default.Search,
                              contentDescription = "search icon"
                          )
                      }
                      IconButton(onClick = { /*TODO*/ }) {
                          Icon(
                              imageVector = Icons.Rounded.MoreVert,
                              contentDescription = "more icon"
                          )
                      }
                  }else {
                      Box{}
                  }
        },
        navigationIcon = {

                         if (icon != null) {
                             Icon(imageVector = icon,
                                 contentDescription = null,
                                 tint = Color.LightGray,
                                 modifier = Modifier.clickable {
                                 onButtonClicked.invoke()
                             })
                         }


        },
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.Transparent))




}








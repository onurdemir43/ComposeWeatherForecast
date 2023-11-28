package com.onurdemir.composeweatherforecastapp.widgets

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.onurdemir.composeweatherforecastapp.model.Favorite
import com.onurdemir.composeweatherforecastapp.navigation.WeatherScreens
import com.onurdemir.composeweatherforecastapp.screens.favorite.FavoriteViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    navController: NavController,
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {} ) {

    val showDialog = remember {
        mutableStateOf(false)
    }

    if (showDialog.value) {
        ShowSettingDropDownMenu(showDialog = showDialog, navController = navController)
    }

    TopAppBar(title = {
        Text(text = title,
            color = Color(0xFFFFC400),
            style = TextStyle(fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp),
        )
    },
        actions = {
                  if (isMainScreen) {
                      IconButton(onClick = {
                          onAddActionClicked.invoke()
                      }) {
                          Icon(
                              imageVector = Icons.Default.Search,
                              contentDescription = "search icon"
                          )
                      }
                      IconButton(onClick = {
                          showDialog.value = true
                      }) {
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
                                 tint = Color.Gray,
                                 modifier = Modifier.clickable {
                                 onButtonClicked.invoke()
                             })
                         }
            if (isMainScreen) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite Icon",
                    modifier = Modifier
                        .scale(0.9f)
                        .clickable {
                            val dataList = title.split(",")
                            favoriteViewModel
                                .insertFavorite(
                                    Favorite(
                                        city = dataList[0], //city name
                                        country = dataList[1] //country code
                                    )
                                )

                        },
                    tint = Color.Red.copy(alpha = 0.6f)
                )
            }


        },
        colors = TopAppBarDefaults.smallTopAppBarColors(Color.Transparent))




}

@Composable
fun ShowSettingDropDownMenu(showDialog: MutableState<Boolean>, navController: NavController) {

    var expanded by remember {
        mutableStateOf(true)
    }

    val items = listOf("About","Favorites","Settings")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .absolutePadding(top = 60.dp, right = 20.dp)
    ) {
        DropdownMenu(expanded = expanded, onDismissRequest = {expanded = false},
        modifier = Modifier
            .width(140.dp)
            .background(Color.White)) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(
                    leadingIcon = {
                        Icon(imageVector = when(s) {
                            "About" -> Icons.Default.Info
                            "Favorites" -> Icons.Default.FavoriteBorder
                            else -> Icons.Default.Settings
                        }, contentDescription = null,
                            tint = Color.LightGray)
                    },
                    text = { Text(text = s, fontWeight = FontWeight.W700, modifier = Modifier.clickable {
                        navController.navigate(
                            when(s) {
                                "About" -> WeatherScreens.AboutScreen.name
                                "Favorites" -> WeatherScreens.FavoriteScreen.name
                                else -> WeatherScreens.SettingsScreen.name
                            })
                    })
                           },
                    onClick = {
                    expanded = false
                    showDialog.value = false
                })

            }
        }
    }
    
}








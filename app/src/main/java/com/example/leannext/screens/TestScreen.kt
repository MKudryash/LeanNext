package com.example.leannext.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.leannext.MainViewModel
import com.example.leannext.R

@Composable
fun TestScreen(
    navHostController: NavHostController,     viewModel: MainViewModel
) {
    var search: String by rememberSaveable { mutableStateOf("") }
    val allDirections by viewModel.allDirection.observeAsState(listOf())
    val searchDirection by viewModel.searchDirections.observeAsState(listOf())
    var searching by remember { mutableStateOf(false) }



    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomSearchView(search = search, onValueChange = {
            search = it
            searching = !search.isEmpty()
            viewModel.getDirectionsWithText(search)
        })

        val list = if (searching) searchDirection else allDirections
        if (list != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 92.dp),
            )
            {
                items(list) { item ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(25.dp, 10.dp)
                            .weight(1f)
                            .height(85.dp)
                            .clickable(onClick =
                            {
                                viewModel.getItemsCriterias(item.id!!)
                                viewModel.getAnswerCriteries(item.id)
                                navHostController.navigate("directionTestScreen/" + item.id+"/"+item.title)

                            }),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                          val context = LocalContext.current
                          val drawableId = remember(item.nameIcon) {
                              context.resources.getIdentifier(
                                  item.nameIcon,
                                  "drawable",
                                  context.packageName
                              )
                          }
                        Icon(
                            modifier = Modifier.weight(2f),
                            painter = painterResource(id = drawableId),
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.secondary
                        )
                        Text(
                            modifier = Modifier
                                .weight(8f)
                                .padding(10.dp, 0.dp, 0.dp, 0.dp),
                            text = item.title,
                            color = MaterialTheme.colorScheme.secondary,
                            fontFamily = FontFamily(Font(R.font.neosanspro_bold)),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,

                            )

                    }
                    if (item.id!! < list.size) {
                        Divider(
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 2.dp,
                            modifier = Modifier.padding(15.dp, 10.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchView(
    search: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit
) {
    Box(
        modifier = modifier
            .padding(20.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0XFFF5F5F9))

    ) {
        TextField(
            value = search,
            onValueChange = onValueChange,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color(0XFFF5F5F9),
                focusedIndicatorColor = Color.Transparent,
                focusedTextColor = Color.Black,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = MaterialTheme.colorScheme.onTertiary
            ),
            modifier = Modifier.background(Color(0XFFF5F5F9)),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search, contentDescription = "",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            },
            placeholder = {
                Text(
                    text = "Искать направление",
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        )

    }
}
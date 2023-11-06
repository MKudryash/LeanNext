package com.example.leannext.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
fun DirectionTestScreen(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory),
    navHostController: NavHostController,
    id: Int,
    name: String
) {
    val itemsListCriterias =
        mainViewModel.foundItemCriteriasForDirection(id).collectAsState(initial = emptyList())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(7.dp, 25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0XFFF5F5F9)), contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.padding(7.dp),
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
            Text(
                modifier = Modifier
                    .weight(8f)
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
                text = name,
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.neosanspro_medium)),
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
        }
        LazyColumn(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 92.dp),
        ) {
            items(itemsListCriterias.value) { item ->
                var count = item.id % 3
                if (count == 0) count = 3
                var progressWeight: Float = count.toFloat()
                var allWeight: Float = 3f - count.toFloat()
                Column(

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${count} вопрос",
                            fontFamily = FontFamily(Font(R.font.neosanspro_bold)),
                            fontSize = 24.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(7.dp, 5.dp)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(5.dp, 0.dp)
                                .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                        )

                        {
                            Text(
                                text = "i",
                                fontFamily = FontFamily(Font(R.font.neosanspro_bold)),
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.padding(12.dp, 3.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp, 10.dp, 20.dp, 20.dp)
                            .border(2.dp, Color.Transparent, RoundedCornerShape(5.dp))
                    ) {
                        Divider(
                            modifier = Modifier.weight(progressWeight),
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 6.dp
                        )
                        if (allWeight > 0) {
                            Divider(
                                modifier = Modifier.weight(allWeight),
                                color = Color(0xFFD9D9D9),
                                thickness = 6.dp
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp, 0.dp)
                            .border(
                                2.dp, Color(0xFFB8C1CC), RoundedCornerShape(20.dp)
                            ), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = item.title,
                            fontFamily = FontFamily(Font(R.font.neosanspro_bold)),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(7.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    val listState = rememberLazyListState()
                    var selectedIndex by remember { mutableStateOf(-1) }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        itemsIndexed(
                            listOf(
                                "1.0", "2.0", "3.0", "4.0", "5.0"
                            )

                        ) { index, item ->
                            var borderColor: Color
                            if (index == selectedIndex) borderColor =MaterialTheme.colorScheme.primary
                            else borderColor = Color(0xFFB8C1CC)
                            Box(
                                modifier = Modifier.border(
                                    2.dp, borderColor, RoundedCornerShape(20.dp)
                                )
                            ) {
                                Text(
                                    text = item,
                                    fontFamily = FontFamily(Font(R.font.neosanspro_regular)),
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .selectable(selected = index == selectedIndex, onClick = {
                                            if (selectedIndex != index) selectedIndex =
                                                index else selectedIndex = -1
                                        })
                                )
                            }
                        }
                    }
                    if (item.id < 2) {
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
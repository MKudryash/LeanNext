package com.example.leannext.bottom_navigation

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leannext.Models.DevelopmentIndex
import com.example.leannext.Models.Directions
import com.example.leannext.R


@Composable
fun DiagramScreen() {

    // Column Composable,
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        // Parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Text to Display the current Screen
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(10.dp, 35.dp),
                text = "Индекс развития технологий и инструментов Кайдзен (КDI)",
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.neosanspro_medium)),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Box(
                Modifier
                    .weight(1f)
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrowleft),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
            Column(
                Modifier.weight(4f),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(
                    text = "Еженедельный отчет",
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.neosanspro_bold)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "30 октября - 6 ноября",
                    modifier = Modifier.padding(top = 10.dp),
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.neosanspro_regular)),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Box(
                Modifier
                    .weight(1f)
                    .size(60.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrowright),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(top = 30.dp),
            painter = painterResource(id = R.drawable.daigramexample),
            contentDescription = ""
        )
        Box{
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 92.dp)
            )
            {
                itemsIndexed(
                    listOf(
                        DevelopmentIndex(1, 1, "5S и Визуальный менеджмент", 0.0),
                        DevelopmentIndex(1, 1, "Всеобщая эксплуатационная система ТРМ", 0.0),
                        DevelopmentIndex(1, 1, "Логистика", 0.0),
                        DevelopmentIndex(1, 1, "Картирование", 0.0),
                        DevelopmentIndex(1, 1, "5S и Визуальный менеджмент", 0.0),
                        DevelopmentIndex(1, 1, "Всеобщая эксплуатационная система ТРМ", 0.0),
                        DevelopmentIndex(1, 1, "Логистика", 0.0),
                        DevelopmentIndex(1, 1, "Картирование", 0.0)
                    )
                )
                { _, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(5.dp, 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Box(
                            Modifier
                                .weight(12f)
                                .wrapContentHeight()
                                .padding(7.dp, 0.dp)
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(20.dp)
                                ),
                            contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                modifier = Modifier.padding(0.dp, 15.dp),
                                text = item.idDirection,
                                color = MaterialTheme.colorScheme.secondary,
                                fontFamily = FontFamily(Font(R.font.neosanspro_regular)),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                            )
                        }
                        Box(
                            Modifier
                                .weight(2.3f)
                                .wrapContentHeight()
                                .padding(7.dp, 0.dp)
                                .border(
                                    2.dp,
                                    MaterialTheme.colorScheme.primary,
                                    RoundedCornerShape(20.dp)
                                ),
                            contentAlignment = Alignment.Center
                        )
                        {
                            Text(
                                modifier = Modifier.padding(0.dp, 15.dp),
                                text = item.mark.toString(),
                                color = MaterialTheme.colorScheme.secondary,
                                fontFamily = FontFamily(Font(R.font.neosanspro_regular)),
                                fontSize = 14.sp,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun TestScreen(onClick:()->Unit) {
    var search: String by rememberSaveable { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomSearchView(search = search, onValueChange = {
            search = it
        })
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 92.dp),
        )
        {
            itemsIndexed(
                listOf(
                    Directions(1, R.drawable.menegment, "5S и Визуальный менеджмент"),
                    Directions(2, R.drawable.system, "Всеобщая эксплуатационная система ТРМ"),
                    Directions(3, R.drawable.smed, "Быстрая переналадка SMED"),
                    Directions(4, R.drawable.standartwork, "Стандартизированная работа"),
                    Directions(5, R.drawable.carta, "Картирование"),
                    Directions(6, R.drawable.thread, "Выстраивание потока"),
                    Directions(7, R.drawable.personal, "Вовлечение персонала"),
                    Directions(8, R.drawable.personal, "Обучение персонала"),
                    Directions(9, R.drawable.menegment, "Логистика")
                )
            )
            { index, item ->
                if (index < 9)

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(25.dp, 10.dp)
                            .clickable(onClick =
                            {
                                onClick()
                            }),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.weight(2f),
                            painter = painterResource(id = item.idIcon),
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
                Divider(
                    color = MaterialTheme.colorScheme.primary,
                    thickness = 2.dp,
                    modifier = Modifier.padding(15.dp, 10.dp)
                )
            }

        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchView(search: String,   modifier: Modifier = Modifier,onValueChange: (String) -> Unit) {
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
                placeholderColor = Color(0XFFF5F5F9),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.onTertiary,
                cursorColor = MaterialTheme.colorScheme.onTertiary
            ),
            modifier = Modifier.background(Color(0XFFF5F5F9)),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "",
                tint = MaterialTheme.colorScheme.onTertiary) },
            placeholder = { Text(text = "Искать направление", color = MaterialTheme.colorScheme.onSecondary) }
        )

    }

}


@Preview
@Composable
fun DirectionTestScreen()
{
    Column(
        modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background),

    ){
        Row {
            Box(modifier = Modifier
                .padding(7.dp,20.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0XFFF5F5F9)),
                contentAlignment = Alignment.Center
            )
            {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "",
                    tint = MaterialTheme.colorScheme.onTertiary)
            }
            Text(
                modifier = Modifier
                    .weight(8f)
                    .padding(10.dp, 0.dp, 0.dp, 0.dp),
                text = "5S и Визуальный менеджмент",
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.neosanspro_medium)),
                fontSize = 16.sp,
                textAlign = TextAlign.Center,

                )

        }
    }

}
@Composable
fun ProfileScreen() {
    // Column Composable,
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        // Parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Text to Display the current Screen
        Text(text = "Test", color = Color(0xFFB8C1CC))
    }
}

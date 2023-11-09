package com.example.leannext.screens

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.Hyphens
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.leannext.MainViewModel
import com.example.leannext.MainViewModelFactory
import com.example.leannext.R
import com.example.leannext.db.modelsDb.DevelopmentIndex
import java.text.SimpleDateFormat
import java.util.Locale


val format = SimpleDateFormat("dd MMMM", Locale("ru"))

@Composable
fun RadarChartSample(itemsListDirectionIndex: List<DevelopmentIndex>?) {
    val labelDirection =
        listOf(
            "5S и Визуальный менеджмент",
            "Cистема ТРМ",
            "Быстрая переналадка SMED",
            "Стандартизированная работа",
            "Картирование",
            "Выстраивание\nпотока",
            "Вовлечение\nперсонала",
            "Обучение\nперсонала",
            "Логистика"
        )
//Сделать в одном цикле!
    var valuesDirection = arrayListOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    var indexDir = 1

    // iterate it using a mutable iterator and modify values
    val iterate = valuesDirection.listIterator()
    while (iterate.hasNext()) {
        iterate.next()
        itemsListDirectionIndex?.forEach {
            if (it.idDirection == indexDir) {
                iterate.set(it.mark)
            }
        }
        indexDir++
    }

    val labelsStyle = TextStyle(
        color = MaterialTheme.colorScheme.secondary,
        fontFamily = FontFamily(Font(R.font.neosanspro_bold)),
        fontSize = 10.sp,
        textAlign = TextAlign.Center,
        hyphens = Hyphens.Auto
    )

    val scalarValuesStyle = TextStyle(
        color = MaterialTheme.colorScheme.secondary,
        fontFamily = FontFamily(Font(R.font.neosanspro_regular)),
        fontSize = 10.sp
    )

    RadarChart(
        modifier = Modifier.size(400.dp),
        radarLabels = labelDirection,
        labelsStyle = labelsStyle,
        netLinesStyle = NetLinesStyle(
            netLineColor = Color(0x90ffD3CFD3),
            netLinesStrokeWidth = 3f,
            netLinesStrokeCap = StrokeCap.Butt
        ),
        scalarSteps = 5,
        scalarValue = 5.00,
        scalarValuesStyle = scalarValuesStyle,
        polygons = listOf(
            Polygon(
                values = valuesDirection,
                unit = "",
                style = PolygonStyle(
                    fillColor = Color(0x594CBBBF),
                    fillColorAlpha = 0.5f,
                    borderColor = MaterialTheme.colorScheme.primary,
                    borderColorAlpha = 0.5f,
                    borderStrokeWidth = 6f,
                    borderStrokeCap = StrokeCap.Butt,
                )
            )
        )
    )
}

@Composable
fun DiagramScreen(
    navHostController: NavHostController,
    viewModel: MainViewModel
) {

    val allDirections by viewModel.allDirection.observeAsState(listOf())
    val itemsForDiagram by viewModel.itemsAllDiagrams.observeAsState(listOf())
    val searchResults by viewModel.searchResults.observeAsState(listOf())
    var searching by remember { mutableStateOf(false) }


    // Column Composable,
    Column(
        modifier = Modifier
            .fillMaxSize(1f)
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
                .fillMaxWidth(1f)
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Box(
                Modifier
                    .weight(1f)
                    .size(60.dp)
                    .clickable {
                        --viewModel.week.value
                        viewModel.checkday()
                        viewModel.findDevelopmentIndex()
                        searching = viewModel.week.value != 0
                    },
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
                    text = "${format.format(viewModel.startDate.value)} - ${
                        format.format(
                            viewModel.endDate.value
                        )
                    }",
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
                    .size(60.dp)
                    .clickable {
                        ++viewModel.week.value
                        viewModel.checkday()
                        viewModel.findDevelopmentIndex()
                        searching = viewModel.week.value != 0
                    },
                contentAlignment = Alignment.Center

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.arrowright),
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }

        val list = if (searching) searchResults else itemsForDiagram
        RadarChartSample(list)


        Box {

            LazyColumn(
                modifier = Modifier.fillMaxSize(3f),
                contentPadding = PaddingValues(bottom = 92.dp)
            )
            {

                items(allDirections) { item ->
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
                                modifier = Modifier.padding(4.dp, 15.dp),
                                text = item.title,
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

                            var index = 0.0
                            list.forEach {
                                if (it.idDirection == item.id) index = it.mark
                            }
                            Text(
                                modifier = Modifier.padding(0.dp, 15.dp),
                                text = String.format("%.1f", index),
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




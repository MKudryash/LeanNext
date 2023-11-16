package com.example.leannext.screens

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.leannext.viewModel.MainViewModel
import com.example.leannext.R
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.utlis.ExportDataToCsv
import java.text.SimpleDateFormat
import java.util.Locale


val format = SimpleDateFormat("dd MMMM", Locale("ru"))

@Composable
fun RadarChartSample(itemsListDirectionIndex: List<DevelopmentIndex>?, w: Dp) {
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
        modifier = Modifier.size(w),
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

    var exportDataToCsv = ExportDataToCsv()
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    // Column Composable,
    BoxWithConstraints(
    ) {
        val derivedDimension = this.maxWidth
        var colorWeek = if (viewModel.week.value == 0) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.secondary
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
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
                    modifier = Modifier
                        .padding(derivedDimension * 0.04f, derivedDimension * 0.09f)
                        .weight(7f),
                    text = "Индекс развития технологий и инструментов Кайдзен (КDI)",
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.neosanspro_medium)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                )
                Box()
                {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.menuicon),
                            contentDescription = "Показать меню"
                        )
                    }
                    DropdownMenu(
                        modifier = Modifier
                            .border(
                                3.dp,
                                MaterialTheme.colorScheme.primary,
                                RoundedCornerShape(20.dp)
                            )
                            .background(MaterialTheme.colorScheme.background),
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    exportDataToCsv.createXlFile(
                                        itemsForDiagram,
                                        allDirections,
                                        format.format(viewModel.startDate.value),
                                        true,
                                        context
                                    )
                                }
                        )
                        {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.saveicon),
                                    contentDescription = ""
                                )
                            }
                            Text(
                                "Скачать",
                                fontFamily = FontFamily(Font(R.font.neosanspro_medium)),
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                        }
                        Divider(
                            color = MaterialTheme.colorScheme.primary,
                            thickness = 2.dp
                        )
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    exportDataToCsv.createXlFile(
                                        itemsForDiagram,
                                        allDirections,
                                        format.format(viewModel.startDate.value),
                                        false,
                                        context
                                    )
                                }
                        )
                        {
                            IconButton(onClick = { expanded = true }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.shareicon),
                                    contentDescription = ""
                                )
                            }
                            Text(
                                "Поделиться",
                                fontFamily = FontFamily(Font(R.font.neosanspro_medium)),
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .padding(10.dp)
                            )
                        }
                    }
                }
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
                        fontSize = 18.sp,
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
                        .clickable {
                            if (viewModel.week.value < 0) ++viewModel.week.value
                            viewModel.checkday()
                            viewModel.findDevelopmentIndex()
                            searching = viewModel.week.value != 0
                        },
                    contentAlignment = Alignment.Center,


                    ) {
                    Icon(
                        painter = painterResource(id = R.drawable.arrowright),
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.secondary
                    )
                }
            }

            val list = if (searching) searchResults else itemsForDiagram
            RadarChartSample(list, derivedDimension)
            var sum = 0.0
            list.forEach {
                sum += it.mark
            }
            try {
                sum /= allDirections.size
            } catch (ex: Exception) {
            }
            val colorItem = if (sum != 0.0) MaterialTheme.colorScheme.primary
            else Color(0xFFFF6864)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(derivedDimension * 0.01f, derivedDimension * 0.02f),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Box(
                    Modifier
                        .weight(12f)
                        .wrapContentHeight()
                        .padding(derivedDimension * 0.02f, 0.dp)
                        .border(
                            2.dp,
                            colorItem,
                            RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        modifier = Modifier.padding(
                            derivedDimension * 0.01f,
                            derivedDimension * 0.04f
                        ),
                        text = "Итог",
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
                        .padding(derivedDimension * 0.02f, 0.dp)
                        .border(
                            2.dp,
                            colorItem,
                            RoundedCornerShape(20.dp)
                        ),
                    contentAlignment = Alignment.Center
                )
                {
                    Text(
                        modifier = Modifier.padding(0.dp, derivedDimension * 0.04f),
                        text = String.format("%.1f", sum),
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
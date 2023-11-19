package com.example.leannext.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.leannext.radarChart.calculatePoints
import com.example.leannext.viewModel.MainViewModel
import kotlin.math.atan2


data class test(
    var angle: Float,
    var id: Int
)

@OptIn(ExperimentalTextApi::class)
@Composable
fun RadarChart(
    radarLabels: List<String>,
    labelsStyle: TextStyle,
    netLinesStyle: NetLinesStyle,
    scalarSteps: Int,
    scalarValue: Double,
    scalarValuesStyle: TextStyle,
    polygons: List<Polygon>,
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: MainViewModel
) {

    val textMeasurer = rememberTextMeasurer()
    var centre by remember {
        mutableStateOf(Offset.Zero)
    }
    var labelsEndPoints by remember {
        mutableStateOf(listOf<Offset>())
    }
    var labelsEndPoints1 by remember {
        mutableStateOf(listOf<Offset>())
    }


    Canvas(modifier = modifier.pointerInput(true) {
        detectTapGestures(
            onTap = { offset ->
                var index = 1
                val listAngle = mutableListOf<test>()

                labelsEndPoints.forEach {
                    listAngle += test(atan2(it.y - centre.y, it.x - centre.x), index)
                    index++
                }

                listAngle += test(3.0f, listAngle.maxBy { it.angle }.id)
                listAngle += test(-3.0f, listAngle.maxBy { it.angle }.id)
                listAngle.sortBy { it.angle }

                index = 0

                val angle = atan2(offset.y - centre.y, offset.x - centre.x)
                Log.d("TapX", offset.x.toString())
                Log.d("TapY", offset.y.toString())
                listAngle.forEach lit@{
                    val i = if (index + 1 != listAngle.size) index + 1
                    else return@lit
                    if (angle in it.angle..listAngle[i].angle)
                    {
                        viewModel.getItemsCriterias(it.id)
                        viewModel.getAnswerCriteries(it.id)
                        navHostController.navigate("directionTestScreen/${it.id}/" + radarLabels[it.id-1])
                    }
                    index++
                }


                /*    var index = 0
                    labelsEndPoints1.size
                    labelsEndPoints.forEach {
                        Log.d("TapX", offset.x.toString())
                        Log.d("TapY", offset.y.toString())
                        var k = 0f
                        var b = 0f
                        var k1 = 0f
                        var b1 = 0f

                        k = (centre.y - it.y) / ((centre.x - it.x))
                        b = it.y - k * it.x
                        var i = if (index + 1 == labelsEndPoints.size) 0
                        else index+1
                        k1 = (centre.y - labelsEndPoints[i].y) / ((centre.x - labelsEndPoints[i].x))
                        b1 = labelsEndPoints[i].y - k1 * labelsEndPoints[i].x
                        if (offset.y >= k * offset.x + b && offset.y >= k1 * offset.x + b1)
                            Log.d("Tap", (index + 1).toString())
                        *//*  if ((it.x >= offset.x - 30 && it.x < offset.x + 30) && (it.y >= offset.y - 30 && it.y < offset.y + 30)) {
                          Log.d("Tap", index.toString())
                          viewModel.getItemsCriterias(index)
                          viewModel.getAnswerCriteries(index)
                          navHostController.navigate("directionTestScreen/$index/" + radarLabels[index - 1])
                      }*//*
                    index++
                }*/

                /* labelsEndPoints.forEach {

                     var onePoint  = Offset()
                     Log.d("TapX", offset.x.toString())
                     Log.d("TapY", offset.y.toString())
                     if ((it.x >= offset.x - 30 && it.x < offset.x + 30)&&(it.y >= offset.y -30 && it.y < offset.y + 30)) {
                             Log.d("Tap", index.toString())
                         viewModel.getItemsCriterias(index)
                         viewModel.getAnswerCriteries(index)
                         navHostController.navigate("directionTestScreen/$index/"+radarLabels[index-1])
                         }
                     index++
                 }*/
            }
        )
    }) {

        centre = Offset(size.width / 2, size.height / 2)
        val labelWidth = measureMaxLabelWidth(radarLabels, labelsStyle, textMeasurer)

        val radius = (size.minDimension / 2.7f) - (10.toDp().toPx())
        val labelRadius = (size.minDimension / 2) - (labelWidth / 5)
        val numLines = radarLabels.size
        val radarChartConfig =
            calculateRadarConfig(labelRadius, radius, size, numLines, scalarSteps)
        labelsEndPoints =
            calculatePoints(labelRadius, radius, size, numLines, scalarSteps).labelsPoints
        labelsEndPoints1 = radarChartConfig.labelsPoints

        drawRadarNet(netLinesStyle, radarChartConfig)


        polygons.forEach {
            drawPolygonShape(
                this,
                it,
                radius,
                scalarValue - 1,
                Offset(size.width / 2, size.height / 2),
                scalarSteps
            )
        }

        drawAxisData(
            labelsStyle,
            scalarValuesStyle,
            textMeasurer,
            radarChartConfig,
            radarLabels,
            scalarValue - 1,
            scalarSteps,
            polygons[0].unit
        )
    }

}

private fun validateRadarChartConfiguration(
    radarLabels: List<String>,
    scalarValue: Double,
    polygons: List<Polygon>,
    scalarSteps: Int
) {
    require(scalarSteps > 0) { "Scalar steps must be a positive value" }
    require(scalarValue > 0.0) { "Scalar value must be greater than 0" }
    require(radarLabels.size >= 3) { "At least 3 radar labels are required" }

    for (polygon in polygons) {
        require(polygon.values.size == radarLabels.size) {
            "Number of polygon values must match the number of radar labels"
        }
        polygon.values.forEachIndexed { index, value ->
            require(value in 0.0..scalarValue) {
                "Polygon value at index $index must be between 0 and scalar value ($scalarValue)"
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
private fun DrawScope.measureMaxLabelWidth(
    radarLabels: List<String>,
    labelsStyle: TextStyle,
    textMeasurer: TextMeasurer
): Float {
    return textMeasurer.measure(
        AnnotatedString(
            text = radarLabels.maxByOrNull { it.length } ?: "",
        ), style = labelsStyle
    ).size.width.toDp().toPx()
}
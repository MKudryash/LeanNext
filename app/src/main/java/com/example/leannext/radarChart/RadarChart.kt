package com.example.leannext.screens

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp

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
    modifier: Modifier = Modifier
) {

    val textMeasurer = rememberTextMeasurer()

    validateRadarChartConfiguration(radarLabels, scalarValue, polygons, scalarSteps)

    Canvas(modifier = modifier) {

        val labelWidth = measureMaxLabelWidth(radarLabels, labelsStyle, textMeasurer)
        val radius = (size.minDimension / 2.7f) - (10.toDp().toPx())
        val labelRadius = (size.minDimension / 2)- (labelWidth / 5)
        val numLines = radarLabels.size
        val radarChartConfig =
            calculateRadarConfig(labelRadius, radius, size, numLines, scalarSteps)


        drawRadarNet(netLinesStyle, radarChartConfig)


        polygons.forEach {
            drawPolygonShape(
                this,
                it,
                radius,
                scalarValue-1,
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
            scalarValue,
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
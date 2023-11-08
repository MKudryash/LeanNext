package com.example.leannext.screens

import android.util.Log
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText

@OptIn(ExperimentalTextApi::class)
internal fun DrawScope.drawAxisData(
    labelsStyle: TextStyle,
    scalarValuesStyle: TextStyle,
    textMeasurer: TextMeasurer,
    radarChartConfig: RadarChartConfig,
    radarLabels: List<String>,
    scalarValue: Double,
    scalarSteps: Int,
    unit: String
) {

    val labelsEndPoints = radarChartConfig.labelsPoints
    val nextStartPoints = radarChartConfig.polygonPoints.toMutableList()
    nextStartPoints.add(0, center)
    //nextStartPoints.removeAt(nextStartPoints.size - 1)

    val scalarStep = scalarValue / (scalarSteps - 1)
    val textVerticalOffset = 20.toDp().toPx()
    val labelHeight = textMeasurer.measure(AnnotatedString("M")).size.height


    for (step in 0 until scalarSteps+1) {
        drawText(
            textMeasurer = textMeasurer,
            text = (scalarStep * step).toString() + " " + unit,
            style = scalarValuesStyle,
            topLeft = Offset(
                nextStartPoints[step].x + 5.toDp().toPx(),
                nextStartPoints[step].y - textVerticalOffset
            )
        )
        Log.d("DRAW",(scalarStep * step).toString())
    }
    for (line in labelsEndPoints.indices) {
        val rect = Rect(Offset(
            labelsEndPoints[line].x - textMeasurer.measure(
                AnnotatedString(
                    text = radarLabels[line],
                ), style = labelsStyle
            ).size.width / 2,
            labelsEndPoints[line].y - labelHeight /2
        ), size.minDimension)
        var degrees = 0f
        rotate(degrees = degrees, rect.center){
        drawText(
            textMeasurer = textMeasurer,
            text = radarLabels[line],
            style = labelsStyle,
            topLeft = Offset(
                rect.center.x,
                rect.center.y
            )
        )
    }
}

}
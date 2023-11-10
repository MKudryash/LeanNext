package com.example.leannext.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leannext.R



@Preview
@Composable
fun RecomendationBottomSheet() {
    Column(modifier = Modifier.background(MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier.padding(13.dp, 25.dp).fillMaxWidth(),
            text = "Рекомендация по критерию",
            color = MaterialTheme.colorScheme.secondary,
            fontFamily = FontFamily(Font(R.font.neosanspro_medium)),
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
        )
        Row(
            modifier = Modifier.fillMaxWidth().padding(30.dp,10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(painter = painterResource(id = R.drawable.icin_recomendation) ,
                contentDescription ="",
                modifier = Modifier.size(80.dp))
            Text(
                modifier = Modifier.fillMaxWidth().padding(45.dp,0.dp,0.dp,0.dp),
                text = "Провести занятия с персоналом по 5S. Разработать программу занятий. Составить по фамильный план обучения с отметками о выполнении и Ф.И.О. преподавателя.",
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = FontFamily(Font(R.font.neosanspro_regular)),
                fontSize = 18.sp,
                textAlign = TextAlign.Justify, softWrap = true
            )
        }
    }
}
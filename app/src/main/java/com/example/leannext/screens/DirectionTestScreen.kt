package com.example.leannext.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.leannext.viewModel.MainViewModel
import com.example.leannext.R
import com.example.leannext.db.modelsDb.Directions
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DirectionTestScreen(
    navHostController: NavHostController,
    id: Int,
    name: String,
    viewModel: MainViewModel
) {
    val itemsListCriterias by viewModel.itemsCriterias.observeAsState(listOf())
    val itemsListAnswerCriterias by viewModel.answerResult.observeAsState(listOf())
    val allDirections by viewModel.allDirection.observeAsState(listOf())

    BoxWithConstraints(
    ) {
        val derivedDimension = this.maxWidth
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .padding(7.dp, 25.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0XFFF5F5F9))
                        .clickable {
                            if (id > 1) {
                                viewModel.getItemsCriterias(id - 1)
                                viewModel.getAnswerCriteries(id - 1)
                                navHostController.navigate("directionTestScreen/" + (id - 1) + "/" + allDirections.first { it -> it.id == id - 1 }.title)
                            }
                        }, contentAlignment = Alignment.Center
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
                        .weight(6f)
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
                    text = name,
                    color = MaterialTheme.colorScheme.secondary,
                    fontFamily = FontFamily(Font(R.font.neosanspro_medium)),
                    fontSize = 5.em,
                    textAlign = TextAlign.Center,
                )
                Box(
                    modifier = Modifier
                        .padding(7.dp, 25.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0XFFF5F5F9))
                        .clickable {
                            if (id < allDirections.size - 1) {
                                viewModel.getItemsCriterias(id + 1)
                                viewModel.getAnswerCriteries(id + 1)

                                navHostController.navigate("directionTestScreen/" + (id + 1) + "/" + allDirections.first { it -> it.id == id + 1 }.title){

                                popUpTo("directionTestScreen/" + id + "/" + allDirections.first { it -> it.id == id}.title)}
                            }
                        }, contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.padding(7.dp),
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.onTertiary
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(derivedDimension * 0.05f, 10.dp, derivedDimension * 0.05f, 20.dp)
                    .border(2.dp, Color.Transparent, RoundedCornerShape(5.dp))
            ) {
                if(itemsListAnswerCriterias.isNotEmpty()) {
                    Divider(
                        modifier = Modifier.weight(itemsListAnswerCriterias.size.toFloat()),
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 8.dp
                    )
                }
                if ((itemsListCriterias.size - itemsListAnswerCriterias.size)!=0) {
                    Divider(
                        modifier = Modifier.weight((itemsListCriterias.size - itemsListAnswerCriterias.size).toFloat()),
                        color = Color(0xFFD9D9D9),
                        thickness = 8.dp
                    )
                }
            }
            LazyColumn(
                verticalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 92.dp),
            ) {
                items(itemsListCriterias) { item ->
                    var count = item.id!! % 3
                    if (count == 0) count = 3
                    val sheetState = rememberModalBottomSheetState()
                    var isSheetOpen by rememberSaveable {
                        mutableStateOf(false)
                    }
                    Column {
                        Row(

                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${count} вопрос",
                                fontFamily = FontFamily(Font(R.font.neosanspro_bold)),
                                fontSize = 6.em,
                                color = MaterialTheme.colorScheme.secondary,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(7.dp, 5.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(5.dp, 0.dp)
                                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                    .clickable {
                                        isSheetOpen = true
                                    }
                            )

                            {
                                Text(
                                    text = "i",
                                    fontFamily = FontFamily(Font(R.font.neosanspro_bold)),
                                    fontSize = 6.em,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(12.dp, 3.dp),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }


                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp, 10.dp)
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
                        var selectedIndex by remember { mutableStateOf(-1) }
                        LazyRow(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(derivedDimension * 0.03f),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            itemsIndexed(
                                listOf(
                                    1.0, 2.0, 3.0, 4.0, 5.0
                                )

                            ) { index, items ->
                                var borderColor: Color
                                selectedIndex =-1
                                if(itemsListAnswerCriterias.isNotEmpty()) {
                                    itemsListAnswerCriterias.forEach {
                                        if (it.idCriterias == item.id) {
                                            when (it.mark) {
                                                1.0 -> selectedIndex = 0
                                                2.0 -> selectedIndex = 1
                                                3.0 -> selectedIndex = 2
                                                4.0 -> selectedIndex = 3
                                                5.0 -> selectedIndex = 4
                                            }
                                        }
                                    }
                                }
                                if (index == selectedIndex) borderColor =
                                    MaterialTheme.colorScheme.primary
                                else borderColor = Color(0xFFB8C1CC)
                                Box(
                                    modifier = Modifier
                                        .border(
                                            2.dp, borderColor, RoundedCornerShape(20.dp)
                                        )
                                        .selectable(
                                            selected = index == selectedIndex,
                                            onClick = {
                                                if (selectedIndex != index) selectedIndex =
                                                    index else selectedIndex = -1
                                                viewModel.insertAnswerCriteries(item.id, items, id)
                                                viewModel.getAnswerCriteries(id)

                                            },
                                            interactionSource = remember { MutableInteractionSource() },
                                            indication = rememberRipple(
                                                bounded = false,
                                                radius = 20.dp,
                                                color = MaterialTheme.colorScheme.primary
                                            ),
                                        )
                                ) {
                                    Text(
                                        text = items.toString(),
                                        fontFamily = FontFamily(Font(R.font.neosanspro_regular)),
                                        fontSize = 4.em,
                                        color = MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier
                                            .padding(derivedDimension * 0.04f)

                                    )
                                }
                            }
                        }
                        if (item.id < itemsListCriterias.size) {
                            Divider(
                                color = MaterialTheme.colorScheme.primary,
                                thickness = 2.dp,
                                modifier = Modifier.padding(15.dp, 10.dp)
                            )
                        }
                        if (isSheetOpen) {
                            ModalBottomSheet(
                                sheetState = sheetState,
                                onDismissRequest = { isSheetOpen = false },
                            ) {
                                Column(
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                        .wrapContentSize(),
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        modifier = Modifier
                                            .padding(13.dp, 25.dp)
                                            .fillMaxWidth(),
                                        text = "Рекомендация по критерию",
                                        color = MaterialTheme.colorScheme.secondary,
                                        fontFamily = FontFamily(Font(R.font.neosanspro_medium)),
                                        fontSize = 18.sp,
                                        textAlign = TextAlign.Center,
                                    )
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(30.dp, 20.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.icin_recomendation),
                                            contentDescription = "",
                                            modifier = Modifier.size(80.dp)
                                        )
                                        Text(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(45.dp, 0.dp, 0.dp, 0.dp),
                                            text = item.recommendations,
                                            color = MaterialTheme.colorScheme.secondary,
                                            fontFamily = FontFamily(Font(R.font.neosanspro_regular)),
                                            fontSize = 16.sp,
                                            textAlign = TextAlign.Start, softWrap = true
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
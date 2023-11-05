package com.example.leannext.bottom_navigation

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.R
import com.example.leannext.utlis.CheckWeek
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Locale

val checkWeek = CheckWeek()
var week = 0

val format = SimpleDateFormat("dd MMMM", Locale("ru"))
var startDate = mutableStateOf(format.format(checkWeek.PreviousNextWeekModay(week)))
var endDate = mutableStateOf(format.format(checkWeek.PreviousNextWeekSunday(week)))
fun ChangeDate(week: Int) {
    startDate.value = format.format(checkWeek.PreviousNextWeekModay(week))
    endDate.value = format.format(checkWeek.PreviousNextWeekSunday(week))
}

@Composable
fun DiagramScreen( mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory),
                   navHostController: NavHostController) {
    val itemsListDirection = mainViewModel.itemsListDirection.collectAsState(initial = emptyList())
    var startDateText by startDate
    var endDateText by endDate
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
                    .size(60.dp)
                    .clickable { ChangeDate(--week) },
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
                    text = "${startDateText} - ${endDateText}",
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
                    .clickable { ChangeDate(++week) },
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
        Box {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 92.dp)
            )
            {
                items(itemsListDirection.value){ item ->
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
                            Text(
                                modifier = Modifier.padding(0.dp, 15.dp),
                                text = "0,0",
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
fun TestScreen(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory),
    navHostController: NavHostController
) {
    var search: String by rememberSaveable { mutableStateOf("") }
    val itemsListDirection = mainViewModel.itemsListDirection.collectAsState(initial = emptyList())
   // mainViewModel.addDataToFirestore()
/*    val db = Firebase.firestore
    val user = hashMapOf(
        "first" to "Ada",
        "last" to "Lovelace",
        "born" to 1815
    )

// Add a new document with a generated ID
    db.collection("userss")
        .add(user)
        .addOnSuccessListener { documentReference ->
            Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
        }
        .addOnFailureListener { e ->
            Log.w(TAG, "Error adding document", e)
        }*/
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        CustomSearchView(search = search, onValueChange = {
            search = it
        })
        if (itemsListDirection != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 92.dp),
            )
            {
                items(itemsListDirection.value) { item ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(25.dp, 10.dp)
                            .weight(1f)
                            .height(85.dp)
                            .clickable(onClick =
                            {
                                navHostController.navigate("directionTestScreen/" + item.id)
                            }),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                      /*  val context = LocalContext.current
                        val name = "menegment"
                        val drawableId = remember(name) {
                            context.resources.getIdentifier(
                                name,
                                "drawable",
                                context.packageName
                            )
                        }*/
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
                    if (item.id < itemsListDirection.value.size) {
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
                placeholderColor = Color(0XFFF5F5F9),
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.onTertiary,
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


@Composable
fun DirectionTestScreen(
    mainViewModel: MainViewModel = viewModel(factory = MainViewModel.factory),
    navHostController: NavHostController,
    id: Int
) {
    val itemsListCriterias =
        mainViewModel.foundItemCriteriasForDirection(id).collectAsState(initial = emptyList())
    val titleDirection = mainViewModel.foundNameItemDirectionWithId(id)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .padding(7.dp, 25.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0XFFF5F5F9)),
                contentAlignment = Alignment.Center
            )
            {
                Icon(
                    modifier = Modifier
                        .padding(7.dp),
                    imageVector = Icons.Default.ArrowBack, contentDescription = "",
                    tint = MaterialTheme.colorScheme.onTertiary
                )
            }
            Text(
                modifier = Modifier
                    .weight(8f)
                    .padding(10.dp, 0.dp, 10.dp, 0.dp),
                text = titleDirection.toString(),
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
        )
        {
            items(itemsListCriterias.value) { item ->
                var count = item.id % 3
                if (count == 0) count = 3
                var progressWeight: Float = count.toFloat()
                var allWeight: Float = 3f - count.toFloat()
                Column(

                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
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
                    )
                    {
                        Divider(
                            modifier = Modifier.weight(progressWeight),
                            color = MaterialTheme.colorScheme.primary, thickness = 6.dp
                        )
                        if (allWeight > 0) {
                            Divider(
                                modifier = Modifier.weight(allWeight),
                                color = Color(0xFFD9D9D9), thickness = 6.dp
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp, 0.dp)
                            .border(
                                2.dp, Color(0xFFB8C1CC),
                                RoundedCornerShape(20.dp)
                            ),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = item.title,
                            fontFamily = FontFamily(Font(R.font.neosanspro_bold)),
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.padding(7.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(25.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        itemsIndexed(
                            listOf(
                                "1.0", "2.0", "3.0", "4.0", "5.0"
                            )
                        )
                        { index, item ->
                            Box(
                                modifier = Modifier.border(
                                    2.dp, Color(0xFFB8C1CC),
                                    RoundedCornerShape(20.dp)
                                )
                            )
                            {
                                Text(
                                    text = item,
                                    fontFamily = FontFamily(Font(R.font.neosanspro_regular)),
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(16.dp)
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

@Composable
fun ProfileScreen(navHostController: NavHostController) {
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

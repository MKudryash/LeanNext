package com.example.leannext.utlis

import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.R
import com.example.leannext.bottom_navigation.BottomItem

sealed class  Screen(val route:String)
object Constants {
    object DiagramScreen : BottomItem("Диаграмма", R.drawable.diagram,"diagram")
    object ProfileScreen : BottomItem("Профиль", R.drawable.profile,"profile")
    object TestScreen : BottomItem("Тесты", R.drawable.test,"test")
    object DirectionTestScreen:Screen("directionTestScreen/{id}/{name}")
    object MainScreen:Screen("mainScreen")
    var userName:String = ""

}
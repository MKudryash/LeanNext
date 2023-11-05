package com.example.leannext.utlis

import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.R
import com.example.leannext.bottom_navigation.BottomItem

sealed class  Screen(val route:String)
object Constants {
    object DiagramScreen : BottomItem("Diagram", R.drawable.diagram,"diagram")
    object ProfileScreen : BottomItem("Profile", R.drawable.profile,"profile")
    object TestScreen : BottomItem("Test", R.drawable.test,"test")
    object DirectionTestScreen:Screen("directionTestScreen/{id}")

    object dateForDataBase{
       val listDirection =listOf(
           Directions(1,R.drawable.menegment,"5S и Визуальный менеджмент"),
           Directions(2,R.drawable.system,"Всеобщая эксплуатационная система ТРМ"),
           Directions(3,R.drawable.smed,"Быстрая переналадка SMED"),
           Directions(4,R.drawable.standartwork,"Стандартизированная работа"),
           Directions(5,R.drawable.carta,"Картирование"),
           Directions(6,R.drawable.thread,"Выстраивание потока"),
           Directions(7,R.drawable.personal,"Вовлечение персонала"),
           Directions(8,R.drawable.personal,"Обучение персонала"),
           Directions(9,R.drawable.menegment,"Логистика")
       )
        var listDevelopmentIndex = listOf(
            DevelopmentIndex(1,1,"5S и Визуальный менеджмент",0.0),
            DevelopmentIndex(1,1,"Всеобщая эксплуатационная система ТРМ",0.0),
            DevelopmentIndex(1,1,"Логистика",0.0),
            DevelopmentIndex(1,1,"Картирование",0.0)
        )
    }
}
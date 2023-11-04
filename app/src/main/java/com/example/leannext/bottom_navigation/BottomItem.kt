package com.example.leannext.bottom_navigation

import com.example.leannext.R

sealed class BottomItem(val title: String, val iconId: Int, val route: String) {
    object DiagramScreen : BottomItem("Diagram", R.drawable.diagram,"diagram")
    object ProfileScreen : BottomItem("Profile", R.drawable.profile,"profile")
    object TestScreen : BottomItem("Test", R.drawable.test,"test")
}
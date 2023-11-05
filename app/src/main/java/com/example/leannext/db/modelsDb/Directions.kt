package com.example.leannext.db.modelsDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Directions"
)
data class Directions(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idIcon:Int,
    @ColumnInfo(name = "title")
    val title: String
)

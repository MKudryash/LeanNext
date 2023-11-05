package com.example.leannext.db.modelsDb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Criterias", foreignKeys = arrayOf(
        ForeignKey(
            entity = Directions::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idDirection"),
            onDelete = ForeignKey.CASCADE
        )
    )
)
data class Criterias(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "title")
    val title: String,
    val idDirection: Int,
    val recommendations: String
)
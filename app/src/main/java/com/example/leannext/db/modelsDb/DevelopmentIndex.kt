package com.example.leannext.db.modelsDb

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.leannext.utlis.Converters
import java.time.LocalDate
import java.util.Date

@Entity(
    tableName = "DevelopmentIndex", foreignKeys = arrayOf(
        ForeignKey(
            entity = Criterias::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idUser"),
            onDelete = CASCADE
        ),
        ForeignKey(
            entity = Directions::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idDirection"),
            onDelete = CASCADE
        )
    )
)
data class DevelopmentIndex(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val idUser:Int,
    @TypeConverters(Converters::class)
    val date:Date,
    val idDirection:Int,
    val mark: Double
)
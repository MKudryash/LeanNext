package com.example.leannext.db.modelsDb

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "AnswerCriterias", foreignKeys = arrayOf(
        ForeignKey(
            entity = Criterias::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("idCriterias"),
            onDelete = CASCADE
        )
    )
)
class AnswerCriterias(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val idCriterias: Int,
    val mark: Double
    /*val date: Date*/
)
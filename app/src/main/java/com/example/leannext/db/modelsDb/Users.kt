package com.example.leannext.db.modelsDb

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Users"
)
data class Users(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val name: String,
    val login:String,
    val password: String
)
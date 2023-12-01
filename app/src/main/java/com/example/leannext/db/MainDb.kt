package com.example.leannext.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.leannext.db.modelsDb.AnswerCriterias
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.db.modelsDb.Users
import com.example.leannext.utlis.Converters

@Database(
    entities = [
        AnswerCriterias::class,
        Criterias::class,
        DevelopmentIndex::class,
        Directions::class,
        Users::class
    ],
    version = 2
)
@TypeConverters(Converters::class)
abstract class MainDb : RoomDatabase() {
    abstract fun dao(): Dao

    companion object {

        private var INSTANCE: MainDb? = null

        fun createDataBase(context: Context): MainDb {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        MainDb::class.java,
                        "leannext"
                    )
                        .createFromAsset("leannext_default.db")
                        .build()
                    INSTANCE = instance

                }
                return instance
            }
        }
    }
}
package com.example.leannext.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.leannext.db.modelsDb.AnswerCriterias
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.db.modelsDb.Users

@Database(
    entities = [
        AnswerCriterias::class,
        Criterias::class,
        DevelopmentIndex::class,
        Directions::class,
        Users::class
    ],
    version = 1
)
abstract class MainDb : RoomDatabase() {
    abstract val dao: Dao
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
                    ).fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance

                }
                return instance
            }
        }
    }
}
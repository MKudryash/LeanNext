package com.example.leannext.db

import android.graphics.Path
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.leannext.db.modelsDb.AnswerCriterias
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.db.modelsDb.Users
import kotlinx.coroutines.flow.Flow

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemUsers(users: Users)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemAnswerCriterias(answerCriterias: AnswerCriterias)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemCriterias(criterias: Criterias)
    @Query("SELECT * FROM Criterias")
    fun getAllItemsCriterias(): Flow<List<Criterias>>
    @Query(value = "SELECT * FROM Criterias WHERE title LIKE  :title")
    fun foundItemCriteriasWithName(title: String): LiveData<Criterias>
    @Query(value = "SELECT * FROM Criterias WHERE idDirection = :id")
    fun foundItemCriteriasForDirection(id: Int): Flow<List<Criterias>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemDevelopmentIndex(developmentIndex: DevelopmentIndex)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemDirection(directions: Directions)
    @Query("SELECT * FROM Directions")
    fun getAllItemsDirection(): Flow<List<Directions>>
    @Query(value = "SELECT * FROM Directions WHERE title LIKE  :title")
    fun foundItemDirectionWithName(title: String): LiveData<Directions>
    @Query(value = "SELECT * FROM Directions WHERE id = :ids")
    fun foundNameItemDirectionWithId(ids: Int): LiveData<Directions>
}
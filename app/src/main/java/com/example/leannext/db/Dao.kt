package com.example.leannext.db

import android.graphics.Path
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.leannext.db.modelsDb.AnswerCriterias
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.db.modelsDb.Users
import kotlinx.coroutines.flow.Flow
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.Date
import java.util.Locale

@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemUsers(users: Users)
    @Query("SELECT * FROM Users")
    fun getAllItemsUsers(): Flow<List<Users>>
    @Query(value = "SELECT * FROM Users WHERE login LIKE  :login")
    fun foundItemUsersWithLogin(login: String): LiveData<Users>



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemAnswerCriterias(answerCriterias: AnswerCriterias?)
    @Update
    suspend fun updateItemAnswerCriterias(answerCriterias: AnswerCriterias?)
    @Query("Update AnswerCriterias set mark = :answer where date=:date and idCriterias = :idCriterias")
    suspend fun updateItemAnswerCriterias(answer:Double, date: Date, idCriterias: Int)
    @Query(value = "SELECT * FROM AnswerCriterias WHERE idCriterias =:idCriterias and date=:date")
    fun foundItemAnswerCriteriasIndexForDate( idCriterias: Int,date:Date): AnswerCriterias

    @Query(value = "select *\n" +
            "from AnswerCriterias as AC join Criterias as C on AC.idCriterias = C.id\n" +
            "Where C.idDirection = :idDirection and date = :date")
    fun getListAnswer( idDirection: Int,date:Date): List<AnswerCriterias>





    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemCriterias(criterias: Criterias)

    @Query("SELECT * FROM Criterias")
    fun getAllItemsCriterias(): LiveData<List<Criterias>>

    @Query(value = "SELECT * FROM Criterias WHERE title LIKE  :title")
    fun foundItemCriteriasWithName(title: String): LiveData<Criterias>

    @Query(value = "SELECT * FROM Criterias WHERE idDirection = :id")
    fun foundItemCriteriasForDirection(id: Int): LiveData<List<Criterias>>
    @Query(value = "SELECT * FROM Criterias WHERE idDirection = :id")
    fun foundItemCriteriasForDirection1(id: Int): List<Criterias>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemDevelopmentIndex(developmentIndex: DevelopmentIndex)
    @Query(value = "SELECT * FROM DevelopmentIndex WHERE date = :date and idDirection = :idDirection")
    fun foundItemDevelopmentIndexForDate(date: Date, idDirection: Int): DevelopmentIndex

    @Query(value = "SELECT * FROM DevelopmentIndex WHERE date(date)>= :startWeek and date(date)<=:endWeek")
    fun getAllDevelopmentIndexDate(startWeek: Date,endWeek:Date): LiveData<List<DevelopmentIndex>>


    @Query(value = "SELECT * FROM DevelopmentIndex WHERE date(date)>= :startWeek and date(date)<=:endWeek")
    fun getAllDevelopmentIndexDate1(startWeek: Date,endWeek:Date): List<DevelopmentIndex>
    @Query("Update DevelopmentIndex set mark = :mark where id = :id")
    fun updateDevelopmentIndex(id: Int,mark:Double)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItemDirection(directions: Directions)

    @Query("SELECT * FROM Directions")
    fun getAllItemsDirection(): LiveData<List<Directions>>



    @Query(value = "SELECT * FROM Directions WHERE title LIKE :title")
    fun foundItemDirectionWithName(title: String): LiveData<Directions>

    @Query(value = "SELECT * FROM Directions WHERE id = :ids")
    fun foundNameItemDirectionWithId(ids: Int): LiveData<Directions>

    @Query(
        value = "select sum(mark)/(select count(id) from Criterias as C where c.idDirection = :idDirection)\n" +
                "from Criterias as C\n" +
                "join AnswerCriterias as AC on C.id = AC.idCriterias\n" +
                "where c.idDirection = :idDirection and date =  :date"
    )
    fun CalculationMartForDevelopmentIndex(idDirection: Int, date: Date): Double
/*
    @Query(
        value = "select title, COALESCE(\n" +
                "(select mark\n" +
                "from directions as dd  join developmentindex as DI on dd.id = DI.idDirection\n" +
                "and dd.title = d.title\n" +
                "Where DI.Date = :date\n" +
                "),0) as mark\n" +
                "from directions as d\n" +
                "left join developmentindex as DI on d.id = DI.idDirection"
    )
    fun FoundDirectionForDiagram(date: String): Flow<List<dir>>*/
}

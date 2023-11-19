package com.example.leannext.db

import android.database.Cursor
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.leannext.db.modelsDb.AnswerCriterias
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.utlis.CheckWeek
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Date

class Repository(private val dao: Dao) {
    val allItemDirection: LiveData<List<Directions>> = dao.getAllItemsDirection()
    val itemsForDiagram: LiveData<List<DevelopmentIndex>> = dao.getAllDevelopmentIndexDate(
        CheckWeek.PreviousNextWeekModay(0),
        CheckWeek.PreviousNextWeekSunday(0)
    )
    val searchResults = MutableLiveData<List<DevelopmentIndex>>()
    val allItemCriterias = MutableLiveData<List<Criterias>>()
    val answerCriteries = MutableLiveData<List<AnswerCriterias>>()
    val searchDirections = MutableLiveData<List<Directions>>()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun changeListDevelopmentIndex(startDate: Date, endDate: Date) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFindDevelopmentIndex(startDate, endDate).await()
        }
    }
    private fun asyncFindDevelopmentIndex(
        startDate: Date,
        endDate: Date
    ): Deferred<List<DevelopmentIndex>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async dao.getAllDevelopmentIndexDate1(startDate, endDate)
        }

    fun changeListCriterias(id: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            allItemCriterias.value = asyncFindAnswerCriterias(id).await()
        }
    }

    private fun asyncFindAnswerCriterias(id: Int): Deferred<List<Criterias>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async dao.foundItemCriteriasForDirection1(id)
        }
    fun changeListAnswerCriterias(date: Date,idDirection:Int) {
        coroutineScope.launch(Dispatchers.Main) {
            answerCriteries.value = asyncFindAnswerCriterias(date,idDirection).await()
        }
    }

    private fun asyncFindAnswerCriterias(date: Date,idDirection: Int): Deferred<List<AnswerCriterias>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async dao.getListAnswer(idDirection,date)
        }


    fun insertAnswerCriteries(answerCriterias: AnswerCriterias, idDirection:Int) {
        coroutineScope.launch(Dispatchers.IO) {
            if (dao.foundItemAnswerCriteriasIndexForDate(
                    answerCriterias.idCriterias,
                    answerCriterias.date
                ) == null
            ) {
                dao.insertItemAnswerCriterias(answerCriterias = answerCriterias)
                var local = dao.foundItemDevelopmentIndexForDate(Date(),idDirection)
                val mark =  dao.CalculationMartForDevelopmentIndex(idDirection,Date())
                if(local==null)
                {
                    dao.insertItemDevelopmentIndex(DevelopmentIndex(null,1,Date(),idDirection,mark))
                }
                else dao.updateDevelopmentIndex(local.id!!, mark)
            } else {
                dao.updateItemAnswerCriterias(answerCriterias.mark,answerCriterias.date,answerCriterias.idCriterias)
                var local = dao.foundItemDevelopmentIndexForDate(Date(),idDirection)
                val mark =  dao.CalculationMartForDevelopmentIndex(idDirection,Date())
                dao.updateDevelopmentIndex(local.id!!, mark)

            }
        }
    }
}
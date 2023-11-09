package com.example.leannext.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.utlis.CheckWeek
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.Date

class Repository (private val dao:Dao) {
    val allItemDirection:LiveData<List<Directions>> = dao.getAllItemsDirection()
    val itemsForDiagram:LiveData<List<DevelopmentIndex>> = dao.getAllDevelopmentIndexDate(CheckWeek.PreviousNextWeekModay(0),CheckWeek.PreviousNextWeekSunday(0))
    val searchResults = MutableLiveData<List<DevelopmentIndex>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertDirection(newItemDirection: Directions) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.insertItemDirection(directions = newItemDirection)
        }
    }

    private fun asyncFind(startDate: Date, endDate:Date): Deferred<List<DevelopmentIndex>?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async dao.getAllDevelopmentIndexDate1(startDate,endDate)
        }
    fun changeList(startDate: Date, endDate:Date):LiveData<List<DevelopmentIndex>>
    {
        return dao.getAllDevelopmentIndexDate(startDate,endDate)
    }
    fun insertDevelopmentIndex(newItemDevelopmentIndex: DevelopmentIndex) {
        coroutineScope.launch(Dispatchers.IO) {
            dao.insertItemDevelopmentIndex(developmentIndex = newItemDevelopmentIndex)
        }
    }
}
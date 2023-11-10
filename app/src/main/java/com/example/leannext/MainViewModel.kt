package com.example.leannext

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leannext.db.MainDb
import com.example.leannext.db.Repository
import com.example.leannext.db.modelsDb.AnswerCriterias
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.utlis.CheckWeek
import java.util.Date


class MainViewModel(application: Application) : ViewModel() {
    val allDirection: LiveData<List<Directions>>
    val itemsAllDiagrams: LiveData<List<DevelopmentIndex>>
    val itemsCriterias:LiveData<List<Criterias>>

    val searchResults: MutableLiveData<List<DevelopmentIndex>>
    val searchDirections: MutableLiveData<List<Directions>>
    val answerResult: MutableLiveData<List<AnswerCriterias>>



    private val repository:Repository


    var week = mutableStateOf(0)
    val startDate = mutableStateOf(CheckWeek.PreviousNextWeekModay(week.value))
    val endDate = mutableStateOf(CheckWeek.PreviousNextWeekSunday(week.value))

    init{
        val db = MainDb.createDataBase(application)
        val dao = db.dao()
        repository = Repository(dao)

        allDirection = repository.allItemDirection
        itemsAllDiagrams = repository.itemsForDiagram
        searchResults = repository.searchResults
        itemsCriterias = repository.allItemCriterias
        answerResult = repository.answerCriteries
        searchDirections = repository.searchDirections
    }
    fun checkday() {
        startDate.value = CheckWeek.PreviousNextWeekModay(week.value)
        endDate.value = CheckWeek.PreviousNextWeekSunday(week.value)
    }
    fun getDirectionsWithText(text:String)
    {
        repository.foundItemCriteriasWithName(text)
    }
    fun getAnswerCriteries(idDirections: Int)
    {
        repository.changeListAnswerCriterias(Date(),idDirections)
    }
    fun getItemsCriterias(id:Int)
    {
        repository.changeListCriterias(id)
    }

    fun insertAnswerCriteries(idCriteries:Int,mark:Double,idDirections: Int) {
        repository.insertAnswerCriteries(AnswerCriterias(null,idCriteries,mark, Date()),idDirections)
    }
    fun findDevelopmentIndex() {
        repository.changeListDevelopmentIndex(startDate.value,endDate.value)
    }
}

/*    fun view(): Flow<List<DevelopmentIndex>> {

          viewModelScope.launch {
              itemsListDirectionIndex.collectIndexed { index, value ->
                  value.forEach {
                      Log.d("ITEMS", it.idDirection.toString())
                      Log.d("ITEMSS", it.mark.toString())
                      Log.d("ITEMSS", startDate.value.toString())
                      Log.d("ITEMSS", endDate.value.toString())
                  }
              }
          }
          return itemsListDirectionIndex
      }*/


//var itemsListDirectionForDiagram = database.dao.FoundDirectionForDiagram(format.format(Date()))

/*
init {
    addDataToFirestore()
}
    fun addDataToFirestore() {
        viewModelScope.launch {
            val db = FirebaseFirestore.getInstance()
            try {
                db.collection("directions").get().await().map {
                    val result = it.toObject(directions::class.java)
                    Log.d("TAG", result.toString())
                    itemsListDirection.value.add(result)
                }
            }
            catch (ex:Exception)
            {
                Log.w("TAG", ex.message.toString())
            }
            Log.d("Tag",itemsListDirection.value.size.toString())
        }
       */
/* val dataBase = FirebaseFirestore.getInstance()
        val data = HashMap<String, Any>()
        data["first_name"] = "Julia"
        data["last_name"] = "Mamsheva"
        dataBase.collection("usersss")
            .add(data)
            .addOnSuccessListener { documentReference ->
                Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "Error adding document", e)

            }
        dataBase.collection("directions")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("TAG", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("TAG", "Error getting documents.", exception)
            }*//*

    }*/
/*data class directions(
    var iconName:String? = null,
    var id: Int? = null,
    var title: String? = null
) {
    //var value: FireFranValue = FireFranValue(false, 0)
    companion object Factory {
        var COLLECTION = "directions"
    }
}*/
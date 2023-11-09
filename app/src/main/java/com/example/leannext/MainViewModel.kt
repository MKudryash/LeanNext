package com.example.leannext

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.leannext.db.App
import com.example.leannext.db.MainDb
import com.example.leannext.db.Repository
import com.example.leannext.db.modelsDb.AnswerCriterias
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.db.modelsDb.Users
import com.example.leannext.utlis.CheckWeek
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Collections.addAll
import java.util.Date
import java.util.Locale
import javax.inject.Inject



class MainViewModel(application: Application) : ViewModel() {
    val allDirection: LiveData<List<Directions>>
    val itemsAllDiagrams: LiveData<List<DevelopmentIndex>>
    val searchResults: MutableLiveData<List<DevelopmentIndex>>
    private val repository:Repository


    var week = mutableStateOf(0)
    val startDate = mutableStateOf(CheckWeek.PreviousNextWeekModay(week.value))
    val endDate = mutableStateOf(CheckWeek.PreviousNextWeekSunday(week.value))

    init{
        val db = MainDb.createDataBase(application)
        val dao = db.dao()
        repository = Repository(dao)
        val listDirection = mutableListOf(
            Directions(null, R.drawable.menegment, "5S и Визуальный менеджмент"),
            Directions(null, R.drawable.system, "Всеобщая эксплуатационная система ТРМ"),
            Directions(null, R.drawable.smed, "Быстрая переналадка SMED"),
            Directions(null, R.drawable.standartwork, "Стандартизированная работа"),
            Directions(null, R.drawable.carta, "Картирование"),
            Directions(null, R.drawable.thread, "Выстраивание потока"),
            Directions(null, R.drawable.personal, "Вовлечение персонала"),
            Directions(null, R.drawable.personal, "Обучение персонала"),
            Directions(null, R.drawable.menegment, "Логистика")
        )
        val listDevelopmentIndex = mutableStateListOf(
            DevelopmentIndex(null, 1, Date(), 1, 4.0),
            DevelopmentIndex(null, 1, Date(), 2, 2.0),
            DevelopmentIndex(null, 1, Date(), 5, 1.0),
            DevelopmentIndex(null, 1, Date(), 1, 2.1),
            DevelopmentIndex(null, 1, Date(), 1, 3.5),
            DevelopmentIndex(null, 1, Date(), 3, 1.0),
            DevelopmentIndex(null, 1, Date(), 7, 3.0),
            DevelopmentIndex(null, 1, Date(), 9, 2.0),
            DevelopmentIndex(null, 1, Date(), 4, 1.0),
        )
        listDirection.forEach{
            insertDirection(it)
        }
        listDevelopmentIndex.forEach{
            insertDevelopmentIndex(it)
        }
        allDirection = repository.allItemDirection
        itemsAllDiagrams = repository.itemsForDiagram
        searchResults = repository.searchResults
    }
    fun checkday() {
        startDate.value = CheckWeek.PreviousNextWeekModay(week.value)
        endDate.value = CheckWeek.PreviousNextWeekSunday(week.value)
    }
    fun insertDirection(itemDirection: Directions) {
        repository.insertDirection(itemDirection)
    }
    fun insertDevelopmentIndex(itemDevelopmentIndex: DevelopmentIndex) {
        repository.insertDevelopmentIndex(itemDevelopmentIndex)
    }
    fun findProduct(name: String) {
        repository.changeList(startDate.value,endDate.value)
    }
}

    /*//var week = mutableStateOf(0)
    val startDate = mutableStateOf(CheckWeek.PreviousNextWeekModay(week.value))
    val endDate = mutableStateOf(CheckWeek.PreviousNextWeekSunday(week.value))
    val mainList = mutableStateOf(emptyList<DevelopmentIndex>())
    //var itemsListDirectionIndex = database.dao.getAllDevelopmentIndexDate(startDate.value, endDate.value)

  *//*  fun getAll(startDate:Date, endDate: Date)= viewModelScope.launch{
mainList.value = database.dao.getAllDevelopmentIndexDate(startDate,endDate)
        val c = 5
    }*//*
   *//* //*

    fun insertAnswerCriterias(answer: Double, idCriterias: Int) = viewModelScope.launch {
        val local: AnswerCriterias? = database.dao.foundItemAnswerCriteriasIndexForDate(idCriterias).value
        if (local?.date == null) {

            database.dao.insertItemAnswerCriterias(
                AnswerCriterias(
                    null,
                    idCriterias,
                    answer,
                    Date()
                )
            )
        }
        else
        {
            //database.dao.insertItemAnswerCriterias(answer,Date(),idCriterias)
            database.dao.updateItemAnswerCriterias( AnswerCriterias(
                local.id,
                idCriterias,
                answer,
                Date()
            ))
        }
    }

    init {

        loadCriteriasDirection()

    }


    fun foundItemCriteriasForDirection(id: Int): Flow<List<Criterias>> {
        return database.dao.foundItemCriteriasForDirection(id)
    }

    private fun loadCriteriasDirection() {
        val listDirection = mutableListOf(
            Directions(1, R.drawable.menegment, "5S и Визуальный менеджмент"),
            Directions(2, R.drawable.system, "Всеобщая эксплуатационная система ТРМ"),
            Directions(3, R.drawable.smed, "Быстрая переналадка SMED"),
            Directions(4, R.drawable.standartwork, "Стандартизированная работа"),
            Directions(5, R.drawable.carta, "Картирование"),
            Directions(6, R.drawable.thread, "Выстраивание потока"),
            Directions(7, R.drawable.personal, "Вовлечение персонала"),
            Directions(8, R.drawable.personal, "Обучение персонала"),
            Directions(9, R.drawable.menegment, "Логистика"),
            Directions(10, R.drawable.menegment, "Логистика")
        )
        val listCriterias = mutableListOf(
            Criterias(
                1,
                "% персонала, ознакомленный с основами, технологиями, инструментами и порядком внедрения системы \"Пять S\"",
                1,
                ""
            ),
            Criterias(2, "% участков, обеспеченных плакатами по 5S", 1, ""),
            Criterias(
                3,
                "% На земле, на полу, на оборудовании, внутри и под оборудованием, под столами, под стеллажами и т.д. отсутствуют инструменты, оснастка, материалы и прочие предметы",
                1,
                ""
            ),
            Criterias(
                4,
                "% оборудования, механизмов, транспортных средств и дорожно-строительной техники охваченных системой TPM",
                2,
                ""
            ),
            Criterias(
                5,
                "% охвата оборудования, механизмов, транспортных средств и дорожно-строительной техники чек-листами самостоятельного технического обслуживания с отметками о выполнении проверок",
                2,
                ""
            ),
            Criterias(
                6,
                "Ведется учет простоев и их причин значимых единиц оборудования, механизмов, транспортных средств и дорожно-строительной техники.",
                2,
                ""
            ),
        )
        val listUsers = mutableListOf(
            Users(1, "Ivan", "LeanNext", "LeanNext"),
            Users(2, "admin", "admin", "admin")
        )

        val format = SimpleDateFormat("dd MM yyyy", Locale("ru"))
        *//*        val listAnswerCriterias = mutableStateListOf(
         *//**//*           AnswerCriterias(null, 1, 5.0, Date()),
            AnswerCriterias(null, 2, 1.0, Date()),
            AnswerCriterias(null, 3, 2.0, Date()),
            AnswerCriterias(null, 1, 1.0, Date()),
            AnswerCriterias(null, 2, 3.0, Date()),
            AnswerCriterias(null, 2, 3.0, Date())*//**//*
        )*//*
        viewModelScope.launch {
            listDirection.forEach {
                try {
                    val local: Directions? = database.dao.foundItemDirectionWithName(it.title).value
                    if (local?.title == null) database.dao.insertItemDirection(it)
                } catch (ex: Exception) {
                    Log.d("ExDB", ex.message.toString())
                }
            }
            listCriterias.forEach {
                try {
                    val local: Criterias? = database.dao.foundItemCriteriasWithName(it.title).value
                    if (local?.title == null) database.dao.insertItemCriterias(it)
                } catch (ex: Exception) {
                    Log.d("ExDB", ex.message.toString())
                }
            }
            listUsers.forEach {
                try {
                    val local: Users? = database.dao.foundItemUsersWithLogin(it.login).value
                    if (local?.login == null) database.dao.insertItemUsers(it)
                } catch (ex: Exception) {
                    Log.d("ExDB", ex.message.toString())
                }
            }
            *//*            listAnswerCriterias.forEach {
                            try {
                                val local: AnswerCriterias? =
                                    database.dao.foundItemAnswerCriteriasIndexForDate(Timestamp(System.currentTimeMillis())).value
                                if (local?.date == null) database.dao.insertItemAnswerCriterias(it)
                            } catch (ex: Exception) {
                                Log.d("ExDB", ex.message.toString())
                            }
                        }*//*
            val listDevelopmentIndex = mutableStateListOf(
                DevelopmentIndex(null, 1, Date(), 1, 4.0),
                DevelopmentIndex(null, 1, Date(), 2, 2.0),
                DevelopmentIndex(null, 1, Date(), 5, 1.0),
                DevelopmentIndex(null, 1, Date(), 1, 2.1),
                DevelopmentIndex(null, 1, Date(), 1, 3.5),
                DevelopmentIndex(null, 1, Date(), 3, 1.0),
                DevelopmentIndex(null, 1, Date(), 7, 3.0),
                DevelopmentIndex(null, 1, Date(), 9, 2.0),
                DevelopmentIndex(null, 1, Date(), 4, 1.0),
            )
            listDevelopmentIndex.forEach {
                try {
                    val local: DevelopmentIndex? =
                        database.dao.foundItemDevelopmentIndexForDate(it.date).value
                    if (local?.date == null) database.dao.insertItemDevelopmentIndex(it)
                } catch (ex: Exception) {
                    Log.d("ExDB", ex.message.toString())
                }
            }
        }
    }

    companion object {
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database =
                    (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return MainViewModel(database) as T
            }
        }
    }
}*/
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
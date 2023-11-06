package com.example.leannext

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.leannext.db.App
import com.example.leannext.db.MainDb
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.db.modelsDb.Users
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
class MainViewModel(val database: MainDb) : ViewModel() {
    var itemsListDirection = database.dao.getAllItemsDirection()

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

    }
*/
    init {
        loadCriteriasDirection()
    }
    fun foundItemCriteriasForDirection(id:Int): Flow<List<Criterias>>
    {
        return database.dao.foundItemCriteriasForDirection(id)
    }
    fun foundNameItemDirectionWithId(id:Int): String?
    {
        return database.dao.foundNameItemDirectionWithId(id).value?.title
    }
    private fun loadCriteriasDirection()
    {

        var c2 =R.drawable.system
        var c3 =R.drawable.smed
        var c4 =R.drawable.standartwork
        var c5 =R.drawable.carta
        var c6 =R.drawable.thread
        var c7 =R.drawable.personal
        var c8 =R.drawable.personal
        var c9 =R.drawable.menegment
        val listDirection = mutableListOf(
            Directions(1, R.drawable.menegment, "5S и Визуальный менеджмент"),
            Directions(2, R.drawable.system, "Всеобщая эксплуатационная система ТРМ"),
            Directions(3, R.drawable.smed, "Быстрая переналадка SMED"),
            Directions(4, R.drawable.standartwork, "Стандартизированная работа"),
            Directions(5, R.drawable.carta, "Картирование"),
            Directions(6, R.drawable.thread, "Выстраивание потока"),
            Directions(7, R.drawable.personal, "Вовлечение персонала"),
            Directions(8, R.drawable.personal, "Обучение персонала"),
            Directions(9, R.drawable.menegment, "Логистика")
        )
        val listCriterias = mutableListOf(
            Criterias(1,"% персонала, ознакомленный с основами, технологиями, инструментами и порядком внедрения системы \"Пять S\"",1,""),
            Criterias(2,"% участков, обеспеченных плакатами по 5S",1,""),
            Criterias(3,"% На земле, на полу, на оборудовании, внутри и под оборудованием, под столами, под стеллажами и т.д. отсутствуют инструменты, оснастка, материалы и прочие предметы",1,""),
            Criterias(4,"% оборудования, механизмов, транспортных средств и дорожно-строительной техники охваченных системой TPM",2,""),
            Criterias(5,"% охвата оборудования, механизмов, транспортных средств и дорожно-строительной техники чек-листами самостоятельного технического обслуживания с отметками о выполнении проверок",2,""),
            Criterias(6,"Ведется учет простоев и их причин значимых единиц оборудования, механизмов, транспортных средств и дорожно-строительной техники.",2,""),
        )
        viewModelScope.launch {
            listDirection.forEach {
                Log.d("DBName",it.title)
                try {
                    val local: Directions? = database.dao.foundItemDirectionWithName(it.title).value
                    if (local?.title == null)   database.dao.insertItemDirection(it)
                }
                catch (ex:Exception)
                {
                    Log.d("ExDB",ex.message.toString())
                }
            }
            listCriterias.forEach {
                Log.d("DBName",it.title)
                try {
                    val local: Criterias? = database.dao.foundItemCriteriasWithName(it.title).value
                    if (local?.title == null)   database.dao.insertItemCriterias(it)
                }
                catch (ex:Exception)
                {
                    Log.d("ExDB",ex.message.toString())
                }

            }
        }
    }
    companion object{
        val factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                val database = (checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as App).database
                return MainViewModel(database) as T
            }
        }
    }
}
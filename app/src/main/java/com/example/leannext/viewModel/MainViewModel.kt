package com.example.leannext.viewModel

import android.Manifest
import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.leannext.R
import com.example.leannext.db.MainDb
import com.example.leannext.db.Repository
import com.example.leannext.db.modelsDb.AnswerCriterias
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.utlis.CheckWeek
import com.example.leannext.utlis.NotificationWorker
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.sql.DataSource


class MainViewModel(application: Application) : ViewModel() {
    val allDirection: LiveData<List<Directions>>
    val itemsAllDiagrams: LiveData<List<DevelopmentIndex>>
    val itemsCriterias: LiveData<List<Criterias>>

    val searchResults: MutableLiveData<List<DevelopmentIndex>>
    val searchDirections: MutableLiveData<List<Directions>>
    val answerResult: MutableLiveData<List<AnswerCriterias>>


    private val repository: Repository


    var week = mutableStateOf(0)
    val startDate = mutableStateOf(CheckWeek.PreviousNextWeekModay(week.value))
    val endDate = mutableStateOf(CheckWeek.PreviousNextWeekSunday(week.value))


    private val workManager = WorkManager.getInstance(application)

    init {
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

    fun getAnswerCriteries(idDirections: Int) {
        repository.changeListAnswerCriterias(Date(), idDirections)
    }

    fun getItemsCriterias(id: Int) {
        repository.changeListCriterias(id)
    }

    fun insertAnswerCriteries(idCriteries: Int, mark: Double, idDirections: Int) {
        repository.insertAnswerCriteries(
            AnswerCriterias(null, idCriteries, mark, Date()),
            idDirections
        )
    }

    fun findDevelopmentIndex() {
        repository.changeListDevelopmentIndex(startDate.value, endDate.value)
    }

     fun scheduleReminder() {


        val myWorkRequestBuilder = OneTimeWorkRequestBuilder<NotificationWorker>()

         val updateTime: Calendar = Calendar.getInstance()
         updateTime.timeZone = TimeZone.getTimeZone("GMT+3")
         updateTime.set(Calendar.HOUR_OF_DAY, 16)
         updateTime.set(Calendar.MINUTE, 5)
        myWorkRequestBuilder.setInitialDelay(1, TimeUnit.MINUTES)
        workManager.enqueue(myWorkRequestBuilder.build())
    }



    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification"
            val descriptionText = "Кажется вы забыли заполнить отчет"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("Channel_id", name, importance).apply {
                description = descriptionText
            }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
        scheduleReminder()
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
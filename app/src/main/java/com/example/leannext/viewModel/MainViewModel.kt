package com.example.leannext.viewModel

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.leannext.db.MainDb
import com.example.leannext.db.Repository
import com.example.leannext.db.modelsDb.AnswerCriterias
import com.example.leannext.db.modelsDb.Criterias
import com.example.leannext.db.modelsDb.DevelopmentIndex
import com.example.leannext.db.modelsDb.Directions
import com.example.leannext.utlis.CheckWeek
import java.util.Calendar
import java.util.Date
import java.util.TimeZone
import java.util.concurrent.TimeUnit

/**Модель данных между UI и DB*/
class MainViewModel(application: Application) : ViewModel() {

    val allDirection: LiveData<List<Directions>> //Лист всех направлений
    val itemsAllDiagrams: LiveData<List<DevelopmentIndex>>// Результаты тестов текущей недели, для построения диаграммы
    val itemsCriterias: LiveData<List<Criterias>> //Вопросы по определенному навправлению

    val searchResults: MutableLiveData<List<DevelopmentIndex>>//Поиск результатов тестов, для определенной недели
    val answerResult: MutableLiveData<List<AnswerCriterias>> //Ответы на вопросы определенного напрваления


    private val repository: Repository //Репозиторий


    var week = mutableStateOf(0) //Переменная для перемещения по неделям
    val startDate = mutableStateOf(CheckWeek.PreviousNextWeekModay(week.value)) //Дата начала недели относительно сдвига
    val endDate = mutableStateOf(CheckWeek.PreviousNextWeekSunday(week.value)) //Дата конца недели относительно сдвига


    init {
        val db = MainDb.createDataBase(application)
        val dao = db.dao()
        repository = Repository(dao)

        allDirection = repository.allItemDirection
        itemsAllDiagrams = repository.itemsForDiagram
        searchResults = repository.searchResults
        itemsCriterias = repository.allItemCriterias
        answerResult = repository.answerCriteries
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
            idDirections,
            startDate.value, endDate.value
        )
    }

    fun findDevelopmentIndex() {
        repository.changeListDevelopmentIndex(startDate.value, endDate.value)
    }

}
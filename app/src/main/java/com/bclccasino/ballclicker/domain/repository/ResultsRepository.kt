package com.bclccasino.ballclicker.domain.repository

import com.bclccasino.ballclicker.domain.models.ResultRealmModel
import com.bclccasino.ballclicker.domain.utils.StatusResultsRequests
import io.reactivex.rxjava3.subjects.BehaviorSubject

interface ResultsRepository {

    val results : BehaviorSubject<ResultRealmModel>
    fun insertResult(result : ResultRealmModel, function:(StatusResultsRequests) -> Unit)
    fun getResults()
}
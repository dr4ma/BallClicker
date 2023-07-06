package com.bclccasino.ballclicker.domain.usecases

import android.util.Log
import com.bclccasino.ballclicker.domain.models.ResultModel
import com.bclccasino.ballclicker.domain.models.ResultRealmModel
import com.bclccasino.ballclicker.domain.repository.ResultsRepository
import com.bclccasino.ballclicker.domain.utils.StatusResultsRequests
import com.bclccasino.ballclicker.domain.utils.TAG_GET_RESULTS
import com.bclccasino.ballclicker.domain.utils.mapToNormalModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject

class ResultsUseCase @Inject constructor(private val resultsRepository: ResultsRepository) {

    private val _results: BehaviorSubject<ResultModel> = BehaviorSubject.create()
    val results = _results
    private val disposeBag = CompositeDisposable()

    fun insertResult(result : ResultRealmModel, function:(StatusResultsRequests) -> Unit){
        resultsRepository.insertResult(result){ status ->
            function(status)
        }
    }

    fun getResults(){
        val results = resultsRepository.results
            .map { it.mapToNormalModel() }
            .subscribeOn(Schedulers.io())
            .subscribe({ model ->
                _results.onNext(model)
            },{ error ->
                _results.onError(error)
                Log.e(TAG_GET_RESULTS, error.message.toString())
            },{
                _results.onComplete()
            })
        resultsRepository.getResults()
        disposeBag.add(results)
    }

    fun clearDisposeBag(){
        disposeBag.clear()
    }
}
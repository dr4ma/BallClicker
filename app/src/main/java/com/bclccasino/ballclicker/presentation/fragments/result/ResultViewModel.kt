package com.bclccasino.ballclicker.presentation.fragments.result

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bclccasino.ballclicker.domain.models.ResultModel
import com.bclccasino.ballclicker.domain.usecases.ResultsUseCase
import com.bclccasino.ballclicker.presentation.utils.TAG_GET_RESULTS_ERROR
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(private val resultsUseCase: ResultsUseCase) : ViewModel() {

    private val _results: MutableLiveData<MutableList<ResultModel>> = MutableLiveData()
    val results = _results

    private val disposeBag = CompositeDisposable()

    fun getResults(){
        val list = mutableListOf<ResultModel>()
        val results = resultsUseCase.results
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe({ model ->
                list.add(model)
            },{ error ->
                _results.postValue(mutableListOf())
                Log.e(TAG_GET_RESULTS_ERROR, error.message.toString())
            },{
                list.sortBy { it.time }
                _results.postValue(list)
            })
        resultsUseCase.getResults()
        disposeBag.add(results)
    }

    fun clearDisposeBag(){
        resultsUseCase.clearDisposeBag()
        disposeBag.clear()
    }
}
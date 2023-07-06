package com.bclccasino.ballclicker.presentation.fragments.game

import android.content.Context
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bclccasino.ballclicker.domain.models.ResultRealmModel
import com.bclccasino.ballclicker.domain.objects.Ball
import com.bclccasino.ballclicker.domain.usecases.ResultsUseCase
import com.bclccasino.ballclicker.domain.utils.StatusResultsRequests
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val mBall: Ball,
    private val resultsUseCase: ResultsUseCase
) : ViewModel() {

    private val _pauseOffset: MutableLiveData<Long> = MutableLiveData(0)
    val pauseOffset = _pauseOffset

    fun setPauseOffset(value: Long) {
        _pauseOffset.value = value
    }

    fun initBall(context: Context, view: ImageView?, firstExp: ImageView?) {
        mBall.initView(context, view, firstExp)
    }

    fun resizeBallByClick(onSuccess: () -> Unit) {
        mBall.resizeBall {
            onSuccess()
        }
    }

    fun insertResult(result: ResultRealmModel, function: (StatusResultsRequests) -> Unit) {
        resultsUseCase.insertResult(result) { status ->
            function(status)
        }
    }
}
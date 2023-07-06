package com.bclccasino.ballclicker.data.realm

import android.util.Log
import com.bclccasino.ballclicker.data.utils.RealmErrorCallback
import com.bclccasino.ballclicker.data.utils.RealmErrorStatusCallback
import com.bclccasino.ballclicker.domain.models.ResultRealmModel
import com.bclccasino.ballclicker.domain.repository.ResultsRepository
import com.bclccasino.ballclicker.domain.utils.StatusResultsRequests
import io.reactivex.rxjava3.subjects.BehaviorSubject
import io.realm.Realm
import javax.inject.Inject

class ResultsRequests @Inject constructor(private val realm: Realm) : ResultsRepository {

    override val results: BehaviorSubject<ResultRealmModel> by lazy {
        BehaviorSubject.create()
    }

    override fun insertResult(result: ResultRealmModel, function: (StatusResultsRequests) -> Unit) {
        realm.executeTransactionAsync({ transition ->
            transition.insert(result)
        }, {
            function(StatusResultsRequests.SUCCESS)
        }, RealmErrorStatusCallback(realm) { function(StatusResultsRequests.ERROR) })

    }

    override fun getResults() {
        realm.executeTransactionAsync({ transition ->
            transition.where(ResultRealmModel::class.java).findAll().forEach { model ->
                Log.e("TAG", model.name)
                results.onNext(model)
            }
            results.onComplete()
        }, RealmErrorCallback(realm))

    }
}
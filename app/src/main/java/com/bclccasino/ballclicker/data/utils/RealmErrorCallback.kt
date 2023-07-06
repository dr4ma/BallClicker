package com.bclccasino.ballclicker.data.utils

import android.util.Log
import io.realm.Realm

class RealmErrorCallback(private val realm : Realm) : Realm.Transaction.OnError {
    override fun onError(error: Throwable) {
        realm.close()
        Log.e(RESULT_REALM_TAG, error.message.toString())
    }
}
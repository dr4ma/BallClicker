package com.bclccasino.ballclicker.data.utils

import android.util.Log
import io.realm.Realm

class RealmErrorStatusCallback(private val realm : Realm, private val function:() -> Unit) : Realm.Transaction.OnError {
    override fun onError(error: Throwable) {
        realm.close()
        Log.e(RESULT_REALM_TAG, error.message.toString())
        function()
    }
}
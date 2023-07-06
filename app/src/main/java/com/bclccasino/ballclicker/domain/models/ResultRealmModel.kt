package com.bclccasino.ballclicker.domain.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class ResultRealmModel(
    @PrimaryKey
    var id : String = "",
    var time : String = "",
    var name : String = ""
) : RealmObject()

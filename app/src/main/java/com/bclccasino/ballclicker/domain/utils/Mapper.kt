package com.bclccasino.ballclicker.domain.utils

import com.bclccasino.ballclicker.domain.models.ResultModel
import com.bclccasino.ballclicker.domain.models.ResultRealmModel

fun ResultRealmModel.mapToNormalModel() : ResultModel{
    return ResultModel(
        id = this.id,
        time = this.time,
        name = this.name
    )
}
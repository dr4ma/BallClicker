package com.bclccasino.ballclicker.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.bclccasino.ballclicker.domain.models.ResultModel
import com.bclccasino.ballclicker.domain.models.ResultRealmModel

class ResultsDiffUtils(
    private val oldList: MutableList<ResultModel>,
    private val newList: MutableList<ResultModel>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList == newList
    }
}
package com.bclccasino.ballclicker.presentation.fragments.result

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bclccasino.ballclicker.databinding.ItemResultRecyclerBinding
import com.bclccasino.ballclicker.domain.models.ResultModel
import com.bclccasino.ballclicker.presentation.utils.ResultsDiffUtils

class ResultsAdapter : RecyclerView.Adapter<ResultsAdapter.ResultsViewHolder>(){

    private var listResults = mutableListOf<ResultModel>()

    class ResultsViewHolder(private val binding: ItemResultRecyclerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(result: ResultModel) {
            with(binding){
                resultText.text = "${result.name} - ${result.time}"
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultsViewHolder {
        val binding = ItemResultRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ResultsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listResults.size
    }

    override fun onBindViewHolder(holder: ResultsViewHolder, position: Int) {
        holder.bind(listResults[position])
    }

    fun setList(newList: MutableList<ResultModel>) {
        val diffUtil = ResultsDiffUtils(listResults, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        listResults.clear()
        listResults.addAll(newList)
        diffResults.dispatchUpdatesTo(this)
    }

}
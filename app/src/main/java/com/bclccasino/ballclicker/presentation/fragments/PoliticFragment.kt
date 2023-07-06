package com.bclccasino.ballclicker.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bclccasino.ballclicker.R
import com.bclccasino.ballclicker.databinding.FragmentGameBinding
import com.bclccasino.ballclicker.databinding.FragmentPoliticBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PoliticFragment : Fragment() {

    private var mBinding: FragmentPoliticBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentPoliticBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding?.apply {
            backPolitics.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}
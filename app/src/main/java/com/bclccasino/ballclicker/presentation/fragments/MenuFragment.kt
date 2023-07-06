package com.bclccasino.ballclicker.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bclccasino.ballclicker.R
import com.bclccasino.ballclicker.databinding.FragmentMenuBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.system.exitProcess

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private var mBinding: FragmentMenuBinding? = null
    private val binding get() = mBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding?.apply {
            startBtn.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_gameFragment)
            }
            resultBtn.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_resultFragment)
            }
            politicBtn.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_politicFragment)
            }
            quitBtn.setOnClickListener {
                activity?.finishAndRemoveTask()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}
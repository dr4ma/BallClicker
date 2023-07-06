package com.bclccasino.ballclicker.presentation.fragments.result

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bclccasino.ballclicker.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private var mBinding: FragmentResultBinding? = null
    private val binding get() = mBinding!!

    private val mViewModel: ResultViewModel by viewModels()

    @Inject
    lateinit var mAdapter : ResultsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()
        initResultAdapter()
    }

    private fun initFields(){
        mBinding?.apply {
            resultRecycler.adapter = mAdapter
            progressResults.visibility = View.VISIBLE
            backResult.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
        }
    }

    private fun initResultAdapter(){
        mViewModel.results.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                mBinding?.progressResults?.visibility = View.GONE
                mBinding?.noResults?.visibility = View.VISIBLE
            }
            else{
                mBinding?.progressResults?.visibility = View.GONE
                mAdapter.setList(it)
            }
        }
        mViewModel.getResults()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewModel.clearDisposeBag()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}
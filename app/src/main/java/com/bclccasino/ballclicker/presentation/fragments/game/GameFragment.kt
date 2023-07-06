package com.bclccasino.ballclicker.presentation.fragments.game

import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bclccasino.ballclicker.R
import com.bclccasino.ballclicker.databinding.FragmentGameBinding
import com.bclccasino.ballclicker.domain.models.ResultRealmModel
import com.bclccasino.ballclicker.domain.utils.StatusResultsRequests
import com.bclccasino.ballclicker.presentation.utils.generateUUID
import com.bclccasino.ballclicker.presentation.utils.hideKeyBoard
import com.bclccasino.ballclicker.presentation.utils.showSnackBar
import com.bclccasino.ballclicker.presentation.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderScriptBlur

@AndroidEntryPoint
class GameFragment : Fragment() {

    private var mBinding: FragmentGameBinding? = null
    private val binding get() = mBinding!!

    private var chronometerRunning = false
    private var chronometerCanceled = false

    private val mViewModel: GameViewModel by viewModels()
    private lateinit var mChronometer: Chronometer
    private lateinit var mBlurView: BlurView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentGameBinding.inflate(inflater, container, false)
        mBinding?.apply {
            mChronometer = timeGame
            mBlurView = blur
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()
        mBinding?.apply {
            backGame.setOnClickListener {
                activity?.onBackPressedDispatcher?.onBackPressed()
            }
            ball.setOnClickListener {
                mViewModel.resizeBallByClick {
                    chronometerCanceled = true
                    pauseChronometer()
                    blurBackground()
                    scoreText.text = mChronometer.text
                }
            }
            acceptScore.setOnClickListener {
                hideKeyBoard(activity)
                if (enterName.text.toString().isNotEmpty()) {
                    acceptScore.visibility = View.INVISIBLE
                    saveLabel.visibility = View.INVISIBLE
                    progressResult.visibility = View.VISIBLE
                    mViewModel.insertResult(ResultRealmModel(generateUUID(), scoreText.text.toString(), enterName.text.toString())) { status ->
                        if (status == StatusResultsRequests.SUCCESS) {
                            showSnackBar(
                                context,
                                it,
                                getString(R.string.your_result_is_saved),
                                R.color.colorGreen
                            )
                            activity?.onBackPressedDispatcher?.onBackPressed()
                        } else {
                            showSnackBar(
                                context,
                                it,
                                getString(R.string.save_error),
                                R.color.colorRed
                            )
                            acceptScore.visibility = View.VISIBLE
                            saveLabel.visibility = View.VISIBLE
                            progressResult.visibility = View.GONE
                        }
                    }
                }
                else{
                    showToast(requireContext(), getString(R.string.name_is_empty))
                }
            }
        }
    }

    private fun initFields() {
        mViewModel.initBall(requireContext(), mBinding?.ball, mBinding?.firstExplose)
    }

    private fun startChronometer() {
        mViewModel.pauseOffset.observe(viewLifecycleOwner) { pauseOffset ->
            if (!chronometerRunning) {
                mChronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                mChronometer.start()
                chronometerRunning = true
            }
        }
    }

    private fun pauseChronometer() {
        if (chronometerRunning) {
            mChronometer.stop()
            mViewModel.setPauseOffset(SystemClock.elapsedRealtime() - mChronometer.base)
        }
    }

    private fun blurBackground() {
        val radius = 8f
        val decorView = activity?.window?.decorView
        val rootView = decorView?.findViewById<ViewGroup>(android.R.id.content)
        val windowBackground = decorView?.background
        if (rootView != null) {
            mBlurView.setupWith(rootView, RenderScriptBlur(requireContext())) // or RenderEffectBlur
                .setFrameClearDrawable(windowBackground)
                .setBlurRadius(radius)
        }
        mBlurView.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        pauseChronometer()
    }

    override fun onResume() {
        super.onResume()
        if (!chronometerCanceled) {
            chronometerRunning = false
        }
        startChronometer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mBinding = null
    }
}
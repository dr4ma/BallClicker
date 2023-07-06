package com.bclccasino.ballclicker.domain.objects

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import com.bclccasino.ballclicker.R
import com.bclccasino.ballclicker.presentation.utils.AnimationListener
import com.bclccasino.ballclicker.presentation.utils.CLICKS_BEFORE_BALL_DEATH

class Ball {

    private var mX = 1f
    private var mY = 1f
    private val pivotXValue = 0.5f
    private val pivotYValue = 0.5f
    private var mAnimationRunning = false
    private var mClickedCount = 0
    private val mCountResize = 0.1f

    private val mClicksBeforeDeath = CLICKS_BEFORE_BALL_DEATH

    private lateinit var mViewBall : ImageView
    private lateinit var mViewFirstExplosion : ImageView
    private lateinit var mContext: Context

    fun initView(context: Context, view : ImageView?, firstExp : ImageView?){
        if (view != null && firstExp != null) {
            mViewBall = view
            mViewFirstExplosion = firstExp
            mContext = context
        }
    }

    fun resizeBall(onSuccess :() -> Unit){
        if (!mAnimationRunning) {
            mClickedCount++
            if (mClickedCount % 3 == 0) {
                if(mClickedCount != mClicksBeforeDeath){
                    val animResize = ScaleAnimation(
                        mX, mX + mCountResize, mY, mY + mCountResize,
                        Animation.RELATIVE_TO_SELF, pivotXValue,
                        Animation.RELATIVE_TO_SELF, pivotYValue
                    )
                    animResize.fillAfter = true
                    animResize.duration = 500
                    animResize.interpolator = DecelerateInterpolator()
                    animResize.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(p0: Animation?) {
                            mAnimationRunning = true
                        }

                        override fun onAnimationEnd(p0: Animation?) {
                            mAnimationRunning = false
                        }

                        override fun onAnimationRepeat(p0: Animation?) {
                        }
                    })
                    mViewBall.startAnimation(animResize)

                    mX += mCountResize
                    mY += mCountResize
                }
                else{
                   finalExplosion{
                       onSuccess()
                   }
                }
            }
        }
    }
    
    private fun finalExplosion(onSuccess: () -> Unit){
        mAnimationRunning = true
        val firstAnimFadeIn = AnimationUtils.loadAnimation(mContext, R.anim.alpha_first_explose_anim)
        val firstAnimFadeOut = AnimationUtils.loadAnimation(mContext, R.anim.alpha_first_explose_anim_fade_out)
        val secondAnimFadeIn = AnimationUtils.loadAnimation(mContext, R.anim.alpha_second_explose_anim)
        val thirdAnimScale = AnimationUtils.loadAnimation(mContext, R.anim.scale_third_explose_anim)
        val forthAnimScale = AnimationUtils.loadAnimation(mContext, R.anim.scale_forth_explose_anim)
        mViewFirstExplosion.startAnimation(firstAnimFadeIn)
        mViewFirstExplosion.visibility = View.VISIBLE

        firstAnimFadeIn.setAnimationListener(AnimationListener{
            mViewFirstExplosion.startAnimation(firstAnimFadeOut)
            mViewFirstExplosion.visibility = View.GONE
            mViewBall.startAnimation(secondAnimFadeIn)
            mViewBall.setImageResource(R.drawable.second_explosion)
        })

        secondAnimFadeIn.setAnimationListener(AnimationListener{
            mViewBall.setImageResource(R.drawable.third_explosion)
            mViewBall.startAnimation(thirdAnimScale)
        })

        thirdAnimScale.setAnimationListener(AnimationListener{
            mViewBall.setImageResource(R.drawable.forth_explosion)
            mViewBall.startAnimation(forthAnimScale)
        })

        forthAnimScale.setAnimationListener(AnimationListener{
            onSuccess()
        })
    }
}
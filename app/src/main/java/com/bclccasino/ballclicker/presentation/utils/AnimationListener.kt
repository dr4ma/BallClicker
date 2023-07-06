package com.bclccasino.ballclicker.presentation.utils

import android.view.animation.Animation

class AnimationListener(val onAnimationEndOperation:() -> Unit) : Animation.AnimationListener {
    override fun onAnimationStart(p0: Animation?) {
    }

    override fun onAnimationEnd(p0: Animation?) {
        onAnimationEndOperation()
    }

    override fun onAnimationRepeat(p0: Animation?) {
    }
}
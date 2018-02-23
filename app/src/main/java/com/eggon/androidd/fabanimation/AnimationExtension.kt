package com.eggon.androidd.fabanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.support.annotation.IntegerRes
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.Interpolator

@SuppressLint("ResourceType")
fun Activity.transaction(@IntegerRes transaction: Int,
                         onTransitionStart: (() -> Unit)? = null,
                         onTransitionCancel: (() -> Unit)? = null,
                         onTransitionPause: (() -> Unit)? = null,
                         onTransitionResume: (() -> Unit)? = null,
                         onTransitionEnd: (() -> Unit)? = null) {
    TransitionInflater.from(this).inflateTransition(transaction).apply {
        this@transaction.window.sharedElementEnterTransition = this
        addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {
                onTransitionStart?.invoke()
            }

            override fun onTransitionCancel(transition: Transition) {
                transition.removeListener(this)
                onTransitionCancel?.invoke()
            }

            override fun onTransitionPause(transition: Transition) {
                onTransitionPause?.invoke()
            }

            override fun onTransitionResume(transition: Transition) {
                onTransitionResume?.invoke()
            }

            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                onTransitionEnd?.invoke()
            }
        })

//        addListener(object :MyTransaction{
//            override fun onTransitionEnd(transition: Transition?) {
//                super.onTransitionEnd(transition)
//
//            }
//        })
    }
}


fun View.revealAnimation(start: Int, end: Int, duration: Long? = 400L, interpolator: Interpolator? = DecelerateInterpolator(),
                         onAnimationStart: (() -> Unit)? = null, onAnimationEnd: (() -> Unit)? = null) {
    val finalRadius = Math.hypot(this.width.toDouble(), this.height.toDouble()).toFloat()
    ViewAnimationUtils.createCircularReveal(this, start, end, 0f, finalRadius).apply {
        this.interpolator = interpolator
        this.duration = duration!!
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                onAnimationStart?.invoke()
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                onAnimationEnd?.invoke()
            }
        })
        start()
    }
}

fun View.hideAnimation(start: Int, end: Int, duration: Long? = 400L, interpolator: Interpolator? = AccelerateInterpolator(),
                       onAnimationStart: (() -> Unit)? = null, onAnimationEnd: (() -> Unit)? = null) {
    val initialRadius = this.height.toFloat()
    ViewAnimationUtils.createCircularReveal(this, start, end, initialRadius, 0f).apply {
        this.interpolator = interpolator
        this.duration = duration!!
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                onAnimationStart?.invoke()
            }

            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                onAnimationEnd?.invoke()
            }
        })
        start()
    }
}
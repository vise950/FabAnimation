package com.eggon.androidd.fabanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import kotlinx.android.synthetic.main.activity_two.*


class SecondActivity : AppCompatActivity() {

    private lateinit var transition: Transition

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        setupEnterAnimation()
    }

    override fun onBackPressed() {
        hideAnimation(background_view,
                background_view.width / 2,
                (activity_contact_fab.top + activity_contact_fab.bottom) / 2, {
            super.onBackPressed()
        })
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupEnterAnimation() {
        transition = TransitionInflater.from(this).inflateTransition(R.transition.arcmotion)
        window.sharedElementEnterTransition = transition
        transition.addListener(object : Transition.TransitionListener {
            override fun onTransitionStart(transition: Transition) {}
            override fun onTransitionCancel(transition: Transition) {}
            override fun onTransitionPause(transition: Transition) {}
            override fun onTransitionResume(transition: Transition) {}
            override fun onTransitionEnd(transition: Transition) {
                transition.removeListener(this)
                revealAnimation(background_view,
                        background_view.width / 2,
                        (activity_contact_fab.top + activity_contact_fab.bottom) / 2)
            }
        })
    }

    private fun revealAnimation(view: View, x: Int, y: Int) {
        val finalRadius = Math.hypot(view.width.toDouble(), view.height.toDouble()).toFloat()
        ViewAnimationUtils.createCircularReveal(view, x, y, 0f, finalRadius).apply {
            interpolator = DecelerateInterpolator()
            duration = 400L
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    view.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    activity_contact_fab.visibility = View.GONE
                }
            })
            start()
        }
    }

    private fun hideAnimation(view: View, x: Int, y: Int, animationEnd: (() -> Unit)) {
        val initialRadius = view.height.toFloat()
        ViewAnimationUtils.createCircularReveal(view, x, y, initialRadius, 0f).apply {
            interpolator = AccelerateInterpolator()
            duration = 400L
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    activity_contact_fab.visibility = View.VISIBLE
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    view.visibility = View.INVISIBLE
                    animationEnd.invoke()
                }
            })
            start()
        }
    }
}
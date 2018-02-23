package com.eggon.androidd.fabanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.Transition
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
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

        background_view.hideAnimation(background_view.width / 2,
                (activity_contact_fab.top + activity_contact_fab.bottom) / 2)
    }

    @SuppressLint("ResourceType")
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun setupEnterAnimation() {
        this.transaction(R.transition.arcmotion, onTransitionEnd = {

            background_view.revealAnimation(background_view.width / 2,
                    (activity_contact_fab.top + activity_contact_fab.bottom) / 2,
                    onAnimationStart = {
                        background_view.visibility = View.VISIBLE
                    },
                    onAnimationEnd = {
                        activity_contact_fab.visibility = View.GONE
                    })
        })
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
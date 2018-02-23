package com.eggon.androidd.fabanimation

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            ActivityOptions.makeSceneTransitionAnimation(this@MainActivity,
                    fab, fab.transitionName)?.let {
                startActivity(Intent(this@MainActivity, SecondActivity::class.java), it.toBundle())
            }
        }
    }
}

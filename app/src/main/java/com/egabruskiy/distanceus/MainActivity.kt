package com.egabruskiy.distanceus

import android.os.Bundle
import com.egabruskiy.distanceus.ui.title.TitleFragment
import dagger.android.support.DaggerAppCompatActivity
import timber.log.Timber

class MainActivity : DaggerAppCompatActivity() {


    val TAG  = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag(TAG).v("      onCreate")

        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, TitleFragment.newInstance())
                    .commitNow()
        }
    }



}
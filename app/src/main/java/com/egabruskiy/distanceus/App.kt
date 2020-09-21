package com.egabruskiy.distanceus

import android.app.Application
import com.egabruskiy.distanceus.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

class App: Application(), HasAndroidInjector {


    @Inject
    lateinit var androidInjector : DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    override fun onCreate() {
        super.onCreate()
        val appComponent = DaggerAppComponent
            .builder()
            .application(this)
            .build()

        appComponent.inject(this)


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
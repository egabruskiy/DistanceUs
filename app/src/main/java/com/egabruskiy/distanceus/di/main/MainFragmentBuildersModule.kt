package com.egabruskiy.distanceus.di.main

import com.egabruskiy.distanceus.ui.title.TitleFragment
import com.egabruskiy.distanceus.ui.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeTitleFragment(): TitleFragment



}
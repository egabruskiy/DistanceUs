package com.egabruskiy.distanceus.di

import com.egabruskiy.distanceus.MainActivity
import com.egabruskiy.distanceus.di.main.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuildersModule {



    @MainScope
   @ContributesAndroidInjector
       (
       modules = [
       MainFragmentBuildersModule::class,
           MainViewModelsModule::class,
       MainModule::class
       ])
    abstract fun contributeMainActivity(): MainActivity





}
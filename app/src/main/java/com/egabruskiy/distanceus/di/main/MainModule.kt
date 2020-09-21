package com.egabruskiy.distanceus.di.main


import com.egabruskiy.distanceus.ui.main.PeopleRecyclerAdapter
import dagger.Module
import dagger.Provides

@Module
class MainModule {

    companion object{
        @MainScope
        @Provides
       fun provideAdapter(): PeopleRecyclerAdapter {
            return  PeopleRecyclerAdapter()
        }


    }
}
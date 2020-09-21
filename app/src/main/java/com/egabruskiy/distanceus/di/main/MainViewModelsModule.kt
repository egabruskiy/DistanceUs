package com.egabruskiy.distanceus.di.main

import androidx.lifecycle.ViewModel
import com.egabruskiy.distanceus.di.ViewModelKey
import com.egabruskiy.distanceus.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel):ViewModel
}
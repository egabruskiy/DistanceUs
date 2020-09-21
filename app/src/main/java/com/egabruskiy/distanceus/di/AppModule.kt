package com.egabruskiy.distanceus.di

import android.app.Application
import android.content.Context
import android.location.LocationManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.egabruskiy.distanceus.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
 class AppModule{
    companion object {

        @Provides
        @Singleton
        internal fun provideContext(application: Application): Context {
            return application.applicationContext
        }

        @Singleton
        @Provides
        internal fun provideFusedLocationProviderClient(context: Context): FusedLocationProviderClient {
            return LocationServices.getFusedLocationProviderClient(context)
        }


        @Singleton
        @Provides
        internal fun provideLocationManager(context: Context): LocationManager {
            return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }


        @Singleton
        @Provides
        fun provideRequestOptions():RequestOptions{

            return RequestOptions()
                .placeholder(R.drawable.white_background)
                .error(R.drawable.white_background)
        }

        @Singleton
        @Provides
        fun provideGlideInstance(application: Application,
                                 requestOptions: RequestOptions):RequestManager{
            return Glide.with(application)
                .setDefaultRequestOptions(requestOptions)
        }


    }


}
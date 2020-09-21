package com.egabruskiy.distanceus.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import kotlinx.android.synthetic.main.main_fragment.*
import java.text.DecimalFormat
import java.time.Duration
import kotlin.math.*

class Utility {

    companion object{
        fun distance(
            lat1: Double, lat2: Double, lon1: Double,
            lon2: Double): String {
            val R = 6371 // Radius of the earth
            val latDistance = Math.toRadians(lat2 - lat1)
            val lonDistance = Math.toRadians(lon2 - lon1)
            val a =
                (sin(latDistance / 2) * sin(latDistance / 2)
                        + (cos(Math.toRadians(lat1)) * cos(
                    Math.toRadians(lat2)
                )
                        * sin(lonDistance / 2) * sin(lonDistance / 2)))
            val c =
                2 * atan2(sqrt(a), sqrt(1 - a))
            var distance = R * c  // convert to kilometers
            distance = distance.pow(2.0)
            return DecimalFormat("0").format( sqrt(distance)).toString()
        }


        fun animateViewVisible(view: View, duration: Long){

            view.apply {
                visibility = View.VISIBLE

                alpha = 0f
                animate()
                    .alpha(1f)
                    .setDuration(duration.toLong())
                    .setListener(null)
            }
        }
        fun animateViewGone(view: View, duration: Long){
            view.animate()
                .alpha(0f)
                .setDuration(duration.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = View.GONE
                    }
                })
        }
    }



}
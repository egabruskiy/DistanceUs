package com.egabruskiy.distanceus.ui.main

import android.view.View
import com.egabruskiy.distanceus.models.Person
import java.text.FieldPosition

interface RecyclerViewListener {
    fun onClick(item: Person,position: Int)

}
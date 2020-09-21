package com.egabruskiy.distanceus.repository

import com.egabruskiy.distanceus.R
import com.egabruskiy.distanceus.models.Person
import java.util.*

object PersonsList {
    var personsList = mutableListOf(
        Person("ALEX",R.drawable.ava_1   ,0.0, 0.0),
        Person("MARIA",R.drawable.ava_7  ,0.0, 0.0),
        Person("ARTEM",R.drawable.ava_2  ,0.0, 0.0),
        Person("ANN",R.drawable.ava_8    ,0.0, 0.0),
        Person("VASILIY",R.drawable.ava_3,0.0, 0.0),
        Person("MAX",R.drawable.ava_4    ,0.0, 0.0),
        Person("OLGA",R.drawable.ava_9   ,0.0, 0.0),
        Person("SVETA",R.drawable.ava_10 ,0.0, 0.0),
        Person("STAS",R.drawable.ava_5   ,0.0, 0.0),
        Person("KRIS",R.drawable.ava_11  ,0.0, 0.0),
        Person("NICK",R.drawable.ava_6   ,0.0, 0.0)
    )


     fun  getList():List<Person>{
        for (person in personsList){
            person.latitude= Random().nextDouble()*100-40
            person.longitude= Random().nextDouble()*100-40
        }
        return personsList
    }
}
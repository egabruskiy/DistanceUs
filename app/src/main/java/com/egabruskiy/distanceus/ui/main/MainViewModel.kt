package com.egabruskiy.distanceus.ui.main

import androidx.lifecycle.*
import com.egabruskiy.distanceus.models.*
import com.egabruskiy.distanceus.repository.PersonsList

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor() : ViewModel() {

    val TAG  = javaClass.simpleName



    private val personsLiveData = MutableLiveData<Resource<List<Person>>>()
    var myWayLatitude :Double = 0.0
    var myWayLongitude:Double = 0.0
    var chosenWayLatitude :Double = 0.0
    var chosenWayLongitude:Double = 0.0
    var itemClicked = -1
    lateinit var person:Person

    fun observePersonsLiveData(): LiveData<Resource<List<Person>>>{
        return personsLiveData
    }


    private fun <T> runCoroutine(
        personsLiveData : MutableLiveData<Resource<T>>,
        block:suspend ()->T) {
        personsLiveData.postValue(Resource(
            status = Status.Loading,
            data = null))

        viewModelScope.launch(Dispatchers.IO) {


            while (true) {
                try {
                    val result = block()
                    personsLiveData.postValue(
                        Resource(
                            status = Status.Success,
                            data = result
                        )
                    )
                } catch (exception: Exception) {
                    personsLiveData.postValue(
                        Resource(
                            status = Status.ShowError(exception.toString()),
                            data = null
                        )
                    )
                }
                delay(3000)
            }
    }

    }

    fun fetchList() {

        runCoroutine(personsLiveData) {
           return@runCoroutine PersonsList.getList()
        }
    }


}







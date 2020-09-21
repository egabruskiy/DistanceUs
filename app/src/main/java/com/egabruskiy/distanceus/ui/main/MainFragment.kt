package com.egabruskiy.distanceus.ui.main


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.egabruskiy.distanceus.R
import com.egabruskiy.distanceus.models.Person
import com.egabruskiy.distanceus.models.Status
import com.egabruskiy.distanceus.utils.Constants.MY_REQUEST_LOCATION
import com.egabruskiy.distanceus.utils.LocationHandler
import com.egabruskiy.distanceus.utils.Utility
import com.egabruskiy.distanceus.utils.Utility.Companion.animateViewGone
import com.egabruskiy.distanceus.utils.Utility.Companion.animateViewVisible
import com.egabruskiy.distanceus.viewmodels.ViewModelProviderFactory
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.main_fragment.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber
import javax.inject.Inject


class MainFragment : DaggerFragment(R.layout.main_fragment) {

    @Inject
    lateinit var requestManager: RequestManager

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: PeopleRecyclerAdapter

    private val TAG = javaClass.simpleName

    private  val viewModel: MainViewModel by activityViewModels()

    private var shortAnimationDuration: Int = -1


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        viewModel =  ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.fetchList()

        subscribeObservers()

        if (viewModel.itemClicked > -1){
            openChosenItem(viewModel.person,viewModel.itemClicked)
        } else{
            chosen_person_layout.visibility = View.GONE
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        requestPermission()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        cancel_button.setOnClickListener { closeChosenItem() }
    }

    private fun openChosenItem(item: Person, position: Int) {

        viewModel.itemClicked = position
        viewModel.person = item

        animateViewVisible(chosen_person_layout,shortAnimationDuration.toLong())

        loadAvatar(item.avatar,chosen_user_ava)

        chosen_person_name.text = item.name
        adapter.mainName = item.name
        chosen_distance_view.text = Utility.distance(
            viewModel.myWayLatitude,
            item.latitude,
            viewModel.myWayLongitude,
            item.longitude
        )

        updateChosenLocationData(item.latitude,item.longitude)
        updateLocationUI()

    }

    private fun loadAvatar(avatar: Int, view: ImageView) {
        requestManager
            .load(avatar)
            .circleCrop()
            .into(view)
    }

    private fun closeChosenItem() {
        viewModel.itemClicked = -1
        animateViewGone(chosen_person_layout,shortAnimationDuration.toLong())
        adapter.mainName = "тебя"
        updateChosenLocationData(viewModel.chosenWayLatitude,viewModel.chosenWayLongitude)
        updateLocationUI()
    }



    private fun updateChosenLocationData(latitude:Double, longitude :Double){
        viewModel.chosenWayLatitude = latitude
        viewModel.chosenWayLongitude = longitude
    }
    private fun updateLocationUI(){
       if (viewModel.itemClicked > -1) {
           adapter.mainLatitude =  viewModel.chosenWayLatitude
           adapter.mainLongitude = viewModel.chosenWayLongitude

           chosen_distance_view.text = Utility.distance(
               viewModel.myWayLatitude,
               viewModel.chosenWayLatitude,
               viewModel.myWayLongitude,
               viewModel.chosenWayLongitude
           )
       } else {
           adapter.mainLatitude = viewModel.myWayLatitude
           adapter.mainLongitude =viewModel.myWayLongitude
       }
        adapter.notifyDataSetChanged()
    }
    private fun subscribeObservers() {
        viewModel.observePersonsLiveData().removeObservers(viewLifecycleOwner)
        viewModel.observePersonsLiveData().observe(
            viewLifecycleOwner,
            Observer { it ->
                when (it.status) {
                    Status.Loading -> {
                        //TODO ProgressBar
                    }
                    Status.Success -> initData(it.data)
                }
            })
    }

    private fun initData(list: List<Person>?) {
        Timber.tag(TAG).v("      List Location Update")
        if (list != null) {
            adapter.setPersons(list)
            if (viewModel.itemClicked > -1) {
                updateChosenLocationData(list[viewModel.itemClicked].latitude,list[viewModel.itemClicked].longitude)
            } else {
                updateChosenLocationData(viewModel.myWayLatitude,viewModel.myWayLongitude)
            }

            updateLocationUI()

        }
    }

    private fun initRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(activity)
        recycler_view.adapter = adapter
        adapter.setClickListener(object :
            RecyclerViewListener {
            override fun onClick(item: Person, position: Int) {
                if (viewModel.itemClicked != position) {
                    openChosenItem(item, position)
                } else {
                    closeChosenItem()
                }
            }
        })
    }


    @AfterPermissionGranted(MY_REQUEST_LOCATION)
    fun requestPermission() {
        val perm: String = Manifest.permission.ACCESS_FINE_LOCATION
        if (EasyPermissions.hasPermissions(requireContext(), perm)) {

            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            startLocationUpdates()

        } else {
            EasyPermissions.requestPermissions(
                this,(getString(R.string.location_perms)),
                MY_REQUEST_LOCATION, perm
            )
        }
    }

    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            updateMyLocation(locationResult.lastLocation.latitude,locationResult.lastLocation.longitude)
            updateLocationUI()
        }
    }

    private fun updateMyLocation(latitude:Double,longitude: Double) {
        viewModel.myWayLatitude = latitude
        viewModel.myWayLongitude = longitude
    }

    private fun startLocationUpdates() {
        LocationHandler(this, mLocationCallback,requireActivity())
    }


    companion object {
        fun newInstance() = MainFragment()
    }


}
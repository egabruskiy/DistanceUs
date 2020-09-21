package com.egabruskiy.distanceus.ui.title

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.egabruskiy.distanceus.R
import com.egabruskiy.distanceus.ui.main.MainFragment
import com.egabruskiy.distanceus.utils.Constants
import dagger.android.support.DaggerFragment
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber


class TitleFragment : DaggerFragment(), EasyPermissions.PermissionCallbacks {

    private val TAG = javaClass.simpleName

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_title, container, false)
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermission()
    }



    @AfterPermissionGranted(Constants.MY_REQUEST_LOCATION)
    fun requestPermission() {
        val perm:String = ACCESS_FINE_LOCATION
        if (EasyPermissions.hasPermissions(requireContext(), perm)) {


            if (ActivityCompat.checkSelfPermission(requireContext(),
                    ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
                return
            }

            activity?.let {activity->
                activity.supportFragmentManager.
                beginTransaction().
                replace(R.id.container, MainFragment.newInstance()).
                commit()
            }

        } else {
            EasyPermissions.requestPermissions(
                this,(getString(R.string.location_perms)),
                Constants.MY_REQUEST_LOCATION, perm
            )
        }
    }


    override  fun onPermissionsDenied(requestCode: Int, perms: List<String?>) {
        if (requestCode==Constants.MY_REQUEST_LOCATION ){
            activity?.finish()
        }

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

        Timber.tag(TAG).v("     onPermissionsGranted" + requestCode + "  "+ perms)
//        activity?.let {activity->
//            activity.supportFragmentManager.
//            beginTransaction().
//            replace(R.id.container, MainFragment.newInstance()).
//            commit()
//        }
    }


    override fun onRequestPermissionsResult(

        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray) {

        Timber.tag(TAG).v("     onRequestPermissionsResult" + requestCode + "  "+grantResults)

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    companion object {
        @JvmStatic
        fun newInstance() = TitleFragment()
            }
    }

package com.example.maksimdimitrov.rickandmorty.controller.items


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.model.Model

typealias Location = Model.Location

class LocationFragment : ItemFragment(){

    private val TAG = LocationFragment::class.qualifiedName

    private lateinit var location: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getParcelable<Location>(LOCATION)?.let {
                location = it
                Log.d(TAG, "Location initialized: $location")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_location, container, false)
        return rootView
    }


    companion object {
        private const val LOCATION = "location"
        @JvmStatic
        fun newInstance(location: Location) =
                LocationFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(LOCATION, location)
                    }
                }
    }

}

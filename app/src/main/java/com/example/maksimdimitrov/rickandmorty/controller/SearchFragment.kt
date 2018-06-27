package com.example.maksimdimitrov.rickandmorty.controller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.maksimdimitrov.rickandmorty.R

class SearchFragment : Fragment() {
    private lateinit var listener: OnSearchFragmentInteractionListener
    private lateinit var rootView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_search, container, false)
        //TODO setListeners
        return rootView
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnSearchFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnSearchFragmentInteractionListener")
        }
    }

    interface OnSearchFragmentInteractionListener {
        fun onSearhItemClick(url: String)
    }

}

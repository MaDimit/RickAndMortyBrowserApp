package com.example.maksimdimitrov.rickandmorty.controller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.maksimdimitrov.rickandmorty.R

const val TYPE = "com.example.maksimdimitrov.rickandmorty.controller.listfragment.type"

class ListFragment : Fragment() {
    enum class Type { CHARACTER, LOCATION, EPISODE }

    private lateinit var type: Type
    private lateinit var rootView : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val argType = it.getSerializable(TYPE)
            if(argType is Type) {
                type = argType
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_list, container, false)
        setAdapter()
        return rootView
    }

    private fun setAdapter(){
        when(type) {
            Type.CHARACTER -> TODO("set character adapter")
            Type.LOCATION -> TODO("set location adapter")
            Type.EPISODE -> TODO("set episode adapter")
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(type: Type) =
                ListFragment().apply {
                    arguments = Bundle().apply {
                        putSerializable(TYPE, type)
                    }
                }
    }
}

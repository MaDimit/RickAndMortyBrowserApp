package com.example.maksimdimitrov.rickandmorty.controller


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.maksimdimitrov.rickandmorty.R

class CharacterFragment : ItemFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_character, container, false)
        return rootView
    }


}

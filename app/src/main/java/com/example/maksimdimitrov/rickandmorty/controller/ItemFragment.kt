package com.example.maksimdimitrov.rickandmorty.controller

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.maksimdimitrov.rickandmorty.R

abstract class ItemFragment : Fragment() {
    private var listener: OnItemInteractionListener? = null
    lateinit var rootView: View

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnItemInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnItemInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnItemInteractionListener {
        fun onItemRedirect(url : String)
    }

}

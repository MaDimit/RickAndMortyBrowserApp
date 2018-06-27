package com.example.maksimdimitrov.rickandmorty.controller.items

import android.content.Context
import android.support.v4.app.Fragment
import android.view.View

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

package com.example.maksimdimitrov.rickandmorty.controller.items


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.model.Model


typealias Character  = Model.Character

class CharacterFragment : ItemFragment() {

    private val TAG = LocationFragment::class.qualifiedName
    lateinit var character: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getParcelable<Character>(CHARACTER)?.let {
                character = it
                Log.d(TAG, "Character initialized: $character")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.fragment_character, container, false)
        return rootView
    }


    companion object {
        private const val CHARACTER = "character"
        @JvmStatic
        fun newInstance(character: Character) =
                CharacterFragment().apply {
                    arguments = Bundle().apply {
                        putParcelable(CHARACTER, character)
                    }
                }
    }
}

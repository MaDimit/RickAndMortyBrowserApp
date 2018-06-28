package com.example.maksimdimitrov.rickandmorty.controller.items


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.model.Model
import kotlinx.android.synthetic.main.fragment_character.*
import kotlinx.android.synthetic.main.fragment_character.view.*


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
        setView()
        return rootView
    }

    private fun setView() {
        //setting all fields
        rootView.let {
            it.character_name.text = character.name
            character.apply {
                status.goneIfEmpty(it.container_status, it.status)
                species.goneIfEmpty(it.container_species, it.species)
                type.goneIfEmpty(it.container_type, it.type)
                gender.goneIfEmpty(it.container_gender, it.gender)
                origin.name.goneIfEmpty(it.container_origin, it.origin)
                location.name.goneIfEmpty(it.container_location, it.location)

                Glide.with(this@CharacterFragment)
                        .load(image)
                        .into(it.character_image)
            }
        }
    }

    private fun String.goneIfEmpty(container: ViewGroup, text: TextView) {
        if(this.isEmpty()) {
            container.visibility = View.GONE
        }else {
            text.text = this
        }
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

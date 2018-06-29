package com.example.maksimdimitrov.rickandmorty.controller.items


import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.adapters.CharacterEpisodesAdapter
import com.example.maksimdimitrov.rickandmorty.controller.ListFragment
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource
import com.example.maksimdimitrov.rickandmorty.model.Model
import kotlinx.android.synthetic.main.fragment_character.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete


typealias Character  = Model.Character

open class CharacterFragment : ItemFragment() {

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
        initView()
        return rootView
    }

    private fun initView() {
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
                initRecyclerView()
                initOnClickListeners()
            }
        }
    }

    private fun initRecyclerView() {
        val rv = rootView.episodes_rv
        rv.layoutManager = LinearLayoutManager(context)
        rootView.character_progressBar.visibility = View.VISIBLE
        doAsync {
            val episodes = character.episodes
                    .map { BaseDataSource.getEpisode(it) }
                    .requireNoNulls()
                    .toList()
            onComplete {
                rootView.character_progressBar.visibility = View.GONE
                rv.adapter = CharacterEpisodesAdapter(episodes)
            }
        }
    }

    private fun initOnClickListeners(){
        rootView.container_origin.setOnClickListener {
            if(context is ListFragment.ItemClickListener){
                doAsync {
                    val origin = BaseDataSource.getLocation(character.origin.url)
                    onComplete {
                        origin?.let {
                            (context as ListFragment.ItemClickListener).onListItemClick(it)
                        }
                    }
                }
            }
        }
        rootView.container_location.setOnClickListener {
            if(context is ListFragment.ItemClickListener){
                doAsync {
                    val location = BaseDataSource.getLocation(character.location.url)
                    onComplete {
                        location?.let {
                            (context as ListFragment.ItemClickListener).onListItemClick(it)
                        }
                    }
                }
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

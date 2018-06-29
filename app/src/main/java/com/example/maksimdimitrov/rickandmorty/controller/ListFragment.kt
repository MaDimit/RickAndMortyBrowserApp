package com.example.maksimdimitrov.rickandmorty.controller

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.adapters.CharacterAdapter
import com.example.maksimdimitrov.rickandmorty.adapters.EpisodeAdapter
import com.example.maksimdimitrov.rickandmorty.adapters.LocationAdapter
import com.example.maksimdimitrov.rickandmorty.controller.items.Episode
import com.example.maksimdimitrov.rickandmorty.controller.items.EpisodeFragment
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource
import com.example.maksimdimitrov.rickandmorty.model.Model
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete


const val TYPE = "com.example.maksimdimitrov.rickandmorty.controller.listfragment.type"
const val BUNDLE_LIST_STATE = "com.example.maksimdimitrov.rickandmorty.controller.listfragment.bundleliststate"

class ListFragment : Fragment() {
    enum class Type { CHARACTER, LOCATION, EPISODE }

    private val TAG = "LIST_FRAGMENT"

    private val dataSource = BaseDataSource
    private lateinit var type: Type
    private lateinit var rootView: View
    private lateinit var recyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var dataSet: List<Model.Item>
    private lateinit var info: Model.PageInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate fired")
        super.onCreate(savedInstanceState)
        arguments?.let {
            val argType = it.getSerializable(TYPE)
            if (argType is Type) {
                type = argType
                Log.d(TAG, "Type is set: $type")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        Log.d(TAG, "onCreateView fired")
        if (!this::rootView.isInitialized) {
            rootView = inflater.inflate(R.layout.fragment_list, container, false)
            initRecyclerView()
            initRecyclerViewPaging()
        }
        return rootView
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val savedRecyclerLayoutState = savedInstanceState.getParcelable<Parcelable>(BUNDLE_LIST_STATE)
            layoutManager.onRestoreInstanceState(savedRecyclerLayoutState)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(BUNDLE_LIST_STATE, layoutManager.onSaveInstanceState())
    }

    private fun initRecyclerView() {
        Log.d(TAG, "initRecyclerView fired")
        recyclerView = rootView.recycler_view
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        doAsync {
            val page = getItems()
            Log.d(TAG, "page created: $page")
            page?.let {
                Log.d(TAG, "page is not null")
                info = it.info
                dataSet = it.items
            }
            onComplete {
                Log.d(TAG, "onComplete fired")
                recyclerView.adapter = getAdapter()
            }
        }
    }

    private fun getItems(pageUrl: String = "") = when (type) {
        Type.CHARACTER -> dataSource.getCharacters(pageUrl)
        Type.EPISODE -> dataSource.getEpisodes(pageUrl)
        Type.LOCATION -> dataSource.getLocations(pageUrl)
    }


    private var loading = true
    private var pastVisiblesItems: Int = 0
    private var visibleItemCount: Int = 0
    private var totalItemCount: Int = 0
    private val visibleThreshold = 5

    private fun initRecyclerViewPaging() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    visibleItemCount = layoutManager.childCount
                    totalItemCount = layoutManager.itemCount
                    pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                    if (loading && visibleItemCount + pastVisiblesItems + visibleThreshold >= totalItemCount) {
                        loading = false
                        doAsync {
                            val page = getItems(info.next)
                            onComplete {
                                page?.let {
                                    recyclerView.adapter?.notifyDataSetChanged()
                                    info = it.info
                                    (dataSet as ArrayList<Model.Item>).addAll(it.items)
                                    if (info.next.isNotEmpty()) {
                                        loading = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })

    }

    private fun getAdapter(): RecyclerView.Adapter<*> {
        return when (type) {
            Type.CHARACTER -> CharacterAdapter(dataSet as List<Model.Character>)
            Type.LOCATION -> LocationAdapter(dataSet as List<Model.Location>)
            Type.EPISODE -> EpisodeAdapter(dataSet as List<Model.Episode>)
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

    interface ItemClickListener {
        fun onListItemClick(item: Model.Item)
    }
}

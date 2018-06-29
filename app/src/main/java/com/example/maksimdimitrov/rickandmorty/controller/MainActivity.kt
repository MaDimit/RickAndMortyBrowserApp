package com.example.maksimdimitrov.rickandmorty.controller

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.controller.items.*
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource
import com.example.maksimdimitrov.rickandmorty.model.Model
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import org.jetbrains.anko.uiThread
import java.util.*
import android.R.attr.tag
import android.R.transition
import android.os.Build



class MainActivity : AppCompatActivity(), ItemFragment.OnItemInteractionListener, ListFragment.ItemClickListener {

    override fun onItemRedirect(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onListItemClick(item: Model.Item) {
        when(item) {
            is Character -> replaceFragment(CharacterFragment.newInstance(item), true)
            is Location -> replaceFragment(LocationFragment.newInstance(item), true)
            is Episode -> replaceFragment(EpisodeFragment.newInstance(item), true)
        }
    }

    private val fm = supportFragmentManager
    private val dataSource = BaseDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavDrawer()

        replaceFragment(ListFragment.newInstance(ListFragment.Type.CHARACTER))
    }

    fun setNavDrawer() {
        nav_view.menu.getItem(0).isChecked = true
        nav_view.itemIconTintList = null

        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
            title = "Characters"
        }

        nav_view.setNavigationItemSelectedListener { menuItem ->
            // set item as selected to persist highlight
            menuItem.isChecked = true
            // close drawer when item is tapped
            drawer_layout.closeDrawers()

            when (menuItem.itemId) {
                R.id.nav_characters -> {
                    supportActionBar?.title = "Characters"
                    replaceFragment(ListFragment.newInstance(ListFragment.Type.CHARACTER))
                }
                R.id.nav_locations -> {
                    supportActionBar?.title = "Locations"
                    replaceFragment(ListFragment.newInstance(ListFragment.Type.LOCATION))
                }
                R.id.nav_episodes -> {
                    supportActionBar?.title = "Episodes"
                    replaceFragment(ListFragment.newInstance(ListFragment.Type.EPISODE))
                }
                R.id.nav_search -> supportActionBar?.title = "Search"
                R.id.nav_random -> {
                    supportActionBar?.title = "Random Character"
                    doAsync {
                        val character = dataSource.getCharacter(id = Random().nextInt(493) + 1)
                        onComplete {
                            character?.let {
                                replaceFragment(CharacterFragment.newInstance(character))
                            }
                        }
                    }
                }
            }

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer_layout.openDrawer(GravityCompat.START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun replaceFragment(fragment: Fragment, addToBackstack: Boolean = false) {
        val tx = fm.beginTransaction()
        tx.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
        tx.replace(R.id.fragment_container, fragment)
        if(addToBackstack){
            tx.addToBackStack(null)
        }
        tx.commit()
    }
}

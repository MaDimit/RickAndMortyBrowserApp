package com.example.maksimdimitrov.rickandmorty.controller

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.maksimdimitrov.rickandmorty.R
import com.example.maksimdimitrov.rickandmorty.controller.items.Character
import com.example.maksimdimitrov.rickandmorty.controller.items.CharacterFragment
import com.example.maksimdimitrov.rickandmorty.controller.items.ItemFragment
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), ItemFragment.OnItemInteractionListener {
    override fun onItemRedirect(url: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val fm = supportFragmentManager
    private val dataSource = BaseDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setNavDrawer()

        fragment_container.setOnClickListener {
            executeAsync(Random().nextInt(493))
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun executeAsync(id : Int) {
        object : AsyncTask<Int, Unit, Character?>() {
            override fun doInBackground(vararg ids: Int?): Character? {
                return dataSource.getCharacter(id = ids[0]!!)
            }

            override fun onPostExecute(character: Character?) {
                if (character != null) {
                    fm.beginTransaction().replace(R.id.fragment_container, CharacterFragment.newInstance(character)).commit()
                }


            }
        }.execute(id)
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

            // Add code here to update the UI based on the item selected
            // For example, swap UI fragments here
            when (menuItem.itemId) {
                R.id.nav_characters -> supportActionBar?.title = "Characters"
                R.id.nav_locations -> supportActionBar?.title = "Locations"
                R.id.nav_episodes -> supportActionBar?.title = "Episodes"
                R.id.nav_search -> supportActionBar?.title = "Search"
                R.id.nav_random -> {supportActionBar?.title = "Random Character"}
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
}

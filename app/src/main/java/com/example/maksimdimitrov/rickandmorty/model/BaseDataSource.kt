package com.example.maksimdimitrov.rickandmorty.model

import android.os.AsyncTask
import android.util.Log
import com.example.maksimdimitrov.rickandmorty.controller.items.Character
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.MalformedURLException
import java.net.URL

object BaseDataSource : DataSource {

    private val TAG = "BASE_DATA_SOURCE"

    private val BASE_URL = "https://rickandmortyapi.com/api/"
    private val LOCATION_URL = "https://rickandmortyapi.com/api/location/"
    private val EPISODE_URL = "https://rickandmortyapi.com/api/episode/"

    //Page constants
    val INFO = "info"
    val RESULTS = "results"

    //Page info constants
    private val COUNT = "count"
    private val PAGES = "pages"
    private val NEXT = "next"
    private val PREV = "prev"

    private val chDataSource = CharacterDataSource

    override fun getCharacter(url: String, id: Int): Model.Character? {
        return chDataSource.getCharacter(url, id)
    }

    override fun getCharacters(pageUrl: String, pageNumber: Int): Model.CharacterPage? {
        return chDataSource.getCharacters(pageUrl, pageNumber)
    }

    override fun findCharacters(name: String, status: String, species: String, type: String, gender: String): Model.CharacterPage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocation(url: String, id: Int): Model.Location? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocations(pageUrl: String, pageNumber: Int): Model.LocationPage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findLocations(name: String, type: String, dimension: String): Model.LocationPage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEpisode(url: String, id: Int): Model.Episode? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEpisodes(pageUrl: String, pageNumber: Int): Model.EpisodePage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findEpisodes(name: String, episode: String): Model.EpisodePage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getJSONString(url: String): String {
        if (!url.contains(BASE_URL)) {
            throw MalformedURLException()
        }
        Log.d(TAG, "getJSONString fired. url: $url")
        return URL(url)
                .openConnection()
                .getInputStream()
                .bufferedReader()
                .use {
                    it.readText()
                }
    }

    fun createPageInfo(jsonObject: JSONObject?): Model.PageInfo? {
        if(jsonObject == null) {
            return null
        }
        return try{val count = jsonObject.getInt(COUNT)
        val pages = jsonObject.getInt(PAGES)
        val next = jsonObject.getString(NEXT)
        val prev = jsonObject.getString(PREV)
            Model.PageInfo(count, pages, next, prev)
        }catch (e : JSONException) {
            e.printStackTrace()
            null
        }
    }

    fun createPageResult(jsonArr: JSONArray?) : List<JSONObject> {
        val result = ArrayList<JSONObject>()
        if(jsonArr == null) {
            return result
        }
        for(i in 0 until jsonArr.length()){
            result.add(jsonArr.getJSONObject(i))
        }
        return result
    }

}
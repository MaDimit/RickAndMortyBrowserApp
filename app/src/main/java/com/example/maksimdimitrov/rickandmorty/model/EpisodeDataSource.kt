package com.example.maksimdimitrov.rickandmorty.model

import android.util.Log
import com.example.maksimdimitrov.rickandmorty.controller.items.Episode
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource.INFO
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource.RESULTS
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object EpisodeDataSource {

    private val EPISODE_URL = "https://rickandmortyapi.com/api/episode/"

    private val ID = "id"
    private val NAME = "name"
    private val AIR_DATE = "air_date"
    private val EPISODE = "episode"
    private val CHARACTERS = "characters"
    private val URL = "url"

    fun getEpisode(url : String, id : Int) : Model.Episode?{
        val requestURL = if (url.isNotEmpty()) url else if (id != -1) "$EPISODE_URL$id" else ""
        val json = BaseDataSource.getJSONString(requestURL)
        return createEpisode(json)
    }

    fun getEpisodes(pageUrl : String, pageNumber : Int) : Model.EpisodePage?{
        val requestURL = if (pageUrl.isNotEmpty()) pageUrl else if (pageNumber != -1) "$EPISODE_URL?page$pageNumber" else EPISODE_URL
        val json = BaseDataSource.getJSONString(requestURL)
        return createEpisodePage(json)
    }

    fun findEpisodes() : Model.EpisodePage?{
        TODO("implement search")
    }

    fun createEpisode(json : String = "", obj : JSONObject = JSONObject(json)) : Model.Episode?{
        return try {
            val id = obj.getInt(ID)
            val name = obj.getString(NAME)
            val airDate = obj.getString(AIR_DATE)
            val episode = obj.getString(EPISODE)
            val characters = getCharacters(obj.getJSONArray(CHARACTERS))
            val url = obj.getString(URL)
            Episode(id, name, airDate, episode, characters, url)
        } catch (e : JSONException) {
            Log.d("EPISODE_DS", e.message.toString())
            null
        }
    }

    private fun getCharacters(jsonArr : JSONArray) : List<String> {
        val list = ArrayList<String>()
        for(i in 0 until jsonArr.length()) {
            list.add(jsonArr[i].toString())
        }
        return list
    }

    private fun createEpisodePage(json: String) : Model.EpisodePage? {
        val obj = JSONObject(json)
        val pageInfo = BaseDataSource.createPageInfo(obj.getJSONObject(INFO)) ?: return null
        val results = BaseDataSource.createPageResult(obj.getJSONArray(RESULTS))
        if(results.isEmpty()) { return null }
        val locations = createEpisodeFromJSONArray(results)
        return Model.EpisodePage(pageInfo, locations)
    }

    private fun createEpisodeFromJSONArray(results : List<JSONObject>) = results
            .map { createEpisode(obj = it) }
            .requireNoNulls()
            .toList()

}
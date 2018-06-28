package com.example.maksimdimitrov.rickandmorty.model

import android.util.Log
import com.example.maksimdimitrov.rickandmorty.controller.items.Character
import com.example.maksimdimitrov.rickandmorty.controller.items.Location
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource.INFO
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource.RESULTS
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object CharacterDataSource {
    private val CHARACTER_URL = "https://rickandmortyapi.com/api/character/"

    private val ID = "id"
    private val NAME = "name"
    private val STATUS = "status"
    private val SPECIES = "species"
    private val TYPE = "type"
    private val GENDER = "gender"
    private val ORIGIN = "origin"
    private val ORIGIN_NAME = "name"
    private val ORIGIN_URL = "url"
    private val LOCATION = "location"
    private val LOCATION_NAME = "name"
    private val LOCATION_URL = "url"
    private val IMAGE = "image"
    private val EPISODE = "episode"
    private val URL = "url"

    fun getCharacter(url: String, id: Int): Model.Character? {
        val requestURL = if (url.isNotEmpty()) url else if (id != -1) "$CHARACTER_URL$id" else ""
        val json = BaseDataSource.getJSONString(requestURL)
        return createCharacter(json)
    }

    fun getCharacters(pageUrl: String, pageNumber: Int): Model.CharacterPage? {
        val requestURL = if (pageUrl.isNotEmpty()) pageUrl else if (pageNumber != -1) "$CHARACTER_URL?page$pageNumber" else CHARACTER_URL
        val json = BaseDataSource.getJSONString(requestURL)
        return createCharactersPage(json)
    }

    fun findCharacters(name: String, status: String, species: String, type: String, gender: String): Model.CharacterPage? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createCharacter(json: String = "", obj: JSONObject = JSONObject(json)): Model.Character? {
        return try {
            val id = obj.getInt(ID)
            val name = obj.getString(NAME)
            val status = obj.getString(STATUS)
            val species = obj.getString(SPECIES)
            val type = obj.getString(TYPE)
            val gender = obj.getString(GENDER)
            val origin = getOrigin(obj.getJSONObject(ORIGIN))
            val location = getLocation(obj.getJSONObject(LOCATION))
            val image = obj.getString(IMAGE)
            val episodes = getEpisodes(obj.getJSONArray(EPISODE))
            val url = obj.getString(URL)
            Character(id, name, status, species, type, gender, origin, location, image, episodes, url)
        } catch (e: JSONException) {
            Log.d("CHARACTER_DS", e.message.toString())
            null
        }
    }

    private fun getOrigin(json: JSONObject): Model.CharacterOrigin {
        return Model.CharacterOrigin(json.getString(ORIGIN_NAME), json.getString(ORIGIN_URL))
    }

    private fun getLocation(json: JSONObject): Model.CharacterLocation {
        return Model.CharacterLocation(json.getString(LOCATION_NAME), json.getString(LOCATION_URL))
    }


    private fun getEpisodes(jsonArr: JSONArray): List<String> {
        val list = ArrayList<String>()
        for (i in 0 until jsonArr.length()) {
            list.add(jsonArr[i].toString())
        }
        return list
    }

    private fun createCharactersPage(json: String): Model.CharacterPage? {
        val obj = JSONObject(json)
        val pageInfo = BaseDataSource.createPageInfo(obj.getJSONObject(INFO)) ?: return null
        val results = BaseDataSource.createPageResult(obj.getJSONArray(RESULTS))
        if(results.isEmpty()) { return null }
        val characters = createCharactersFromJSONArray(results)
        return Model.CharacterPage(pageInfo, characters)
    }

    private fun createCharactersFromJSONArray(results: List<JSONObject>)  = results
            .map { createCharacter(obj = it) }
            .requireNoNulls()
            .toList()
}
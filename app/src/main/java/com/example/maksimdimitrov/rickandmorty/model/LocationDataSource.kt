package com.example.maksimdimitrov.rickandmorty.model

import android.util.Log
import com.example.maksimdimitrov.rickandmorty.controller.items.Location
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource.INFO
import com.example.maksimdimitrov.rickandmorty.model.BaseDataSource.RESULTS
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

object LocationDataSource {

    private val LOCATION_URL = "https://rickandmortyapi.com/api/location/"

    private val ID = "id"
    private val NAME = "name"
    private val TYPE = "type"
    private val DIMENSION = "dimension"
    private val RESIDENTS = "residents"
    private val URL = "url"


    fun getLocation(url : String, id : Int) : Model.Location?{
        val requestURL = if (url.isNotEmpty()) url else if (id != -1) "$LOCATION_URL$id" else ""
        val json = BaseDataSource.getJSONString(requestURL)
        return createLocation(json)
    }

    fun getLocations(pageUrl : String, pageNumber : Int) : Model.LocationPage?{
        val requestURL = if (pageUrl.isNotEmpty()) pageUrl else if (pageNumber != -1) "$LOCATION_URL?page$pageNumber" else LOCATION_URL
        val json = BaseDataSource.getJSONString(requestURL)
        return createLocationPage(json)
    }

    fun findLocations() : Model.LocationPage?{
        TODO("implement search")
    }

    fun createLocation(json : String = "", obj : JSONObject = JSONObject(json)) : Model.Location?{
        return try {
            val id = obj.getInt(ID)
            val name = obj.getString(NAME)
            val type = obj.getString(TYPE)
            val dimension = obj.getString(DIMENSION)
            val residents = getResidents(obj.getJSONArray(RESIDENTS))
            val url = obj.getString(URL)
            Location(id, name, type, dimension, residents, url)
        } catch (e : JSONException) {
            Log.d("LOCATION_DS", e.message.toString())
            null
        }
    }

    private fun getResidents(jsonArr : JSONArray) : List<String> {
        val list = ArrayList<String>()
        for(i in 0 until jsonArr.length()) {
            list.add(jsonArr[i].toString())
        }
        return list
    }

    private fun createLocationPage(json: String) : Model.LocationPage? {
        val obj = JSONObject(json)
        val pageInfo = BaseDataSource.createPageInfo(obj.getJSONObject(INFO)) ?: return null
        val results = BaseDataSource.createPageResult(obj.getJSONArray(RESULTS))
        if(results.isEmpty()) { return null }
        val locations = createLocationsFromJSONArray(results)
        return Model.LocationPage(pageInfo, locations)
    }

    private fun createLocationsFromJSONArray(results : List<JSONObject>) = results
            .map { createLocation(obj = it) }
            .requireNoNulls()
            .toList()
}
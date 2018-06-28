package com.example.maksimdimitrov.rickandmorty.model

interface DataSource {

    fun getCharacter(url : String = "", id : Int = -1) : Model.Character?

    fun getCharacters(pageUrl: String = "", pageNumber: Int = -1) : Model.CharacterPage?

    fun findCharacters(name : String = ""
                       , status : String = ""
                       , species : String = ""
                       , type: String = ""
                       , gender : String = "") : Model.CharacterPage?

    fun getLocation(url : String = "", id : Int = -1) : Model.Location?

    fun getLocations(pageUrl: String = "", pageNumber: Int = -1) : Model.LocationPage?

    fun findLocations(name : String = "", type: String = "", dimension : String = "") : Model.LocationPage?

    fun getEpisode(url : String = "", id : Int = -1) : Model.Episode?

    fun getEpisodes(pageUrl: String = "", pageNumber: Int = -1) : Model.EpisodePage?

    fun findEpisodes(name : String = "", episode: String = "") : Model.EpisodePage?
}
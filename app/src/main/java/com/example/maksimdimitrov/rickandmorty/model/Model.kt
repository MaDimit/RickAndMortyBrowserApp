package com.example.maksimdimitrov.rickandmorty.model

import android.os.Parcel
import android.os.Parcelable

class Model {
    class CharacterOrigin(val name : String, val url : String) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<CharacterOrigin> {
            override fun createFromParcel(parcel: Parcel): CharacterOrigin {
                return CharacterOrigin(parcel)
            }

            override fun newArray(size: Int): Array<CharacterOrigin?> {
                return arrayOfNulls(size)
            }
        }
    }

    class CharacterLocation(val name : String, val url : String) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readString(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(name)
            parcel.writeString(url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<CharacterLocation> {
            override fun createFromParcel(parcel: Parcel): CharacterLocation {
                return CharacterLocation(parcel)
            }

            override fun newArray(size: Int): Array<CharacterLocation?> {
                return arrayOfNulls(size)
            }
        }
    }

    class Character(val id: Int
                    , val name: String
                    , val status: String
                    , val species: String
                    , val type: String
                    , val gender: String
                    , val origin: CharacterOrigin
                    , val location : CharacterLocation
                    , val image : String
                    , val episodes : List<String>
                    , val url : String) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.readParcelable(CharacterOrigin::class.java.classLoader),
                parcel.readParcelable(CharacterLocation::class.java.classLoader),
                parcel.readString(),
                parcel.createStringArrayList(),
                parcel.readString())

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(name)
            parcel.writeString(status)
            parcel.writeString(species)
            parcel.writeString(type)
            parcel.writeString(gender)
            parcel.writeParcelable(origin, flags)
            parcel.writeParcelable(location, flags)
            parcel.writeString(image)
            parcel.writeStringList(episodes)
            parcel.writeString(url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Character> {
            override fun createFromParcel(parcel: Parcel): Character {
                return Character(parcel)
            }

            override fun newArray(size: Int): Array<Character?> {
                return arrayOfNulls(size)
            }
        }
    }

    class Location(val id: Int
                   , val name: String
                   , val type: String, val dimension : String
                   , val residents : List<String>
                   , val url : String ) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.createStringArrayList(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(name)
            parcel.writeString(type)
            parcel.writeString(dimension)
            parcel.writeStringList(residents)
            parcel.writeString(url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Location> {
            override fun createFromParcel(parcel: Parcel): Location {
                return Location(parcel)
            }

            override fun newArray(size: Int): Array<Location?> {
                return arrayOfNulls(size)
            }
        }
    }

    class Episode(val id: Int
                  , val name: String
                  , val airDate: String
                  , val episode : String
                  , val characters: List<String>
                  , val url: String) : Parcelable {
        constructor(parcel: Parcel) : this(
                parcel.readInt(),
                parcel.readString(),
                parcel.readString(),
                parcel.readString(),
                parcel.createStringArrayList(),
                parcel.readString()) {
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(name)
            parcel.writeString(airDate)
            parcel.writeString(episode)
            parcel.writeStringList(characters)
            parcel.writeString(url)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Episode> {
            override fun createFromParcel(parcel: Parcel): Episode {
                return Episode(parcel)
            }

            override fun newArray(size: Int): Array<Episode?> {
                return arrayOfNulls(size)
            }
        }
    }
}
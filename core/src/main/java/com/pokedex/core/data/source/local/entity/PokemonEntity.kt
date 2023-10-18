package com.pokedex.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemon", indices = [androidx.room.Index(value = ["name"], unique = true)])
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    var uId : Int = 0,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "url")
    var url: String?,
)
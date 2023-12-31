package com.pokedex.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pokedex.core.data.source.local.entity.PokemonEntity

@Database(entities = [PokemonEntity::class,], version = 1, exportSchema = false)
abstract class PokedexDatabase : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao
}
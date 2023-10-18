package com.pokedex.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.pokedex.core.data.source.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon")
    fun getPokemons(): Flow<List<PokemonEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemons(pokemonList: List<PokemonEntity>)

}
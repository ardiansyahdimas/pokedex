package com.pokedex.core.data.source.local

import com.pokedex.core.data.source.local.entity.PokemonEntity
import com.pokedex.core.data.source.local.room.PokemonDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val pokemonDao: PokemonDao,
) {
    fun getPokemons():Flow<List<PokemonEntity>> = pokemonDao.getPokemons()
    suspend fun insertPokemons(pokemonList: List<PokemonEntity>) = pokemonDao.insertPokemons(pokemonList)
}
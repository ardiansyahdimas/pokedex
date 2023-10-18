package com.pokedex.core.domain.repository

import com.pokedex.core.data.Resource
import com.pokedex.core.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    fun getPokemons(offset:Int): Flow<Resource<List<PokemonModel>>>
}
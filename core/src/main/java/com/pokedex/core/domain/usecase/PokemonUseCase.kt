package com.pokedex.core.domain.usecase

import com.pokedex.core.data.Resource
import com.pokedex.core.domain.model.PokemonModel
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    fun getPokemons(offset:Int): Flow<Resource<List<PokemonModel>>>
}
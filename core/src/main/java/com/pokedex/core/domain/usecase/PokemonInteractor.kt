package com.pokedex.core.domain.usecase

import com.pokedex.core.data.Resource
import com.pokedex.core.domain.model.PokemonModel
import com.pokedex.core.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonInteractor @Inject constructor(private val pokemonRepository: IPokemonRepository):
    PokemonUseCase {
    override fun getPokemons(offset: Int): Flow<Resource<List<PokemonModel>>> = pokemonRepository.getPokemons(offset)
}
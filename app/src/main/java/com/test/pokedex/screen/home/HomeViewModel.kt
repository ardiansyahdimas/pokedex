package com.test.pokedex.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.pokedex.core.data.Resource
import com.pokedex.core.domain.model.PokemonModel
import com.pokedex.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor (private val pokemonUseCase: PokemonUseCase) : ViewModel() {
    var resultValuePokemon : LiveData<Resource<List<PokemonModel>>>? =null

    fun getPokemons(offset:Int){
        resultValuePokemon = pokemonUseCase.getPokemons(offset).asLiveData()
    }


}
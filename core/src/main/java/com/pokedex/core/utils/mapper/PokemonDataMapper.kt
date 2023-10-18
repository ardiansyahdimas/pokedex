package com.pokedex.core.utils.mapper


import com.pokedex.core.data.source.local.entity.PokemonEntity
import com.pokedex.core.data.source.remote.response.ResultsPokemonItem
import com.pokedex.core.domain.model.PokemonModel

object PokemonDataMapper {
    fun mapResponsesToEntities(input: List<ResultsPokemonItem>): List<PokemonEntity> {
        val pokemonList = ArrayList<PokemonEntity>()
        input.map {
            val pokemon = PokemonEntity(
                name  = it.name,
                url = it.url
            )
            pokemonList.add(pokemon)
        }
        return pokemonList
    }

    fun mapEntitiesToDomain(input: List<PokemonEntity>): List<PokemonModel> =
        input.map {
            PokemonModel(
                id = it.uId,
                name  = it.name,
                url = it.url
            )
        }
}
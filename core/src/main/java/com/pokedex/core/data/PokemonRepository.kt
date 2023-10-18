package com.pokedex.core.data

import com.pokedex.core.NetworkBoundResource
import com.pokedex.core.data.source.local.LocalDataSource
import com.pokedex.core.data.source.remote.RemoteDataSource
import com.pokedex.core.data.source.remote.network.ApiResponse
import com.pokedex.core.data.source.remote.response.ResultsPokemonItem
import com.pokedex.core.domain.model.PokemonModel
import com.pokedex.core.domain.repository.IPokemonRepository
import com.pokedex.core.utils.mapper.PokemonDataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : IPokemonRepository {

    override fun getPokemons(offset:Int): Flow<Resource<List<PokemonModel>>> =
        object : NetworkBoundResource<List<PokemonModel>, List<ResultsPokemonItem>>() {
            override fun loadFromDB(): Flow<List<PokemonModel>>  {
                return localDataSource.getPokemons().map { PokemonDataMapper.mapEntitiesToDomain(it)}
            }

            override fun shouldFetch(data: List<PokemonModel>?): Boolean =
                if (offset < 21) {
                    data.isNullOrEmpty()
                } else {
                    true
                }

            override suspend fun createCall(): Flow<ApiResponse<List<ResultsPokemonItem>>> = remoteDataSource.getPokemons(offset)


            override suspend fun saveCallResult(data: List<ResultsPokemonItem>) {
                val pokemonList = PokemonDataMapper.mapResponsesToEntities(data)
                localDataSource.insertPokemons(pokemonList)
            }
        }.asFlow()

}
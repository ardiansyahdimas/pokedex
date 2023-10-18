package com.pokedex.core.data.source.remote

import com.pokedex.core.data.source.remote.network.ApiResponse
import com.pokedex.core.data.source.remote.network.ApiService
import com.pokedex.core.data.source.remote.response.GetPokemonResponse
import com.pokedex.core.data.source.remote.response.ResultsPokemonItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    val TAG = "RemoteDataSource"
    private val pageSize = 20

    suspend fun getPokemons(offset:Int): Flow<ApiResponse<List<ResultsPokemonItem>>> {
        return flow {
            try {
                val response =apiService.getPokemons(offset, pageSize)
                val dataArray = response.results
                if(dataArray?.isNotEmpty() == true){
                    emit(ApiResponse.Success(response.results))
                }else {
                    emit(ApiResponse.Empty)
                }
            }catch (e:Exception){
                emit(ApiResponse.Error(e.toString()))
                Timber.tag(TAG).d("getPokemons: ${e.message}")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPokemonById(id: Int): Flow<GetPokemonResponse> {
        return flow {
            val response = apiService.getPokemonById(id)
            emit(response)
        }
    }
}

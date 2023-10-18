package com.test.pokedex.screen.detail

import androidx.lifecycle.ViewModel
import com.pokedex.core.data.source.remote.RemoteDataSource
import com.pokedex.core.data.source.remote.response.GetPokemonResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor (private val remoteDataSource: RemoteDataSource) : ViewModel() {
    suspend fun getPokemonById(id: Int): Flow<GetPokemonResponse> {
        return remoteDataSource.getPokemonById(id)
    }
}
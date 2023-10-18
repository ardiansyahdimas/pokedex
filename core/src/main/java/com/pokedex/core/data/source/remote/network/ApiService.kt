package com.pokedex.core.data.source.remote.network


import com.pokedex.core.data.source.remote.response.GetListPokemonResponse
import com.pokedex.core.data.source.remote.response.GetPokemonResponse
import retrofit2.http.*


interface ApiService {

    @Headers("Content-Type: application/json")
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("offset") offset: Int,
        @Query("limit") limit: Int
    ): GetListPokemonResponse

    @GET("pokemon/{id}/")
    suspend fun getPokemonById(
        @Path("id") id: Int
    ): GetPokemonResponse

}

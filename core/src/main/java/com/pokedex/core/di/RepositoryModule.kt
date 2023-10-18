package com.pokedex.core.di

import com.pokedex.core.data.PokemonRepository
import com.pokedex.core.domain.repository.IPokemonRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providePokemonRepository(pokemonRepository: PokemonRepository): IPokemonRepository
}
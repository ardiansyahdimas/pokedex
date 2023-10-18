package com.pokedex.core.di

import android.content.Context
import androidx.room.Room
import com.pokedex.core.data.source.local.room.PokedexDatabase
import com.pokedex.core.data.source.local.room.PokemonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context : Context): PokedexDatabase {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("astra".toCharArray())
        val factory = SupportFactory(passphrase)
        return Room.databaseBuilder(
            context,
            PokedexDatabase::class.java, "Pokedex.db"
        ).fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }

    @Provides
    fun providePokemonDao(database: PokedexDatabase): PokemonDao = database.pokemonDao()

}
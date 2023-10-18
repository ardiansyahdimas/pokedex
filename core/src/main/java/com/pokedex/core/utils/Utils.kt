package com.pokedex.core.utils

object Utils {
    fun getPokemonIdFromUrl(url: String?): String? {
        return url?.substringAfter("pokemon/")?.substringBefore("/")
    }
}
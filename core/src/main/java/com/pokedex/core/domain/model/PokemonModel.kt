package com.pokedex.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonModel(
    val id: Int,
    val name: String?,
    val url: String?,
):Parcelable
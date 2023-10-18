package com.pokedex.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class GetListPokemonResponse(

	@field:SerializedName("next")
	val next: String? = null,

	@field:SerializedName("previous")
	val previous: String? = null,

	@field:SerializedName("count")
	val count: Int? = null,

	@field:SerializedName("results")
	val results: List<ResultsPokemonItem>? = null
)

data class ResultsPokemonItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

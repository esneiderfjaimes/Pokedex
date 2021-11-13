package com.red.courses.pokedex.models

class PokemonResponse {
    val results: MutableList<Pokemon> = mutableListOf()
    val next: String? = null
    val previous: String? = null
}
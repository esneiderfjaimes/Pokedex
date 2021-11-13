package com.red.courses.pokedex.api

import com.red.courses.pokedex.models.PokemonResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    fun getListPokemon(@Url url: String): Call<PokemonResponse>
}

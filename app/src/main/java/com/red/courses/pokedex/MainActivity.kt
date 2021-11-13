package com.red.courses.pokedex

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import coil.annotation.ExperimentalCoilApi
import com.red.courses.pokedex.api.ApiService
import com.red.courses.pokedex.models.Pokemon
import com.red.courses.pokedex.models.PokemonResponse
import com.red.courses.pokedex.ui.setContent
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

@ExperimentalCoilApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        processUrl("$urlBasePokemon?${offsetKey}0${limitKey}20/")
    }

    companion object {
        const val urlBasePokemon = "https://pokeapi.co/api/v2/pokemon/"
        const val offsetKey = "offset="
        const val limitKey = "limit="
    }

    private fun processUrl(url: String) {
        val split = url.replace("$urlBasePokemon?$offsetKey", "")
            .replace("&$limitKey", ";").replace("/", "").split(";")

        val offset = try {
            split[0].toInt()
        } catch (e: Exception) {
            e.printStackTrace(); 0
        }

        val limit = try {
            split[1].toInt()
        } catch (e: Exception) {
            e.printStackTrace(); 20
        }

        // Retrofit
        Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create()).build()
            .create<ApiService>().getListPokemon(url).enqueue(getCallback(offset, limit))
    }

    private fun getCallback(offset: Int, limit: Int) = object : Callback<PokemonResponse?> {
        override fun onResponse(
            call: Call<PokemonResponse?>,
            response: Response<PokemonResponse?>
        ) {
            if (response.isSuccessful) {
                if (response.body() != null) {
                    val body: PokemonResponse = response.body()!!
                    val listPokemon: MutableList<Pokemon> = body.results
                    setContent(
                        this@MainActivity,
                        listPokemon,
                        offset, limit,
                        onClickNext = {
                            body.next?.let { processUrl("$it/") }
                        },
                        onClickPrevious = {
                            body.previous?.let { processUrl("$it/") }
                        },
                        body.previous != null,
                        body.next != null
                    )
                }
            } else Log.e("MainLog", "onFailure: " + response.errorBody())
        }

        override fun onFailure(call: Call<PokemonResponse?>, t: Throwable) {
            Log.e("MainLog", "onFailure: ", t)
        }
    }
}

package com.red.courses.pokedex.ui

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.red.courses.pokedex.models.Pokemon
import com.red.courses.pokedex.ui.theme.PokedexTheme

@ExperimentalCoilApi
@ExperimentalFoundationApi
fun setContent(
    activity: ComponentActivity,
    listPokemon: MutableList<Pokemon>,
    offset: Int, limit: Int,
    onClickNext: () -> Unit,
    onClickPrevious: () -> Unit,
    enablePrevious: Boolean = false,
    enableNext: Boolean = false
) = activity.setContent {
    PokedexTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column {
                TopBar(offset, limit, onClickNext, onClickPrevious, enablePrevious, enableNext)

                LazyVerticalGrid(cells = GridCells.Fixed(3)) {
                    itemsIndexed(listPokemon) { index, pokemon ->
                        ItemPokemon(offset + index + 1, pokemon)
                    }
                }
            }
        }
    }
}

@Composable
fun TopBar(
    offset: Int,
    limit: Int,
    onClickNext: () -> Unit,
    onClickPrevious: () -> Unit,
    enablePrevious: Boolean = false,
    enableNext: Boolean = false
) {
    TopAppBar(
        title = { Text("Pokedex, index:${offset + 1}-${offset + limit}") },
        actions = {
            IconButton(onClick = onClickPrevious, enabled = enablePrevious) {
                Icon(Icons.Filled.ArrowBack, null)
            }
            IconButton(onClick = onClickNext, enabled = enableNext) {
                Icon(Icons.Filled.ArrowForward, null)
            }
        }
    )
}

@Preview
@Composable
fun Preview() {
    TopBar(0, 20, onClickNext = {}, onClickPrevious = {})
}

@ExperimentalCoilApi
@Composable
fun ItemPokemon(index: Int, pokemon: Pokemon) {
    val urlImage =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$index.png"
    Log.i("ItemPokemon", "ItemPokemon: url: $urlImage")
    Card(Modifier.padding(5.dp)) {
        Column(Modifier.fillMaxWidth()) {
            Image(
                rememberImagePainter(urlImage),
                null,
                Modifier
                    .height(100.dp)
                    .align(Alignment.CenterHorizontally)
            )
            Text("$index:${pokemon.name}", Modifier.align(Alignment.CenterHorizontally))
        }
    }
}
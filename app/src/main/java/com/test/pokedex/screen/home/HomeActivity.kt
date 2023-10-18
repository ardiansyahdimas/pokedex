package com.test.pokedex.screen.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pokedex.core.R
import com.pokedex.core.data.Resource
import com.pokedex.core.ui.PokemonListAdapter
import com.pokedex.core.utils.Utils.getPokemonIdFromUrl
import com.test.pokedex.PokedexApp
import com.test.pokedex.databinding.ActivityHomeBinding
import com.test.pokedex.screen.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var pokemonListAdapter: PokemonListAdapter
    private var currentOffset = 20
    private var isSearch = false
    private var isAscending = true
    private var isSort = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        pokemonListAdapter = PokemonListAdapter()

        getPokemons(currentOffset, isAscending)
        setupRecyclerView()
        menu()
    }

    fun getPokemons(offset:Int, isAscending:Boolean){
        viewModel.getPokemons(offset)
        viewModel.resultValuePokemon?.observe(this){result ->
            when (result) {
                is Resource.Loading -> {
                    progressBar(true)
                }
                is Resource.Success -> {
                    progressBar(false)
                    val resultData =  if (isAscending) result.data?.sortedBy { it.name } else result.data?.sortedByDescending { it.name }
                    if (offset > 20 && !isSort) {
                        resultData?.forEach {
                            pokemonListAdapter.addItem(it)
                        }
                    } else {
                        pokemonListAdapter.setData(resultData)
                    }
                    isSort = false
                }
                is Resource.Error -> {
                    progressBar(false)
                }
                else -> {
                    progressBar(false)
                }
            }
        }

        pokemonListAdapter.onItemClick = {pokemon ->
            val pokemonId = getPokemonIdFromUrl(pokemon.url)
            val intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra(DetailActivity.POKEMON_ID, pokemonId)
            startActivity(intent)
        }

        if (offset < 21) {
            with(binding.rvPokemon) {
                layoutManager = GridLayoutManager(PokedexApp.context, 2)
                setHasFixedSize(true)
                adapter = pokemonListAdapter
            }
        }
    }

    private fun progressBar(isLoading:Boolean){
        binding.progressbar.isVisible = isLoading
    }


    private fun setupRecyclerView() {
        binding.rvPokemon.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if ((visibleItemCount + firstVisibleItem) >= totalItemCount && !isSearch && !isSort) {
                    currentOffset += 20
                    getPokemons(currentOffset, isAscending)
                }
            }
        })
    }

    private  fun menu() {
        val menuHost: MenuHost = this
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menu.clear()
                menuInflater.inflate(R.menu.menu_search, menu)
                val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
                val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView
                searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
                searchView.queryHint = resources.getString(R.string.search_by_name)
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String): Boolean {
                        isSearch = query.isNotEmpty()
                        if (query.isNotEmpty()) {
                            pokemonListAdapter.filter.filter(query)
                        }
                        return true
                    }

                    override fun onQueryTextChange(newText: String): Boolean {
                        isSearch = newText.isNotEmpty()
                        pokemonListAdapter.filter.filter(newText)
                        return false
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.action_sort -> {
                        isAscending = !isAscending
                        isSort = true
                        getPokemons(currentOffset, isAscending)
                        true
                    }
                    else -> false
                }
            }
        }, this , Lifecycle.State.RESUMED)
    }
}
package com.test.pokedex.screen.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.pokedex.core.ui.AbilitieAdapter
import com.test.pokedex.PokedexApp
import com.test.pokedex.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private lateinit var abilitieAdapter: AbilitieAdapter

    companion object {
        const val POKEMON_ID = "pokemon_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        abilitieAdapter = AbilitieAdapter()
        updateUi(true)
        val pokemonId = intent?.getStringExtra(POKEMON_ID)
        if (pokemonId != null){
            showImage(pokemonId)
            showData(pokemonId.toInt())
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    @SuppressLint("SetTextI18n")
    private fun showData(pokemonId: Int){
        lifecycleScope.launch {
            viewModel.getPokemonById(pokemonId).asLiveData().observe(this@DetailActivity) {result ->
                if (result != null) {
                    updateUi(false)
                    binding.tvName.text = "Name: ${result.name}"
                    binding.tvOrder.text = "Order: ${result.order}"
                    binding.tvSpecies.text = "Species: ${result.species?.name}"
                    binding.tvWeight.text = "Weight: ${result.weight}"
                    binding.tvHeight.text = "Height: ${result.height}"
                    val listAbilitesName = arrayListOf<String>()
                    result.abilities?.forEach {
                        listAbilitesName.add(it?.ability?.name.toString())
                    }
                    abilitieAdapter.setData(listAbilitesName)
                }
            }
            with(Dispatchers.Main) {
                val rvAbilitie = binding.rvAbilities
                rvAbilitie.layoutManager = GridLayoutManager(PokedexApp.context, 2)
                rvAbilitie.setHasFixedSize(true)
                rvAbilitie.adapter = abilitieAdapter
            }
        }
    }

    private fun showImage(pokemonId:String){
        with(binding) {
            Glide.with(PokedexApp.context)
                .load(getString(com.pokedex.core.R.string.base_url_image, pokemonId))
                .into(image)
        }
    }

    private fun updateUi(isLoading:Boolean){
        binding.containerInfo.isVisible = !isLoading
        binding.tvAbilities.isVisible = !isLoading
        binding.rvAbilities.isVisible = !isLoading
        binding.progressbar.isVisible  = isLoading
    }
}
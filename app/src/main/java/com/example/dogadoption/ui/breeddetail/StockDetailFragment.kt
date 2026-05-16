package com.example.dogadoption.ui.breeddetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.dogadoption.R
import com.example.dogadoption.data.remote.model.NewsItem
import com.example.dogadoption.databinding.FragmentStockDetailBinding
import com.example.dogadoption.util.Resource
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StockDetailFragment : Fragment() {

    private var _binding: FragmentStockDetailBinding? = null
    private val binding get() = _binding!!

    private val viewModel: StockDetailViewModel by viewModels()
    private lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStockDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val symbol = arguments?.getString("symbol") ?: ""
        val logoUrl = arguments?.getString("logoUrl") ?: ""
        val name = arguments?.getString("name") ?: ""

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val statusBarHeight = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top
            v.setPadding(0, statusBarHeight, 0, 0)
            insets
        }

        setupRecyclerView()
        setupInitialInfo(symbol, logoUrl, name)
        observeViewModel()

        viewModel.loadStockDetails(symbol, logoUrl, name)

        binding.btnBack.setOnClickListener { findNavController().navigateUp() }
        binding.fabFavorite.setOnClickListener { viewModel.toggleWatchlist() }
        binding.btnCloseFullscreen.setOnClickListener { dismissFullscreen() }
        binding.fullscreenOverlay.setOnClickListener { dismissFullscreen() }
        binding.imageFullscreen.setOnClickListener {}
        binding.buttonAdopt.setOnClickListener {
            val bundle = Bundle().apply { putString("symbol", symbol) }
            findNavController().navigate(R.id.action_breedDetailFragment_to_adoptionFragment, bundle)
        }
    }

    private fun setupRecyclerView() {
        newsAdapter = NewsAdapter { newsItem -> showFullscreenImage(newsItem) }
        binding.recyclerViewImages.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewImages.adapter = newsAdapter
    }

    private fun showFullscreenImage(newsItem: NewsItem) {
        Glide.with(this)
            .load(newsItem.image.ifBlank { null })
            .placeholder(R.drawable.ic_placeholder_stock)
            .error(R.drawable.ic_placeholder_stock)
            .into(binding.imageFullscreen)
        binding.fullscreenOverlay.visibility = View.VISIBLE
    }

    private fun dismissFullscreen() {
        binding.fullscreenOverlay.visibility = View.GONE
    }

    private fun setupInitialInfo(symbol: String, logoUrl: String, name: String) {
        binding.textBreedTitle.text = name.ifBlank { symbol }
        binding.textSubBreeds.text = symbol
        binding.textStatSubbreedsValue.text = getString(R.string.label_stat_none)
        Glide.with(this)
            .load(logoUrl.ifBlank { null })
            .placeholder(R.drawable.ic_placeholder_stock)
            .error(R.drawable.ic_placeholder_stock)
            .centerInside()
            .into(binding.imageHero)
    }

    private fun observeViewModel() {
        viewModel.detailState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    val (profile, quote) = resource.data!!
                    binding.textBreedTitle.text = profile.name
                    binding.textSubBreeds.text = if (profile.exchange.isNotBlank()) {
                        "${arguments?.getString("symbol")} • ${profile.exchange}"
                    } else {
                        arguments?.getString("symbol") ?: ""
                    }
                    val priceText = if (quote.currentPrice > 0.0) {
                        getString(R.string.price_format, quote.currentPrice)
                    } else {
                        getString(R.string.label_stat_none)
                    }
                    binding.textStatSubbreedsValue.text = priceText
                    val changeText = if (quote.changePercent != 0.0) {
                        getString(R.string.change_format, quote.changePercent)
                    } else {
                        getString(R.string.label_stat_none)
                    }
                    binding.textAboutDescription.text = getString(
                        R.string.stock_detail_description,
                        profile.finnhubIndustry.ifBlank { getString(R.string.label_stat_none) },
                        changeText,
                        if (profile.weburl.isNotBlank()) profile.weburl else getString(R.string.label_stat_none)
                    )
                    Glide.with(this)
                        .load(profile.logo.ifBlank { null })
                        .placeholder(R.drawable.ic_placeholder_stock)
                        .error(R.drawable.ic_placeholder_stock)
                        .centerInside()
                        .into(binding.imageHero)
                }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(binding.root, getString(R.string.error_loading_data), Snackbar.LENGTH_LONG).show()
                }
            }
        }

        viewModel.newsState.observe(viewLifecycleOwner) { resource ->
            if (resource is Resource.Success) {
                newsAdapter.submitList(resource.data ?: emptyList())
            }
        }

        viewModel.isInWatchlist.observe(viewLifecycleOwner) { isWatched ->
            val icon = if (isWatched) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            binding.fabFavorite.setImageResource(icon)
            val desc = if (isWatched) {
                getString(R.string.remove_from_favorites)
            } else {
                getString(R.string.add_to_favorites)
            }
            binding.fabFavorite.contentDescription = desc
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

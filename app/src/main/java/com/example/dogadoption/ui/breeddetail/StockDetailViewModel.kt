package com.example.dogadoption.ui.breeddetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.dogadoption.data.local.entity.WatchlistEntity
import com.example.dogadoption.data.remote.model.CompanyProfile
import com.example.dogadoption.data.remote.model.NewsItem
import com.example.dogadoption.data.remote.model.Quote
import com.example.dogadoption.data.repository.StocksRepository
import com.example.dogadoption.data.repository.WatchlistRepository
import com.example.dogadoption.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockDetailViewModel @Inject constructor(
    private val stocksRepository: StocksRepository,
    private val watchlistRepository: WatchlistRepository
) : ViewModel() {

    private val _detailState = MutableLiveData<Resource<Pair<CompanyProfile, Quote>>>()
    val detailState: LiveData<Resource<Pair<CompanyProfile, Quote>>> = _detailState

    private val _newsState = MutableLiveData<Resource<List<NewsItem>>>()
    val newsState: LiveData<Resource<List<NewsItem>>> = _newsState

    private val _currentSymbol = MutableLiveData<String>()
    val isInWatchlist: LiveData<Boolean> = _currentSymbol.switchMap { symbol ->
        watchlistRepository.isInWatchlist(symbol).asLiveData()
    }

    private var currentSymbol: String = ""
    private var currentLogoUrl: String = ""
    private var currentName: String = ""
    private var isLoaded = false

    fun loadStockDetails(symbol: String, logoUrl: String, name: String) {
        _currentSymbol.value = symbol
        if (isLoaded) return
        isLoaded = true
        currentSymbol = symbol
        currentLogoUrl = logoUrl
        currentName = name
        _detailState.value = Resource.Loading()
        viewModelScope.launch {
            _detailState.value = stocksRepository.getStockDetail(symbol)
        }
        viewModelScope.launch {
            _newsState.value = stocksRepository.getStockNews(symbol)
        }
    }

    fun toggleWatchlist() {
        viewModelScope.launch {
            val existing = watchlistRepository.getWatchlistItem(currentSymbol)
            if (existing != null) {
                watchlistRepository.removeFromWatchlist(existing)
            } else {
                watchlistRepository.addToWatchlist(
                    WatchlistEntity(
                        symbol = currentSymbol,
                        name = currentName,
                        logoUrl = currentLogoUrl
                    )
                )
            }
        }
    }
}

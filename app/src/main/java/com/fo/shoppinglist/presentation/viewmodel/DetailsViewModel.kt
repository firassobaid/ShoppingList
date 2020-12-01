package com.fo.shoppinglist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fo.shoppinglist.data.source.ProductsRepository
import com.fo.shoppinglist.data.source.Result
import com.fo.shoppinglist.presentation.viewstate.*
import com.fo.shoppinglist.util.SingleEvent
import kotlinx.coroutines.launch

class DetailsViewModel(private val productsRepository: ProductsRepository) : ViewModel() {

    private val _viewState = MutableLiveData<DetailsViewState>()
    val viewState: LiveData<DetailsViewState> = _viewState

    private val _singleEvent = MutableLiveData<SingleEvent<DetailsSingleEventState>>()
    val singleEvent: LiveData<SingleEvent<DetailsSingleEventState>> = _singleEvent

    val name = MutableLiveData<String>()

    fun start(productId: String) {
        viewModelScope.launch {
            val product = productsRepository.getProductById(productId)
            if (product is Result.Success) {
                _viewState.value = InitDetailsViewState(product = product.data)
                //update the name in the view
                name.value = product.data.name
            } else {
                _viewState.value = ErrorDetailsViewState
            }
        }
    }

    fun deleteItem(productId: String) {
        viewModelScope.launch {
            productsRepository.deleteProductById(productId)
            _singleEvent.value = SingleEvent(NavigateToShoppingListFragment)
        }
    }

    fun deleteButtonPress() {
        _singleEvent.value = SingleEvent(ShowDialogEvent)
    }
}
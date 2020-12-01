package com.fo.shoppinglist.presentation.viewmodel

import androidx.lifecycle.*
import com.fo.shoppinglist.data.model.Product
import com.fo.shoppinglist.data.source.ProductsRepository
import com.fo.shoppinglist.data.source.Result
import com.fo.shoppinglist.presentation.viewstate.NavigateToAddProduct
import com.fo.shoppinglist.presentation.viewstate.NavigateToProductDetails
import com.fo.shoppinglist.presentation.viewstate.ShoppingListSingleEventState
import com.fo.shoppinglist.presentation.viewstate.ShoppingListViewState
import com.fo.shoppinglist.util.SingleEvent

class ShoppingListViewModel(repository: ProductsRepository) : ViewModel() {

    private val _viewState = MutableLiveData<ShoppingListViewState>()
    val viewState: LiveData<ShoppingListViewState> = _viewState

    private val _singleEvent = MutableLiveData<SingleEvent<ShoppingListSingleEventState>>()
    val singleEvent: LiveData<SingleEvent<ShoppingListSingleEventState>> = _singleEvent

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _products = repository.observeProducts()
            .distinctUntilChanged()
            .switchMap { filterProducts(it) }


    val products: LiveData<List<Product>> = _products

    private fun filterProducts(productsResult: Result<List<Product>>): LiveData<List<Product>> {
        val result = MutableLiveData<List<Product>>()
        if (productsResult is Result.Success) {
            result.value = productsResult.data
        } else {
            //Show error state
            result.value = emptyList()
        }
        return result
    }

    fun onFabPress() {
        _singleEvent.value = SingleEvent(NavigateToAddProduct)
    }

    fun onItemSelect(product: Product) {
        _singleEvent.value = SingleEvent(NavigateToProductDetails(product))
    }
}
package com.fo.shoppinglist.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fo.shoppinglist.data.model.Product
import com.fo.shoppinglist.data.source.ProductsRepository
import com.fo.shoppinglist.presentation.viewstate.AddButtonPressed
import com.fo.shoppinglist.presentation.viewstate.AddProductSingleEventViewState
import com.fo.shoppinglist.presentation.viewstate.AddProductViewState
import com.fo.shoppinglist.presentation.viewstate.EmptyName
import com.fo.shoppinglist.util.SingleEvent
import kotlinx.coroutines.launch
import java.util.*

class AddProductViewModel(private val productsRepository: ProductsRepository) : ViewModel() {

    val productName = MutableLiveData<String>()

    private val _viewState = MutableLiveData<AddProductViewState>()
    val viewState: LiveData<AddProductViewState> = _viewState

    private val _singleEvent = MutableLiveData<SingleEvent<AddProductSingleEventViewState>>()
    val singleEvent: LiveData<SingleEvent<AddProductSingleEventViewState>> = _singleEvent

    fun onAddButtonPressed() {
        val name = productName.value
        if (name.isNullOrBlank()) {
            _viewState.value = EmptyName
        } else {
            viewModelScope.launch {
                productsRepository.addProduct(
                    Product(
                        name.toString(),
                        UUID.randomUUID().toString()
                    )
                )
                _singleEvent.value = SingleEvent(AddButtonPressed)
            }
        }
    }

}
package com.fo.shoppinglist.util

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.fo.shoppinglist.data.source.ProductsRepository
import com.fo.shoppinglist.presentation.viewmodel.AddProductViewModel
import com.fo.shoppinglist.presentation.viewmodel.DetailsViewModel
import com.fo.shoppinglist.presentation.viewmodel.ShoppingListViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val productsRepository: ProductsRepository,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ) = with(modelClass) {
        when {
            isAssignableFrom(ShoppingListViewModel::class.java) ->
                ShoppingListViewModel(productsRepository)
            isAssignableFrom(AddProductViewModel::class.java) ->
                AddProductViewModel(productsRepository)
            isAssignableFrom(DetailsViewModel::class.java) ->
                DetailsViewModel(productsRepository)
            else ->
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    } as T
}
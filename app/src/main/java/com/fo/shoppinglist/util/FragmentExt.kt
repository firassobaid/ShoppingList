package com.fo.shoppinglist.util

import androidx.fragment.app.Fragment
import com.fo.shoppinglist.ShoppingListApplication


fun Fragment.getViewModelFactory(): ViewModelFactory {
    val repo = (requireContext().applicationContext as ShoppingListApplication).productsRepository
    return ViewModelFactory(repo, this)
}
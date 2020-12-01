package com.fo.shoppinglist.presentation.viewstate

import com.fo.shoppinglist.data.model.Product

sealed class ShoppingListViewState
object ErrorState : ShoppingListViewState()

sealed class ShoppingListSingleEventState
object NavigateToAddProduct : ShoppingListSingleEventState()
data class NavigateToProductDetails(val product: Product) : ShoppingListSingleEventState()



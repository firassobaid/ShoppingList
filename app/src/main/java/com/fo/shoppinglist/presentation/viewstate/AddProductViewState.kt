package com.fo.shoppinglist.presentation.viewstate

sealed class AddProductViewState
object EmptyName: AddProductViewState()

sealed class AddProductSingleEventViewState
object AddButtonPressed: AddProductSingleEventViewState()

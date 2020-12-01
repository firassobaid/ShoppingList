package com.fo.shoppinglist.presentation.viewstate

import com.fo.shoppinglist.data.model.Product

sealed class DetailsViewState
data class InitDetailsViewState(val product: Product) : DetailsViewState()
object ErrorDetailsViewState : DetailsViewState()

sealed class DetailsSingleEventState
object ShowDialogEvent : DetailsSingleEventState()
object NavigateToShoppingListFragment : DetailsSingleEventState()

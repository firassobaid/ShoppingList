package com.fo.shoppinglist

import android.app.Application
import com.fo.shoppinglist.data.source.ProductsRepository
import com.fo.shoppinglist.data.source.ServiceLocator
import timber.log.Timber

class ShoppingListApplication : Application() {

    val productsRepository: ProductsRepository get() = ServiceLocator.provideProductsRepository(this)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}
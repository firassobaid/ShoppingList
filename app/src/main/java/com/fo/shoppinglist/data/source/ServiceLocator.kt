package com.fo.shoppinglist.data.source

import android.content.Context
import androidx.room.Room
import com.fo.shoppinglist.data.source.local.ProductsLocalDataSource
import com.fo.shoppinglist.data.source.local.ShoppingListDataBase

object ServiceLocator {
    private var database: ShoppingListDataBase? = null
    private var productsRepo: ProductsRepository? = null

    fun provideProductsRepository(context: Context): ProductsRepository {
        return productsRepo ?: productsRepo ?: createRepo(context)
    }

    private fun createRepo(context: Context): ProductsRepository {
        val repo = DefaultProductsRepository(createLocalDataSource(context))
        productsRepo = repo
        return repo
    }

    private fun createLocalDataSource(context: Context): ProductsDataSource {
        val db = database ?: createDataBase(context)
        return ProductsLocalDataSource(db.productDao())
    }

    private fun createDataBase(context: Context): ShoppingListDataBase {
        val db = Room.databaseBuilder(
            context.applicationContext,
            ShoppingListDataBase::class.java, "Products.db"
        ).build()
        database = db
        return db
    }

}
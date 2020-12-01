package com.fo.shoppinglist.data.source

import androidx.lifecycle.LiveData
import com.fo.shoppinglist.data.model.Product
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DefaultProductsRepository(private val productsDataSource: ProductsDataSource) :
    ProductsRepository {
    override fun observeProducts(): LiveData<Result<List<Product>>> {
        return productsDataSource.observeProducts()
    }

    override suspend fun addProduct(product: Product) {
        coroutineScope {
            launch { productsDataSource.addProduct(product) }
        }
    }

    override suspend fun getProducts(): Result<List<Product>> = productsDataSource.getProducts()

    override suspend fun getProductById(id: String): Result<Product> =
        productsDataSource.getProductById(id)

    override suspend fun deleteProductById(id: String) =
        productsDataSource.deleteProductById(id)
}
package com.fo.shoppinglist.data.source

import androidx.lifecycle.LiveData
import com.fo.shoppinglist.data.model.Product

interface ProductsRepository {
    fun observeProducts(): LiveData<Result<List<Product>>>
    suspend fun addProduct(product: Product)
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getProductById(id: String): Result<Product>
    suspend fun deleteProductById(id: String)
}
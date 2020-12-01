package com.fo.shoppinglist.data.source.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.fo.shoppinglist.data.model.Product
import com.fo.shoppinglist.data.source.ProductsDataSource
import com.fo.shoppinglist.data.source.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Suppress("UNCHECKED_CAST")
class ProductsLocalDataSource constructor(
    private val productDao: ProductDao,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : ProductsDataSource {
    override fun observeProducts(): LiveData<Result<List<Product>>> {
        return productDao.observeProducts().map {
            Result.Success(it)
        }
    }

    override suspend fun addProduct(product: Product) = productDao.insertProducts(product)
    override suspend fun getProducts(): Result<List<Product>> = withContext(ioDispatcher) {
        return@withContext try {
            Result.Success(productDao.getProducts())
        } catch (e: Exception) {
            Error(e)
        }
    } as Result<List<Product>>

    override suspend fun getProductById(id: String): Result<Product> = withContext(ioDispatcher) {
        try {
            return@withContext Result.Success(productDao.getProductById(id))
        } catch (e: Exception) {
            return@withContext Result.Error(e)
        }
    }

    override suspend fun deleteProductById(id: String) = withContext(ioDispatcher) {
        productDao.delete(id)
    }
}

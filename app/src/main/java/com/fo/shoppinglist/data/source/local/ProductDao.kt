package com.fo.shoppinglist.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fo.shoppinglist.data.model.Product

@Dao
interface ProductDao {
    @Query("SELECT * FROM product")
    suspend fun getProducts(): List<Product>

    @Query("SELECT * FROM product")
    fun observeProducts(): LiveData<List<Product>>

    @Query("DELETE FROM product WHERE id = :productId")
    suspend fun delete(productId: String)

    @Insert
    suspend fun insertProducts(vararg products: Product)

    @Query("SELECT * FROM product WHERE id =:id")
    suspend fun getProductById(id: String): Product
}
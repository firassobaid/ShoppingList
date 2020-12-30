package com.fo.shoppinglist.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.fo.shoppinglist.data.model.Product
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingListRoomTest {
    private lateinit var database: ShoppingListDataBase
    private lateinit var dao: ProductDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingListDataBase::class.java
        )
            .allowMainThreadQueries().build()
        dao = database.productDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertProductTest() = runBlockingTest {
        val product = Product("name", "id")
        dao.insertProducts(product)

        val item = dao.getProductById("id")
        assert(item.id == product.id)
    }

    @Test
    fun deleteProductTest() = runBlockingTest {
        val product = Product("name", "id")
        dao.insertProducts(product)
        dao.delete(product.id)
        val items = dao.getProducts()
        assert(items.isEmpty())
    }

    @Test
    fun getProductsTest() = runBlockingTest {
        val product = Product("name", "id")
        val product1 = Product("name", "id1")
        val product2 = Product("name", "id2")
        dao.insertProducts(product)
        dao.insertProducts(product1)
        dao.insertProducts(product2)

        val items = dao.getProducts()
        assert(items.size == 3)
    }
}
package com.fo.shoppinglist.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fo.shoppinglist.data.model.Product

@Database(entities = [Product::class], version = 1)
abstract class ShoppingListDataBase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
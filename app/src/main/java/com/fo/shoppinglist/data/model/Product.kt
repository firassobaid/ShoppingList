package com.fo.shoppinglist.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Product(
    @ColumnInfo(name = "first_name") val name: String,
    @PrimaryKey val id: String
)
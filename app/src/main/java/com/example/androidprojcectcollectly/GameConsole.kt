package com.example.androidprojcectcollectly

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameConsole(@PrimaryKey  val uid: Int, @ColumnInfo(name = "name") val name: String?) {


}
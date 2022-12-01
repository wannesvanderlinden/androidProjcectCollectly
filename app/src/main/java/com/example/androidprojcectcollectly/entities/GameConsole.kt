package com.example.androidprojcectcollectly.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GameConsole(@PrimaryKey(autoGenerate = true)  val uid: Int?, @ColumnInfo(name = "name") val name: String?) {


}
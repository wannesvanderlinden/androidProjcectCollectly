package com.example.androidprojcectcollectly.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Game(@PrimaryKey(autoGenerate = true)  val uid: Int?,@ColumnInfo(name = "title") val title: String,@ColumnInfo(name = "gameConsole") val gameConsole: String)  {
}
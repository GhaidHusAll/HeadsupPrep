package com.example.headsupprep.roomModel

import androidx.room.*


@Entity(tableName = "celebrities")
data class CelebritiesRoom(
    @PrimaryKey(autoGenerate = true)
    var pk: Int,
    var name: String,
    var taboo1: String,
    var taboo2: String,
    var taboo3: String

)

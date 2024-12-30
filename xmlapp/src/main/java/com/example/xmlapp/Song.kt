package com.example.xmlapp

data class Song(
    val title: String ="",
    val singer: String=""

)

data class Album(
    var title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null,
    var songList: ArrayList<Song>? = null
)

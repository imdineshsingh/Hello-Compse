package com.example.firstlayoutinjp.model

data class Music (
    val id: Int,
    val title: String,
    val year: Int,
    val description: String,
    val musicImageId: Int = 0
)


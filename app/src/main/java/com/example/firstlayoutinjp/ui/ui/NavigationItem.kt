package com.example.firstlayoutinjp.ui.ui

import com.example.firstlayoutinjp.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String){

    object Home : NavigationItem("home", R.drawable.home, "Home")
    object Music : NavigationItem("music", R.drawable.music, "Music")
    object Movies : NavigationItem("movies", R.drawable.videos, "Movies")
    object Books : NavigationItem("books", R.drawable.book, "Books")
    object Profile : NavigationItem("profile", R.drawable.profile, "Profile")

}

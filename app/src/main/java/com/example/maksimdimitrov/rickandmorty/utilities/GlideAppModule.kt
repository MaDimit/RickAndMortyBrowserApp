package com.example.maksimdimitrov.rickandmorty.utilities

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideAppModule : AppGlideModule()

fun ImageView.loadImage(url: String, context: Context) {
    Glide.with(context)
            .load(url)
            .into(this)
}
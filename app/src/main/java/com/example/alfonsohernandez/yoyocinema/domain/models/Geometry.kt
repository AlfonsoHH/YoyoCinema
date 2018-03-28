package com.example.alfonsohernandez.yoyocinema.domain.models

import com.google.gson.annotations.SerializedName

data class Geometry(

        @field:SerializedName("viewport")
	val viewport: Viewport? = null,

        @field:SerializedName("location")
	val location: Location? = null
)
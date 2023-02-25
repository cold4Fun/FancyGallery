package edu.vt.cs5254.gallery.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import edu.vt.cs5254.gallery.GalleryItem

@JsonClass(generateAdapter = true)
data class PhotoResponse(
    @Json(name = "photo") val galleryItems: List<GalleryItem>
)
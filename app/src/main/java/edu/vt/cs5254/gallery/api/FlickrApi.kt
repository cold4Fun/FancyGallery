package edu.vt.cs5254.gallery.api

import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "3148b08f1f95b04b8d751cf39343cbd0"
private var perPage = 100

interface FlickrApi {

    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=$API_KEY" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s,geo"
    )
    suspend fun fetchPhotos(@Query("per_page") perPage: Int): FlickrResponse
}
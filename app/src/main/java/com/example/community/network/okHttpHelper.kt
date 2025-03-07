package com.example.community.network

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

/**
 * A singleton helper class for making HTTP POST requests using OkHttp.
 */
object OkHttpHelper {

    private val client = OkHttpClient()

    /**
     * Sends a POST request with the given JSON payload to the specified URL.
     *
     * @param url The endpoint URL.
     * @param jsonPayload A JSONObject containing the data to post.
     * @param callback The OkHttp callback to handle response or failure.
     */
    fun post(url: String, jsonPayload: JSONObject, callback: Callback) {
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val body = jsonPayload.toString().toRequestBody(mediaType)
        val request = Request.Builder()
            .url(url)
            .post(body)
            .build()

        client.newCall(request).enqueue(callback)
    }
}

package com.example.shufuroomapp.core.api

import android.content.Context
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.UUID
import com.example.shufuroomapp.BuildConfig

object SupabaseClient {
    private const val SUPABASE_URL = BuildConfig.SUPABASE_URL
    private const val SUPABASE_KEY = BuildConfig.SUPABASE_ANON_KEY
    private const val BUCKET_NAME = "room-images" // Replace with your actual bucket name

    private val client = OkHttpClient()

    suspend fun uploadImage(context: Context, uri: Uri): String? = withContext(Dispatchers.IO) {
        try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() ?: return@withContext null
            val fileName = "${UUID.randomUUID()}.jpg"

            val requestBody = bytes.toRequestBody("image/jpeg".toMediaTypeOrNull(), 0, bytes.size)
            val request = Request.Builder()
                .url("$SUPABASE_URL/storage/v1/object/$BUCKET_NAME/$fileName")
                .addHeader("Authorization", "Bearer $SUPABASE_KEY")
                .addHeader("apikey", SUPABASE_KEY)
                .post(requestBody)
                .build()

            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                return@withContext "$SUPABASE_URL/storage/v1/object/public/$BUCKET_NAME/$fileName"
            } else {
                // THE NEW LOGGING: Forces the exact Supabase error into Logcat
                val errorBody = response.body?.string()
                Log.e("SupabaseError", "HTTP ${response.code}: $errorBody")
            }
        } catch (e: Exception) {
            // Also catches any network crashes
            Log.e("SupabaseError", "Crash during upload: ${e.message}", e)
        }
        return@withContext null
    }
}
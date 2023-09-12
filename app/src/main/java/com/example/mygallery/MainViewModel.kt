package com.example.mygallery

import android.app.Application
import android.content.ContentUris
import android.net.Uri
import android.provider.MediaStore
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val _photoUris = mutableStateOf(emptyList<Uri>())
    val photoUris: State<List<Uri>> = _photoUris

    fun getPhotos() {
        val uris = mutableListOf<Uri>()
        getApplication<Application>().contentResolver.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            null,
            null,
            "${MediaStore.Images.Media.DATE_TAKEN} DESC"
        )?.use { cursor ->
            val idColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns._ID)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumnIndex)
                val contentUri =
                    ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id)

                uris.add(contentUri)
            }
        }

        _photoUris.value = uris
    }


}
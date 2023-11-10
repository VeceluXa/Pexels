package com.danilovfa.pexels.utils.extension

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

const val SHARED_IMAGE_FILENAME = "shared_image.jpg"
const val FILE_PROVIDER_AUTHORITY = "com.danilovfa.pexels.FileProvider"

fun Bitmap.save(context: Context): Uri {
    val cacheDir = context.cacheDir

    val imageFile = File(cacheDir, SHARED_IMAGE_FILENAME)
    return try {
        val outputStream = FileOutputStream(imageFile)
        compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()

        FileProvider.getUriForFile(
            context,
            FILE_PROVIDER_AUTHORITY,
            imageFile
        )
    } catch (e: IOException) {
        Uri.EMPTY
    }
}
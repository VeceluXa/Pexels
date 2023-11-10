package com.danilovfa.pexels.utils.extension

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.Uri

internal fun Context.sharePhoto(uri: Uri) {
    val shareCardIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        clipData = ClipData.newRawUri(null, uri)
        flags += Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    val shareIntent = Intent.createChooser(shareCardIntent, "Share photo")
//    shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    startActivity(shareIntent)
}
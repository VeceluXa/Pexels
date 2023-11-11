package com.danilovfa.pexels.utils.extension

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.sharePhoto(uri: Uri) {
    val shareCardIntent = Intent().apply {
        action = Intent.ACTION_SEND
        type = "image/png"
        putExtra(Intent.EXTRA_STREAM, uri)
        clipData = ClipData.newRawUri(null, uri)
        flags += Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    val shareIntent = Intent.createChooser(shareCardIntent, "Share photo")
    startActivity(shareIntent)
}

fun Context.showError(error: Throwable) {
    Toast.makeText(this, error.message.toString(), Toast.LENGTH_SHORT).show()
}

fun Context.showMessage(@StringRes messageResId: Int) {
    Toast.makeText(this, getString(messageResId), Toast.LENGTH_SHORT).show()
}

fun Context.hasNetwork(): Boolean {
    var isConnected = false
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
        isConnected = when {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
    return isConnected
}

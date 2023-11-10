package com.danilovfa.pexels.data.remote.interceptor

import okhttp3.logging.HttpLoggingInterceptor

val loggingInterceptor = HttpLoggingInterceptor().run {
    setLevel(HttpLoggingInterceptor.Level.BODY)
}

package com.danilovfa.pexels.data.remote.interceptor

import android.util.Log
import com.danilovfa.pexels.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class PexelsInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .addHeader("Authorization", BuildConfig.PEXELS_API_KEY)
            .build()
        Log.d("BugFix", "intercept: $request")
        return chain.proceed(request)
    }
}
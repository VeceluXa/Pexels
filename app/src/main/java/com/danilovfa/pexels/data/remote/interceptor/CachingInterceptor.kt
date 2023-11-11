package com.danilovfa.pexels.data.remote.interceptor

import android.content.Context
import com.danilovfa.pexels.data.remote.PexelsApi
import com.danilovfa.pexels.utils.extension.hasNetwork
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CachingInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (doSkipCaching(request)) {
            return chain.proceed(request)
        }

        val cacheControlHeader = if (context.hasNetwork()) {
            /*
             *  If there is Internet, get the cache that was stored 1 hour ago.
             *  If the cache is older than 1 hour, then discard it,
             *  and indicate an error in fetching the response.
             *  The 'max-age' attribute is responsible for this behavior.
             */
            "public, max-age=$MAX_AGE"
        } else {
            /*
             *  If there is no Internet, get the cache that was stored 1 hour ago.
             *  If the cache is older than 1 hour, then discard it
             *  and indicate an error in fetching the response.
             *  The 'max-stale' attribute is responsible for this behavior.
             *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
             */
            "public, only-if-cached, max-stale=$MAX_STALE"
        }

        request = request
            .newBuilder()
            .addHeader("Cache-Control", cacheControlHeader)
            .build()

        return chain.proceed(request)
    }

    /**
     * Skip caching search response. Only cache curated list and collections.
     *
     * @see [PexelsApi]
     */
    private fun doSkipCaching(request: Request): Boolean {
        return request.url.encodedPath == SKIP_PATH
    }

    companion object {
        const val MAX_AGE = 60 * 60
        const val MAX_STALE = 60 * 60

        const val SKIP_PATH = "/v1/search"
    }
}

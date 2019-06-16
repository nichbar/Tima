package work.nich.tima.common.network

import okhttp3.Interceptor
import okhttp3.Response
import work.nich.tima.BuildConfig

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "OAuth ${BuildConfig.FEEDLY_DEV_TOKEN}")
            .build()

        return chain.proceed(request)
    }
}
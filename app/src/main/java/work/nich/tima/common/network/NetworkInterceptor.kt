package work.nich.tima.common.network

import okhttp3.Interceptor
import okhttp3.Response
import work.nich.tima.common.ACCESS_KEY

class NetworkInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "OAuth $ACCESS_KEY")
            .build()

        return chain.proceed(request)
    }
}
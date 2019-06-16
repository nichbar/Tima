package work.nich.tima.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import work.nich.tima.common.FEEDLY_BASE_URL
import work.nich.tima.common.network.ApiService
import work.nich.tima.common.network.NetworkInterceptor
import work.nich.tima.view.collection.CollectionViewModel
import java.util.concurrent.TimeUnit

val appModule = module {

    single<OkHttpClient> {
        val builder = OkHttpClient.Builder()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        builder.addInterceptor(NetworkInterceptor())
        builder.addNetworkInterceptor(loggingInterceptor)
        builder.addNetworkInterceptor(StethoInterceptor())
        builder.connectTimeout(5, TimeUnit.SECONDS)
        builder.build()
    }

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(FEEDLY_BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    single<ApiService> {
        get<Retrofit>().create(ApiService::class.java)
    }

    viewModel {
        CollectionViewModel(get())
    }

}

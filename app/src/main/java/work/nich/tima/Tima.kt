package work.nich.tima

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import work.nich.tima.di.appModule

class Tima : Application() {

    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()

        app = this

        startKoin {
            androidContext(this@Tima)
            modules(appModule)
        }

        Stetho.initializeWithDefaults(this)
    }

}
package work.nich.tima

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.android.startKoin
import work.nich.tima.di.appModule

class Tima : Application() {

    companion object {
        lateinit var app: Application
    }

    override fun onCreate() {
        super.onCreate()

        app = this

        startKoin(this, listOf(appModule))

        Stetho.initializeWithDefaults(this)
    }

}
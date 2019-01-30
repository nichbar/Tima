package work.nich.tima.common.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import work.nich.tima.common.util.DisplayUtils

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(provideContentView())

        ButterKnife.bind(this)

        DisplayUtils.setFullScreen(this)
    }

    protected fun inflate(id: Int): View {
        return layoutInflater.inflate(id, null)
    }

    protected abstract fun provideContentView(): View

}
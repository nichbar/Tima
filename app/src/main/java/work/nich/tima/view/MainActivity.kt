package work.nich.tima.view

import android.os.Bundle
import android.view.View
import work.nich.tima.R
import work.nich.tima.common.util.IntentHelper
import work.nich.tima.common.view.BaseActivity

class MainActivity : BaseActivity() {

    override fun provideContentView(): View {
        return inflate(R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        IntentHelper.startCollection(this)
        finish()
    }

}
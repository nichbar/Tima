package work.nich.tima.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import work.nich.tima.R
import work.nich.tima.common.util.IntentHelper
import work.nich.tima.common.util.IntentType
import work.nich.tima.common.view.BaseActivity
import work.nich.tima.view.collection.CollectionFragment

class ShellActivity : BaseActivity() {

    override fun provideContentView(): View {
        return inflate(R.layout.activity_shell)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        handleIntent(intent.extras)
    }

    private fun handleIntent(bundle: Bundle?) {
        bundle?.let {
            val intentType = bundle.get(IntentHelper.INTENT_TYPE)

            intentType?.let {
                when (it) {
                    IntentType.COLLECTION -> {
                        val collectionFragment = CollectionFragment().with(bundle)
                        startFragment(collectionFragment)
                    }
                    IntentType.FEED -> {

                    }
                }
            }
        }
    }

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.placeholderView, fragment).commitAllowingStateLoss()
    }

}
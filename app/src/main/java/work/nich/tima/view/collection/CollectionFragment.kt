package work.nich.tima.view.collection

import android.view.View
import work.nich.tima.R
import work.nich.tima.common.view.BaseFragment

class CollectionFragment : BaseFragment() {

    override fun provideContentView(): View {
        return inflate(R.layout.fragment_collection)
    }

}
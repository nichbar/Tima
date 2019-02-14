package work.nich.tima.view.collection

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import work.nich.tima.R
import work.nich.tima.common.view.BaseFragment

class CollectionFragment : BaseFragment() {

    private val mViewModel: CollectionViewModel by viewModel()

    override fun provideContentView(): View {
        return inflate(R.layout.fragment_collection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewModel.getCollections()
        mViewModel.collectionLiveData.observe(this, Observer {
            Toast.makeText(requireContext(), it.size.toString(), Toast.LENGTH_SHORT).show()
        })
    }

}
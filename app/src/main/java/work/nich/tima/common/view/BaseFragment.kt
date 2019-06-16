package work.nich.tima.common.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import butterknife.ButterKnife

abstract class BaseFragment : Fragment() {

    private lateinit var mLayoutView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mLayoutView = provideLayoutView()
        ButterKnife.bind(this, mLayoutView)
        return mLayoutView
    }

    protected fun inflate(id: Int): View {
        return layoutInflater.inflate(id, null)
    }

    protected abstract fun provideLayoutView(): View

    fun with(bundle: Bundle): BaseFragment {
        arguments = bundle
        return this
    }
}
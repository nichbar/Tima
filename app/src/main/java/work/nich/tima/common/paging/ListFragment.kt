package work.nich.tima.common.paging

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import work.nich.tima.R
import work.nich.tima.common.view.BaseFragment

/**
 * LD -> ListData means data entity from internet
 * ILD -> ItemListData means decorated data entity for displaying.
 */
abstract class ListFragment<LD, ILD> : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {

    companion object {
        const val SAVED_RECYCLER_VIEW_STATE = "saved_recycler_view_state"
    }

    @BindView(R.id.recyclerview)
    protected lateinit var mRecyclerView: RecyclerView
    @BindView(R.id.swipe_refresh)
    protected lateinit var mSwipeRefreshLayout: SwipeRefreshLayout
    @BindView(R.id.pg_list_loading)
    protected lateinit var mLoadingView: ProgressBar
    @BindView(R.id.container_error)
    protected lateinit var mErrorContainer: View
    @BindView(R.id.error_tv)
    protected lateinit var mErrorTv: TextView

    protected lateinit var mListAdapter: ListAdapter<ILD>

    protected lateinit var mListViewModel: ListViewModel<LD, ILD>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mListViewModel = provideViewModel()
        mListAdapter = provideAdapter()

        mListAdapter.retryCallback = object : ListAdapter.RetryCallback {
            override fun retry() {
                mListViewModel.loadMore()
            }
        }

        mListViewModel.refreshStatus.observe(this, Observer {
            when (it?.status) {
                LoadingStatus.Status.ERROR -> {
                    hideRefreshLayout()
                    showRefreshError(it.message)
                    when (it.errorType) {
                        LoadingStatus.ErrorType.UNKNOWN -> {
                            if (mListAdapter.itemCount == 0) showNoContentError()
                        }
                        LoadingStatus.ErrorType.NO_INTERNET_CONNECTION -> {
                            if (mListAdapter.itemCount == 0) showNoInternetError()
                        }
                    }
                }
                LoadingStatus.Status.REACH_THE_END, LoadingStatus.Status.SUCCESS -> {
                    hideRefreshLayout()
                    hideLoadingProgressBar()
                    hideError()
                }
                LoadingStatus.Status.LOADING -> {
                    hideError()
                    mListAdapter.updateFooterStatus(ListAdapter.FooterStatus.LOADING)
                }
            }
        })

        mListViewModel.loadMoreStatus.observe(this, Observer {
            when (it?.status) {
                LoadingStatus.Status.ERROR -> {
                    hideRefreshLayout()
                    hideLoadingProgressBar()
                    when (it.errorType) {
                        LoadingStatus.ErrorType.UNKNOWN -> {
                            showLoadMoreError(it.message)
                            if (mListAdapter.itemCount == 0) showNoContentError()
                        }
                        LoadingStatus.ErrorType.NO_INTERNET_CONNECTION -> {
                            showLoadMoreError(it.message)
                            if (mListAdapter.itemCount == 0) showNoInternetError()
                        }
                    }
                }
                LoadingStatus.Status.SUCCESS -> {
                    hideError()
                    hideLoadingProgressBar()
                    if (mListAdapter.itemCount != 0) hideError()
                }
                LoadingStatus.Status.LOADING -> {
                    hideError()
                    mListAdapter.updateFooterStatus(ListAdapter.FooterStatus.LOADING)
                    if (mListAdapter.itemCount == 0) showLoadingProgressBar()
                }
                LoadingStatus.Status.REACH_THE_END -> {
                    hideError()
                    hideLoadingProgressBar()
                    mRecyclerView.postDelayed({
                        mListAdapter.updateFooterStatus(ListAdapter.FooterStatus.REACH_THE_END)
                    }, 200L)
                }
            }
        })

        mListViewModel.itemListLiveData.observe(this, Observer { it ->
            it?.let {
                if (it.isEmpty() && mListAdapter.itemCount == 0) {
                    showNoContentError()
                } else {
                    hideError()
                }

                // Auto load more, if item size is smaller than 8.
                // TODO 8 is not an accurate number, it depends on the height of every item.
                mRecyclerView.postDelayed({
                    if (it.size + mListAdapter.itemCount < 8 && mListAdapter.footerStatus != ListAdapter.FooterStatus.REACH_THE_END) {
                        mListViewModel.loadMore()
                    }
                }, 300)

                mListAdapter.submitList(it)
            }
        })

        mListViewModel.initialLoad()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mSwipeRefreshLayout.setOnRefreshListener(this)

        mRecyclerView.adapter = mListAdapter
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                val llManager = recyclerView.layoutManager as LinearLayoutManager

                if (mListAdapter.footerStatus == ListAdapter.FooterStatus.REACH_THE_END || mListAdapter.itemCount == 0) return

                if (llManager.findLastVisibleItemPosition() > mListAdapter.itemCount - 3 && RecyclerView.SCROLL_STATE_IDLE == newState) {
                    mListViewModel.loadMore()
                }
            }
        })
        savedInstanceState?.let {
            mRecyclerView.postDelayed({
                mRecyclerView.layoutManager?.onRestoreInstanceState(it.getParcelable(SAVED_RECYCLER_VIEW_STATE))
            }, 50)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelable(SAVED_RECYCLER_VIEW_STATE, mRecyclerView.layoutManager?.onSaveInstanceState())
        super.onSaveInstanceState(outState)
    }

    open fun showLoadMoreError(message: String) {

    }

    open fun showRefreshError(message: String) {

    }

    private fun showNoContentError() {
        mErrorContainer.visibility = View.VISIBLE
        mErrorTv.text = getString(R.string.error_no_content)
    }

    private fun showNoInternetError() {
        mErrorContainer.visibility = View.VISIBLE
        mErrorTv.text = getString(R.string.error_network_unavailable)
    }

    private fun hideError() {
        mErrorContainer.visibility = View.GONE
    }

    private fun hideRefreshLayout() {
        mSwipeRefreshLayout.isRefreshing = false
    }

    override fun provideLayoutView(): View {
        return inflate(R.layout.fragment_list)
    }

    override fun onRefresh() {
        mListViewModel.refresh()
    }

    private fun showLoadingProgressBar() {
        mLoadingView.visibility = View.VISIBLE
        mLoadingView.isEnabled = true
    }

    private fun hideLoadingProgressBar() {
        mLoadingView.visibility = View.GONE
        mLoadingView.isEnabled = false
    }

    abstract fun provideViewModel(): ListViewModel<LD, ILD>

    abstract fun provideAdapter(): ListAdapter<ILD>
}
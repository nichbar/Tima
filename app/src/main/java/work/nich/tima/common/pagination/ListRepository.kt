package work.nich.tima.common.pagination

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import work.nich.tima.common.network.Response
import work.nich.tima.common.util.NetworkUtils

class ListRepository<T>(
    private val mCallMethod: CallMethod<List<T>>,
    private var mApplication: Application,
    private val mOfflineAvailable: Boolean) {

    private var mContinuation = ""

    private val mList = ArrayList<T>()

    val listLiveData = MutableLiveData<List<T>>()
    val refreshStatus = MutableLiveData<LoadingStatus>()
    val loadMoreStatus = MutableLiveData<LoadingStatus>()

    var reachTheEnd = false

    fun load(type: LoadType) {

        if (!mOfflineAvailable && !NetworkUtils.isNetworkConnected(mApplication)) {
            when (type) {
                LoadType.INITIAL, LoadType.LOAD_MORE -> loadMoreStatus.postValue(LoadingStatus.errorNoInternetConnection())
                LoadType.REFRESH -> refreshStatus.postValue(LoadingStatus.errorNoInternetConnection())
            }
            return
        }

        when (type) {
            LoadType.INITIAL -> {

                if (loadMoreStatus.value?.status == LoadingStatus.Status.LOADING) return
                loadMoreStatus.postValue(LoadingStatus(status = LoadingStatus.Status.LOADING))

                // mList is not empty means that the view had just recreated itself.
                if (mList.size != 0) {
                    loadMoreStatus.postValue(LoadingStatus(status = LoadingStatus.Status.SUCCESS))
                    if (reachTheEnd) loadMoreStatus.postValue(LoadingStatus(status = LoadingStatus.Status.REACH_THE_END))

                    listLiveData.postValue(mList)
                } else {
                    loadMore()
                }
            }

            LoadType.LOAD_MORE -> {
                loadMore()
            }

            LoadType.REFRESH -> {
                refresh()
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun loadMore() {
        if (loadMoreStatus.value?.status == LoadingStatus.Status.LOADING || refreshStatus.value?.status == LoadingStatus.Status.LOADING) return

        loadMoreStatus.postValue(LoadingStatus(status = LoadingStatus.Status.LOADING))

        mCallMethod.load(mContinuation).subscribeOn(Schedulers.io())
            .subscribe(object : Response<List<T>>() {
                override fun onSuccess(data: List<T>) {
                    loadMoreStatus.postValue(LoadingStatus(status = LoadingStatus.Status.SUCCESS))

                    if (data.isEmpty() || mCallMethod.reachTheEnd(data.size) || reachTheEnd) {
                        reachTheEnd = true
                        loadMoreStatus.postValue(LoadingStatus(status = LoadingStatus.Status.REACH_THE_END))
                    } else {
                        mContinuation == ""
                    }

                    mList.addAll(data)
                    listLiveData.postValue(mList)
                }

                override fun onFailure(error: NetworkError) {
                    loadMoreStatus.postValue(
                        LoadingStatus(
                            status = LoadingStatus.Status.ERROR,
                            message = error.message
                        )
                    )
                }
            })
    }

    @SuppressLint("CheckResult")
    private fun refresh() {
        if (refreshStatus.value?.status == LoadingStatus.Status.LOADING) return

        mContinuation = ""
        mList.clear()
        reachTheEnd = false

        refreshStatus.postValue(LoadingStatus(status = LoadingStatus.Status.LOADING))

        mCallMethod.load(mContinuation).subscribeOn(Schedulers.io())
            .subscribe(object : Response<List<T>>() {
                override fun onSuccess(data: List<T>) {
                    mList.addAll(data)
                    listLiveData.postValue(mList)
                    refreshStatus.postValue(LoadingStatus(status = LoadingStatus.Status.SUCCESS))
                }

                override fun onFailure(error: NetworkError) {
                    refreshStatus.postValue(LoadingStatus(status = LoadingStatus.Status.ERROR, message = error.message))
                }
            })
    }

    interface CallMethod<V> {
        fun load(continuation: String): Single<V>

        // Check if the list meets the end by comparing itemCount with preset pageSize.
        fun reachTheEnd(itemCount: Int): Boolean

        // Update continuation for the next load
        fun updateContinuation(continuation: String)
    }
}
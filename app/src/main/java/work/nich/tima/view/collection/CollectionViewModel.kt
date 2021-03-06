package work.nich.tima.view.collection

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import work.nich.tima.common.data.Collection
import work.nich.tima.common.network.ApiService
import work.nich.tima.common.network.Response

class CollectionViewModel(private val mApiService: ApiService) : ViewModel() {

    val collections = MutableLiveData<List<Collection>>()

    @SuppressLint("CheckResult")
    fun getCollections() {
        mApiService.getPersonalCollections()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Response<List<Collection>>() {
                override fun onSuccess(data: List<Collection>) {
                    collections.postValue(data)
                }

                override fun onFailure(error: NetworkError) {
                    Timber.e(error.message)
                }
            })
    }

}
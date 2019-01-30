package work.nich.tima.view

import android.os.Bundle
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import timber.log.Timber
import work.nich.tima.R
import work.nich.tima.common.data.CollectionItem
import work.nich.tima.common.network.ApiService
import work.nich.tima.common.network.Response
import work.nich.tima.common.view.BaseActivity

class MainActivity : BaseActivity() {

    val apiService: ApiService by inject()

    override fun provideContentView(): View {
        return inflate(R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        apiService.getPersonalCollections()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object: Response<List<CollectionItem>>() {
                override fun onSuccess(data: List<CollectionItem>) {
                    responseTv.text = data[0].description
                }

                override fun onFailure(error: NetworkError) {
                    Timber.e(error.message)
                }
            })
    }

}
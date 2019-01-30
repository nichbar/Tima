package work.nich.tima.common.network

import io.reactivex.Single
import retrofit2.http.GET
import work.nich.tima.common.data.CollectionItem

interface ApiService {
    @GET("v3/collections")
    fun getPersonalCollections(): Single<List<CollectionItem>>
}
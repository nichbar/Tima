package work.nich.tima.common.network

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.PUT
import work.nich.tima.common.data.Collection
import work.nich.tima.common.data.Feed
import work.nich.tima.common.data.Profile

interface ApiService {

    @GET("v3/collections")
    fun getPersonalCollections(): Single<List<Collection>>

    @GET("v3/profile")
    fun getProfile(): Single<Profile>

    @PUT("v3/collections/:categoryId/feeds")
    fun subscribeNewFeed(feed: Feed): Single<ResponseBody>

}
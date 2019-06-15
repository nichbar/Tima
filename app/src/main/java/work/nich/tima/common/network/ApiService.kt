package work.nich.tima.common.network

import io.reactivex.Single
import retrofit2.http.GET
import work.nich.tima.common.data.Collection
import work.nich.tima.common.data.Profile

interface ApiService {

    @GET("v3/collections")
    fun getPersonalCollections(): Single<List<Collection>>

    @GET("v3/profile")
    fun getProfile(): Single<Profile>

}
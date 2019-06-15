package work.nich.tima.common.data

import androidx.annotation.Keep

@Keep
data class Profile(
    var email: String? = "",
    var facebook: String? = "",
    var familyName: String? = "",
    var gender: String? = "",
    var givenName: String? = "",
    var google: String? = "",
    var id: String? = "",
    var locale: String? = "",
    var picture: String? = "",
    var reader: String? = "",
    var twitter: String? = "",
    var wave: String? = ""
)
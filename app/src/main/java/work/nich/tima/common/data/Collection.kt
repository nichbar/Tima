package work.nich.tima.common.data

data class Collection(
    val cover: String? = null,
    val created: Long? = null,
    val description: String? = null,
    val feeds: List<Feed?>? = null,
    val id: String? = null,
    val label: String? = null
)

package work.nich.tima.common.data

data class CollectionItem(
    val cover: String? = null,
    val created: Long? = null,
    val description: String? = null,
    val feeds: List<FeedItem?>? = null,
    val id: String? = null,
    val label: String? = null
)

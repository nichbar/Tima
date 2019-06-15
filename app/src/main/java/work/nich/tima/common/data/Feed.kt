package work.nich.tima.common.data

data class Feed(
	val website: String? = null,
	val numReadEntriesPastMonth: Int? = null,
	val added: Long? = null,
	val totalReadingTimePastMonth: Int? = null,
	val numTaggedEntriesPastMonth: Int? = null,
	val numLongReadEntriesPastMonth: Int? = null,
	val id: String? = null,
	val velocity: Double? = null,
	val title: String? = null,
	val visualUrl: String? = null,
	val updated: Long? = null
)

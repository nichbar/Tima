package work.nich.tima.common.util

import android.content.Context
import android.content.Intent
import work.nich.tima.view.ShellActivity

object IntentHelper {
    const val INTENT_TYPE = "intent_type"

    fun startCollection(context: Context) {
        val intent = Intent(context, ShellActivity::class.java)
        intent.putExtra(INTENT_TYPE, IntentType.COLLECTION)
        context.startActivity(intent)
    }
}

enum class IntentType {
    COLLECTION,

    FEED;
}
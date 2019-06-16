package work.nich.tima.common

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Convenience for callbacks/listeners whose return value indicates an event was consumed.
 */
inline fun consume(f: () -> Unit): Boolean {
    f()
    return true
}

fun <T> LiveData<T?>.observeNonNull(owner: LifecycleOwner, callback: (T) -> Unit) {
    observe(owner, Observer { value ->
        if (value != null) {
            callback(value)
        }
    })
}

inline fun tryWithDefaultCatch(action: (() -> Unit)) {
    try {
        action.invoke()
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

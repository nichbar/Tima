package work.nich.tima.common.network

import com.google.gson.JsonSyntaxException
import io.reactivex.functions.BiConsumer
import retrofit2.HttpException

abstract class Response<T> : BiConsumer<T, Throwable> {

    override fun accept(t: T, throwable: Throwable?) {
        if (t != null) onSuccess(t)

        if (throwable != null) {
            throwable.printStackTrace()
            when (throwable) {
                is HttpException -> {
                    val response = throwable.response()
                    onFailure(NetworkError(message = "${response.code()} error."))
                }

                is JsonSyntaxException -> {
                    onFailure(NetworkError(message = "Json syntax error -> ${throwable.message}"))
                }

                else -> {
                    val message = throwable.message
                    if (message == null) {
                        onFailure(NetworkError(message = "Unknown error."))
                    } else {
                        onFailure(NetworkError(message = message))
                    }
                }
            }
        }
    }

    abstract fun onSuccess(data: T)

    abstract fun onFailure(error: NetworkError)

    data class NetworkError(var code: Int = 0, var message: String = "")
}
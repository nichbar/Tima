package work.nich.tima.common.pagination

data class LoadingStatus(var status: Status, var message: String = "", var errorType: ErrorType = ErrorType.UNKNOWN) {

    companion object {
        fun errorNoInternetConnection(): LoadingStatus {
            return LoadingStatus(
                Status.ERROR,
                "网络异常，请检查您的网络连接",
                ErrorType.NO_INTERNET_CONNECTION
            )
        }
    }

    enum class Status {
        ERROR,

        SUCCESS,

        LOADING,

        REACH_THE_END
    }

    enum class ErrorType {
        UNKNOWN,

        NO_INTERNET_CONNECTION
    }
}
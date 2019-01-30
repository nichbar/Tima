package work.nich.tima.common.rxbus

class RxEvent<T>(var type: Type, var data: T) {

    enum class Type {
        ACTION_PACKAGE_ADDED,
        ACTION_PACKAGE_REMOVED,
        ACTION_PACKAGE_REPLACED,

        ACTION_DOWNLOAD_LIST_CHANGED,
    }
}

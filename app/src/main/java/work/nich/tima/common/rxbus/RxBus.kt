package work.nich.tima.common.rxbus

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

object RxBus {
    private val mBus: Subject<Any> = PublishSubject.create<Any>().toSerialized()

    fun post(a: Any) {
        mBus.onNext(a)
    }

    fun post(type: RxEvent.Type, a: Any) {
        mBus.onNext(RxEvent(type, a))
    }

    fun <T> toObservable(type: Class<T>): Observable<T> {
        return mBus.ofType(type)
    }

    fun <T> toObservable(type: RxEvent.Type, clazz: Class<T>): Observable<T> {
        return mBus.ofType(RxEvent::class.java).filter { event -> event.type == type }.cast(clazz).observeOn(AndroidSchedulers.mainThread())
    }

}
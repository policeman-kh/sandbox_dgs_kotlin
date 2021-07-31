package sandbox.dgs.processor

import org.reactivestreams.Publisher

interface IProcessor<T> {
    fun publish(): Publisher<T>
    fun emit(t: T)
}
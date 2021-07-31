package sandbox.dgs.processor

import org.reactivestreams.Publisher
import org.springframework.stereotype.Component
import reactor.core.publisher.Sinks
import reactor.core.scheduler.Schedulers
import sandbox.dgs.gen.types.Sticker

@Component
class StickerInMemoryProcessor : IStickerProcessor {
    override fun publish(): Publisher<Sticker> {
        return sinksOne.asFlux().publishOn(Schedulers.single())
    }

    override fun emit(t: Sticker) {
        sinksOne.tryEmitNext(t)
    }

    companion object {
        val sinksOne: Sinks.Many<Sticker> = Sinks.many().multicast().onBackpressureBuffer();
    }
}
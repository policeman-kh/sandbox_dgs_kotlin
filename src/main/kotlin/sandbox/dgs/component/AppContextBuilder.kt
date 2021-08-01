package sandbox.dgs.component

import com.netflix.graphql.dgs.reactive.DgsReactiveCustomContextBuilderWithRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Mono
import java.util.*

@Component
class AppContextBuilder : DgsReactiveCustomContextBuilderWithRequest<AppContext> {
    override fun build(
        extensions: Map<String, Any>?,
        headers: HttpHeaders?,
        serverRequest: ServerRequest?
    ): Mono<AppContext> {
        return Mono.just(AppContext(UUID.randomUUID().toString()))
    }
}

data class AppContext(val id: String)
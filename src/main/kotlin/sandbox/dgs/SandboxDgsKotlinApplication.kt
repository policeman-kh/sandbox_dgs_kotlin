package sandbox.dgs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SandboxDgsKotlinApplication

fun main(args: Array<String>) {
	runApplication<SandboxDgsKotlinApplication>(*args)
}

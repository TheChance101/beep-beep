import di.appModule
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin(){
    startKoin {
        modules(appModule())
    }
}


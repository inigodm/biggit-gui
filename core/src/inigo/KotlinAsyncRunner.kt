package inigo

import kotlinx.coroutines.*
import java.util.function.Consumer
import java.util.function.Supplier

class AsyncRunner() {
    val DELAY = 10000L
    fun <T> runAsync(supplier: Supplier<T>, consumer: Consumer<T>) {
        (KotlinAsyncRunner()).runAsync(supplier, consumer, DELAY)
    }
}

class KotlinAsyncRunner() : CoroutineScope {

    private val job = Job() // or SupervisorJob()
    override val coroutineContext = job + Dispatchers.Default

    fun <T> runAsync(supplier: Supplier<T>, consumer: Consumer<T>, delayms: Long = 10000) {
        var job = launch {
            consumer.accept(supplier.get())
        }
        launch {
            cancelCommandOnTimeout(job, delayms)
        }
    }

    private suspend fun cancelCommandOnTimeout(job: Job, delayMiliseconds: Long) {
        delay(delayMiliseconds)
        if (!job.isCompleted) {
            job.cancel()
            job.join()
        }
    }

}

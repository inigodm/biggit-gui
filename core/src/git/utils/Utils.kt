package inigo.gitgui.git.utils

import kotlinx.coroutines.*
import java.io.File
import java.io.Reader
import java.lang.Runnable
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit
import java.util.function.Consumer
import java.util.stream.Stream


class StreamGobbler(
    val stream: Stream<Reader>,
    val consumer: Consumer<String>
) :
    Runnable {
    override fun run() {
        stream.forEach{ it.forEachLine { consumer.accept(it) }}
    }

}

    fun String.runCommand(workingDir: File = File(System.getProperty("user.home")+"/codel/gitgui/borrame"),
                          consumer: Consumer<String> = Consumer { println(it) }) {
        println("Running $this")
        AsyncRunner().fireAndForget(consumer, workingDir, this)
    }

    class AsyncRunner : CoroutineScope {
        private val job = Job() // or SupervisorJob()
        override val coroutineContext = job + Dispatchers.Default

        fun fireAndForget( consumer: Consumer<String>,
                           workingDir: File,
                           command: String) {
            var job = launch {
                runCommand(consumer, workingDir, command)
            }
            launch{
                delay(5000)
                if (!job.isCompleted) {
                    job.cancel()
                    job.join()
                    consumer.accept("finished")
                }
            }
        }

        suspend fun runCommand(consumer: Consumer<String>,
                               workingDir: File,
                               command: String){
            val builder = ProcessBuilder()
            builder.command("sh", "-c", command)
            builder.directory(workingDir)
            val process = builder.start()
            Stream.of(process.inputStream.bufferedReader(), process.errorStream.bufferedReader())
                    .forEach { it.forEachLine { i -> consumer.accept(i) } }

        }
    }
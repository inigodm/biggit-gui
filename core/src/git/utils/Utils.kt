package inigo.gitgui.git.utils

import java.io.File
import java.io.Reader
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
    val builder = ProcessBuilder()
    builder.command("sh", "-c", this)
    builder.directory(workingDir)
    val process = builder.start()
    val streamGobbler = StreamGobbler(
        Stream.of(process.inputStream.bufferedReader(), process.errorStream.bufferedReader()),
        consumer = consumer
    )
    val executorService: ExecutorService = ThreadPoolExecutor(
        1, 1, 0L, TimeUnit.MILLISECONDS,
        LinkedBlockingQueue()
    )
    executorService.submit(streamGobbler)
    val exitCode2 = process.waitFor()
    executorService.shutdownNow()
    assert(exitCode2 == 0)
}
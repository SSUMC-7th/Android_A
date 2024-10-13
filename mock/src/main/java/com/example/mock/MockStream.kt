package com.example.mock

import io.ktor.application.ApplicationCall
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.response.header
import io.ktor.response.respondBytesWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.RandomAccessFile
import kotlin.math.min

object MockStream {
    suspend fun streamFile(file: File, call: ApplicationCall, start: Long, end: Long?) {
        val realEnd = min(end ?: file.length(), file.length() - 1)
        val contentLength = realEnd - start + 1
        val randomAccessFile = withContext(Dispatchers.IO) {
            RandomAccessFile(file, "r").also { it.seek(start) }
        }

        call.response.header(HttpHeaders.ContentRange, "bytes $start-$realEnd/${file.length()}")
        call.respondBytesWriter(status = HttpStatusCode.PartialContent) {
            val buffer = ByteArray(4096)
            var bytesLeft = contentLength

            while (bytesLeft > 0) {
                val bytesRead = randomAccessFile.read(buffer)
                if (bytesRead == -1) break
                writeFully(buffer, 0, bytesRead.coerceAtMost(bytesLeft.toInt()))
                bytesLeft -= bytesRead
            }
        }

        withContext(Dispatchers.IO) {
            randomAccessFile.close()
        }
    }
}


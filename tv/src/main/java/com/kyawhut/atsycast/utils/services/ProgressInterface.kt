package com.kyawhut.atsycast.utils.services

interface ProgressInterface {
    fun update(
        bytesRead: Long,
        contentLength: Long,
        done: Boolean
    )
}

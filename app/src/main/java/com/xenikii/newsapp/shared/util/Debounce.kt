package com.xenikii.newsapp.shared.util

import java.util.Timer
import java.util.TimerTask
import kotlin.time.Duration

class Debounce {
    private var timer: Timer? = null

    fun start(duration: Duration, action: () -> Unit) {
        timer?.cancel()
        timer = Timer()
        timer?.schedule(object : TimerTask() {
            override fun run() {
                action()
            }
        }, duration.inWholeMilliseconds)
    }
}
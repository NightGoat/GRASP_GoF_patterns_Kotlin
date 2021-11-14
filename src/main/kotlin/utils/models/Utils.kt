package utils.models

import sun.awt.Mutex

suspend fun Mutex.withLock(doFun: suspend () -> Unit) {
    this.lock()
    doFun()
    this.unlock()
}
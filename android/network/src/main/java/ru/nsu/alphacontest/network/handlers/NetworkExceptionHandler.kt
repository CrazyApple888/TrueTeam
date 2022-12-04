package ru.nsu.alphacontest.network.handlers

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

inline fun coroutineNetworkExceptionHandler(
    crossinline handler: (exception: Throwable) -> Unit,
): CoroutineExceptionHandler =
    object : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
        override fun handleException(context: CoroutineContext, exception: Throwable)  {
            handler.invoke(exception)
        }
    }
package org.thechance.service_taxi.data.utils

import org.litote.kmongo.coroutine.CoroutineFindPublisher

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) =
    skip((page - 1) * limit).limit(limit)
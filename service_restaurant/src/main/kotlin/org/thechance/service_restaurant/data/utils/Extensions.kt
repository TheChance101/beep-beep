package org.thechance.service_restaurant.data.utils

import org.litote.kmongo.coroutine.CoroutineFindPublisher

fun <T : Any> CoroutineFindPublisher<T>.paginate(page: Int, limit: Int) = this.skip((page - 1) * limit).limit(limit)



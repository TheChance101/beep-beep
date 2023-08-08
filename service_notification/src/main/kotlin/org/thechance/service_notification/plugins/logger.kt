package org.thechance.service_notification.plugins

import org.koin.core.KoinApplication
import org.koin.core.logger.Level
import org.koin.logger.SLF4JLogger

fun KoinApplication.slf4jLogger(level: Level = Level.INFO) {
    logger(SLF4JLogger(level))
}
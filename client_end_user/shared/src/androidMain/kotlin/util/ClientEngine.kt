package util

import io.ktor.client.engine.cio.CIO


actual fun getClientEngine() = CIO.create()


package org.thechance.api_gateway.data.model

open class ApiGatewayException(override val message: String) : Throwable(message)

class MultiErrorException(private val errors: List<Int>) : ApiGatewayException(errors.joinToString(","))

class MultiLocalizedMessageException(private val errors: List<String>) : ApiGatewayException(errors.joinToString(","))
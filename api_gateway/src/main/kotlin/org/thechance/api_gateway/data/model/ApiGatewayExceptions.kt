package org.thechance.api_gateway.data.model

open class ApiGatewayException(override val message: String) : Throwable(message)

class MultiErrorException(private val errors: List<Int>) : ApiGatewayException(errors.joinToString(","))

class MultiLocalizedMessageException(val errors: List<Map<Int, String>>) : ApiGatewayException(errors.joinToString(","))
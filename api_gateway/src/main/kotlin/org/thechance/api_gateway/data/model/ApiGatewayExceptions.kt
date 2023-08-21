package org.thechance.api_gateway.data.model

open class ApiGatewayException(override val message: String) : Throwable(message)

class MultiLocalizedMessageException(val errors: List<Map<Int, String>>) : ApiGatewayException(errors.joinToString(","))
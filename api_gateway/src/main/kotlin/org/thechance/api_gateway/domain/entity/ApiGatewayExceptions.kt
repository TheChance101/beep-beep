package org.thechance.api_gateway.domain.entity

open class ApiGatewayException(override val message: String) : Throwable(message)

class MultiErrorException(val errors: List<Int>) : ApiGatewayException(errors.joinToString(","))

class MultiLocalizedMessageException(val errors: List<String>) : ApiGatewayException(errors.joinToString(","))
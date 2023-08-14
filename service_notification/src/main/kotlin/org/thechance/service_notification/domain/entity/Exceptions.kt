package org.thechance.service_notification.domain.entity

open class NotificationServiceExceptions(message: String) : Throwable(message)

class NotFoundException(message: String) : NotificationServiceExceptions(message)

class MissingRequestParameterException(message: String) : NotificationServiceExceptions(message)

class InternalServerErrorException(message: String) : NotificationServiceExceptions(message)

class ResourceAlreadyExistsException(message: String) : NotificationServiceExceptions(message)
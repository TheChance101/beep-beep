package org.thechance.common.domain.getway

import java.io.File


interface ILocalGateway {

     suspend fun saveTaxiReport(file: File)

}
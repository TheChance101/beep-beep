package org.thechance.common.data.local


import org.thechance.common.domain.getway.ILocalGateway
import java.io.File

class LocalGateway : ILocalGateway {

    override suspend fun saveTaxiReport(file: File) {
        //todo save file
    }

}
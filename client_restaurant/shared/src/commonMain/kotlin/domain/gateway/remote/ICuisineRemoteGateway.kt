package domain.gateway.remote


interface ICuisineRemoteGateway {
    suspend fun getCuisines(): List<String>

}
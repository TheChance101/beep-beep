package domain.gateway


interface IRemoteIdentityGateway {

    suspend fun loginUser(userName: String, password: String): Pair<String, String>



}
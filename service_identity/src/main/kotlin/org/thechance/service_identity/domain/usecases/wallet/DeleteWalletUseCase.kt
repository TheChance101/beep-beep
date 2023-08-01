interface DeleteWalletUseCase {
    suspend fun invoke(id: String): Boolean
}
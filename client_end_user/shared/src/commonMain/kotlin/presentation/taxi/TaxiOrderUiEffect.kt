package presentation.taxi

sealed class TaxiOrderUiEffect {
    data object NavigateBack : TaxiOrderUiEffect()

    data object NavigateToSearch : TaxiOrderUiEffect()
}

package presentation.cart

import cafe.adriel.voyager.core.model.coroutineScope
import domain.entity.Cart
import domain.usecase.ManageCartUseCase
import presentation.base.BaseScreenModel
import presentation.base.ErrorState

class CartScreenModel(private val cartManagement: ManageCartUseCase) :
    BaseScreenModel<CartUiState, CartUiEffect>(CartUiState()),
    CartInteractionListener {
    override val viewModelScope = coroutineScope

    init {
        getCartMeals()
    }

    private fun getCartMeals() {
        tryToExecute(
            { cartManagement.getAllCartMeals() },
            ::onGetCartMealsSuccess,
            ::onError
        )
    }

    override fun onClickPlus(index: Int, count: Long) {
        val updatedCount = if (count < 99) count + 1 else count
        val meal = state.value.meals[index].copy(count = updatedCount)
        updateState {
            it.copy(
                meals = it.meals
                    .mapIndexed { mealIndex, cartMealUiState ->
                        if (mealIndex == index) meal else cartMealUiState
                    }
            )
        }
    }

    override fun onClickMinus(index: Int, count: Long) {
        val updatedCount = if (count > 1) count - 1 else count
        val meal = state.value.meals[index].copy(count = updatedCount)
        updateState {
            it.copy(
                meals = it.meals
                    .mapIndexed { mealIndex, cartMealUiState ->
                        if (mealIndex == index) meal else cartMealUiState
                    }
            )
        }
    }

    override fun onClickOrderNow() {
        println("make order")
    }

    override fun onClickBack() {
        sendNewEffect(CartUiEffect.NavigateUp)
    }

    private fun onGetCartMealsSuccess(cart: Cart) {
        val cartUiState = cart.toUiState()
        updateState {
            it.copy(
                meals = cartUiState.meals,
                totalPrice = cartUiState.totalPrice,
                currency = cartUiState.currency
            )
        }
    }

    private fun onError(error: ErrorState) {
        println("error: $error")
    }

}
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

    // region getting cart meals
    private fun getCartMeals() {
        tryToExecute(
            cartManagement::getCart,
            ::onGetCartMealsSuccess,
            ::onError
        )
    }


    private fun onGetCartMealsSuccess(cart: Cart) {
        val cartUiState = cart.toUiState()
        updateState {
            it.copy(
                meals = cartUiState.meals,
                currency = cartUiState.currency
            )
        }
    }
    // endregion

    // region saving cart
    override fun onDispose() {
        tryToExecute(
            { cartManagement.updateCart(state.value.toEntity()) },
            {},
            ::onError
        )
        super.onDispose()
    }

    private fun orderNow() {
        tryToExecute(cartManagement::orderNow, ::onOrderNowSuccess, ::onError)
    }

    private fun onOrderNowSuccess(success: Boolean) {
        if (success) {
            sendNewEffect(CartUiEffect.NavigateUp)
        }
    }
    // endregion

    // region interactions
    override fun onClickPlus(index: Int, count: Int) {
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

    override fun onClickMinus(index: Int, count: Int) {
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
        orderNow()
    }

    override fun onClickBack() {
        sendNewEffect(CartUiEffect.NavigateUp)
    }
    // endregion

    // region error handling
    private fun onError(error: ErrorState) {
        println("error: $error")
    }
    // endregion
}
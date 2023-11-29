package presentation.cart

import presentation.base.BaseInteractionListener

interface CartInteractionListener: BaseInteractionListener {
    fun onClickPlus(index: Int, count: Int)
    fun onClickMinus(index: Int, count: Int)
    fun onClickOrderNow()
    fun onDismissSnackBar()
    fun onClickBack()
}

package presentation.cart

import presentation.base.BaseInteractionListener

interface CartInteractionListener: BaseInteractionListener {
    fun onClickPlus(index: Int, count: Long)
    fun onClickMinus(index: Int, count: Long)
    fun onClickOrderNow()
    fun onClickBack()
}
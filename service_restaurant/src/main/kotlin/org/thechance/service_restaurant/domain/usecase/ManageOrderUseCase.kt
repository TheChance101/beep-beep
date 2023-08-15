package org.thechance.service_restaurant.domain.usecase

import org.thechance.service_restaurant.domain.entity.Order
import org.thechance.service_restaurant.domain.gateway.IRestaurantOptionsGateway
import org.thechance.service_restaurant.domain.usecase.validation.IOrderValidationUseCase
import org.thechance.service_restaurant.domain.utils.IValidation
import org.thechance.service_restaurant.domain.utils.OrderStatus
import org.thechance.service_restaurant.domain.utils.exceptions.INVALID_ID
import org.thechance.service_restaurant.domain.utils.exceptions.MultiErrorException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface IManageOrderUseCase {
    suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order>
    suspend fun getOrderById(orderId: String): Order
    suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Order

    suspend fun getOrdersHistory(restaurantId: String,page: Int, limit: Int): List<Order>

     fun  checkRestaurantOpen(openingTime : String,closingTime:String):Boolean

}

class ManageOrderUseCase(
    private val optionsGateway: IRestaurantOptionsGateway,
    private val basicValidation: IValidation,
    private val orderValidationUseCase: IOrderValidationUseCase
) : IManageOrderUseCase {
    override suspend fun getOrdersByRestaurantId(restaurantId: String): List<Order> {

       return optionsGateway.getOrdersByRestaurantId(restaurantId=restaurantId)
    }

    override suspend fun getOrderById(orderId: String): Order {
        if (!basicValidation.isValidId(orderId)) {
            throw MultiErrorException(listOf(INVALID_ID))
        }
        return optionsGateway.getOrderById(orderId=orderId)!!
    }

    override suspend fun updateOrderStatus(orderId: String, status: OrderStatus): Order {
        orderValidationUseCase.validateUpdateOrder(orderId=orderId,status= status)
        return optionsGateway.updateOrderStatus(orderId=orderId,status= status)!!
    }

    override suspend fun getOrdersHistory(restaurantId: String,page: Int, limit: Int): List<Order> {
        return optionsGateway.getOrdersHistory(restaurantId=restaurantId,page=page,limit= limit)
    }

    override fun checkRestaurantOpen(openTime: String, closTime: String): Boolean {
        val currentTime = Calendar.getInstance().time
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        val openingTime = sdf.parse(openTime)
        val closingTime = sdf.parse(closTime)

        return currentTime.after(openingTime) && currentTime.before(closingTime)
    }

}
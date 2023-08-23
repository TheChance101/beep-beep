package data.remote.gateway

import data.remote.model.AddressDto
import data.remote.model.MealDto
import data.remote.model.OrderDto
import data.remote.model.OrderMealDto
import data.remote.model.RestaurantDto
import data.remote.model.toEntity
import data.remote.model.toOrderEntity
import domain.entity.Category
import domain.entity.Meal
import domain.entity.Order
import domain.entity.Restaurant
import domain.gateway.IRemoteGateWay

class FakeRemoteGateWay : IRemoteGateWay {

    private val orders = mutableListOf(
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto("e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a", 1),
                OrderMealDto("ba9b9700-6d24-434b-8d67-daf9e45e1063", 2),
                OrderMealDto("ba9b9700-6d24-434b-8d67-e45e10636243", 3)
            ),
            totalPrice = 22.74,
            createdAt = 1002656085967,
            orderStatus = 0
        ),
        OrderDto(
            id = "4d7bdc9b-6233-44ef-80a0-6a09ef856862",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto("f8d21b6d-49d1-43eb-932c-5a30a5914d78", 3),
                OrderMealDto("2d5bbf8a-4854-49c6-99ed-ef09899c2d8e", 1)
            ),
            totalPrice = 28.48,
            createdAt = 1000000000000,
            orderStatus = 0
        ),
        OrderDto(
            id = "891ecf91-62bf-4d91-96bf-8d4cc8271a81",
            userId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            meals = listOf(
                OrderMealDto("b39e9f1e-0dc7-43b7-90e2-0a075b818dc5", 1),
            ),
            totalPrice = 26.49,
            createdAt = 1672656010258,
            orderStatus = 3
        ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto("e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a", 1),
                OrderMealDto("ba9b9700-6d24-434b-8d67-daf9e45e1063", 2)
            ),
            totalPrice = 22.74,
            createdAt = 1662067200000,
            orderStatus = 1
        ),
        OrderDto(
            id = "4d7bdc9b-6233-44ef-80a0-6a09ef856862",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto("f8d21b6d-49d1-43eb-932c-5a30a5914d78", 3),
                OrderMealDto("2d5bbf8a-4854-49c6-99ed-ef09899c2d8e", 1),
                OrderMealDto("2d5bbf8a-4854-49c6-99ed-ef098d549c62", 1)
            ),
            totalPrice = 28.48,
            createdAt = 1672656000000,
            orderStatus = 1
        ),
        OrderDto(
            id = "891ecf91-62bf-4d91-96bf-8d4cc8271a81",
            userId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            meals = listOf(
                OrderMealDto("b39e9f1e-0dc7-43b7-90e2-0a075b818dc5", 1),
                OrderMealDto("e772ad66-0251-412f-99a1-4a10435f9a07", 2)
            ),
            totalPrice = 26.49,
            createdAt = 1672656000333,
            orderStatus = 1
        ),
        OrderDto(
            id = "d59b00c3-923c-4cf4-bd0e-3a4c997a3156",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto("e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a", 2),
                OrderMealDto("ba9b9700-6d24-434b-8d67-daf9e45e1063", 1),
                OrderMealDto("ba9b9700-6d24-434b-8d67-e45e10636243", 3)
            ),
            totalPrice = 20.73,
            createdAt = 1672655550000,
            orderStatus = 3
        ),
        OrderDto(
            id = "9e94fdd9-9cbf-4b7e-a97e-8ea31c4876b2",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto("f8d21b6d-49d1-43eb-932c-5a30a5914d78", 2),
                OrderMealDto("2d5bbf8a-4854-49c6-99ed-ef09899c2d8e", 2),
                OrderMealDto("2d5bbf8a-4854-49c6-99ed-ef098d549c62", 1)
            ),
            totalPrice = 41.96,
            createdAt = 1672656014782,
            orderStatus = 4
        ),
        OrderDto(
            id = "c07d45e5-4c5d-4847-a518-8f21c66620f9",
            userId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            meals = listOf(
                OrderMealDto("b39e9f1e-0dc7-43b7-90e2-0a075b818dc5", 2),
                OrderMealDto("e772ad66-0251-412f-99a1-4a10435f9a07", 1)
            ),
            totalPrice = 35.98,
            createdAt = 1672656098210,
            orderStatus = 4
        ),
        OrderDto(
            id = "d59b00c3-923c-4cf4-bd0e-3a4c997a3156",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto("e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a", 2),
                OrderMealDto("ba9b9700-6d24-434b-8d67-daf9e45e1063", 1),
                OrderMealDto("ba9b9700-6d24-434b-8d67-e45e10636243", 3)
            ),
            totalPrice = 20.73,
            createdAt = 1672656012409,
            orderStatus = 0
        ),
        OrderDto(
            id = "9e94fdd9-9cbf-4b7e-a97e-8ea31c4876b2",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto("f8d21b6d-49d1-43eb-932c-5a30a5914d78", 2),
                OrderMealDto("2d5bbf8a-4854-49c6-99ed-ef09899c2d8e", 2),
                OrderMealDto("2d5bbf8a-4854-49c6-99ed-ef098d549c62", 1)
            ),
            totalPrice = 41.96,
            createdAt = 1672656025147,
            orderStatus = 0
        ),
        OrderDto(
            id = "c07d45e5-4c5d-4847-a518-8f21c66620f9",
            userId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            meals = listOf(
                OrderMealDto("b39e9f1e-0dc7-43b7-90e2-0a075b818dc5", 2),
                OrderMealDto("e772ad66-0251-412f-99a1-4a10435f9a07", 1)
            ),
            totalPrice = 35.98,
            createdAt = 1672656098742,
            orderStatus = 0
        ),
        OrderDto(
            id = "1a2b3c4d-5e6f-7a8b-9c0d-e1f2g3h4i5j6",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderMealDto("e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a", 3),
                OrderMealDto("ba9b9700-6d24-434b-8d67-daf9e45e1063", 2)
            ),
            totalPrice = 28.45,
            createdAt = 1672656012358,
            orderStatus = 1
        ),
        OrderDto(
            id = "2b3c4d5e-6f7a-8b9c-0d1e-2f3g4h5i6j7",
            userId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            meals = listOf(
                OrderMealDto("f8d21b6d-49d1-43eb-932c-5a30a5914d78", 1),
                OrderMealDto("2d5bbf8a-4854-49c6-99ed-ef09899c2d8e", 1)
            ),
            totalPrice = 23.99,
            createdAt = 1672125000000,
            orderStatus = 2
        ),
    )

    private val meals = mutableListOf(
        MealDto(
            id = "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            name = "Hummus Platter",
            imageUrl = "https://www.deliciouslycleaneats.com.au/wp-content/uploads/2018/08/Meal-Plan-Spread1.jpg",
            description = "A delicious platter of hummus served with pita bread.",
            price = 8.99
        ),
        MealDto(
            id = "f8d21b6d-49d1-43eb-932c-5a30a5914d78",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            name = "Spicy Tuna Roll",
            imageUrl = "https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/roast_chicken_for_one_41998_16x9.jpg",
            description = "A spicy roll made with fresh tuna and spicy mayo.",
            price = 12.49
        ),
        MealDto(
            id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            name = "Beignet Sampler",
            imageUrl = "https://realfood.tesco.com/media/images/472x310-Teriyaki-glazed-sausages-6c3c4a03-b353-49c9-85a3-978f326ba592-0-472x310.jpg",
            description = "A delightful sampler of beignets with different toppings.",
            price = 9.99
        ),
        MealDto(
            id = "4d1c8f5e-7f24-4df3-9835-06f0d63f8eb1",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            name = "Falafel Wrap",
            imageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
            description = "A wrap filled with crispy falafel balls and fresh veggies.",
            price = 7.95
        ),
        MealDto(
            id = "8a2a4387-1cc2-4b68-9df1-3f497ddc94a2",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            name = "Salmon Nigiri",
            imageUrl = "https://i.pinimg.com/1200x/b8/7b/c7/b87bc72f970fe00c115e3d1471956c4d.jpg",
            description = "Fresh salmon slices on bite-sized beds of seasoned rice.",
            price = 10.99
        ),
        MealDto(
            id = "a1ebe83d-617a-4e14-9e27-4d0367c4e0d2",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            name = "Gumbo",
            imageUrl = "https://www.southernliving.com/thmb/iL2CEgCAMqC4cpp6taRqwYQI1gs=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/27162_FGFsuperbowl_0359_16x9-2000-5dd253dc23044ee78aacd9673f5befbc.jpg",
            description = "A hearty stew with a mix of meats, seafood, and vegetables.",
            price = 14.75
        ),
        MealDto(
            id = "ba9b9700-6d24-434b-8d67-daf9e45e1063",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            name = "Shawarma Plate",
            imageUrl = "https://media.theeverygirl.com/wp-content/uploads/2022/05/healthy-meal-prep-dinners-teg-new-gallery.jpeg",
            description = "A plate of mouthwatering shawarma served with garlic sauce.",
            price = 13.75
        ),
        MealDto(
            id = "2d5bbf8a-4854-49c6-99ed-ef09899c2d8e",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            name = "Dragon Roll",
            imageUrl = "https://www.foodandwine.com/thmb/bRz199ONebY-5h5gcvpOcHRxAkA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Web_4000-Trifecta-Chicken-Breast-Sweet-Potato-Mixed-Vegetable_04-72a24aaee5584c06a26451603daec5c9.jpg",
            description = "An exquisite roll with eel, avocado, and a sweet soy glaze.",
            price = 15.99
        ),
        MealDto(
            id = "e772ad66-0251-412f-99a1-4a10435f9a07",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            name = "Jambalaya",
            imageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
            description = "A spicy Cajun dish with rice, sausage, chicken, and shrimp.",
            price = 16.50
        )
    )

    private val restaurants = listOf(
        RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            ownerUsername = "@Zeko",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            priceLevel = "$$$",
            address = AddressDto(latitude = 31.0285807, longitude = 38.2588888)
        ),
        RestaurantDto(
            id = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            ownerId = "8a430be2-15b7-47f5-9e06-3f236f8c25ec",
            ownerUsername = "@Uzumaki_Naruto",
            name = "Sushi Haven",
            description = "A trendy sushi restaurant offering a variety of fresh sushi rolls and sashimi.",
            rate = 3.5,
            phone = "+1234567890",
            openingTime = "11:30",
            closingTime = "21:30",
            address = AddressDto(latitude = 40.712776, longitude = -74.005974),
            priceLevel = "$$"
        ),
        RestaurantDto(
            id = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            ownerId = "f26dab15-7193-4e8d-bf6e-f4d2ae8799af",
            ownerUsername = "@Victoria_Delacroix",
            name = "Café du Monde",
            description = "A historic café known for its beignets, coffee, and vibrant atmosphere.",
            rate = 2.5,
            phone = "+18005551234",
            openingTime = "07:00",
            closingTime = "23:00",
            address = AddressDto(latitude = 29.9583507, longitude = -90.0656312),
            priceLevel = "$$"
        )
    )

    //region restaurant
    override suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant> {
        return restaurants.toEntity()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Restaurant? {
        // Just Kda W Kda 😉
        return restaurant
    }

    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant? {
        return getRestaurantsByOwnerId("7bf7ef77d907").find { it.id == restaurantId }
    }
    //endregion restaurant

    //region meal
    override suspend fun getMealsByRestaurantId(restaurantId: String): List<Meal> {
        return meals.toEntity()
    }

    override suspend fun getMealById(mealId: String): Meal? {
        return getMealsByRestaurantId("ef77d90").find { it.id == mealId }
    }

    override suspend fun addMeal(meal: Meal): Meal {
        return meal
    }

    override suspend fun updateMeal(meal: Meal): Meal {
        // Just Kda W Kda 😉
        return meal
    }
    //endregion meal

    //region order
    override suspend fun getCurrentOrders(restaurantId: String): List<Order> {
        return orders.filter { it.orderStatus != 3 || it.orderStatus != 4 }.toOrderEntity()
    }

    override suspend fun getOrdersHistory(restaurantId: String): List<Order> {
        return orders.filter { it.orderStatus == 3 || it.orderStatus == 4 }.toOrderEntity()
    }

    override suspend fun updateOrderState(orderId: String, orderState: Int): Order? {
        val order = orders.find { it.id == orderId }
        orders.indexOf(order).also { orders[it].orderStatus = orderState }
        return order?.toEntity()
    }
    //endregion order

    //region category
    override suspend fun getCategoriesByRestaurantId(restaurantId: String): Category {
        return Category(id = "8a-4854-49c6-99ed-ef09899c2", name = "Sea Food")
    }
    //endregion category

}
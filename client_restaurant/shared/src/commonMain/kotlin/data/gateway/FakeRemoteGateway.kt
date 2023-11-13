package data.gateway

import data.remote.mapper.toEntity
import data.remote.mapper.toOrderEntity
import data.remote.model.CuisineDto
import data.remote.model.LocationDto
import data.remote.model.MealDto
import data.remote.model.OrderDto
import data.remote.model.RestaurantDto
import domain.entity.Category
import domain.entity.Cuisine
import domain.entity.Location
import domain.entity.Meal
import domain.entity.Order
import domain.entity.Restaurant
import domain.gateway.IFakeRemoteGateway
import presentation.base.InvalidPasswordException
import presentation.base.RequestException

class FakeRemoteGateWay : IFakeRemoteGateway {

    private val orders = mutableListOf(
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
        OrderDto(
            id = "f5c8b31e-5c4d-4c8a-babc-0e9463daad20",
            userId = "550e8400-e29b-41d4-a716-446655440989",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            meals = listOf(
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
                    quantity = 2,
                    mealName = "Pasta",
                    price = 10.0,
                ),
                OrderDto.MealDto(
                    id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
                    mealImageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
                    quantity = 2,
                    mealName = "Breakfast burrito",
                    price = 10.0,
                )
            ),
            totalPrice = 22.74,
            createdAt = 123541235,
            orderStatus = 0,
            currency = "USD",
            restaurantImage = "https://takethemameal.com/files_images_v2/stam.jpg",
            restaurantName = "Restaurant 1",

            ),
    )

    private val cuisines = listOf(
        CuisineDto(id = "za9b9700-6d24-434b-8d67-daf9e45e1063", name = "Main Course"),
        CuisineDto(id = "xab493b4-4b8d-410a-a13e-780346243f3a", name = "Keto"),
        CuisineDto(id = "ya9b9700-6d24-434b-8d67-daf9e45e1065", name = "Pizza"),
        CuisineDto(id = "ha9b9700-6d24-434b-8d67-daf9e45e1066", name = "Dinner"),
        CuisineDto(id = "iab493b4-4b8d-410a-a13e-780346243f3c", name = "Chinese"),
        CuisineDto(id = "gab493b4-4b8d-410a-a13e-780346243f3d", name = "Italian"),
        CuisineDto(id = "kab493b4-4b8d-410a-a13e-780346243f3e", name = "Middle-East"),
        CuisineDto(id = "lab493b4-4b8d-410a-a13e-780346243f3f", name = "Indian"),
        CuisineDto(id = "mab493b4-4b8d-410a-a13e-780346243f3g", name = "French"),
        CuisineDto(id = "nab493b4-4b8d-410a-a13e-780346243f3h", name = "Japanese"),
        CuisineDto(id = "oab493b4-4b8d-410a-a13e-780346243f3i", name = "Spanish"),
        CuisineDto(id = "pab493b4-4b8d-410a-a13e-780346243f3j", name = "Greek"),
        CuisineDto(id = "qa9b9700-6d24-434b-8d67-daf9e45e1064", name = "Burrito"),
    )

    private val meals = mutableListOf(
        MealDto(
            id = "e5b1a329-6f3a-4d63-bb7f-895f1e1c2f9a",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            name = "Hummus Platter",
            imageUrl = "https://www.deliciouslycleaneats.com.au/wp-content/uploads/2018/08/Meal-Plan-Spread1.jpg",
            description = "A delicious platter of hummus served with pita bread.",
            price = 8.99,
            cuisines = cuisines.subList(0, 3)

        ),
        MealDto(
            id = "f8d21b6d-49d1-43eb-932c-5a30a5914d78",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            name = "Spicy Tuna Roll",
            imageUrl = "https://ichef.bbci.co.uk/food/ic/food_16x9_832/recipes/roast_chicken_for_one_41998_16x9.jpg",
            description = "A spicy roll made with fresh tuna and spicy mayo.",
            price = 12.49,
            cuisines = cuisines.subList(0, 3)
        ),
        MealDto(
            id = "b39e9f1e-0dc7-43b7-90e2-0a075b818dc5",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            name = "Beignet Sampler",
            imageUrl = "https://realfood.tesco.com/media/images/472x310-Teriyaki-glazed-sausages-6c3c4a03-b353-49c9-85a3-978f326ba592-0-472x310.jpg",
            description = "A delightful sampler of beignets with different toppings.",
            price = 9.99,
            cuisines = cuisines.subList(0, 4)
        ),
        MealDto(
            id = "4d1c8f5e-7f24-4df3-9835-06f0d63f8eb1",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            name = "Falafel Wrap",
            imageUrl = "https://img.taste.com.au/k1UFa9O6/w720-h480-cfill-q80/taste/2022/12/one-pan-piri-piri-chicken-183821-2.jpg",
            description = "A wrap filled with crispy falafel balls and fresh veggies.",
            price = 7.95,
            cuisines = cuisines.subList(4, 6)
        ),
        MealDto(
            id = "8a2a4387-1cc2-4b68-9df1-3f497ddc94a2",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            name = "Salmon Nigiri",
            imageUrl = "https://i.pinimg.com/1200x/b8/7b/c7/b87bc72f970fe00c115e3d1471956c4d.jpg",
            description = "Fresh salmon slices on bite-sized beds of seasoned rice.",
            price = 10.99,
            cuisines = cuisines.subList(4, 6)
        ),
        MealDto(
            id = "a1ebe83d-617a-4e14-9e27-4d0367c4e0d2",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            name = "Gumbo",
            imageUrl = "https://www.southernliving.com/thmb/iL2CEgCAMqC4cpp6taRqwYQI1gs=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/27162_FGFsuperbowl_0359_16x9-2000-5dd253dc23044ee78aacd9673f5befbc.jpg",
            description = "A hearty stew with a mix of meats, seafood, and vegetables.",
            price = 14.75,
            cuisines = cuisines.subList(4, 10)
        ),
        MealDto(
            id = "ba9b9700-6d24-434b-8d67-daf9e45e1063",
            restaurantId = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            name = "Shawarma Plate",
            imageUrl = "https://media.theeverygirl.com/wp-content/uploads/2022/05/healthy-meal-prep-dinners-teg-new-gallery.jpeg",
            description = "A plate of mouthwatering shawarma served with garlic sauce.",
            price = 13.75,
            cuisines = cuisines.subList(4, 10)
        ),
        MealDto(
            id = "2d5bbf8a-4854-49c6-99ed-ef09899c2d8e",
            restaurantId = "7c3d631e-6d49-48c9-9f91-9426ec559eb1",
            name = "Dragon Roll",
            imageUrl = "https://www.foodandwine.com/thmb/bRz199ONebY-5h5gcvpOcHRxAkA=/1500x0/filters:no_upscale():max_bytes(150000):strip_icc()/Web_4000-Trifecta-Chicken-Breast-Sweet-Potato-Mixed-Vegetable_04-72a24aaee5584c06a26451603daec5c9.jpg",
            description = "An exquisite roll with eel, avocado, and a sweet soy glaze.",
            price = 15.99,
            cuisines = cuisines.subList(4, 10)
        ),
        MealDto(
            id = "e772ad66-0251-412f-99a1-4a10435f9a07",
            restaurantId = "91c2ae1f-8495-4c0c-bc47-7bf7ef77d907",
            name = "Jambalaya",
            imageUrl = "https://takethemameal.com/files_images_v2/stam.jpg",
            description = "A spicy Cajun dish with rice, sausage, chicken, and shrimp.",
            price = 16.50,
            cuisines = cuisines.subList(4, 10)
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
            address = "paris, 123 street",
            location = LocationDto(latitude = 31.0285807, longitude = 38.2588888)
        ),
        RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = "paris, 123 street",
            location = LocationDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = "paris, 123 street",
            location = LocationDto(latitude = 31.0285807, longitude = 38.2588888)
        ),
        RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = "paris, 123 street",
            location = LocationDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = "paris, 123 street",
            location = LocationDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = "paris, 123 street",
            location = LocationDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = "paris, 123 street",
            location = LocationDto(latitude = 31.0285807, longitude = 38.2588888)
        ), RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = "paris, 123 street",
            location = LocationDto(latitude = 31.0285807, longitude = 38.2588888)
        ),
        RestaurantDto(
            id = "6ab493b4-4b8d-410a-a13e-780346243f3a",
            ownerId = "550e8400-e29b-41d4-a716-446655440989",
            name = "Zeko Tlawoth",
            description = "It's a restaurant that is famous for preparing Arabian Foods",
            rate = 4.5,
            phone = "+9641324221423",
            openingTime = "09:00",
            closingTime = "22:00",
            address = "paris, 123 street",
            location = LocationDto(latitude = 31.0285807, longitude = 38.2588888)
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
            address = "paris, 123 street",
            location = LocationDto(latitude = 40.712776, longitude = -74.005974),
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
            address = "paris, 123 street",
            location = LocationDto(latitude = 29.9583507, longitude = -90.0656312),
            priceLevel = "$$"
        )
    )

    private val ordersCount: List<Map<String, Int>> = listOf(
        mapOf(
            "1" to 500,
            "2" to 800,
            "3" to 900,
            "4" to 700,
            "5" to 300,
            "6" to 200,
            "7" to 100,
        )
    )

    private val revenue: List<Map<String, Double>> = listOf(
        mapOf(
            "1" to 500.0,
            "2" to 800.0,
            "3" to 900.0,
            "4" to 700.0,
            "5" to 300.0,
            "6" to 200.0,
            "7" to 100.0,
        )
    )


    override suspend fun loginUser(userName: String, password: String): Pair<String, String> {
        if (userName != "theChance") {
            throw InvalidPasswordException()
        }
        if (password != "theChance23") {
            throw InvalidPasswordException()
        }
        return Pair("wertqyhgt", "qazswxza")
    }


    //region restaurant
    override suspend fun getRestaurantsByOwnerId(ownerId: String): List<Restaurant> {
        return restaurants.toEntity()
    }

    override suspend fun updateRestaurantInfo(restaurant: Restaurant): Boolean {
        // Just Kda W Kda 😉
        return true
    }

    override suspend fun getRestaurantInfo(restaurantId: String): Restaurant {
        return getRestaurantsByOwnerId("7bf7ef77d907").find { it.id == restaurantId }
            ?: Restaurant(
                id = "",
                ownerId = "",
                address = "",
                location = Location(0.0, 0.0),
                phone = "",
                openingTime = "",
                closingTime = "",
                rate = 0.0,
                priceLevel = "",
                description = "",
                ownerUsername = "",
                name = "",
                imageUrl = "",
                image = byteArrayOf()
            )
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
    override suspend fun getCurrentOrders(): List<Order> {
        return orders.filter { it.orderStatus == 0 || it.orderStatus == 1 }.toOrderEntity()
    }

    override suspend fun getOrdersHistory(restaurantId: String): List<Order> {
        return orders.filter { it.orderStatus == 2 || it.orderStatus == 3 }.toOrderEntity()
    }

    override suspend fun updateOrderState(orderId: String, orderState: Int): Order {
        val order = orders.find { it.id == orderId }
        return order?.toEntity() ?: throw RequestException("")
    }

    override suspend fun getOrderById(orderId: String): Order? {
        return orders.find { it.id == orderId }?.toEntity()
    }
    //endregion order

    //region category
    override suspend fun getCategoriesByRestaurantId(restaurantId: String): Category {
        return Category(id = "8a-4854-49c6-99ed-ef09899c2", name = "Sea Food")
    }
    //endregion category

    //region cuisines
    override suspend fun getCuisines(): List<Cuisine> {
        return cuisines.toEntity()
    }

    override suspend fun getCuisinesInMeal(mealId: String): List<Cuisine> {
        val mealCuisines = meals.find { it.id == mealId }?.cuisines
        return if (mealCuisines != null) {
            val cuisines = cuisines.filter { it.id in mealCuisines.map { it.id } }
            cuisines.toEntity()
        } else {
            throw Throwable()
        }
    }

    override suspend fun getCuisineByRestaurantId(restaurantId: String): List<Cuisine> {
        return cuisines.toEntity()
    }

    override suspend fun getMealsByCuisineId(id: String): List<Meal> {
        return meals.filter { meal ->
            meal.cuisines?.find { it.id == id } != null
        }.toEntity()
    }


    //endregion cuisines


    //region charts
    override suspend fun getOrdersRevenueByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Map<String, Double>> {
        return revenue
    }

    override suspend fun getOrdersCountByDaysBefore(
        restaurantId: String,
        daysBack: Int
    ): List<Map<String, Int>> {
        return ordersCount
    }

    //endregion charts


}
